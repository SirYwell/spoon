package spoon.test.annotation.testclasses.typeannotations.pvarargs;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public class Varargs {
	void m(final String @TypeUseA ... a) { }
}
