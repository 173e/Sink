package sink.studio.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JPanel;

import sink.core.Config;
import sink.core.Sink;
import sink.studio.core.SinkStudio;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

final public class SinkPanel extends JPanel implements DropTargetListener {
	private static final long serialVersionUID = 1L;
	static LwjglCanvas can;
	LwjglAWTCanvas canvas;
	
	DropTarget dropTarget = null;
	
	public SinkPanel(){
		super(new BorderLayout());
		dropTarget = new DropTarget( this, this );
		setPreferredSize(new Dimension(400, 240));
		setMaximumSize(new Dimension(400, 240));
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
			Config.firstSceneClassName = "sink.studio.panel.IntroScene";
			Config.targetWidth = 800;
			Config.targetHeight = 640;
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
	
	@Override
	public void dragEnter( DropTargetDragEvent event ){
	    event.acceptDrag( DnDConstants.ACTION_MOVE );
	 }

	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop(DropTargetDropEvent event) {
		try
	    {
	      Transferable transferable = event.getTransferable();

	      // we accept only Strings
	      if( transferable.isDataFlavorSupported( DataFlavor.stringFlavor ) )
	      {

	        event.acceptDrop( DnDConstants.ACTION_MOVE );
	        String s = ( String )transferable.getTransferData( DataFlavor.stringFlavor );
	        SinkStudio.log(s);
	        String[] data = s.split(":");
	        IntroScene intro = (IntroScene) Sink.getScene();
	        //float x = event.getLocation().x - getLocationOnScreen().x;
	       // float y = event.getLocation().y - getLocationOnScreen().y;
	       // x= (x/getWidth()) * Config.targetWidth;
	       // y = y - (y/getHeight() * Config.targetHeight);
	        switch(AssetType.getType(data[0])){
	        	case Font:
	        		intro.createLabel(data[1]);
	        		break;
	        	case Texture:
	        		intro.createTexture(data[1]);
					break;
				case Animation:
					break;
				case Music:
					break;
				case None:
					break;
				case Particle:
					break;
				case Sound:
					break;
				default:
					break;
	        }
	        event.getDropTargetContext().dropComplete( true );
	      }
	      else{
	        event.rejectDrop();
	      }
	    }
	    catch( Exception exception ){
	      System.err.println( "Exception" + exception.getMessage() );
	      event.rejectDrop();
	    }
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		
	}
}