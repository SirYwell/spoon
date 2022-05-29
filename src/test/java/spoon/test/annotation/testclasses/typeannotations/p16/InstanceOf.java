package spoon.test.annotation.testclasses.typeannotations.p16;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public class InstanceOf {
	public boolean m(Object o) {
		return o instanceof @TypeUseA String;
	}
}
