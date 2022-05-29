package spoon.test.annotation.testclasses.typeannotations.p13;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;
import spoon.test.annotation.testclasses.typeannotations.TypeUseB;

public class InstanceCreation {
	static InstanceCreation a = new @TypeUseA InstanceCreation();
	static InstanceCreation b = new @TypeUseB InstanceCreation() { };
}
