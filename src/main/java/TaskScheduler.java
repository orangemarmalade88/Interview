
public class TaskScheduler {

	long start = System.currentTimeMillis();

	private class Task implements Runnable {

		String method = "";
		int interval;

		Task(String method, int interval) {
			this.method = method;
			this.interval = interval;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(interval * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Running " + method + " at "
						+ (System.currentTimeMillis() - start) / 1000);

			}
		}

	}

	TaskScheduler() {

	}

	void scheduleTask(Task t) throws InterruptedException {
		Thread thread = new Thread(t);
		thread.start();

	}

	public static void main(String[] args) throws InterruptedException {
		// task 3 at t=2
		// task 3 at t=4
		// task 1 at t=5

		TaskScheduler ts = new TaskScheduler();
		Task t1 = ts.new Task("task 1", 5);
		Task t2 = ts.new Task("task 2", 8);
		Task t3 = ts.new Task("task 3", 2);
		ts.scheduleTask(t1);
		ts.scheduleTask(t2);
		ts.scheduleTask(t3);
	}
}
