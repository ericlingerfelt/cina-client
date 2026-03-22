package org.nucastrodata.table.utilities;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.PrintfFormat;

import java.text.*;


/**
 * The Class DoubleCellRenderer.
 */
public class DoubleCellRenderer extends JLabel implements TableCellRenderer{
	
	/** The model. */
	private DefaultTableModel model;
	
	/** The format string. */
	private String formatString;
	
	/**
	 * Instantiates a new double cell renderer.
	 *
	 * @param model the model
	 * @param formatString the format string
	 */
	public DoubleCellRenderer(DefaultTableModel model
								, String formatString){
		this.model = model;
		this.formatString = formatString;
		setOpaque(true);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table
    												, Object object
    												, boolean isSelected
    												, boolean hasFocus
    												, int row
    												, int column){
		
		String string = "";
		if(object!=null){
			if(formatString.equals("Parameter")){
				string = getFormattedParameter(((Double)object).doubleValue());
			}else{
				string = new PrintfFormat(formatString).sprintf(((Double)object).doubleValue());
			}
				
		}
    	setText(string);
    	setHorizontalAlignment(SwingConstants.RIGHT);															
    	setFont(Fonts.textFont);
    	setBackground(Color.white);
    	setForeground(Color.black);
    	
    	if(isSelected && hasFocus){
    		setBackground(table.getSelectionBackground());
    	}
    	
    	if(!model.isCellEditable(row, column)){
    		setBackground(new Color(204, 204, 204));
    	}
    	
    	return this;												
   		
    }

	/**
	 * Gets the formatted parameter.
	 *
	 * @param number the number
	 * @return the formatted parameter
	 */
	private String getFormattedParameter(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat(".000000E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();

		if((!string.substring(8,9).equals("-") && !string.substring(0,1).equals("-"))
				|| (!string.substring(9,10).equals("-") && string.substring(0,1).equals("-"))){
			String[] tempArray = new String[2];
			tempArray = string.split("E");
			string = tempArray[0] + "E" + "+" + tempArray[1];
		}

		if(string.substring(0,1).equals("-")){
			string = "-0" + string.substring(1);
		}else{
			string = " 0" + string;
		}

		return string;
	}
	
}

