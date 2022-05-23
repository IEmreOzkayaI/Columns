package Lists.Nodes;
public class NumNode {
	private Object number;
	private NumNode next;

	public NumNode(Object number) {
		this.number = number;
		next = null;
	}

	public Object getNumber() {
		return number;
	}

	public void setNumber(Object number) {
		this.number = number;
	}

	public NumNode getNext() {
		return next;
	}

	public void setNext(NumNode next) {
		this.next = next;
	}

}
