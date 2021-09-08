package databricks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/*
 * To execute Java, please define "static void main" on a class named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {

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
