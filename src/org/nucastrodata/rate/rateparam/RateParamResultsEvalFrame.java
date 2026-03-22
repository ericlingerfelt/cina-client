package org.nucastrodata.rate.rateparam;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


//This class creates a window which allows a user to enter a temp in T9 within athe valid temp range
//And get the corresponding rate value 
/**
 * The Class RateParamResultsEvalFrame.
 */
public class RateParamResultsEvalFrame extends JFrame implements WindowListener, ActionListener{

    /** The calc button. */
    private JButton calcButton;
    
    /** The rate field. */
    private JTextField tempField, rateField;
    
    /** The rate label. */
    private JLabel rateLabel;
    
    /** The ds. */
    private RateParamDataStructure ds;

    //Constructor
	/**
     * Instantiates a new rate param results eval frame.
     *
     * @param ds the ds
     */
    public RateParamResultsEvalFrame(RateParamDataStructure ds){
		
		this.ds = ds;
		
		//Fill frame's container
		Container c = getContentPane();
		
		//Set container layout
		c.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
	
		//Set frame properties
		setSize(412, 130);
		setVisible(true);
		
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			//Set new frame title
			setTitle("Calculate R(Temp Value): " + ds.getRateDataStructure().getReactionString());
	
		}else{
			
			setTitle("Calculate R(Temp Value): " 
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "]");
		
		}

		//Create buttons
		calcButton = new JButton("Calculate Rate");
		calcButton.setFont(Fonts.buttonFont);
		calcButton.addActionListener(this);
		
		//Create fields
		tempField = new JTextField(15);
		tempField.setFont(Fonts.textFont);
		
		rateField = new JTextField(15);
		rateField.setFont(Fonts.textFont);
		
		//Create labels
		JLabel tempLabel = new JLabel("Enter temperature value (T9): ");
		tempLabel.setFont(Fonts.textFont);
		
		rateLabel = new JLabel("Reaction rate (" + ds.getRateUnits() + "): ");
		rateLabel.setFont(Fonts.textFont);
		
		//Set gbc
		//And put it all together
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		c.add(tempLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		c.add(tempField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		c.add(rateLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		c.add(rateField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		c.add(calcButton, gbc);
		
		//Add window listener to this frame
		addWindowListener(this);
		
		//Set color formatting
		
		
		//Validate interface
		c.validate();
	
	}
	
	//Method to refresh data in frame
	/**
	 * Refresh data.
	 */
	public void refreshData(){
	
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			//Set new frame title
			setTitle("Evaluate Rate: " + ds.getRateDataStructure().getReactionString());
	
		}else{
			
			setTitle("Evaluate Rate: " 
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "]");
		
		}
		
		//Set new rate info
		rateLabel.setText("Reaction rate (" + ds.getRateUnits() + "): ");
		
		//Set color formatting
		
	
	}
	
	//Method for Action Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		//If source is calculate rate button
		if(ae.getSource()==calcButton){
			
			try{
				
				//Set text of rate field to calculated rate
				rateField.setText(String.valueOf(Cina.cinaMainDataStructure.calcRate(Double.valueOf(tempField.getText()).doubleValue()
									, ds.getRateDataStructure().getParameters()
									, ds.getRateDataStructure().getNumberParameters())));
	
			}catch(NumberFormatException nfe){
		
				String string = "Temperature entry must be a numeric value.";
			
				Dialogs.createExceptionDialog(string, this);
		
			}
		
		}
	
	}
	
	//Methods for Window Listener
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent we) {
		
		//Close this window
        setVisible(false);
        dispose();
    } 
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(WindowEvent we){}
   
}