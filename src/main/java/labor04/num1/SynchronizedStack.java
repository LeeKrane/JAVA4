package labor04.num1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedStack<E> {
	private E []elements;
	private int size;
	
	private ReentrantLock lock = new ReentrantLock();
	private Condition isEmpty = lock.newCondition();
	private Condition isFull = lock.newCondition();
	
	@SuppressWarnings("unchecked")
	SynchronizedStack (int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException();
		elements = (E[]) new Object[capacity];
	}
	
	void push (E e) throws InterruptedException {
		lock.lock();
		while (elements.length == size)
			isFull.await();
		
		elements[size] = e;
		size++;
		isEmpty.signal();
		
		lock.unlock();
	}
	
	E pop () throws InterruptedException {
		lock.lock();
		while (size == 0)
			isEmpty.await();
		
		E ret = elements[size - 1];
		elements[size - 1] = null;
		size--;
		isFull.signal();
		
		lock.unlock();
		return ret;
	}
	
	public int size () {
		return size;
	}
	
	@Override
	public String toString () {
		StringBuilder builder = new StringBuilder("[");
		
		for (int i = 0; i < size; i++) {
			builder.append(elements[i]);
			if (i < size - 1)
				builder.append(", ");
		}
		
		return builder.append("]").toString();
	}
}
