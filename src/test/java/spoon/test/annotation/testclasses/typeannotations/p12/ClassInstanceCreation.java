package spoon.test.annotation.testclasses.typeannotations.p12;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public class ClassInstanceCreation {
	public <T> ClassInstanceCreation() {}
	public void m() {
		new <@TypeUseA String>ClassInstanceCreation();
	}
}
