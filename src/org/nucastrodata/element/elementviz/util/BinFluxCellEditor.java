package org.nucastrodata.element.elementviz.util;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;


/**
 * The Class BinFluxCellEditor.
 */
public class BinFluxCellEditor extends AbstractCellEditor implements TableCellEditor{

	/** The spinner. */
	JSpinner spinner;
	
	/** The model. */
	SpinnerNumberModel model;
	
	/**
	 * Instantiates a new bin flux cell editor.
	 */
	public BinFluxCellEditor(){
		
		model = new SpinnerNumberModel(-35, -35, 0, 1);
		spinner = new JSpinner(model);
		((JSpinner.DefaultEditor)(spinner.getEditor())).getTextField().setEditable(false);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	public Object getCellEditorValue(){return model.getValue();}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table,
				                                 Object value,
				                                 boolean isSelected,
				                                 int row,
				                                 int column){

		model.setValue(value);          	
		return spinner;
                                 	
	}

}
