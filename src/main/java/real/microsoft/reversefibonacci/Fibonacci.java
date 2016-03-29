package real.microsoft.reversefibonacci;

/*

 Implement a method that
 generates a reverse Fibonacci series of numbers when the first 2 numbers are
 provided.

 Example: 80,50,30,20,10,10,0

 */

public class Fibonacci {
	// generate a series of n numbers for a reverse Fibonacci series with the
	// first 2 numbers
	// O(n) time, O(n) space
	int[] reverseBibonacci(int first, int second, int n) {
		// error checking
		if (n < 2)
			return null;

		int[] result = new int[n];
		result[0] = first;
		result[1] = second;
		for (int i = 2; i < n; i++) {
			result[i] = result[i - 2] - result[i - 1];
			// Dynamic Programming
		}
		return result;
	}
}
