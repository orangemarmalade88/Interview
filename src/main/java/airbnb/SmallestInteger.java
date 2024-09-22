package airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SmallestInteger {
	// Part 1: Get the smallest integer from the array of digits
	public static String getSmallestInteger(List<Integer> digits) {
		// Filter out zeros and sort the remaining digits
		List<Integer> nonZeroDigits = new ArrayList<>();
		for (int digit : digits) {
			if (digit != 0) {
				nonZeroDigits.add(digit);
			}
		}
		Collections.sort(nonZeroDigits); // Sort to get the smallest order

		// Convert list to string
		StringBuilder result = new StringBuilder();
		for (int digit : nonZeroDigits) {
			result.append(digit);
		}
		return result.toString();
	}

	public static void main(String[] args) {
		List<Integer> digits1 = Arrays.asList(1, 3, 3, 4, 2);
		System.out.println("Smallest Integer: " + getSmallestInteger(digits1)); // Output:
																				// "12334"

		List<Integer> digits2 = Arrays.asList(0, 1, 2);
		System.out.println("Smallest Integer: " + getSmallestInteger(digits2)); // Output:
																				// "12"
	}

}
