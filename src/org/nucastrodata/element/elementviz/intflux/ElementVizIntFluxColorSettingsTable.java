package org.nucastrodata.element.elementviz.intflux;

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
import org.nucastrodata.element.elementviz.util.BinFluxCellEditor;
import org.nucastrodata.element.elementviz.util.ColorEditor;
import org.nucastrodata.element.elementviz.util.ColorRenderer;
import org.nucastrodata.element.elementviz.util.ElementVizColorSettingsTableModel;
import org.nucastrodata.element.elementviz.util.FluxRowData;
import org.nucastrodata.element.elementviz.util.HeaderRenderer;
import org.nucastrodata.element.elementviz.util.LinestyleCellRenderer;
import org.nucastrodata.element.elementviz.util.LinestyleComboBoxRenderer;
import org.nucastrodata.element.elementviz.util.LinewidthCellEditor;

import java.text.*;


/**
 * The Class ElementVizIntFluxColorSettingsTable.
 */
public class ElementVizIntFluxColorSettingsTable extends JTable{

	/** The table model. */
	public ElementVizColorSettingsTableModel tableModel;

	/** The num bin. */
	int numBin;
	
	/** The col names der vector. */
	Vector colNamesAbundVector, colNamesFluxVector, colNamesDerVector;

	/** The init abund. */
	boolean initAbund = false;
	
	/** The init der. */
	boolean initDer = false;
	
	/** The init flux. */
	boolean initFlux = false;

	/** The ds. */
	private ElementVizDataStructure ds;

	/**
	 * Instantiates a new element viz int flux color settings table.
	 *
	 * @param ds the ds
	 */
	public ElementVizIntFluxColorSettingsTable(ElementVizDataStructure ds){
		
		this.ds = ds;
		
		colNamesAbundVector = new Vector();
		colNamesAbundVector.addElement("Interval Min");
		colNamesAbundVector.addElement("Interval Max");
		colNamesAbundVector.addElement("Include bin?");
		colNamesAbundVector.addElement("Color");
		
		colNamesDerVector = new Vector();
		colNamesDerVector.addElement("Interval Min");
		colNamesDerVector.addElement("Interval Max");
		colNamesDerVector.addElement("Include bin?");
		colNamesDerVector.addElement("Positive Color");
		colNamesDerVector.addElement("Negative Color");
		
		colNamesFluxVector = new Vector();
		colNamesFluxVector.addElement("Interval Min");
		colNamesFluxVector.addElement("Interval Max");
		colNamesFluxVector.addElement("Include bin?");
		colNamesFluxVector.addElement("Color");
		colNamesFluxVector.addElement("Flux Arrow Linestyle");
		colNamesFluxVector.addElement("Flux Arrow Linewidth");
	
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
		
		tableModel.setDataVector(ds.getFluxBinData(), colNamesFluxVector);

		int currentRowCount = tableModel.getRowCount();

		if(numBin>currentRowCount){
		
			Integer newMax = (Integer)((Vector)(tableModel.getDataVector().elementAt(currentRowCount-1))).elementAt(0);
			Integer newMin = new Integer(newMax.intValue() - 1);
		
			tableModel.addRow(new FluxRowData(newMin.intValue(), newMax.intValue(), true, new Color(0,0,0), 0, 1));
			
		}else if(numBin<currentRowCount){
		
			tableModel.removeRow(tableModel.getDataVector().size()-1);
		
		}
		
		ds.setFluxBinData(tableModel.getDataVector());
	
		TableColumn minFluxColumn = getColumnModel().getColumn(0);
		BinFluxCellEditor minFluxCellEditor = new BinFluxCellEditor();
		minFluxColumn.setCellEditor(minFluxCellEditor);
		
		TableColumn maxFluxColumn = getColumnModel().getColumn(1);
		BinFluxCellEditor maxFluxCellEditor = new BinFluxCellEditor();
		maxFluxColumn.setCellEditor(maxFluxCellEditor);
					
		TableColumn linestyleColumn = getColumnModel().getColumn(4);
		JComboBox linestyleComboBox = new JComboBox();
		LinestyleCellRenderer linestyleCellRenderer = new LinestyleCellRenderer();
		linestyleCellRenderer.setPreferredSize(new Dimension(70, 15));
		
		linestyleComboBox.addItem(new Integer(0));
		linestyleComboBox.addItem(new Integer(1));
		linestyleComboBox.addItem(new Integer(2));
		linestyleComboBox.addItem(new Integer(3));
		
		LinestyleComboBoxRenderer comboBoxRenderer = new LinestyleComboBoxRenderer();
		comboBoxRenderer.setPreferredSize(new Dimension(70, 15));
		linestyleComboBox.setRenderer(comboBoxRenderer);
		linestyleComboBox.setMaximumRowCount(4);

		linestyleColumn.setCellEditor(new DefaultCellEditor(linestyleComboBox));
		linestyleColumn.setCellRenderer(linestyleCellRenderer);
		
		TableColumn linewidthColumn = getColumnModel().getColumn(5);
		LinewidthCellEditor linewidthCellEditor = new LinewidthCellEditor();
		linewidthColumn.setCellEditor(linewidthCellEditor);

		
		
		validate();
		
	}

}