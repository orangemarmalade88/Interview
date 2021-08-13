package booking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HotelReviews {
	public static List<Integer> awardTopKHotels(String positiveKeywords,
			String negativeKeywords, List<Integer> hotelIds,
			List<String> reviews, int k) {
		// Write your code here
		String[] positives = positiveKeywords.split(" ");
		Set<String> pos = new HashSet<>();
		for (String s : positives) {
			pos.add(s.toLowerCase());
		}

		String[] negatives = negativeKeywords.split(" ");
		Set<String> neg = new HashSet<>();
		for (String s : negatives) {
			neg.add(s.toLowerCase());
		}

		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < hotelIds.size(); i++) {
			int id = hotelIds.get(i);
			String[] words = reviews.get(i).split("[,. ]");

			for (String s : words) {
				if (pos.contains(s.toLowerCase()))
					map.put(id, map.getOrDefault(id, 0) + 3);

				if (neg.contains(s.toLowerCase()))
					map.put(id, map.getOrDefault(id, 0) - 1);
			}
		}

		List<Map.Entry<Integer, Integer>> list = new ArrayList<>(
				map.entrySet());
		Collections.sort(list,
				(a, b) -> (a.getValue() == b.getValue()
						? a.getKey() - b.getKey()
						: b.getValue() - a.getValue()));

		List<Integer> res = new ArrayList<>();
		int i = 0;
		while (i < k && i < list.size()) {
			res.add(list.get(i).getKey());
			i++;
		}

		return res;

	}
}
