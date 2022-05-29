package spoon.test.annotation.testclasses.typeannotations.p12;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public class ExplicitConstructorInvocation {
	public <T> ExplicitConstructorInvocation() {}
	class I1 extends ExplicitConstructorInvocation {
		<T> I1() {
			<@TypeUseA String>super();
		}

		I1(Void v) {
			<@TypeUseA String>this();
		}
	}
	private ExplicitConstructorInvocation eField;
	private ExplicitConstructorInvocation[] eFieldArray;
	private ExplicitConstructorInvocation eMethod() {
		return new ExplicitConstructorInvocation();
	}
	class I2 extends I1 {
		I2() {
			new ExplicitConstructorInvocation().<@TypeUseA String>super();
		}
		I2(ExplicitConstructorInvocation eParam) {
			eParam.<@TypeUseA String>super();
		}
		I2(Void v) {
			eField.<@TypeUseA String>super();
		}
		I2(Void v1, Void v2) {
			eFieldArray[0].<@TypeUseA String>super();
		}
		I2(Void v1, Void v2, Void v3) {
			eMethod().<@TypeUseA String>super();
		}
	}
}
