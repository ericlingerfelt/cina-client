package org.nucastrodata.element.elementviz.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;


/**
 * The Class ColorRenderer.
 */
public class ColorRenderer extends JLabel implements TableCellRenderer{
	
    /** The unselected border. */
    Border unselectedBorder = null;
    
    /** The selected border. */
    Border selectedBorder = null;
    
    /** The is bordered. */
    boolean isBordered = true;

    /**
     * Instantiates a new color renderer.
     *
     * @param isBordered the is bordered
     */
    public ColorRenderer(boolean isBordered){
        this.isBordered = isBordered;
        setOpaque(true);
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table
    												, Object color
    												, boolean isSelected
    												, boolean hasFocus
    												, int row
    												, int column){
    													
        Color newColor = (Color)color;
        
        setBackground(newColor);
        
        if(isBordered){
        	
            if(isSelected){
            	
                if(selectedBorder == null){
                	
                    selectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                              table.getSelectionBackground());
                                              
                }
                
                setBorder(selectedBorder);
                
            }else{
            	
                if(unselectedBorder == null){
                	
                    unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                              table.getBackground());
                                              
                }
                
                setBorder(unselectedBorder);
                
            }
            
        }
       
        return this;
    }
}
