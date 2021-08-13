package microsoft;

public class Main {

	// dp[i][j] = max(sum(i ,j) - dp[i+1][j], sum(i,j) - dp[i][j-1])

	// 3 4 1 5

	int maxCoins(int[] coins) {
		int n = coins.length;
		int[][] dp = new int[n][n];

		int[][] sum = new int[n][n];
		for (int i = 0; i < n; i++) {
			int s = 0;
			for (int j = i; j < n; j++) {
				s += coins[j];
				sum[i][j] = s;
			}
		}

		for (int i = 0; i < n; i++) {
			dp[i][i] = coins[i];
		}

		for (int l = 1; l < n; l++) {
			for (int i = 0; i < n - 1 - l; i++) {
				dp[i][i + l] = Math.max(sum[i][i + l] - dp[i + 1][i + l],
						sum[i][i + l] - dp[i][i + l - 1]);
			}

		}

		return dp[0][n - 1];

	}
}
