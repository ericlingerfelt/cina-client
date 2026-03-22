package org.nucastrodata.wizard.isotopeselector;

import javax.swing.*; 

import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.RateLibDataStructure;
import org.nucastrodata.wizard.gui.JScrollPaneCorner;

import java.awt.*;
import java.awt.event.*;
import info.clearthought.layout.*;


/**
 * The Class IsotopeSelectorChartDialog.
 */
public class IsotopeSelectorChartDialog extends JDialog implements ActionListener, IsotopeSelectorChartListener{

	/** The chart. */
	private IsotopeSelectorChart chart;
	
	/** The isl. */
	private IsotopeSelectorListener isl;
	
	/** The close button. */
	private JButton submitButton, closeButton;
	
	/** The isotope field. */
	private JTextField isotopeField;
	
	/** The ip. */
	private IsotopePoint ip;
	
	/** The n ruler. */
	private IsotopeRuler zRuler, nRuler;
	
	/** The sp. */
	private JScrollPane sp;
	
	/**
	 * Instantiates a new isotope selector chart dialog.
	 *
	 * @param owner the owner
	 */
	public IsotopeSelectorChartDialog(JFrame owner){
	
		super(owner, true);
		
		Container c = getContentPane();
		
		setSize(750, 500);
		setTitle("Select Isotope with a Nuclide Chart");
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, 20, TableLayoutConstants.PREFERRED, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL, gap};
		c.setLayout(new TableLayout(column, row));
		
		chart = new IsotopeSelectorChart();
		sp = new JScrollPane(chart);
		chart.setScrollPane(sp);
		
		submitButton = new JButton("Submit Isotope");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		submitButton.setEnabled(false);
		
		closeButton = new JButton("Close Window");
		closeButton.setFont(Fonts.buttonFont);
		closeButton.addActionListener(this);
		
		isotopeField = new JTextField();
		isotopeField.setEditable(false);
		
		JLabel isotopeLabel = new JLabel("Selected Isotope"); 
		isotopeLabel.setFont(Fonts.textFont);
		
		c.add(sp, "1, 1, 1, 9, f, f");
		c.add(isotopeLabel, "3, 1, f, f");
		c.add(isotopeField, "3, 3, f, f");
		c.add(submitButton, "3, 5, f, f");
		c.add(closeButton, "3, 7, f, t");
		
	}
	
	/**
	 * Initialize scroll pane.
	 */
	private void initializeScrollPane(){
		sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(chart.getHeight());
    	sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		sp.getVerticalScrollBar().setUnitIncrement(chart.getBoxSize());
		sp.getHorizontalScrollBar().setUnitIncrement(chart.getBoxSize());
		
		zRuler = new IsotopeRuler(IsotopeRuler.HORIZONTAL);
		nRuler = new IsotopeRuler(IsotopeRuler.VERTICAL);
		zRuler.setPreferredWidth((int)chart.getSize().getWidth());
       	nRuler.setPreferredHeight((int)chart.getSize().getHeight());
		zRuler.setCurrentState(chart.getZmax()
								, chart.getNmax()
								, chart.getMouseX()
								, chart.getMouseY()
								, chart.getXOffset()
								, chart.getYOffset()
								, chart.getCrosshairsOn());						
    	nRuler.setCurrentState(chart.getZmax()
								, chart.getNmax()
								, chart.getMouseX()
								, chart.getMouseY()
								, chart.getXOffset()
								, chart.getYOffset()
								, chart.getCrosshairsOn());
    	chart.setZRuler(zRuler);
    	chart.setNRuler(nRuler);
    	sp.setColumnHeaderView(zRuler);
        sp.setRowHeaderView(nRuler);
		sp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new JScrollPaneCorner());
        sp.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new JScrollPaneCorner());
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param ip the ip
	 * @param rlds the rlds
	 * @param isl the isl
	 */
	public void setCurrentState(IsotopePoint ip
									, RateLibDataStructure rlds
									, IsotopeSelectorListener isl){
		chart.initialize(ip, rlds, this);
		initializeScrollPane();
		this.ip = ip;
		this.isl = isl;
		if(!ip.equals(new IsotopePoint(0, 0))){
			isotopeField.setText(ip.toString());
			submitButton.setEnabled(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==closeButton){
			setVisible(false);
			dispose();
		}else if(ae.getSource()==submitButton){
			setVisible(false);
			dispose();
			isl.fireIsotopeSubmitted(ip);
		}
	}

	/* (non-Javadoc)
	 * @see org.nucastrodata.eval.wizard.isotopeselector.IsotopeSelectorChartListener#fireIsotopeSelected(org.nucastrodata.eval.wizard.datastructure.IsotopePoint)
	 */
	public void fireIsotopeSelected(IsotopePoint ip) {
		this.ip = ip;
		isotopeField.setText(ip.toString());
		submitButton.setEnabled(true);
	}
	
}
