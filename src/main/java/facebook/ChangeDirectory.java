package facebook;

import java.util.ArrayDeque;

public class ChangeDirectory {

	public static void main(String[] args) {

		ChangeDirectory s = new ChangeDirectory();

		String current1 = "/";
		String change1 = "/facebook ";
		System.out.println(s.simplifyPath(current1, change1)); // ans: /facebook

		String current2 = "/facebook/anin";
		String change2 = "../abc/def";
		System.out.println(s.simplifyPath(current2, change2)); // ans:
																// /facebook/abc/def

		String current3 = "/facebook/instagram";
		String change3 = "../../../../.";
		System.out.println(s.simplifyPath(current3, change3)); // ans: /

		String current4 = "/f";
		String change4 = "/facebook ";
		System.out.println(s.simplifyPath(current4, change4)); // ans: /facebook

	}

	// Time: O(N + M) (N = current Length, M = change Length)
	// Space: O(N + M)
	public String simplifyPath(String current, String change) {
		if (change == null || change.trim().isEmpty()) {
			return current;
		}

		ArrayDeque<String> stack = new ArrayDeque<>();
		// O(N)
		String[] currentComponents = current.split("/");
		// O(Directory Size) <= O(N)
		// Assuming directory is normal
		for (String directory : currentComponents) {
			if (!directory.isEmpty()) {
				stack.push(directory);
			}
		}
		String[] changeComponents = change.split("/");

		// O(Directory Size) <= O(N)
		for (String directory : changeComponents) {
			// current directory . or empty directory
			if (directory.equals(".")) {
				continue;
			} else if (directory.equals("..")) {
				if (!stack.isEmpty()) {
					stack.pop();
				}
			} else if (directory.isEmpty()) {
				stack.clear();
			} else {
				stack.push(directory);
			}
		}

		// O(Directory Size) <= O(N)
		StringBuilder path = new StringBuilder();
		while (!stack.isEmpty()) {
			path.append("/");
			path.append(stack.pollLast());
		}
		return path.length() > 0 ? path.toString() : "/";
	}

}
