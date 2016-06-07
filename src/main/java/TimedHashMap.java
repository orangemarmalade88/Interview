import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimedHashMap {

	// key -> map<timestamp, value>
	private final Map<Integer, TreeMap<Long, Integer>> keyToTreeMap = new HashMap<>();

	public void put(int key, int value, long timestamp) {
		TreeMap<Long, Integer> map = keyToTreeMap.get(key);
		if (map == null) {
			map = new TreeMap<>();
			map.put(timestamp, value);
			keyToTreeMap.put(key, map);
		} else {
			map.put(timestamp, value);
		}
	}

	public Integer get(int key, long timestamp) {
		TreeMap<Long, Integer> map = keyToTreeMap.get(key);
		if (map == null)
			return null;
		else
			return map.floorEntry(timestamp) == null ? null : map.floorEntry(
					timestamp).getValue();
	}

	public Integer get(int key) {
		TreeMap<Long, Integer> map = keyToTreeMap.get(key);
		if (map == null)
			return null;
		else
			return map.lastEntry() == null ? null : map.lastEntry().getValue();
	}

	public static void main(String[] args) {
		TimedHashMap map = new TimedHashMap();
		map.put(1, 1, 10);

		System.out.println(map.get(1, 9));
		System.out.println(map.get(1, 10));
		System.out.println(map.get(1, 11));

		map.put(1, 2, 20);

		System.out.println(map.get(1, 9));
		System.out.println(map.get(1, 10));
		System.out.println(map.get(1, 11));
		System.out.println(map.get(1, 19));
		System.out.println(map.get(1, 20));
		System.out.println(map.get(1, 21));
	}
}
