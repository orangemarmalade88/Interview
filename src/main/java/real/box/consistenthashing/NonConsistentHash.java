package real.box.consistenthashing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class NonConsistentHash {

	private final List<Node> list = new ArrayList<>();
	MessageDigest md;

	public NonConsistentHash() {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
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
		list.add(node);
	}

	public void remove(Node node) {
		list.remove(node);
	}

	public Node getNode(String key) {
		if (list.isEmpty())
			return null;
		BigInteger hashkey = hash(key);
		int id = hashkey.mod(new BigInteger(String.valueOf(list.size())))
				.intValue();
		return list.get(id);
	}

	public static void collisionTest() {
		NonConsistentHash ch = new NonConsistentHash();

		int nodes = 30;
		for (int i = 0; i < nodes; i++) {
			Node n = ch.createNode("Node" + i + "_");
			ch.add(n);
		}

		NonConsistentHash ch2 = new NonConsistentHash();

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
		NonConsistentHash ch = new NonConsistentHash();
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
