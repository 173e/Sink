package sink.studio.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.fife.ui.rsyntaxtextarea.Theme;

public class Asset {
	
	static String[] iconsList = new String[]{
		"vs", "trash", "toc_open", "szoomout", "szoomin", 
		"sweblaf", "supdate", "style", "stop", "stexturepacker", "sparticle", "snuke", 
		"slibGDX", "shierologo", "shiero", "search", "screen", "sabout", "run", "refresh",
		"paste_edit", "packer", "options", "newprj", "newpack", 
		"newinterface", "newfolder", "newfile", "newenum", "newclass", "lib", "level", 
		"idea", "icon", "high", "go", "font", "find", "esource", "eprj", "epaste", "epackage", 
		"eopen", "emusic", "empty", "eimage", "efile", "editor", "eclipse", "dark", "console", 
		"color", "up","down", "resume", "pause", "export"
	};
	
	// Icons
	private static HashMap<String, ImageIcon> iconMap = new HashMap<String, ImageIcon>();
	public static void loadIcons(){
		for(String f : iconsList){
			//String[] files = new File("./bin/icon").list();
			//System.out.print("\""+f.replace(".png", "")+"\", ");
			//SinkStudio.log(f);
			iconMap.put(f, loadIcon(f+".png"));
		}
	}
	
	public static ImageIcon icon(String name){
		return iconMap.get(name);
	}
	
	public static Theme loadTheme(String name){
		try{
			return Theme.load(Asset.getResourceAsStream("theme/"+name+".xml"));
		}
		catch ( IOException e ){
             e.printStackTrace ();
         }
		return null;
	}

	public static ImageIcon loadIcon(String path){
		return new ImageIcon(Asset.getResource("icon/"+path));
	}

	public static InputStream getResourceAsStream(String path){
		return Asset.class.getClassLoader().getResourceAsStream(path);
	}

	public static URL getResource(String path){
	    return Asset.class.getClassLoader().getResource(path);
	}
}