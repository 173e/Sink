package sink.studio.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileReader;
import net.java.truevfs.access.TFileWriter;
import sink.studio.panel.FilePanel;
import sink.studio.panel.ProjectPanel;
import sink.studio.panel.ScenePanel;
import web.laf.lite.popup.NotificationManager;

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
		writeFile("font/", "");
		writeFile("atlas/", "");
		writeFile("music/", "");
		writeFile("sound/", "");
		writeFile("map/", "");
		writeFile("skin/", "");
		writeFile("particle/", "");
		writeFile("source/", "");
		writeFile("META-INF/MANIFEST.MF", manifestText);
		updateConfigFile();
		FilePanel.updateList();
		ScenePanel.updateList();
	}
	
	public static void writeFile(String filename, String data){
		if(!Content.projectExists())
			return;
		SinkStudio.log("Writing File: "+filename);
		File entry = new TFile(Content.getProject()+"/"+filename);
		Writer writer = null;
		try {
			writer = new TFileWriter(entry);
			writer.write(data);
		} catch (IOException e){ 
			e.printStackTrace();
			NotificationManager.showNotification("Error: Could'nt Save File: "+filename);
		} finally {
		    try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String readFile(String filename){
		if(!Content.projectExists())
			return "";
		SinkStudio.log("Reading File: "+filename);
		String text = "";
		File entry = new TFile(Content.getProject()+"/"+filename);
		Reader reader = null;
		BufferedReader br = null;
		try {
			reader = new TFileReader(entry);
			br = new BufferedReader(reader);
			String line;
            while ((line=br.readLine())!=null)
                text+=line+System.lineSeparator();
		} catch (IOException e) {
			e.printStackTrace();
			NotificationManager.showNotification("Error: Could'nt Read File: "+filename);
		}finally {
		    try {
		    	br.close(); 
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return text;
	}
	
	public static void writeFile2(String filename, String data){
		if(!Content.projectExists())
			return;
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
		ArrayList<String> list = new ArrayList<String>();
		TFile archive = new TFile(Content.getProject()+"/"+foldername);
		for (String member : archive.list())
		  list.add(member);
		return list;
	}
	
	public static void deleteFile(String filename){
		if(!Content.projectExists())
			return;
		SinkStudio.log("Deleting File: "+filename);
		try {
			new TFile(Content.getProject()+"/"+filename).rm();
		} catch (IOException e) {
			NotificationManager.showNotification("Error: Could'nt Delete File: "+filename);
			e.printStackTrace();
		}
	}
	
	private static String fromStream(InputStream in) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    StringBuilder out = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        out.append(line);
	        out.append(System.lineSeparator());
	    }
	    //reader.close();
	    //in.close();
	    return out.toString();
	}
}