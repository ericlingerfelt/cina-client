package org.nucastrodata.format;

import javax.swing.JEditorPane;


/**
 * The Class FormattedHTMLEditorPane.
 */
public class FormattedHTMLEditorPane extends JEditorPane{
	
	/* (non-Javadoc)
	 * @see javax.swing.JEditorPane#setText(java.lang.String)
	 */
	public void setText(String string){
		if(string.indexOf("<body>")!=-1){
			string = string.replaceAll("<body>", "<style type=\"text/css\">body { font-size: 12pt; font-family: sans-serif }</style>");
		}
		super.setText(string);
	}
	
}
