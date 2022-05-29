package spoon.test.annotation.testclasses.typeannotations.p04;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public class OnThrowsTypeC {
	OnThrowsTypeC() throws @TypeUseA Exception {}

	void m() throws @TypeUseA Exception {}
}
