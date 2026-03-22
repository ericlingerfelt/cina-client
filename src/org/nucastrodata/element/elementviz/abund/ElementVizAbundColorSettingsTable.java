package org.nucastrodata.element.elementviz.abund;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import javax.swing.border.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.element.elementviz.util.AbundRowData;
import org.nucastrodata.element.elementviz.util.BinAbundCellEditor;
import org.nucastrodata.element.elementviz.util.ColorEditor;
import org.nucastrodata.element.elementviz.util.ColorRenderer;
import org.nucastrodata.element.elementviz.util.ElementVizColorSettingsTableModel;
import org.nucastrodata.element.elementviz.util.HeaderRenderer;

import java.text.*;


/**
 * The Class ElementVizAbundColorSettingsTable.
 */
public class ElementVizAbundColorSettingsTable extends JTable{

	/** The table model. */
	public ElementVizColorSettingsTableModel tableModel;

	/** The num bin. */
	int numBin;
	
	/** The col names abund vector. */
	Vector colNamesAbundVector;

	/** The init abund. */
	boolean initAbund = false;

	/** The ds. */
	private ElementVizDataStructure ds;

	/**
	 * Instantiates a new element viz abund color settings table.
	 *
	 * @param ds the ds
	 */
	public ElementVizAbundColorSettingsTable(ElementVizDataStructure ds){
		
		this.ds = ds;
		
		colNamesAbundVector = new Vector();
		colNamesAbundVector.addElement("Interval Min");
		colNamesAbundVector.addElement("Interval Max");
		colNamesAbundVector.addElement("Include bin?");
		colNamesAbundVector.addElement("Color");
	
		tableModel = new ElementVizColorSettingsTableModel();

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
			
		tableModel.setDataVector(ds.getFinalAbundBinData(), colNamesAbundVector);

		int currentRowCount = tableModel.getRowCount();

		if(numBin>currentRowCount){
		
			Integer newMax = (Integer)((Vector)(tableModel.getDataVector().elementAt(currentRowCount-1))).elementAt(0);
			Integer newMin = new Integer(newMax.intValue() - 1);
		
			tableModel.addRow(new AbundRowData(newMin.intValue(), newMax.intValue(), true, new Color(0,0,0)));
			
		}else if(numBin<currentRowCount){
		
			tableModel.removeRow(tableModel.getDataVector().size()-1);
		
		}
		
		ds.setFinalAbundBinData(tableModel.getDataVector());

		TableColumn minAbundColumn = getColumnModel().getColumn(0);
		BinAbundCellEditor minAbundCellEditor = new BinAbundCellEditor();
		minAbundColumn.setCellEditor(minAbundCellEditor);

		TableColumn maxAbundColumn = getColumnModel().getColumn(1);
		BinAbundCellEditor maxAbundCellEditor = new BinAbundCellEditor();
		maxAbundColumn.setCellEditor(maxAbundCellEditor);
		
		
		
		validate();
		
	}

}