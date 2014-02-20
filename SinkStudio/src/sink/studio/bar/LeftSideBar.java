package sink.studio.bar;

import java.awt.Graphics;

import javax.swing.JPanel;

import sink.studio.core.LafStyle;
import sink.studio.panel.AssetPanel;
import sink.studio.panel.PropertyPanel;
import sink.studio.panel.ScenePanel;
import web.laf.lite.layout.VerticalFlowLayout;

final public class LeftSideBar extends JPanel{
	private static final long serialVersionUID = 1L;
	
	
	static final ScenePanel scenePanel = new ScenePanel();
	static final PropertyPanel propertyPanel = new PropertyPanel();
	static final AssetPanel assetPanel = new AssetPanel();
	

	public LeftSideBar(){
		super(new VerticalFlowLayout(0, 10));
		add(scenePanel);
		add(propertyPanel);
		add(assetPanel);
	}
	
	@Override
	public void paintComponent(Graphics g){
		LafStyle.drawVerticalBar(g, getWidth (), getHeight ());
	}
}