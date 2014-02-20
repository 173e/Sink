package sink.studio.panel;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.badlogic.gdx.scenes.scene2d.Actor;

import sink.core.Sink;
import sink.studio.core.LafStyle;
import sink.studio.core.SinkStudio;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

public class PropertyPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	static JList<String> propList;
	static DefaultListModel<String> propModel = new DefaultListModel<String>();
	
	public PropertyPanel(){
		super(new VerticalFlowLayout());
		UIUtils.setUndecorated(this, false);
		propList = new JList<String>(propModel);
		add(LafStyle.createHeaderLabel("Properties"));
		JScrollPane scrollPane = new JScrollPane(propList);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		UIUtils.setDrawBorder(scrollPane, false);
		add(scrollPane);
	}
	
	public static void changeActor(String actorName){
		SinkStudio.log("ChangeActor: "+actorName);
		propModel.clear();
		Actor actor = Sink.getScene().findActor(actorName);
		propModel.addElement("x="+actor.getX());
		propModel.addElement("y="+actor.getY());
		propModel.addElement("w="+actor.getWidth());
		propModel.addElement("h="+actor.getHeight());
	}

}
