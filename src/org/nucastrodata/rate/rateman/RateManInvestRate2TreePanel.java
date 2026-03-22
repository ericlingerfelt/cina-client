package org.nucastrodata.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateManInvestRate2TreePanel.
 */
public class RateManInvestRate2TreePanel extends WizardPanel implements ActionListener, TreeExpansionListener{
	
	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The button panel. */
	JPanel mainPanel, buttonPanel; 
	
	/** The lib node. */
	DefaultMutableTreeNode libNode;
	
	/** The user lib group node array. */
	DefaultMutableTreeNode[] publicLibGroupNodeArray, sharedLibGroupNodeArray, userLibGroupNodeArray;
	
	/** The lib tree. */
	JTree libTree;
	
	/** The sp. */
	JScrollPane sp;
	
	/** The delay dialog. */
	JDialog delayDialog;
	
	/** The tree title label. */
	JLabel treeTitleLabel;
	
	/** The tree panel. */
	JPanel treePanel;

	/** The reaction list model. */
	DefaultListModel reactionListModel; 
	
	/** The tree model. */
	DefaultTreeModel treeModel;

	/** The tree selection model. */
	TreeSelectionModel treeSelectionModel;
	
	/** The add rate button. */
	JButton removeRateButton, addRateButton;
	
	/** The applds temp. */
	LibraryDataStructure appldsTemp;

	/** The used tree paths. */
	Vector usedTreePaths;

	/** The rate field. */
	JTextField rateField;

	/** The rate label. */
	JLabel rateLabel;

	/** The element string array. */
	String[] elementStringArray = {"n","H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na"
			, "Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe" 
			, "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb"
			, "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te", "I", "Xe", "Cs", "Ba"
			, "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu"
			, "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn"
			, "Fr", "Ra", "Ac", "Th", "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md"
			, "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg", "Cn", "Nh", "Fl", "Mc"
			, "Lv", "Ts", "Og", "Uue", "Ubn", "Ubu", "Ubb", "Ubt", "Ubq", "Ubp", "Ubh", "Ubs", "Ubo"
			, "Ube", "Utn", "Utu", "Utb", "Utt", "Utq", "Utp", "Uth", "Uts", "Uto", "Ute", "Uqn", "Uqu"
			, "Uqb", "Uqt", "Uqq", "Uqp", "Uqh", "Uqs", "Uqo", "Uqe", "Upn", "Upu", "Upb", "Upt", "Upq"
			, "Upp", "Uph", "Ups", "Upo", "Upe", "Uhn", "Uhu", "Uhb", "Uht", "Uhq", "Uhp", "Uhh", "Uhs"
			, "Uho", "Uhe", "Usn", "Usu", "Usb", "Ust", "Usq", "Usp"};
	
	/** The ds. */
	private RateManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate man invest rate2 tree panel.
	 *
	 * @param ds the ds
	 */
	public RateManInvestRate2TreePanel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(6);
		Cina.rateManFrame.setCurrentPanelIndex(2);

		mainPanel = new JPanel(new BorderLayout());
		gbc = new GridBagConstraints();
		
		usedTreePaths = new Vector();
		
		addWizardPanel("Rate Manager", "Rate Locator", "2", "3");
		
		treePanel = new JPanel(new BorderLayout());
		
		treeTitleLabel = new JLabel("<html>Select reaction for investigation<p>from the tree at the left.</html>");
		
		libNode = new DefaultMutableTreeNode("Available Rates");
		
		treeModel = new DefaultTreeModel(libNode);
		
		treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		libTree = new JTree(treeModel);
		libTree.setEditable(false);
		libTree.putClientProperty("JTree.linestyle", "Angled");
		libTree.setSelectionModel(treeSelectionModel);
		libTree.addTreeExpansionListener(this);
		libTree.setShowsRootHandles(true);

		sp = new JScrollPane(libTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(300, 300));
		
		treePanel.add(sp, BorderLayout.CENTER);
		
		rateField = new JTextField(20);
		rateField.setEditable(false);
		
		rateLabel = new JLabel("Reaction Rate: ");
		rateLabel.setFont(Fonts.textFont);
		
		removeRateButton = new JButton("Clear Rate Field");
		removeRateButton.setFont(Fonts.buttonFont);
		removeRateButton.addActionListener(this);
		
		addRateButton = new JButton("Add Selected Rate");
		addRateButton.setFont(Fonts.buttonFont);
		addRateButton.addActionListener(this);
		
		buttonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(treeTitleLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(rateLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		buttonPanel.add(rateField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		buttonPanel.add(addRateButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		buttonPanel.add(removeRateButton, gbc);

		mainPanel.add(treePanel, BorderLayout.CENTER);
		
		mainPanel.add(buttonPanel, BorderLayout.EAST);

		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeExpansionListener#treeExpanded(javax.swing.event.TreeExpansionEvent)
	 */
	public void treeExpanded(TreeExpansionEvent tee){

		if(tee.getPath().getPathCount()==3 
				&& !usedTreePaths.contains(tee.getPath())){
			
			appldsTemp = ds.getCurrentLibraryDataStructure();
			
			int zIndex = ((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getParent().getIndex(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent());
			
			int aIndex = ((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getIndex((DefaultMutableTreeNode)tee.getPath().getLastPathComponent());
	
			if(appldsTemp.getIsotopeList()[zIndex][aIndex]!=-1){
				
				createReactionLeaves(appldsTemp
										, (DefaultMutableTreeNode)tee.getPath().getLastPathComponent()
										, appldsTemp.getZList()[zIndex]
										, appldsTemp.getIsotopeList()[zIndex][aIndex]);
										
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
	
		if(ae.getSource()==removeRateButton){
		
			rateField.setText("");
			
			ds.getInvestRateDataStructure().setReactionID("");
		
		}else if(ae.getSource()==addRateButton){
			
			if(((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).isLeaf()){
			
				String newReaction = libTree.getSelectionPath().getLastPathComponent().toString();
	
				String mainLibrary = "ReaclibV2.2";
				
				String tempString = new String(getRateIDString("Shared", mainLibrary, newReaction));
	
				ds.getInvestRateDataStructure().setReactionID(tempString);
				
				ds.getInvestRateDataStructure().setReactionString(newReaction);
				
				ds.getInvestRateDataStructure().parseIDForDecayType();

				rateField.setText(newReaction);
			
			}
			
		}
	
	}
	
	/**
	 * Gets the rate id string.
	 *
	 * @param libGroup the lib group
	 * @param library the library
	 * @param reactionString the reaction string
	 * @return the rate id string
	 */
	public String getRateIDString(String libGroup, String library, String reactionString){
		
		String rateID = "";
		
		String decay = "";
		
		if(reactionString.indexOf("[")!=-1){
		
			decay = reactionString.substring(reactionString.indexOf("[")+1, reactionString.indexOf("]"));
		
			reactionString = reactionString.substring(0, reactionString.indexOf("[")-1);

		}
		
		for(int j=0; j<ds.getCurrentLibraryDataStructure().getRateDataStructures().length; j++){
		
			if(reactionString.equals(ds.getCurrentLibraryDataStructure().getRateDataStructures()[j].getReactionString())
				&& decay.equals(ds.getCurrentLibraryDataStructure().getRateDataStructures()[j].getDecay())){
			
				rateID = ds.getCurrentLibraryDataStructure().getRateDataStructures()[j].getReactionID();
			
			}
		
		}
					
		return rateID;	
		
	}
	
	/**
	 * Creates the z nodes.
	 */
	public void createZNodes(){
	
		ds.setLibraryName("ReaclibV2.2");
	
		LibraryDataStructure applds = new LibraryDataStructure();
	
		applds.setLibName("ReaclibV2.2");
	
		ds.setCurrentLibraryDataStructure(applds);
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIBRARY ISOTOPES", Cina.rateManFrame);
		
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
		
					for(int i=0; i<applds.getZList().length; i++){
					
						DefaultMutableTreeNode tempZNode = new DefaultMutableTreeNode(getElementSymbol(applds.getZList()[i]));
						
						treeModel.insertNodeInto(tempZNode, libNode, i);
						
						createANodes(applds, tempZNode, i);
					
					}
				}
			}
		}	
	}
	
	/**
	 * Creates the a nodes.
	 *
	 * @param applds the applds
	 * @param parentNode the parent node
	 * @param zIndex the z index
	 */
	public void createANodes(LibraryDataStructure applds, DefaultMutableTreeNode parentNode, int zIndex){
			
		for(int j=0; j<200; j++){
		
			if(applds.getIsotopeList()[zIndex][j]!=-1){
			
				DefaultMutableTreeNode tempANode = new DefaultMutableTreeNode(String.valueOf(applds.getIsotopeList()[zIndex][j]) + (String)(parentNode.getUserObject()));

				treeModel.insertNodeInto(tempANode, parentNode, j);
				
				treeModel.insertNodeInto(new DefaultMutableTreeNode("Not available."), tempANode, 0);
			
			}
		
		}
	
	}
	
	/**
	 * Creates the reaction leaves.
	 *
	 * @param applds the applds
	 * @param parentNode the parent node
	 * @param zIndex the z index
	 * @param aIndex the a index
	 */
	public void createReactionLeaves(LibraryDataStructure applds, DefaultMutableTreeNode parentNode, int zIndex, int aIndex){
	
		ds.setCurrentLibraryDataStructure(applds);
		
		ds.setLibraryName(applds.getLibName());
	
		ds.setIsotopeString(String.valueOf(zIndex) + "," + String.valueOf(aIndex));
	
		ds.setTypeDatabaseString("");
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIST", Cina.rateManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
	
					DefaultMutableTreeNode tempRateNode = null;
	
					for(int i=0; i<applds.getRateDataStructures().length; i++){
				
						if(applds.getRateDataStructures()[i].getDecay().equals("")){
				
							tempRateNode = new DefaultMutableTreeNode(applds.getRateDataStructures()[i].getReactionString());
							
						}else{
						
							tempRateNode = new DefaultMutableTreeNode(applds.getRateDataStructures()[i].getReactionString()
																								+ " ["
																								+ applds.getRateDataStructures()[i].getDecay()
																								+ "]");
						
						}
							
						treeModel.insertNodeInto(tempRateNode, parentNode, i);

					}
					
					treeModel.removeNodeFromParent((DefaultMutableTreeNode)parentNode.getChildAt(applds.getRateDataStructures().length));
					
				}
			}
		}
	}
	
	/**
	 * Check rate field.
	 *
	 * @return true, if successful
	 */
	public boolean checkRateField(){
	
		boolean goodRate = false;
	
		if(!rateField.getText().equals("")){
		
			goodRate = true;
		
		}
	
		return goodRate;
	
	}
	
	/**
	 * Gets the element symbol.
	 *
	 * @param z the z
	 * @return the element symbol
	 */
	public String getElementSymbol(int z){return elementStringArray[z];}
	
	/**
	 * Good rates exist.
	 *
	 * @return true, if successful
	 */
	public boolean goodRatesExist(){
	
		boolean goodRatesExist = false;
		
		ds.setRates(getRateIDList());
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("RATES EXIST", Cina.rateManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
	
					goodRatesExist = true;
					
				}
			}
		}
		
		return goodRatesExist;
	
	}
	
	/**
	 * Good rate info.
	 *
	 * @return true, if successful
	 */
	public boolean goodRateInfo(){
    
    	boolean goodRateInfo = false;

		ds.setRates(getGoodRateIDList());

		ds.setRateProperties("Reaction String"
        																+ "\u0009Number of Parameters"
        																+ "\u0009Parameters"
        																+ "\u0009Resonant Components"
        																+ "\u0009Reaction Type"
        																+ "\u0009Biblio Code"
        																+ "\u0009Reaction Notes"
        																+ "\u0009Q-value"
        																+ "\u0009Chisquared"
        																+ "\u0009Creation Date"
        																+ "\u0009Max Percent Difference"
        																+ "\u0009Reference Citation"
        																+ "\u0009Valid Temp Range");

		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE INFO", Cina.rateManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
	
					goodRateInfo = true;
					
				}
			}
		}
		
		return goodRateInfo;
		
    }
	
	/**
	 * Gets the good rate id list.
	 *
	 * @return the good rate id list
	 */
	public String getGoodRateIDList(){
	
		String string = "";
		
		Vector rateIDVector = new Vector();
		
		String[] tempArray = ds.getInvestRateIDs();
		
		for(int i=0; i<tempArray.length; i++){
		
			StringTokenizer st09 = new StringTokenizer(tempArray[i], "\u0009");
		
			String firstPart = st09.nextToken();
			String secondPart = st09.nextToken();
		
			if(st09.nextToken().equals("EXIST=YES")){
			
				rateIDVector.addElement(firstPart + "\u0009" + secondPart);	
			
			}
		
		}

		rateIDVector.trimToSize();

		for(int i=0; i<rateIDVector.size(); i++){
		
			if(i==0){
			
				string += (String)rateIDVector.elementAt(i);
			
			}else{
				
				string += "\n" + (String)rateIDVector.elementAt(i);
			
			}
		
		}

		RateDataStructure[] apprdsa = new RateDataStructure[rateIDVector.size()];

		for(int i=0; i<rateIDVector.size(); i++){
		
			apprdsa[i] = new RateDataStructure();
			apprdsa[i].setReactionID((String)rateIDVector.elementAt(i));
		
		}

		ds.setInvestRateDataStructureArray(apprdsa);

		return string;
	
	}
	
	/**
	 * Gets the rate id list.
	 *
	 * @return the rate id list
	 */
	public String getRateIDList(){
	
		String string = "";
	
		String currentRateID = ds.getInvestRateDataStructure().getReactionID();
		
		String firstPart = currentRateID.substring(0,8);

		String secondPart = currentRateID.substring(currentRateID.indexOf("\u0009"));

		String[] libraryNameArray = new String[ds.getNumberPublicLibraryDataStructures()
												+ ds.getNumberSharedLibraryDataStructures()
												+ ds.getNumberUserLibraryDataStructures()];
		int counter = 0;
		
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
		
			libraryNameArray[counter] = ds.getPublicLibraryDataStructureArray()[i].getLibName();
			
			counter++;
		
		}	
		
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
		
			libraryNameArray[counter] = ds.getSharedLibraryDataStructureArray()[i].getLibName();
			
			counter++;
		
		}									
		
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
		
			libraryNameArray[counter] = ds.getUserLibraryDataStructureArray()[i].getLibName();
			
			counter++;
		
		}
		
		for(int i=0; i<libraryNameArray.length; i++){
			
			if(i==0){
			
				string += firstPart + libraryNameArray[i] + secondPart;
			
			}else{
			
				string += "\n" + firstPart + libraryNameArray[i] + secondPart;
			
			}
			
		}

		return string;
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		rateField.setText(ds.getCurrentRateStringInvest());

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
	
		ds.setCurrentRateStringInvest(rateField.getText());
	
	}
	
}