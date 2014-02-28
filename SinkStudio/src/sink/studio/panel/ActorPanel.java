package sink.studio.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import santhosh.DefaultListCellEditor;
import santhosh.DefaultMutableListModel;
import santhosh.JListMutable;
import sink.core.Sink;
import sink.studio.core.SinkStudio;
import sink.studio.core.StatusBar;
import sink.studio.core.Style;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ActorPanel extends JPanel implements ActionListener, ListSelectionListener{
	private static final long serialVersionUID = 1L;
	
	static JListMutable<String> actorsList;
	static DefaultMutableListModel<String> actorsModel = new DefaultMutableListModel<String>();
	JTextField tf = new JTextField();
	
	String[] btns = new String[]{
			"Delete", "trash"
	};

	public static Actor selectedActor = null;
	
	public ActorPanel(){
		super(new VerticalFlowLayout());
		UIUtils.setUndecorated(this, false);
		actorsList = new JListMutable<String>(actorsModel);
		actorsList.addListSelectionListener(this);
		actorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    UIUtils.setDrawFocus(tf, false);
	    UIUtils.setShadeWidth(tf, 0);
	    actorsList.setListCellEditor(new DefaultListCellEditor(tf));
		add(Style.createHeaderLabel("Actors"));
		add(Style.createButtonToolBar(this, btns));
		JScrollPane scrollPane = new JScrollPane(actorsList);
		scrollPane.setPreferredSize(new Dimension(200, 175));
		UIUtils.setDrawBorder(scrollPane, false);
		add(scrollPane);
		tf.addFocusListener(new FocusListener(){
			String oldName = "";
			@Override
			public void focusGained(FocusEvent arg0) {
				oldName = actorsList.getSelectedValue();
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				//if(contains(name))
				Sink.getScene().findActor(oldName).setName(actorsList.getSelectedValue());
			}
		});
	}

	public static void addActor(String actorName){
		ListSelectionListener l = actorsList.getListSelectionListeners()[0];
		actorsList.removeListSelectionListener(l);
		SinkStudio.log("AddActor: "+actorName);
		actorsModel.addElement(actorName);
		actorsList.addListSelectionListener(l);
	}
	
	public static void clear(){
		ListSelectionListener l = actorsList.getListSelectionListeners()[0];
		actorsList.removeListSelectionListener(l);
		actorsModel.clear();
		Sink.getScene().clearChildren();
		ActorPanel.setSelectedActor(null);
		actorsList.addListSelectionListener(l);
	}
	
	public static boolean contains(String name){
		return actorsModel.contains(name);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			selectedActor = Sink.getScene().findActor(actorsList.getSelectedValue());
			PropertyPanel.changeActor();
			StatusBar.updateSelected(actorsList.getSelectedValue());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		actorsList.removeListSelectionListener(this);
		Sink.getScene().removeActor(Sink.getScene().findActor(actorsList.getSelectedValue()));
		actorsModel.removeElement(actorsList.getSelectedValue());
		actorsList.addListSelectionListener(this);
	}

	public static void setSelectedActor(Actor actor){
		if(actor != null && actor.getName() != null && actor != Sink.getScene()){
			selectedActor = actor;
			actorsList.setSelectedIndex(actorsModel.indexOf(actor.getName()));
			StatusBar.updateSelected(actor.getName());
		}
		else{
			selectedActor = null;
			PropertyPanel.clear();
			actorsList.setSelectedIndex(-1);
			StatusBar.updateSelected("None");
		}
	}
	
	public static void updateSelectedActor(float x, float y){
		StatusBar.updateXY(x, y);
		if(selectedActor != null){
			selectedActor.setPosition(x, y);
			PropertyPanel.updateProperty("X", ""+x);
			PropertyPanel.updateProperty("Y", ""+y);
		}
	}
}
