package airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class NestedListIterator {
	private final List<List<Integer>> nestedList; // The 2D nested list of
													// integers
	private int outerIndex; // Tracks the current row (list) in the 2D list
	private int innerIndex; // Tracks the current column (element) in the
							// current list (row)
	private int lastOuterIndex; // Tracks the row of the last accessed element
	private int lastInnerIndex; // Tracks the column of the last accessed
								// element
	private boolean lastDeleted; // Tracks if the last element has been deleted
									// or not

	public NestedListIterator(List<List<Integer>> nestedList) {
		this.nestedList = nestedList;
		this.outerIndex = 0; // Start at the first list (row)
		this.innerIndex = 0; // Start at the first element in the first list
								// (column)
		this.lastOuterIndex = -1; // Initialize as invalid, since no next() has
									// been called yet
		this.lastInnerIndex = -1; // Initialize as invalid, since no next() has
									// been called yet
		this.lastDeleted = false; // To track if the last returned element was
									// deleted
		moveToNextAvailable(); // Move to the first available element
	}

	// Move to the next available element if the current position is invalid
	private void moveToNextAvailable() {
		// Skip any empty rows (lists) until we find a list that has elements
		while (outerIndex < nestedList.size()
				&& innerIndex >= nestedList.get(outerIndex).size()) {
			outerIndex++;
			innerIndex = 0; // Reset innerIndex to the start of the next list
		}
	}

	// Check if there are more elements available
	public boolean hasNext() {
		return outerIndex < nestedList.size(); // If outerIndex is within
												// bounds, more elements exist
	}

	// Return the next element in the iteration
	public Integer next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		// Fetch the current element at nestedList[outerIndex][innerIndex]
		int value = nestedList.get(outerIndex).get(innerIndex);

		// Save the current indices to track the last accessed element
		lastOuterIndex = outerIndex;
		lastInnerIndex = innerIndex;

		// Move innerIndex to the next element in the current list
		innerIndex++;
		moveToNextAvailable(); // Move to the next available element if the
								// current list is exhausted

		lastDeleted = false; // Mark that the element was accessed, and not yet
								// deleted
		return value;
	}

	// Remove the last element returned by next() in place
	public void delete() {
		if (lastDeleted || lastOuterIndex == -1) {
			throw new IllegalStateException(
					"Cannot delete before calling next() or after deletion.");
		}

		// Remove the element at nestedList[lastOuterIndex][lastInnerIndex]
		nestedList.get(lastOuterIndex).remove(lastInnerIndex);

		// Adjust the innerIndex to correctly point to the current element's
		// place after removal
		innerIndex = lastInnerIndex;

		// If the list becomes empty after deletion, remove the entire list
		if (nestedList.get(lastOuterIndex).isEmpty()) {
			nestedList.remove(lastOuterIndex);
			outerIndex = Math.min(outerIndex, nestedList.size());
		}

		// Mark that the last element has been deleted
		lastDeleted = true;

		// Re-adjust to find the next available element after deletion
		moveToNextAvailable();
	}

	public static void main(String[] args) {
		List<List<Integer>> nestedList = new ArrayList<>();
		nestedList.add(new ArrayList<>(Arrays.asList(1, 2)));
		nestedList.add(new ArrayList<>(Arrays.asList(3)));
		nestedList.add(new ArrayList<>(Arrays.asList(4, 5, 6)));

		NestedListIterator iterator = new NestedListIterator(nestedList);

		System.out.println(iterator.next()); // 1
		System.out.println(iterator.next()); // 2
		iterator.delete(); // Removes 2
		System.out.println(iterator.next()); // 3
		iterator.delete(); // Removes 3
		System.out.println(iterator.next()); // 4
		iterator.delete(); // Removes 4
		System.out.println(iterator.hasNext()); // true
		System.out.println(iterator.next()); // 5
		iterator.delete(); // Removes 5
		System.out.println(iterator.hasNext()); // true
		System.out.println(iterator.next()); // 6
		iterator.delete(); // Removes 6
		System.out.println(iterator.hasNext()); // false
	}
}
