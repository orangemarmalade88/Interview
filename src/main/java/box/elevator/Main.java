package box.elevator;

import box.elevator.Elevator.Direction;
import box.elevator.Elevator.State;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		oneElevatorTestCase();
	}

	private static void waitUntilIdle(Elevator e) throws InterruptedException {
		while (e.getState() != State.IDLE) {
			Thread.sleep(100);
		}
		Thread.sleep(1000);
	}

	private static void waitUntilIFloor(Elevator e, int floor)
			throws InterruptedException {
		while (e.getFloor() != floor) {
			Thread.sleep(100);
		}
		Thread.sleep(1000);
	}

	private static void oneElevatorTestCase() throws InterruptedException {
		ElevatorController ec = new ElevatorController(1);
		ec.startAllElevators();
		Thread.sleep(3000);
		Elevator e = ec.handleExternalRequest(new Request(5, Direction.DOWN));
		Thread.sleep(5000);
		ec.handleExternalRequest(new Request(5, Direction.DOWN));
		e.addRequest(7);
		e.addRequest(3);
		waitUntilIdle(e);
		ec.handleExternalRequest(new Request(20, Direction.UP));
		Thread.sleep(1000);
		ec.handleExternalRequest(new Request(20, Direction.DOWN));
		ec.handleExternalRequest(new Request(21, Direction.UP));
		ec.handleExternalRequest(new Request(21, Direction.DOWN));
		ec.handleExternalRequest(new Request(19, Direction.UP));
		ec.handleExternalRequest(new Request(19, Direction.DOWN));
		ec.handleExternalRequest(new Request(3, Direction.UP));
		ec.handleExternalRequest(new Request(3, Direction.DOWN));
		ec.handleExternalRequest(new Request(2, Direction.UP));
		ec.handleExternalRequest(new Request(2, Direction.DOWN));
		waitUntilIdle(e);
		ec.handleExternalRequest(new Request(10, Direction.DOWN));
		waitUntilIFloor(e, 10);
		e.addRequest(15);
	}
}
