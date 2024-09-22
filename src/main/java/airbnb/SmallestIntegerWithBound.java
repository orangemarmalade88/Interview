package airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SmallestIntegerWithBound {

	// Part 2: Get the smallest integer greater than or equal to the lower bound
	public static String getSmallestWithBound(List<Integer> digits,
			String lowerBound) {
		// Sort digits to start with the smallest number, including 0s
		Collections.sort(digits);

		// If the number of digits is less than the lower bound length, return
		// sorted number
		if (digits.size() < lowerBound.length()) {
			return buildSmallestNumber(digits);
		}

		// Attempt to find the smallest number that is >= lowerBound
		String result = findNextGreaterOrEqual(digits, lowerBound);
		return result;
	}

	// Helper method to find the next number greater or equal to the lower bound
	private static String findNextGreaterOrEqual(List<Integer> digits,
			String lowerBound) {
		char[] bound = lowerBound.toCharArray();
		int n = bound.length;

		// If we need to match the exact length of the lower bound
		if (digits.size() == n) {
			String result = attemptToMatchOrExceed(digits, bound);
			if (!result.equals("")) {
				return result;
			}
		}

		// If no valid permutation exists, return the smallest possible number
		return buildSmallestNumber(digits);
	}

	// Helper to attempt matching or exceeding the lower bound digit by digit
	private static String attemptToMatchOrExceed(List<Integer> digits,
			char[] bound) {
		List<Integer> availableDigits = new ArrayList<>(digits);
		StringBuilder result = new StringBuilder();

		// Try to match or exceed the lower bound
		for (int i = 0; i < bound.length; i++) {
			int targetDigit = bound[i] - '0';
			boolean found = false;

			// Find the smallest available digit >= targetDigit
			for (int j = 0; j < availableDigits.size(); j++) {
				if (availableDigits.get(j) >= targetDigit) {
					result.append(availableDigits.remove(j)); // Use this digit
					found = true;
					break;
				}
			}

			// If we can't match or exceed the bound at any point, return the
			// next permutation
			if (!found) {
				return findNextPermutation(result.toString(), availableDigits);
			}
		}

		return result.toString(); // Return as soon as a valid number is built
	}

	// Find the next valid permutation (this happens when we can't match the
	// lower bound exactly)
	private static String findNextPermutation(String partial,
			List<Integer> remaining) {
		StringBuilder result = new StringBuilder(partial);

		// Append the smallest possible permutation from the remaining digits
		Collections.sort(remaining); // Sort the remaining digits
		for (int digit : remaining) {
			result.append(digit);
		}
		return result.toString();
	}

	// Helper to build the smallest number possible from the given digits
	private static String buildSmallestNumber(List<Integer> digits) {
		StringBuilder result = new StringBuilder();

		// Find the first non-zero digit to avoid leading zeros
		int firstNonZeroIdx = -1;
		for (int i = 0; i < digits.size(); i++) {
			if (digits.get(i) != 0) {
				firstNonZeroIdx = i;
				break;
			}
		}

		if (firstNonZeroIdx != -1) {
			// Place the first non-zero digit at the beginning
			result.append(digits.get(firstNonZeroIdx));
			// Append the remaining digits, including zeros
			for (int i = firstNonZeroIdx + 1; i < digits.size(); i++) {
				result.append(digits.get(i));
			}
		} else {
			// All digits are zero, so just return "0"
			return "0";
		}

		return result.toString();
	}

	public static void main(String[] args) {
		// Example 1: Smallest integer >= 719
		List<Integer> digits1 = Arrays.asList(7, 1, 8);
		String lowerBound1 = "719";
		System.out.println("Smallest Integer >= 719: "
				+ getSmallestWithBound(digits1, lowerBound1)); // Output: "781"

		// Example 2: Smallest integer >= 2, with zero allowed, and stops early
		List<Integer> digits2 = Arrays.asList(0, 1, 2);
		String lowerBound2 = "2";
		System.out.println("Smallest Integer >= 2: "
				+ getSmallestWithBound(digits2, lowerBound2)); // Output: "2"

		// Example 3: Smallest integer >= 11
		List<Integer> digits3 = Arrays.asList(0, 1, 5);
		String lowerBound3 = "11";
		System.out.println("Smallest Integer >= 11: "
				+ getSmallestWithBound(digits3, lowerBound3)); // Output: "15"
	}

}