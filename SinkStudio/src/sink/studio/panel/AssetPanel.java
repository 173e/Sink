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

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import santhosh.DefaultListCellEditor;
import santhosh.DefaultMutableListModel;
import santhosh.JListMutable;
import sink.core.Asset;
import sink.studio.core.Style;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

final public class AssetPanel extends JPanel implements ActionListener, DragSourceListener, DragGestureListener{
	private static final long serialVersionUID = 1L;
	
	static JListMutable<String> assetsList;
	static DefaultMutableListModel<String> assetsModel = new DefaultMutableListModel<String>();
	
	String[] btns = new String[]{
			"New File", "newfile", "Delete", "trash",
			"Font", "f","Texture", "t", 
			"Animation", "a", "Music", "m",
			"Sound", "s", "Particle", "p"
	};
	
	AssetType selectedAsset = AssetType.Font;
	
	DragSource dragSource = new DragSource();

	public AssetPanel(){
		super(new VerticalFlowLayout());
		UIUtils.setUndecorated(this, false);
		assetsList = new JListMutable<String>(assetsModel);
		assetsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		assetsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		UIUtils.setHighlightRolloverCell(assetsList, false);
		//UIUtils.setDecorateSelection(assetsList, false);
	    JTextField tf = new JTextField();
	    UIUtils.setDrawFocus(tf, false);
	    UIUtils.setShadeWidth(tf, 0);
	    assetsList.setListCellEditor(new DefaultListCellEditor(tf));
		JScrollPane scrollPane = new JScrollPane(assetsList);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(200, 155));
		UIUtils.setDrawBorder(scrollPane, false);
		
		add(Style.createHeaderLabel("Assets"));
		add(Style.createButtonToolBar(this, btns));
		add(scrollPane);
		dragSource.addDragSourceListener(this);
	    dragSource.createDefaultDragGestureRecognizer(assetsList, DnDConstants.ACTION_MOVE, this);
	}
	
	public static void updateAsset(){
		for(String names: Asset.fontMap.keys())
			assetsModel.addElement(names);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		assetsModel.clear();
		switch(((JButton)event.getSource()).getToolTipText()){
			case "Font": 
				for(String names: Asset.fontMap.keys()) 
					assetsModel.addElement(names);
				selectedAsset = AssetType.Font;
				break;
			case "Texture": 
				for(String names: Asset.texMap.keys()) 
					assetsModel.addElement(names);
				selectedAsset = AssetType.Texture;
				break;
			case "Animation": 
				for(String names: Asset.animMap.keys()) 
					assetsModel.addElement(names);
				selectedAsset = AssetType.Animation;
				break;
			case "Music": 
				for(String names: Asset.musicMap.keys()) 
					assetsModel.addElement(names);
				selectedAsset = AssetType.Music;
				break;
			case "Sound": 
				for(String names: Asset.soundMap.keys()) 
					assetsModel.addElement(names);
				selectedAsset = AssetType.Sound;
				break;
			//case "Particle": 	for(String names: Asset.musicMap.keys()) assetModel.addElement(names); break;
		}
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent event) {
	    StringSelection text = new StringSelection(AssetType.getString(selectedAsset)+":"+assetsList.getSelectedValue());
	    dragSource.startDrag( event, DragSource.DefaultMoveDrop, text, this);
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