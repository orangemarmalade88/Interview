package real.microsoft.addtwonumbers;

/*

 You are given two integers. Each integer is represented by a linked list where each
 node in the list represents one digit. Return the sum of these two integers as
 a linked list.

 Example:
 Input: 1->2->6 and 4->5
 Output: 1->7->1

 What is the runtime complexity of your solution?

 */

public class AddTwoNumbers {
	// O(M+N) time, where M, N are the size of the two linked lists

	public class Node {
		public int value;
		public Node next;

		public Node(int value) {
			this.value = value;
		}
	}

	// reverse a linked list
	public Node reverseList(Node head) {

		Node prev = null;
		Node current = head;

		while (current != null) {
			Node next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}

		return prev;
	}

	public Node addTwoNumbers(Node first, Node second) {
		// Because we want to add the numbers from the lowest bit first, we need
		// to reverse the list
		Node l1 = reverseList(first);
		Node l2 = reverseList(second);

		Node dummy = new Node(0);
		Node current = dummy;

		int carry = 0;

		while (l1 != null || l2 != null) {
			if (l1 == null) {
				carry = l2.value + carry;
				l2 = l2.next;
			} else if (l2 == null) {
				carry = l1.value + carry;
				l1 = l1.next;
			} else {
				// if none of them are null, we just need to sum them together
				// with the carry-over
				carry = l1.value + l2.value + carry;
				l1 = l1.next;
				l2 = l2.next;
			}
			current.next = new Node(carry % 10);
			current = current.next;
			carry = carry / 10;
		}

		if (carry == 1) {
			current.next = new Node(carry);
		}

		return reverseList(dummy.next);
	}

}
