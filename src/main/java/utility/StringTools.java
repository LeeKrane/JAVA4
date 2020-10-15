package utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringTools {
	public static String anagram (String word, int[] pattern) {
		StringBuilder builder = new StringBuilder();
		for (int i : pattern)
			builder.append(word.charAt(i-1));
		return builder.toString();
	}
	
	public static Set<String> readWords (String filename, int length, boolean distinct) throws IOException {
		try (Stream<String> words = Files.lines(Paths.get("src", "main", "resources", "labor01", filename))) {
			return words.filter(word -> word.length() == length)
					.map(String::toUpperCase)
					.filter(word -> !distinct || word.chars().distinct().count() == length)
					.collect(Collectors.toSet());
		}
	}
}
