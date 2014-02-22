package sink.studio.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import sink.studio.core.LafStyle;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

final public class ScenePanel extends JPanel implements ActionListener {
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
		add(LafStyle.createHeaderLabel("Scenes"));
		JScrollPane scrollPane = new JScrollPane(scenesList);
		scrollPane.setPreferredSize(new Dimension(200, 175));
		UIUtils.setDrawBorder(scrollPane, false);
		initToolBar();
		add(scrollPane);
	}
	
	void initToolBar(){
		JPanel tools = LafStyle.createButtonToolBarPanel();
		tools.add(LafStyle.createExplorerToolPopButton("New Scene", "newfile", addField, this));
		tools.add(LafStyle.createButtonToolBar(this, btns));
		add(tools);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
