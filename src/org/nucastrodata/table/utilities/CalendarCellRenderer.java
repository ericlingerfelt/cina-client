package org.nucastrodata.table.utilities;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.table.DefaultTableCellRenderer;


/**
 * The Class CalendarCellRenderer.
 */
public class CalendarCellRenderer extends DefaultTableCellRenderer{
	
	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableCellRenderer#setValue(java.lang.Object)
	 */
	public void setValue(Object value){
		if(value==null){
			setText("0000-00-00 00:00:00");
		}else if(value instanceof java.util.Calendar){
			Calendar c = (Calendar)value;
			StringBuffer sb = new SimpleDateFormat().format(c.getTime(), new StringBuffer(), new FieldPosition(0));
	        setText(sb.toString());
		}
	}
}
