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

import sink.studio.core.Content;
import sink.studio.core.Export;
import sink.studio.core.Style;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.popup.NotificationManager;
import web.laf.lite.utils.UIUtils;


final public class FilePanel extends JPanel implements ListSelectionListener, ActionListener {
	private static final long serialVersionUID = 1L;
	
	static JList<String> filesList;
	static DefaultListModel<String> filesModel = new DefaultListModel<String>();
	
	JTextField classField, interfaceField, enumField;

	public FilePanel(){
		super(new VerticalFlowLayout());
		classField = new JTextField();
		interfaceField = new JTextField();
		enumField = new JTextField();
		UIUtils.setUndecorated(this, false);
		filesList = new JList<String>(filesModel);
		filesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		UIUtils.setHighlightRolloverCell(filesList, false);
		filesList.addListSelectionListener(this);
		add(Style.createHeaderLabel("Files"));
		initToolBar();
		JScrollPane scrollPane = new JScrollPane(filesList);
		scrollPane.setPreferredSize(new Dimension(200, 175));
		UIUtils.setDrawBorder(scrollPane, false);
		add(scrollPane);
		if(Content.checkProjectExists()){
			updateList();
			if(Content.checkFileExists()){
				filesList.setSelectedIndex(filesModel.indexOf(Content.getFile()));
			}
		}
	}
	void initToolBar(){
		JPanel tools = Style.createButtonToolBarPanel();
		tools.add(Style.createExplorerToolPopButton("New Class", "newclass", classField, (ActionListener)this));
		tools.add(Style.createExplorerToolPopButton("New Interface", "newinterface", interfaceField, (ActionListener)this));
		tools.add(Style.createExplorerToolPopButton("New Enum", "newenum", enumField, (ActionListener)this));
		tools.add(Style.createExplorerToolButton("Delete", "trash", this));
		add(tools);
	}
	
	public static void updateList(){
		filesModel.clear();
		for(String fname: Export.listFiles())
			filesModel.addElement(fname.replace("source/", "").replace(".java", ""));
	}
	
	public static void createJavaFile(String type, String name){
		if(name == null || name.isEmpty())
			return;
		name = name.replace(".java", "");
		if(filesModel.contains(name)){
			NotificationManager.showNotification("Error: File already exists: "+name);
			return;
		}
		switch(type){
			case "class": 
				Export.writeFile("source/"+name+".java", "public class "+name+" {\n\n    public "+name+"(){\n\n    }\n}");
				break;
			case "interface":
				Export.writeFile("source/"+name+".java", "public interface "+name+" {\n\n}");
				break;
			case "enum":
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
				Export.deleteFile("source/"+filesList.getSelectedValue()+".java");
				//Content.editorFile = "";
				//Content.editor.setText("");
				filesModel.removeElement(filesList.getSelectedValue());
				break;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			if(filesList.getSelectedValue() == null)
				return;
			if(Content.getFile().equals(filesList.getSelectedValue()))
				return;
			Content.editor.save();
			Content.setFile(filesList.getSelectedValue());
			Content.showEditor();
			Content.editor.load();
		}
	}
	
}