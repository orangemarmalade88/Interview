package newsbreak;

import java.util.Set;

public class NewsBreakCoding {

	///            1
	//      2              3
	//  4      5
	// 6 7
	//

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p,
			TreeNode q) {
		if (root == null)
			return null;
		if (root == p || root == q)
			return root;
		TreeNode left = lowestCommonAncestor(root.left, p, q);
		TreeNode right = lowestCommonAncestor(root.right, p, q);
		if (left != null && right != null)
			return root;
		if (left != null)
			return left;
		return right;
	}

	TreeNode lca = null;

	public TreeNode lowestCommonAncestor(TreeNode root, Set<TreeNode> nodes) {
		helper(root, nodes);
		return lca;
	}

	public int helper(TreeNode root, Set<TreeNode> nodes) {
		if (root == null)
			return 0;
		int left = helper(root.left, nodes);
		int right = helper(root.right, nodes);
		int found = left + right;
		if (nodes.contains(root))
			found++;
		if (found == nodes.size() && lca == null)
			lca = root;
		return found;
	}
}
