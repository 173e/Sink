package sink.studio.panel;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import sink.core.Config;
import sink.core.Sink;
import sink.studio.core.SinkStudio;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

final public class StudioPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	static LwjglCanvas can;
	LwjglAWTCanvas canvas;
	
	final Timer saveTimer = new Timer(10000, new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			StudioScene.save();
		}
	});
	
	
	public StudioPanel(){
		super(new BorderLayout());
		setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;
			public synchronized void drop(DropTargetDropEvent event) {
				try{
					Transferable transferable = event.getTransferable();
					if( transferable.isDataFlavorSupported(DataFlavor.stringFlavor )){
						event.acceptDrop( DnDConstants.ACTION_MOVE );
						String s = (String)transferable.getTransferData(DataFlavor.stringFlavor);
						SinkStudio.log(s);
						String[] data = s.split(":");
						StudioScene intro = (StudioScene) Sink.getScene();
						//float x = event.getLocation().x - getLocationOnScreen().x;
						// float y = event.getLocation().y - getLocationOnScreen().y;
						// x= (x/getWidth()) * Config.targetWidth;
						// y = y - (y/getHeight() * Config.targetHeight);
						switch(AssetType.getType(data[0])){
						case Font:
							intro.createLabel(data[1]);
							break;
						case Texture:
							intro.createImage(data[1]);
							break;
						case Animation:
							break;
						case Music:
							break;
						case Sound:
							break;
						case Particle:
							break;

						case Button:
							intro.createButton();
							break;
						case TextButton:
							intro.createTextButton();
							break;
						case None:
							break;
						default:
							break;
						}
						event.getDropTargetContext().dropComplete(true);
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
		});
	}
	
	public void createCanvas(){
		SinkStudio.log("Creating Studio Canvas");
		//SinkStudio.log(""+Frame.content.getPreferredSize().getWidth());
		try{
			canvas = new  LwjglAWTCanvas(new Sink(), false);
			Config.firstSceneClassName = StudioScene.class.getName();
			Config.targetWidth = 860;
			Config.targetHeight = 650;
			Config.isJar = false;
			Config.useDrag = true;
			Config.showFps = true;
			Config.showLogger = false;
			add(canvas.getCanvas(), BorderLayout.CENTER);
			canvas.getCanvas().addFocusListener(new FocusListener(){
				@Override
				public void focusGained(FocusEvent arg0) {
					saveTimer.start();
				}

				@Override
				public void focusLost(FocusEvent arg0) {
					saveTimer.stop();
					StudioScene.save();
				}
			});
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
		}
	}
}