package org.nucastrodata.dialogs;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.datastructure.util.ErrorResultDataStructure;

/**
 * The Class ErrorResultDialog.
 *
 * @author Eric J. Lingerfelt
 */
public class ErrorResultDialog extends JDialog{
	
	/**
	 * Creates the dialog.
	 *
	 * @param owner the owner
	 * @param result the result
	 */
	public static void createErrorResultDialog(Frame frame, ErrorResultDataStructure result){
		MessageDialog dialog = new MessageDialog(frame, result.getString(), "Error!");
    		dialog.setVisible(true);
	}
	
	public static void createErrorResultDialog(Dialog parentDialog, ErrorResultDataStructure result){
		MessageDialog dialog = new MessageDialog(parentDialog, result.getString(), "Error!");
    		dialog.setVisible(true);
	}
	
	public static void createErrorResultDialog(Window window, ErrorResultDataStructure result){
		MessageDialog dialog = new MessageDialog(window, result.getString(), "Error!");
    		dialog.setVisible(true);
	}
	
}


