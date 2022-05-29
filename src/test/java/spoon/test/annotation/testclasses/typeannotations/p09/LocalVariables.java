package spoon.test.annotation.testclasses.typeannotations.p09;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class LocalVariables {
	void m() {
		@TypeUseA int a;
		for (@TypeUseA int b; ; ) {
			break;
		}
		for (@TypeUseA String s : List.<String>of()) { }
		try (@TypeUseA OutputStream os = OutputStream.nullOutputStream()) {
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
