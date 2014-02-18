package sink.studio.core;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import sink.studio.bar.ExplorerToolBar;
import sink.studio.bar.LeftSideBar;
import sink.studio.bar.RightSideBar;
import sink.studio.bar.StatusBar;
import sink.studio.bar.ToolBar;


final public class Frame extends JFrame implements WindowListener{
	private static final long serialVersionUID = 1L;
	static ToolBar toolBar;
	static RightSideBar rightSideBar;
	static LeftSideBar leftSideBar;
	static StatusBar statusBar;
	static Content content;
	static JSplitPane splitPane;
	

    public Frame() {
       setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       addWindowListener(this);
       Config.addHotKey(KeyEvent.VK_T, KeyEvent.ALT_MASK, new AbstractAction() {
		private static final long serialVersionUID = 1L;
		@Override
         public void actionPerformed(ActionEvent e) {
           Frame.toggleToolBar();
         }
       });
    }

    public void initToolBar(){
    	toolBar = new ToolBar();
    	add(toolBar, BorderLayout.NORTH);
    }
    
    public void initSideBar(){
   	 	rightSideBar = new RightSideBar();
   	 	leftSideBar = new LeftSideBar();
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
	
	public static void togglePanelToolBar(){
		Content.panelToolBar.setVisible(!Content.panelToolBar.isVisible());
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