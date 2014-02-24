package santhosh;

import javax.swing.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class DefaultMutableListModel<E> extends DefaultListModel implements MutableListModel {
	private static final long serialVersionUID = 1L;

	public boolean isCellEditable(int index){
        return true;
    }

    public void setValueAt(Object value, int index){
        super.setElementAt(value, index);
    }
}
