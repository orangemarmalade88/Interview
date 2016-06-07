import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue {

	Lock lock = new ReentrantLock();
	Condition notFull = lock.newCondition();
	Condition notEmpty = lock.newCondition();

	int[] values = null;
	int capacity = 0;
	int size = 0;

	int putPtr = 0;
	int takePtr = 0;

	MyBlockingQueue(int capacity) {
		this.capacity = capacity;
		values = new int[capacity];
	}

	void put(int value) throws InterruptedException {
		lock.lock();
		try {
			while (size == capacity) {
				notFull.await();
			}
			values[putPtr] = value;
			putPtr = (putPtr++) % capacity;
			size++;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	int take() throws InterruptedException {
		lock.lock();
		try {
			while (size == 0) {
				notEmpty.await();
			}
			int value = values[takePtr];
			takePtr = (takePtr++) % capacity;
			notFull.signal();
			return value;
		} finally {
			lock.unlock();
		}
	}

}
