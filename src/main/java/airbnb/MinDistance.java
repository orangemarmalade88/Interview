package airbnb;

public class MinDistance {
	public int minDistance(String word1, String word2) {
		int m = word1.length();
		int n = word2.length();

		// dp[i][j] will hold the edit distance between the first i characters
		// of word1 and the first j characters of word2.
		int[][] dp = new int[m + 1][n + 1];

		// Initialize the dp array
		for (int i = 0; i <= m; i++) {
			dp[i][0] = i; // i deletions needed to convert word1 to empty string
		}

		for (int j = 0; j <= n; j++) {
			dp[0][j] = j; // j insertions needed to convert empty string to
							// word2
		}

		// Compute the edit distance
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1]; // No operation needed
				} else {
					dp[i][j] = Math.min(dp[i - 1][j - 1], // Replace
							Math.min(dp[i - 1][j], // Delete
									dp[i][j - 1])); // Insert
					dp[i][j] += 1;
				}
			}
		}

		return dp[m][n];
	}

}
