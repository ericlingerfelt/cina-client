package org.nucastrodata.element.elementviz.animator.bottle;

import javax.swing.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.feature.BottleDataStructure;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.wizard.gui.*;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;


/**
 * The Class BottleParamPanel.
 */
public class BottleParamPanel extends JPanel{

	/** The ds. */
	private BottleDataStructure ds;
	
	/** The sep field. */
	private JTextField lowField, highField, sepField;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The parent. */
	private JFrame parent;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/**
	 * Instantiates a new bottle param panel.
	 *
	 * @param ds the ds
	 * @param mds the mds
	 * @param parent the parent
	 * @param cgiCom the cgi com
	 */
	public BottleParamPanel(BottleDataStructure ds, MainDataStructure mds, JFrame parent, CGICom cgiCom){
		
		this.ds = ds;
		this.mds = mds;
		this.parent = parent;
		this.cgiCom = cgiCom;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 50, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Please enter the parameters for determining the bottleneck reactions in the fields below. "
							+ "Flux Separation Factor: the maximum flux ratio between the reaction with the second largest "
							+ "flux to that of the largest flux (the potential bottleneck reaction) [default is 1.0E-04] in this mass region. " 
							+ "High Flux Threshold: the minimum flux ratio of a Major Bottleneck Reaction to the maximum flux in "
							+ "the simulation [default is 1.0E-05]. "
							+ "Low Flux Threshold: the minimum flux ratio of a Minor Bottleneck Reaction to the maximum flux in "
							+ "the simulation [default is 1.0E-12].</html>");
		
		lowField = new JTextField(10);
		highField = new JTextField(10);
		sepField = new JTextField(10);
		
		JLabel lowLabel = new JLabel("Low Flux Threshold: ");
		lowLabel.setFont(Fonts.textFont);
		
		JLabel highLabel = new JLabel("High Flux Threshold: ");
		highLabel.setFont(Fonts.textFont);
		
		JLabel sepLabel = new JLabel("Flux Separation Factor (between 0 and 1): ");
		sepLabel.setFont(Fonts.textFont);
		
		add(topLabel, "1, 1, 3, 1, f, c");
		add(sepLabel, "1, 3, r, c");
		add(sepField, "3, 3, l, c");
		add(lowLabel, "1, 5, r, c");
		add(lowField, "3, 5, l, c");
		add(highLabel, "1, 7, r, c");
		add(highField, "3, 7, l, c");
		
		
	}
	
	/**
	 * Good data.
	 *
	 * @return true, if successful
	 */
	public boolean goodData(){
		String low = lowField.getText().trim();
		String high = highField.getText().trim();
		String sep = sepField.getText().trim();
		
		if(low.equals("") || high.equals("") || sep.equals("")){
			return false;
		}
		try{
			Double.valueOf(low);
			Double.valueOf(high);
			double value = Double.valueOf(sep);
			
			if(value>1 || value<0){
				return false;
			}
			
		}catch(NumberFormatException nfe){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		lowField.setText(new Double(ds.getLowThreshold()).toString());
		highField.setText(new Double(ds.getHighThreshold()).toString());
		sepField.setText(new Double(ds.getSepFactor()).toString());
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		ds.setLowThreshold(new Double(lowField.getText()));
		ds.setHighThreshold(new Double(highField.getText()));
		ds.setSepFactor(new Double(sepField.getText()));
	}
	
}

