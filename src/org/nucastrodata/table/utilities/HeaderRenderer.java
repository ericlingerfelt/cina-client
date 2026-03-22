package org.nucastrodata.table.utilities;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import org.nucastrodata.Cina;
import org.nucastrodata.ColorFormat;
import org.nucastrodata.datastructure.MainDataStructure;


/**
 * The Class HeaderRenderer.
 */
public class HeaderRenderer extends JLabel implements TableCellRenderer{

	/** The table cell renderer. */
	private TableCellRenderer tableCellRenderer;
	
	/** The mds. */
	private MainDataStructure mds;

	/**
	 * Instantiates a new header renderer.
	 *
	 * @param tableCellRenderer the table cell renderer
	 * @param mds the mds
	 */
	public HeaderRenderer(TableCellRenderer tableCellRenderer
								, MainDataStructure mds){
		this.tableCellRenderer = tableCellRenderer;
		this.mds = mds;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
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
			l.setHorizontalAlignment(SwingConstants.CENTER);
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