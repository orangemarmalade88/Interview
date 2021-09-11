package amazon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

class FileSystem {
	class File {
		boolean isFile;
		String content;
		List<File> children;

		int getSize() {
			return 0;
		}

		String getName() {
			return "";
		}
	}

	public static void main(String[] args) {
		Predicate<File> size = f -> f.getSize() > 5;
		Predicate<File> name = f -> f.getName().endsWith(".xml");
		FileSystem fs = new FileSystem();
		File f = fs.new File();
		FileSystem.getMatches(f, size.or(name));
	}

	static List<File> getMatches(File path, Predicate<File> conditions) {
		List<File> list = new ArrayList<>();
		Set<File> seen = new HashSet<>();
		seen.add(path);
		getMatch(path, conditions, list, seen);
		return list;
	}

	// Symbolic link
	static void getMatch(File file, Predicate<File> conditions, List<File> res,
			Set<File> seen) {
		if (file.isFile) {
			if (!conditions.test(file))
				return;
			res.add(file);
		} else {
			for (File f : file.children) {
				if (!seen.contains(f)) {
					seen.add(f);
					getMatch(f, conditions, res, seen);
				}
			}
		}

	}
}