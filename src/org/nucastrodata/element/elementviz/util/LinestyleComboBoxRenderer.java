package org.nucastrodata.element.elementviz.util;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.nucastrodata.element.elementviz.util.DashedLine;
import org.nucastrodata.element.elementviz.util.SolidLine;
import org.nucastrodata.element.elementviz.util.SparseDashedLine;
import org.nucastrodata.element.elementviz.util.SparseDashedLine2;


/**
 * The Class LinestyleComboBoxRenderer.
 */
public class LinestyleComboBoxRenderer extends JLabel implements ListCellRenderer{

	/**
	 * Instantiates a new linestyle combo box renderer.
	 */
	public LinestyleComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(
                                       JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus) {

        int selectedIndex = ((Integer)value).intValue();
        
        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		
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
