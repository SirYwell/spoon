package spoon.testing.utils;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedClass;
import jdk.jfr.consumer.RecordedClassLoader;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedFrame;
import jdk.jfr.consumer.RecordedMethod;
import jdk.jfr.consumer.RecordedStackTrace;
import jdk.jfr.consumer.RecordingFile;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.kohsuke.MetaInfServices;
import org.opentest4j.AssertionFailedError;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@MetaInfServices
public class FileWriteDetector implements BeforeEachCallback, AfterEachCallback {

	private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create("spoon", "fileWriteDetector");
	private static final PathMatcher TEMP_DIR_MATCHER = createTempDirMatcher();

	private static PathMatcher createTempDirMatcher() {
		Path tmpDir = Path.of(System.getProperty("java.io.tmpdir"));
		return path -> path.startsWith(tmpDir);
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		Path tempFile = Files.createTempFile("test-recording", ".jfr");
		context.getStore(NAMESPACE).put(Path.class, tempFile);

		Recording recording = new Recording();
		context.getStore(NAMESPACE).put(Recording.class, recording);
		recording.setToDisk(true);
		recording.setDestination(tempFile);
		recording.enable("jdk.FileWrite").withStackTrace();
		recording.start();
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		PathMatcher allowedWrites = createPathMatcher(context);

		Path tempFile = context.getStore(NAMESPACE).remove(Path.class, Path.class);
		try (Recording recording = context.getStore(NAMESPACE).remove(Recording.class, Recording.class)) {
			// close
		}
		List<Path> accepted = new ArrayList<>();
		try (RecordingFile recordingFile = new RecordingFile(tempFile)) {
			while (recordingFile.hasMoreEvents()) {
				RecordedEvent event = recordingFile.readEvent();
				String raw = event.getString("path");
				Path path = Path.of(raw).toAbsolutePath();
				if (path.equals(tempFile)) {
					continue;
				}
				if (!allowedWrites.matches(path)) {
					RecordedStackTrace stackTrace = event.getStackTrace();
					if (stackTrace != null) {
						throw constructExceptionFromRecorded(stackTrace, path);
					} else {
						fail("Illegal write to " + path + " (no stacktrace present)");
					}
				} else {
					accepted.add(path);
				}
			}
		} finally {
			Files.delete(tempFile);
		}
		context.publishReportEntry("AcceptedFileWritesSize", String.valueOf(accepted.size()));
	}

	private Stacktrace constructExceptionFromRecorded(RecordedStackTrace stackTrace, Path path) {
		StackTraceElement[] elements = stackTrace.getFrames().stream()
				.filter(RecordedFrame::isJavaFrame)
				.map(frame -> {
					RecordedMethod method = frame.getMethod();
					RecordedClass type = method.getType();
					RecordedClassLoader classLoader = type.getClassLoader();
					String classLoaderName = classLoader == null ? null : classLoader.getName();
					return new StackTraceElement(
							classLoaderName,
							tryGetModule(type),
							null,
							type.getName(),
							method.getName(),
							tryGetClass(type),
							frame.getLineNumber()
					);
				}).toArray(StackTraceElement[]::new);
		Stacktrace stacktrace = new Stacktrace("Illegal write to " + path);
		stacktrace.setStackTrace(elements);
		return stacktrace;
	}

	static class Stacktrace extends AssertionFailedError {
		public Stacktrace(String message) {
			super(message);
		}

		@Override
		public synchronized Throwable fillInStackTrace() {
			return this;
		}
	}

	private String tryGetClass(RecordedClass type) {
		try {
			Class<?> aClass = Class.forName(type.getName());
			Class<?> current = aClass;
			while ((current = current.getDeclaringClass()) != null) {
				aClass = current;
			}
			return aClass.getSimpleName() + ".java";
		} catch (ClassNotFoundException ignored) {
			return null;
		}
	}

	private String tryGetModule(RecordedClass type) {
		try {
			return Class.forName(type.getName()).getModule().getName();
		} catch (ClassNotFoundException ignored) {
			return null;
		}
	}

	private PathMatcher createPathMatcher(ExtensionContext context) {
		Method method = context.getRequiredTestMethod();
		List<PathMatcher> matchers = new ArrayList<>();
		matchers.add(TEMP_DIR_MATCHER);
		FileSystem system = FileSystems.getDefault();
		for (AllowedPath path : method.getAnnotationsByType(AllowedPath.class)) {
			matchers.add(system.getPathMatcher(path.type() + ":" + path.value()));
		}
		return new MultiPathMatcher(matchers);
	}

	@Retention(RetentionPolicy.RUNTIME)
	@interface AllowedPaths {
		AllowedPath[] value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Repeatable(AllowedPaths.class)
	public @interface AllowedPath {
		String value();
		PathMatchSyntax type() default PathMatchSyntax.GLOB;
	}

	public enum PathMatchSyntax {
		REGEX,
		GLOB
	}

	static class MultiPathMatcher implements PathMatcher {
		private final List<PathMatcher> pathMatchers;

		MultiPathMatcher(List<PathMatcher> pathMatchers) {
			this.pathMatchers = pathMatchers;
		}

		@Override
		public boolean matches(Path path) {
			return pathMatchers.stream().anyMatch(pathMatcher -> pathMatcher.matches(path));
		}
	}
}
