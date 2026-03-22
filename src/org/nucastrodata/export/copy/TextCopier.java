package org.nucastrodata.export.copy;

import java.awt.*;
import javax.swing.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;


/**
 * The Class TextCopier.
 */
public class TextCopier extends JPanel{

	/**
	 * Copy text.
	 *
	 * @param string the string
	 */
	public static void copyText(String string){
        StringSelection text = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
     	clipboard.setContents(text, text);
    }
}