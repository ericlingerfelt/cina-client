package org.nucastrodata.eval.wizard.reactionselector;

import javax.swing.*; 

import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.RateLibDataStructure;
import org.nucastrodata.datastructure.util.ReactionDataStructure;
import org.nucastrodata.wizard.gui.JScrollPaneCorner;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import info.clearthought.layout.*;


/**
 * The Class ReactionSelectorChartDialog.
 */
public class ReactionSelectorChartDialog extends JDialog implements ActionListener{

	/** The chart. */
	private IsotopeSelectorChart chart;
	
	/** The rscl. */
	private ReactionSelectorChartListener rscl;
	
	/** The rsl. */
	private ReactionSelectorListener rsl;
	
	/** The close button. */
	private JButton selectButton, submitButton, closeButton;
	
	/** The reaction field. */
	private JTextField reactionField;
	
	/** The rds. */
	private ReactionDataStructure rds;
	
	/** The reaction list. */
	private JList reactionList;
	
	/** The list model. */
	private DefaultListModel listModel; 
	
	/** The n ruler. */
	private IsotopeRuler zRuler, nRuler;
	
	/** The sp. */
	private JScrollPane sp;
	
	/**
	 * Instantiates a new reaction selector chart dialog.
	 *
	 * @param owner the owner
	 */
	public ReactionSelectorChartDialog(JFrame owner){
	
		super(owner, true);
		
		Container c = getContentPane();
		
		setSize(750, 500);
		setTitle("Select Reaction with a Nuclide Chart");
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, 20, TableLayoutConstants.PREFERRED, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED, gap};
		c.setLayout(new TableLayout(column, row));
		
		chart = new IsotopeSelectorChart();
		sp = new JScrollPane(chart);
		chart.setScrollPane(sp);
		
		selectButton = new JButton("Select Reaction");
		selectButton.setFont(Fonts.buttonFont);
		selectButton.addActionListener(this);
		
		submitButton = new JButton("Submit Reaction");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		submitButton.setEnabled(false);
		
		closeButton = new JButton("Close Window");
		closeButton.setFont(Fonts.buttonFont);
		closeButton.addActionListener(this);
		
		reactionField = new JTextField();
		reactionField.setEditable(false);
		
		listModel = new DefaultListModel();
		reactionList = new JList(listModel);
		reactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listPane = new JScrollPane(reactionList);
		listPane.setPreferredSize(new Dimension(200, 200));
		
		JLabel listLabel = new JLabel("List of Reactions");
		listLabel.setFont(Fonts.textFont);
		JLabel reactionLabel = new JLabel("Selected Reaction"); 
		reactionLabel.setFont(Fonts.textFont);
		//JLabel statusLabel = new JLabel("Evaluation Status Legend");
		//statusLabel.setFont(CinaFonts.textFont);
		
		c.add(sp, "1, 1, 1, 13, f, f");
		c.add(listLabel, "3, 1, f, f");
		c.add(listPane, "3, 3, f, f");
		c.add(reactionLabel, "3, 5, f, f");
		c.add(reactionField, "3, 7, f, f");
		c.add(selectButton, "3, 9, f, f");
		c.add(submitButton, "3, 11, f, f");
		c.add(closeButton, "3, 13, f, f");
		
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
	 * Sets the reaction list.
	 *
	 * @param list the new reaction list
	 */
	public void setReactionList(ArrayList<ReactionDataStructure> list){
		listModel.clear();
		for(ReactionDataStructure rds: list){
			listModel.addElement(rds);
		}
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param rds the rds
	 * @param rlds the rlds
	 * @param rscl the rscl
	 * @param rsl the rsl
	 */
	public void setCurrentState(ReactionDataStructure rds
									, RateLibDataStructure rlds
									, ReactionSelectorChartListener rscl
									, ReactionSelectorListener rsl){
		chart.initialize(rds, rlds, rscl);
		initializeScrollPane();
		this.rds = rds;
		this.rscl = rscl;
		this.rsl = rsl;
		if(!rds.getIsotopePoint().equals(new IsotopePoint(0, 0))){
			rscl.fireIsotopeSelected(rds.getIsotopePoint());
			reactionList.setSelectedValue(rds, true);
			reactionField.setText(rds.toString());
			submitButton.setEnabled(true);
		}
	}
	
	/**
	 * Gets the reaction data structure.
	 *
	 * @return the reaction data structure
	 */
	public ReactionDataStructure getReactionDataStructure(){
		return this.rds;
	}
	
	/**
	 * Check data fields.
	 *
	 * @return true, if successful
	 */
	public boolean checkDataFields(){
		return !reactionField.getText().equals("");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==selectButton){
			if(reactionList.getSelectedValue()!=null){
				rds = (ReactionDataStructure)reactionList.getSelectedValue();
				reactionField.setText(rds.toString());
				submitButton.setEnabled(true);
			}
		}else if(ae.getSource()==closeButton){
			setVisible(false);
			dispose();
		}else if(ae.getSource()==submitButton){
			setVisible(false);
			dispose();
			rsl.fireReactionSubmitted(rds);
		}
	}
	
}
