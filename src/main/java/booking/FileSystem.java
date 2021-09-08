package booking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class FileSystem {
	class File {
		boolean isFile;
		String name;
		List<File> subDirs;
	}

	List<List<File>> deduplicate(File root) {
		List<List<File>> res = new ArrayList<>();
		if (root.isFile) {
			return res;
		}
		Set<File> files = new HashSet<>();
		Map<String, List<File>> map = new HashMap<>();
		iterate(root, map);
		for (String s : map.keySet()) {
			List<File> list = map.get(s);
			if (list.size() > 1)
				res.add(list);
			for (File f : list) {
				files.add(f);
			}
		}
		for (List<File> list : res) {
			for (File f : list) {
				// remove parent
				if (files.contains(f)) {
					list.remove(f);
				}
			}
		}
		return res;
	}

	void iterate(File cur, Map<String, List<File>> map) {

		for (File sub : cur.subDirs) {
			iterate(sub, map);
		}
		String s = serialize(cur);
		if (!map.containsKey(s)) {
			map.put(s, new ArrayList<>());
		}
		map.get(s).add(cur);
	}

	// /bar/fiz
	// [[a][b]]
	String serialize(File cur) {
		if (cur.isFile) {
			return "[" + cur.name + "]";
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			List<String> list = new ArrayList<>();
			for (File sub : cur.subDirs) {
				list.add(serialize(sub));
			}
			Collections.sort(list);
			for (String s : list) {
				sb.append(s);
			}
			sb.append("]");
			return sb.toString();
		}
	}

	// serialize func Time complexity: O(n * logn), n = files and directories
	// serialize func Space complexity: O (n)

	static int addNumbers(int a, int b) {
		return a + b;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int a;
		a = in.nextInt();
		int b;
		b = in.nextInt();
		int sum;

		sum = addNumbers(a, b);
		System.out.println(sum);
	}
}
