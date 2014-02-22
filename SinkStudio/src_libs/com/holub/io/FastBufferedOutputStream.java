package com.holub.io;
import  java.io.*;
import	com.holub.tools.Tester; // for testing
import	com.holub.io.Std;		// for testing

import	com.holub.tools.D;		// for testing
//import com.holub.tools.debug.D;// for testing

/** This version of BufferedOutputStream isn't
 *	thread safe, so is much faster than the standard
 *	BufferedOutputStream in situations where the stream is not
 *	shared between threads; Otherwise, it works identically
 *	to {@link java.io.BufferedOutputStream}.
 */

public class FastBufferedOutputStream extends FilterOutputStream
{	
	private final int	   size;	  	// buffer size
	private		  byte[]   buffer;
	private 	  int	   current		 = 0;
	private		  boolean  flushed		 = false;
	private		  int	   bytes_written = 0;

	public static final int DEFAULT_SIZE = 2048;

	/**	Create a FastBufferedOutputStream whose buffer is
	 *	FastBufferedOutputStream.DEFULT_SIZE in size.
	 */
	public FastBufferedOutputStream( OutputStream out )
	{	this( out, DEFAULT_SIZE );
	}

	/**	Create a FastBufferedOutputStream whose buffer is
	 *	the indicated size.
	 */
	public FastBufferedOutputStream( OutputStream out, int size )
	{	super( out );
		this.size	= size;
		buffer		= new byte[ size ];
	}

	// Inherit write(byte[]);

	public void close() throws IOException
	{	D.ebug("\t\tFastBufferedOutputStream closing");

		flush();
		buffer = null;
		current = 0;
		super.close();
	}

	public void flush() throws IOException
	{	if( current > 0 )
		{	D.ebug("\t\tFlushing");
			out.write( buffer, 0, current );
			out.flush( );
			current = 0;
			flushed = true;
		}
	}

	/** Write a character on the stream. Flush the buffer
	 *	first if the buffer is full. That is, if you
	 *  have a 10-character buffer, the flush occurs
	 *  just before writing the 11th character.
	 */

	public void write(int the_byte) throws IOException
	{	
		if( current >= buffer.length )
			flush();	// resets current to 0

		D.ebug(   "\t\twrite(" + the_byte 
				+ "): current=" + current 
				+ ", buffer.length=" + buffer.length );

		buffer[current++] = (byte)the_byte;
		++bytes_written;
	}

	public void write(byte[] bytes, int offset, int length)
												throws IOException
	{	while( --length >= 0 )
			write( bytes[offset++] );
	}

	/**************************************************************
	 * Return the total number of bytes written to this stream.
	 */
	public int bytes_written(){ return bytes_written; }

	/**************************************************************
	 * Return the object wrapped by the FastBufferedOutputStream.
	 * (I don't consider this to be a violation of encapsulation
	 * because that object is passed into the Decorator, so is
	 * externally accessable anyway.) The internal buffer is
	 * flushed so it is safe to write directly to the
	 * "contents" object.
	 */

	public OutputStream contents() throws IOException
	{	flush();
		return out;
	}

	/**************************************************************
	 *	Return true if the buffer has been flushed to the underlying
	 *	stream.
	 */
	public boolean has_flushed(){ return flushed; }
	
	/**************************************************************
	 *	If the buffer has never been flushed to the wrapped stream,
	 *	copy it to <code>destination</code> stream and return true
	 *	(without sending the characters to the wrapped stream),
	 *  otherwise return false; in any event, close the stream.
	 *	@see #has_flushed
	 */

	public boolean export_buffer_and_close(OutputStream destination)
												throws IOException
	{	
		if( !flushed )
		{	destination.write( buffer, 0, current );
			current = 0;
		}
		close();
		return !flushed;
	}

	/**************************************************************
	 *	If the buffer has never been flushed to the wrapped stream,
	 *	return it;
	 *  otherwise return null. In any event, close the stream;
	 *	@see #has_flushed
	 */

	public byte[] export_buffer_and_close() throws IOException
	{	byte[] buffer = null;

		if( !flushed )
		{	buffer = this.buffer;
			current = 0;
		}
		close();
		return buffer ;
	}

	/**************************************************************
	 * A test class.
	 */

	static public class Test
	{	static public void main(String[] args) throws Exception
		{	Tester	t = new Tester( args.length > 0, Std.out() );
			try
			{
				File f = File.createTempFile( "FastBufferedOutputStream", ".test");
				FastBufferedOutputStream out = new FastBufferedOutputStream
												( new FileOutputStream(f), 10 );

				for( char c = 'a'; c <= 'x'; ++c )
					out.write( (byte)c );

				out.write( new byte[]{ (byte)'y', (byte)'z' } );
				out.close();

				t.check("FastBufferedOutputStream.1.0", 'z'-'a'+1, out.bytes_written() );

				t.verbose(Tester.OFF);
				char got;
				FileInputStream in = new FileInputStream(f);
				for( char c = 'a'; c <= 'z'; ++c )
					t.check("FastBufferedOutputStream.1.1", c,  in.read() );
				t.verbose(Tester.RESTORE);
				t.check("FastBufferedOutputStream.1.1", !t.errors_were_found(),
															"read/write test");

				t.check("FastBufferedOutputStream.1.2", -1, in.read() );
				in.close();

				if( !t.errors_were_found() )
					f.delete();
				else
					Std.out().println("Test file not deleted: f.getName()" );

				//----------------------------------------------------------------
				File temp = new File("Fast.2.test");

				t.check( "FastBufferedOutputStream.2.0", false, temp.exists() );

				DelayedOutputStream stream	= new DelayedOutputStream(temp);

				t.check( "FastBufferedOutputStream.2.1", false, temp.exists() );

				out = new FastBufferedOutputStream( stream, 2 );

				t.check( "FastBufferedOutputStream.2.2", false, temp.exists() );

				out.write( (byte)'x' );

				t.check( "FastBufferedOutputStream.2.3", false, temp.exists() );

				out.write( (byte)'x' );

				t.check( "FastBufferedOutputStream.2.4", false, temp.exists() );

				out.write( (byte)'x' );
				t.check( "FastBufferedOutputStream.2.5", true, temp.exists() );

				out.close();
				boolean deleted = temp.delete();
				t.check( "FastBufferedOutputStream.2.6", deleted,
														"Deleting temporary file");

				//----------------------------------------------------------------

				stream = new DelayedOutputStream("Fast",".3.test");
				out = new FastBufferedOutputStream( stream, 2 );

				out.write( 'a' );
				out.write( 'b' );

				byte[] buffer = out.export_buffer_and_close();
				t.check("FastBufferedOutputStream.3.1", buffer != null, "Expected non null");
				t.check("FastBufferedOutputStream.3.2", buffer[0]=='a' && buffer[1]=='b',
													"Expected \"ab\"");

				t.check("FastBufferedOutputStream.3.3", stream.temporary_file()==null,
													"Expected no temporary-file reference");

				//----------------------------------------------------------------

				stream = new DelayedOutputStream("Fast",".4.test");
				out = new FastBufferedOutputStream( stream, 2 );

				out.write( 'a' );
				out.write( 'b' );

				ByteArrayOutputStream bytes = new ByteArrayOutputStream();

				t.check("FastBufferedOutputStream.4.1", true, out.export_buffer_and_close(bytes) );
				t.check("FastBufferedOutputStream.4.2", bytes.toString().equals("ab"),
													"Expected \"ab\"");
				t.check("FastBufferedOutputStream.4.3", stream.temporary_file()==null,
													"Expected no temporary-file reference");

				//----------------------------------------------------------------

				stream = new DelayedOutputStream("Fast",".5.test");
				out = new FastBufferedOutputStream( stream, 2 );

				for( char c = 'a'; c <= 'z'; ++c )
					out.write( (byte)c );
				out.close();

				buffer = out.export_buffer_and_close();
				t.check("FastBufferedOutputStream.5.1", buffer==null, "Expected null");
				t.check("FastBufferedOutputStream.5.2", false, out.export_buffer_and_close(bytes) );
				t.check("FastBufferedOutputStream.5.3", stream.temporary_file()!=null,
													"Expected temporary-file reference");

				in = new FileInputStream(stream.temporary_file());
				for( char c = 'a'; c <= 'z'; ++c )
					t.check("FastBufferedOutputStream.1.1", c,  in.read() );
				t.check("FastBufferedOutputStream.1.2", -1, in.read() );
				in.close();

				String name = stream.temporary_file().getName();
				stream.delete_temporary();
				t.check("FastBufferedOutputStream.5.3", !(new File(name).exists()),
													"Temporary file destroyed" );
			}
			catch( Exception e )
			{	t.check( "FastBufferedOutputStream.Abort", false,
								"Terminated by Exception toss" );
				e.printStackTrace();
			}
			finally
			{	t.exit();
			}
		}
	}
}
