package booking;

import java.util.Collections;
import java.util.List;

public class ParkingLotRoof {
	public static long carParkingRoof(List<Long> cars, int k) {
		// Write your code here
		Collections.sort(cars);
		long min = Long.MAX_VALUE;
		int count = 0;
		long width = 1;
		for (int i = 1; i < cars.size(); i++) {
			count++;
			width += cars.get(i) - cars.get(i - 1);
			if (count >= k)
				width -= cars.get(i - k + 1) - cars.get(i - k);
			if (count >= k - 1)
				min = Math.min(min, width);

		}
		return min;
	}
}
