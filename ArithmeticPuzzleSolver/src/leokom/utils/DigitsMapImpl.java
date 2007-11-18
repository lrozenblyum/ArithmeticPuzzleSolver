package leokom.utils;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Class will represent "map" of chars and digits.
 */
public class DigitsMapImpl implements DigitsOrderedMap{
	
	
	private static void initEtalonDigits() {
		etalonDigits.clear();
		for ( byte digit = MINIMAL_DIGIT;
			digit <= MAXIMAL_DIGIT; digit++ ) {
			etalonDigits.add( digit );
		}
	}
	public DigitsMapImpl() {
		initEtalonDigits();
	}


	private static boolean isDigitValid( byte digit ) {
		return ( digit >= MINIMAL_DIGIT && digit <= MAXIMAL_DIGIT );
	}
	
	
	private static final int NUMBER_SYSTEM_MULTIPLICATOR = 10; //for decimal
	
	private static final byte MINIMAL_DIGIT = 0;
	private static final byte MAXIMAL_DIGIT = NUMBER_SYSTEM_MULTIPLICATOR - 1;
	
	private boolean putIsAllowed = true;
	
	private int allocationsCount = -1;
	private int allocationIndex = -1; //index of current allocation
	
	private ArrayList<Character> chars = new ArrayList<Character>();//[ START_ARRAY_LENGTH ]; 
	private ArrayList<Byte> digits = new ArrayList<Byte>();//[ START_ARRAY_LENGTH ];
	
	
	//default (first) allocation
	private static final ArrayList<Byte> etalonDigits = new ArrayList<Byte>();
	
	/**
	 * Gets index of char if present else -1
	 * @return index of char or -1
	 */
	private int findCharInArray( char characterToFind ) {
		int charsCount = chars.size();
		for ( int charIndex = 0; charIndex < charsCount; charIndex++ ) {
			if ( characterToFind == chars.get( charIndex ) )
				return charIndex;
		}
		return -1;
	}
	
	/**
	 * add not-existing char-digit pair to arrays.
	 * @param character
	 * @param digit
	 */
	private void addNewCharDigitPair( char character, byte digit ) {
		//TODO: maybe improve this strategy
		
		chars.add( character );
		digits.add( digit );
	}
	
	private void put(char character, byte digit) {
		if ( !putIsAllowed ) {
			throw new IllegalStateException( "Put method call isn't allowed now. Use reinit if needed" );
		}
		if ( !isDigitValid( digit ) ) {
			throw new IllegalArgumentException( "Digit must be in 0..9 range" );
		}
		
		int charIndex = findCharInArray( character );
		boolean characterAlreadyPresent = ( charIndex != -1 );
		if ( characterAlreadyPresent )
			//set value in digits array - its memory is already allocated
			digits.set( charIndex, digit );
		else
			addNewCharDigitPair( character, digit );
		
	}
	
	public void put(char character) {
		put( character, (byte) 0 );
	}

	public void reInitSequence() {
		putIsAllowed = true;
		//TODO: check what new initializations are needed
//		for ( int digitIndex = 0; digitIndex < digits.length; digitIndex++ ) {
//			digits[ digitIndex  ] = 0;
//		}
//		
		allocationIndex = 0;
		
	    calculateAllocationsCount();
	}

	public byte get(char character) throws NoSuchElementException {
		int charIndex = findCharInArray( character );
	    boolean characterAlreadyPresent = ( charIndex != -1 );
		if ( characterAlreadyPresent ) {
			Byte digit = digits.get( charIndex );
			if ( digit == null )
				throw new IllegalStateException( "Digit with index: " + charIndex + " is undefined. This is unexpected behaviour. Corresponding character: " + chars.get( charIndex ) );
			return digit;
		}
		else //character is absent
			throw new NoSuchElementException( "Character: " + character + " is absent in char array" );
	}

	/**
	 * inits private variable that represents allocations count
	 */
	private void calculateAllocationsCount() {
	    allocationsCount = 1;
		final int totalDigitsCount = NUMBER_SYSTEM_MULTIPLICATOR;
		int digitsInArrayCount = digits.size();
		if ( totalDigitsCount < digitsInArrayCount )
			throw new IllegalStateException( "NUMBER_SYSTEM_MULTIPLICATOR constant is too small. It needs to be at least = " + digitsInArrayCount ) ;
	    for (
			int i = totalDigitsCount - digitsInArrayCount + 1;
			i <= totalDigitsCount;
			i++
		)
	        allocationsCount *= i;
	}
	
	public boolean gotoNextSequence() {
		boolean firstCallAfterReinit = putIsAllowed;
		
		if ( firstCallAfterReinit ) {
			reInitSequence();
		}
	    
	    //disallow putting new elements
	    putIsAllowed = false;
		
		//no new allocations will be found
		if ( allocationIndex >= allocationsCount )
			return false;
		
		
		
		//copy all default digits
		
		
		
		// next will implement getting new sequence (distribution) R(n,r) without repetitions
		// n - count of digits
		// r - count of places
	    initEtalonDigits();
		int goodDigitIndex, magicNumber;
		
		int n = etalonDigits.size(); // must be 10
		int r = digits.size();
		
		//allocationIndex (and therefore k) will produce unique distribution!
	    int k = allocationIndex;
		
	    for ( int j = 0; j < r; j++ ) {
		
			//can change for (j=n; j > n-r; j--) and avoid magic number
			magicNumber = n - j;
			
	        goodDigitIndex = k % magicNumber;
	        
			
			// digits[ j ] = etalonDigits[ l ];
	        digits.set( j, etalonDigits.get( goodDigitIndex ) );

			//shift etalon left on one position 			
	        for ( int f = goodDigitIndex; f < magicNumber - 1; f++ )
	            etalonDigits.set( f, etalonDigits.get( f + 1 ) );
				
	        k = k / magicNumber;    
	    }
	    allocationIndex++;
		return true;
		
	}	
	
	public static void main( String [] args ) {
		DigitsOrderedMap digitsMap = new DigitsMapImpl();
		
		digitsMap.put( 'a' );
		digitsMap.put( 'b' );
	    
		
		
		while ( digitsMap.gotoNextSequence() ) {
		    System.out.print(digitsMap.get( 'a' ));
		    System.out.println(digitsMap.get( 'b' ));

		}
		
		digitsMap.reInitSequence();
		digitsMap.put( 'q' );
		
	    while ( digitsMap.gotoNextSequence() ) {
	        System.out.print(digitsMap.get( 'a' ));
	        System.out.print(digitsMap.get( 'b' ));
	        System.out.println(digitsMap.get( 'q' ));
	    }
	    
	}

	
}
