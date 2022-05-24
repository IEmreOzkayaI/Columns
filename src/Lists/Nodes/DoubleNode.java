package Lists.Nodes;


public class DoubleNode {
		Object data;
		private DoubleNode prev;
		private DoubleNode next;

		public DoubleNode(Object data) {
			this.data = data;
			prev = null;
			next = null;
		}
		
		

		



		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public DoubleNode getPrev() {
			return prev;
		}

		public void setPrev(DoubleNode previous) {
			this.prev = previous;
		}

		public DoubleNode getNext() {
			return next;
		}

		public void setNext(DoubleNode next) {
			this.next = next;
		}

	}