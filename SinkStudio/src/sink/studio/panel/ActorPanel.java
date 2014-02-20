package sink.studio.panel;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sink.studio.core.LafStyle;
import sink.studio.core.SinkStudio;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

public class ActorPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	static JList<String> actorsList;
	static DefaultListModel<String> actorsModel = new DefaultListModel<String>();
	
	public ActorPanel(){
		super(new VerticalFlowLayout());
		UIUtils.setUndecorated(this, false);
		actorsList = new JList<String>(actorsModel);
		actorsList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				PropertyPanel.changeActor(actorsList.getSelectedValue());
				SinkScene.setSelectedActor(actorsList.getSelectedValue());
			}
		});
		add(LafStyle.createHeaderLabel("Actors"));
		JScrollPane scrollPane = new JScrollPane(actorsList);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		UIUtils.setDrawBorder(scrollPane, false);
		add(scrollPane);
	}

	public static void addActor(String actorName){
		SinkStudio.log("AddActor: "+actorName);
		actorsModel.addElement(actorName);
	}

	public static void selectActor(String actorName){
		actorsList.setSelectedIndex(actorsModel.indexOf(actorName));
	}
}
