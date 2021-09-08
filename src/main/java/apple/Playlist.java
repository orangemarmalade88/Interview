package apple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Playlist {

	/**
	 * Implement a playlist generator, that can fetch user preference which has
	 * weighted language preference and weighted genre preference.
	 *
	 * Playlist created must have - Exact number of songs user requested - Songs
	 * must be distributed across languages and generes from user preference
	 * based on the weights
	 */

	// index 0
	// prev_index 0
	// map: 0 en 0.5 es

	// weight = 0.3
	// submap: 0 en

	class PlaylistGenerator {
		public List<Song> generatePlaylist(String userId, int numSongs) {
			List<Song> res = new ArrayList<>();
			Preference p = ps.getUserPreference(userId);
			double index = 0.0;
			TreeMap<Double, String> map = new TreeMap<>();
			for (String l : p.languages.keySet()) {
				double weight = p.languages.get(l);
				map.put(index, l);
				index += weight;

			}

			index = 0.0;
			double prev_index = 0.0;
			for (String g : p.generes.keySet()) {
				double weight = p.generes.get(g);
				SortedMap<Double, String> m = map.subMap(index, prev_index);
				double sub_index = prev_index;
				for (Map.Entry<Double, String> e : m.entrySet()) {
					int num = (int) ((e.getKey() - sub_index) * numSongs);
					sub_index = e.getKey();
					List<Song> songs = ss.fetch(e.getValue(), g, num);
					res.addAll(songs);
				}
				prev_index = index;
				index += weight;
			}

			// ToDo: implement here
			return res;
		}

		PreferenceService ps;
		SongStore ss;
	}

	class Song {
		private String title;
		private int durationSecs;
	}

	class Preference {

		// weight will be 0 to 1
		// [{"en-us": 0.5, "es-ex": 0.5}]
		public Map<String, Double> languages; // 1
		// pop - 30% rock 30% country 40%
		public Map<String, Double> generes; // 1

	}

	interface SongStore {
		List<Song> fetch(final String language, String genre, int numSongs); //
	}

	interface PreferenceService {
		Preference getUserPreference(String userId);
	}

}
