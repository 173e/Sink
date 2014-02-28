package sink.studio.core;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import sink.studio.panel.FilePanel;
import sink.studio.panel.ProjectPanel;
import web.laf.lite.popup.NotificationManager;

import com.holub.tools.Archive;

public class Export {

	static String manifestText = "Manifest-Version: 1.0\n"
				+"Class-Path: .\n"
				+"Created-By: pyros2097\n"
				+"Main-Class: sink.main.MainDesktop\n";
	
	public static void updateConfigFile(){
		writeFile("config.json", updateConfigString());
	}
	
	private static String updateConfigString(){
		return 
		"{"
		+ createField("title", ProjectPanel.titleField.getText())
		+ createField("showIcon", ""+ProjectPanel.iconSwitch.isSelected())
		+ createField("targetWidth", ""+ProjectPanel.targetWidthField.getText())
		+ createField("targetHeight", ""+ProjectPanel.targetHeightField.getText())
		+ createField("screenWidth", ""+ProjectPanel.screenWidthField.getText())
		+ createField("screenHeight", ""+ProjectPanel.screenHeightField.getText())
		+ createField("x", ""+ProjectPanel.xField.getText())
		+ createField("y", ""+ProjectPanel.yField.getText())
		+ createField("audio", ""+ProjectPanel.audioField.getText())
		+ createField("icon", ""+ProjectPanel.iconField.getText())
		+ createField("firstScene", ""+ProjectPanel.firstSceneField.getText())
		
		+ createField("showIcon", ""+ProjectPanel.iconSwitch.isSelected())
		+ createField("resizeable", ""+ProjectPanel.resizeableSwitch.isSelected())
		+ createField("forceExit", ""+ProjectPanel.forceExitSwitch.isSelected())
		+ createField("fullScreen", ""+ProjectPanel.fullScreenSwitch.isSelected())
		+ createField("useGL20", ""+ProjectPanel.useGL20Switch.isSelected())
		+ createField("vSync", ""+ProjectPanel.vSyncSwitch.isSelected())
		+ createField("disableAudio", ""+ProjectPanel.disableAudioSwitch.isSelected())
		+ createField("keepAspectRatio", ""+ProjectPanel.keepAspectRatioSwitch.isSelected())
		
		+ createField("isJar", ""+ProjectPanel.isJarSwitch.isSelected())
		+ createField("useCloud", ""+ProjectPanel.useCloudSwitch.isSelected())
		+ createField("showFPS", ""+ProjectPanel.showFPSSwitch.isSelected())
		
		+ createField("showLogger", ""+ProjectPanel.showLoggerSwitch.isSelected())
		+ createField("loggingEnabled", ""+ProjectPanel.loggingEnabledSwitch.isSelected())
		
		+ "}";
	}
	
	private static String createField(String name, String data){
		return "\""+name+"\":"+"\""+data+"\",";
	}
	
	public static void createJar(){
		SinkStudio.log("Creating Project: "+Content.getProject());
		Archive archive;
		try {
			archive = new Archive(Content.getProject());
	    	archive.output_stream_for("font/");
	    	archive.output_stream_for("atlas/");
	    	archive.output_stream_for("music/");
	    	archive.output_stream_for("sound/");
	    	archive.output_stream_for("map/");
	    	archive.output_stream_for("skin/");
	    	archive.output_stream_for("particle/");
	    	archive.output_stream_for("source/");
	    	archive.close();
	    	writeFile("META-INF/MANIFEST.MF", manifestText);
	    	updateConfigFile();
	    	FilePanel.updateList();
		} catch (IOException e) {
			NotificationManager.showNotification("Error: Could'nt Create Project: "+Content.getProject());
			e.printStackTrace();
		} catch (InterruptedException e) {
			NotificationManager.showNotification("Error: Could'nt Create Project: "+Content.getProject());
			e.printStackTrace();
		}
	}
	
	public static void writeFile(String filename, String data){
		if(!Content.projectExists())
			return;
		SinkStudio.log("Writing File: "+filename);
		Archive archive;
		try {
			archive = new Archive(Content.getProject());
			copyInputToOutput(data, archive.output_stream_for(filename));
			archive.close();
		} catch (IOException e) {
			NotificationManager.showNotification("Error: Could'nt Save File: "+filename);
			e.printStackTrace();
		} catch (InterruptedException e) {
			NotificationManager.showNotification("Error: Could'nt Save File: "+filename);
			e.printStackTrace();
		}
	}
	
	public static void writeFile2(String filename, String data){
		SinkStudio.log("Writing File: "+filename);
		File file = new File(filename);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data.getBytes());
			fos.close();
		} catch (IOException e) {
			NotificationManager.showNotification("Error: Could'nt Save File: "+filename);
			e.printStackTrace();
		}
	}
	
	public static String readFile(String filename){
		if(!Content.projectExists())
			return "";
		SinkStudio.log("Reading File: "+filename);
		Archive archive;
		String text = "";
		try {
			archive = new Archive(Content.getProject());
			text = fromStream(archive.input_stream_for(filename));
			archive.close();
		} catch (IOException e) {
			NotificationManager.showNotification("Error: Could'nt Read File: "+filename);
			e.printStackTrace();
		} catch (InterruptedException e) {
			NotificationManager.showNotification("Error: Could'nt Read File: "+filename);
			e.printStackTrace();
		}
		return text;
	}
	
	public static String readFile2(String filename){
		SinkStudio.log("Reading File: "+filename);
		File file = new File(filename);
		String text = "";
		try {
			FileInputStream fin = new FileInputStream(file);
			text = fromStream(fin);
			fin.close();
		} catch (IOException e) {
			NotificationManager.showNotification("Error: Could'nt Read File: "+filename);
			e.printStackTrace();
		}
		return text;
	}
	
	public static ArrayList<String> listFiles(String foldername){
		if(!Content.projectExists())
			return null;
		SinkStudio.log("Listing Files");
		ArrayList<String> list = new ArrayList<String>();
		ZipFile zf;
		try {
			zf = new ZipFile(Content.getProject());
			Enumeration<? extends ZipEntry> e = zf.entries();
			while (e.hasMoreElements()) 
			{
				ZipEntry ze=(ZipEntry)e.nextElement();
				String entryName = ze.getName();
				if(entryName.startsWith(foldername)) {
					list.add(entryName);
				}
			}
			zf.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return list;
	}
	
	public static void deleteFile(String filename){
		if(!Content.projectExists())
			return;
		SinkStudio.log("Deleting File: "+filename);
		Archive archive;
		try {
			archive = new Archive(Content.getProject());
			archive.remove(filename);
			archive.close();
		} catch (IOException e) {
			NotificationManager.showNotification("Error: Could'nt Delete File: "+filename);
			e.printStackTrace();
		} catch (InterruptedException e) {
			NotificationManager.showNotification("Error: Could'nt Delete File: "+filename);
			e.printStackTrace();
		}
	}
	
	public static OutputStream getOutputStream(String filename){
		SinkStudio.log("Compiling File: "+filename);
		Archive archive;
		OutputStream out = null;
		try {
			archive = new Archive(Content.getProject());
			out = archive.output_stream_for(filename);
			//archive.close();
		} catch (IOException e) {
			NotificationManager.showNotification("Error: Could'nt Compile File: "+filename);
			e.printStackTrace();
		//} catch (InterruptedException e) {
		//	NotificationManager.showNotification("Error: Could'nt Compile File: "+filename);
		//	e.printStackTrace();
		}
		return out;
	}
	
	private static void copyInputToOutput(String data, OutputStream out){
		InputStream in = new ByteArrayInputStream(data.getBytes(Charset.forName("UTF-8")));
		try {
			while (0 < in.available()){
				int read = in.read();
				out.write(read);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String fromStream(InputStream in) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    StringBuilder out = new StringBuilder();
	    String newLine = System.getProperty("line.separator");
	    String line;
	    while ((line = reader.readLine()) != null) {
	        out.append(line);
	        out.append(newLine);
	    }
	    //reader.close();
	    //in.close();
	    return out.toString();
	}
}