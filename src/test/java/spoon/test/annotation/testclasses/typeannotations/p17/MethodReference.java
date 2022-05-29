package spoon.test.annotation.testclasses.typeannotations.p17;

import spoon.test.annotation.testclasses.typeannotations.TypeUseA;

import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class MethodReference {
	private ToIntFunction<String> a = @TypeUseA String::hashCode;
	private Supplier<String> b = @TypeUseA String::new;
	private IntFunction<int[]> c = @TypeUseA int[]::new;
}
