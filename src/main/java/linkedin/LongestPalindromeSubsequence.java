package linkedin;

/*
 * Find the length of the longest palindrome subsequence of the string
 */
public class LongestPalindromeSubsequence {
	int longestPalindomeSubsequence(String str) {
		int n = str.length();
		char[] cArray = str.toCharArray();
		int dp[][] = new int[n][n];

		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}

		for (int i = 0; i + 1 < n; i++) {
			dp[i][i + 1] = (cArray[i] == cArray[i + 1]) ? 2 : 1;
		}

		for (int step = 2; step < n; step++) {
			for (int i = 0; i + step < n; i++) {
				dp[i][i + step] = (cArray[i] == cArray[i + step]) ? dp[i + 1][i
						+ step - 1] + 2 : Math.max(dp[i + 1][i + step], dp[i][i
						+ step - 1]);
			}
		}

		return dp[0][n - 1];
	}

	public static void main(String[] args) {
		LongestPalindromeSubsequence lps = new LongestPalindromeSubsequence();
		System.out.println(lps.longestPalindomeSubsequence("GEEKS FOR GEEKS"));
	}
}
