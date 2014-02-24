package santhosh;

import javax.swing.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public interface MutableListModel<E> extends ListModel<E> {
    public boolean isCellEditable(int index);
    public void setValueAt(Object value, int index);
}
