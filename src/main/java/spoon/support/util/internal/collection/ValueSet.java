package spoon.support.util.internal.collection;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

final class ValueSet<K, E> extends AbstractSet<E> implements Serializable {
	private static final long serialVersionUID = 1L;

	private final ValueSetMap<K, E> map;

	ValueSet(ValueSetMap<K, E> map) {
		this.map = map;
	}

	@Override
	public boolean add(E e) {
		K apply = map.valueToKeyMapper().apply(e);
		return map.computeIfAbsent(apply, k -> e) == e;
	}

	@Override
	public Iterator<E> iterator() {
		return new ValuesSetIterator();
	}

	@Override
	public int size() {
		return map.size();
	}

	@SuppressWarnings("SuspiciousMethodCalls")
	@Override
	public boolean contains(Object o) {
		return map.containsValue(o);
	}

	private class ValuesSetIterator implements Iterator<E> {
		private final Iterator<? extends Map.Entry<?, E>> iterator = map.entrySet().iterator();
		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public E next() {
			return iterator.next().getValue();
		}
	}
}
