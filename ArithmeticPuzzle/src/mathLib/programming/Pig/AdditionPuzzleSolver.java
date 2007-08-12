/**
 * This class should solve additive 2-argument arithmetic puzzles of kind:
 * 	SEND
 * +MORE
 * -----
 * MONEY
 */
package mathLib.programming.Pig;

import java.util.ArrayList;
import java.util.HashMap;

import leokom.utils.ConsoleStringReader;
import leokom.utils.DigitsMapImpl;
import leokom.utils.DigitsOrderedMap;

/**
 * This class should solve additive 2-argument arithmetic puzzles.
 * E.g.
 * 	SEND
 * +MORE
 * -----
 * MONEY
 */
public class

AdditionPuzzleSolver {

	private static final String FIRST_ADDENDUM = "first addendum";
	private static final String SECOND_ADDENDUM = "second addendum";
	private static final String SUM = "sum";
	private static final int NUMBER_SYSTEM_MULTIPLICATOR = 10; //for decimal
	/**
	 * Receive needed data from user (using keyboard)
	 * @return hash map of strings received from the user
	 */
	HashMap<String, String> getInputData() {
		HashMap result = new HashMap();
		System.out.println("Input first addendum");
		String firstAddendum = ConsoleStringReader.readString();
	    result.put( FIRST_ADDENDUM, firstAddendum );
		
		
		System.out.println( "Input second addendum" );
		String secondAddendum = ConsoleStringReader.readString();
		result.put( SECOND_ADDENDUM, secondAddendum );
		
		System.out.println( "Input sum" );
		String sum = ConsoleStringReader.readString();
		result.put( SUM, sum );
		 
		return result;
	}

	/**
	 * adds string into existing hashmap (e.g. "Hello" result: {{"H", ??},{"e",??},{"l",??},{"o", ??}} ).
	 * @param argument
	 * @return
	 */
	private static void addStringIntoDigitsMap(String argument, 
											DigitsOrderedMap digitsMap) {
		for (char character: argument.toCharArray()) {
			digitsMap.put(character);
		}
	}
	
	/**
	 * Parse valuable input arguments into pairs char -> digit 
	 * @param inputArguments
	 * @return
	 */
	private DigitsOrderedMap parseInputIntoDigits( HashMap inputArguments ) {
		DigitsOrderedMap digitsMap = new DigitsMapImpl();
		addStringIntoDigitsMap( (String) inputArguments.get( FIRST_ADDENDUM ), digitsMap );
	    addStringIntoDigitsMap( (String) inputArguments.get( SECOND_ADDENDUM ), digitsMap );
		addStringIntoDigitsMap( (String) inputArguments.get( SUM ), digitsMap );
		return digitsMap;
	}
	
	/**
	 * Fill in the string with real digits values, e.g.
	 * pattern = "foo", digitsMap = {'a'->0, 'f' -> 1, 'o' -> 2};
	 * result will be 122
	 * @param pattern - string "pattern"
	 * @param digitsMap - map with real digit values
	 * @return
	 */
	private static int fillInStringFromDigitsMap( String pattern, DigitsOrderedMap digitsMap ) {
		int result = 0;
		for ( char character: pattern.toCharArray() )	{
			result = result * NUMBER_SYSTEM_MULTIPLICATOR + digitsMap.get( character );
		}
		return result;
	}
	
	
	
	
	/**
	 * Solve the puzzle if possible.
	 * @param inputArguments - "string" values of arguments (e.g sum, addendums)
	 * @return hashMap with values - solved if possible - else null
	 */
	private ArrayList<HashMap<String, String>> solvePuzzle(HashMap inputArguments) {
	    ArrayList<HashMap<String, String>> solutions = new ArrayList<HashMap<String,String>>();
		// parse valuable input arguments into pairs char -> digit (of course digit is unknown ^-)
		DigitsOrderedMap digitsMap = parseInputIntoDigits( inputArguments );
		
	    //create first, second arg and the sum
	    String firstAddendumString = (String)inputArguments.get( FIRST_ADDENDUM );
	    String secondAddendumString = (String)inputArguments.get( SECOND_ADDENDUM );
	    String sumString = (String)inputArguments.get( SUM );
		
		int firstAddendumStringLength = firstAddendumString.length();
		int secondAddendumStringLength = secondAddendumString.length();
		int sumStringLength = sumString.length();
		
		// for each combination of digits in the hashmap
		while ( digitsMap.gotoNextSequence() ) {
			
			int firstAddendum = fillInStringFromDigitsMap( firstAddendumString , digitsMap );
		    //using "pseudo-standard" hack for converting int to string
			String firstAddendumNumberString = firstAddendum + "";
			//ensure that first digit isn't zero
			if ( firstAddendumNumberString.length() != firstAddendumStringLength )
				continue;
			
		    int secondAddendum = fillInStringFromDigitsMap( secondAddendumString, digitsMap );
		    //using "pseudo-standard" hack for converting int to string
			String secondAddendumNumberString = secondAddendum + "";
		    //ensure that first digit isn't zero
		    if ( secondAddendumNumberString.length() != secondAddendumStringLength )
		        continue;
		    
		    int sum = fillInStringFromDigitsMap( sumString, digitsMap );
		    //using "pseudo-standard" hack for converting int to string
			String sumNumberString = sum + "";
		    //ensure that first digit isn't zero
		    if ( sumNumberString.length() != sumStringLength )
		        continue;
			
			//check if the sum is correct -
			if ( sum == firstAddendum + secondAddendum ) {
			    // if yes - fill in the result, return it
				HashMap <String, String> puzzleSolution = new HashMap<String, String>();
				
				
				puzzleSolution.put( FIRST_ADDENDUM, firstAddendumNumberString );
			    puzzleSolution.put( SECOND_ADDENDUM, secondAddendumNumberString );
			    puzzleSolution.put( SUM, sumNumberString );
				solutions.add( puzzleSolution );
			}
			// if no - go to next combination
		}
		
		//return empty collection if nothing found
		return solutions;
	}

	/**
	 * Inform user about the puzzle result
	 * @param solutions - array of hashMap with result values
	 */
	private void outputResult( ArrayList<HashMap<String, String>> solutions ) {
		System.out.println( "Found " + ( solutions != null ? solutions.size() : 0 ) + " solutions of the puzzle." );
			for ( HashMap<String,String> result : solutions ) {
				System.out.println( "-----" );
				System.out.println( "First addendum: " + result.get( FIRST_ADDENDUM ) );			
				System.out.println( "Second addendum: " + result.get( SECOND_ADDENDUM ) );            
				System.out.println( "Sum: " + result.get( SUM ) );            
			    System.out.println( "-----" );
			}
	}

	/**
	 * Solve the additive 2-argument arithmetic puzzle.
	 * @param args arguments from command line
	 */
	public static void main(String[] args) {
		AdditionPuzzleSolver additionPuzzleSolver = new AdditionPuzzleSolver();
		//receive input from the user
		HashMap inputArguments = additionPuzzleSolver.getInputData();

		//solve the puzzle
		ArrayList<HashMap<String, String>> solvedPuzzle = additionPuzzleSolver.solvePuzzle( inputArguments );
		
		//output the result (if exists) - else show error
		additionPuzzleSolver.outputResult( solvedPuzzle );
		
		System.out.println( "Bye-bye! See you next time!" );

	}



}
