public class TreeNode {

	WordFreq data;
	TreeNode left, right;

	public TreeNode(WordFreq data) {
		this.data = data;
	}

	public WordFreq getData() {
		return data;
	}

	public void setData(WordFreq data) {
		this.data = data;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "Node: " + data; 
	}
	
} //end Node
