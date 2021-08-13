package newsbreak;

import java.util.HashSet;
import java.util.Set;

public class NQueens {

	// 3 1 4 5
	private final Set<Integer> occupiedCols = new HashSet<Integer>();
	private final Set<Integer> occupiedDiag1s = new HashSet<Integer>();
	private final Set<Integer> occupiedDiag2s = new HashSet<Integer>();

	public int totalNQueens(int n) {
		return totalNQueensHelper(0, 0, n);
	}

	private int totalNQueensHelper(int row, int count, int n) {
		for (int col = 0; col < n; col++) {
			if (occupiedCols.contains(col))
				continue;
			int diag1 = row - col;
			if (occupiedDiag1s.contains(diag1))
				continue;
			int diag2 = row + col;
			if (occupiedDiag2s.contains(diag2))
				continue;
			// we can now place a queen here
			if (row == n - 1)
				count++;
			else {
				occupiedCols.add(col);
				occupiedDiag1s.add(diag1);
				occupiedDiag2s.add(diag2);
				count = totalNQueensHelper(row + 1, count, n);
				// recover
				occupiedCols.remove(col);
				occupiedDiag1s.remove(diag1);
				occupiedDiag2s.remove(diag2);
			}
		}

		return count;
	}

	int firstMIssingPositive(int[] nums, int n) {
		for (int i = 0; i < nums.length; i++) {
			while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i])
				swap(nums, i, nums[i] - 1);
		}

		for (int i = 0; i < n; i++) {
			if (nums[i] != i + 1)
				return i + 1;
		}

		return n + 1;
	}

	private void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	int ways = 0;
	int[][] dirs = { { 1, 1 }, { 1, -1 }, { -1, -1 }, { -1, 1 } };

	int eightQueens() {
		boolean[][] board = new boolean[8][8];
		dfs(board, 0);
		return ways;
	}

	void dfs(boolean[][] board, int x) {
		if (x == 8) {
			ways++;
			return;
		}
		for (int j = 0; j < 8; j++) {
			if (canPlace(board, x, j)) {
				board[x][j] = true;
				dfs(board, x + 1);
				board[x][j] = false;
			}
		}

	}

	boolean canPlace(boolean[][] board, int i, int j) {
		for (int x = 0; x < 8; x++) {
			if (board[x][j])
				return false;
		}
		for (int y = 0; y < 8; y++) {
			if (board[i][y])
				return false;
		}
		for (int[] d : dirs) {
			for (int steps = 1; steps < 8; steps++) {
				int x = i + steps * d[0];
				int y = j + steps * d[1];
				if (x >= 0 && x < 8 && y >= 0 && y < 8 && board[x][y])
					return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {
		NQueens c = new NQueens();
		for (int i = 1; i <= 20; i++)
			System.out.println(c.totalNQueens(i));
	}
}
