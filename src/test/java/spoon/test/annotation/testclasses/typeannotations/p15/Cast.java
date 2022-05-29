package spoon.test.annotation.testclasses.typeannotations.p15;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

public class Cast {
	static int a = (@TypeUseA int) 1.5d;
}
