package airbnb;

import java.util.HashMap;
import java.util.Map;

public class KeywordHighlighter {
	// Trie Node class
	static class TrieNode {
		Map<Character, TrieNode> children = new HashMap<>();
		String category;
		boolean isEndOfWord;

		public TrieNode() {
		}
	}

	private final TrieNode root;

	public KeywordHighlighter(Map<String, String> keywordMap) {
		root = new TrieNode();
		for (Map.Entry<String, String> entry : keywordMap.entrySet()) {
			insert(entry.getKey().toLowerCase(), entry.getValue());
		}
	}

	// Insert keyword into trie
	private void insert(String word, String category) {
		TrieNode node = root;
		for (char ch : word.toCharArray()) {
			ch = Character.toLowerCase(ch);
			node = node.children.computeIfAbsent(ch, k -> new TrieNode());
		}
		node.isEndOfWord = true;
		node.category = category;
	}

	// Highlight keywords in text
	public String highlight(String text) {
		int n = text.length();
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < n) {
			TrieNode node = root;
			int maxLen = -1;
			String maxCategory = null;
			int j = i;
			while (j < n) {
				char ch = Character.toLowerCase(text.charAt(j));
				if (!node.children.containsKey(ch)) {
					break;
				}
				node = node.children.get(ch);
				if (node.isEndOfWord) {
					maxLen = j - i + 1;
					maxCategory = node.category;
				}
				j++;
			}
			if (maxLen != -1) {
				sb.append("[").append(maxCategory).append("]{");
				sb.append(text.substring(i, i + maxLen));
				sb.append("}");
				i += maxLen;
			} else {
				sb.append(text.charAt(i));
				i++;
			}
		}
		return sb.toString();
	}

	// Main method with test cases
	public static void main(String[] args) {
		Map<String, String> keywordMap = new HashMap<>();
		keywordMap.put("Mountain View", "city");
		keywordMap.put("Google", "company");
		keywordMap.put("Mountain", "nature");

		String text = "We visited google office in Mountain View on Feb 30th.";

		KeywordHighlighter highlighter = new KeywordHighlighter(keywordMap);
		String result = highlighter.highlight(text);

		System.out.println(result);
		// Expected Output:
		// We visited [company]{google} office in [city]{Mountain View} on Feb
		// 30th.

		// Additional Test Case
		Map<String, String> keywordMap2 = new HashMap<>();
		keywordMap2.put("Amazon", "company");
		keywordMap2.put("Amazon River", "nature");
		keywordMap2.put("South America", "continent");

		String text2 = "The amazon river flows through South America.";

		KeywordHighlighter highlighter2 = new KeywordHighlighter(keywordMap2);
		String result2 = highlighter2.highlight(text2);

		System.out.println(result2);
		// Expected Output:
		// The [nature]{amazon river} flows through [continent]{South America}.
	}
}
