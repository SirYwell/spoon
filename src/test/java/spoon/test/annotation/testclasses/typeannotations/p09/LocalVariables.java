package spoon.test.annotation.testclasses.typeannotations.p09;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class LocalVariables {
	void m() {
		final @TypeUseA String a;
		for (final @TypeUseA String b; ; ) {
			break;
		}
		for (final @TypeUseA String s : List.<String>of()) { }
		try (final @TypeUseA OutputStream os = null) {
		} catch (IOException e) {
		}
	}
}
