package sink.studio.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import web.laf.lite.widget.Register;

public class SinkStudio {
	public static final String version = "0.15";
	
	public static Frame frame;
	private static int ind = 8;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	 try{
            		 Register.init("sinkstudio");
            		if(!Register.getLaf().isEmpty())
            			UIManager.setLookAndFeel(Register.getLaf());
            		else
            			UIManager.setLookAndFeel("web.laf.lite.ui.WebLookAndFeelLite");
                 }
                 catch ( Throwable e ){
                     e.printStackTrace ();
                 }
            	createSplash();
            	//skyFrame.pack();
       	     	frame.setVisible(true);
       	     	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
       	     	StatusBar.redirectSystemStreams();
            }
        });
	}
	
	public static void setLaf(final String lafName){
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            		try{
            			UIManager.setLookAndFeel(lafName);
            		}
            		catch ( Throwable e ){
            			e.printStackTrace ();
            		}
            		SwingUtilities.updateComponentTreeUI(frame.getRootPane());
            }
		});
	}
	
	
	
	/**
     * Forces global components UI update in all existing application windows.
     */
    static void updateAllComponentUIs ()
    {
        for ( Window window : Window.getWindows () )
        {
            SwingUtilities.updateComponentTreeUI ( window );
        }
    }
    
    public static ArrayList<File> projects = new ArrayList<File>();
	
	
    
    static void createFrameWithoutSplash(){
	     Asset.loadIcons();
	     frame = new Frame();
	     frame.setIconImage(Asset.icon("icon").getImage());
	     frame.setTitle("Sink Studio");
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 frame.setSize(screenSize);
		 frame.setLocation(0, 0);
		 frame.setLocationRelativeTo(null);
	     frame.initSideBar();
	     frame.initStatusBar();
	     frame.initContent();
	     frame.initToolBar();
    }
	
	static void createSplash(){
		Graphics2D g = null;
	    final SplashScreen splash = SplashScreen.getSplashScreen();
	    if (splash == null) createFrameWithoutSplash();
	    else {
	     g = splash.createGraphics();  
	     renderSplashFrame(g, 1, "Initializing");
	     //Config.init();
	     splash.update();
	     renderSplashFrame(g, 2, "Loading Icons");
	     splash.update();
	     Asset.loadIcons();
	     renderSplashFrame(g, 3, "Loading Frame");
	     splash.update();
	     frame = new Frame();
	     frame.setIconImage(Asset.icon("icon").getImage());
	     frame.setTitle("SkyCode");
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 frame.setSize(screenSize);
		 frame.setLocation(0, 0);
		 frame.setLocationRelativeTo(null);
		 renderSplashFrame(g, 4, "Loading Explorer");
	     splash.update();
	     renderSplashFrame(g, 5, "Loading SideBar");
	     splash.update();
	     frame.initSideBar();
	     renderSplashFrame(g, 6, "Loading Status Bar");
	     splash.update();
	     frame.initStatusBar();
	     renderSplashFrame(g, 7, "Loading Content");
	     splash.update();
	     frame.initContent();
	     renderSplashFrame(g, 8, "Loading ToolBar");
	     splash.update();
	     frame.initToolBar();
	     renderSplashFrame(g, 9, "Finished");
	     splash.update();   
	    }
	}
	
	static int height = 200;
	static int width = 400;
	static int fontHeight = 12;
	
	static void renderSplashFrame(Graphics2D g, int frame,  String content) {
		g.setComposite(AlphaComposite.Src);
		g.setColor(Color.BLACK);  
		g.setPaintMode();  
		g.fillRect(0, height - 29, width, 17);  
		g.setColor(Color.RED);
		ind--;
		if(ind == 0) ind = 1;
		for(int i= 0; i < frame; i ++)
			for(int j= 0; j < i*frame/ind; j ++){
				int x = 9 *j;
				/*if(x > 0 || x < 0) g.setColor(Color.RED);
				if(x > width/4) g.setColor(Color.ORANGE);
				if(x > width/2) g.setColor(Color.YELLOW);
				if(x > 3*width/4) g.setColor(Color.GREEN);*/
				g.fillRect(x , height - 28, 8, 8);
			}
		g.setColor(Color.ORANGE);
		for(int i= 0; i < frame; i ++)
			for(int j= 0; j < i*frame/1.1; j ++)
				g.fillRect(9 * j , height - 20, 8, 8);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, height - 12, width, fontHeight);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Purisa", Font.PLAIN, 13));
		g.drawString(content, 0, height - 2);	
	}

	public static void log(String text){
		System.out.println(text);
	}
}