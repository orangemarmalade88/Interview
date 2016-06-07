package uber;
public class NumberOfOccurancesInSortedArray {
	public static int count(int[] array, int target) {

		// error checking
		int first = findFirstOrLast(array, target, true);
		if (first == -1)
			return 0;
		return findFirstOrLast(array, target, false) - first + 1;
	}

	private static int findFirstOrLast(int[] array, int target,
			boolean findFirst) {
		int low = 0, high = array.length - 1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (array[mid] == target
					&& (findFirst && (mid == 0 || array[mid - 1] < target))
					|| (!findFirst && (mid == array.length - 1 || array[mid + 1] > target))) {
				return mid;
			} else if (array[mid] == target) {
				if (findFirst)
					high = mid - 1;
				else
					low = mid + 1;
			} else if (array[mid] > target)
				high = mid - 1;
			else
				low = mid + 1;
		}
		return -1;
	}
}
