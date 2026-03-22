package org.nucastrodata.rate.rateparam;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.awt.event.*;
import java.util.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


/**
 * The Class RateParamPointFrame.
 */
public class RateParamPointFrame extends JFrame implements ActionListener{
	
	/** The submit button. */
	private JButton submitButton;
	
	/** The text area. */
	private JTextArea textArea;
	
	/** The continue dialog. */
	private JDialog continueDialog;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/**
	 * Instantiates a new rate param point frame.
	 *
	 * @param ds the ds
	 */
	public RateParamPointFrame(RateParamDataStructure ds){
	
		this.ds = ds;
	
		setTitle("Modify Rate Points");
		setSize(327, 501);
		
		Container c = getContentPane();
		c.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		submitButton = new JButton("Submit New Points");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
	
		JLabel topLabel = new JLabel("<html>With this tool, you can add, modify, or delete<p>rate points by "
											+ "typing into the field below.<p>" 
											+ "Enter new rate points in order of increasing<p>temperature.<p>"
											+ "Click <i>Submit New Points</i> when finished.</html>");
	
		textArea = new JTextArea();

		JScrollPane sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(250, 300));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		c.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		c.add(sp, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		c.add(submitButton, gbc);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				dispose();
				setVisible(true);
			}
		});
		
		
		validate();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==submitButton){
		
			if(checkPoints()){
		
				ds.setFile(textArea.getText());
				ds.setInputType("R(T)");
				ds.setXUnits("T9");
				
				if(Cina.cinaCGIComm.doCGICall("READ INPUT", this)){
					
					checkExtraPoints();
					
					if(isSameAsOrig()){
						ds.setRateModified(false);
					}else{
						ds.setRateModified(true);
					}
					
					Cina.rateParamFrame.rateParamOptionsModifyPanel.setCurrentState();
					
					setVisible(false);
					dispose();
					
					if(ds.getRateModified()){
					
						String string = "The values of chi-squared and maximum percent difference will be calculated using the original set of reaction rate points. These modified rate points will not be saved.";
						Dialogs.createExceptionDialog(string, Cina.rateParamFrame);
						
					}else{
					
						String string = "No rate points were modified.";
						Dialogs.createExceptionDialog(string, Cina.rateParamFrame);
					
					}
	
				}
			
			}
		
		}
	
	}
	
	/**
	 * Checks if is same as orig.
	 *
	 * @return true, if is same as orig
	 */
	public boolean isSameAsOrig(){
	
		boolean isSameAsOrig = true;
		
		if(ds.getTempDataArray().length==ds.getTempDataArrayOrig().length){
			
			notTheSame:
			for(int i=0; i<ds.getTempDataArray().length; i++){
			
				if(ds.getTempDataArray()[i]!=ds.getTempDataArrayOrig()[i]
					|| ds.getRateDataArray()[i]!=ds.getRateDataArrayOrig()[i]){
					
					isSameAsOrig = false;
					break notTheSame;
					
				}
			
			}
		
		}else{
			
			isSameAsOrig = false;
			
		}
		
		return isSameAsOrig;
	
	}
	
	/**
	 * Check points.
	 *
	 * @return true, if successful
	 */
	public boolean checkPoints(){
		
		boolean goodPoints = true;
		
		try{
		
			String valueString = textArea.getText();
			StringTokenizer st = new StringTokenizer(valueString);
		
			int numberTokens = (int)(st.countTokens()/2);
			
			badValue:
			for(int i=0; i<numberTokens; i++){
			
				double tempValue = Double.valueOf(st.nextToken()).doubleValue();
				double rateValue = Double.valueOf(st.nextToken()).doubleValue();
				
				if(tempValue<0.0001 || tempValue>100){
				
					String string = "Temperature values must be greater than or equal to 0.0001 T9 and less than or equal to 100 T9.";
					Dialogs.createExceptionDialog(string, this);
					
					goodPoints = false;
					
					break badValue;
				}
				
				if(rateValue>=1E150 || rateValue<=1E-150){
				
					String string = "Rate values must be greater than 1E-150 and less than 1E150.";
					Dialogs.createExceptionDialog(string, this);
					
					goodPoints = false;
					
					break badValue;
				}
				
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "Rate points must be numeric values.";
			Dialogs.createExceptionDialog(string, this);
		
		}
		
		return goodPoints;
	
	}
	
	/**
	 * Check extra points.
	 */
	public void checkExtraPoints(){
	
		double[] tempArray = ds.getTempDataArray();
		double[] rateArray = ds.getRateDataArray();
	
		double[] tempArrayOrig = ds.getTempDataArrayOrig();
		double[] rateArrayOrig = ds.getRateDataArrayOrig();
		
		Vector indexVector = new Vector();
		
		if(tempArray.length>tempArrayOrig.length){
			
			for(int i=0; i<tempArray.length; i++){
				
				foundTempPoint:
				for(int j=0; j<tempArrayOrig.length; j++){
			
					if(tempArray[i]==tempArrayOrig[j]){
						
						indexVector.addElement(new Integer(i));
						break foundTempPoint;
					
					}
				
				}
			
			}
		
			indexVector.trimToSize();
			int numberExtraPoints = tempArray.length-indexVector.size();
		
			double[] rateArrayExtra = new double[numberExtraPoints];
			double[] tempArrayExtra = new double[numberExtraPoints];
		
			int counter = 0;
		
			for(int i=0; i<tempArray.length; i++){
			
				if(!indexVector.contains(new Integer(i))){
				
					rateArrayExtra[counter] = rateArray[i];
					tempArrayExtra[counter] = tempArray[i];
					
					counter++;

				}
			
			}
			
			ds.setExtraPoints(true);
			ds.setRateDataArrayExtra(rateArrayExtra);
			ds.setTempDataArrayExtra(tempArrayExtra);
		
		}else{
		
			ds.setRateDataArrayExtra(null);
			ds.setTempDataArrayExtra(null);
			ds.setExtraPoints(false);
		
		}
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		textArea.setText(getRateTempString());
		textArea.setCaretPosition(0);
	
	}

	/**
	 * Gets the rate temp string.
	 *
	 * @return the rate temp string
	 */
	public String getRateTempString(){
		
		String string = "";
		
		double[] tempArray = ds.getTempDataArray();
		double[] rateArray = ds.getRateDataArray();
		
		for(int i=0; i<tempArray.length; i++){
		
			string += tempArray[i] + "   " + rateArray[i] + "\n";
		
		}
		
		return string;
	
	}

}