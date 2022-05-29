package spoon.test.annotation.testclasses.typeannotations.p12;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public class MethodInvocation {

	public <T> void m() {
		this.<@TypeUseA T>m();
	}
}
