package linkedin;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Design a delayed scheduler
 */

public class DelayedScheduler implements Runnable {

	class Printer implements Runnable {

		String name;
		long startTime;

		Printer(String name, long startTime) {
			this.name = name;
			this.startTime = startTime;
		}

		@Override
		public void run() {
			long now = System.currentTimeMillis();
			double diff = (now - startTime) / 1000.0;
			System.out.println("Printing from " + name + " at " + diff
					+ " secs.");
		}
	}

	class Task {
		Runnable r;
		long startTime;

		Task(Runnable r, long startTime) {
			this.r = r;
			this.startTime = startTime;
		}
	}

	private final Lock lock = new ReentrantLock();
	private final Condition noTask = lock.newCondition();
	private final Queue<Task> pq = new PriorityQueue<>(new Comparator<Task>() {

		@Override
		public int compare(Task o1, Task o2) {
			return o1.startTime - o2.startTime <= 0 ? -1 : 1;
		}
	});

	void schedule(Runnable r, long delay) {
		boolean signal = false;
		long curr = System.currentTimeMillis();
		lock.lock();
		long startTime = curr + delay;
		if (pq.isEmpty() || startTime < pq.peek().startTime) {
			signal = true;
		}
		pq.add(new Task(r, startTime));
		if (signal)
			noTask.signal();
		lock.unlock();
	}

	@Override
	public void run() {
		while (true) {
			try {
				lock.lock();
				while (pq.isEmpty()
						|| pq.peek().startTime > System.currentTimeMillis()) {
					if (pq.isEmpty())
						noTask.await();
					else {
						long wait = pq.peek().startTime
								- System.currentTimeMillis();
						noTask.await(wait, TimeUnit.MILLISECONDS);
					}
				}
				Task t = pq.poll();
				t.r.run();
			} catch (InterruptedException e) {
				// not possible
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		DelayedScheduler s = new DelayedScheduler();
		Thread t = new Thread(s);
		long t1 = System.currentTimeMillis();
		Runnable r1 = s.new Printer("r1", t1);
		Runnable r2 = s.new Printer("r2", t1);
		Runnable r3 = s.new Printer("r3", t1);
		t.start();
		s.schedule(r1, 10000L);
		s.schedule(r2, 15000L);
		Thread.sleep(2000L);
		s.schedule(r3, 3000L);
		t.join();
	}
}
