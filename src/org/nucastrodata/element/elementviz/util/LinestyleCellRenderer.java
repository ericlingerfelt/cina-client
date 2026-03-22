package org.nucastrodata.element.elementviz.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.nucastrodata.element.elementviz.util.DashedLine;
import org.nucastrodata.element.elementviz.util.SolidLine;
import org.nucastrodata.element.elementviz.util.SparseDashedLine;
import org.nucastrodata.element.elementviz.util.SparseDashedLine2;


/**
 * The Class LinestyleCellRenderer.
 */
public class LinestyleCellRenderer extends JLabel implements TableCellRenderer{

	/**
	 * Instantiates a new linestyle cell renderer.
	 */
	public LinestyleCellRenderer(){
		setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        setOpaque(true);
    }
    
    /* (non-Javadoc)
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table
    												, Object value
    												, boolean isSelected
    												, boolean hasFocus
    												, int row
    												, int column){
    													
    	int selectedIndex = ((Integer)value).intValue();
    	
        setBackground(Color.white);
    	
    	switch(selectedIndex){
		
			case 0: 
				setIcon(new SolidLine());	
				break;
			
			case 1: 
				setIcon(new DashedLine());	
				break;
				
			case 2: 
				setIcon(new SparseDashedLine());	
				break;
				
			case 3: 
				setIcon(new SparseDashedLine2());	
				break;
		
		}
		
		return this;
    
    }

}
