package Common;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Contains the logic to map and obtain a shorten url.
 */
public class IDtoURLMapper {
	
	
	private IDtoURLMapper() {
		initCharToIndexMap();
		initIndexToCharMap();
	}
	
	//used for the conversion of 10 base to 62 base
	private static Map<Character, Integer> charToIndexMap;
	private static Map<Integer, Character> indexToCharMap;
	
	
	private void initCharToIndexMap() {

		charToIndexMap = new HashMap<>();
		/*
		 * ASCII MAPPING
		 *  0 - 10 : 48 - 57
		 *  a - z  : 97 - 122
		 *  A - Z  : 65 - 90
		 */
		/*
		 * CHAR TO INDEX MAPING
		 * a - z : 0 - 25
		 * A - Z : 26 - 51
		 * 0 - 9 : 52 - 61
		 */
		char c = 'a';
		for(int i=0; i<26; i++) {
			c += i;
			charToIndexMap.put(c, i);
		}
		
		c = 'A';
		for(int i=26; i<52; i++) {
			c += (i - 26);
			charToIndexMap.put(c, i);
		}
		
		c = '0';
		for(int i=52; i<62; i++) {
			c += (i - 52);
			charToIndexMap.put(c, i);
		}
	}

	private void initIndexToCharMap() {
		indexToCharMap = new HashMap<>();
		char c = 'a';
		for(int i=0; i<26; i++) {
			c += i;
			indexToCharMap.put(i, c);
		}
		
		c = 'A';
		for(int i=26; i<52; i++) {
			c += (i - 26);
			indexToCharMap.put(i, c);
		}
		
		c = '0';
		for(int i=52; i<62; i++) {
			c += (i - 52);
			indexToCharMap.put(i, c);
		}
	}
	
	// MEMBER FUNCIONS 
	
	/*
	 * CREATE A NEW ID / SHORT URL
	 */
	public static String createShortUrlID(Long id) {
		List<Integer> base62MidWay = base10To62(id);
		
		StringBuilder string = new StringBuilder();
		for (int digit : base62MidWay) {
			string.append(indexToCharMap.get(digit));
		}
		return null;
	}
	
	public static Long getKeyFromShortUrlID(String urlId) {
		
		List<Character> base62ShortUrlId = new ArrayList<>();
		
		for(int i=0; i<urlId.length();i++) {
			base62ShortUrlId.add(urlId.charAt(i));
		}
		
		Long key = base62to10(base62ShortUrlId);
		
		return key;
	}

	
	//UTILITY METHODS
	private static Long base62to10(List<Character> UIDChar) {
		Long id = 0L;
		int exponent = UIDChar.size() - 1;
		
		for(Character character : UIDChar) {
			id += ((long)Math.pow(62,exponent) + charToIndexMap.get(character)); 
		}
		
		return id;
	}
	
	private static List<Integer> base10To62(Long id){
		List<Integer> digit = new ArrayList<>();
		
		while (id > 0) {
			digit.add((int)(id % 62));
			id /= 62;
		}
		return digit;
	}
	
}
