package sink.studio.panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sink.studio.core.LafStyle;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;


final public class PropertyPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	static JList<String> propList;
	static DefaultListModel<String> propModel = new DefaultListModel<String>();
	
	String[] btns = new String[]{
			"Font", "newfile","Texture", "eopen", "Animation", "eopen"
	};

	public PropertyPanel(){
		super(new VerticalFlowLayout());
		UIUtils.setUndecorated(this, false);
		propList = new JList<String>(propModel);
		add(LafStyle.createHeaderLabel("Properties"));
		JScrollPane scrollPane = new JScrollPane(propList);
		scrollPane.setPreferredSize(new Dimension(200, 175));
		UIUtils.setDrawBorder(scrollPane, false);
		add(LafStyle.createButtonToolBar(this, btns));
		add(scrollPane);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
}