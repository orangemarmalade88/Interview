package smartnews;

import java.util.ArrayList;

public class MinimumBags {
	/*
	 * Your previous Plain Text content is preserved below:
	 *
	 * Problem:
	 *
	 * Ball: 1->2->3->4->5->6->7->8->9->10 Bag Number: 4 (Red, Blue, Yellow,
	 * Green)
	 *
	 * Each bag is the same size Split the linked list into continuous subs The
	 * bag size must greater than or equal the sum of ball size Find the minimal
	 * bag size then print the linked list 0 < bag size < 1000 0 < bag number <
	 * 1000
	 *
	 * Input: Output: min bag size
	 *
	 *
	 * Example: 1->2->3->4->5->6->7->8->9->10 Red: 1->2->3->4->5 = 15 Blue: 6->7
	 * = 13 Yellow: 8->9 = 17 Green: 10 = 10
	 *
	 *
	 * min bag size : 17
	 *
	 */
	public static void main(String[] args) {
		int[] balls = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int min = minimumBagSize(balls, 4);

		ArrayList<String> strings = new ArrayList<String>();
		strings.add("Hello, World!");
		strings.add("Welcome to CoderPad.");
		strings.add("This pad is running Java " + Runtime.version().feature());
		strings.add("Minimum bag size is " + min);

		for (String string : strings) {
			System.out.println(string);
		}
	}

	static int global_min = Integer.MAX_VALUE;

	public static int minimumBagSize(int[] balls, int bags) {
		int sum = 0;
		for (Integer i : balls) {
			sum += i;
		}

		// binary search
		int left = sum / bags;
		int right = sum;

		binarySearch(balls, left, right, bags);

		return global_min;
	}

	public static void binarySearch(int[] balls, int l, int r, int bag_count) {
		if (r > l) {
			int m = l + (r - l) / 2;

			if (fit(balls, m, bag_count)) {
				binarySearch(balls, l, m, bag_count);
			} else {
				binarySearch(balls, m + 1, r, bag_count);
			}
		}
	}

	public static boolean fit(int[] balls, int bag_size, int bag_count) {
		int current_sum = 0;
		int current_bags = 1;
		for (int i = 0; i < balls.length; i++) {
			if (current_sum + balls[i] > bag_size) {
				current_sum = 0;
				if (balls[i] > bag_size)
					return false;
				else {
					current_bags++;
					i--;
				}
			} else {
				current_sum += balls[i];
			}
		}
		if (current_bags <= bag_count) {
			global_min = Math.min(bag_size, global_min);
			return true;
		} else {
			return false;
		}
	}

}
