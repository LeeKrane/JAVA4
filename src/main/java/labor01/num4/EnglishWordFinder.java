package labor01.num4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnglishWordFinder {
	public static void main (String[] args) {
		Map<Integer, Character> letters = new TreeMap<>();
		Set<String> words = getWordStream().collect(Collectors.toSet());
		
		getWordStream().forEach(word -> {
			letters.clear();
			
			for (int i = 0; i < word.length(); i++) {
				letters.put(i + 1, word.charAt(i));
			}
			
			final String word2 = fetchWord(new int[]{2, 3, 1, 4, 5, 6, 7, 8, 9}, letters);
			final String word3 = fetchWord(new int[]{8, 9, 3, 1, 2, 4, 5, 6, 7}, letters);
			
			if (words.contains(word) && words.contains(word2) && words.contains(word3)) {
				System.out.println(word + '\n' + word2 + '\n' + word3 + '\n');
			}
		});
	}
	
	private static String fetchWord (int[] pattern, Map<Integer, Character> letters) {
		StringBuilder builder = new StringBuilder();
		for (int i : pattern) {
			builder.append(letters.get(i));
		}
		return builder.toString();
	}
	
	private static Stream<String> getWordStream () {
		BufferedReader reader = new BufferedReader(new InputStreamReader(EnglishWordFinder.class.getResourceAsStream("/labor01/words.txt")));
		return reader.lines()
				.map(word -> word.toUpperCase())
				.filter(word -> word.length() == 9 && word.chars().distinct().count() == 9);
	}
}
