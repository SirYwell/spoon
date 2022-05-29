package spoon.test.annotation.testclasses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.function.Function;
import java.util.function.Supplier;

public class AnnotationsInSeveralPlaces implements @TypeUse("C") A<@TypeUse Object @TypeUse2 [] @TypeUse []> {

	public AnnotationsInSeveralPlaces() {
		<@TypeUse String>this(null);
	}

	public <T> AnnotationsInSeveralPlaces(@TypeUse2 String a) {

	}
	void throwing() throws @TypeUse2 @TypeUse Exception {
		try {
			throw new Exception();
		} catch (@TypeUse IllegalArgumentException | @TypeUse2 IllegalStateException e) {
			throw new @TypeUse @TypeUse2 Error(e);
		}
	}
	Function<String, String> identity = (@TypeUse2 String s) -> s;
	Supplier<I> identity2 = @TypeUse("C") I2::<@TypeUse String>new;
	String value = (@TypeUse String) null;
	boolean isString(@TypeUse AnnotationsInSeveralPlaces this, Object o) {
		@TypeUse("C")
		boolean b = o instanceof @TypeUse String;
		return b;
	}

	class I {
		public <T> I() {
		}
	}

	class I2 extends I {
		I2() {
			new @TypeUse("C") AnnotationsInSeveralPlaces().<@TypeUse I2>super();
		}
		I2(AnnotationsInSeveralPlaces o) {
			o.<@TypeUse String>super();
		}
	}
}
interface A<T> { }
interface B extends @TypeUse("C") A<@TypeUse String> { }
interface C<T extends @TypeUse2 CharSequence> extends A<@TypeUse T> { }
enum E {
	@TypeUse("C") A;
}
@Target(ElementType.TYPE_USE)
@interface TypeUse {
	String value() default "";
}
@Target(ElementType.TYPE_USE)
@interface TypeUse2 {
}
