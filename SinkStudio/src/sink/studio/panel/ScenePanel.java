package sink.studio.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sink.core.Sink;
import sink.studio.core.Content;
import sink.studio.core.Export;
import sink.studio.core.Style;
import sink.studio.core.SinkStudio;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.popup.NotificationManager;
import web.laf.lite.utils.UIUtils;

final public class ScenePanel extends JPanel implements ListSelectionListener, ActionListener {
	private static final long serialVersionUID = 1L;
	
	static JList<String> scenesList;
	static DefaultListModel<String> scenesModel = new DefaultListModel<String>();
	
	final JTextField addField = new JTextField ();
	String[] btns = new String[]{
			"Delete", "trash", "Resume", "resume","Pause", "pause", "Stop", "stop"
	};
	
	public ScenePanel(){
		super(new VerticalFlowLayout());
		UIUtils.setUndecorated(this, false);
		scenesList = new JList<String>(scenesModel);
		scenesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(Style.createHeaderLabel("Scenes"));
		JScrollPane scrollPane = new JScrollPane(scenesList);
		scrollPane.setPreferredSize(new Dimension(200, 175));
		UIUtils.setDrawBorder(scrollPane, false);
		initToolBar();
		add(scrollPane);
		if(Content.projectExists()){
			updateList();
			if(Content.sceneExists()){
				scenesList.setSelectedIndex(scenesModel.indexOf(Content.getScene()));
			}
		}
		scenesList.addListSelectionListener(this);
	}
	
	void initToolBar(){
		JPanel tools = Style.createButtonToolBarPanel();
		tools.add(Style.createExplorerToolPopButton("New Scene", "newfile", addField, this));
		tools.add(Style.createButtonToolBar(this, btns));
		add(tools);
	}
	
	public static void updateList(){
		scenesModel.clear();
		for(String fname: Export.listFiles("scene/"))
			scenesModel.addElement(fname.replace("scene/", "").replace(".json", ""));
	}
	
	public void createSceneFile(){
		if(addField.getText() == null || addField.getText().isEmpty())
			return;
		String name = addField.getText().replace(".json", "");
		if(scenesModel.contains(name)){
			NotificationManager.showNotification("Error: File already exists: "+name);
		}
		else{
			Export.writeFile("scene/"+name+".json", "");
			scenesModel.addElement(name);
		}
		addField.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch(((JButton)event.getSource()).getToolTipText()){
			case "New Scene": createSceneFile();break;
			case "Delete":
				Export.deleteFile("scene/"+scenesList.getSelectedValue()+".json");
				Content.setScene("");
				scenesModel.removeElement(scenesList.getSelectedValue());
				break;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			if(scenesList.getSelectedValue() == null)
				return;
			StudioScene intro = (StudioScene) Sink.getScene();
			SinkStudio.log("Scene Selected: "+scenesList.getSelectedValue());
			Content.setScene(scenesList.getSelectedValue());
			if(!Content.currentView.equals("Scene"))
				Content.toggleView(3);
			ActorPanel.clear();
			intro.load();
		}
	}

}
