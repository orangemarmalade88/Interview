public class MergeSort {
	public void mergeSort(int[] array) {
		// Uses exactly O(N) space
		int[] temp = new int[array.length];
		mergeSort(array, temp, 0, array.length - 1);
	}

	public void mergeSort(int[] array, int[] temp, int start, int end) {
		if (start >= end)
			return;
		// Consider overflow
		int middle = start + (end - start) / 2;
		mergeSort(array, temp, start, middle);
		mergeSort(array, temp, middle + 1, end);
		merge(array, temp, start, middle, end);
	}

	void merge(int[] a, int[] temp, int start, int middle, int end) {
		int i = start, j = middle + 1, k = start;
		while (i <= middle && j <= end) {
			// Includes == here for stable sort
			if (a[i] <= a[j])
				temp[k++] = a[i++];
			else
				temp[k++] = a[j++];
		}
		while (i <= middle)
			temp[k++] = a[i++];
		while (j <= end)
			temp[k++] = a[j++];

		for (int n = start; n <= end; n++) {
			a[n] = temp[n];
		}

	}
}
