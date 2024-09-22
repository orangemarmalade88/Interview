import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

	List<long[]> data; // timestamp -> value
	AtomicInteger sum;
	public char[] count;

	Counter() {
		data = new ArrayList<>();
		sum = new AtomicInteger();
	}

	void increment(int x) {
		long timestamp = System.currentTimeMillis();
		data.add(new long[] { timestamp, x });
		sum.addAndGet(x);
	}

	int getSum() {
		return sum.get();
	}

	int getIncreseInTimeRange(long start, long end) {
		return 0;
	}

	int getSingleMaxIncrementInTimeRange(long start, long end) {
		return 0;
	}

	public void decrement() {
		// TODO Auto-generated method stub

	}
}
