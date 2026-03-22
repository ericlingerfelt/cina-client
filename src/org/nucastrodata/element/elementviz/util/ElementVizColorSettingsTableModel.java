package org.nucastrodata.element.elementviz.util;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;


/**
 * The Class ElementVizColorSettingsTableModel.
 */
public class ElementVizColorSettingsTableModel extends DefaultTableModel{

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#fireTableCellUpdated(int, int)
	 */
	public void fireTableCellUpdated(int row, int column){
	
		if(column==1){
		
			if((row-1)>=0){
		
				setValueAt(getValueAt(row, column), row-1, column-1);
				fireTableDataChanged();
			
			}
		
		}
	
	}

    /* (non-Javadoc)
     * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
     */
    public Object getValueAt(int row, int col){
        return ((Vector)getDataVector().elementAt(row)).elementAt(col);
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    public Class getColumnClass(int c){
        return getValueAt(0, c).getClass();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int col){
		if((col==0 && row<(getRowCount()-1))){
			return false;
		}
		return true;
	}	

}
