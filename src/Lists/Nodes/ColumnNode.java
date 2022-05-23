package Lists.Nodes;

public class ColumnNode {

	private Object columnName;
	private ColumnNode right;
	private NumNode down;

	public ColumnNode(Object columnName) {
		this.columnName = columnName;
		right = null;
		down = null;
	}

	public Object getColumnName() {
		return columnName;
	}

	public void setColumnName(Object columnName) {
		this.columnName = columnName;
	}

	public ColumnNode getRight() {
		return right;
	}

	public void setRight(ColumnNode right) {
		this.right = right;
	}

	public NumNode getDown() {
		return down;
	}

	public void setDown(NumNode down) {
		this.down = down;
	}

}
