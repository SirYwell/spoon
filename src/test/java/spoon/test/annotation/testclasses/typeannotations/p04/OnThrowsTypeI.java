package spoon.test.annotation.testclasses.typeannotations.p04;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public interface OnThrowsTypeI {
	void m() throws @TypeUseA Exception;
}
