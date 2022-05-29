package spoon.test.annotation.testclasses.typeannotations.p07;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

import java.util.function.IntConsumer;

public class FormalParameters {
	public void m(@TypeUseA int b) { }
	public FormalParameters(@TypeUseA int a) { }
	IntConsumer consumer = (@TypeUseA int c) -> { };
}
