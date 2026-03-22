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

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateManRateErrorCheck1Panel.
 */
public class RateManRateErrorCheck1Panel extends WizardPanel implements ActionListener, TreeExpansionListener{
	
	/** The user lib group node. */
	private DefaultMutableTreeNode libNode, publicLibGroupNode, sharedLibGroupNode, userLibGroupNode;
	
	/** The user lib group node array. */
	private DefaultMutableTreeNode[] publicLibGroupNodeArray, sharedLibGroupNodeArray, userLibGroupNodeArray;
	
	/** The lib tree. */
	private JTree libTree;
	
	/** The delay dialog. */
	private JDialog delayDialog;
	
	/** The reaction list model. */
	private DefaultListModel reactionListModel; 
	
	/** The tree model. */
	private DefaultTreeModel treeModel;
	
	/** The tree selection model. */
	private TreeSelectionModel treeSelectionModel;
	
	/** The add rate button. */
	private JButton removeRateButton, addRateButton;
	
	/** The applds temp. */
	private LibraryDataStructure appldsTemp;
	
	/** The used tree paths. */
	private Vector usedTreePaths;
	
	/** The lib field. */
	private JTextField rateField, libField;
	
	/** The over box. */
	private JCheckBox tempBox, inverseBox, overBox;
	
	/** The ds. */
	private RateManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate man rate error check1 panel.
	 *
	 * @param ds the ds
	 */
	public RateManRateErrorCheck1Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(5);
		Cina.rateManFrame.setCurrentPanelIndex(1);

		JPanel mainPanel = new JPanel(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		usedTreePaths = new Vector();
		
		addWizardPanel("Rate Manager", "Rate Error Check", "1", "2");
		
		tempBox = new JCheckBox("Extreme temperature behavior", true);
		overBox = new JCheckBox("OverFlow", true);
		inverseBox = new JCheckBox("<html>Rate determined by detailed balance<p>(inverse rate) included</html>", true);
 
		tempBox.setFont(Fonts.textFont);
		overBox.setFont(Fonts.textFont);
		inverseBox.setFont(Fonts.textFont);
		
		JPanel treePanel = new JPanel(new BorderLayout());
		
		JLabel treeTitleLabel = new JLabel("Select reaction for error check from the tree at the left.");
		
		libNode = new DefaultMutableTreeNode("Libraries");
		
		treeModel = new DefaultTreeModel(libNode);
		
		treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		libTree = new JTree(treeModel);
		libTree.setEditable(false);
		libTree.putClientProperty("JTree.linestyle", "Angled");
		libTree.setSelectionModel(treeSelectionModel);
		libTree.addTreeExpansionListener(this);
		libTree.setShowsRootHandles(true);

		JScrollPane sp = new JScrollPane(libTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(300, 300));
		
		treePanel.add(treeTitleLabel, BorderLayout.NORTH);
		treePanel.add(sp, BorderLayout.CENTER);
		
		rateField = new JTextField(20);
		rateField.setEditable(false);
		
		libField = new JTextField(20);
		libField.setEditable(false);
		
		JLabel rateLabel = new JLabel("Reaction Rate: ");
		rateLabel.setFont(Fonts.textFont);
		
		JLabel libLabel = new JLabel("Library: ");
		libLabel.setFont(Fonts.textFont);
		
		removeRateButton = new JButton("Clear Fields");
		removeRateButton.setFont(Fonts.buttonFont);
		removeRateButton.addActionListener(this);
		
		addRateButton = new JButton("Add Selected Rate");
		addRateButton.setFont(Fonts.buttonFont);
		addRateButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(rateLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		buttonPanel.add(rateField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		buttonPanel.add(libLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		buttonPanel.add(libField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		buttonPanel.add(addRateButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		buttonPanel.add(removeRateButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.WEST;
		buttonPanel.add(tempBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 7;
		buttonPanel.add(inverseBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 8;
		buttonPanel.add(overBox, gbc);

		mainPanel.add(treePanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.EAST);
		add(mainPanel, BorderLayout.CENTER);
		validate();
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeExpansionListener#treeExpanded(javax.swing.event.TreeExpansionEvent)
	 */
	public void treeExpanded(TreeExpansionEvent tee){

		if(tee.getPath().getPathCount()==5 
				&& !usedTreePaths.contains(tee.getPath())){
			
			for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
			
				if(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getParent().toString().equals(ds.getPublicLibraryDataStructureArray()[i].getLibName())){
				
					appldsTemp = ds.getPublicLibraryDataStructureArray()[i];
				
				}

			}
			
			for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
			
				if(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getParent().toString().equals(ds.getSharedLibraryDataStructureArray()[i].getLibName())){
				
					appldsTemp = ds.getSharedLibraryDataStructureArray()[i];
				
				}

			}
			
			for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
			
				if(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getParent().toString().equals(ds.getUserLibraryDataStructureArray()[i].getLibName())){
				
					appldsTemp = ds.getUserLibraryDataStructureArray()[i];
				
				}

			}
			
			int zIndex = ((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getParent().getIndex(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent());
			
			int aIndex = ((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getParent().getIndex((DefaultMutableTreeNode)tee.getPath().getLastPathComponent());
	
			if(appldsTemp.getIsotopeList()[zIndex][aIndex]!=-1){
				
				createReactionLeaves(appldsTemp
										, (DefaultMutableTreeNode)tee.getPath().getLastPathComponent()
										, appldsTemp.getZList()[zIndex]
										, appldsTemp.getIsotopeList()[zIndex][aIndex]);
										
			}
			
			usedTreePaths.addElement(tee.getPath());
										
		}else if(tee.getPath().getPathCount()==3 
				&& !usedTreePaths.contains(tee.getPath())){
			
			for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
				
				if(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).toString().equals(ds.getPublicLibraryDataStructureArray()[i].getLibName())){
				
					appldsTemp = ds.getPublicLibraryDataStructureArray()[i];
				
				}

			}
			
			for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
			
				if(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).toString().equals(ds.getSharedLibraryDataStructureArray()[i].getLibName())){
				
					appldsTemp = ds.getSharedLibraryDataStructureArray()[i];
				
				}

			}
			
			for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
			
				if(((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).toString().equals(ds.getUserLibraryDataStructureArray()[i].getLibName())){
				
					appldsTemp = ds.getUserLibraryDataStructureArray()[i];
				
				}

			}
			
			treeModel.removeNodeFromParent((DefaultMutableTreeNode)((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getChildAt(0));
			
			createZNodes(appldsTemp, ((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()));
			
			
			
			usedTreePaths.addElement(tee.getPath());
			
			libTree.expandPath(tee.getPath());
			
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
			libField.setText("");
			ds.getErrorCheckRateDataStructure().setReactionID("");
		
		}else if(ae.getSource()==addRateButton){
			
			if(((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).isLeaf()){
			
				String newReaction = libTree.getSelectionPath().getLastPathComponent().toString();
				String newLibrary = ((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().toString();
				String tempString = new String(getRateIDString(((DefaultMutableTreeNode)((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent()).getUserObject().toString()
													, ((DefaultMutableTreeNode)((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent()).getUserObject().toString()
													, ((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getUserObject().toString()));
	
				ds.getErrorCheckRateDataStructure().setReactionID(tempString);
				ds.getErrorCheckRateDataStructure().parseIDForDecayType();
				rateField.setText(newReaction);
				libField.setText(newLibrary);
			
			}
			
		}
	
	}
	
	/**
	 * Creates the lib group nodes.
	 */
	protected void createLibGroupNodes(){
	
		publicLibGroupNode = new DefaultMutableTreeNode("Public");
		sharedLibGroupNode = new DefaultMutableTreeNode("Shared");
		userLibGroupNode = new DefaultMutableTreeNode("User");

		if(ds.getNumberPublicLibraryDataStructures()>0){	
			treeModel.insertNodeInto(publicLibGroupNode, libNode, 0);
			createPublicLibraryDataStructureNodes();
		}
		
		if(ds.getNumberSharedLibraryDataStructures()>0){	
			treeModel.insertNodeInto(sharedLibGroupNode, libNode, 0);
			createSharedLibraryDataStructureNodes();
		}
		
		if(ds.getNumberUserLibraryDataStructures()>0){
			treeModel.insertNodeInto(userLibGroupNode, libNode, 0);
			createUserLibraryDataStructureNodes();
		}
	
	}
	
	/**
	 * Creates the public library data structure nodes.
	 */
	private void createPublicLibraryDataStructureNodes(){

		publicLibGroupNodeArray = new DefaultMutableTreeNode[ds.getNumberPublicLibraryDataStructures()];
		int nodeCounter = 0;

		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
		
			publicLibGroupNodeArray[i] = new DefaultMutableTreeNode(ds.getPublicLibraryDataStructureArray()[i].getLibName());
			treeModel.insertNodeInto(publicLibGroupNodeArray[i], publicLibGroupNode, nodeCounter);
			nodeCounter++;
			treeModel.insertNodeInto(new DefaultMutableTreeNode("Not available."), publicLibGroupNodeArray[i], 0);
			
			//createZNodes(ds.getPublicLibraryDataStructureArray()[i], publicLibGroupNodeArray[i]);
					
		}

	}
	
	/**
	 * Creates the shared library data structure nodes.
	 */
	private void createSharedLibraryDataStructureNodes(){

		sharedLibGroupNodeArray = new DefaultMutableTreeNode[ds.getNumberSharedLibraryDataStructures()];
		int nodeCounter = 0;

		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){

			sharedLibGroupNodeArray[i] = new DefaultMutableTreeNode(ds.getSharedLibraryDataStructureArray()[i].getLibName());
			treeModel.insertNodeInto(sharedLibGroupNodeArray[i], sharedLibGroupNode, nodeCounter);
			nodeCounter++;
			treeModel.insertNodeInto(new DefaultMutableTreeNode("Not available."), sharedLibGroupNodeArray[i], 0);
			
			//createZNodes(ds.getSharedLibraryDataStructureArray()[i], sharedLibGroupNodeArray[i]);
					
		}

	}
		
	/**
	 * Creates the user library data structure nodes.
	 */
	private void createUserLibraryDataStructureNodes(){
	
		userLibGroupNodeArray = new DefaultMutableTreeNode[ds.getNumberUserLibraryDataStructures()];
		int nodeCounter = 0;
		
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
		
			userLibGroupNodeArray[i] = new DefaultMutableTreeNode(ds.getUserLibraryDataStructureArray()[i].getLibName());
			treeModel.insertNodeInto(userLibGroupNodeArray[i], userLibGroupNode, nodeCounter);
			nodeCounter++;
			treeModel.insertNodeInto(new DefaultMutableTreeNode("Not available."), userLibGroupNodeArray[i], 0);
			
			//createZNodes(ds.getUserLibraryDataStructureArray()[i], userLibGroupNodeArray[i]);
			
		}
		
	}
	
	/**
	 * Creates the z nodes.
	 *
	 * @param applds the applds
	 * @param parentNode the parent node
	 */
	private void createZNodes(LibraryDataStructure applds, DefaultMutableTreeNode parentNode){
	
		ds.setLibraryName(applds.getLibName());
		ds.setCurrentLibraryDataStructure(applds);
	
		boolean check = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY ISOTOPES", Cina.rateManFrame);
		
		if(check){

			for(int i=0; i<applds.getZList().length; i++){
			
				DefaultMutableTreeNode tempZNode = new DefaultMutableTreeNode(Cina.cinaMainDataStructure.getElementSymbol(applds.getZList()[i]));
				treeModel.insertNodeInto(tempZNode, parentNode, i);
				createANodes(applds, tempZNode, i);
			
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
	private void createANodes(LibraryDataStructure applds, DefaultMutableTreeNode parentNode, int zIndex){
			
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
	private void createReactionLeaves(LibraryDataStructure applds, DefaultMutableTreeNode parentNode, int zIndex, int aIndex){
	
		ds.setCurrentLibraryDataStructure(applds);
		ds.setLibraryName(applds.getLibName());
		ds.setIsotopeString(String.valueOf(zIndex) + "," + String.valueOf(aIndex));
		ds.setTypeDatabaseString("");
	
		boolean check = Cina.cinaCGIComm.doCGICall("GET RATE LIST", Cina.rateManFrame);

		if(check){

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
	
	/**
	 * Check rate field.
	 *
	 * @return true, if successful
	 */
	protected boolean checkRateField(){
	
		if(!rateField.getText().equals("")){
		
			return true;
		
		}else{
			return false;
		}

	}
	
	/**
	 * Good rate info.
	 *
	 * @return true, if successful
	 */
	private boolean goodRateInfo(){
    
		ds.setRates(ds.getErrorCheckRateDataStructure().getReactionID());
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

		return Cina.cinaCGIComm.doCGICall("GET RATE INFO", Cina.rateManFrame);
		
    }
	
	/**
	 * Gets the rate id string.
	 *
	 * @param libGroup the lib group
	 * @param library the library
	 * @param reactionString the reaction string
	 * @return the rate id string
	 */
	private String getRateIDString(String libGroup, String library, String reactionString){
		
		String rateID = "";
		String decay = "";
		
		if(reactionString.indexOf("[")!=-1){
		
			decay = reactionString.substring(reactionString.indexOf("[")+1, reactionString.indexOf("]"));
			reactionString = reactionString.substring(0, reactionString.indexOf("[")-1);

		}
		
		if(libGroup.equals("Public")){
		
			for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
			
				if(ds.getPublicLibraryDataStructureArray()[i].getLibName().equals(library)){
				
					for(int j=0; j<ds.getPublicLibraryDataStructureArray()[i].getRateDataStructures().length; j++){
					
						if(reactionString.equals(ds.getPublicLibraryDataStructureArray()[i].getRateDataStructures()[j].getReactionString())
							&& decay.equals(ds.getPublicLibraryDataStructureArray()[i].getRateDataStructures()[j].getDecay())){
						
							rateID = ds.getPublicLibraryDataStructureArray()[i].getRateDataStructures()[j].getReactionID();
						
						}
					
					}
				
				}
			
			}
		
		}else if(libGroup.equals("Shared")){
		
			for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
			
				if(ds.getSharedLibraryDataStructureArray()[i].getLibName().equals(library)){
				
					for(int j=0; j<ds.getSharedLibraryDataStructureArray()[i].getRateDataStructures().length; j++){
					
						if(reactionString.equals(ds.getSharedLibraryDataStructureArray()[i].getRateDataStructures()[j].getReactionString())
							&& decay.equals(ds.getSharedLibraryDataStructureArray()[i].getRateDataStructures()[j].getDecay())){
						
							rateID = ds.getSharedLibraryDataStructureArray()[i].getRateDataStructures()[j].getReactionID();
						
						}
					
					}
				
				}
			
			}
		
		}else if(libGroup.equals("User")){
		
			for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
			
				if(ds.getUserLibraryDataStructureArray()[i].getLibName().equals(library)){
				
					for(int j=0; j<ds.getUserLibraryDataStructureArray()[i].getRateDataStructures().length; j++){
					
						if(reactionString.equals(ds.getUserLibraryDataStructureArray()[i].getRateDataStructures()[j].getReactionString())
							&& decay.equals(ds.getUserLibraryDataStructureArray()[i].getRateDataStructures()[j].getDecay())){
						
							rateID = ds.getUserLibraryDataStructureArray()[i].getRateDataStructures()[j].getReactionID();
						
						}
					
					}
				
				}
			
			}
		
		}
		
		return rateID;	
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		rateField.setText(ds.getCurrentRateStringErrorCheck());
		libField.setText(ds.getCurrentLibraryStringErrorCheck());

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
	
		ds.setCurrentRateStringErrorCheck(rateField.getText());
		ds.setCurrentLibraryStringErrorCheck(libField.getText());
		
		if(overBox.isSelected()){
			ds.setCHK_OVERFLOW("YES");
		}else{
			ds.setCHK_OVERFLOW("NO");
		}
		
		if(inverseBox.isSelected()){
			ds.setCHK_INVERSE("YES");
		}else{
			ds.setCHK_INVERSE("NO");
		}
	
		if(tempBox.isSelected()){
			ds.setCHK_TEMP_BEHAVIOR("YES");
		}else{
			ds.setCHK_TEMP_BEHAVIOR("NO");
		}
	
	}
	
}