package spoon.test.annotation.testclasses.typeannotations.p03;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public @interface OnReturnTypeA {
	@TypeUseA String value();
}
