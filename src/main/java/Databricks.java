
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Databricks {
	/*
	 * Click `Run` to execute the snippet below!
	 */

	// To implement:
	// insert(revenue: int) -> int (auto-incrementing customer id starting at 0)
	// insert(revenue: int, referrer: int) -> int (auto-incrementing customer id
	// starting at 0)
	// get_lowest_k_by_total_revenue(k: int, min_total_revenue: int) -> Set<int>

	// Total revenue = initial revenue + initial revenue of directly referred
	// customers

	// Example:
	// insert(10) -> 0 total = 10 + 20 = 30
	// insert(20, 0) -> 1 total = 20 + 40 = 60
	// insert(40, 1) -> 2 total = 40
	// get_lowest_k_by_total_revenue(1, 35) -> Set(2)
	// get_lowest_k_by_total_revenue(2, 35) -> Set(1, 2)

	// get_nested_revenue(id: int, max_nesting: int) -> int
	// insert(10) -> 0
	// insert(20, 0) -> 1
	// insert(40, 1) -> 2
	// get_nested_revenue(0, 0) -> 10
	// get_nested_revenue(0, 1) -> 30
	// get_nested_revenue(0, 2) -> 70
	// get_nested_revenue(1, 1) -> 60

	// Map<Integer, List<Integer>>
	// 0-> 10, 30, 70
	// 1-> 20, 60
	// 2-> 40

	// Map<Integer, Integer>
	// 1->0
	// 2->1

	/*
	 * To execute Java, please define "static void main" on a class named
	 * Solution.
	 *
	 * If you need more classes, simply define them inline.
	 */

	Map<Integer, Integer> map = new HashMap<>();
	SortedMap<Integer, Set<Integer>> treeMap = new TreeMap<>();
	int nextId = 0;

	int insert(int revenue) {
		map.put(nextId, revenue);
		if (!treeMap.containsKey(revenue)) {
			treeMap.put(revenue, new HashSet<>());
		}
		treeMap.get(revenue).add(nextId);
		nextId++;
		return nextId - 1;
	}

	int insert(int revenue, int referrer) {
		int prev_revenue = map.get(referrer);
		int cur_revenue = prev_revenue + revenue;
		map.put(referrer, cur_revenue);
		treeMap.get(prev_revenue).remove(referrer);
		if (!treeMap.containsKey(cur_revenue)) {
			treeMap.put(cur_revenue, new HashSet<>());
		}
		treeMap.get(cur_revenue).add(referrer);

		return insert(revenue);
	}

	Set<Integer> getLowestKByTotalRevenue(int k, int min_total_revenue) {
		Set<Integer> res = new HashSet<>();
		SortedMap<Integer, Set<Integer>> m = treeMap.tailMap(min_total_revenue);
		int count = 0;
		for (Map.Entry<Integer, Set<Integer>> e : m.entrySet()) {
			for (int id : e.getValue()) {
				res.add(id);
				count++;
				if (count == k)
					return res;
			}
		}
		return res;

	}

	public static void main(String[] args) {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("Hello, World!");
		strings.add("Welcome to CoderPad.");
		strings.add("This pad is running Java " + Runtime.version().feature());

		for (String string : strings) {
			System.out.println(string);
		}
	}
}
