package org.nucastrodata.element.elementviz.util;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.nucastrodata.Cina;
import org.nucastrodata.ColorFormat;



/**
 * The Class HeaderRenderer.
 */
public class HeaderRenderer extends JLabel implements TableCellRenderer{

	/** The table cell renderer. */
	private TableCellRenderer tableCellRenderer;

	/**
	 * Instantiates a new header renderer.
	 *
	 * @param tableCellRenderer the table cell renderer
	 */
	public HeaderRenderer(TableCellRenderer tableCellRenderer){
		this.tableCellRenderer = tableCellRenderer;
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
