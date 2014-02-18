package sink.studio.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.ImageIcon;

import org.fife.ui.rsyntaxtextarea.Theme;

public class Asset {
	
	// Icons
	private static HashMap<String, ImageIcon> iconMap = new HashMap<String, ImageIcon>();
	public static void loadIcons(){
		if(Config.isJar)
			loadFromJar();
		else
			for(String f: new File("./bin/icon").list())
				iconMap.put(f.replace(".png",  ""), Asset.$loadIcon(f));
	}
		
	private static void loadFromJar(){
			try{
				File jarName = new File(Asset.class.getProtectionDomain().getCodeSource().getLocation().toURI());
				ZipFile zf = new ZipFile(jarName.getAbsoluteFile());
			    Enumeration<? extends ZipEntry> e=zf.entries();
			    while (e.hasMoreElements()) 
			     {
			          ZipEntry ze=(ZipEntry)e.nextElement();
			          String entryName = ze.getName();
					  if(entryName.startsWith("icon") &&  entryName.endsWith(".png")) {
						  iconMap.put(entryName.replace(".png",  "").replace("icon/", ""), Asset.$loadIconFromJar(entryName));
					  }
			     }
			     zf.close();
			}
			catch (Exception e){e.printStackTrace();}
	}
	
	public static ImageIcon icon(String name){
		return iconMap.get(name);
	}
	
	public static Theme loadTheme(String name){
		try{
			return Theme.load(Asset.$getResourceAsStream("theme/"+name+".xml"));
		}
		catch ( IOException e ){
             e.printStackTrace ();
         }
		return null;
	}

	public static URL $loadStyle(String path){
		return Asset.$getResource("style/"+path);
	}

	public static ImageIcon $loadIcon(String path){
		//$log(path);
		return new ImageIcon(Asset.$getResource("icon/"+path));
	}

	public static ImageIcon $loadIconFromJar(String path){
		//$log(path);
		return new ImageIcon(Asset.$getResource(path));
	}

	public static InputStream $getResourceAsStream(String path){
		return Asset.class.getClassLoader().getResourceAsStream(path);
	}

	public static URL $getResource(String path){
	    return Asset.class.getClassLoader().getResource(path);
	}
}