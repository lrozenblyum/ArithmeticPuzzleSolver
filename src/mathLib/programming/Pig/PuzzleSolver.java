package mathLib.programming.Pig;

import java.util.ArrayList;
import java.util.HashMap;

import leokom.utils.ConsoleStringReader;
import leokom.utils.DigitsMapImpl;
import leokom.utils.DigitsOrderedMap;

/**
 * This class should solve 2-argument arithmetic puzzles.
 * E.g.
 * 	SEND
 * +MORE
 * -----
 * MONEY
 */
public class

PuzzleSolver {

	private static final int FIRST_OPERAND_NUMBER = 1;
	private static final int SECOND_OPERAND_NUMBER = 2;
	private static final String OPERAND = "operand";
	private static final String OPERATION = "operation";
	private static final int RESULT_NUMBER = 0;
	private static final int NUMBER_SYSTEM_MULTIPLICATOR = 10; //for decimal
	
	
	/**
	 * Get operand name by its number. (Sum or other result has number 0)
	 * @return
	 */
	private String getOperandName( int operandNumber ) {
		return OPERAND + operandNumber;
	}
	/**
	 * Receive needed data from user (using keyboard)
	 * @return hash map of strings received from the user
	 */
	HashMap<String, String> getInputData() {
		HashMap inputData = new HashMap();
		System.out.println("Input first operand");
		String firstAddendum = ConsoleStringReader.readString();
	    inputData.put( getOperandName( FIRST_OPERAND_NUMBER ), firstAddendum );
		
		System.out.println( "Input operation ( + - * / )" );
		Character operation = ConsoleStringReader.readChar();
		inputData.put( OPERATION, operation );
		
		System.out.println( "Input second operand" );
		String secondAddendum = ConsoleStringReader.readString();
		inputData.put( getOperandName( SECOND_OPERAND_NUMBER ), secondAddendum );
		
		System.out.println( "Input result" );
		String result = ConsoleStringReader.readString();
		inputData.put( getOperandName( RESULT_NUMBER ), result );
		 
		return inputData;
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
		addStringIntoDigitsMap( (String) inputArguments.get( getOperandName( FIRST_OPERAND_NUMBER ) ), digitsMap );
	    addStringIntoDigitsMap( (String) inputArguments.get( getOperandName( SECOND_OPERAND_NUMBER ) ), digitsMap );
		addStringIntoDigitsMap( (String) inputArguments.get( getOperandName( RESULT_NUMBER ) ), digitsMap );
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
	 * Perform binary operation on operands.
	 * @param firstOperand
	 * @param secondOperand
	 * @param operation
	 * @return
	 */
	int performOperation( int firstOperand, int secondOperand, char operation ) {
		int operationResult;
		switch ( operation ) {
			case '+':
				operationResult = firstOperand + secondOperand;
				break;
		    case '-':
		        operationResult = firstOperand - secondOperand;
		        break;
		    case '*':
		        operationResult = firstOperand * secondOperand;
		        break;
		    case '/':
				if ( firstOperand % secondOperand != 0 )
					throw new ArithmeticException( "First operand should be evenly divisible on second" );
		        operationResult = firstOperand / secondOperand;
		        break;
			default:
				//TODO: check if default behaviour is correct - use sum by default
				operationResult = firstOperand + secondOperand;
		}
		return operationResult;
	}
	
	/**
	 * Check if parameters form solution of the puzzle.
	 * @param firstOperand
	 * @param secondOperand
	 * @param result
	 * @param operation
	 * @return true if solution found
	 */
	private boolean isSolutionFound( int firstOperand, int secondOperand, int result, char operation ) {
	    boolean isSolutionFound = false;
		try {
			isSolutionFound = result == performOperation( firstOperand, secondOperand, operation );		
		}
		catch ( ArithmeticException ex) {
			//do nothing; for division exception
			//isSolutionFound = false;
		}
		return isSolutionFound;
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
		
	    //create first, second arg and the result
	    String firstAddendumString = (String)inputArguments.get( getOperandName( FIRST_OPERAND_NUMBER ) );
	    String secondAddendumString = (String)inputArguments.get( getOperandName( SECOND_OPERAND_NUMBER ) );
	    String resultString = (String)inputArguments.get( getOperandName( RESULT_NUMBER ) );
		
		int firstAddendumStringLength = firstAddendumString.length();
		int secondAddendumStringLength = secondAddendumString.length();
		int resultStringLength = resultString.length();
		
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
		    
		    int result = fillInStringFromDigitsMap( resultString, digitsMap );
		    //using "pseudo-standard" hack for converting int to string
			String resultNumberString = result + "";
		    //ensure that first digit isn't zero
		    if ( resultNumberString.length() != resultStringLength )
		        continue;
			
			Character operation = (Character)inputArguments.get( OPERATION );
			//check if the result is correct -
			if ( isSolutionFound( firstAddendum, secondAddendum, result, operation ) ) {
			    // if yes - fill in the result, return it
				HashMap <String, String> puzzleSolution = new HashMap<String, String>();
				
				
				puzzleSolution.put( getOperandName( FIRST_OPERAND_NUMBER ), firstAddendumNumberString );
			    puzzleSolution.put( getOperandName( SECOND_OPERAND_NUMBER ), secondAddendumNumberString );
			    puzzleSolution.put( getOperandName( RESULT_NUMBER ), resultNumberString );
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
				System.out.println( "First operand: " + result.get( getOperandName( FIRST_OPERAND_NUMBER ) ) );			
				System.out.println( "Second operand: " + result.get( getOperandName( SECOND_OPERAND_NUMBER ) ) );            
				System.out.println( "Result: " + result.get( getOperandName( RESULT_NUMBER ) ) );            
			    System.out.println( "-----" );
			}
	}

	/**
	 * Solve the additive 2-argument arithmetic puzzle.
	 * @param args arguments from command line
	 */
	public static void main(String[] args) {
		PuzzleSolver additionPuzzleSolver = new PuzzleSolver();
		//receive input from the user
		HashMap inputArguments = additionPuzzleSolver.getInputData();

		//solve the puzzle
		ArrayList<HashMap<String, String>> solvedPuzzle = additionPuzzleSolver.solvePuzzle( inputArguments );
		
		//output the result (if exists) - else show error
		additionPuzzleSolver.outputResult( solvedPuzzle );
		
		System.out.println( "Bye-bye! See you next time!" );

	}



}
