package sink.studio.bar;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import sink.core.Asset;
import sink.core.Sink;
import sink.studio.core.LafStyle;
import sink.studio.core.SinkStudio;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

final public class RightSideBar extends JPanel{
	private static final long serialVersionUID = 1L;
	
	Actor currentActor;
	
	static JList<String> actorsList;
	static DefaultListModel<String> actorsModel = new DefaultListModel<String>();
	
	static JList<String> propList;
	static DefaultListModel<String> propModel = new DefaultListModel<String>();
	
	public RightSideBar(){
		super(new VerticalFlowLayout(0, 10));
		initObjects();
		initProperties();
		initWidgets();
	}

	
	@Override
	public void paintComponent(Graphics g){
		LafStyle.drawVerticalBar(g, getWidth(), getHeight());
	}
	
	
	void initObjects(){
		JPanel libPanel = new JPanel(new VerticalFlowLayout());
		UIUtils.setUndecorated(libPanel, false);
		actorsList = new JList<String>(actorsModel);
		actorsList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				changeActor(actorsList.getSelectedValue());
			}
		});
		libPanel.add(LafStyle.createHeaderLabel("Actors"));
		JScrollPane scrollPane = new JScrollPane(actorsList);
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
	
	void initWidgets(){
		JPanel libPanel = new JPanel(new VerticalFlowLayout());
		UIUtils.setUndecorated(libPanel, false);
		final JList<String> list = new JList<String>(new String[]{"Text", "Image", "Button","TextButton","ScrollPane"});
		list.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				switch(list.getSelectedValue()){
					case "Text": 
						//Display Dialog
						break;
				}
			}
		});
		libPanel.add(LafStyle.createHeaderLabel("Widgets"));
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(200, 180));
		UIUtils.setDrawBorder(scrollPane, false);
		libPanel.add(scrollPane);
		add(libPanel);
	}
	
	public static void addActor(String actorName){
		SinkStudio.log("AddActor: "+actorName);
		actorsModel.addElement(actorName);
	}
	
	public void changeActor(String actorName){
		SinkStudio.log("ChangeActor: "+actorName);
		propModel.clear();
		currentActor = Sink.getScene().findActor(actorName);
		propModel.addElement("x="+currentActor.getX());
		propModel.addElement("y="+currentActor.getY());
		propModel.addElement("w="+currentActor.getWidth());
		propModel.addElement("h="+currentActor.getHeight());
	}
}

