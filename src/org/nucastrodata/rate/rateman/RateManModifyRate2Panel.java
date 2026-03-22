package org.nucastrodata.rate.rateman;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateManModifyRate2Panel.
 */
public class RateManModifyRate2Panel extends WizardPanel{

	/** The Q value label. */
	private JLabel reactionStringLabel, QValueLabel;
	
	/** The append notes text area. */
	private JTextArea notesTextArea, appendNotesTextArea;
	
	/** The biblio code field. */
	private JTextField biblioCodeField;
	
	/** The ds. */
	private RateManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate man modify rate2 panel.
	 *
	 * @param ds the ds
	 */
	public RateManModifyRate2Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(3);
		Cina.rateManFrame.setCurrentPanelIndex(2);
		
		addWizardPanel("Rate Manager", "Modify Existing Rate", "2", "3");
		
		reactionStringLabel = new JLabel("Reaction: ");
		reactionStringLabel.setFont(Fonts.textFont);
		
		JLabel biblioCodeLabel = new JLabel("Biblio Code: ");
		biblioCodeLabel.setFont(Fonts.textFont);
		
		JLabel biblioLabel2 = new JLabel("(less than five characters)");
		biblioLabel2.setFont(Fonts.textFont);
		
		JLabel notesLabel = new JLabel("Notes (not editable): ");
		notesLabel.setFont(Fonts.textFont);
		
		JLabel appendNotesLabel = new JLabel("Append new notes below: ");
		appendNotesLabel.setFont(Fonts.textFont);
		
		QValueLabel = new JLabel("Q-value: ");
		QValueLabel.setFont(Fonts.textFont);
		
		JLabel decayTypeLabel = new JLabel("Decay Type: ");
		decayTypeLabel.setFont(Fonts.textFont);
		
		notesTextArea = new JTextArea();
		notesTextArea.setLineWrap(true);
		notesTextArea.setWrapStyleWord(true);
		notesTextArea.setFont(Fonts.textFont);
		notesTextArea.setEditable(false);
		
		JScrollPane sp1 = new JScrollPane(notesTextArea
									, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
									, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		appendNotesTextArea = new JTextArea();
		appendNotesTextArea.setLineWrap(true);
		appendNotesTextArea.setWrapStyleWord(true);
		appendNotesTextArea.setFont(Fonts.textFont);
		
		JScrollPane sp2 = new JScrollPane(appendNotesTextArea
									, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
									, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp2.setPreferredSize(new Dimension(400, 50));
		
		biblioCodeField = new JTextField();
		
		double gap = 10;
		double[] column = {20, TableLayoutConstants.FILL, gap
							, TableLayoutConstants.FILL, gap
							, TableLayoutConstants.FILL, 20};
		double[] row = {20, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL, 20};
		setLayout(new TableLayout(column, row));
		 
		add(reactionStringLabel, "1, 1, 5, 1 ,l, c");
		add(QValueLabel, "1, 3, 5, 3, l, c");
		add(biblioCodeLabel, "1, 5, l, c");
		add(biblioCodeField, "3, 5, f, c");
		add(biblioLabel2, "5, 5, l, c");
		add(notesLabel, "1, 7, 5, 7 ,l, c");
		add(sp1, "1, 9, 5, 9, f, f");
		add(appendNotesLabel, "1, 11, 5, 11, l, c");
		add(sp2, "1, 13, 5, 13, f, f");

		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		if(!ds.getModifyRateDataStructure().getDecay().equals("")){

			reactionStringLabel.setText("Reaction: " 
											+ ds.getModifyRateDataStructure().getReactionString()
											+ " ["
											+ ds.getModifyRateDataStructure().getDecay()
											+ "]"
											+ " ("
											+ ds.getCurrentLibraryStringModify()
											+ ")");
										
		}else{
		
			reactionStringLabel.setText("Reaction: " 
											+ ds.getModifyRateDataStructure().getReactionString()
											+ " ("
											+ ds.getCurrentLibraryStringModify()
											+ ")");
		
		}

		biblioCodeField.setText(ds.getModifyRateDataStructure().getBiblioString());
		
		if(ds.getModifyRateDataStructure().getQValue()!=0.0){
		
			QValueLabel.setText("Q-Value: "+ String.valueOf(ds.getModifyRateDataStructure().getQValue()));

		}else if(ds.getModifyRateDataStructure().getQValue()==0.0
					|| String.valueOf(ds.getModifyRateDataStructure().getQValue()).equals("")){
		
			QValueLabel.setText("Q-Value: Not Entered");
		
		}

		notesTextArea.setText(ds.getModifyRateDataStructure().getReactionNotes());
		appendNotesTextArea.setText(ds.getAppendedNotes());
		ds.getModifyRateDataStructureCompare().setParameters(ds.getModifyRateDataStructure().getParameters());

		validate();
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		ds.getModifyRateDataStructure().setBiblioString(biblioCodeField.getText());
		ds.setAppendedNotes(appendNotesTextArea.getText());
		
	}
	
	/**
	 * Check data fields.
	 *
	 * @return true, if successful
	 */
	protected boolean checkDataFields(){

		if(!biblioCodeField.getText().equals("") && biblioCodeField.getText().length()>4){
			String string = "Biblio Code must be less than 5 characters.";
			Dialogs.createExceptionDialog(string, Cina.rateManFrame);
			return false;
		}else{
			return true;
		}
		
	}
}