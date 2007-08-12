package leokom.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class used for reading strings from console.
 */
public class ConsoleStringReader {
	private static InputStreamReader inputStreamReader = null;
	private static BufferedReader bufferedReader = null;
	
	/**
	 * Init readers
	 */
	static {
		inputStreamReader = new InputStreamReader( System.in );
		bufferedReader = new BufferedReader( inputStreamReader );
	}
	/**
	 * Read string from console.
	 * if error - return empty string
	 * @return the result string
	 */
	public static String readString() {
		String consoleInput = "";
		// Java Cookbook recipe for reading from standard input     
		try {
			consoleInput = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println( "Exception caught while reading line from std in \n" + e );
		}
		return consoleInput;
	}
}
