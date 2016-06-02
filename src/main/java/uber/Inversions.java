package uber;

import java.util.Random;

public class Inversions {
	public static int inversions1(int[] array) {
		int count = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[j] < array[i])
					count++;
			}
		}
		return count;
	}

	public static int mergeSort(int[] array, int begin, int end) {

		int count = 0;

		if (begin == end)
			return 0;

		int mid = (begin + end) >>> 1;
		count += mergeSort(array, begin, mid);
		count += mergeSort(array, mid + 1, end);
		count += merge(array, begin, mid, end);
		return count;
	}

	public static int merge(int[] array, int begin, int mid, int end) {

		int count = 0;

		int[] temp = new int[end - begin + 1];
		int i = begin, j = mid + 1, k = 0;
		while (i <= mid && j <= end) {
			if (array[i] <= array[j]) {
				temp[k++] = array[i++];
			} else {
				temp[k++] = array[j++];

				count += mid - i + 1;
			}
		}

		while (i <= mid) {
			temp[k++] = array[i++];
		}

		while (j <= end) {
			temp[k++] = array[j++];
		}

		for (int l = 0; l < temp.length; l++) {
			array[begin + l] = temp[l];
		}
		return count;
	}

	public static void main(String[] args) {
		int[] array = new int[1000];
		for (int i = 0; i < array.length; i++) {
			array[i] = new Random().nextInt();
		}
		long a = System.currentTimeMillis();
		System.out.println(inversions1(array));
		long b = System.currentTimeMillis();
		System.out.println(mergeSort(array, 0, array.length - 1));
		long c = System.currentTimeMillis();

		System.out.println(c - b);
		System.out.println(b - a);
	}
}
