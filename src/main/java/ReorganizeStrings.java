import java.util.ArrayList;
import java.util.List;

public class ReorganizeStrings {
	public List<String> reorg(List<String> strs) {

		List<String> result = new ArrayList<>();
		List<List<String>> lists = new ArrayList<>();
		for (String str : strs) {
			String[] parts = str.split(" ");
			if (lists.isEmpty()) {
				for (int i = 0; i < parts.length; i++) {
					lists.add(new ArrayList<String>());
				}
			}
			for (int i = 0; i < parts.length; i++) {
				lists.get(i).add(parts[i]);
			}
		}
		dfs(0, "", lists, result, lists.get(0).size());
		return result;
	}

	private void dfs(int currentIdx, String current, List<List<String>> lists,
			List<String> result, int total) {
		if (currentIdx == total) {
			result.add(current.trim());
		} else {
			List<String> candidates = lists.get(currentIdx);
			for (String s : candidates) {
				dfs(currentIdx + 1, current + s + " ", lists, result, total);
			}
		}
	}
}
