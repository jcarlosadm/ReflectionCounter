package queue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Queue {
	private static final int SIZE = 100;
	
	private List<Integer> list = new ArrayList<>();

	public void add(int element) throws IllegalStateException {
		if (this.list.size() >= SIZE)
			throw new IllegalStateException();
		
		this.list.add(element);
	}

	public int remove() throws NoSuchElementException {
		if (this.list.isEmpty())
			throw new NoSuchElementException();

		return this.list.remove(0);
	}

	public int element() throws NoSuchElementException {
		if (this.list.isEmpty())
			throw new NoSuchElementException();

		return this.list.get(0);
	}

	public int size() {
		return this.list.size();
	}
}