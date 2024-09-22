package airbnb;

import java.util.function.Function;
import java.util.function.Predicate;

//Requirements.
//Given a common interface implement small retrying library with following retriers:

//1. Retryer that retries once.
//2. Retryer that retries multiple times with fixed intervals.
//3. Retryer with exponential back-off.

//As we need to design a library it should be easily extensible by user defined retryers.

import java.util.function.Supplier;

public class Main {
	// Common interface
	private static class Retryer {
		/**
		 * A Retryer executes a callback and returns the callback output on
		 * success. If the callback fails, it retries execution of the callback.
		 *
		 * @param callback
		 *            is a function that takes no parameters and returns a
		 *            result. It can be instantiated directly or as a lambda
		 *            expression, e.g.: Supplier<String> successfulCall = new
		 *            Supplier<String>() {
		 * @Override public String get() { return "Success"; } };
		 * @return the result of the callback
		 * @param <T>
		 *            the type of result returned by the callback.
		 */
		private final Predicate<Integer> retries;
		private final Function<Integer, Long> interval;
		private final Predicate<Exception> exceptions;

		Retryer(Predicate<Integer> retries, Function<Integer, Long> interval,
				Predicate<Exception> exceptions) {
			this.retries = retries;
			this.interval = interval;
			this.exceptions = exceptions;
		}

		public <T> T execute(Supplier<T> callback) {
			int attemps = 0;
			while (true) {
				try {
					return callback.get();
				} catch (Exception e) {
					attemps++;
					if (retries.test(attemps)) {
						throw e;
					}
					long inter = interval.apply(attemps);
					try {
						Thread.sleep(inter);
					} catch (InterruptedException e1) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
	}

	// Examples of usage
	public static void main(String[] args) {
		Supplier<String> successfulCall = () -> "Success";
		Supplier<String> failedCall = () -> {
			throw new RuntimeException("Failed");
		};

		Retryer retryer = new Retryer(i -> (i <= 10), j -> (1000L * (1 << j)),
				e -> (e instanceof RuntimeException));

		// This should succeed
		System.out.println(retryer.execute(successfulCall));

		// This should fail and retry
		retryer.execute(failedCall);
	}
}