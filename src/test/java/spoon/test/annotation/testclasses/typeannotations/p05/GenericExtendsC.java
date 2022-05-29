package spoon.test.annotation.testclasses.typeannotations.p05;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

import java.io.Serializable;

public class GenericExtendsC<T extends @TypeUseA CharSequence> {
	<C extends @TypeUseA Serializable> GenericExtendsC() {}
	<M extends @TypeUseA Cloneable> void m() {}
}
