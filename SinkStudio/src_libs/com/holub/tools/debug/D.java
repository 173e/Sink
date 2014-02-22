package com.holub.tools.debug;

import com.holub.io.Std;

public class D
{	static private boolean enabled = true;

	public static final void ebug_enable (){ enabled = true;  }
	public static final void ebug_disable(){ enabled = false; }

	public static final void ebug( String text )
	{	if( enabled )
			Std.err().println( text );
	}
}   
