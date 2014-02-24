package sink.studio.panel;

import java.awt.Dimension;
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

import sink.studio.core.Style;
import sink.studio.core.SinkStudio;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.UIUtils;

public class WidgetPanel extends JPanel  implements ListSelectionListener, DragSourceListener, DragGestureListener {
	private static final long serialVersionUID = 1L;
	
	static JList<String> widgetList;
	static DefaultListModel<String> widgetModel = new DefaultListModel<String>();
	
	DragSource dragSource = new DragSource();
	
	public WidgetPanel(){
		super(new VerticalFlowLayout());
		UIUtils.setUndecorated(this, false);
		widgetList = new JList<String>(new String[]{"Sprite", "Button","TextButton", "ScrollPane", "Table"});
		widgetList.addListSelectionListener(this);
		//widgetList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		add(Style.createHeaderLabel("Widgets"));
		JScrollPane scrollPane = new JScrollPane(widgetList);
		scrollPane.setPreferredSize(new Dimension(200, 180));
		UIUtils.setDrawBorder(scrollPane, false);
		add(scrollPane);
		dragSource.addDragSourceListener(this);
	    dragSource.createDefaultDragGestureRecognizer(widgetList, DnDConstants.ACTION_MOVE, this);
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
	    StringSelection text = new StringSelection(widgetList.getSelectedValue()+":");
	    dragSource.startDrag(event, DragSource.DefaultMoveDrop, text, this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			switch(widgetList.getSelectedValue()){
			case "Text": 
				//Display Dialog
				break;
			}
		}
	}
}
