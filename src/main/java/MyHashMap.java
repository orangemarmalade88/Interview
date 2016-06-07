public class MyHashMap {

	private class Node {
		int key;
		int value;
		Node next;

		Node(int key, int value) {
			this.key = key;
			this.value = value;
		}
	}

	private final int capacity = 16;
	private int size = 0;
	private Node[] array = null;

	MyHashMap() {
		array = new Node[capacity];
	}

	private int hash(Integer key) {
		// return key.hashCode();
		return key;
	}

	int size() {
		return size;
	}

	void put(Integer key, Integer value) {
		if (key == null || value == null) {
			throw new RuntimeException();
		}

		int index = hash(key) % capacity;

		if (array[index] == null) {
			array[index] = new Node(key, value);
			size++;
		} else {
			Node curr = array[index];
			while (curr.key != key && curr.next != null) {
				curr = curr.next;
			}
			if (curr.key == key) {
				curr.value = value;
			} else {
				curr.next = new Node(key, value);
				size++;
			}
		}
	}

	Integer get(Integer key) {
		if (key == null) {
			throw new RuntimeException();
		}

		int index = hash(key) % capacity;
		if (array[index] == null) {
			return null;
		} else {
			Node curr = array[index];
			while (curr.key != key && curr.next != null) {
				curr = curr.next;
			}
			if (curr.key == key) {
				return curr.value;
			} else {
				return null;
			}
		}
	}

	void remove(Integer key) {

	}

	public static void main(String[] args) {
		MyHashMap map = new MyHashMap();
		map.put(2, 102);
		map.put(3, 103);
		map.put(4, 104);
		map.put(18, 118);
		map.put(34, 134);

		System.out.println(map.get(2));
		System.out.println(map.get(3));
		System.out.println(map.get(4));
		System.out.println(map.get(18));

		System.out.println(map.get(34));
		System.out.println(map.get(35));

		System.out.println(map.size());

		map.put(2, 2);
		map.put(3, 3);
		map.put(4, 4);
		map.put(18, 18);
		map.put(34, 34);

		System.out.println(map.get(2));
		System.out.println(map.get(3));
		System.out.println(map.get(4));
		System.out.println(map.get(18));
		System.out.println(map.get(34));
		System.out.println(map.get(35));

		System.out.println(map.size());
	}

}
