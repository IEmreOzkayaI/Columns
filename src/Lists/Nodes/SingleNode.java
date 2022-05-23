package Lists.Nodes;

public class SingleNode {

		Object data;
		SingleNode link;

		public SingleNode(Object data) {
			this.data = data;
			link = null;
		}

		public Object getData() {
			return data;
		}

		public SingleNode getLink() {
			return link;
		}

		public void setLink(SingleNode link) {
			this.link = link;
		}
		
		

	}