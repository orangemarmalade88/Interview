package real.twosigma.stringchain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*

 You are given a library with n words (w[0], w[1], ..., w[n-1]). You choose a word from it, and in each step, remove one letter 
 from this word only if doing so yields a another word in the library. What is the longest possible chain of these removal steps?

 Constraints:
 - 1 <= n <= 50000
 - 1 <= the length of each string in w <= 50
 - Each String composed of lowercase ascii letter only

 Input format:
 Complete the function "longest_chain" which contains an array of strings "w" as its argument

 Output format:
 Return a single integer that represents the length of the longest chain of character removals possible

 Sample Input:
 a, b, ba, bca, bda, bdca

 Sample Output:
 4

 */

public class StringChain {
	public int longest_chain(String[] w) {

		// Sort the array from longest string to shortest
		// because longest string are more likely to give longer chains
		Comparator<String> stringLengthComparator = new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o2.length() - o1.length();
			}
		};
		Arrays.sort(w, stringLengthComparator);

		// Add the words to a dict
		Set<String> dict = new HashSet<>();
		for (String s : w) {
			dict.add(s);
		}

		// Use a map to store results for each string to avoid
		// duplicate work
		Map<String, Integer> map = new HashMap<>();
		int max_length = 0;
		for (String s : w) {
			// no need to process strings with length <= current_max
			// because they won't beat the current longest chain
			if (s.length() <= max_length)
				continue;
			int l = dfs(s, map, dict);
			max_length = Math.max(l, max_length);
			map.put(s, l);
		}
		return max_length;
	}

	private int dfs(String s, Map<String, Integer> map, Set<String> dict) {
		if (s.isEmpty()) {
			return 0;
		}

		if (map.containsKey(s))
			return map.get(s);

		int max_length = 0;
		for (int i = 0; i < s.length(); i++) {
			StringBuilder sb = new StringBuilder(s);
			sb.deleteCharAt(i);
			String candidate = sb.toString();
			if (dict.contains(candidate)) {
				int l = dfs(candidate, map, dict);
				max_length = Math.max(l, max_length);
			}
		}

		// the length of current string will be
		// 1 + maximum length of all possible cuts
		// or just 1 if no such cuts
		int result = max_length + 1;
		map.put(s, result);
		return result;
	}
}
