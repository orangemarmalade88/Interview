package WholeFood;

import java.io.IOException;
import java.util.List;

public class FormWords {
	/**
	 * Iterate through each line of input.
	 */
	static boolean canForm = false;

	public static boolean canForm(String word, List<String> tiles) {
		boolean[] visited = new boolean[tiles.size()];
		dfs(word, 0, tiles, visited);
		return canForm;
	}

	public static void dfs(String word, int index, List<String> tiles,
			boolean[] visited) {
		if (canForm)
			return;
		if (index == word.length()) {
			canForm = true;
			return;
		}
		for (int i = 0; i < tiles.size(); i++) {
			if (!visited[i] && word.startsWith(tiles.get(i), index)) {
				visited[i] = true;
				dfs(word, index + tiles.get(i).length(), tiles, visited);
				visited[i] = false;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String word = "foobarbaz";
		List<String> words = List.of("foob", "foo", "ba", "ba", "r", "z");
		System.out.println(canForm(word, words));
	}
}
