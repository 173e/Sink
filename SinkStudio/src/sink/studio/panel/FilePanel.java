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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sink.studio.core.Content;
import sink.studio.core.Export;
import sink.studio.core.LafStyle;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.popup.NotificationManager;
import web.laf.lite.utils.UIUtils;


final public class FilePanel extends JPanel implements ListSelectionListener, ActionListener {
	private static final long serialVersionUID = 1L;
	
	static JList<String> propList;
	static DefaultListModel<String> propModel = new DefaultListModel<String>();
	
	JTextField classField, interfaceField, enumField;

	public FilePanel(){
		super(new VerticalFlowLayout());
		classField = new JTextField();
		interfaceField = new JTextField();
		enumField = new JTextField();
		UIUtils.setUndecorated(this, false);
		propList = new JList<String>(propModel);
		propList.addListSelectionListener(this);
		add(LafStyle.createHeaderLabel("Files"));
		initToolBar();
		JScrollPane scrollPane = new JScrollPane(propList);
		scrollPane.setPreferredSize(new Dimension(200, 175));
		UIUtils.setDrawBorder(scrollPane, false);
		add(scrollPane);
		updateList();
	}
	void initToolBar(){
		JPanel tools = LafStyle.createButtonToolBarPanel();
		tools.add(LafStyle.createExplorerToolPopButton("New Class", "newclass", classField, (ActionListener)this));
		tools.add(LafStyle.createExplorerToolPopButton("New Interface", "newinterface", interfaceField, (ActionListener)this));
		tools.add(LafStyle.createExplorerToolPopButton("New Enum", "newenum", enumField, (ActionListener)this));
		tools.add(LafStyle.createExplorerToolButton("Delete", "trash", this));
		add(tools);
	}
	
	public static void updateList(){
		propModel.clear();
		for(String fname: Export.listFiles())
			propModel.addElement(fname.replace("source/", ""));
	}
	
	public static void createJavaFile(String type, String name){
		if(name != null && name.isEmpty())
			return;
		if(name.endsWith(".java"))
			if(propModel.contains(name)){
				NotificationManager.showNotification("Error: File already exists: "+name);
				return;
			}
		else
			if(propModel.contains(name+".java")){ //bug
				NotificationManager.showNotification("Error: File already exists: "+name);
				return;
			}
		switch(type){
			case "class": 
				if(name.endsWith(".java"))
					Export.writeFile("source/"+name, "public class "+name.replace(".java", "")+" {\n\n    public "+name.replace(".java", "")+"(){\n\n    }\n}");
				else
					Export.writeFile("source/"+name+".java", "public class "+name+" {\n\n    public "+name+"(){\n\n    }\n}");
				break;
			case "interface":
				if(name.endsWith(".java"))
					Export.writeFile("source/"+name, "public interface "+name.replace(".java", "")+" {\n\n}");
				else
					Export.writeFile("source/"+name+".java", "public interface "+name+" {\n\n}");
				break;
			case "enum":
				if(name.endsWith(".java"))
					Export.writeFile("source/"+name, "public enum "+name.replace(".java", "")+" {\n\n}");
				else
					Export.writeFile("source/"+name+".java", "public enum "+name+" {\n\n}");
				break;
			default: break;
		}
		updateList();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		switch(((JButton)event.getSource()).getToolTipText()){
			case "New Class": createJavaFile("class", classField.getText());classField.setText("");break;
			case "New Interface": createJavaFile("interface", interfaceField.getText());interfaceField.setText("");break;
			case "New Enum": createJavaFile("enum", enumField.getText());enumField.setText("");break;
			case "Delete":
				Export.deleteFile("source/"+propList.getSelectedValue());
				Content.editorFile = null;
				Content.editor.setText("");
				updateList();
				break;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Content.showEditor();
		Content.editor.save();
		Content.editorFile = propList.getSelectedValue();
		if(Content.editorFile != null && !Content.editorFile.isEmpty())
			Content.editor.setText(Export.readFile("source/"+Content.editorFile));
	}
	
}