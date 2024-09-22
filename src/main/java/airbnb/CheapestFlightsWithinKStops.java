package airbnb;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class CheapestFlightsWithinKStops {
	public static int findCheapestPrice(int n, int[][] flights, int src,
			int dst, int K) {
		// Create adjacency list to represent the flight graph
		Map<Integer, List<int[]>> adjList = new HashMap<>();
		for (int[] flight : flights) {
			adjList.computeIfAbsent(flight[0], k -> new ArrayList<>())
					.add(new int[] { flight[1], flight[2] });
		}

		// Min-heap to store [current cost, current city, number of stops]
		PriorityQueue<int[]> pq = new PriorityQueue<>(
				Comparator.comparingInt(a -> a[0]));
		pq.offer(new int[] { 0, src, 0 }); // start from src with cost 0 and 0
											// stops

		// Map to store the shortest cost to reach each city with a specific
		// number of stops
		// The key is (city * 1000 + stops) to uniquely identify the state
		// (city, stops)
		Map<Integer, Integer> costAtStops = new HashMap<>();
		costAtStops.put(src * 1000, 0); //

		while (!pq.isEmpty()) {
			int[] current = pq.poll();
			int currentCost = current[0];
			int currentCity = current[1];
			int stops = current[2];

			// If we reached the destination, return the cost
			if (currentCity == dst) {
				return currentCost;
			}

			// If we exceeded the stop limit, skip this route
			if (stops > K)
				continue;

			// Explore neighboring cities
			if (adjList.containsKey(currentCity)) {
				for (int[] flight : adjList.get(currentCity)) {
					int nextCity = flight[0];
					int nextCost = flight[1];
					int newCost = currentCost + nextCost;
					int newStops = stops + 1;

					// Only consider the next city if we haven't visited it with
					// fewer stops
					if (!costAtStops.containsKey(nextCity * 1000 + newStops)
							|| newCost < costAtStops
									.get(nextCity * 1000 + newStops)) {
						pq.offer(new int[] { newCost, nextCity, newStops });
						costAtStops.put(nextCity * 1000 + newStops, newCost);
					}
				}
			}
		}

		// If we didn't reach the destination, return -1
		return -1;
	}

	public static void main(String[] args) {
		int[][] flights = { { 0, 1, 500 }, // London -> Japan, 500
				{ 1, 2, 100 }, // Japan -> Beijing, 100
				{ 0, 2, 1000 } // London -> Beijing, 1000
		};
		int n = 3; // Number of cities
		int src = 0; // London (0)
		int dst = 2; // Beijing (2)
		int K = 1; // Maximum 1 stop

		int result = findCheapestPrice(n, flights, src, dst, K);
		System.out.println("Cheapest price: " + result); // Output: 600
	}

}
