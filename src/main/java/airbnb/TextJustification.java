package airbnb;

import java.util.ArrayList;
import java.util.List;

public class TextJustification {

	public static List<String> fullJustify(String[] words, int maxWidth) {
		List<String> result = new ArrayList<>();
		int index = 0;

		while (index < words.length) {
			int totalChars = words[index].length();
			int last = index + 1;

			// Find the range of words that can fit in the current line
			while (last < words.length) {
				if (totalChars + 1 + words[last].length() > maxWidth)
					break;
				totalChars += 1 + words[last].length();
				last++;
			}

			// Create a new line with justified text
			StringBuilder builder = new StringBuilder();
			int numberOfWords = last - index - 1;

			// If this is the last line or there is only one word in the line,
			// left-justify
			if (last == words.length || numberOfWords == 0) {
				for (int i = index; i < last; i++) {
					builder.append(words[i]);
					if (i < last - 1)
						builder.append(" ");
				}
				for (int i = builder.length(); i < maxWidth; i++) {
					builder.append(" ");
				}
			} else {
				// Distribute spaces evenly
				int spaces = (maxWidth - totalChars) / numberOfWords;
				int extraSpaces = (maxWidth - totalChars) % numberOfWords;

				for (int i = index; i < last - 1; i++) {
					builder.append(words[i]);
					builder.append(" ");
					for (int j = 0; j < spaces
							+ (i - index < extraSpaces ? 1 : 0); j++) {
						builder.append(" ");
					}
				}
				builder.append(words[last - 1]);
			}

			result.add(builder.toString());
			index = last;
		}

		return result;
	}

	public static void main(String[] args) {
		String[] words = { "This", "is", "an", "example", "of", "text",
				"justification." };
		int maxWidth = 16;
		List<String> justifiedText = fullJustify(words, maxWidth);

		for (String line : justifiedText) {
			System.out.println("\"" + line + "\"");
		}
	}
}
