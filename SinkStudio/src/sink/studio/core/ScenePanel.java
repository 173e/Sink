package sink.studio.core;

import java.awt.BorderLayout;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JPanel;

import sink.core.Config;
import sink.core.Sink;
import sink.studio.bar.EngineToolBar;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

final class ScenePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	static LwjglCanvas can;
	LwjglAWTCanvas canvas;
	
	public ScenePanel(){
		super(new BorderLayout());
	}
	
	public void createCanvas(){
		SinkStudio.log("Creating Scene Canvas");
		try{
			File file = new File("C:/CODE/Warsong/core/bin/"); 
			File file2 = new File("C:/CODE/Warsong/core/libs/"); //, file2.toURI().toURL()
			URL[] urls = new URL[]{file.toURI().toURL() }; 
			ClassLoader cl = new URLClassLoader(urls); 
			Class  cls = cl.loadClass("sink.core.Sink");
			Sink obj = (Sink) cls.newInstance();
			canvas = new  LwjglAWTCanvas(obj, false);
			Config.firstScene = "intro";
			Config.firstSceneClassName = "sink.studio.core.IntroScene";
			Config.targetWidth = 400;
			Config.targetHeight = 240;
			Config.isJar = false;
			add(canvas.getCanvas(), BorderLayout.CENTER);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void destroyCanvas(){
		SinkStudio.log("Destroying Scene Canvas");
		if(canvas != null){
			remove(canvas.getCanvas());
			canvas.stop();
			canvas = null;
			//Gdx.app.exit();
		}
	}
}