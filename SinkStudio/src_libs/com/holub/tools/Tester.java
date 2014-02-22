package com.holub.tools;

import com.holub.tools.debug.Assert;
import java.io.*;

/****************************************************************
 * A simple class to help in testing. Various check() methods
 * are passed a test id, an expected value, and an actual value.
 * The test prints appropriate messages, and keeps track of the
 * total error count for you.
 * For example:
 * <pre>
 * class Test
 * {	public static void main(String[] args);
 *		{
 *			// Create a tester that sends output to standard error, which
 *			// operates in verbose mode if there are any command-line
 *			// arguments.
 *
 * 			Tester t = new Tester( args.length > 0,
 *										com.holub.tools.Std.out() );
 * 			//...
 *
 * 			t.check("test.1", 0,     foo()); 		// check that foo() returns 0.
 * 			t.check("test.2", "abc", bar()); 		// check that bar() returns "abc".
 * 			t.check("test.3", true , cow()); 		// check that cow() returns true
 * 			t.check("test.4", true , dog()==null);	// check that dog() returns null
 *
 *													// Check arbitrary statement
 *			t.check("test.5", f()!=g(), "Expected f() to return same value as g()" );
 *
 * 			//...
 *			t.exit();
 *		}
 *	}
 * </pre>
 */

public class Tester
{
	private int		 		  errors = 0;
	private boolean	  		  verbose;
	private final PrintWriter log;
	private final boolean	  original_verbosity;

	/**
	 * Create a tester that has the specified behavior and output stream:
	 *
	 * @param verbose	Print messages even if test succedes. (Normally,
	 *					only failures are indicated.)
	 * @param log		if not null, all output is sent here, otherwise
	 *					output is sent to standard error.
	 */
	public Tester( boolean verbose, PrintWriter log )
	{	this.verbose 			= verbose;
		this.original_verbosity = verbose;
		this.log	 			= log;
	}

	/******************************************************************
	 * Change the verbose mode, overriding the mode passed to the
	 * constructor.
	 * @param mode
	 *		<table>
	 *		<tr><td>Tester.ON</td> 		<td>Messages are reported.</td></tr>
	 *		<tr><td>Tester.OFF</td>		<td>Messages aren't reported.</td></tr>
	 *		<tr><td>Tester.RESTORE</td>	<td>Use Verbose mode specifed in constructor.</td></tr>
	 *		</table>
	 */

	public void verbose( int mode )
	{	switch( mode )
		{
		case ON:	verbose = true;					break;
		case OFF:	verbose = false;				break;
		default:	verbose = original_verbosity;	break;
		}
	}

	public static final int ON  	= 0;
	public static final int OFF 	= 1;
	public static final int RESTORE = 2;

	/******************************************************************
	 * Check that and expected result of type String is equal to the
	 * actual result.
	 *
	 * @param test_id	String that uniquely identifies this test.
	 * @param expected	the expected result
	 * @param actual	the value returned from the function under test
	 * @return true if the expected and actual parameters matched.
	 */

	public boolean check( String test_id, String expected, String actual)
	{
		Assert.is_true( log != null    , "Tester.check(): log is null" 		);
		Assert.is_true( test_id != null, "Tester.check(): test_id is null"	);

		boolean okay = expected.equals( actual );

		if( !okay )
			++errors;

		if( !okay || verbose )
		{	log.println (  (okay ? "   okay " : "** FAIL ")
						 + "("  + test_id + ")"
						 + " expected: " + expected
						 + " got: " 	 + actual
					);
		}
		return okay;
	}
	/******************************************************************
	 * Print the message if verbose mode is on.
	 */

	public void println( String message )
	{	if( verbose )
			log.println( "\t" + message );
	}

	/******************************************************************
	 * For situations not covered by normal check() methods. If
	 * <code>okay</code> is false, ups the error count and prints the
	 * associated message string (assuming verbose is on). Otherwise
	 * does nothing.
	 */
	public void check( String test_id, boolean okay, String message )
	{	Assert.is_true( message != null );

		if( !okay )
			++errors;

		if( !okay || verbose )
		{	
			log.println (  (okay ? "   okay " : "** FAIL ")
						 + "("  + test_id + ") "
						 + message
					);
		}
	}
	/******************************************************************
	 * Convenience method, compares a string against a StringBuffer
	 */
	public boolean check( String test_id, String expected, StringBuffer actual)
	{	return check( test_id, expected, actual.toString());
	}
	/******************************************************************
	 * Convenience method, compares two doubles
	 */
	public boolean check( String test_id, double expected, double actual)
	{	return check( test_id, "" + expected, "" + actual );
	}
	/******************************************************************
	 * Convenience method, compares two longs
	 */
	public boolean check( String test_id, long expected, long actual)
	{	return check( test_id, "" + expected, "" + actual );
	}
	/******************************************************************
	 * Convenience method, compares two booleans
	 */
	public boolean check( String test_id, boolean expected, boolean actual)
	{	return check( test_id, expected?"true":"false", actual?"true":"false" );
	}
	/******************************************************************
	 * Return true if any preceding check() call resulted in an error.
	 */
	public boolean errors_were_found()
	{	return errors != 0;
	}
	/******************************************************************
	 * Exit the program, using the total error count as the exit status.
	 */
	public void exit()
	{	if( verbose )
			log.println( "\n" + errors + " errors detected" );
		System.exit( errors );
	}
}
