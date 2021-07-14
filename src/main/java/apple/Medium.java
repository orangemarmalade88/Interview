package apple;

import java.util.List;

public class Medium {
	// - 32 bit integers (java integers, signed)
	// - billions of measurements
	// - cluster is a set of n nodes

	interface Node {
		long count(); // number of integers on that node

		long lt(int probe); // less than value -- the count of numbers that is
							// strictly less than probe

		long gt(int probe); // greater than value -- the count of numbers that
							// is strictly greater than probe

		long lte(int probe); // less than or equal value -- the count of numbers
								// that is less than probe

		long gte(int probe); // greater than or equal value -- the count of
								// numbers that is greater than probe
	};

	// n: [ 5, 0, -2, 5, 10 ]

	// n.count() --> 5
	// n.lt(0) --> 1
	// n.gte(5) --> 3

	// odd card: element in the middle: f(count/2)
	// even card: avg of middle numbers: (f(count/2) + f(count/2 +1))/2

	int getMedian(List<Node> nodes) {
		int left = Integer.MIN_VALUE;
		int right = Integer.MAX_VALUE;

		while (left < right) {
			long mid = left + (right - left) / 2;
			if (total_lt(nodes, (int) mid) > total_count(nodes) / 2) {
				right = (int) (mid - 1);
			} else if (total_lt(nodes, (int) mid) < total_count(nodes) / 2) {
				left = (int) (mid + 1);
			} else
				return (int) mid;
		}
		return 0;
	}

	int isMedian(List<Node> nodes, int probe) {
		// 0 if probe is median
		// -1 if probe is less than median
		// 1 if probe is greater than median
		return 0;
	}

	int total_count(List<Node> nodes) {
		return -1;
	}

	int total_lt(List<Node> nodes, int probe) {
		return -1;
	}
}
