package airbnb;

import java.util.Arrays;

public class MinNumberFromDigits {

	public String smallestNumber(int[] digits, int lowerBound) {
		// Convert lowerBound to a string for easy comparison
		String lowerBoundStr = String.valueOf(lowerBound);
		int n = digits.length;

		// Sort the digits to get the smallest possible number
		Arrays.sort(digits);
		String smallestNumber = arrayToString(digits);

		// If the smallest possible number is >= lower bound, return it
		if (smallestNumber.compareTo(lowerBoundStr) >= 0) {
			return smallestNumber;
		}

		// Otherwise, we need to find the next number larger than the lower
		// bound
		return findNextGreater(digits, lowerBoundStr);
	}

	// Helper function to convert an array of digits to a string
	private String arrayToString(int[] digits) {
		StringBuilder sb = new StringBuilder();
		for (int digit : digits) {
			sb.append(digit);
		}
		return sb.toString();
	}

	// Function to find the smallest permutation that is larger than the lower
	// bound
	private String findNextGreater(int[] digits, String lowerBoundStr) {
		int n = digits.length;
		int[] originalDigits = digits.clone();

		// We iterate from the end to find the next permutation larger than the
		// lower bound
		for (int i = n - 1; i > 0; i--) {
			if (digits[i] > digits[i - 1]) {
				// Swap digits[i-1] with the smallest digit greater than
				// digits[i-1] in the right subarray
				int j = n - 1;
				while (digits[j] <= digits[i - 1]) {
					j--;
				}
				swap(digits, i - 1, j);

				// Sort the digits after position i-1 to get the smallest
				// possible number
				Arrays.sort(digits, i, n);

				String nextGreater = arrayToString(digits);
				// Check if the new number is greater than or equal to the lower
				// bound
				if (nextGreater.compareTo(lowerBoundStr) >= 0) {
					return nextGreater;
				} else {
					// Reset the digits and try again if the current attempt is
					// not valid
					digits = originalDigits.clone();
				}
			}
		}

		return null;
	}

	// Helper function to swap elements in an array
	private void swap(int[] digits, int i, int j) {
		int temp = digits[i];
		digits[i] = digits[j];
		digits[j] = temp;
	}

	public static void main(String[] args) {
		MinNumberFromDigits solver = new MinNumberFromDigits();
		int[] digits = { 7, 1, 1, 8 };
		int lowerBound = 719;
		String result = solver.smallestNumber(digits, lowerBound);
		System.out.println(result); // Output should be "781"
	}
}
