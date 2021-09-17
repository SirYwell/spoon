package spoon.support.util.internal.collection;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * A bijective map that exposes a value {@link Set} instead of just a {@link java.util.Collection}.
 *
 * The {@link #containsValue(Object)} call has the same performance characteristics as {@link #get(Object)}
 * with the overhead of the {@link #valueToKeyMapper()}.
 *
 * This map does not allow null keys or values (and therefore the {@link #values()} Set does not allow
 * {@code null} values either).
 *
 * @param <K> the key type.
 * @param <V> the value type.
 */
public interface ValueSetMap<K, V> extends Map<K, V> {

	/**
	 * Returns a function that maps values to keys.
	 *
	 * @return the function to map a value to a key.
	 */
	Function<? super V, ? extends K> valueToKeyMapper();

	/**
	 * Returns the type of V as class.
	 *
	 * @return the type of the value in this map.
	 */
	Class<? extends V> valueType();

	@Override
	Set<V> values();

	/**
	 * Adds a value to the map.
	 * The key used for the value is determined by using the given {@link #valueToKeyMapper()} function.
	 *
	 * This method behaves the same as {@link Set#add(Object)}, means that an existing object is not replaced.
	 *
	 * @param v the value to add.
	 * @return {@code true} if the map was modified by adding the value.
	 */
	default boolean add(V v) {
		return values().add(v);
	}

	@Override
	default boolean containsValue(Object value) {
		if (value != null && !valueType().isAssignableFrom(value.getClass())) {
			return false;
		}
		@SuppressWarnings("unchecked") // value is either null or can be casted
		K key = valueToKeyMapper().apply((V) value);
		return Objects.equals(get(key), value);
	}
}
