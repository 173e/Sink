package sink.studio.panel;

import java.awt.Dimension;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sink.core.Asset;
import sink.studio.core.LafStyle;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

final public class AssetPanel extends JPanel implements ActionListener, DragSourceListener, DragGestureListener{
	private static final long serialVersionUID = 1L;
	
	static JList<String> assetList;
	static DefaultListModel<String> assetModel = new DefaultListModel<String>();
	
	String[] btns = new String[]{
			"Font", "newfile","Texture", "eopen", "Animation", "eopen", "Music", "eopen",
			"Sound", "eopen", "Particle", "eopen"
	};
	
	AssetType selectedAsset = AssetType.Font;
	
	DragSource dragSource = new DragSource();

	public AssetPanel(){
		super(new VerticalFlowLayout());
		UIUtils.setUndecorated(this, false);
		assetList = new JList<String>(assetModel);
		JScrollPane scrollPane = new JScrollPane(assetList);
		scrollPane.setPreferredSize(new Dimension(200, 155));
		UIUtils.setDrawBorder(scrollPane, false);
		
		add(LafStyle.createHeaderLabel("Assets"));
		add(LafStyle.createButtonToolBar(this, btns));
		add(scrollPane);
		dragSource.addDragSourceListener(this);
	    dragSource.createDefaultDragGestureRecognizer( assetList, DnDConstants.ACTION_MOVE, this );
	}
	
	public static void updateAsset(){
		for(String names: Asset.fontMap.keys())
			assetModel.addElement(names);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		assetModel.clear();
		switch(((JButton)event.getSource()).getToolTipText()){
			case "Font": 
				for(String names: Asset.fontMap.keys()) 
					assetModel.addElement(names);
				selectedAsset = AssetType.Font;
				break;
			case "Texture": 
				for(String names: Asset.texMap.keys()) 
					assetModel.addElement(names);
				selectedAsset = AssetType.Texture;
				break;
			case "Animation": 
				for(String names: Asset.animMap.keys()) 
					assetModel.addElement(names);
				selectedAsset = AssetType.Animation;
				break;
			case "Music": 
				for(String names: Asset.musicMap.keys()) 
					assetModel.addElement(names);
				selectedAsset = AssetType.Music;
				break;
			case "Sound": 
				for(String names: Asset.soundMap.keys()) 
					assetModel.addElement(names);
				selectedAsset = AssetType.Sound;
				break;
			//case "Particle": 	for(String names: Asset.musicMap.keys()) assetModel.addElement(names); break;
		}
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent event) {
		Object selected = assetList.getSelectedValue();
	    if( selected != null )
	    {
	      StringSelection text = new StringSelection(AssetType.getString(selectedAsset)+":"+selected.toString());
	      dragSource.startDrag( event, DragSource.DefaultMoveDrop, text, this);
	    }
	    else
	    {
	      System.out.println( "nothing was selected" );
	    }
	}

	@Override
	public void dragDropEnd(DragSourceDropEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragEnter(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragExit(DragSourceEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragOver(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

enum AssetType {
	None,
	Font,
	Texture,
	Animation,
	Music,
	Sound,
	Particle;
	
	public static String getString(AssetType at){
		switch(at){
			case Font: return "Font";
			case Texture: return "Texture";
			case Animation: return "Animation";
			case Music: return "Music";
			case Sound: return "Sound";
			case Particle: return "Particle";
			default: return "";
		}
	}
	
	public static AssetType getType(String name){
		switch(name){
			case "Font": return AssetType.Font;
			case "Texture": return AssetType.Texture;
			case "Animation": return AssetType.Animation;
			case "Music": return AssetType.Music;
			case "Sound": return AssetType.Sound;
			case "Particle": return AssetType.Particle;
			default: return AssetType.None;
		}
	}
}