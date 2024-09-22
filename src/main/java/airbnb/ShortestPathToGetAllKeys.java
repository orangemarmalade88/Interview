package airbnb;

import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathToGetAllKeys {
	// Directions for moving up, down, left, right
	private static final int[][] DIRECTIONS = { { 0, 1 }, { 0, -1 }, { 1, 0 },
			{ -1, 0 } };

	// Function to find the shortest path to collect all keys and open all locks
	public static int shortestPathAllKeys(String[] grid) {
		int rows = grid.length;
		int cols = grid[0].length();

		// Variables to store starting position and the total number of keys
		int startX = 0, startY = 0, totalKeys = 0;

		// Scan the grid to find the start position and count total number of
		// keys
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				char cell = grid[i].charAt(j);
				if (cell == '@') {
					startX = i;
					startY = j;
				}
				if (cell >= 'a' && cell <= 'f') {
					totalKeys++;
				}
			}
		}

		// BFS setup: queue stores (row, col, keys collected as bitmask)
		Queue<int[]> queue = new LinkedList<>();
		boolean[][][] visited = new boolean[rows][cols][1 << totalKeys];
		queue.offer(new int[] { startX, startY, 0 });
		visited[startX][startY][0] = true;

		int steps = 0;

		// BFS traversal
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				int[] curr = queue.poll();
				int x = curr[0], y = curr[1], keys = curr[2];

				// If we've collected all keys, return the number of steps
				if (keys == (1 << totalKeys) - 1) {
					return steps;
				}

				// Explore the four possible directions
				for (int[] direction : DIRECTIONS) {
					int newX = x + direction[0];
					int newY = y + direction[1];

					if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) {
						continue; // Skip out-of-bound coordinates
					}

					char nextCell = grid[newX].charAt(newY);

					// If it's a wall, skip it
					if (nextCell == '#') {
						continue;
					}

					// If it's a key, collect it (update the bitmask for keys)
					int newKeys = keys;
					if (nextCell >= 'a' && nextCell <= 'f') {
						newKeys |= (1 << (nextCell - 'a')); // Collect key
					}

					// If it's a lock, make sure we have the corresponding key
					if (nextCell >= 'A' && nextCell <= 'F'
							&& (newKeys & (1 << (nextCell - 'A'))) == 0) {
						continue; // Skip if we don't have the key
					}

					// If we haven't visited this state (position + keys), add
					// to the queue
					if (!visited[newX][newY][newKeys]) {
						visited[newX][newY][newKeys] = true;
						queue.offer(new int[] { newX, newY, newKeys });
					}
				}
			}
			steps++; // Increment steps after each BFS level
		}

		// If we cannot collect all keys, return -1
		return -1;
	}

}
