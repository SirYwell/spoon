package spoon.test.annotation.testclasses.typeannotations.p05;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public class GenericExtendsC<T extends @TypeUseA CharSequence> {
	<C extends @TypeUseA String> GenericExtendsC() {}
	<M extends @TypeUseA Cloneable> void m() {}
}
