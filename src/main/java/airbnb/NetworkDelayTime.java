package airbnb;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class NetworkDelayTime {
	public static int networkDelayTime(int[][] times, int N, int K) {
		// Create an adjacency list to represent the graph
		Map<Integer, List<int[]>> graph = new HashMap<>();
		for (int[] time : times) {
			graph.computeIfAbsent(time[0], k -> new ArrayList<>())
					.add(new int[] { time[1], time[2] });
		}

		// Use a priority queue to perform Dijkstra's algorithm
		PriorityQueue<int[]> pq = new PriorityQueue<>(
				Comparator.comparingInt(a -> a[1]));
		pq.offer(new int[] { K, 0 });

		// Map to store the shortest time to each node
		Map<Integer, Integer> dist = new HashMap<>();

		while (!pq.isEmpty()) {
			int[] curr = pq.poll();
			int node = curr[0];
			int time = curr[1];

			// If we already have a shorter time for this node, skip it
			if (dist.containsKey(node))
				continue;

			dist.put(node, time);

			// Explore the neighbors
			if (graph.containsKey(node)) {
				for (int[] neighbor : graph.get(node)) {
					int nextNode = neighbor[0];
					int travelTime = neighbor[1];
					if (!dist.containsKey(nextNode)) {
						pq.offer(new int[] { nextNode, time + travelTime });
					}
				}
			}
		}

		// If we didn't reach all nodes, return -1
		if (dist.size() != N)
			return -1;

		// Return the maximum time taken to reach any node
		return dist.values().stream().max(Integer::compare).orElse(-1);
	}

	public static void main(String[] args) {
		int[][] times = { { 2, 1, 1 }, { 2, 3, 1 }, { 3, 4, 1 } };
		int N = 4; // Number of nodes
		int K = 2; // Starting node

		int result = networkDelayTime(times, N, K);
		System.out.println("Network Delay Time: " + result); // Output: 2
	}

}
