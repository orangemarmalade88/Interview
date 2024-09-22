
public class WordSearch {
	static String optiver = "OPTIVER";
	static int m;
	static int n;

	static int countOptiverOccurrences(String[] characterGrid) {
		int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		m = characterGrid.length;
		n = characterGrid[0].length();
		int count = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				for (int[] dir : dirs) {
					if (isValid(characterGrid, i, j, dir))
						count++;
				}
			}
		}
		return count;
	}

	static boolean isValid(String[] characterGrid, int i, int j, int[] dir) {
		for (int k = 0; k < optiver.length(); k++) {
			int x = i + dir[0] * k;
			int y = j + dir[1] * k;
			if (x < 0 || x >= m || y < 0 || y >= n)
				return false;
			if (characterGrid[x].charAt(y) != optiver.charAt(k))
				return false;
		}
		return true;
	}
}
