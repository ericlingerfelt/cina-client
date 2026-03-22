package org.nucastrodata.rate.rateparam;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import org.nucastrodata.Fonts;


/**
 * The Class RateParamResultsNotesFrame.
 */
public class RateParamResultsNotesFrame extends JFrame implements ActionListener{

	/** The text area. */
	private JTextArea textArea;
	
	/** The submit button. */
	private JButton submitButton;
	
	/** The ds. */
	private RateParamDataStructure ds;

	/**
	 * Instantiates a new rate param results notes frame.
	 *
	 * @param ds the ds
	 */
	public RateParamResultsNotesFrame(RateParamDataStructure ds){
	
		this.ds = ds;
	
		setSize(450, 300);
		setTitle("Edit Rate Notes");
	
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
	
		textArea = new JTextArea("");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(Fonts.textFont);
		
		JScrollPane sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(400, 100));
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		submitButton = new JButton("Submit Notes");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(submitButton, gbc);
		
		c.add(sp, BorderLayout.CENTER);
		c.add(buttonPanel, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
	
		
		validate();
	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==submitButton){
		
			ds.setNotes(textArea.getText());
		
		}
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		textArea.setText(ds.getNotes());
	
	}

}