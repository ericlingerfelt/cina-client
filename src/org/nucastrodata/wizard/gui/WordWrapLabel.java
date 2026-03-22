package org.nucastrodata.wizard.gui;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

import org.nucastrodata.ColorFormat;


/**
 * The Class WordWrapLabel.
 */
public class WordWrapLabel extends JEditorPane{

	/** The is bold. */
	private boolean isBold;
	
	/**
	 * Instantiates a new word wrap label.
	 */
	public WordWrapLabel(){
		setEditable(false);
		setBorder(null);
		setEditorKit(new HTMLEditorKit());
		setBackground(ColorFormat.backColor);
	}
	
	/**
	 * Instantiates a new word wrap label.
	 *
	 * @param isBold the is bold
	 */
	public WordWrapLabel(boolean isBold){
		this();
		this.isBold = isBold;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JEditorPane#setText(java.lang.String)
	 */
	public void setText(String text){
		super.setText(text);
		if(isBold){
			super.setText(getText().replaceAll("<body>", "<body><font face=\"sans-serif\" size=\"3\" color=\"#FFFFFF\"><b>"));
		}else{
			super.setText(getText().replaceAll("<body>", "<body><font face=\"sans-serif\" size=\"3\" color=\"#FFFFFF\">"));
		}
	}
	
	/**
	 * Sets the red text.
	 *
	 * @param text the new red text
	 */
	public void setRedText(String text){
		super.setText(text);
		if(isBold){
			super.setText(getText().replaceAll("<body>", "<body><font face=\"sans-serif\" size=\"3\" color=\"#FF0000\"><b>"));
		}else{
			super.setText(getText().replaceAll("<body>", "<body><font face=\"sans-serif\" size=\"3\" color=\"#FF0000\">"));
		}
	}
	
	public void setCyanText(String text){
		super.setText(text);
		if(isBold){
			super.setText(getText().replaceAll("<body>", "<body><font face=\"sans-serif\" size=\"3\" color=\"#00FFFF\"><b>"));
		}else{
			super.setText(getText().replaceAll("<body>", "<body><font face=\"sans-serif\" size=\"3\" color=\"#00FFFF\">"));
		}
	}
}

