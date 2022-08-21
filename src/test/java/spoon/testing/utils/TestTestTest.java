package spoon.testing.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ExtendWith(FileWriteDetector.class)
// @Disabled
public class TestTestTest {

	@Test
	void writeFile() throws IOException {
		Files.writeString(Path.of("a.txt"), "Hello World");
	}
	@Test
	void writeLegal(@TempDir Path dir) throws IOException {
		Files.writeString(dir.resolve("a.txt"), "Hello World");
	}
}
