package Lists;

import Lists.Nodes.DoubleNode;

public class DoublyLinkedList {
	private DoubleNode head;
	private DoubleNode tail;

	public DoublyLinkedList() {
		head = null;
		tail = null;
	}



	public void add(Object dataToAdd) {
		if (head == null && tail == null) {
			DoubleNode newNode = new DoubleNode(dataToAdd);
			head = newNode; /* pointing the first node */
			tail = newNode; /* pointing the last node */
		} else {
			DoubleNode newnode = new DoubleNode(dataToAdd);
			newnode.setPrev(tail);
			tail.setNext(newnode);
			tail = newnode;

		}
	}
}
