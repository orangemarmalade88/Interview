package ponyai;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSubMatrixWithOnes {

	int maximumSubMatrixWithOnes(int[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;
		int[] dp = new int[n];
		int max = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == 0) {
					dp[j] = 0;
				} else
					dp[j] = dp[j] + 1;
			}
			max = Math.max(max, largestRectangleArea(dp));
		}
		return max;
	}

	int largestRectangleArea(int[] heights) {
		Deque<Integer> stack = new ArrayDeque<Integer>();
		int max = 0;
		for (int i = 0; i < heights.length; i++) {
			if (stack.isEmpty() || heights[i] >= heights[stack.peek()]) {
				stack.push(i);
			} else {
				while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
					int cur = stack.pop();
					int width = stack.isEmpty() ? i : i - stack.peek() - 1;
					int cur_area = heights[cur] * (width);
					max = Math.max(max, cur_area);
				}
				stack.push(i);
			}
		}
		while (!stack.isEmpty()) {
			int cur = stack.pop();
			int width = stack.isEmpty() ? heights.length
					: heights.length - stack.peek() - 1;
			int cur_area = heights[cur] * (width);
			max = Math.max(max, cur_area);
		}
		return max;
	}

	public static void main(String[] args) {
		MaximumSubMatrixWithOnes m = new MaximumSubMatrixWithOnes();
		int[][] matrix = { { 0, 0, 1, 1, 1 }, { 0, 1, 1, 0, 1 },
				{ 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 1 } };
		System.out.println(m.maximumSubMatrixWithOnes(matrix));
	}

}
