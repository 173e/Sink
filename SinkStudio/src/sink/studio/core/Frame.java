package sink.studio.core;


import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import sink.studio.panel.ActorPanel;
import sink.studio.panel.AssetPanel;
import sink.studio.panel.FilePanel;
import sink.studio.panel.PropertyPanel;
import sink.studio.panel.ScenePanel;
import sink.studio.panel.WidgetPanel;
import web.laf.lite.layout.VerticalFlowLayout;


final public class Frame extends JFrame implements WindowListener{
	private static final long serialVersionUID = 1L;
	static ToolBar toolBar;
	static JPanel rightSideBar;
	static JPanel leftSideBar;
	static StatusBar statusBar;
	static Content content;
	static JSplitPane splitPane;
	

    public Frame() {
       setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       addWindowListener(this);
    }

    public void initToolBar(){
    	toolBar = new ToolBar();
    	add(toolBar, BorderLayout.NORTH);
    }
    
    public void initSideBar(){
   	 	rightSideBar = new JPanel(new VerticalFlowLayout(0, 10)){
			private static final long serialVersionUID = 1L;
			@Override
	   		public void paintComponent(Graphics g){
	   			LafStyle.drawVerticalBar(g, getWidth(), getHeight());
	   		}
   	 	};
   	 	rightSideBar.add(new ActorPanel());
   	 	rightSideBar.add(new PropertyPanel());
   	 	rightSideBar.add(new WidgetPanel());
   	 	
   	 	leftSideBar = new JPanel(new VerticalFlowLayout(0, 10)){
   	 	private static final long serialVersionUID = 1L;
	   	 	@Override
	   		public void paintComponent(Graphics g){
	   			LafStyle.drawVerticalBar(g, getWidth(), getHeight());
	   		}
   	 	};
   	 	leftSideBar.add(new ScenePanel());
   	 	leftSideBar.add(new FilePanel());
   	 	leftSideBar.add(new AssetPanel());
   	 	
   	 	add(rightSideBar, BorderLayout.EAST);
   	 	add(leftSideBar, BorderLayout.WEST);
   }
    
   public void initStatusBar(){
   	 	statusBar = new StatusBar();
   	 	add(statusBar, BorderLayout.SOUTH);
   }
    
    public void initContent(){
    	content = new Content();
    	add(content, BorderLayout.CENTER);
    }
    
    public static void toggleToolBar(){
    	toolBar.setVisible(!toolBar.isVisible());
    }
    
    public static void toggleStatusBar(){
    	statusBar.setVisible(!statusBar.isVisible());
    }
    
    public static void toggleLeftSideBar(){
    	leftSideBar.setVisible(!leftSideBar.isVisible());
    }
    
    public static void toggleRightSideBar(){
    	rightSideBar.setVisible(!rightSideBar.isVisible());
    }
    
    public static void toggleEditor(){
		content.setVisible(!content.isVisible());
		splitPane.setRightComponent(content.isVisible() ? content : null);
        splitPane.revalidate();
	}
    
	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		Content.editor.save();
		content.dispose();
		dispose();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}