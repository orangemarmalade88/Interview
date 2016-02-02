package real.box.elevator;

import real.box.elevator.Elevator.Direction;

public class Request {
	int destination;
	Direction direction;

	public Request(int destination, Direction direction) {
		super();
		this.destination = destination;
		this.direction = direction;
	}

}
