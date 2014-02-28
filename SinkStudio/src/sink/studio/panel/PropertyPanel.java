package sink.studio.panel;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import sink.core.Sink;
import sink.studio.core.SinkStudio;
import sink.studio.core.Style;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class PropertyPanel extends JPanel implements TableModelListener {
	private static final long serialVersionUID = 1L;
	
	static DefaultTableModel propModel = new DefaultTableModel();
	static DefaultTableColumnModel propColumnModel = new DefaultTableColumnModel();
	static JTable propTable = new JTable(propModel);
	
	public PropertyPanel(){
		super(new VerticalFlowLayout());
		UIUtils.setUndecorated(this, false);
		propTable.setShowGrid(true);
		propTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		propModel.setColumnCount(2);
		propModel.setColumnIdentifiers(new String[]{"Property", "Value"});
		populate();
		propModel.addTableModelListener(this);
		add(Style.createHeaderLabel("Properties"));
		JScrollPane scrollPane = new JScrollPane(propTable);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(200, 195));
		UIUtils.setDrawBorder(scrollPane, false);
		add(scrollPane);
	}
	
	public static void populate(){
		for(int i=0;i<11;i++)	
			propModel.addRow(new String[]{"", ""});
	}
	
	public static void clear(){
		TableModelListener t = propModel.getTableModelListeners()[0];
		propModel.removeTableModelListener(t);
		propModel.getDataVector().clear();
		propModel.addTableModelListener(t);
		propTable.repaint();
		populate();
	}
	
	public static void changeActor(){
		if(ActorPanel.selectedActor != null){
			TableModelListener t = propModel.getTableModelListeners()[0];
			propModel.removeTableModelListener(t);
			SinkStudio.log("ChangeActor: "+ActorPanel.selectedActor.getName());
			propModel.getDataVector().clear();
			propModel.addRow(new String[]{"X", ""+ActorPanel.selectedActor.getX()});
			propModel.addRow(new String[]{"Y", ""+ActorPanel.selectedActor.getY()});
			propModel.addRow(new String[]{"Width", ""+ActorPanel.selectedActor.getWidth()});
			propModel.addRow(new String[]{"Height", ""+ActorPanel.selectedActor.getHeight()});
			propModel.addRow(new String[]{"Z-Index", ""+ActorPanel.selectedActor.getZIndex()});
			//for(int i =0;i<12-tableModel.getRowCount()/2;i++)	
			//	tableModel.addRow(new String[]{"", ""});
			propModel.addTableModelListener(t);
		}
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		if(ActorPanel.selectedActor != null){
			if(e.getColumn() == 1 && e.getLastRow() < propModel.getRowCount()){
				String key = (String)propModel.getValueAt(e.getLastRow(), e.getColumn()-1);
				String value = (String)propModel.getValueAt(e.getLastRow(), e.getColumn());
				setProperty(key, value);
			}
		}
	}
	
	public void setProperty(String key, String value){
		switch(key){
			case "X": ActorPanel.selectedActor.setX(Float.parseFloat(value));break;
			case "Y": ActorPanel.selectedActor.setY(Float.parseFloat(value));break;
			case "Width": ActorPanel.selectedActor.setWidth(Float.parseFloat(value));break;
			case "Height": ActorPanel.selectedActor.setHeight(Float.parseFloat(value));break;
			case "Z-Index": ActorPanel.selectedActor.setZIndex(Integer.parseInt(value));break;
		}
	}
	
	public static void updateProperty(String key, String value){
		if(propModel.getRowCount() == 5){
			switch(key){
				case "X": propModel.setValueAt(value, 0, 1);break;
				case "Y": propModel.setValueAt(value, 1, 1);break;
			}
		}
	}
}