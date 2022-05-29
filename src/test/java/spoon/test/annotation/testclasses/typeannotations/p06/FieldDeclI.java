package spoon.test.annotation.testclasses.typeannotations.p06;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public interface FieldDeclI {
	@TypeUseA int a = 0;
	public @TypeUseA int d = 0;
}
