package google;

import java.util.HashMap;
import java.util.Map;

/*

 Find one popular element in an sorted array.
 Popular element is defined as # of occurrences >= Size / 4.

 */

public class PopularElements {

	int startIdx(int[] sortedArray, int index) {
		int start = 0;
		int end = index;
		while (start < end) {
			int mid = (start + end) >>> 1;
			if (sortedArray[index] == sortedArray[mid])
				end = mid;
			else
				start = mid + 1;
		}
		return start;
	}

	int findPopularElements(int[] sortedArray, int n) {

		int doubleN = n * 2;

		if (sortedArray == null || sortedArray.length == 0)
			return -1;
		if (sortedArray.length >= doubleN) {

			Map<Integer, Integer> map = new HashMap<>();

			int segmentLength = sortedArray.length / doubleN;
			for (int i = 1; i < doubleN; i++) {
				if (sortedArray[i * segmentLength] == sortedArray[(i - 1)
						* segmentLength]) {
					map.putIfAbsent(sortedArray[i * segmentLength], (i - 1)
							* segmentLength);
				}
			}

			for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				int element = entry.getKey();
				int bound = entry.getValue();
				int start = startIdx(sortedArray, bound);
				if (sortedArray[start + sortedArray.length / n - 1] == element)
					return element;
			}
			return -1;

		} else {
			return sortedArray[0];
		}
	}

	public static void main(String[] args) {
		PopularElements pp = new PopularElements();
		int[] a = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		System.out.println(pp.findPopularElements(a, 4));
	}
}
