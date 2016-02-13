package real.box.topkstrings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/*

 Input: integer, root folder path
 Output: collection of the top `k` strings (lexographical ordering) - all strings should be different

 Given an integer `k` and a root folder path, find the
 top k (lexographically ordered) distinct strings stored in the files in that root folder.

 - Every file in the folder has 1 string per line
 - There can be duplicate strings within or across files
 - Folders can contain more folders
 - Example directory structure:
 - /root
 -		/a
 -			a1.txt
 -			a2.txt
 -			/aa
 -				aa1.txt
 -		/b
 -			b1.txt
 -		root1.txt
 -		root2.txt
 -		root3.txt

 - You may/can assume a function that returns child files/folders in the first level of a directory

 Example file content:
 $ cat some_file.txt
 hello
 world
 this
 is
 a
 text
 text
 file
 text

 */

public class TopKStrings {

	// Special cases:
	// 1. invalid directory
	// 2. empty directory

	private void addToHeapAndSet(int k, String line, Queue<String> pq,
			Set<String> set) {
		if (set.contains(line))
			return;
		// Mistake: condition is < k if you don't initialize it
		if (pq.size() < k) {
			pq.add(line);
			set.add(line);
		} else if (line.compareTo(pq.peek()) < 0) {
			set.remove(pq.poll());
			pq.add(line);
			set.add(line);
		}
	}

	public Collection<String> findTopKStrings(int k, File root)
			throws IOException {
		Queue<String> pq = new PriorityQueue<>(k, Collections.reverseOrder());
		Set<String> set = new HashSet<>();
		if (!root.isDirectory()) {
			String line = null;
			try (FileInputStream fis = new FileInputStream(root);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(fis));) {
				while ((line = br.readLine()) != null) {
					addToHeapAndSet(k, line, pq, set);
				}
			}
		} else {
			File[] files = root.listFiles();
			for (File file : files) {
				Collection<String> topKStrings = findTopKStrings(k, file);
				for (String line : topKStrings) {
					addToHeapAndSet(k, line, pq, set);
				}
			}
		}
		return pq;
	}
}
