import java.util.HashMap;
import java.util.Map;

public class CharactersInString {
	boolean charsInString(char[] chars, String s) {
		Map<Character, Integer> counts = new HashMap<>();

		for (char c : s.toCharArray()) {
			if (counts.containsKey(c)) {
				counts.put(c, counts.get(c) + 1);
			} else
				counts.put(c, 1);
		}

		for (char c : chars) {
			if (!counts.containsKey(c))
				return false;
			int count = counts.get(c);
			if (count == 0)
				return false;
			counts.put(c, count - 1);
		}
		return true;

	}

	public static void main(String[] args) {
		CharactersInString main = new CharactersInString();
		System.out.println(main.charsInString(
				new char[] { 't', 'a', 'c', 't' }, "attach"));
		System.out.println(main.charsInString(
				new char[] { 't', 'a', 'c', 't' }, "atttach"));

		System.out.println(main.charsInString(
				new char[] { 't', 'a', 'c', 't' }, "atach"));

	}

}
