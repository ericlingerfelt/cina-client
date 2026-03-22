package org.nucastrodata.table.utilities;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import org.nucastrodata.Fonts;


/**
 * The Class BooleanCellRenderer.
 */
public class BooleanCellRenderer extends JCheckBox implements TableCellRenderer{
	
	/** The model. */
	private DefaultTableModel model;
	
	/**
	 * Instantiates a new boolean cell renderer.
	 *
	 * @param model the model
	 */
	public BooleanCellRenderer(DefaultTableModel model){
		this.model = model;
		setOpaque(true);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table
    												, Object object
    												, boolean isSelected
    												, boolean hasFocus
    												, int row
    												, int column){
    													
    	boolean flag = ((Boolean)object).booleanValue();
    	setSelected(flag);		
    	setHorizontalAlignment(SwingConstants.CENTER);													
    	setFont(Fonts.textFont);
    	setBackground(Color.white);
    	setForeground(Color.black);
    	
    	if(isSelected && hasFocus){
    		setBackground(table.getSelectionBackground());
    	}
    	
    	if(!model.isCellEditable(row, column)){
    		setBackground(new Color(204, 204, 204));
    	}
    	
    	return this;												
   		
    }

}