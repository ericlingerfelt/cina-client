package org.nucastrodata.rate.rategen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;

import org.nucastrodata.Fonts;


/**
 * The Class RateGenResultsRateInfoFrame.
 */
public class RateGenResultsRateInfoFrame extends JFrame implements WindowListener{

	/** The title label. */
	private JLabel titleLabel = new JLabel("Reaction Rate Information");
	
	/** The label array. */
	private JLabel[] labelArray = new JLabel[4];
	
	/** The string array. */
	private String[] stringArray = {"dR/dT min: "
									, "dR/dT max: "
									, "Number of original S Factor points: "
									, "Technique used for integration: "};
	
	/** The ds. */
	private RateGenDataStructure ds;
	
	/** The c. */
	private Container c;
	
	/** The gbc. */
	private GridBagConstraints gbc;
	
	/**
	 * Instantiates a new rate gen results rate info frame.
	 *
	 * @param ds the ds
	 */
	public RateGenResultsRateInfoFrame(RateGenDataStructure ds){
		
		this.ds = ds;
		
		c = getContentPane();
		c.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
	
		setSize(432, 155);
		setVisible(true);
	
		setLabelLayout();
		
		addWindowListener(this);
		
		
	
	}
	
	/**
	 * Sets the label layout.
	 */
	public void setLabelLayout(){
		
		if(ds.getRateDataStructure().getDecay().equals("")){
		
			setTitle("Additional Information: " + ds.getRateDataStructure().getReactionString());
		
		}else{
		
			setTitle("Additional Information: " 
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "]");
		
		}
		
		for(int i=0; i<4; i++){

			labelArray[i] = new JLabel();
			labelArray[i].setText("");
			
			gbc.anchor = GridBagConstraints.NORTHWEST;
			
			gbc.insets = new Insets(5, 0, 0, 0);
			
			gbc.gridx = 0;
			gbc.gridy = i + 1;
			
			c.add(labelArray[i], gbc);
			
			c.removeAll();
			c.validate();
		
		}

		labelArray[0].setText(stringArray[0] 
								+ ds.getRateDerivmin());
		labelArray[0].setFont(Fonts.textFont);
		
		labelArray[1].setText(stringArray[1] 
								+ ds.getRateDerivmax());
		labelArray[1].setFont(Fonts.textFont);
	
		labelArray[2].setText(stringArray[2] 
								+ ds.getNumberInterpolatedPointsRT());
	
		labelArray[2].setFont(Fonts.textFont);
		
		labelArray[3].setText(stringArray[3] 
								+ ds.getTechniqueRT());
	
		labelArray[3].setFont(Fonts.textFont);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		
		gbc.insets = new Insets(5, 0, 0, 0);
		
		for(int i=0; i<4; i++){
		
			gbc.gridx = 0;
			gbc.gridy = i + 1;
			
			c.add(labelArray[i], gbc);
		}
		
		
		
		c.validate();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent we) {
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