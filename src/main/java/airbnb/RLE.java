package airbnb;

import java.util.ArrayList;
import java.util.List;

public class RLE {

	public static List<int[]> runLengthEncoding(int[] arr) {
		List<int[]> result = new ArrayList<>();
		int n = arr.length;
		int i = 0;
		while (i < n) {
			int count = 1;
			int num = arr[i];
			while (i + 1 < n && arr[i + 1] == num) {
				i++;
				count++;
			}
			result.add(new int[] { count, num });
			i++;
		}
		return result;
	}

	public static List<int[]> arithmeticRLE(int[] arr) {
		List<int[]> result = new ArrayList<>();
		int n = arr.length;
		int i = 0;

		while (i < n) {
			int start = arr[i];
			if (i + 1 >= n) {
				// Single element sequence
				result.add(new int[] { start, 0, 1 });
				break;
			}
			int diff = arr[i + 1] - arr[i];
			int count = 2;
			int j = i + 1;
			while (j + 1 < n && arr[j + 1] - arr[j] == diff) {
				j++;
				count++;
			}
			result.add(new int[] { start, diff, count });
			i = j + 1;
		}
		return result;
	}

	public static void main(String[] args) {
		int[] arr = { 3, 4, 5, 10, 15, 20, 25 };
		List<int[]> encoded = arithmeticRLE(arr);
		for (int[] triple : encoded) {
			System.out.println("(" + triple[0] + ", " + triple[1] + ", "
					+ triple[2] + ")");
		}
	}

}
