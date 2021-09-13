package google;

import java.util.HashMap;
import java.util.Map;

public class ConvertEmoji {

	/*
	 *
	 * "Hi" -> "Hi" "Hi :)" -> "Hi \u263a" "Hi:)" -> "Hi\u263a"
	 *
	 *
	 *
	 * You’re working on a chat server that recognizes shortcuts for emoji such
	 * as :) or :-) for ☺. Write a method that takes a String containing the
	 * message the user entered and replaces emoji shortcuts with Unicode
	 * equivalents. For example, ":)" would be replaced with "\u263A" (☺). You
	 * can assume that emoji shortcuts and their unicode equivalents are already
	 * loaded into whatever data structure you would like.
	 *
	 * =D:) → 😄☺
	 *
	 * Hi :(:)
	 *
	 * Hi :(:) abc → abc
	 */
	public class TrieNode {
		private HashMap<Character, TrieNode> children;
		private String content;
		private boolean isWord;
	}

	boolean startsWith(String input, TrieNode root) {
		int index = 0;
		while (index < input.length()) {
			if (!root.children.containsKey(input.charAt(index)))
				return false;
			root = root.children.get(input.charAt(index));
			index++;
		}
		return true;
	}

	boolean endsWith(String input, TrieNode root) {
		int index = 0;
		while (index < input.length()) {
			if (!root.children.containsKey(input.charAt(index)))
				return false;
			root = root.children.get(input.charAt(index));
			index++;
		}
		return root.isWord;
	}

	String convertEmoji(String input, TrieNode root, Map<String, String> map) {
		StringBuilder res = new StringBuilder();
		int left = 0;
		int right = 0;
		while (left < input.length()) {
			StringBuilder sb = new StringBuilder();
			sb.append(input.charAt(right++));
			String longestMatch = "";
			while (startsWith(sb.toString(), root)) {
				if (endsWith(sb.toString(), root)) {
					longestMatch = sb.toString();
				}
				sb.append(input.charAt(right++));
			}
			if (longestMatch.isEmpty()) {
				res.append(input.charAt(left));
				left++;
				right = left;
			} else {
				res.append(map.get(longestMatch));
				right--;
				left = right;
			}

		}
		return res.toString();
	}
}
