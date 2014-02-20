package sink.studio.bar;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

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
import sink.studio.panel.IntroScene;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

final public class RightSideBar extends JPanel implements DragSourceListener, DragGestureListener {
	private static final long serialVersionUID = 1L;
	
	Actor currentActor;
	
	static JList<String> actorsList;
	static DefaultListModel<String> actorsModel = new DefaultListModel<String>();
	
	static JList<String> propList;
	static DefaultListModel<String> propModel = new DefaultListModel<String>();
	
	static JList<String> widgetList;
	static DefaultListModel<String> widgetModel = new DefaultListModel<String>();
	
	DragSource dragSource = new DragSource();
	
	public RightSideBar(){
		super(new VerticalFlowLayout(0, 10));
		initObjects();
		initProperties();
		initWidgets();
		dragSource.addDragSourceListener(this);
	    dragSource.createDefaultDragGestureRecognizer( widgetList, DnDConstants.ACTION_MOVE, this );
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
				IntroScene.setSelectedActor(actorsList.getSelectedValue());
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
		widgetList = new JList<String>(new String[]{"Text", "Image", "Button","TextButton","ScrollPane"});
		widgetList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				switch(widgetList.getSelectedValue()){
					case "Text": 
						//Display Dialog
						break;
				}
			}
		});
		libPanel.add(LafStyle.createHeaderLabel("Widgets"));
		JScrollPane scrollPane = new JScrollPane(widgetList);
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
	
	public static void selectActor(String actorName){
		actorsList.setSelectedIndex(actorsModel.indexOf(actorName));
	}
	
	public static void unselect(){
		//actorsList.setS
	}


	@Override
	public void dragDropEnd(DragSourceDropEvent arg0) {
		SinkStudio.log("Dropped");
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


	@Override
	public void dragGestureRecognized(DragGestureEvent event) {
		Object selected = widgetList.getSelectedValue();
	    if( selected != null )
	    {
	      StringSelection text = new StringSelection( selected.toString() );
	      dragSource.startDrag( event, DragSource.DefaultMoveDrop, text, this );
	    }
	    else
	    {
	      System.out.println( "nothing was selected" );
	    }
	}
}

