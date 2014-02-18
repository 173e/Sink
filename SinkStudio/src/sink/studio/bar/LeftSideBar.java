package sink.studio.bar;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ArrayMap;

import sink.core.Asset;
import sink.studio.core.LafStyle;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

final public class LeftSideBar extends JPanel{
	private static final long serialVersionUID = 1L;
	
	static JList<String> scenesList;
	static DefaultListModel<String> scenesModel = new DefaultListModel<String>();
	
	static JList<String> propList;
	static DefaultListModel<String> propModel = new DefaultListModel<String>();
	
	static JList<String> assetList;
	static DefaultListModel<String> assetModel = new DefaultListModel<String>();
	

	public LeftSideBar(){
		super(new VerticalFlowLayout(0, 10));
		initScenes();
		initProperties();
		initAssets();
	}
	
	@Override
	public void paintComponent(Graphics g){
		LafStyle.drawVerticalBar(g, getWidth (), getHeight ());
	}
	
	void initScenes(){
		JPanel libPanel = new JPanel(new VerticalFlowLayout());
		UIUtils.setUndecorated(libPanel, false);
		scenesList = new JList<String>(scenesModel);
		libPanel.add(LafStyle.createHeaderLabel("Scenes"));
		JScrollPane scrollPane = new JScrollPane(scenesList);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		UIUtils.setDrawBorder(scrollPane, false);
		libPanel.add(scrollPane);
		add(libPanel);
	}
	
	void initProperties(){
		JPanel libPanel = new JPanel(new VerticalFlowLayout());
		UIUtils.setUndecorated(libPanel, false);
		propList = new JList<String>(propModel);
		libPanel.add(LafStyle.createHeaderLabel("Properties"));
		JScrollPane scrollPane = new JScrollPane(propList);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		UIUtils.setDrawBorder(scrollPane, false);
		libPanel.add(scrollPane);
		add(libPanel);
	}
	
	void initAssets(){
		JPanel libPanel = new JPanel(new VerticalFlowLayout());
		UIUtils.setUndecorated(libPanel, false);
		assetList = new JList<String>(assetModel);
		libPanel.add(LafStyle.createHeaderLabel("Assets"));
		JScrollPane scrollPane = new JScrollPane(assetList);
		scrollPane.setPreferredSize(new Dimension(200, 180));
		UIUtils.setDrawBorder(scrollPane, false);
		libPanel.add(scrollPane);
		add(libPanel);
	}
	
	public static void addScene(){
		
	}
	
	public static void changeScene(){
		
	}
	
	public static void updateAsset(){
		for(String names: Asset.fontMap.keys())
			assetModel.addElement(names);
	}
}