package linkedin;

import java.util.Collections;
import java.util.List;

public class Connections {

	// Given API, returns a sorted list of connections of A
	private List<Integer> getConnections(int a) {
		return null;
	}

	// O(lgN)
	public boolean is1stDegreeConnection(int a, int b) {
		List<Integer> connectionA = getConnections(a);
		return Collections.binarySearch(connectionA, b) >= 0;
	}

	// O(N)
	public boolean is2stDegreeConnection(int a, int b) {
		List<Integer> connectionA = getConnections(a);
		List<Integer> connectionB = getConnections(b);
		int sa = connectionA.size();
		int sb = connectionB.size();
		int i = 0;
		int j = 0;
		while (i < sa && j < sb) {
			if (connectionA.get(i) < connectionB.get(j)) {
				i++;
			} else if (connectionA.get(i) > connectionB.get(j)) {
				j++;
			} else
				return true;
		}
		return false;
	}

	// O(N^2)
	public boolean is3stDegreeConnection(int a, int b) {
		List<Integer> connectionA = getConnections(a);
		for (int i : connectionA) {
			if (is2stDegreeConnection(i, b))
				return true;
		}
		return false;
	}
}
