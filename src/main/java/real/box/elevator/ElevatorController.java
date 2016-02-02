package real.box.elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import real.box.elevator.Elevator.Direction;

public class ElevatorController {
	private List<Elevator> elevators = null;
	private List<Thread> threads = null;
	private int elevatorCount = 0;

	public ElevatorController(int number) {
		elevators = new ArrayList<>();
		threads = new ArrayList<>();
		elevatorCount = number;
		for (int i = 0; i < number; i++) {
			Elevator e = new Elevator(i, 1);
			elevators.add(e);
			threads.add(new Thread(e));
		}
	}

	public void startAllElevators() {
		for (int i = 0; i < elevatorCount; i++) {
			threads.get(i).start();
		}
	}

	private Elevator findBestElevatorToServerRequest(final Request rq) {
		return Collections.min(elevators, new Comparator<Elevator>() {

			@Override
			public int compare(Elevator o1, Elevator o2) {
				// if both are in same direction, find closest
				if ((o1.getState() == Elevator.State.IDLE
						|| (o1.getDirection() == Direction.UP
								&& rq.direction == Direction.UP && o1
								.getFloor() < rq.destination) || (o1
						.getDirection() == Direction.DOWN
						&& rq.direction == Direction.DOWN && o1.getFloor() > rq.destination))
						&& (o2.getState() == Elevator.State.IDLE
								|| (o2.getDirection() == Direction.UP
										&& rq.direction == Direction.UP && o2
										.getFloor() < rq.destination) || (o2
								.getDirection() == Direction.DOWN
								&& rq.direction == Direction.DOWN && o2
								.getFloor() > rq.destination))

				) {
					return Math.abs(o1.getFloor() - rq.destination)
							- Math.abs(o2.getFloor() - rq.destination);
				} else if (o1.getState() == Elevator.State.IDLE
						|| (o1.getDirection() == Direction.UP && o1.getFloor() < rq.destination)
						|| (o1.getDirection() == Direction.DOWN && o1
								.getFloor() > rq.destination)) {
					return -1;
				} else if (o2.getState() == Elevator.State.IDLE
						|| (o2.getDirection() == Direction.UP && o2.getFloor() < rq.destination)
						|| (o2.getDirection() == Direction.DOWN && o2
								.getFloor() > rq.destination)) {
					return 1;
				} else {
					return Math.abs(o1.getFloor() - rq.destination)
							- Math.abs(o2.getFloor() - rq.destination);
				}
			}
		});
	}

	public Elevator handleExternalRequest(Request rq) {
		Elevator e = findBestElevatorToServerRequest(rq);
		// System.out.println("Elevator [" + e.getId()
		// + "] will handle requested floor " + rq.destination
		// + " direction = " + rq.direction + " . Current at Floor "
		// + e.getFloor() + " State = " + e.getState() + " Direction = "
		// + e.getDirection());
		e.addRequest(rq);
		return e;
	}
}
