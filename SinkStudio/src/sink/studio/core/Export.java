package sink.studio.core;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import sink.studio.panel.ProjectPanel;
import web.laf.lite.popup.NotificationManager;

import com.holub.tools.Archive;

public class Export {

	static String mani = "Manifest-Version: 1.0\n"
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
	    	writeFile("META-INF/MANIFEST.MF", mani);
	    	updateConfigFile();
		} catch (IOException e) {
			NotificationManager.showNotification("Error: Could'nt Create Project: "+Content.getProject());
			e.printStackTrace();
		} catch (InterruptedException e) {
			NotificationManager.showNotification("Error: Could'nt Create Project: "+Content.getProject());
			e.printStackTrace();
		}
	}
	
	public static void writeFile(String filename, String data){
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
	
	public static String readFile(String filename){
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
	
	public static ArrayList<String> listFiles(){
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
				if(entryName.startsWith("source") &&  entryName.endsWith(".java")) {
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
	
	/*
	public static void updateConfigFile() throws IOException{
		addFilesToExistingZip("config.json", updateConfigString());
	}

	
	public static void writeFile(String name,String text) throws IOException {
		ZipOutputStream jar = new ZipOutputStream(new FileOutputStream(exportFile));
    	ZipEntry configEntry = new ZipEntry("config.json");
    	jar.putNextEntry(configEntry);
    	InputStream in = new ByteArrayInputStream(configString.getBytes());
    	while (0 < in.available()){
    		int read = in.read();
    		jar.write(read);
    	}
    	in.close();
    	jar.closeEntry();
    	jar.close();
	}
	
    public static void copy() throws IOException, URISyntaxException {
    	File jarName = new File(Export.class.getProtectionDomain().getCodeSource().getLocation().toURI());
    	ZipFile srcFile = new ZipFile(jarName.getAbsoluteFile());
    	ZipOutputStream destFile = new ZipOutputStream(new FileOutputStream(exportFile));
    	Enumeration<? extends ZipEntry> entries = srcFile.entries();
    	byte[] buffer = new byte[512];
    	while (entries.hasMoreElements()) {
    		ZipEntry entry = (ZipEntry)entries.nextElement();
    		String entryName = entry.getName();
			//if(entryName.startsWith("sink")) {
			//	Sink.log(entry.getName());
			//}
    		ZipEntry newEntry = new ZipEntry(entry.getName());  // empty entry
    		destFile.putNextEntry(newEntry);
    		//destFile.putNextEntry(entry); //to keep meta-data
    		InputStream in = srcFile.getInputStream(entry);
    		while (0 < in.available()){
    			int read = in.read(buffer);
    			destFile.write(buffer,0,read);
    		}
    		in.close();
    		destFile.closeEntry();
    	}
    	destFile.close();
    	srcFile.close();
    }
    
    public static void writeFileToZipEntry(File file) throws IOException{
    	ZipOutputStream destFile = new ZipOutputStream(new FileOutputStream(exportFile));
    	add(file, destFile);
    	destFile.close();
    	//byte[] buffer = new byte[512];
    	//ZipEntry newEntry = new ZipEntry(entry.getName());  // empty entry
    	//destFile.putNextEntry();
    	InputStream in = new FileInputStream(file);
    	//InputStream ing = new ByteArrayInputStream( "gsg".getBytes());
		while (0 < in.available()){
			int read = in.read(buffer);
			destFile.write(buffer,0,read);
		}
		in.close();
    }

	public static void add(File source, ZipOutputStream target) throws IOException {
	  BufferedInputStream in = null;
	  try
	  {
	    if (source.isDirectory())
	    {
	      String name = source.getPath().replace("\\", "/");
	      if (!name.isEmpty())
	      {
	        if (!name.endsWith("/"))
	          name += "/";
	        ZipEntry entry = new ZipEntry(name);
	        entry.setTime(source.lastModified());
	        target.putNextEntry(entry);
	        target.closeEntry();
	      }
	      for (File nestedFile: source.listFiles())
	        add(nestedFile, target);
	      return;
	    }

	    ZipEntry entry = new ZipEntry(source.getName());
	    entry.setTime(source.lastModified());
	    target.putNextEntry(entry);
	    in = new BufferedInputStream(new FileInputStream(source));

	    byte[] buffer = new byte[1024];
	    while (true)
	    {
	      int count = in.read(buffer);
	      if (count == -1)
	        break;
	      target.write(buffer, 0, count);
	    }
	    target.closeEntry();
	  }
	  finally
	  {
	    if (in != null)
	      in.close();
	  }
	}
	
	public static void addFilesToExistingZip(File zipFile, File[] files) throws IOException {
	        // get a temp file
	    File tempFile = File.createTempFile(zipFile.getName(), null);
	        // delete it, otherwise you cannot rename your existing zip to it.
	    tempFile.delete();

	    boolean renameOk=zipFile.renameTo(tempFile);
	    if (!renameOk)
	    {
	        throw new RuntimeException("could not rename the file "+zipFile.getAbsolutePath()+" to "+tempFile.getAbsolutePath());
	    }
	    byte[] buf = new byte[1024];

	    ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
	    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

	    ZipEntry entry = zin.getNextEntry();
	    while (entry != null) {
	        String name = entry.getName();
	        boolean notInFiles = true;
	        for (File f : files) {
	            if (f.getName().equals(name)) {
	                notInFiles = false;
	                break;
	            }
	        }
	        if (notInFiles) {
	            // Add ZIP entry to output stream.
	            out.putNextEntry(new ZipEntry(name));
	            // Transfer bytes from the ZIP file to the output file
	            int len;
	            while ((len = zin.read(buf)) > 0) {
	                out.write(buf, 0, len);
	            }
	        }
	        entry = zin.getNextEntry();
	    }
	    // Close the streams        
	    zin.close();
	    // Compress the files
	    for (int i = 0; i < files.length; i++) {
	        InputStream in = new FileInputStream(files[i]);
	        // Add ZIP entry to output stream.
	        out.putNextEntry(new ZipEntry(files[i].getName()));
	        // Transfer bytes from the file to the ZIP file
	        int len;
	        while ((len = in.read(buf)) > 0) {
	            out.write(buf, 0, len);
	        }
	        // Complete the entry
	        out.closeEntry();
	        in.close();
	    }
	    // Complete the ZIP file
	    out.close();
	    tempFile.delete();
	}
	
	public static void addFilesToExistingZip(String fileName, String data) throws IOException {
		File zipFile = new File(exportFile);
		// get a temp file
		File tempFile = File.createTempFile(zipFile.getName(), null);
		// delete it, otherwise you cannot rename your existing zip to it.
		tempFile.delete();

		boolean renameOk=zipFile.renameTo(tempFile);
		if (!renameOk)
		{
			throw new RuntimeException("could not rename the file "+zipFile.getAbsolutePath()+" to "+tempFile.getAbsolutePath());
		}
		byte[] buf = new byte[1024];

		ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

		ZipEntry entry = zin.getNextEntry();
		while (entry != null) {
			String name = entry.getName();
			if (fileName.equals(name)) {
				InputStream in = new ByteArrayInputStream(data.getBytes());
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(fileName));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
				// Complete the ZIP file
			}
			else {
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(name));
				// Transfer bytes from the ZIP file to the output file
				int len;
				while ((len = zin.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			}
			entry = zin.getNextEntry();
		}
		// Close the streams        
		zin.close();
		out.close();
		// Compress the files
		tempFile.delete();
	}
	
	*/
}
