package box.keyvaluestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

/*

 Implement a key-value storage (in-memory database) with get/set/delete/countValue and nested transactional support.
 Commit commits all transactions but roll back only rolls back the last transaction.

 */

public class KeyValueStorage {

	// Improvement: No need to store the new value in the log, as it is already
	// in the storage

	private final Map<String, Object> storage = new HashMap<>();
	private final Map<Object, Integer> count = new HashMap<>();
	private Transaction current = null;

	private class Log {
		private final String key;
		private final Object oldValue;
		private final Object newValue;

		private Log(String key, Object oldValue, Object newValue) {
			super();
			this.key = key;
			this.oldValue = oldValue;
			this.newValue = newValue;
		}
	}

	private class Transaction {
		private final Stack<Log> logs = new Stack<>();
		private Transaction parent = null;

		private void addLog(String key, Object oldValue, Object newValue) {
			logs.add(new Log(key, oldValue, newValue));
		}
	}

	public void set(String key, Object value) {

		Object oldValue = storage.put(key, value);

		if (current != null) {
			current.addLog(key, oldValue, value);
		}

		if (oldValue != null) {
			count.put(oldValue, count.get(oldValue) - 1);
		}

		if (count.containsKey(value)) {
			count.put(value, count.get(value) + 1);
		} else {
			count.put(value, 1);
		}
	}

	public Object get(String key) {
		return storage.get(key);
	}

	public int count(Object value) {
		if (count.containsKey(value)) {
			return count.get(value);
		} else
			return 0;
	}

	public void delete(String key) {

		Object oldValue = storage.remove(key);

		if (current != null) {
			current.addLog(key, oldValue, null);
		}

		if (oldValue != null) {
			count.put(oldValue, count.get(oldValue) - 1);
		}
	}

	public void beginTransaction() {
		Transaction t = new Transaction();
		t.parent = current;
		current = t;
	}

	public void commit() {
		if (current == null)
			throw new RuntimeException("No Open Transaction.");
		current = null;
	}

	public void rollBack() {
		if (current == null)
			throw new RuntimeException("No Open Transaction.");

		Stack<Log> logs = current.logs;
		while (!logs.isEmpty()) {
			Log current = logs.pop();
			if (current.oldValue == null) {
				storage.remove(current.key);
			} else {
				storage.put(current.key, current.oldValue);
			}

			if (current.oldValue != null) {
				count.put(current.oldValue, count.get(current.oldValue) + 1);
			}
			if (current.newValue != null) {
				count.put(current.newValue, count.get(current.newValue) - 1);
			}
		}
		current = current.parent;
	}

	public static void run() {
		KeyValueStorage db = new KeyValueStorage();
		boolean exit = false;
		try (Scanner sc = new Scanner(System.in);) {

			while (!exit) {
				String line = sc.nextLine();
				StringTokenizer st = new StringTokenizer(line);
				String operand = st.nextToken();
				switch (operand) {
					case "SET":
						db.set(st.nextToken(), Integer.valueOf(st.nextToken()));
						break;
					case "GET":
						Object value = db.get(st.nextToken());
						if (value == null) {
							System.out.println("NULL");
						} else {
							System.out.println(value);
						}
						break;
					case "DELETE":
						db.delete(st.nextToken());
						break;
					case "COUNT":
						System.out.println(db.count(Integer.valueOf(st
								.nextToken())));
						break;
					case "BEGIN":
						db.beginTransaction();
						break;
					case "ROLLBACK":
						try {
							db.rollBack();
						} catch (RuntimeException e) {
							System.out.println("NO TRANSACTION");
						}
						break;
					case "COMMIT":
						try {
							db.commit();
						} catch (RuntimeException e) {
							System.out.println("NO TRANSACTION");
						}
						break;
					case "END":
						exit = true;
						break;
					default:
						throw new RuntimeException("Unsupported operand!");
				}
			}
		}
	}

	public static void main(String[] args) {
		run();
	}
}
