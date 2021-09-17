package spoon.support.util.internal.collection;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A bijective map that wraps another map and provides a {@link Set} view for the values.
 *
 * This implementation is meant to work with wrapping thread-safe map implementations but does
 * not have any own synchronization mechanisms.
 *
 * @param <K> the key type.
 * @param <V> the value type.
 */
public class CompositeValueSetMap<K, V> implements ValueSetMap<K, V> {
	private final Class<V> valueType;
	private final Function<? super V, ? extends K> toKeyMapper;
	private final Map<K, V> underlying;
	private final Set<V> values;

	public CompositeValueSetMap(Class<V> valueType, Function<? super V, ? extends K> toKeyMapper, Map<K, V> underlying) {
		this.valueType = valueType;
		this.toKeyMapper = toKeyMapper;
		this.underlying = underlying;
		this.values = new ValueSet<>(this);
	}

	@Override
	public Function<? super V, ? extends K> valueToKeyMapper() {
		return toKeyMapper;
	}

	@Override
	public Class<? extends V> valueType() {
		return valueType;
	}

	@Override
	public Set<V> values() {
		return values;
	}

	@Override
	public int size() {
		return underlying.size();
	}

	@Override
	public boolean isEmpty() {
		return underlying.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return underlying.containsKey(key);
	}

	@Override
	public V get(Object key) {
		return underlying.get(key);
	}

	@Override
	public V put(K key, V value) {
		return underlying.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return underlying.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		underlying.putAll(m);
	}

	@Override
	public void clear() {
		underlying.clear();
	}

	@Override
	public Set<K> keySet() {
		return underlying.keySet();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return underlying.entrySet();
	}

	@Override
	public V getOrDefault(Object key, V defaultValue) {
		return underlying.getOrDefault(key, defaultValue);
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		underlying.forEach(action);
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		underlying.replaceAll(function);
	}

	@Override
	public V putIfAbsent(K key, V value) {
		return underlying.putIfAbsent(key, value);
	}

	@Override
	public boolean remove(Object key, Object value) {
		return underlying.remove(key, value);
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return underlying.replace(key, oldValue, newValue);
	}

	@Override
	public V replace(K key, V value) {
		return underlying.replace(key, value);
	}

	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return underlying.computeIfAbsent(key, mappingFunction);
	}

	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return underlying.computeIfPresent(key, remappingFunction);
	}

	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return underlying.compute(key, remappingFunction);
	}

	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return underlying.merge(key, value, remappingFunction);
	}
}
