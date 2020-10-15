package labor01.num4;

import java.io.IOException;
import java.util.Set;

import static utility.StringTools.anagram;
import static utility.StringTools.readWords;

public class EnglishWordFinder {
	public static void main (String[] args) {
		try {
			Set<String> words = readWords("words.txt", 9, true);
			
			for (String word : words) {
				final String word2 = anagram(word, new int[] {2, 3, 1, 4, 5, 6, 7, 8, 9});
				final String word3 = anagram(word, new int[] {8, 9, 3, 1, 2, 4, 5, 6, 7});
				
				if (words.contains(word2) && words.contains(word3))
					System.out.println(word + " - " + word2 + " - " + word3);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
