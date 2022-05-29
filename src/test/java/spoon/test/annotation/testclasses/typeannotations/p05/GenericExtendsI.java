package spoon.test.annotation.testclasses.typeannotations.p05;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public interface GenericExtendsI<T extends @TypeUseA CharSequence> {
	<M extends @TypeUseA Cloneable> void m();
}
