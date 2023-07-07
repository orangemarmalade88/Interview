package roblox;

import java.util.Arrays;
// For game recommendation, we will collect players information as parameters, such as geolocation, age, popular games among friends, etc. Then calculate a "weight" for each game, it means the possibility the game is being recommended. And we also want players to be able to see different games every time they visit the page, so we want to add some randomization in the algorithm.
// Think of your solution as a simplified game recommendation prototype, you're given a dictionary, where the keys are strings and values are positive integers representing the weight of the key (). Write a function that returns a string randomly based on its weight.
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

// Example:
// Input: dictionary = {'apple': 2, 'baby': 3, 'cat': 5}
// Output: "cat"

// {"2:apple","5:baby¡±, "10£º7"}
// 7 -> "cat"

// Input: dictionary = {'apple': 2, 'baby': 3, 'cat': 5}
// Output: "baby"

// Main class should be named 'Solution' and should not be public.
class RandomPickWithWeight {
	static TreeMap<Integer, String> m = new TreeMap<>();
	static Random r = new Random();
	static int total_weight = 0;
	static int[] sum;
	static String[] games;

	public static String outputGame() {
		int random = r.nextInt(total_weight) + 1;
		// [1,10]
		// 2 -> 1,2
		// 3 -> 3,4,5
		// 5 -> 6,7,8,9,10
		return m.ceilingEntry(random).getValue();
	}

	public static String outputGame2() {
		int random = r.nextInt(total_weight) + 1;
		// [1,10]
		// [2, 5, 10]
		// 2 -> 1,2
		// 3 -> 3,4,5
		// 5 -> 6,7,8,9,10
		int index = Arrays.binarySearch(sum, random);
		if (index < 0)
			index = -(index + 1);
		return games[index];
	}

	public static void generateGame(Map<String, Integer> map) {
		int weight = 0;
		for (Map.Entry<String, Integer> e : map.entrySet()) {
			weight += e.getValue();
			m.put(weight, e.getKey());
		}
		total_weight = weight;
	}

	public static void generateGame2(Map<String, Integer> map) {
		sum = new int[map.size()];
		games = new String[map.size()];
		int weight = 0;
		int i = 0;
		for (Map.Entry<String, Integer> e : map.entrySet()) {
			weight += e.getValue();
			sum[i] = weight;
			games[i] = e.getKey();
			i++;
		}
		total_weight = weight;
	}

	public static void main(String[] args) {
		Map<String, Integer> m = new HashMap<>();
		m.put("apple", 2);
		m.put("baby", 3);
		m.put("cat", 5);
		RandomPickWithWeight.generateGame2(m);
		for (int i = 0; i < 10; i++)
			System.out.println(RandomPickWithWeight.outputGame2());
	}
}
