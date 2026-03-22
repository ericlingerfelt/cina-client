package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.rate.ratelibman.listener.RateLibManEraseLibDirListener;
import org.nucastrodata.rate.ratelibman.worker.RateLibManEraseLibDirWorker;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.event.*;
import java.util.Iterator;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


//This class creates the window for the Erase Library sub feature of th eRate Library Manager feature 
/**
 * The Class RateLibManEraseLibPanel.
 */
public class RateLibManEraseLibDirPanel extends WizardPanel implements ActionListener, RateLibManEraseLibDirListener{
	
	JPanel libDirPanel, mainPanel; 
	JLabel libDirLabel, libDirLabel2, topLabel; 
	JComboBox<String> libDirComboBox;
	JButton eraseLibDirButton, yesButton, noButton;
	JDialog cautionDialog, eraseLibDialog;
	private RateLibManDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new rate lib man erase lib panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManEraseLibDirPanel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		//Set current feature index to 6
		Cina.rateLibManFrame.setCurrentFeatureIndex(9);
		Cina.rateLibManFrame.setCurrentPanelIndex(1);

		addWizardPanel("Rate Library Manager", "Erase Library Directory", "1", "1");
		
		mainPanel = new JPanel();
		
		libDirComboBox = new JComboBox<String>();
		libDirComboBox.setFont(Fonts.textFont);
		
		libDirLabel = new JLabel("Library Directory to erase: ");
		libDirLabel.setFont(Fonts.textFont);
		
		libDirLabel2 = new JLabel("There are no User library directories to erase.");
		libDirLabel2.setFont(Fonts.textFont);
		
		topLabel = new JLabel("<html>With the Erase Library Directory tool, you can delete rate library directories from your User storage.</html>");
		
		eraseLibDirButton = new JButton("Erase Library Directory");
		eraseLibDirButton.addActionListener(this);
		
		double[] column = {20, TableLayoutConstants.FILL, 20};
		double[] row = {100, TableLayoutConstants.PREFERRED
							, 30, TableLayoutConstants.PREFERRED
							, 30, TableLayoutConstants.PREFERRED, 20};
		mainPanel.setLayout(new TableLayout(column, row));
		
		libDirPanel = new JPanel();
		double[] columnLibDir = {TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.PREFERRED};
		double[] rowLibDir = {TableLayoutConstants.PREFERRED};
		libDirPanel.setLayout(new TableLayout(columnLibDir, rowLibDir));
		libDirPanel.add(libDirLabel, "0, 0, r, c");
		libDirPanel.add(libDirComboBox, "2, 0, f, c");
		
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	//Method for Action Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		//If source is the erase lib button
		if(ae.getSource()==eraseLibDirButton){
		
			//Ask user one more time if that is what they wnat to do 
			String string = "You are about to erase the User library directory " + (String)libDirComboBox.getSelectedItem() + ".";
		
			createCautionDialog(string, Cina.rateLibManFrame);
		
		//If source is yes button of caution dialog
		}else if(ae.getSource()==yesButton){
			
			//Close caution dialog
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			
			ds.setLibDir((String)libDirComboBox.getSelectedItem());
			RateLibManEraseLibDirWorker worker  = new RateLibManEraseLibDirWorker(this, ds, Cina.rateLibManFrame);
			worker.execute();
			
	
		//If no button is source
		//ie the user chooses not to erase lib
		}else if(ae.getSource()==noButton){
		
			//Close caution dialog
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}
		
		validate();
		
	} 
	
	//Method to set the current state of this panel
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		mainPanel.removeAll();
		
		//Remove all item from the library combo box
		libDirComboBox.removeAllItems();
		
		//If there are libraries to erase
		if(ds.getLibDirList().size()>0){
		
			Iterator<String> itr = ds.getLibDirList().iterator();
			while(itr.hasNext()){
				libDirComboBox.addItem(itr.next());
			}
			if(!ds.getLibDir().equals("")){
				libDirComboBox.setSelectedItem(ds.getLibDir());
			}else{
				libDirComboBox.setSelectedIndex(0);
			}

			mainPanel.add(topLabel, "1, 1, c, c");
			mainPanel.add(libDirPanel, "1, 3, c, c");
			mainPanel.add(eraseLibDirButton, "1, 5, c, c");
		
		//If there are no more user libraries to erase
		}else{
			
			mainPanel.add(libDirLabel2, "1, 1, c, c");
		
		}
		
		validate();

	}
	
	//Method to get the current state of this panel
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	
	//Create erase lib dialog for erase lib report
	/**
	 * Creates the erase lib dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createEraseLibDirDialog(String string, JFrame frame){
    	
	    	GridBagConstraints gbc1 = new GridBagConstraints();
	    	
	    	eraseLibDialog = new JDialog(frame, "Rate Library Erased", true);
	    	eraseLibDialog.setSize(350, 210);
	    	eraseLibDialog.getContentPane().setLayout(new GridBagLayout());
		eraseLibDialog.setLocationRelativeTo(frame);
		
		JTextArea eraseLibTextArea = new JTextArea("", 5, 30);
		eraseLibTextArea.setLineWrap(true);
		eraseLibTextArea.setWrapStyleWord(true);
		eraseLibTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(eraseLibTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		eraseLibTextArea.setText(string);
		eraseLibTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					eraseLibDialog.setVisible(false);
					eraseLibDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		eraseLibDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		eraseLibDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(eraseLibDialog);
		
		//Show the dialog
		eraseLibDialog.setVisible(true);
	
	}
	
	//Create caution dialog to ask user if they want to erase this library for sure
	/**
	 * Creates the caution dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createCautionDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
	    	cautionDialog = new JDialog(frame, "Caution!", true);
	    	cautionDialog.setSize(320, 215);
	    	cautionDialog.getContentPane().setLayout(new GridBagLayout());
		cautionDialog.setLocationRelativeTo(frame);
		
		JTextArea cautionTextArea = new JTextArea("", 5, 30);
		cautionTextArea.setLineWrap(true);
		cautionTextArea.setWrapStyleWord(true);
		cautionTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(cautionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String cautionString = string;

		cautionTextArea.setText(cautionString);
		
		cautionTextArea.setCaretPosition(0);
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");	
		
		yesButton = new JButton("Yes");
		yesButton.setFont(Fonts.buttonFont);
		yesButton.addActionListener(this);
		
		noButton = new JButton("No");
		noButton.setFont(Fonts.buttonFont);
		noButton.addActionListener(this);
		
		JPanel cautionButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.gridwidth = 2;
		gbc1.insets = new Insets(0, 0, 0, 0);
		cautionButtonPanel.add(cautionLabel, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.gridwidth = 1;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionButtonPanel.add(yesButton, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 1;
		
		cautionButtonPanel.add(noButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		cautionDialog.getContentPane().add(cautionButtonPanel, gbc1);
		
		//Cina.setFrameColors(cautionDialog);
		
		cautionDialog.setVisible(true);

	}


	public void updateAfterEraseLibDir() {
		
		mainPanel.removeAll();
		
		//If there is at least one more library to erase
		if(libDirComboBox.getItemCount()>1){
	
			//Remove the selected library from the menu
			libDirComboBox.removeItem(libDirComboBox.getSelectedItem());
		
			mainPanel.add(topLabel, "1, 1, c, c");
			mainPanel.add(libDirPanel, "1, 3, c, c");
			mainPanel.add(eraseLibDirButton, "1, 5, c, c");
			
		//If there is not at least one more library to erase
		}else{
			
			mainPanel.add(libDirLabel2, "1, 1, c, c");
		
		}
	
		validate();
		repaint();
		
		createEraseLibDirDialog("The Rate Library Directory has been erased.", Cina.rateLibManFrame);
		
	}

}