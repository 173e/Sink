package sink.studio.core;

import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import sink.core.Asset;
import sink.core.Sink;

public class Export {
	
	private static void loadFromJar(){
		try{
			File jarName = new File(Asset.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			ZipFile zf = new ZipFile(jarName.getAbsoluteFile());
		    Enumeration<? extends ZipEntry> e=zf.entries();
		    while (e.hasMoreElements()) 
		     {
		          ZipEntry ze=(ZipEntry)e.nextElement();
		          String entryName = ze.getName();
				  if(entryName.startsWith("sink")) {
					  Sink.log(ze.getName());
				  }
		     }
		     zf.close();
		}
		catch (Exception e){e.printStackTrace();}
	}

}
