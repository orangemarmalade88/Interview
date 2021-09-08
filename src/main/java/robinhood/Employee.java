package robinhood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee {
	static Map<String, List<String>> unusual(String[][] badgeTimes) {
		Map<String, List<Integer>> map = new HashMap<>();
		for (String[] s : badgeTimes) {
			if (!map.containsKey(s[0]))
				map.put(s[0], new ArrayList<Integer>());
			int t = Integer.valueOf(s[1]);
			int minutes = t / 100 * 60 + t % 100;
			map.get(s[0]).add(minutes);
		}

		Map<String, List<String>> res = new HashMap<>();

		for (String s : map.keySet()) {
			List<Integer> times = map.get(s);
			Collections.sort(times);
			int slow = 0;
			if (times.size() < 3)
				continue;
			int fast = 2;

			while (slow < times.size() && fast < times.size()) {
				int early = times.get(slow);
				int late = times.get(fast);
				if (late - early <= 60) {
					List<String> l = new ArrayList<>();
					while (slow < times.size()
							&& times.get(slow) - early <= 60) {
						l.add(convert(times.get(slow)));
						slow++;
					}
					res.put(s, l);
					// bug 2 : break to return early
					break;
				}
				slow++;
				fast++;
			}

		}
		return res;

	}

	// bug 1 : append 0 for minutes < 10
	static public String convert(int t) {
		StringBuilder sb = new StringBuilder();
		int hours = t / 60;
		if (hours > 0) {
			sb.append(hours);
		}
		int minutes = t % 60;

		if (minutes < 10)
			sb.append(0);
		sb.append(t % 60);
		return sb.toString();
	}

	public static void main(String[] argv) {

		String[][] badgeTimes = new String[][] { { "Paul", "1355" },
				{ "Jennifer", "1910" }, { "John", "835" }, { "John", "830" },
				{ "Paul", "1315" }, { "John", "1615" }, { "John", "1640" },
				{ "Paul", "1405" }, { "John", "855" }, { "John", "930" },
				{ "John", "915" }, { "John", "730" }, { "John", "940" },
				{ "Jennifer", "1335" }, { "Jennifer", "730" },
				{ "John", "1630" }, { "Jennifer", "5" }, };
		System.out.println(unusual(badgeTimes));
	}
}

/*
 * We are working on a security system for a badged-access room in our company's
 * building.
 *
 * We want to find employees who badged into our secured room unusually often.
 * We have an unordered list of names and entry times over a single day. Access
 * times are given as numbers up to four digits in length using 24-hour time,
 * such as "800" or "2250".
 *
 * Write a function that finds anyone who badged into the room three or more
 * times in a one-hour period. Your function should return each of the employees
 * who fit that criteria, plus the times that they badged in during the one-hour
 * period. If there are multiple one-hour periods where this was true for an
 * employee, just return the earliest one for that employee.
 *
 * badge_times = [ ["Paul", "1355"], ["Jennifer", "1910"], ["John", "835"],
 * ["John", "830"], ["Paul", "1315"], ["John", "1615"], ["John", "1640"],
 * ["Paul", "1405"], ["John", "855"], ["John", "930"], ["John", "915"], ["John",
 * "730"], ["John", "940"], ["Jennifer", "1335"], ["Jennifer", "730"], ["John",
 * "1630"], ["Jennifer", "5"] ]
 *
 * Expected output (in any order) John: 830 835 855 915 930 Paul: 1315 1355 1405
 *
 * n: length of the badge records array
 *
 */
