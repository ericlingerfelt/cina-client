package org.nucastrodata.eval.wizard.reactionselector;

import javax.swing.*; 

import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.util.RateLibDataStructure;
import org.nucastrodata.datastructure.util.ReactionDataStructure;

import java.awt.*;
import java.awt.event.*;
import info.clearthought.layout.*;


/**
 * The Class ReactionSelectorRecentDialog.
 */
public class ReactionSelectorRecentDialog extends JDialog implements ActionListener{

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
	
	/**
	 * Instantiates a new reaction selector recent dialog.
	 *
	 * @param owner the owner
	 */
	public ReactionSelectorRecentDialog(JFrame owner){
		
		super(owner, true);
		
		Container c = getContentPane();
		
		setSize(610, 350);
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
		
		selectButton = new JButton("Select Reaction");
		selectButton.setFont(Fonts.buttonFont);
		selectButton.addActionListener(this);
		
		submitButton = new JButton("Submit Reaction");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
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
		
		c.add(listLabel, "3, 1, f, f");
		c.add(listPane, "3, 3, f, f");
		c.add(reactionLabel, "3, 5, f, f");
		c.add(reactionField, "3, 7, f, f");
		c.add(selectButton, "3, 9, f, f");
		c.add(submitButton, "3, 11, f, f");
		c.add(closeButton, "3, 13, f, f");
		
		
		
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param rds the rds
	 * @param rlds the rlds
	 * @param rsl the rsl
	 */
	public void setCurrentState(ReactionDataStructure rds, RateLibDataStructure rlds, ReactionSelectorListener rsl){
		this.rds = rds;
		this.rsl = rsl;
		reactionField.setText(rds.toString());
	}
	
	/**
	 * Gets the reaction data structure.
	 *
	 * @return the reaction data structure
	 */
	public ReactionDataStructure getReactionDataStructure(){
		return this.rds;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==selectButton){
			if(reactionList.getSelectedValue()!=null){
				rds = (ReactionDataStructure)reactionList.getSelectedValue();
				reactionField.setText(rds.toString());
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
