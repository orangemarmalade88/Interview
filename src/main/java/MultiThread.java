import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

class Counter {
	int count;

	public synchronized void increment() throws InterruptedException {
		Thread.sleep(100);
		count++;
		System.out.println("+");
	}

	public synchronized void decrement() throws InterruptedException {
		Thread.sleep(100);

		count--;
		System.out.println("-");

	}
}

class C {
	class D {
		int a;
	}
}

public class MultiThread {

	public static void main(String[] args) throws InterruptedException {
		Counter c = new Counter();

		LinkedList<Counter> e = new LinkedList<>();
		e.remove(c);

		Set<Integer> s = new LinkedHashSet<>();

		s.add(15);
		s.add(25);
		s.add(35);
		s.add(45);
		s.add(55);

		for (int i : s) {
			System.out.println(i);
		}

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					try {
						c.increment();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					try {
						c.decrement();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println(c.count);
	}
}
