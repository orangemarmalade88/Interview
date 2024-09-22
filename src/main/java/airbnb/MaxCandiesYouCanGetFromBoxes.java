package airbnb;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class MaxCandiesYouCanGetFromBoxes {
	public int maxCandies(int[] status, int[] candies, int[][] keys,
			int[][] containedBoxes, int[] initialBoxes) {
		Queue<Integer> queue = new LinkedList<>(); // For open and
													// ready-to-process boxes
		Set<Integer> availableKeys = new HashSet<>(); // Keys we've collected
		Set<Integer> availableBoxes = new HashSet<>(); // Boxes we've picked but
														// can't open yet
		boolean[] visited = new boolean[status.length]; // Track which boxes
														// have been fully
														// processed

		// Add all initial boxes to the available boxes
		for (int box : initialBoxes) {
			if (status[box] == 1) { // Open box
				queue.offer(box);
			} else {
				availableBoxes.add(box); // Locked box
			}
		}

		int totalCandies = 0;

		// Process all boxes in the queue
		while (!queue.isEmpty()) {
			int currentBox = queue.poll();

			// Collect candies from the current box
			totalCandies += candies[currentBox];
			visited[currentBox] = true;

			// Collect all keys from the current box
			for (int key : keys[currentBox]) {
				availableKeys.add(key);

				// If we have a box that was previously locked, now we can try
				// to open it
				if (availableBoxes.contains(key)) {
					queue.offer(key); // Now we can process the box
					availableBoxes.remove(key);
				}
			}

			// Add all contained boxes to the processing queue or available
			// boxes
			for (int box : containedBoxes[currentBox]) {
				if (status[box] == 1 || availableKeys.contains(box)) {
					queue.offer(box); // Box is open or we have the key, so we
										// can process it
				} else {
					availableBoxes.add(box); // Box is locked but we don't have
												// the key yet
				}
			}
		}

		return totalCandies;
	}

	public static void main(String[] args) {
		MaxCandiesYouCanGetFromBoxes solution = new MaxCandiesYouCanGetFromBoxes();

		// Test Case 1
		int[] status1 = { 1, 0, 1, 0 };
		int[] candies1 = { 7, 5, 4, 100 };
		int[][] keys1 = { {}, {}, { 1 }, {} };
		int[][] containedBoxes1 = { { 1, 2 }, { 3 }, {}, {} };
		int[] initialBoxes1 = { 0 };
		System.out.println(solution.maxCandies(status1, candies1, keys1,
				containedBoxes1, initialBoxes1)); // Output: 16

		// Test Case 2
		int[] status2 = { 1, 0, 0, 0, 0, 1 };
		int[] candies2 = { 1, 1, 1, 1, 1, 1 };
		int[][] keys2 = { { 1, 2, 3, 4, 5 }, {}, {}, {}, {}, {} };
		int[][] containedBoxes2 = { { 1, 2, 3, 4, 5 }, {}, {}, {}, {}, {} };
		int[] initialBoxes2 = { 0 };
		System.out.println(solution.maxCandies(status2, candies2, keys2,
				containedBoxes2, initialBoxes2)); // Output: 6

		// Test Case 3: Deadlock scenario
		int[] status3 = { 0, 0, 0, 0 };
		int[] candies3 = { 1, 2, 3, 4 };
		int[][] keys3 = { {}, {}, {}, {} };
		int[][] containedBoxes3 = { {}, {}, {}, {} };
		int[] initialBoxes3 = { 0 };
		System.out.println(solution.maxCandies(status3, candies3, keys3,
				containedBoxes3, initialBoxes3)); // Output: 0
	}
}
