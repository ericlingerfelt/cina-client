package org.nucastrodata.element.elementviz.animator.wait;

import javax.swing.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.feature.WaitingDataStructure;
import org.nucastrodata.io.CGICom;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;


/**
 * The Class WaitingParamPanel.
 */
public class WaitingParamPanel extends JPanel{

	/** The ds. */
	private WaitingDataStructure ds;
	
	/** The ratio field. */
	private JTextField abundField, effLifetimeField, ratioField;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The parent. */
	private JFrame parent;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/**
	 * Instantiates a new waiting param panel.
	 *
	 * @param ds the ds
	 * @param mds the mds
	 * @param parent the parent
	 * @param cgiCom the cgi com
	 */
	public WaitingParamPanel(WaitingDataStructure ds, MainDataStructure mds, JFrame parent, CGICom cgiCom){
		
		this.ds = ds;
		this.mds = mds;
		this.parent = parent;
		this.cgiCom = cgiCom;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED, 30};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 50, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("<html>Please enter criteria for determination of waiting points in the fields below.</html>");
		
		abundField = new JTextField(10);
		effLifetimeField = new JTextField(10);
		ratioField = new JTextField(10);
		
		JLabel abundLabel = new JLabel("Min abundance: ");
		abundLabel.setFont(Fonts.textFont);
		
		JLabel effLifetimeLabel = new JLabel("Min effective lifetime (lifetime against destruction): ");
		effLifetimeLabel.setFont(Fonts.textFont);
		
		JLabel ratioLabel = new JLabel("Min ratio of effective lifetime to beta lifetime: ");
		ratioLabel.setFont(Fonts.textFont);
		
		add(topLabel, "0, 1, 2, 1, c, c");
		add(abundLabel, "0, 3, r, c");
		add(abundField, "2, 3, l, c");
		add(effLifetimeLabel, "0, 5, r, c");
		add(effLifetimeField, "2, 5, l, c");
		add(ratioLabel, "0, 7, r, c");
		add(ratioField, "2, 7, l, c");
		
	}
	
	/**
	 * Good data.
	 *
	 * @return true, if successful
	 */
	public boolean goodData(){
		String abund = abundField.getText().trim();
		String eff = effLifetimeField.getText().trim();
		String ratio = ratioField.getText().trim();
		
		if(abund.equals("") || eff.equals("") || ratio.equals("")){
			return false;
		}
		try{
			Double.valueOf(abund);
			Double.valueOf(eff);
			Double.valueOf(ratio);
		}catch(NumberFormatException nfe){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		abundField.setText(new Double(ds.getAbund()).toString());
		effLifetimeField.setText(new Double(ds.getEffLifetime()).toString());
		ratioField.setText(new Double(ds.getRatio()).toString());
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		ds.setAbund(new Double(abundField.getText()));
		ds.setEffLifetime(new Double(effLifetimeField.getText()));
		ds.setRatio(new Double(ratioField.getText()));
	}
	
}
