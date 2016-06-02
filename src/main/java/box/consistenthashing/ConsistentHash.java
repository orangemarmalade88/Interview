package box.consistenthashing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

public class ConsistentHash {

	// Add, Remove, Get O(lgN), N = nodes

	private final int numberOfReplicas;
	private final TreeMap<BigInteger, Node> hashRing = new TreeMap<>();
	MessageDigest md;

	public ConsistentHash(int numberOfReplicas) {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		this.numberOfReplicas = numberOfReplicas;
	}

	private BigInteger hash(String key) {
		byte[] bytes = md.digest(key.getBytes());
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		String digest = sb.toString();
		BigInteger bi = new BigInteger(digest, 16);
		return bi;
	}

	private Node createNode(String name) {
		return new Node(name);
	}

	// Cache Server
	public class Node {

		String name;

		public Node(String name) {
			this.name = name;
		}
	}

	public void add(Node node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			hashRing.put(hash(node.name + i), node);
		}
	}

	public void remove(Node node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			hashRing.remove(hash(node.name + i), node);
		}
	}

	public Node getNode(String key) {
		if (hashRing.isEmpty())
			return null;
		BigInteger hashkey = hash(key);
		Map.Entry<BigInteger, Node> entry = hashRing.ceilingEntry(hashkey);
		if (entry == null)
			return hashRing.firstEntry().getValue();
		else
			return entry.getValue();
	}

	public static void collisionTest() {
		ConsistentHash ch = new ConsistentHash(100);

		int nodes = 30;
		for (int i = 0; i < nodes; i++) {
			Node n = ch.createNode("Node" + i + "_");
			ch.add(n);
		}

		ConsistentHash ch2 = new ConsistentHash(100);

		int nodes2 = 31;
		for (int i = 0; i < nodes2; i++) {
			Node n = ch2.createNode("Node" + i + "_");
			ch2.add(n);
		}

		int c = 0;
		int w = 0;

		int trails = 100000;
		for (int i = 0; i < trails; i++) {
			String s = UUID.randomUUID().toString();
			Node n = ch.getNode(s);
			Node n2 = ch2.getNode(s);
			if (n.name.equals(n2.name))
				c++;
			else
				w++;
		}

		System.out.println("hits = " + c);
		System.out.println("misses = " + w);
		System.out.println("miss rate = " + (w / (double) (w + c)));

	}

	public static void uniformTest() {
		ConsistentHash ch = new ConsistentHash(100);
		Map<Node, Integer> count = new LinkedHashMap<>();

		int nodes = 30;
		for (int i = 0; i < nodes; i++) {
			Node n = ch.createNode("Node" + i + "_");
			ch.add(n);
			count.put(n, 0);
		}

		int trails = 100000;
		for (int i = 0; i < trails; i++) {
			String s = UUID.randomUUID().toString();
			Node n = ch.getNode(s);
			count.put(n, count.get(n) + 1);
		}

		for (Entry<Node, Integer> entry : count.entrySet()) {
			System.out.println(entry.getKey().name + " has " + entry.getValue()
					+ " entries.");
		}
	}

	public static void main(String[] args) {
		// collisionTest();
		uniformTest();
	}
}
