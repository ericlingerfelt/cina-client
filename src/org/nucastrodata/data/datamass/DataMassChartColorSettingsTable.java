package org.nucastrodata.data.datamass;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import javax.swing.border.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.ColorFormat;
import org.nucastrodata.data.datamass.ColorEditor;
import org.nucastrodata.data.datamass.ColorRenderer;
import org.nucastrodata.data.datamass.DataMassChartColorSettingsTableModel;
import org.nucastrodata.data.datamass.HeaderRenderer;
import org.nucastrodata.data.datamass.RowData;


/**
 * The Class DataMassChartColorSettingsTable.
 */
public class DataMassChartColorSettingsTable extends JTable{

	/** The table model. */
	public DataMassChartColorSettingsTableModel tableModel;

	/** The num bin. */
	int numBin;
	
	/** The col names vector. */
	Vector colNamesVector;

	/** The init abund. */
	boolean initAbund = false;

	/** The ds. */
	private DataMassDataStructure ds;

	/**
	 * Instantiates a new data mass chart color settings table.
	 *
	 * @param ds the ds
	 */
	public DataMassChartColorSettingsTable(DataMassDataStructure ds){
		
		this.ds = ds;
		
		colNamesVector = new Vector();
		colNamesVector.addElement("Interval Min");
		colNamesVector.addElement("Interval Max");
		colNamesVector.addElement("Include bin?");
		colNamesVector.addElement("Color");
	
		tableModel = new DataMassChartColorSettingsTableModel();

		setDefaultRenderer(Color.class, new ColorRenderer(true));                      
        setDefaultEditor(Color.class, new ColorEditor());

		setModel(tableModel);
		setRowHeight(20);
		
		getTableHeader().setDefaultRenderer(new HeaderRenderer(getTableHeader().getDefaultRenderer()));

		
		
		validate();

	}
	
	/**
	 * Sets the current state.
	 *
	 * @param numBin the new current state
	 */
	public void setCurrentState(int numBin){
	
		this.numBin = numBin;
			
		tableModel.setDataVector(ds.getBinData(), colNamesVector);

		int currentRowCount = tableModel.getRowCount();

		if(numBin>currentRowCount){
		
			Double newMax = (Double)((Vector)(tableModel.getDataVector().elementAt(currentRowCount-1))).elementAt(0);
			Double newMin = new Double(newMax.doubleValue());
		
			tableModel.addRow(new RowData(newMin.doubleValue()-1, newMax.doubleValue(), true, new Color(0,0,0)));
			
		}else if(numBin<currentRowCount){
		
			tableModel.removeRow(tableModel.getDataVector().size()-1);
		
		}
		
		ds.setBinData(tableModel.getDataVector());
		
		
		
		validate();
		
	}

}

class RowData extends Vector{

	public RowData(double min, double max, boolean show, Color color){
	
		addElement(new Double(min));
		addElement(new Double(max));
		addElement(new Boolean(show));
		addElement(color);
	
	}

}

class DataMassChartColorSettingsTableModel extends DefaultTableModel{

	public void fireTableCellUpdated(int row, int column){
	
		if(column==1){
		
			if((row-1)>=0){
		
				setValueAt(getValueAt(row, column), row-1, column-1);
				fireTableDataChanged();
			
			}
		
		}
	
	}

    public Object getValueAt(int row, int col){
        return ((Vector)getDataVector().elementAt(row)).elementAt(col);
    }

    public Class getColumnClass(int c){
        return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col){
		if((col==0 && row<(getRowCount()-1))){
			return false;
		}else{
			return true;
		}
	}	

}

class ColorRenderer extends JLabel implements TableCellRenderer{
	
    Border unselectedBorder = null;
    Border selectedBorder = null;
    boolean isBordered = true;

    public ColorRenderer(boolean isBordered){
        this.isBordered = isBordered;
        setOpaque(true);
    }

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

class ColorEditor extends AbstractCellEditor implements TableCellEditor, ActionListener{
	
    Color currentColor;
    JButton button;
    JColorChooser colorChooser;
    JDialog dialog;
    protected static final String EDIT = "edit";

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

    public Object getCellEditorValue(){return currentColor;}

    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column){
                                                 	
        currentColor = (Color)value;
        return button;
        
    }
    
}

class HeaderRenderer extends JLabel implements TableCellRenderer{

	private TableCellRenderer tableCellRenderer;

	public HeaderRenderer(TableCellRenderer tableCellRenderer){
		this.tableCellRenderer = tableCellRenderer;
	}

	public Component getTableCellRendererComponent(JTable table, 
													Object value,
													boolean isSelected, 
													boolean hasFocus,
													int row, 
													int column){

		Component c = tableCellRenderer.getTableCellRendererComponent(table, 
										value, isSelected, hasFocus, row, column);

		if(c instanceof JLabel){

			JLabel l = (JLabel)c;
			l.setHorizontalAlignment(JLabel.CENTER);
			l.setForeground(ColorFormat.frontColor);
			l.setBackground(ColorFormat.backColor);

			if(Cina.system.equals("MAC")){
				l.setBackground(ColorFormat.frontColor);
				l.setForeground(ColorFormat.backColor);

			}

		}
		
		return c;
		
	}
	
}