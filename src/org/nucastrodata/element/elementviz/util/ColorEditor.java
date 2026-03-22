package org.nucastrodata.element.elementviz.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


/**
 * The Class ColorEditor.
 */
public class ColorEditor extends AbstractCellEditor implements TableCellEditor, ActionListener{
	
    /** The current color. */
    Color currentColor;
    
    /** The button. */
    JButton button;
    
    /** The color chooser. */
    JColorChooser colorChooser;
    
    /** The dialog. */
    JDialog dialog;
    
    /** The Constant EDIT. */
    protected static final String EDIT = "edit";

    /**
     * Instantiates a new color editor.
     */
    public ColorEditor() {

        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);

        colorChooser = new JColorChooser();
        
        dialog = JColorChooser.createDialog(button,
                                        "Pick a Color",
                                        true,
                                        colorChooser,
                                        this,
                                        null);
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	
        if(EDIT.equals(ae.getActionCommand())){

            button.setBackground(currentColor);
            colorChooser.setColor(currentColor);
            dialog.setVisible(true);
            fireEditingStopped();

        }else{
        	
            currentColor = colorChooser.getColor();
            
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue(){return currentColor;}

    /* (non-Javadoc)
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column){
                                                 	
        currentColor = (Color)value;
        return button;
        
    }
    
}
