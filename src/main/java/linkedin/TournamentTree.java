package linkedin;

public class TournamentTree {
	public class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int x) {
			val = x;
		}
	}

	/*
	 * Find the second smallest element in a tournament tree
	 */

	public int secondSmallestElement(TreeNode root) {

		if (root == null)
			return Integer.MAX_VALUE;
		TreeNode other;
		TreeNode min;
		if (root.left != null && root.left.val == root.val) {
			other = root.right;
			min = root.left;
		} else {
			other = root.left;
			min = root.right;
		}
		return Math.min(secondSmallestElement(min),
				other == null ? Integer.MAX_VALUE : other.val);
	}

	public static void main(String[] args) {
		TournamentTree tt = new TournamentTree();
		TreeNode root = tt.new TreeNode(1);

		TreeNode left = tt.new TreeNode(1);
		TreeNode right = tt.new TreeNode(3);

		root.left = left;
		root.right = right;

		TreeNode leftleft = tt.new TreeNode(1);
		TreeNode leftright = tt.new TreeNode(6);

		left.left = leftleft;
		left.right = leftright;

		TreeNode rightleft = tt.new TreeNode(4);
		TreeNode rightright = tt.new TreeNode(3);

		right.left = rightleft;
		right.right = rightright;

		System.out.println(tt.secondSmallestElement(root));
	}
}
