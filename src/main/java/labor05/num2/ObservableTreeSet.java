package labor05.num2;

import java.time.LocalDateTime;
import java.util.*;

public class ObservableTreeSet<T> extends TreeSet<T> {
	// TODO: FX Anwendung
	
	private final Set<ObservableTreeSetListener<T>> observerSet = new HashSet<>();
	
	public ObservableTreeSet () {
		super();
	}
	
	public ObservableTreeSet (Comparator<? super T> comparator) {
		super(comparator);
	}
	
	public ObservableTreeSet (Collection<? extends T> c) {
		super(c);
	}
	
	public ObservableTreeSet (SortedSet<T> s) {
		super(s);
	}
	
	public void addObservableTreeSetListener (ObservableTreeSetListener<T> listener) {
		observerSet.add(listener);
	}
	
	public void removeObservableTreeSetListener (ObservableTreeSetListener<T> listener) {
		observerSet.remove(listener);
	}
	
	private void notifyObserver (ObservableTreeSetEvent<T> event) {
		observerSet.forEach(o -> o.observableTreeSetChanged(event));
	}
	
	@Override
	public boolean add (T t) {
		int oldSize = size();
		boolean ret = super.add(t);
		notifyObserver(new ObservableTreeSetEvent<>(oldSize, size(), this, "add", LocalDateTime.now()));
		return ret;
	}
	
	@Override
	public boolean remove (Object o) {
		int oldSize = size();
		boolean ret = super.remove(o);
		notifyObserver(new ObservableTreeSetEvent<>(oldSize, size(), this, "remove", LocalDateTime.now()));
		return ret;
	}
	
	@Override
	public boolean addAll (Collection<? extends T> c) {
		int oldSize = size();
		boolean ret = super.addAll(c);
		notifyObserver(new ObservableTreeSetEvent<>(oldSize, size(), this, "addAll", LocalDateTime.now()));
		return ret;
	}
	
	@Override
	public T pollFirst () {
		int oldSize = size();
		T ret = super.pollFirst();
		notifyObserver(new ObservableTreeSetEvent<>(oldSize, size(), this, "pollFirst", LocalDateTime.now()));
		return ret;
	}
	
	@Override
	public T pollLast () {
		int oldSize = size();
		T ret = super.pollLast();
		notifyObserver(new ObservableTreeSetEvent<>(oldSize, size(), this, "pollLast", LocalDateTime.now()));
		return ret;
	}
	
	@Override
	public boolean removeAll (Collection<?> c) {
		int oldSize = size();
		boolean ret = super.removeAll(c);
		notifyObserver(new ObservableTreeSetEvent<>(oldSize, size(), this, "removeAll", LocalDateTime.now()));
		return ret;
	}
	
	@Override
	public boolean retainAll (Collection<?> c) {
		int oldSize = size();
		boolean ret = super.retainAll(c);
		notifyObserver(new ObservableTreeSetEvent<>(oldSize, size(), this, "retainAll", LocalDateTime.now()));
		return ret;
	}
}
