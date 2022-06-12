package spoon.test.annotation.testclasses.typeannotations.p14;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;
import spoon.test.annotation.testclasses.typeannotations.TypeUseB;

public class ArrayCreation {
	static String[] a = new @TypeUseA String[0];
	static String[] b = new @TypeUseB String[] {};
}
