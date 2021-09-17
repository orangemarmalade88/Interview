package flexport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomString {
	// Input: the sentence is not is a sentence is tree open it the door not
	// open it tree yes
	// N: 5

	// Output: a sentence is not open
	// Output: a sentence is tree open

	// Input: the sentence is not is a sentence is tree open it the door not
	// open it tree yes
	// N: 5
	// K: 2

	// Output: a sentence is not is

	/*
	 * Click `Run` to execute the snippet below!
	 */

	/*
	 * To execute Java, please define "static void main" on a class named
	 * Solution.
	 *
	 * If you need more classes, simply define them inline.
	 */

	Random r = new Random();

	private String generateKey(String[] parts, int i, int k) {
		StringBuilder key = new StringBuilder();
		for (int j = 0; j < k; j++) {
			int index = i + j;
			if (index >= parts.length)
				index -= parts.length;
			key.append(parts[index]).append(" ");
		}
		key.deleteCharAt(key.length() - 1);
		return key.toString();

	}

	private void putIntoMap(String[] parts, Map<String, List<String>> map,
			int i, int k) {
		String s = generateKey(parts, i, k);
		if (!map.containsKey(s)) {
			map.put(s, new ArrayList<>());
		}
		int j = i + k;
		if (j >= parts.length)
			j -= parts.length;
		map.get(s).add(parts[j]);
	}

	public String random(String input, int n, int k) {
		StringBuilder sb = new StringBuilder();
		String[] parts = input.split(" ");
		Map<String, List<String>> map = new HashMap<>();
		for (int i = 0; i < parts.length; i++) {
			putIntoMap(parts, map, i, k);
		}

		String start = generateKey(parts, r.nextInt(parts.length), k);
		sb.append(start).append(" ");
		while (n - k > 0) {
			List<String> next_strings = map.get(start);
			String next = next_strings.get(r.nextInt(next_strings.size()));
			start = start.substring(start.indexOf(" ") + 1);
			start = start + " " + next;
			sb.append(next).append(" ");
			n--;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		RandomString s = new RandomString();
		// ArrayList<String> strings = new ArrayList<String>();
		// strings.add("Hello, World!");
		// strings.add("Welcome to CoderPad.");
		// strings.add("This pad is running Java " +
		// Runtime.version().feature());

		String input = "the sentence is not is the sentence is tree open it the sentence tree yes";
		// String input = "the";
		for (int i = 0; i < 10; i++)
			System.out.println(s.random(input, 5, 2));

		// for (String string : strings) {
		// System.out.println(string);
		// }
	}

}
