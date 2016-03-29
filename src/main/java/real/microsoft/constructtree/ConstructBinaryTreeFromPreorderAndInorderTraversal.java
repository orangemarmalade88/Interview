package real.microsoft.constructtree;

/*

 Given an in order and pre order traversal of binary tree. It is guaranteed that there
 are no duplicate elements present in the binary tree. Reconstruct the binary
 tree using the following signature:

 */

public class ConstructBinaryTreeFromPreorderAndInorderTraversal {

	// O(N) time, where N is the size of the binary tree

	public class TreeNode {

		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int val) {
			this.val = val;
		}
	}

	public TreeNode buildTree(int[] preOrderTraversal, int[] inOrderTraversal) {
		// error checking
		if (preOrderTraversal.length != inOrderTraversal.length)
			return null;

		return buildTree(preOrderTraversal, 0, inOrderTraversal, 0,
				inOrderTraversal.length - 1);
	}

	public TreeNode buildTree(int[] preOrderTraversal, int preBegin,
			int[] inOrderTraversal, int inBegin, int inEnd) {
		if (preBegin > preOrderTraversal.length - 1 || inBegin > inEnd)
			return null;

		// first node in pre-order is the root
		TreeNode root = new TreeNode(preOrderTraversal[preBegin]);
		// find the inorder index of the root,
		// then now elements [instart, ..., inIndex) is the left subtree
		// (inIndex ... inEnd] is the right subtree
		int inIndex = 0;
		for (int i = inBegin; i <= inEnd; i++) {
			if (inOrderTraversal[i] == root.val) {
				inIndex = i;
				break;
			}
		}

		// recursively build left subtree and right subtree
		root.left = buildTree(preOrderTraversal, preBegin + 1,
				inOrderTraversal, inBegin, inIndex - 1);
		root.right = buildTree(preOrderTraversal, preBegin + inIndex - inBegin
				+ 1, inOrderTraversal, inIndex + 1, inEnd);
		return root;
	}
}
