package spoon.test.annotation.testclasses.typeannotations.p14;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;
import spoon.test.annotation.testclasses.typeannotations.TypeUseB;

public class ArrayCreation {
	static int[] a = new @TypeUseA int[0];
	static int[] b = new @TypeUseB int[] {};
}
