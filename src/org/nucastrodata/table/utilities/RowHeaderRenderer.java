package org.nucastrodata.table.utilities;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import org.nucastrodata.Cina;
import org.nucastrodata.ColorFormat;
import org.nucastrodata.datastructure.MainDataStructure;


/**
 * The Class RowHeaderRenderer.
 */
public class RowHeaderRenderer extends JLabel implements ListCellRenderer{     
	
	/** The tool tip text array. */
	private String[] toolTipTextArray;

	/**
	 * Instantiates a new row header renderer.
	 *
	 * @param table the table
	 * @param mds the mds
	 * @param toolTipTextArray the tool tip text array
	 */
	public RowHeaderRenderer(JTable table
								, MainDataStructure mds
								, String[] toolTipTextArray){   
	 
	 	this.toolTipTextArray = toolTipTextArray;
	 	
		JTableHeader header = table.getTableHeader();    
		setOpaque(true);    
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));    
		setHorizontalAlignment(CENTER);    
		setForeground(ColorFormat.frontColor);    
		setBackground(ColorFormat.backColor);    
		if(Cina.system.equals("MAC")){
			setBackground(ColorFormat.frontColor);
			setForeground(ColorFormat.backColor);
		}
		setFont(header.getFont());  
	}    
		
	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(JList list          
														, Object value
														, int index
														, boolean isSelected
														, boolean cellHasFocus){
		if(toolTipTextArray!=null){
			if(toolTipTextArray[index]!=null){
				setToolTipText(toolTipTextArray[index]);
			}
		}
		setText((value == null) ? "": value.toString());    
		return this;  
	}
	
}