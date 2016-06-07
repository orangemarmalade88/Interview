public class RateLimiter {

	static int limit = 5;
	static int ptr = 0;
	static long[] times = new long[limit];

	public static synchronized boolean validate() {
		long curr = System.currentTimeMillis();
		if (curr - times[ptr] >= 1000) {
			times[ptr] = curr;
			ptr = (ptr + 1) % limit;
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		long old = System.currentTimeMillis();
		for (int i = 0; i < 200; i++) {
			long newT = System.currentTimeMillis();
			System.out.println(validate() + "" + (newT - old));
			Thread.sleep(25);
		}
	}
}
