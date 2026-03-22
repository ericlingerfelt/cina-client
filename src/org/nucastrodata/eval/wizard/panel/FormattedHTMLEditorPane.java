package org.nucastrodata.eval.wizard.panel;

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
			super.setText(getText().replaceAll("<body>", "<body><font face=\"sans-serif\" size=\"3\">"));
		}
		super.setText(string);
	}
	
}
