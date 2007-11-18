package leokom.utils;

/**
 * Represent "map" of chars and digits.
 */
public interface DigitsOrderedMap {
//	/**
//	 * Add (char->digit) pair to the map (change if exists).
//	 * @param character - any character
//	 * @param digit - any digit (0-9)
//	 */
//	void put( char character, byte digit );
	
	/**
	 * Put character to the map, digit value is undefined.
	 * @param character character
	 */
	void put( char character );
	
	/**
	 * Get current digit corresponding to the character.
	 * @param character char
	 * @return digit
	 */
	byte get( char character );
	
	/**
	 * This method is used for allowing new put methods.
	 * gotoNextSequence will get the first sequence with new values (if present)
	 */
	void reInitSequence();
	
	/**
	 * Goto next "rozmishennia bez povtoren'" of the digits.
	 * It disables calling put
	 * @return true if next sequence exists - else false
	 */
	boolean gotoNextSequence();
	
}
