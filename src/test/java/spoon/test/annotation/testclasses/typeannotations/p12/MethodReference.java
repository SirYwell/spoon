package spoon.test.annotation.testclasses.typeannotations.p12;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

import java.util.function.Supplier;

public class MethodReference {
	public <T> MethodReference() { }
	public Supplier<MethodReference> f = MethodReference::<@TypeUseA String>new;
	public Supplier<?> f2 = new N2()::<@TypeUseA String>value;

	static class N1 {
		public <T> T value() {
			return null;
		}
	}
	static class N2 extends N1 {
		public Supplier<?> f = super::<@TypeUseA String>value;
	}
}
