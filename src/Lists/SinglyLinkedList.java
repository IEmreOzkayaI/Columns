package Lists;

import Lists.Nodes.SingleNode;

public class SinglyLinkedList {
	private SingleNode head;

	

	static SingleNode removeFirstNode(SingleNode head)
    {
        if (head == null)
            return null;
 
        // Move the head pointer to the next node
        SingleNode temp = head;
        head = head.getLink();
 
        return head;
    }
	
	public SingleNode setHead(Object data, SingleNode link) {
		
		SingleNode head = new SingleNode(data);
		head.setLink(head);
		return head;
		
	}
	
	public SingleNode setNode(Object data, SingleNode link) {
		SingleNode node = new SingleNode(data);
		node.setLink(link);
		return node;
	}

	public SingleNode getHead() {
		return head;
	}
	
	void pop_front() {
		  if(this.head != null) {
		    
		    //1. if head is not null, create a
		    //   temp node pointing to head
			  SingleNode temp = this.head;
		    
		    //2. move head to next of head
		    this.head = this.head.getLink();
		    
		    //3. delete temp node
		    temp = null;  
		  }
		}

	public void Add(Object data) {
		if (head == null) {
			SingleNode newNode = new SingleNode(data);
			head = newNode;
		} else {
			SingleNode temp = head;
			while (temp.getLink() != null) {
				temp = temp.getLink();
			}
			SingleNode newNode = new SingleNode(data);
			temp.setLink(newNode);
		}
	}

	public int size() {
		if (head == null)
			return 0;
		else {
			int count = 0;

			SingleNode temp = head;

			while (temp != null) {
				temp = temp.getLink();
				count++;
			}

			return count;
		}
	}

	public void display() {
		if (head == null)
			System.out.println("List is Empty!");
		else {
			SingleNode temp = head;

			while (temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			}
			System.out.println();
		}
	}

	public void remove(Object dataToDelete) {
		if (head == null)
			System.out.println("Linked list is empty");
		else {
			while ((Integer) head.getData() == (Integer) dataToDelete)
				head = head.getLink();

			SingleNode temp = head;
			SingleNode previous = null;
			while (temp != null) {
				if ((Integer) temp.getData() == (Integer) dataToDelete) {
					previous.setLink(temp.getLink());
					temp = previous;
				}
				previous = temp;
				temp = temp.getLink();
			}
		}
	}

	public int findMax() {
		if (head == null) {
			System.err.println("Linked List is empty");
			return Integer.MIN_VALUE;
		} else {
			int maxVal = Integer.MIN_VALUE;
			SingleNode temp = head;

			while (temp != null) {
				if ((int) temp.getData() > maxVal) {
					maxVal = (int) temp.getData();
				}
				temp = temp.getLink();
			}
			return maxVal;
		}
	}

	public boolean search(Object data) {
		if (head == null) {
			System.out.println("List is empty");
			return false;
		} else {
			SingleNode temp = head;
			while (temp != null) {
				if ((Integer) temp.getData() == (Integer) data)
					return true;
				temp = temp.getLink();
			}
			return false;
		}
	}

	public boolean equality() {
		if (head == null) {
			System.out.println("List is empty!");
			return true;
		} else {
			int firstHalf = 0;
			int secondHalf = 0;
			int count = size();
			if (count % 2 != 0) {
				System.out.println("The element count should be even");
				return false;
			} else {
				SingleNode temp = head;
				for (int i = 0; i < count; i++) {
					if (i > count / 2)
						firstHalf += Integer.parseInt(temp.getData().toString());
					else
						secondHalf += Integer.parseInt(temp.getData().toString());
					temp = temp.getLink();
				}
			}
			if (firstHalf == secondHalf)
				return true;
			else
				return false;
		}

	}

}