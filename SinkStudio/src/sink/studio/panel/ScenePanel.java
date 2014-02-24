package sink.studio.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
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
		scenesList.addListSelectionListener(this);
		add(Style.createHeaderLabel("Scenes"));
		JScrollPane scrollPane = new JScrollPane(scenesList);
		scrollPane.setPreferredSize(new Dimension(200, 175));
		UIUtils.setDrawBorder(scrollPane, false);
		initToolBar();
		add(scrollPane);
		//updateList();
	}
	
	void initToolBar(){
		JPanel tools = Style.createButtonToolBarPanel();
		tools.add(Style.createExplorerToolPopButton("New Scene", "newfile", addField, this));
		tools.add(Style.createButtonToolBar(this, btns));
		add(tools);
	}
	
	public static void updateList(){
		scenesModel.clear();
		for(String fname: Export.listFiles())
			scenesModel.addElement(fname.replace("source/", "").replace(".java", ""));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			SinkStudio.log("clicked");// bug
			if(!Content.currentView.equals("Scene"))
				return;
			if(scenesList.getSelectedValue() == null)
				return;
			if(Content.getFile().equals(scenesList.getSelectedValue()))
				return;
			StudioScene intro = (StudioScene) Sink.getScene();
			intro.save();
			Content.setFile(scenesList.getSelectedValue());
			//Content.showScene();
			intro.load();
		}
	}

}
