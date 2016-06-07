public class BinarySearchTree {

	private class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		private TreeNode(int val) {
			this.val = val;
		}
	}

	public TreeNode insert(TreeNode root, int value) {
		if (root == null) {
			return new TreeNode(value);
		} else if (value < root.val) {
			root.left = insert(root.left, value);
		} else
			root.right = insert(root.right, value);
		return root;
	}

}
