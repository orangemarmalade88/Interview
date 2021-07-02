package bytedance;

public class StringCombinations {
	//
	// 1 2 3 a b c
	// 12 3 12 c
	// 1 23 a 23

	// OrderService
	// - CreateOrder
	// - Query
	// - Cancel

	private int ways = 0;

	private void dfs(String num, int start_index) {
		if (start_index == num.length()) {
			ways++;
			return;
		}
		if (num.charAt(start_index) == '0')
			return;
		dfs(num, start_index + 1);
		if (start_index + 1 < num.length()) {
			String two_digit = num.substring(start_index, start_index + 2);
			int two = Integer.parseInt(two_digit);
			if (two <= 26) {
				dfs(num, start_index + 2);
			}
		}
	}

	public int combinations(String num) {
		dfs(num, 0);
		return ways;
	}

	public static void main(String[] args) {
		// Scanner in = new Scanner(System.in);
		// int a = in.nextInt();
		// System.out.println(a);
		StringCombinations s = new StringCombinations();
		int a = s.combinations("123");
		System.out.println(a);
	}
}
