package sink.main;

public class MainAndroid {
	 /*
     *  This may be needed for android
     */
	/*File jarName = new File(MainDesktop.class.getProtectionDomain().getCodeSource().getLocation().toURI());
	ZipFile zf = new ZipFile(jarName.getAbsoluteFile());
    Enumeration<? extends ZipEntry> e = zf.entries();
    while (e.hasMoreElements()) {
          ZipEntry ze =(ZipEntry)e.nextElement();
          String entryName = ze.getName();
		  if(entryName.startsWith("config") &&  entryName.endsWith(".json")) {
			  input = zf.getInputStream(ze);
			  br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
              String line;
              while((line = br.readLine()) != null)
            	  text+= line;
              br.close();
              input.close();
		  }
    }
    zf.close();*/
}
