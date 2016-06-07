import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class SchedulerWithCooldown {
	public int executionTime(String input, int interval) {
		int wait = 0;

		if (interval <= 1)
			return input.length();

		Map<Character, Integer> lastSeen = new HashMap<>();

		for (int i = 0; i < input.length(); i++) {
			char curr = input.charAt(i);
			if (lastSeen.containsKey(curr)) {
				if ((i + wait) - lastSeen.get(curr) < interval)
					wait += interval - ((i + wait) - lastSeen.get(curr));
			}
			lastSeen.put(curr, (i + wait));
		}

		return input.length() + (interval - 1) + wait;
	}

	class Node {
		Character c;
		int count;

		public Node(Character c, int count) {
			this.c = c;
			this.count = count;
		}
	}

	public String reschedule(String input, int interval) {
		Map<Character, Integer> count = new HashMap<>();
		Map<Character, Integer> lastSeen = new HashMap<>();
		for (char c : input.toCharArray()) {
			count.put(c, count.getOrDefault(c, 0) + 1);
		}
		Queue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o2.count - o1.count;
			}
		});
		Queue<Node> queue2 = new LinkedList<>();
		for (char c : count.keySet()) {
			queue.add(new Node(c, count.get(c)));
		}
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (sb.length() != input.length()) {
			Node q = null;
			if (!queue.isEmpty()) {
				q = queue.poll();
			} else {
				q = queue2.poll();
			}
			q.count--;
			char curr = q.c;
			sb.append(curr);
			if (!lastSeen.containsKey(curr)
					|| i - lastSeen.get(curr) < interval) {
				queue.add(q);
			} else {
				queue2.add(q);
			}

			lastSeen.put(curr, i);
			i++;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		SchedulerWithCooldown swc = new SchedulerWithCooldown();
		String input = "ABBABBC";

		System.out.println(swc.executionTime(input, 1));
		System.out.println(swc.executionTime(input, 2));
		System.out.println(swc.executionTime(input, 3));
		System.out.println(swc.executionTime(input, 4));
		System.out.println(swc.reschedule(input, 1));
		System.out.println(swc.executionTime(swc.reschedule(input, 1), 1));

		System.out.println(swc.reschedule(input, 2));
		System.out.println(swc.executionTime(swc.reschedule(input, 2), 2));

		System.out.println(swc.reschedule(input, 3));
		System.out.println(swc.executionTime(swc.reschedule(input, 3), 3));

		System.out.println(swc.reschedule(input, 4));
		System.out.println(swc.executionTime(swc.reschedule(input, 4), 4));
	}
}
