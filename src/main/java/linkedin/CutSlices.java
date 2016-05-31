package linkedin;

/*
 * maximum number of slices you can get by cutting a cake N times
 *
 * follow up:
 * O(1) space?
 * O(1) time?
 *
 */

public class CutSlices {
	int maxNumOfSlices(int cuts) {
		int[] c = new int[cuts + 1];
		c[0] = 1;
		for (int i = 1; i <= cuts; i++) {
			c[i] = c[i - 1] + i;
		}
		return c[cuts];
	}

	int maxNumOfSlices2(int cuts) {
		int prev = 1;
		for (int i = 1; i <= cuts; i++) {
			prev = prev + i;
		}
		return prev;
	}

	int maxNumOfSlices3(int cuts) {

		return cuts * (cuts + 1) / 2 + 1;
	}

	public static void main(String[] args) {
		CutSlices cs = new CutSlices();
		System.out.println(cs.maxNumOfSlices(9));
		System.out.println(cs.maxNumOfSlices2(9));
		System.out.println(cs.maxNumOfSlices3(9));

	}
}
