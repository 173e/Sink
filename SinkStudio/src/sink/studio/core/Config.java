package sink.studio.core;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;


public class Config {
	static Preferences prefs;
	static Integer counter = new Integer(0);
	static Register lafsRegister = new Register(103);
	static Register themesRegister = new Register(104);
	
	public static ArrayList<File> projects = new ArrayList<File>();
	public static ArrayList<File> files = new ArrayList<File>();
	public static List<String> keyList;  
	private static HashMap<KeyStroke, Action> keyMap = new HashMap<KeyStroke, Action>();
	static KeyboardFocusManager kfm;
	
	public static DefaultLogger $antLogger;

	public static void init(){
		prefs = Preferences.userRoot().node("sinkstudio");
		try {
			keyList = Arrays.asList(prefs.keys());
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}  
		registerString(lafsRegister, "Sink");
		registerString(themesRegister, "default");
		
		$antLogger = new DefaultLogger();
		$antLogger.setErrorPrintStream(System.err);
		$antLogger.setOutputPrintStream(System.out);
		$antLogger.setMessageOutputLevel(Project.MSG_INFO);
		//redirectSystemStreams();
		
		kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.addKeyEventDispatcher( new KeyEventDispatcher() {
		    @Override
		    public boolean dispatchKeyEvent(KeyEvent e) {
		      KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);
		      if (keyMap.containsKey(keyStroke) ) {
		        final Action a = keyMap.get(keyStroke);
		        SwingUtilities.invokeLater( new Runnable() {
		          @Override
		          public void run() {
		            a.actionPerformed(null);
		          }
		        } ); 
		        return true;
		      }
		      return false;
		    }
		  });
	}
	
	static void redirectSystemStreams(){
		SinkStudio.log("Redirecting System.out and System.err");
		final OutputStream out = new OutputStream() 
		{
		      @Override
		      public void write(int b) throws IOException {
		        //RightSideBar.$updateConsoleArea(String.valueOf((char) b));
		      }
		
		      @Override
		      public void write(byte[] b, int off, int len) throws IOException {
		        //RightSideBar.$updateConsoleArea(new String(b, off, len));
		      }
		
		      @Override
		      public void write(byte[] b) throws IOException {
		        write(b, 0, b.length);
		      }
		 };
		SinkStudio.log("Redirecting System.out and System.err"); 
		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}
	
	public static void addHotKey(int key1, Action action){
		keyMap.put(KeyStroke.getKeyStroke(key1, 0), action);
	}
	
	public static void addHotKey(int key, int modifier, Action action){
		keyMap.put(KeyStroke.getKeyStroke(key, modifier), action);
	}

	public static void setTheme(String iconName) {
		putString(themesRegister, iconName);
	}
	
	public static String getTheme() {
		return getString(themesRegister);
	}
	
	public static void setLaf(String lafName) {
		putString(lafsRegister, lafName);
	}
	
	public static String getLaf() {
		return getString(lafsRegister);
	}
	
	public static void registerBoolean(Register reg){
		if(reg.getValue() == 0){
			counter++;
			reg.setValue(counter.intValue());
		}
		if(!keyList.contains(""+reg.getValue()))
			prefs.putBoolean(""+reg.getValue(), false);
	}
	
	public static void putBoolean(Register reg, boolean value){
		prefs.putBoolean(""+reg.getValue(), value);
		flush();
	}
	
	public static boolean getBoolean(Register reg){
		return prefs.getBoolean(""+reg.getValue(), false);
	}
	
	public static void registerString(Register reg){
		if(reg.getValue() == 0){
			counter++;
			reg.setValue(counter.intValue());
		}
		if(!keyList.contains(""+reg.getValue()))
			prefs.put(""+reg.getValue(), "");
	}
	
	public static void registerString(Register reg, String defValue){
		if(reg.getValue() == 0){
			counter++;
			reg.setValue(counter.intValue());
		}
		if(!keyList.contains(""+reg.getValue()))
			prefs.put(""+reg.getValue(), defValue);
	}
	
	public static void putString(Register reg, String value){
		prefs.put(""+reg.getValue(), value);
		flush();
	}
	
	public static String getString(Register reg){
		return prefs.get(""+reg.getValue(), "");
	}
	
	public static void flush(){
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
}