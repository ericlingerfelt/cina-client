//This was written by Eric Lingerfelt
//09/19/2003
package org.nucastrodata.rate.rategen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;
import org.nucastrodata.datastructure.util.NucDataSetDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rategen.RateGenChartFrame;


//Class RateGenIDPanel allows the user to enter the Z and A of each
//particle of the reaction. Dynamic fields representing each possible 
//type of reaction in REACLIB are setSelectedIndexed using the reaction type 
//drop down menu
/**
 * The Class RateGenIDPanel.
 */
public class RateGenIDPanel extends WizardPanel implements ItemListener, ActionListener, TreeExpansionListener{
	
	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The combo box panel. */
	JPanel mainPanel, reactionUIPanel, notesPanel, reactionTypeComboBoxPanel, comboBoxPanel; 
	
	//Declare text area for the "Notes:" section
	/** The notes text area. */
	JTextArea notesTextArea;
	
	/** The box2. */
	JRadioButton box1, box2;
	
	/** The button group. */
	ButtonGroup buttonGroup;
	
	/** The top label2. */
	JLabel topLabel, topLabel2;
    
    //Declare reaction ytpe drop down menu
    /** The decay type combo box. */
    JComboBox reactionTypeComboBox, reactionNameComboBox, decayTypeComboBox;
    
    //Declare reactionString text fields
    /** The reaction string in z field array. */
    JTextField[] reactionStringInZFieldArray = new JTextField[3];
    
    /** The reaction string in a field array. */
    JTextField[] reactionStringInAFieldArray = new JTextField[3];
    
    /** The reaction string out z field array. */
    JTextField[] reactionStringOutZFieldArray = new JTextField[4];
    
    /** The reaction string out a field array. */
    JTextField[] reactionStringOutAFieldArray = new JTextField[4];
    
    //Declare reactionString labels
    /** The Z label array. */
    JLabel[] ZLabelArray = new JLabel[6];
    
    /** The A label array. */
    JLabel[] ALabelArray = new JLabel[6];
    
    /** The plus label array. */
    JLabel[] plusLabelArray = new JLabel[4];
    
    /** The iso in1. */
    Point[] isoIn1 = new Point[3];
	
	/** The iso out1. */
	Point[] isoOut1 = new Point[4];
	
	/** The iso in2. */
	Point[] isoIn2 = new Point[3];
	
	/** The iso out2. */
	Point[] isoOut2 = new Point[4];
	
	/** The iso in3. */
	Point[] isoIn3 = new Point[3];
	
	/** The iso out3. */
	Point[] isoOut3 = new Point[4];
	
	/** The iso in4. */
	Point[] isoIn4 = new Point[3];
	
	/** The iso out4. */
	Point[] isoOut4 = new Point[4];
	
	/** The iso in5. */
	Point[] isoIn5 = new Point[3];
	
	/** The iso out5. */
	Point[] isoOut5 = new Point[4];
	
	/** The iso in6. */
	Point[] isoIn6 = new Point[3];
	
	/** The iso out6. */
	Point[] isoOut6 = new Point[4];
	
	/** The iso in7. */
	Point[] isoIn7 = new Point[3];
	
	/** The iso out7. */
	Point[] isoOut7 = new Point[4];
	
	/** The iso in8. */
	Point[] isoIn8 = new Point[3];
	
	/** The iso out8. */
	Point[] isoOut8 = new Point[4];
	
	/** The iso in9. */
	Point[] isoIn9 = new Point[3];
	
	/** The iso out9. */
	Point[] isoOut9 = new Point[4];
	
	/** The iso in10. */
	Point[] isoIn10 = new Point[3];
	
	/** The iso out10. */
	Point[] isoOut10 = new Point[4];
	
	/** The iso in11. */
	Point[] isoIn11 = new Point[3];
	
	/** The iso out11. */
	Point[] isoOut11 = new Point[4];
	
	/** The iso in12. */
	Point[] isoIn12 = new Point[3];
	
	/** The iso out12. */
	Point[] isoOut12 = new Point[4];
	
    //Declare other reactionString labels
    /** The decay type label. */
    JLabel arrowLabel, plusParenLabel, parenLabel, reactionTypeLabel, notesLabel, reactionNameLabel, decayTypeLabel;
    
    //Reaction rate units
	/** The units. */
    String[] units = {"", "reactions/sec", "reactions/sec", "reactions/sec", "cm^3/(mole*s)"
                                , "cm^3/(mole*s)", "cm^3/(mole*s)", "cm^3/(mole*s)"
                                , "cm^6/(mole^2*s)"};
    
    /** The reaction index array. */
    int[] reactionIndexArray = {3, 4, 4, 3, 3, 5, 4, 4, 4, 3, 3, 4};
    
    /** The reaction name array. */
    String[] reactionNameArray = {"d + d --> 4He"
    								, "d + d --> p + t"
    								, "d + d --> n + 3He"
    								, "p + d --> 3He"
    								, "t + 4He --> 7Li"
    								, "3He + 3He --> p + p + 4He"
    								, "p + 6Li --> 3He + 4He"
    								, "p + 7Li --> 4He + 4He"
    								, "p + 9Be --> 4He + 6Li"
    								, "p + 9Be --> 10B"
    								, "p + 10B --> 11C"
    								, "p + 11B --> 4He + 8Be"};
    
    /** The iso in vector. */
    Vector isoInVector = new Vector(); 
    
    /** The iso out vector. */
    Vector isoOutVector = new Vector();
	
	//Array holding total number of particles vs. reaction type
	/** The number particles array. */
	int[] numberParticlesArray = {2, 3, 4, 3, 4, 5, 6, 5};
	
	//Array holding total number of reactants (input particles) vs. reaction type
	/** The number reactants. */
	int[] numberReactants = {1, 1, 1, 2, 2, 2, 2, 3};
	
	//Array holding total number of products (output particles) vs. reaction type
	/** The number products. */
	int[] numberProducts = {1, 2, 3, 1, 2, 3, 4, 2};
	
	/** The sp. */
	JScrollPane sp;
		
	//STUFF FOR NUC DATA TREE///////////////////////////////////////////////////////////////////////////////
	/** The user set group node. */
	DefaultMutableTreeNode setNode, publicSetGroupNode, sharedSetGroupNode, userSetGroupNode;
	
	/** The user set group node array. */
	DefaultMutableTreeNode[] publicSetGroupNodeArray, sharedSetGroupNodeArray, userSetGroupNodeArray;
	
	/** The set tree. */
	JTree setTree;
	
	/** The delay dialog. */
	JDialog delayDialog;
	
	/** The tree title label. */
	JLabel treeTitleLabel;
	
	/** The button panel. */
	JPanel treePanel, buttonPanel;
	
	/** The tree model. */
	DefaultTreeModel treeModel;

	/** The tree selection model. */
	TreeSelectionModel treeSelectionModel;
	
	/** The chart button. */
	JButton removeNucDataButton, addNucDataButton, chartButton;
	
	/** The appndsds temp. */
	NucDataSetDataStructure appndsdsTemp;

	/** The used tree paths. */
	Vector usedTreePaths;

	/** The nuc data set field. */
	JTextField nucDataField, rateField, nucDataSetField;

	/** The nuc data set label. */
	JLabel nucDataLabel, rateLabel, nucDataSetLabel;
	
	/** The ds. */
	private RateGenDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate gen id panel.
	 *
	 * @param ds the ds
	 */
	public RateGenIDPanel(RateGenDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateGenFrame.setCurrentPanelIndex(1);
		
		if(!Cina.cinaMainDataStructure.getUser().equals("guest")
				&& ds.getUploadData()){
		
			//Instantiate and set up the reaction type choice box//////////////CHOICE//BOX//////////////////
			reactionTypeComboBox = new JComboBox();
	    	reactionTypeComboBox.addItem("a-->b");
	    	reactionTypeComboBox.addItem("a-->b+c");
	    	reactionTypeComboBox.addItem("a-->b+c+d");
	    	reactionTypeComboBox.addItem("a+b-->c");
	    	reactionTypeComboBox.addItem("a+b-->c+d");
	    	reactionTypeComboBox.addItem("a+b-->c+d+e");
	    	reactionTypeComboBox.addItem("a+b-->c+d+e+f");
	    	reactionTypeComboBox.addItem("a+b+c-->d(+e)");
				
			reactionTypeComboBox.setEnabled(true);
			reactionTypeComboBox.addItemListener(this);
			reactionTypeComboBox.setFont(Fonts.textFont);
			
			reactionNameComboBox = new JComboBox();
			
	    	for(int i=0; i<reactionNameArray.length; i++){
	    	
	    		reactionNameComboBox.addItem(reactionNameArray[i]);
	    	
	    	}
	    	
	    	reactionNameComboBox.setSelectedIndex(0);
			reactionNameComboBox.addItemListener(this);

			decayTypeComboBox = new JComboBox();
			decayTypeComboBox.addItem("none");
			decayTypeComboBox.addItem("electron capture");
			decayTypeComboBox.addItem("beta+");
			decayTypeComboBox.addItem("beta-");
			decayTypeComboBox.setFont(Fonts.textFont);
			decayTypeComboBox.addItemListener(this);

			for(int i=0; i<3; i++){
	    		reactionStringInZFieldArray[i] = new JTextField(3);
	    		reactionStringInZFieldArray[i].setFont(Fonts.textFont);
	    		reactionStringInZFieldArray[i].setEditable(true);
	    		
	    		reactionStringInAFieldArray[i] = new JTextField(3);
	    		reactionStringInAFieldArray[i].setFont(Fonts.textFont);
	    		reactionStringInAFieldArray[i].setEditable(true);
	    	}
	    	
	    	for(int i=0; i<4; i++){
	    		reactionStringOutZFieldArray[i] = new JTextField(3);
	    		reactionStringOutZFieldArray[i].setFont(Fonts.textFont);
	    		reactionStringOutZFieldArray[i].setEditable(true);
	    		
	    		reactionStringOutAFieldArray[i] = new JTextField(3);
	    		reactionStringOutAFieldArray[i].setFont(Fonts.textFont);
	    		reactionStringOutAFieldArray[i].setEditable(true);
	    	}

			//Instantiate labels//////////////////////////////////////////LABELS////////////////////////////
			for(int i=0; i<6; i++){
	    		ZLabelArray[i] = new JLabel("Z");
	    		ZLabelArray[i].setFont(Fonts.textFont);
	    		
	    		ALabelArray[i] = new JLabel("A");
	    		ALabelArray[i].setFont(Fonts.textFont);
	    	}
	    	
	    	for(int i=0; i<4; i++){
	    		plusLabelArray[i] = new JLabel("+");
	    		plusLabelArray[i].setFont(Fonts.textFont);
	    	
	    	}
	    	
	    	topLabel2 = new JLabel("<html>Choose a reaction type from the dropdown menu, "
								    	+ "then enter the element <p>and mass number for each reactant and product in the fields below. "
								    	+ "Choose<p>a decay type from the dropdown menu. If the reaction is not a decay, choose<p>\"none\" "
								    	+ "from the decay type menu. Add notes for this reaction in the <i>Notes</i> field."
								    	+ "<br><br><font color=\"#FF0000\">NOTE:</font> This tool can not currently generate rates for neutron induced reactions.</html>");
								    	
	    	plusParenLabel = new JLabel("(+");
	    	plusParenLabel.setFont(Fonts.textFont);
	    	
	    	parenLabel = new JLabel(")");
	    	parenLabel.setFont(Fonts.textFont);
	    	
	    	arrowLabel = new JLabel("-->");
	    	arrowLabel.setFont(Fonts.textFont);
			
			reactionTypeLabel = new JLabel("Reaction type: ");
			reactionTypeLabel.setFont(Fonts.textFont);
			
			reactionNameLabel = new JLabel("Please choose a reaction: ");
			reactionNameLabel.setFont(Fonts.textFont);
			
			notesLabel = new JLabel("Notes: ");
			notesLabel.setFont(Fonts.textFont);
			
			decayTypeLabel = new JLabel("Decay type: ");
			decayTypeLabel.setFont(Fonts.textFont);
			
			//Instantiate text area//////////////////////////////////////////TEXT//AREA////////////////////
			notesTextArea = new JTextArea("NACRE Charged Particle-Induced Reaction Rate Library 1999 - Angulo et al., NACRE Collab. [ULB] ", 4, 60);
			notesTextArea.setLineWrap(true);
			notesTextArea.setWrapStyleWord(true);
			notesTextArea.setFont(Fonts.textFont);
			
			sp = new JScrollPane(notesTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			sp.setPreferredSize(new Dimension(400, 100));
			
			chartButton = new JButton("Select Rate from Nuclide Chart");
			chartButton.setFont(Fonts.buttonFont);
			chartButton.addActionListener(this);
			
			mainPanel = new JPanel(new GridBagLayout());
			gbc = new GridBagConstraints();
			
			reactionUIPanel = new JPanel(new GridBagLayout());
			
			notesPanel = new JPanel(new GridBagLayout());
			
			//Layout Components
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			
			notesPanel.add(notesLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.insets = new Insets(0, 0, 0, 0);
			notesPanel.add(sp, gbc);
			
			gbc.insets = new Insets(0, 0, 0, 0);
			
			addWizardPanel("Rate Generator", "Rate Identification", "1", "7");
			
			comboBoxPanel = new JPanel(new GridBagLayout());
			
			gbc.gridx = 0; 
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.anchor = GridBagConstraints.EAST;
			comboBoxPanel.add(reactionTypeLabel, gbc);
			
			gbc.gridx = 1; 
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			comboBoxPanel.add(reactionTypeComboBox, gbc);
			
			gbc.gridx = 2; 
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			comboBoxPanel.add(decayTypeLabel, gbc);
			
			gbc.gridx = 3; 
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			comboBoxPanel.add(decayTypeComboBox, gbc);
			
			gbc.gridx = 0; 
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(topLabel2, gbc);
			
			gbc.gridx = 0; 
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(comboBoxPanel, gbc);
			
			gbc.gridx = 0; 
			gbc.gridy = 2;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(reactionUIPanel, gbc);
			
			gbc.gridx = 0; 
			gbc.gridy = 3;
			gbc.gridwidth = 1;
			mainPanel.add(chartButton, gbc);
			
			gbc.gridx = 0; 
			gbc.gridy = 4;
			gbc.gridwidth = 1;
			mainPanel.add(notesPanel, gbc);
			
			gbc.gridwidth = 1;
			
			add(mainPanel, BorderLayout.CENTER);
		
		}else if(Cina.cinaMainDataStructure.getUser().equals("guest")){
		
			//Instantiate and set up the reaction type choice box//////////////CHOICE//BOX//////////////////
			reactionTypeComboBox = new JComboBox();
	    	reactionTypeComboBox.addItem("a-->b");
	    	reactionTypeComboBox.addItem("a-->b+c");
	    	reactionTypeComboBox.addItem("a-->b+c+d");
	    	reactionTypeComboBox.addItem("a+b-->c");
	    	reactionTypeComboBox.addItem("a+b-->c+d");
	    	reactionTypeComboBox.addItem("a+b-->c+d+e");
	    	reactionTypeComboBox.addItem("a+b-->c+d+e+f");
	    	reactionTypeComboBox.addItem("a+b+c-->d(+e)");
	    	
			reactionTypeComboBox.setEnabled(false);
				
			reactionNameComboBox = new JComboBox();
			
	    	for(int i=0; i<reactionNameArray.length; i++){
	    	
	    		reactionNameComboBox.addItem(reactionNameArray[i]);
	    	
	    	}
	    	
	    	reactionNameComboBox.setSelectedIndex(0);
			reactionNameComboBox.addItemListener(this);
			reactionNameComboBox.setFont(Fonts.textFont);
		
			//Instantiate Fields///////////////////////////////////////////FIELDS/////////////////////////////
			for(int i=0; i<3; i++){
	    		reactionStringInZFieldArray[i] = new JTextField(3);
	    		reactionStringInZFieldArray[i].setFont(Fonts.textFont);
	    		reactionStringInZFieldArray[i].setEditable(false);
	    		
	    		reactionStringInAFieldArray[i] = new JTextField(3);
	    		reactionStringInAFieldArray[i].setFont(Fonts.textFont);
	    		reactionStringInAFieldArray[i].setEditable(false);
	    	}
	    	
	    	for(int i=0; i<4; i++){
	    		reactionStringOutZFieldArray[i] = new JTextField(3);
	    		reactionStringOutZFieldArray[i].setFont(Fonts.textFont);
	    		reactionStringOutZFieldArray[i].setEditable(false);
	    		
	    		reactionStringOutAFieldArray[i] = new JTextField(3);
	    		reactionStringOutAFieldArray[i].setFont(Fonts.textFont);
	    		reactionStringOutAFieldArray[i].setEditable(false);
	    	}
			
			//Instantiate labels//////////////////////////////////////////LABELS////////////////////////////
			for(int i=0; i<6; i++){
	    		ZLabelArray[i] = new JLabel("Z");
	    		ZLabelArray[i].setFont(Fonts.textFont);
	    		
	    		ALabelArray[i] = new JLabel("A");
	    		ALabelArray[i].setFont(Fonts.textFont);
	    	}
	    	
	    	for(int i=0; i<4; i++){
	    		plusLabelArray[i] = new JLabel("+");
	    		plusLabelArray[i].setFont(Fonts.textFont);
	    	
	    	}
	    	
	    	plusParenLabel = new JLabel("(+");
	    	plusParenLabel.setFont(Fonts.textFont);
	    	
	    	parenLabel = new JLabel(")");
	    	parenLabel.setFont(Fonts.textFont);
	    	
	    	arrowLabel = new JLabel("-->");
	    	arrowLabel.setFont(Fonts.textFont);
			
			reactionTypeLabel = new JLabel("Reaction type: ");
			reactionTypeLabel.setFont(Fonts.textFont);
			
			reactionNameLabel = new JLabel("Please choose a reaction: ");
			reactionNameLabel.setFont(Fonts.textFont);
			
			notesLabel = new JLabel("Notes: ");
			
			//Instantiate text area//////////////////////////////////////////TEXT//AREA////////////////////
			notesTextArea = new JTextArea("", 4, 60);
			notesTextArea.setLineWrap(true);
			notesTextArea.setWrapStyleWord(true);
			notesTextArea.setFont(Fonts.textFont);
			
			sp = new JScrollPane(notesTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			sp.setPreferredSize(new Dimension(400, 100));
			
			mainPanel = new JPanel(new GridBagLayout());
			gbc = new GridBagConstraints();
			
			reactionUIPanel = new JPanel(new GridBagLayout());
			
			notesPanel = new JPanel(new GridBagLayout());
			
			//Layout Components
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			
			notesPanel.add(notesLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.insets = new Insets(0, 0, 0, 0);
			notesPanel.add(sp, gbc);
			
			gbc.insets = new Insets(0, 0, 0, 0);
			
			addWizardPanel("Rate Generator", "Rate Identification", "1", "7");
			
			box1 = new JRadioButton("Cross Section vs. Energy Data --> Rate vs. Temperature", true);
			box1.addItemListener(this);
			box1.setFont(Fonts.textFont);
			
			box2 = new JRadioButton("S-factor vs. Energy Data --> Rate vs. Temperature", false);
			box2.addItemListener(this);
			box2.setFont(Fonts.textFont);
			
			buttonGroup = new ButtonGroup();
			
			buttonGroup.add(box1);
			buttonGroup.add(box2);
			
			topLabel = new JLabel("<html>Guest users are restricted to a selected subset of files located on our server. <p>Registered users upload files to our server and process them with <p>the Rate Generator codes. <p><br>Choose a path below and select a reaction from the dropdown menu.</html>");
			
			reactionTypeComboBoxPanel = new JPanel(new GridBagLayout());
			
			createPointArrays();
			
			gbc.gridx = 0; 
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 10, 0);
			gbc.anchor = GridBagConstraints.EAST;
			reactionTypeComboBoxPanel.add(reactionNameLabel, gbc);
			
			gbc.gridx = 1; 
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			reactionTypeComboBoxPanel.add(reactionNameComboBox, gbc);
			
			gbc.gridx = 0; 
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.CENTER;
			
			mainPanel.add(topLabel, gbc);
			
			gbc.gridy = 1;
			gbc.anchor = GridBagConstraints.WEST;			
			mainPanel.add(box1, gbc);
			
			gbc.gridy = 2;
			
			mainPanel.add(box2, gbc);
			
			gbc.gridy = 3;
			
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(reactionTypeComboBoxPanel, gbc);
			
			add(mainPanel, BorderLayout.CENTER);

		}else if(!Cina.cinaMainDataStructure.getUser().equals("guest")
				&& !ds.getUploadData()){
		
			gbc = new GridBagConstraints();
		
			addWizardPanel("Rate Generator", "Choose Nuclear Data", "1", "7");
		
			mainPanel = new JPanel(new BorderLayout());
		
			usedTreePaths = new Vector();
			
			treePanel = new JPanel(new BorderLayout());
		
			treeTitleLabel = new JLabel("Select nuclear data from the tree below.");
											
			
			setNode = new DefaultMutableTreeNode("Nuclear Data Sets");
			
			treeModel = new DefaultTreeModel(setNode);
			
			treeSelectionModel = new DefaultTreeSelectionModel();
			treeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			
			setTree = new JTree(treeModel);
			setTree.setEditable(false);
			setTree.putClientProperty("JTree.linestyle", "Angled");
			setTree.setSelectionModel(treeSelectionModel);
			setTree.addTreeExpansionListener(this);
			setTree.setShowsRootHandles(true);
	
			sp = new JScrollPane(setTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sp.setPreferredSize(new Dimension(300, 300));
			
			treePanel.add(treeTitleLabel, BorderLayout.NORTH);
			
			treePanel.add(sp, BorderLayout.CENTER);
			
			topLabel = new JLabel("<html>Please select a nuclear data file from the<p>tree on the left."
										+ "<br><br><font color=\"#FF0000\">NOTE:</font> This tool can not currently generate<p>rates for neutron induced reactions.</html>");
	    	
			
			nucDataField = new JTextField(20);
			nucDataField.setEditable(false);
			
			rateField = new JTextField(20);
			rateField.setEditable(false);
			
			nucDataSetField = new JTextField(20);
			nucDataSetField.setEditable(false);
			
			nucDataLabel = new JLabel("Nuclear Data: ");
			nucDataLabel.setFont(Fonts.textFont);
			
			rateLabel = new JLabel("Reaction Rate: ");
			rateLabel.setFont(Fonts.textFont);
			
			nucDataSetLabel = new JLabel("Nuclear Data Set: ");
			nucDataSetLabel.setFont(Fonts.textFont);
			
			removeNucDataButton = new JButton("Clear Fields");
			removeNucDataButton.setFont(Fonts.buttonFont);
			removeNucDataButton.addActionListener(this);
			
			addNucDataButton = new JButton("Add Selected Nuclear Data");
			addNucDataButton.setFont(Fonts.buttonFont);
			addNucDataButton.addActionListener(this);
			
			buttonPanel = new JPanel(new GridBagLayout());
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(3, 3, 3, 3);
			
			buttonPanel.add(topLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			
			buttonPanel.add(nucDataLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			
			buttonPanel.add(nucDataField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			
			buttonPanel.add(rateLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			buttonPanel.add(rateField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 5;
			buttonPanel.add(nucDataSetLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			buttonPanel.add(nucDataSetField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 7;
			buttonPanel.add(addNucDataButton, gbc);
	
			gbc.gridx = 0;
			gbc.gridy = 8;
			buttonPanel.add(removeNucDataButton, gbc);
	
			mainPanel.add(sp, BorderLayout.CENTER);
			
			mainPanel.add(buttonPanel, BorderLayout.EAST);
			
			add(mainPanel, BorderLayout.CENTER);
		
		}
		
		validate();
	
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeExpansionListener#treeExpanded(javax.swing.event.TreeExpansionEvent)
	 */
	public void treeExpanded(TreeExpansionEvent tee){

		if(tee.getPath().getPathCount()==5 
				&& !usedTreePaths.contains(tee.getPath())){
			
			for(int i=0; i<ds.getNumberPublicNucDataSetDataStructures(); i++){
			
				if(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getParent().toString().equals(ds.getPublicNucDataSetDataStructureArray()[i].getNucDataSetName())){
				
					appndsdsTemp = ds.getPublicNucDataSetDataStructureArray()[i];
				
				}

			}
			
			for(int i=0; i<ds.getNumberSharedNucDataSetDataStructures(); i++){
			
				if(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getParent().toString().equals(ds.getSharedNucDataSetDataStructureArray()[i].getNucDataSetName())){
				
					appndsdsTemp = ds.getSharedNucDataSetDataStructureArray()[i];
				
				}

			}
			
			for(int i=0; i<ds.getNumberUserNucDataSetDataStructures(); i++){
			
				if(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getParent().toString().equals(ds.getUserNucDataSetDataStructureArray()[i].getNucDataSetName())){
				
					appndsdsTemp = ds.getUserNucDataSetDataStructureArray()[i];
				
				}

			}
			
			int zIndex = ((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getParent().getIndex(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent());
			
			int aIndex = ((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getIndex((DefaultMutableTreeNode)tee.getPath().getLastPathComponent());
	
			if(appndsdsTemp.getIsotopeList()[zIndex][aIndex]!=-1){
				
				createReactionNodes(appndsdsTemp
										, (DefaultMutableTreeNode)tee.getPath().getLastPathComponent()
										, appndsdsTemp.getZList()[zIndex]
										, appndsdsTemp.getIsotopeList()[zIndex][aIndex]);
										
			}
			
			usedTreePaths.addElement(tee.getPath());
										
		}
	
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeExpansionListener#treeCollapsed(javax.swing.event.TreeExpansionEvent)
	 */
	public void treeCollapsed(TreeExpansionEvent tee){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==removeNucDataButton){
		
			nucDataField.setText("");
			
			rateField.setText("");
			
			nucDataSetField.setText("");
		
			ds.getNucDataDataStructure().setNucDataID("");
		
		}else if(ae.getSource()==addNucDataButton){
			
			if(((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).isLeaf()){
			
				String newNucData = ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getUserObject().toString();
					
				String newReaction = ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().toString();
	
				String newNucDataSet = ((DefaultMutableTreeNode)((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent().getParent()).getUserObject().toString();
											
				String tempString = new String(getNucDataIDString(((DefaultMutableTreeNode)((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent().getParent().getParent()).getUserObject().toString()
													, ((DefaultMutableTreeNode)((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent().getParent()).getUserObject().toString()
													, ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().toString()
													, ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getUserObject().toString()));
	
				if(newNucData.indexOf("Reaction does not")==-1){
	
					ds.getNucDataDataStructure().setNucDataID(tempString);
		
					nucDataField.setText(newNucData);
		
					rateField.setText(newReaction);
					
					nucDataSetField.setText(newNucDataSet);
				
				}
			
			}
			
		}else if(ae.getSource()==chartButton){
		
			if(Cina.rateGenFrame.rateGenChartFrame==null){
	
				Cina.rateGenFrame.rateGenChartFrame = new RateGenChartFrame(ds);
				Cina.rateGenFrame.rateGenChartFrame.setCurrentState();
				Cina.rateGenFrame.rateGenChartFrame.setVisible(true);
				
			}else{
				
				Cina.rateGenFrame.rateGenChartFrame.setCurrentState();
				Cina.rateGenFrame.rateGenChartFrame.setVisible(true);
			
			}
		
		}
	
	}
	
	/**
	 * Creates the set group nodes.
	 */
	public void createSetGroupNodes(){
	
		publicSetGroupNode = new DefaultMutableTreeNode("Public");
	
		sharedSetGroupNode = new DefaultMutableTreeNode("Shared");
	
		userSetGroupNode = new DefaultMutableTreeNode("User");

		if(ds.getNumberPublicNucDataSetDataStructures()>0){
				
			treeModel.insertNodeInto(publicSetGroupNode, setNode, 0);
			
			createPublicNucDataSetDataStructureNodes();
			
		}
		
		if(ds.getNumberSharedNucDataSetDataStructures()>0){
				
			treeModel.insertNodeInto(sharedSetGroupNode, setNode, 0);
			
			createSharedNucDataSetDataStructureNodes();
			
		}
		
		if(ds.getNumberUserNucDataSetDataStructures()>0){

			treeModel.insertNodeInto(userSetGroupNode, setNode, 0);
			
			createUserNucDataSetDataStructureNodes();
		
		}
	
	}
	
	/**
	 * Creates the public nuc data set data structure nodes.
	 */
	public void createPublicNucDataSetDataStructureNodes(){

		publicSetGroupNodeArray = new DefaultMutableTreeNode[ds.getNumberPublicNucDataSetDataStructures()];
		
		int nodeCounter = 0;

		for(int i=0; i<ds.getNumberPublicNucDataSetDataStructures(); i++){
		
			publicSetGroupNodeArray[i] = new DefaultMutableTreeNode(ds.getPublicNucDataSetDataStructureArray()[i].getNucDataSetName());
	
			treeModel.insertNodeInto(publicSetGroupNodeArray[i], publicSetGroupNode, nodeCounter);
			
			nodeCounter++;
			
			createZNodes(ds.getPublicNucDataSetDataStructureArray()[i], publicSetGroupNodeArray[i]);
					
		}

	}
	
	/**
	 * Creates the shared nuc data set data structure nodes.
	 */
	public void createSharedNucDataSetDataStructureNodes(){

		sharedSetGroupNodeArray = new DefaultMutableTreeNode[ds.getNumberSharedNucDataSetDataStructures()];
		
		int nodeCounter = 0;

		for(int i=0; i<ds.getNumberSharedNucDataSetDataStructures(); i++){
		
			sharedSetGroupNodeArray[i] = new DefaultMutableTreeNode(ds.getSharedNucDataSetDataStructureArray()[i].getNucDataSetName());
	
			treeModel.insertNodeInto(sharedSetGroupNodeArray[i], sharedSetGroupNode, nodeCounter);
			
			nodeCounter++;
			
			createZNodes(ds.getSharedNucDataSetDataStructureArray()[i], sharedSetGroupNodeArray[i]);
					
		}

	}
	
	/**
	 * Creates the user nuc data set data structure nodes.
	 */
	public void createUserNucDataSetDataStructureNodes(){
	
		userSetGroupNodeArray = new DefaultMutableTreeNode[ds.getNumberUserNucDataSetDataStructures()];
		
		int nodeCounter = 0;
		
		for(int i=0; i<ds.getNumberUserNucDataSetDataStructures(); i++){
		
			userSetGroupNodeArray[i] = new DefaultMutableTreeNode(ds.getUserNucDataSetDataStructureArray()[i].getNucDataSetName());
		
			treeModel.insertNodeInto(userSetGroupNodeArray[i], userSetGroupNode, nodeCounter);
			
			nodeCounter++;
			
			createZNodes(ds.getUserNucDataSetDataStructureArray()[i], userSetGroupNodeArray[i]);
			
		}
		
	}
	
	/**
	 * Creates the z nodes.
	 *
	 * @param appndsds the appndsds
	 * @param parentNode the parent node
	 */
	public void createZNodes(NucDataSetDataStructure appndsds, DefaultMutableTreeNode parentNode){
	
		ds.setNucDataSetName(appndsds.getNucDataSetName());
	
		ds.setCurrentNucDataSetDataStructure(appndsds);
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET NUC DATA SET ISOTOPES", Cina.rateGenFrame);
		
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
		
					for(int i=0; i<appndsds.getZList().length; i++){
					
						DefaultMutableTreeNode tempZNode = new DefaultMutableTreeNode(Cina.cinaMainDataStructure.getElementSymbol(appndsds.getZList()[i]));
						
						treeModel.insertNodeInto(tempZNode, parentNode, i);
						
						createANodes(appndsds, tempZNode, i);
					
					}
				}
			}
		}	
	}
	
	/**
	 * Creates the a nodes.
	 *
	 * @param appndsds the appndsds
	 * @param parentNode the parent node
	 * @param zIndex the z index
	 */
	public void createANodes(NucDataSetDataStructure appndsds, DefaultMutableTreeNode parentNode, int zIndex){
			
		for(int j=0; j<200; j++){
		
			if(appndsds.getIsotopeList()[zIndex][j]!=-1){
			
				DefaultMutableTreeNode tempANode = new DefaultMutableTreeNode(String.valueOf(appndsds.getIsotopeList()[zIndex][j]) + (String)(parentNode.getUserObject()));

				treeModel.insertNodeInto(tempANode, parentNode, j);
				
				treeModel.insertNodeInto(new DefaultMutableTreeNode("Not available."), tempANode, 0);
			
			}
		
		}
	
	}
	
	/**
	 * Creates the reaction nodes.
	 *
	 * @param appndsds the appndsds
	 * @param parentNode the parent node
	 * @param zIndex the z index
	 * @param aIndex the a index
	 */
	public void createReactionNodes(NucDataSetDataStructure appndsds, DefaultMutableTreeNode parentNode, int zIndex, int aIndex){
	
		ds.setCurrentNucDataSetDataStructure(appndsds);
		
		ds.setNucDataSetName(appndsds.getNucDataSetName());
	
		ds.setIsotopeString(String.valueOf(zIndex) + "," + String.valueOf(aIndex));
	
		ds.setTypeString("");
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET NUC DATA LIST", Cina.rateGenFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
	
					Vector reactionNodeViktor = new Vector();
					
					reactionNodeViktor.addElement(new String(""));
	
					int counter = 0;
	
					for(int i=0; i<appndsds.getNucDataDataStructures().length; i++){
					
						if(appndsds.getNucDataDataStructures()[i].getDecay().equals("")){
					
							if(!reactionNodeViktor.contains(appndsds.getNucDataDataStructures()[i].getReactionString())){
							
								reactionNodeViktor.addElement(appndsds.getNucDataDataStructures()[i].getReactionString());
							
								DefaultMutableTreeNode tempNucDataNode = new DefaultMutableTreeNode(appndsds.getNucDataDataStructures()[i].getReactionString());
	
								treeModel.insertNodeInto(tempNucDataNode, parentNode, counter);
								
								createDataTypeNodes(appndsds, tempNucDataNode);
								
								counter++;
							
							}
						
						}else{
						
							if(!reactionNodeViktor.contains(appndsds.getNucDataDataStructures()[i].getReactionString()
								+ " [" 
								+ appndsds.getNucDataDataStructures()[i].getDecay()
								+ "]")){
							
								reactionNodeViktor.addElement(appndsds.getNucDataDataStructures()[i].getReactionString()
																+ " [" 
																+ appndsds.getNucDataDataStructures()[i].getDecay()
																+ "]");
							
								DefaultMutableTreeNode tempNucDataNode = new DefaultMutableTreeNode(appndsds.getNucDataDataStructures()[i].getReactionString()
																										+ " [" 
																										+ appndsds.getNucDataDataStructures()[i].getDecay()
																										+ "]");
	
								treeModel.insertNodeInto(tempNucDataNode, parentNode, counter);
								
								createDataTypeNodes(appndsds, tempNucDataNode);
								
								counter++;
							
							}
						
						}
					
					}
					
					treeModel.removeNodeFromParent((DefaultMutableTreeNode)parentNode.getChildAt(counter));
					
				}
			}
		}
	}
	
	/**
	 * Creates the data type nodes.
	 *
	 * @param appndsds the appndsds
	 * @param parentNode the parent node
	 */
	public void createDataTypeNodes(NucDataSetDataStructure appndsds, DefaultMutableTreeNode parentNode){
		
		int counter = 0;
		
		csFound:			
		for(int i=0; i<appndsds.getNucDataDataStructures().length; i++){
		
			if(appndsds.getNucDataDataStructures()[i].getNucDataType().equals("CS(E)")){
			
				DefaultMutableTreeNode tempNucDataNode = new DefaultMutableTreeNode("CS(E)");
						
				treeModel.insertNodeInto(tempNucDataNode, parentNode, counter);
				
				createNucDataNodes(appndsds, tempNucDataNode);
				
				counter++;
				
				break csFound;
				
			}
			
		}

		sFound:
		for(int i=0; i<appndsds.getNucDataDataStructures().length; i++){
		
			if(appndsds.getNucDataDataStructures()[i].getNucDataType().equals("S(E)")){
			
				DefaultMutableTreeNode tempNucDataNode = new DefaultMutableTreeNode("S(E)");
						
				treeModel.insertNodeInto(tempNucDataNode, parentNode, counter);
				
				createNucDataNodes(appndsds, tempNucDataNode);
				
				counter++;
				
				break sFound;
				
			}
			
		}
		
		if(counter==0){
		
			DefaultMutableTreeNode tempNucDataNode = new DefaultMutableTreeNode("Reaction does not have CS(E) or S(E) data type");
					
			treeModel.insertNodeInto(tempNucDataNode, parentNode, counter);
		
		}

	}
	
	/**
	 * Creates the nuc data nodes.
	 *
	 * @param appndsds the appndsds
	 * @param parentNode the parent node
	 */
	public void createNucDataNodes(NucDataSetDataStructure appndsds, DefaultMutableTreeNode parentNode){
	
		int counter = 0;
	
		for(int i=0; i<appndsds.getNucDataDataStructures().length; i++){
		
			if(appndsds.getNucDataDataStructures()[i].getDecay().equals("")){
		
				if(appndsds.getNucDataDataStructures()[i].getReactionString().equals(parentNode.getParent().toString())){
				
					if(appndsds.getNucDataDataStructures()[i].getNucDataType().equals(parentNode.toString())){
					
						DefaultMutableTreeNode tempNucDataNode = new DefaultMutableTreeNode(appndsds.getNucDataDataStructures()[i].getNucDataName());
	
						treeModel.insertNodeInto(tempNucDataNode, parentNode, counter);
						
						counter++;
					
					}
				
				}
			
			}else{
			
				if((appndsds.getNucDataDataStructures()[i].getReactionString()
						+ " ["
						+ appndsds.getNucDataDataStructures()[i].getDecay()
						+ "]").equals(parentNode.getParent().toString())){
				
					if(appndsds.getNucDataDataStructures()[i].getNucDataType().equals(parentNode.toString())){
					
						DefaultMutableTreeNode tempNucDataNode = new DefaultMutableTreeNode(appndsds.getNucDataDataStructures()[i].getNucDataName());
	
						treeModel.insertNodeInto(tempNucDataNode, parentNode, counter);
						
						counter++;
					
					}
				
				}
			
			}
		
		}
	
	} 
	
	/**
	 * Check nuc data field.
	 *
	 * @return true, if successful
	 */
	public boolean checkNucDataField(){
	
		boolean goodNucData = false;
	
		if(!nucDataField.getText().equals("")){
		
			goodNucData = true;
		
		}
	
		return goodNucData;
	
	}
	
	/**
	 * Gets the nuc data id string.
	 *
	 * @param setGroup the set group
	 * @param set the set
	 * @param reactionString the reaction string
	 * @param nucDataName the nuc data name
	 * @return the nuc data id string
	 */
	public String getNucDataIDString(String setGroup, String set, String reactionString, String nucDataName){
		
		String nucDataID = "";
		
		String decay = "";
		
		if(reactionString.indexOf("[")!=-1){
		
			decay = reactionString.substring(reactionString.indexOf("[")+1, reactionString.indexOf("]"));
		
			reactionString = reactionString.substring(0, reactionString.indexOf("[")-1);

		}
		
		if(setGroup.equals("Public")){
		
			for(int i=0; i<ds.getNumberPublicNucDataSetDataStructures(); i++){
			
				if(ds.getPublicNucDataSetDataStructureArray()[i].getNucDataSetName().equals(set)){
				
					for(int j=0; j<ds.getPublicNucDataSetDataStructureArray()[i].getNucDataDataStructures().length; j++){
					
						if(reactionString.equals(ds.getPublicNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getReactionString())
							&& decay.equals(ds.getPublicNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getDecay())){
						
							if(nucDataName.equals(ds.getPublicNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getNucDataName())){
						
								nucDataID = ds.getPublicNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getNucDataID();
						
							}
						
						}
					
					}
				
				}
			
			}
		
		}else if(setGroup.equals("Shared")){
		
			for(int i=0; i<ds.getNumberSharedNucDataSetDataStructures(); i++){
			
				if(ds.getSharedNucDataSetDataStructureArray()[i].getNucDataSetName().equals(set)){
				
					for(int j=0; j<ds.getSharedNucDataSetDataStructureArray()[i].getNucDataDataStructures().length; j++){
					
						if(reactionString.equals(ds.getSharedNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getReactionString())
							&& decay.equals(ds.getSharedNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getDecay())){
						
							if(nucDataName.equals(ds.getSharedNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getNucDataName())){
						
								nucDataID = ds.getSharedNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getNucDataID();
						
							}
						
						}
					
					}
				
				}
			
			}
		
		}else if(setGroup.equals("User")){
		
			for(int i=0; i<ds.getNumberUserNucDataSetDataStructures(); i++){
			
				if(ds.getUserNucDataSetDataStructureArray()[i].getNucDataSetName().equals(set)){
				
					for(int j=0; j<ds.getUserNucDataSetDataStructureArray()[i].getNucDataDataStructures().length; j++){
					
						if(reactionString.equals(ds.getUserNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getReactionString())
							&& decay.equals(ds.getUserNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getDecay())){
						
							if(nucDataName.equals(ds.getUserNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getNucDataName())){
						
								nucDataID = ds.getUserNucDataSetDataStructureArray()[i].getNucDataDataStructures()[j].getNucDataID();
						
							}
						
						}
					
					}
				
				}
			
			}
		
		}
		
		return nucDataID;	
		
	}
	
	/**
	 * Creates the point arrays.
	 */
	public void createPointArrays(){
		
		//for d + d --> 4He
		isoIn1[0] = new Point(1, 2);
		isoIn1[1] = new Point(1, 2);
		isoOut1[0] = new Point(2, 4);
		
		isoInVector.add(isoIn1);
		isoOutVector.add(isoOut1);
		
		//for d + d --> p + t
		isoIn2[0] = new Point(1, 2);
		isoIn2[1] = new Point(1, 2);
		isoOut2[0] = new Point(1, 1);
		isoOut2[1] = new Point(1, 3);
		
		isoInVector.add(isoIn2);
		isoOutVector.add(isoOut2);
		
		//for d + d --> n + 3He
		isoIn3[0] = new Point(1, 2);
		isoIn3[1] = new Point(1, 2);
		isoOut3[0] = new Point(0, 1);
		isoOut3[1] = new Point(2, 3);
		
		isoInVector.add(isoIn3);
		isoOutVector.add(isoOut3);
		
		//for p + d --> 3He
		isoIn4[0] = new Point(1, 1);
		isoIn4[1] = new Point(1, 2);
		isoOut4[0] = new Point(2, 3);
		
		isoInVector.add(isoIn4);
		isoOutVector.add(isoOut4);
		
		//for t + 4He --> g + 7Li
		isoIn5[0] = new Point(1, 3);
		isoIn5[1] = new Point(2, 4);
		isoOut5[0] = new Point(3, 7);
		
		isoInVector.add(isoIn5);
		isoOutVector.add(isoOut5);
		
		//for 3He + 3He --> p + p + 4He
		isoIn6[0] = new Point(2, 3);
		isoIn6[1] = new Point(2, 3);
		isoOut6[0] = new Point(1, 1);
		isoOut6[1] = new Point(1, 1);
		isoOut6[2] = new Point(2, 4);
		
		isoInVector.add(isoIn6);
		isoOutVector.add(isoOut6);
		
		//for p + 6Li --> 3He + 4He
		isoIn7[0] = new Point(1, 1);
		isoIn7[1] = new Point(3, 6);
		isoOut7[0] = new Point(2, 3);
		isoOut7[1] = new Point(2, 4);
		
		isoInVector.add(isoIn7);
		isoOutVector.add(isoOut7);
		
		//for p + 7Li --> 4He + 4He
		isoIn8[0] = new Point(1, 1);
		isoIn8[1] = new Point(3, 7);
		isoOut8[0] = new Point(2, 4);
		isoOut8[1] = new Point(2, 4);
		
		isoInVector.add(isoIn8);
		isoOutVector.add(isoOut8);
		
		//for p + Be9 --> 4He + 6Li
		isoIn9[0] = new Point(1, 1);
		isoIn9[1] = new Point(4, 9);
		isoOut9[0] = new Point(2, 4);
		isoOut9[1] = new Point(3, 6);
		
		isoInVector.add(isoIn9);
		isoOutVector.add(isoOut9);
		
		//for p + Be9 --> B10
		isoIn10[0] = new Point(1, 1);
		isoIn10[1] = new Point(4, 9);
		isoOut10[0] = new Point(5, 10);
		
		isoInVector.add(isoIn10);
		isoOutVector.add(isoOut10);
		
		//for p + B10 --> C11
		isoIn11[0] = new Point(1, 1);
		isoIn11[1] = new Point(5, 10);
		isoOut11[0] = new Point(6, 11);
		
		isoInVector.add(isoIn11);
		isoOutVector.add(isoOut11);
		
		//for p + B11 --> 4He + Be8
		isoIn12[0] = new Point(1, 1);
		isoIn12[1] = new Point(5, 11);
		isoOut12[0] = new Point(2, 4);
		isoOut12[1] = new Point(4, 8);
		
		isoInVector.add(isoIn12);
		isoOutVector.add(isoOut12);
	
	} 
	
	/**
	 * Check data fields.
	 *
	 * @return true, if successful
	 */
	public boolean checkDataFields(){

		boolean pass = true;
	
		if(ds.getUploadData()){
	
			try{
			
				if(reactionTypeComboBox.getSelectedIndex()!=7 
					|| (reactionTypeComboBox.getSelectedIndex()==7 
							&&(!reactionStringOutZFieldArray[1].getText().equals("") && !reactionStringOutAFieldArray[1].getText().equals("")))
					|| (reactionTypeComboBox.getSelectedIndex()==7 
							&&(reactionStringOutZFieldArray[1].getText().equals("0") && reactionStringOutAFieldArray[1].getText().equals("0")))){
			
					Point[] isoIn = new Point[3];
					Point[] isoOut = new Point[4];
					
					//Loop over reactant textfields
					for(int i=0; i<numberReactants[reactionTypeComboBox.getSelectedIndex()]; i++){
						isoIn[i] = new Point(Integer.valueOf(reactionStringInZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringInAFieldArray[i].getText()).intValue());
					}
		
					for(int i=0; i<numberProducts[reactionTypeComboBox.getSelectedIndex()]; i++){
						isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
					}
				
					ds.setUseSecondProduct(true);
						
				}else{
					
					Point[] isoIn = new Point[3];
					Point[] isoOut = new Point[4];
					
					//Loop over reactant textfields
					for(int i=0; i<numberReactants[reactionTypeComboBox.getSelectedIndex()]; i++){
						isoIn[i] = new Point(Integer.valueOf(reactionStringInZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringInAFieldArray[i].getText()).intValue());
					}
		
					for(int i=0; i<1; i++){
						isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
					}
					
					ds.setUseSecondProduct(false);
					
				}
			
			}catch(NumberFormatException nfe){
			
				String string = "Z and A entries must be integer values.";
				
				Dialogs.createExceptionDialog(string, Cina.rateGenFrame);
				
				pass = false;
			
			}
		
		}else{
		
			if(!nucDataField.getText().equals("")){
		
				pass = true;
			
			}
		
		}
		
		return pass;
	
	}
	
	/**
	 * Sets the reaction names demo.
	 */
	public void setReactionNamesDemo(){
		
		reactionNameComboBox.removeItemListener(this);
	
		reactionNameComboBox.removeAllItems();
		
		for(int i=0; i<reactionNameArray.length; i++){
    		reactionNameComboBox.addItem(reactionNameArray[i]);
    	}
		
		reactionNameComboBox.addItemListener(this);
		
		reactionNameComboBox.setSelectedIndex(ds.getDemoComboBox());
		
	}
	
	/**
	 * Creates the cgi comm reaction string.
	 *
	 * @param in the in
	 * @param out the out
	 * @param numIn the num in
	 * @param numOut the num out
	 * @return the string
	 */
	public String createCGICommReactionString(Point[] in, Point[] out, int numIn, int numOut){
	
		String string = "";
		
		for(int i=0; i<numIn; i++){
		
			if(i!=(numIn-1)){
			
				string = string + String.valueOf((int)in[i].getX()) + "," 
							+ String.valueOf((int)in[i].getY()) + "+";
			
			}else{
			
				string = string + String.valueOf((int)in[i].getX()) + "," 
							+ String.valueOf((int)in[i].getY()) + "-->";
			
			}
		
		}
		
		for(int i=0; i<numOut; i++){
		
			if(i!=(numOut-1)){
			
				string = string + String.valueOf((int)out[i].getX()) + "," 
							+ String.valueOf((int)out[i].getY()) + "+";
			
			}else{
			
				string = string + String.valueOf((int)out[i].getX()) + "," 
							+ String.valueOf((int)out[i].getY());
			
			}
		
		}
	
		return string;
		
	}
	
	//Method setCurremtState sets the state of each component
	//in the panel by accessing the RateGenDataStructure object
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		if(ds.getUploadData()){
		
			reactionNameComboBox.setSelectedIndex(ds.getDemoComboBox());
			
			//Set the reaction type drop down menu to the saved reaction type
			reactionTypeComboBox.setSelectedIndex(ds.getRateDataStructure().getReactionType()-1);
			
			//Call method setReactionStringUI to set up dynamic Z,A input fields
			setReactionStringUI(ds.getRateDataStructure().getReactionType()-1);
			
			if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
	
				decayTypeComboBox.setSelectedItem(ds.getRateDataStructure().getDecayType());
			
			}
			
			if(Cina.cinaMainDataStructure.getUser().equals("guest")){
			
				ds.getRateDataStructure().setIsoIn((Point[])isoInVector.elementAt(ds.getDemoComboBox()));
				ds.getRateDataStructure().setIsoOut((Point[])isoOutVector.elementAt(ds.getDemoComboBox()));
			
			}
			
			//Loop over reactant textfields
			for(int i=0; i<numberReactants[reactionTypeComboBox.getSelectedIndex()]; i++){
				
				//Set the text of each text field to the value in the data structure
				reactionStringInZFieldArray[i].setText(String.valueOf((int)ds.getRateDataStructure().getIsoIn()[i].getX()));           
				
				//This is important. DO NOT CHANGE!
				//When creating a dynamic set of fields
				//always set columns to one. Otherwise, is the value of Z or A is 
				//larger than two digits, the field containing it
				//will change size, thereby, destroying the appropriate
				//layout configuration.  
				reactionStringInZFieldArray[i].setColumns(3);
				reactionStringInAFieldArray[i].setText(String.valueOf((int)ds.getRateDataStructure().getIsoIn()[i].getY()));
				reactionStringInAFieldArray[i].setColumns(3);
				
			}
			
			if(ds.getRateDataStructure().getReactionType()==8){
			
				if(ds.getUseSecondProduct()){
				
					//Loop over product text Z,A fields
					for(int i=0; i<numberProducts[reactionTypeComboBox.getSelectedIndex()]; i++){
							
						reactionStringOutZFieldArray[i].setText(String.valueOf((int)ds.getRateDataStructure().getIsoOut()[i].getX()));           
						reactionStringOutZFieldArray[i].setColumns(3);
						reactionStringOutAFieldArray[i].setText(String.valueOf((int)ds.getRateDataStructure().getIsoOut()[i].getY())); 
						reactionStringOutAFieldArray[i].setColumns(3);
					
					}
				
				}else{
				
					//Loop over product text Z,A fields
					for(int i=0; i<1; i++){
							
						reactionStringOutZFieldArray[i].setText(String.valueOf((int)ds.getRateDataStructure().getIsoOut()[i].getX()));           
						reactionStringOutZFieldArray[i].setColumns(3);
						reactionStringOutAFieldArray[i].setText(String.valueOf((int)ds.getRateDataStructure().getIsoOut()[i].getY())); 
						reactionStringOutAFieldArray[i].setColumns(3);
					
					}
				
				}
			
			}else{
			
				for(int i=0; i<numberProducts[reactionTypeComboBox.getSelectedIndex()]; i++){
							
					reactionStringOutZFieldArray[i].setText(String.valueOf((int)ds.getRateDataStructure().getIsoOut()[i].getX()));           
					reactionStringOutZFieldArray[i].setColumns(3);
					reactionStringOutAFieldArray[i].setText(String.valueOf((int)ds.getRateDataStructure().getIsoOut()[i].getY())); 
					reactionStringOutAFieldArray[i].setColumns(3);
				
				}
			
			}
			
			//Set the textof the Notes area
			notesTextArea.setText(ds.getRateDataStructure().getReactionNotes());
			
			if(Cina.cinaMainDataStructure.getUser().equals("guest")){
			
				if(ds.getInputType().equals("CS(E)")){
				
					box1.setSelected(true);
				
				}else if(ds.getInputType().equals("S(E)")){
				
					box2.setSelected(true);
				
				}
			}
			
			if(Cina.cinaMainDataStructure.getUser().equals("guest")){
			
				createPointArrays();
				setReactionNamesDemo();
			
			}
			
		}else{
		
			nucDataField.setText(ds.getCurrentNucDataString());
			rateField.setText(ds.getCurrentRateString());
			nucDataSetField.setText(ds.getCurrentNucDataSetString());
		
		}
		
	}
	
	//Method getCurrentState updates the current data structure
	//with the information currently showing in the JPanel
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		if(ds.getUploadData()){
		
			//Create to Point arrays to hold Z,A pairs 
			//for raectants and products
			Point[] isoIn = new Point[3];
			Point[] isoOut = new Point[4];
			
			ds.getRateDataStructure().setReactionString((String)reactionNameComboBox.getSelectedItem());
			
			ds.setDemoComboBox(reactionNameComboBox.getSelectedIndex());
			
			//Set the reaction type index in the data structure to the one on the screen
			ds.getRateDataStructure().setReactionType(reactionTypeComboBox.getSelectedIndex()+1);
	
			if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
	
				ds.getRateDataStructure().setDecayType((String)decayTypeComboBox.getSelectedItem());
	
			}
	
			//Loop over reactant textfields
			for(int i=0; i<numberReactants[reactionTypeComboBox.getSelectedIndex()]; i++){
				isoIn[i] = new Point(Integer.valueOf(reactionStringInZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringInAFieldArray[i].getText()).intValue());
			}
			
			//Update the isoIn array of the data structure
			//with the local one  
			ds.getRateDataStructure().setIsoIn(isoIn); 
			
			if(ds.getUseSecondProduct()){
			
				for(int i=0; i<numberProducts[reactionTypeComboBox.getSelectedIndex()]; i++){
					isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
				}
			
				ds.getRateDataStructure().setNumberReactants(numberReactants[reactionTypeComboBox.getSelectedIndex()]);
				ds.getRateDataStructure().setNumberProducts(numberProducts[reactionTypeComboBox.getSelectedIndex()]);
				
			}else{
			
				for(int i=0; i<1; i++){
					isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
				}
				
				ds.getRateDataStructure().setNumberReactants(numberReactants[reactionTypeComboBox.getSelectedIndex()]);
				ds.getRateDataStructure().setNumberProducts(1);
				
			}
			
			//Update the isoOut array of the data structure
			//with the local one
			ds.getRateDataStructure().setIsoOut(isoOut); 
			
			//Update the data structure with the latest notes
			ds.getRateDataStructure().setReactionNotes(notesTextArea.getText());
			
			//for cgi properties
			ds.setNotes(notesTextArea.getText());
			
			ds.setRateUnits(units[ds.getRateDataStructure().getReactionType()]);
	
			if(Cina.cinaMainDataStructure.getUser().equals("guest")){
			
				if(box1.isSelected()){
				
					ds.setInputType("CS(E)");
				
				}else if(box2.isSelected()){
				
					ds.setInputType("S(E)");
				
				}
				
			}
		
		}else{
		
			ds.setCurrentNucDataString(nucDataField.getText());
			ds.setCurrentRateString(rateField.getText());
			ds.setCurrentNucDataSetString(nucDataSetField.getText());
		
		}
	
	}
	
	//Method itemStateChanged for the reaction type drop down menu
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		
		//If the source is the menu
		if(ie.getSource()==reactionNameComboBox){
			
			reactionTypeComboBox.setSelectedIndex(reactionIndexArray[reactionNameComboBox.getSelectedIndex()]);

			//Loop over reactant textfields
			for(int i=0; i<numberReactants[reactionIndexArray[reactionNameComboBox.getSelectedIndex()]]; i++){
				
				//Set the text of each text field to the value in the data structure
				reactionStringInZFieldArray[i].setText(String.valueOf((int)(((Point[])(isoInVector.elementAt(reactionNameComboBox.getSelectedIndex())))[i].getX())));           
				
				//This is important. DO NOT CHANGE!
				//When creating a dynamic set of fields
				//always set columns to one. Otherwise, is the value of Z or A is 
				//larger than two digits, the field containing it
				//will change size, thereby, destroying the appropriate
				//layout configuration.  
				reactionStringInZFieldArray[i].setColumns(3);
				reactionStringInAFieldArray[i].setText(String.valueOf((int)(((Point[])(isoInVector.elementAt(reactionNameComboBox.getSelectedIndex())))[i].getY())));          
				reactionStringInAFieldArray[i].setColumns(3);
				
			}
			
			//Loop over product text Z,A fields
			for(int i=0; i<numberProducts[reactionIndexArray[reactionNameComboBox.getSelectedIndex()]]; i++){
				
				reactionStringOutZFieldArray[i].setText(String.valueOf((int)(((Point[])(isoOutVector.elementAt(reactionNameComboBox.getSelectedIndex())))[i].getX())));                     
				reactionStringOutZFieldArray[i].setColumns(3);
				reactionStringOutAFieldArray[i].setText(String.valueOf((int)(((Point[])(isoOutVector.elementAt(reactionNameComboBox.getSelectedIndex())))[i].getY()))); 
				reactionStringOutAFieldArray[i].setColumns(3);
				
			}
			
			//then call method to update UI
			setReactionStringUI(reactionIndexArray[reactionNameComboBox.getSelectedIndex()]);

			//notesTextArea.setText(reactionNameArray[reactionNameComboBox.getSelectedIndex()]);
			
		}else if(ie.getSource()==reactionTypeComboBox){
		
			setReactionStringUI(reactionTypeComboBox.getSelectedIndex());
		
		}else if(ie.getSource()==box1 || ie.getSource()==box2){
			
			createPointArrays();
			setReactionNamesDemo();
			
			reactionTypeComboBox.setSelectedIndex(reactionIndexArray[reactionNameComboBox.getSelectedIndex()]);
			
			//Loop over reactant textfields
			for(int i=0; i<numberReactants[reactionIndexArray[reactionNameComboBox.getSelectedIndex()]]; i++){
				
				//Set the text of each text field to the value in the data structure
				reactionStringInZFieldArray[i].setText(String.valueOf((int)(((Point[])(isoInVector.elementAt(reactionNameComboBox.getSelectedIndex())))[i].getX())));           
				
				//This is important. DO NOT CHANGE!
				//When creating a dynamic set of fields
				//always set columns to one. Otherwise, is the value of Z or A is 
				//larger than two digits, the field containing it
				//will change size, thereby, destroying the appropriate
				//layout configuration.  
				reactionStringInZFieldArray[i].setColumns(3);
				reactionStringInAFieldArray[i].setText(String.valueOf((int)(((Point[])(isoInVector.elementAt(reactionNameComboBox.getSelectedIndex())))[i].getY())));          
				reactionStringInAFieldArray[i].setColumns(3);
				
			}
			
			//Loop over product text Z,A fields
			for(int i=0; i<numberProducts[reactionIndexArray[reactionNameComboBox.getSelectedIndex()]]; i++){
				
				reactionStringOutZFieldArray[i].setText(String.valueOf((int)(((Point[])(isoOutVector.elementAt(reactionNameComboBox.getSelectedIndex())))[i].getX())));                     
				reactionStringOutZFieldArray[i].setColumns(3);
				reactionStringOutAFieldArray[i].setText(String.valueOf((int)(((Point[])(isoOutVector.elementAt(reactionNameComboBox.getSelectedIndex())))[i].getY()))); 
				reactionStringOutAFieldArray[i].setColumns(3);
				
			}
			
			//then call method to update UI
			setReactionStringUI(reactionIndexArray[reactionNameComboBox.getSelectedIndex()]);

		}
	}
	
	
	//Method setReactionStringUI sets up the dynamic input interface
    //for the (Z,A) pairs
    //Argument: reaction type
    /**
	 * Sets the reaction string ui.
	 *
	 * @param type the new reaction string ui
	 */
	public void setReactionStringUI(int type){
    	
    	//remove all components before assigning new ones
  		reactionUIPanel.removeAll();
    	
    	//Switch on type
		switch(type){
		
			case 0:
				
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
				
				break;
		
			case 1:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				break;
				
			case 2:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 8;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[1], gbc);
    			
    			gbc.gridx = 9;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[2], gbc);
    			
    			gbc.gridx = 10;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[2], gbc);
    			
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				gbc.gridx = 9;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[3], gbc);
				
				gbc.gridx = 10;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[3], gbc);	
				
				break;
		
			case 3:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[1], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[1], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				break;
				
			case 4:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[1], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[1], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 8;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[1], gbc);
    			
    			gbc.gridx = 9;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 10;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				gbc.gridx = 9;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[3], gbc);
				
				gbc.gridx = 10;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[3], gbc);

				break;
		
			case 5:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[1], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[1], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 8;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[1], gbc);
    			
    			gbc.gridx = 9;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 10;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 11;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[2], gbc);
    			
    			gbc.gridx = 12;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[2], gbc);
    			
    			gbc.gridx = 13;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[2], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				gbc.gridx = 9;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[3], gbc);
				
				gbc.gridx = 10;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[3], gbc);
				
				gbc.gridx = 12;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[4], gbc);
				
				gbc.gridx = 13;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[4], gbc);
	
				break;
				
			case 6:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[1], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[1], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 8;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[1], gbc);
    			
    			gbc.gridx = 9;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 10;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 11;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[2], gbc);
    			
    			gbc.gridx = 12;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[2], gbc);
    			
    			gbc.gridx = 13;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[2], gbc);
    			
    			gbc.gridx = 14;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[3], gbc);
    			
    			gbc.gridx = 15;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[3], gbc);
    			
    			gbc.gridx = 16;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[3], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				gbc.gridx = 9;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[3], gbc);
				
				gbc.gridx = 10;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[3], gbc);
				
				gbc.gridx = 12;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[4], gbc);
				
				gbc.gridx = 13;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[4], gbc);
				
				gbc.gridx = 15;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[5], gbc);
				
				gbc.gridx = 16;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[5], gbc);

				break;
		
			case 7:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[1], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[1], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[1], gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[2], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[2], gbc);
    			
    			gbc.gridx = 8;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 9;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 10;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 11;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusParenLabel, gbc);
    			
    			gbc.gridx = 12;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 13;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 14;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(parenLabel, gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				gbc.gridx = 9;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[3], gbc);
				
				gbc.gridx = 10;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[3], gbc);
				
				gbc.gridx = 12;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[4], gbc);
				
				gbc.gridx = 13;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[4], gbc);
				
				break;
		}
		
		
		//Reset gbc variables for other components
		gbc.anchor = GridBagConstraints.WEST;
    	gbc.insets = new Insets(0, 0, 0, 0);
    	
    	
    	
    	reactionUIPanel.setVisible(false);
    	reactionUIPanel.setVisible(true);
	}

}