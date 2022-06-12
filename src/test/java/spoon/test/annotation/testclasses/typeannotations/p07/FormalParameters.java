package spoon.test.annotation.testclasses.typeannotations.p07;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class FormalParameters {
	public void m(final @TypeUseA String b) { }
	public FormalParameters(final @TypeUseA String a) { }
	Consumer<String> consumer = (final @TypeUseA String c) -> { };
}
