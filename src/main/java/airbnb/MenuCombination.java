package airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuCombination {

	// Cache for memoization
	private static Map<List<String>, Double> memo = new HashMap<>();

	// Main function to find minimum cost
	public static double minCost(List<String[]> menu, List<String> wishlist) {
		return dfs(menu, wishlist);
	}

	// Helper function for DFS with memoization
	private static double dfs(List<String[]> menu, List<String> wishlist) {
		if (wishlist.isEmpty())
			return 0; // If wishlist is empty, cost is 0
		if (memo.containsKey(wishlist))
			return memo.get(wishlist); // Check cache

		double minCost = Double.MAX_VALUE; // Set initial high value for
											// comparison

		for (String[] mealSet : menu) {
			String[] items = mealSet[1].split(",");
			double price = Double.parseDouble(mealSet[0]);

			// Find remaining wishlist after taking this meal set
			List<String> remainingWishlist = new ArrayList<>(wishlist);
			for (String item : items) {
				remainingWishlist.remove(item);
			}

			if (remainingWishlist.size() < wishlist.size()) {
				// Recursive call with remaining wishlist
				double cost = price + dfs(menu, remainingWishlist);
				minCost = Math.min(minCost, cost); // Update minimum cost
			}
		}

		// Memoize the result
		memo.put(wishlist, minCost);
		return minCost;
	}

	public static void main(String[] args) {
		List<String[]> menu = Arrays.asList(new String[] { "5.00", "pizza" },
				new String[] { "8.00", "sandwich,coke" },
				new String[] { "4.00", "pasta" },
				new String[] { "2.00", "coke" },
				new String[] { "6.00", "pasta,coke,pizza" },
				new String[] { "8.00", "burger,coke,pizza" },
				new String[] { "5.00", "sandwich" });

		List<String> wishlist = Arrays.asList("burger", "pasta");

		System.out.println("Minimum Cost: " + minCost(menu, wishlist));
	}
}