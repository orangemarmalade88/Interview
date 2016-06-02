package box.elevator;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Elevator implements Runnable {

	private final long travelTime = 1000;
	private final long stopTime = 3000;

	private final int id;
	private int floor;
	private Direction direction;
	private State state;

	public enum Direction {
		UP,
		DOWN,
		NONE
	}

	public enum State {
		RUNNING,
		STOPPED,
		IDLE
	}

	public Elevator(int id, int initialFloor) {
		this.id = id;
		this.floor = initialFloor;
		direction = Direction.NONE;
		state = State.IDLE;
	}

	public int getFloor() {
		return floor;
	}

	public int getId() {
		return id;
	}

	public Direction getDirection() {
		return direction;
	}

	public State getState() {
		return state;
	}

	private final Comparator<Request> comp = new Comparator<Request>() {
		@Override
		public int compare(Request r1, Request r2) {
			return r1.destination - r2.destination;
		}
	};

	private final Queue<Request> upQueue = new PriorityQueue<>(comp);
	private final Queue<Request> downQueue = new PriorityQueue<Request>(
			comp.reversed());
	private Queue<Request> currentQueue = upQueue;
	private Queue<Request> nextQueue = downQueue;

	public void addRequest(int destination) {
		Direction dir = Direction.NONE;
		if (destination > floor)
			dir = Direction.UP;
		else if (destination < floor)
			dir = Direction.DOWN;

		Request request = new Request(destination, dir);
		addRequest(request);
	}

	public void addRequest(Request request) {
		if (state == State.IDLE
				|| (state == State.STOPPED && request.destination == floor)) {
			currentQueue.add(request);
		} else if (direction == Direction.UP) {
			if (request.direction == Direction.UP
					&& request.destination > floor) {
				upQueue.add(request);
			} else {
				downQueue.add(request);
			}
		} else {
			if (request.direction == Direction.DOWN
					&& request.destination < floor) {
				downQueue.add(request);
			} else {
				upQueue.add(request);
			}
		}
	}

	private void moveUp() {
		state = State.RUNNING;
		direction = Direction.UP;
		System.out.println(state + "\t " + direction + "\t@" + floor
				+ "\tElevator [" + id + "] moving up to floor " + (floor + 1)
				+ ".");
		try {
			Thread.sleep(travelTime);
		} catch (InterruptedException e) {

		}
		floor++;

	}

	private void moveDown() {
		state = State.RUNNING;
		direction = Direction.DOWN;
		System.out.println(state + "\t " + direction + "\t@" + floor
				+ "\tElevator [" + id + "] moving down to floor " + (floor - 1)
				+ ".");
		try {
			Thread.sleep(travelTime);
		} catch (InterruptedException e) {

		}
		floor--;
	}

	private void stopAndOpenAndCloseDoor() {
		state = State.STOPPED;
		System.out.println(state + "\t " + direction + "\t@" + floor
				+ "\tElevator [" + id + "] arrived at floor " + floor + ".");
		try {
			Thread.sleep(stopTime);
		} catch (InterruptedException e) {

		}
	}

	// should only be called in state stopped and state idle
	private void handleAllRequests() {
		while (true) {
			if (upQueue.isEmpty() && downQueue.isEmpty()) {
				state = State.IDLE;
				direction = Direction.NONE;
				System.out.println(state + "\t " + direction + "\t@" + floor
						+ "\tElevator [" + id + "] became idle at floor "
						+ floor + ".");
				while (upQueue.isEmpty() && downQueue.isEmpty()) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				}
			} else {
				while (!currentQueue.isEmpty()) {
					Request currentRequest = currentQueue.peek();
					if (currentRequest.destination == floor) {
						Request rq = currentQueue.poll();
						if (rq.direction != direction
								&& rq.direction != Direction.NONE) {
							direction = rq.direction;
							swtichQueues();
						}
						stopAndOpenAndCloseDoor();
					} else {
						if (currentQueue == upQueue) {
							moveUp();
						} else {
							moveDown();
						}
					}
				}
				if (!nextQueue.isEmpty())
					swtichQueues();
				handleAllRequests();
			}
		}

	}

	private void swtichQueues() {
		if (currentQueue == upQueue) {
			currentQueue = downQueue;
			nextQueue = upQueue;
		} else {
			currentQueue = upQueue;
			nextQueue = downQueue;
		}
	}

	@Override
	public void run() {
		handleAllRequests();
	}

}
