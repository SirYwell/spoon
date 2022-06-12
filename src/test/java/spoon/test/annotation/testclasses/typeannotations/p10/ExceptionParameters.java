package spoon.test.annotation.testclasses.typeannotations.p10;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;
import spoon.test.annotation.testclasses.typeannotations.TypeUseB;

public class ExceptionParameters {

	public void uniCatch() {
		try {
		} catch (@TypeUseA Exception e) {
		}
	}

	public void multiCatch() {
		try {
		} catch (final @TypeUseA IllegalStateException | @TypeUseB IllegalArgumentException e) {
		}
	}
}
