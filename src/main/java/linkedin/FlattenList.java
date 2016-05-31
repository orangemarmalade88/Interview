package linkedin;

/*
 * Flatten a linked list with up/down pointer to a flattened list
 *
 */

public class FlattenList {
	class Node {
		int value;
		Node up;
		Node down;
		Node left;
		Node right;

		Node(int value) {
			this.value = value;
		}
	}

	void flatternList(Node head) {

		while (head != null) {

			if (head.up != null) {
				Node right = head.right;
				Node upLeft = head.up;
				while (upLeft.left != null) {
					upLeft = upLeft.left;
				}
				head.right = upLeft;
				upLeft.left = head;

				Node upRight = head.up;
				while (upRight.right != null) {
					upRight = upRight.right;
				}

				upRight.right = right;
				if (right != null)
					right.left = upRight;

				head.up.down = null;
				head.up = null;

			}

			if (head.down != null) {
				Node right = head.right;
				Node downLeft = head.down;
				while (downLeft.left != null) {
					downLeft = downLeft.left;
				}
				head.right = downLeft;
				downLeft.left = head;

				Node downRight = head.down;
				while (downRight.right != null) {
					downRight = downRight.right;
				}

				downRight.right = right;
				if (right != null)
					right.left = downRight;

				head.down.up = null;
				head.down = null;

			}

			head = head.right;
		}

	}

	public static void main(String[] args) {
		FlattenList fl = new FlattenList();
		Node head = fl.new Node(1);
		Node n2 = fl.new Node(2);
		Node n3 = fl.new Node(3);
		Node n4 = fl.new Node(4);
		Node n5 = fl.new Node(5);
		Node n6 = fl.new Node(6);
		Node n7 = fl.new Node(7);
		Node n8 = fl.new Node(8);

		Node n9 = fl.new Node(9);

		head.down = n5;
		n5.up = head;

		n5.left = n4;
		n4.right = n5;

		n5.right = n6;
		n6.left = n5;

		head.right = n2;
		n2.left = head;

		n2.right = n3;
		n3.left = n2;

		n3.up = n7;
		n7.down = n3;

		n3.down = n8;
		n8.up = n3;

		n3.right = n9;
		n9.left = n3;

		Node c = head;
		while (c != null) {
			System.out.print(c.value + " ");
			c = c.right;
		}
		System.out.println();

		fl.flatternList(head);

		c = head;
		while (c != null) {
			System.out.print(c.value + " ");
			c = c.right;
		}
		System.out.println();
	}

}
