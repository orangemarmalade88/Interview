package real.box.topkstrings;

import java.util.Collections;
import java.util.PriorityQueue;

public class FindKthSmallestElement {

	// O(k + (n-k)lgk) = O(Nlgk)

	int findKthSmallestElement(int[] array, int k) {
		PriorityQueue<Integer> pq = new PriorityQueue<>(k,
				Collections.reverseOrder());

		// assuming k < array.length
		int i = 0;
		for (; i < k; i++) {
			pq.add(array[i]);
		}

		for (; i < array.length; i++) {
			int head = pq.peek();
			if (array[i] < head) {
				pq.poll();
				pq.add(array[i]);
			}
		}
		return pq.peek();
	}
}
