package airbnb;

public class TerrainPrinter {
	public static void printTerrain(int[] heights) {
		int max = 0;
		for (int i : heights) {
			max = Math.max(max, i);
		}

		for (int i = max; i >= 1; i--) {
			StringBuilder sb = new StringBuilder();
			for (int j : heights) {
				if (j >= i) {
					sb.append("+");
				} else {
					sb.append(" ");
				}
			}
			System.out.println(sb.toString());
		}
	}

	public static void dumpWater(int[] heights, int waterAmount, int column) {
		int n = heights.length;
		int[] water = new int[n];

		for (int i = 0; i < waterAmount; i++) {
			int idx = column;
			// Move left
			idx = findLowestPosition(heights, water, idx, -1);
			// If couldn't move left, try moving right
			if (idx == column) {
				idx = findLowestPosition(heights, water, idx, 1);
			}
			// Place water at the final position
			water[idx]++;
		}

		printTerrainWithWater(heights, water);
	}

	private static int findLowestPosition(int[] heights, int[] water, int start,
			int direction) {
		int n = heights.length;
		int idx = start;
		int minIdx = start;
		int minHeight = heights[start] + water[start];

		while (true) {
			int nextIdx = idx + direction;
			if (nextIdx < 0 || nextIdx >= n) {
				break;
			}
			int nextHeight = heights[nextIdx] + water[nextIdx];
			int currentHeight = heights[idx] + water[idx];
			if (nextHeight < minHeight) {
				minHeight = nextHeight;
				minIdx = nextIdx;
			} else if (nextHeight > currentHeight) {
				break;
			}
			idx = nextIdx;
		}
		return minIdx;
	}

	private static void printTerrainWithWater(int[] heights, int[] water) {
		int maxHeight = 0;
		for (int i = 0; i < heights.length; i++) {
			maxHeight = Math.max(maxHeight, heights[i] + water[i]);
		}

		for (int level = maxHeight; level >= 1; level--) {
			StringBuilder row = new StringBuilder();
			for (int i = 0; i < heights.length; i++) {
				if (heights[i] >= level) {
					row.append("+");
				} else if (heights[i] + water[i] >= level) {
					row.append("W");
				} else {
					row.append(" ");
				}
			}
			System.out.println(row.toString());
		}
		// Base layer
		System.out.println(
				new String(new char[heights.length]).replace("\0", "+"));
	}

	public static void main(String[] args) {
		int[] terrain = { 5, 4, 3, 2, 1, 3, 4, 0, 3, 4 };
		int waterAmount = 8;
		int column = 1;
		dumpWater(terrain, waterAmount, column);
	}

}
