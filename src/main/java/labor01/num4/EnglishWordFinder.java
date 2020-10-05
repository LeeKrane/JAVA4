package labor01.num4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class EnglishWordFinder {
	private static final int PATTERN_LENGTH = 9;
	
	public static void main (String[] args) {
		Map<Integer, Character> letters = new TreeMap<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(EnglishWordFinder.class.getResourceAsStream("/labor01/words.txt")))) {
			Set<String> words = reader.lines()
					.map(String::toUpperCase)
					.filter(word -> word.length() == PATTERN_LENGTH && word.chars().distinct().count() == PATTERN_LENGTH)
					.collect(Collectors.toSet());
			
			for (String word : words) {
				letters.clear();
				
				for (int i = 0; i < word.length(); i++)
					letters.put(i + 1, word.charAt(i));
				
				final String word2 = fetchWord(new int[] {2, 3, 1, 4, 5, 6, 7, 8, 9}, letters);
				final String word3 = fetchWord(new int[] {8, 9, 3, 1, 2, 4, 5, 6, 7}, letters);
				
				if (words.contains(word2) && words.contains(word3))
					System.out.println(word + '\n' + word2 + '\n' + word3 + '\n');
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private static String fetchWord (int[] pattern, Map<Integer, Character> letters) {
		StringBuilder builder = new StringBuilder();
		for (int i : pattern)
			builder.append(letters.get(i));
		return builder.toString();
	}
}
