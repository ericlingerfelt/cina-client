package org.nucastrodata.data.dataeval;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataEvalDataStructure;
import org.nucastrodata.datastructure.util.NucDataSetDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class DataEvalInterData2Panel.
 */
public class DataEvalInterData2Panel extends WizardPanel implements ActionListener, TreeExpansionListener{
	
	/** The user set group node. */
	private DefaultMutableTreeNode setNode
									, publicSetGroupNode
									, sharedSetGroupNode
									, userSetGroupNode;
	
	/** The user set group node array. */
	private DefaultMutableTreeNode[] publicSetGroupNodeArray
										, sharedSetGroupNodeArray
										, userSetGroupNodeArray;									
	
	/** The set tree. */
	private JTree setTree;
	
	/** The delay dialog. */
	private JDialog delayDialog;
	
	/** The tree model. */
	private DefaultTreeModel treeModel;
	
	/** The tree selection model. */
	private TreeSelectionModel treeSelectionModel;
	
	/** The add nuc data button. */
	private JButton removeNucDataButton, addNucDataButton;
	
	/** The appndsds temp. */
	private NucDataSetDataStructure appndsdsTemp;
	
	/** The used tree paths. */
	private Vector usedTreePaths;
	
	/** The nuc data set field. */
	private JTextField nucDataField, rateField, nucDataSetField;
	
	/** The ds. */
	private DataEvalDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new data eval inter data2 panel.
	 *
	 * @param ds the ds
	 */
	public DataEvalInterData2Panel(DataEvalDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.dataEvalFrame.setCurrentFeatureIndex(1);
		Cina.dataEvalFrame.setCurrentPanelIndex(2);

		JPanel mainPanel = new JPanel(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		usedTreePaths = new Vector();
		addWizardPanel("Nuclear Data Evaluator's Toolkit", "Cross Section Fuser", "2", "3");
		
		JPanel treePanel = new JPanel(new BorderLayout());
		JLabel treeTitleLabel = new JLabel("Select nuclear data from the tree below.");
		
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

		JScrollPane sp = new JScrollPane(setTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(300, 300));
		
		treePanel.add(treeTitleLabel, BorderLayout.NORTH);
		treePanel.add(sp, BorderLayout.CENTER);
		
		JLabel topLabel = new JLabel("<html>Now select the cross section file for<p>the second curve from the tree at<p>the right.</html>");
		
		nucDataField = new JTextField(20);
		nucDataField.setEditable(false);
		
		rateField = new JTextField(20);
		rateField.setEditable(false);
		
		nucDataSetField = new JTextField(20);
		nucDataSetField.setEditable(false);
		
		JLabel nucDataLabel = new JLabel("Nuclear Data: ");
		nucDataLabel.setFont(Fonts.textFont);
		
		JLabel rateLabel = new JLabel("Reaction Rate: ");
		rateLabel.setFont(Fonts.textFont);
		
		JLabel nucDataSetLabel = new JLabel("Nuclear Data Set: ");
		nucDataSetLabel.setFont(Fonts.textFont);
		
		removeNucDataButton = new JButton("Clear Fields");
		removeNucDataButton.setFont(Fonts.buttonFont);
		removeNucDataButton.addActionListener(this);
		
		addNucDataButton = new JButton("Add Selected Nuclear Data");
		addNucDataButton.setFont(Fonts.buttonFont);
		addNucDataButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 20, 3);
		buttonPanel.add(topLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(3, 3, 3, 3);
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
			ds.getInter2NucDataDataStructure().setNucDataID("");
		
		}else if(ae.getSource()==addNucDataButton){
			
			if(((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).isLeaf()){
			
				String newNucData = ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getUserObject().toString();
				
				if(!newNucData.equals("Only S-factor data is available")){
					
					String newReaction = ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().toString();
					String newNucDataSet = ((DefaultMutableTreeNode)((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent()).getUserObject().toString();						
					String tempString = new String(getNucDataIDString(((DefaultMutableTreeNode)((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent().getParent()).getUserObject().toString()
														, ((DefaultMutableTreeNode)((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent()).getUserObject().toString()
														, ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().toString()
														, ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getUserObject().toString()));
		
					if(newNucData.indexOf("Reaction does not")==-1){
		
						ds.getInter2NucDataDataStructure().setNucDataID(tempString);
			
						nucDataField.setText(newNucData);
						rateField.setText(newReaction);
						nucDataSetField.setText(newNucDataSet);
					
					}
				
				}
			
			}
			
		}
	
	}
	
	/**
	 * Creates the set group nodes.
	 */
	protected void createSetGroupNodes(){
	
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
	private void createPublicNucDataSetDataStructureNodes(){

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
	private void createSharedNucDataSetDataStructureNodes(){

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
	private void createUserNucDataSetDataStructureNodes(){
	
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
	private void createZNodes(NucDataSetDataStructure appndsds, DefaultMutableTreeNode parentNode){
	
		ds.setNucDataSetName(appndsds.getNucDataSetName());
		ds.setCurrentNucDataSetDataStructure(appndsds);
	
		boolean check = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET ISOTOPES", Cina.dataEvalFrame);
		
		if(check){
		
			for(int i=0; i<appndsds.getZList().length; i++){
			
				DefaultMutableTreeNode tempZNode = new DefaultMutableTreeNode(Cina.cinaMainDataStructure.getElementSymbol(appndsds.getZList()[i]));
				treeModel.insertNodeInto(tempZNode, parentNode, i);
				createANodes(appndsds, tempZNode, i);
			
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
	private void createANodes(NucDataSetDataStructure appndsds, DefaultMutableTreeNode parentNode, int zIndex){
			
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
	private void createReactionNodes(NucDataSetDataStructure appndsds, DefaultMutableTreeNode parentNode, int zIndex, int aIndex){
	
		ds.setCurrentNucDataSetDataStructure(appndsds);
		ds.setNucDataSetName(appndsds.getNucDataSetName());
		ds.setIsotopeString(String.valueOf(zIndex) + "," + String.valueOf(aIndex));
		ds.setTypeString("");
	
		boolean check = Cina.cinaCGIComm.doCGICall("GET NUC DATA LIST", Cina.dataEvalFrame);
	
		if(check){
	
			Vector reactionNodeViktor = new Vector();
			reactionNodeViktor.addElement(new String(""));

			int counter = 0;

			for(int i=0; i<appndsds.getNucDataDataStructures().length; i++){
			
				if(appndsds.getNucDataDataStructures()[i].getDecay().equals("")){
			
					if(!reactionNodeViktor.contains(appndsds.getNucDataDataStructures()[i].getReactionString())){
					
						reactionNodeViktor.addElement(appndsds.getNucDataDataStructures()[i].getReactionString());
						DefaultMutableTreeNode tempNucDataNode = new DefaultMutableTreeNode(appndsds.getNucDataDataStructures()[i].getReactionString());
						treeModel.insertNodeInto(tempNucDataNode, parentNode, counter);
						createNucDataNodes(appndsds, tempNucDataNode);
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
						createNucDataNodes(appndsds, tempNucDataNode);
						counter++;
					
					}
				
				}
			
			}
			
			treeModel.removeNodeFromParent((DefaultMutableTreeNode)parentNode.getChildAt(counter));
					
		}
	}
	
	/**
	 * Creates the nuc data nodes.
	 *
	 * @param appndsds the appndsds
	 * @param parentNode the parent node
	 */
	private void createNucDataNodes(NucDataSetDataStructure appndsds, DefaultMutableTreeNode parentNode){
	
		int counter = 0;
	
		for(int i=0; i<appndsds.getNucDataDataStructures().length; i++){

			if(appndsds.getNucDataDataStructures()[i].getDecay().equals("")){

				if(appndsds.getNucDataDataStructures()[i].getReactionString().equals(parentNode.toString())){
				
					if(appndsds.getNucDataDataStructures()[i].getNucDataType().equals("CS(E)")){
										
						DefaultMutableTreeNode tempNucDataNode = new DefaultMutableTreeNode(appndsds.getNucDataDataStructures()[i].getNucDataName());
						treeModel.insertNodeInto(tempNucDataNode, parentNode, counter);
						counter++;
					
					}
				
				}
			
			}else{
			
				if((appndsds.getNucDataDataStructures()[i].getReactionString()
						+ " ["
						+ appndsds.getNucDataDataStructures()[i].getDecay()
						+ "]").equals(parentNode.toString())){
				
					if(appndsds.getNucDataDataStructures()[i].getNucDataType().equals("CS(E)")){
										
						DefaultMutableTreeNode tempNucDataNode = new DefaultMutableTreeNode(appndsds.getNucDataDataStructures()[i].getNucDataName());
						treeModel.insertNodeInto(tempNucDataNode, parentNode, counter);
						counter++;
					
					}
				
				}
			
			}
		
		}
		
		if(counter==0){
		
			DefaultMutableTreeNode tempNucDataNode = new DefaultMutableTreeNode("Reaction does not have CS(E) data type");
			treeModel.insertNodeInto(tempNucDataNode, parentNode, counter);
		
		}
	
	} 
	
	/**
	 * Check nuc data field.
	 *
	 * @return true, if successful
	 */
	protected boolean checkNucDataField(){
	
		if(!nucDataField.getText().equals("")){
			return true;
		}else{
			return false;
		}

	}
	
	/**
	 * Good reaction match.
	 *
	 * @return true, if successful
	 */
	protected boolean goodReactionMatch(){
	
		boolean goodNucData = false;
	
		if(ds.getInter1NucDataDataStructure().getDecay().equals("")){
	
			if(ds.getInter1NucDataDataStructure().getReactionString().equals(rateField.getText())){
			
				goodNucData = true;
			
			}
		
		}else{
		
			if((ds.getInter1NucDataDataStructure().getReactionString()
					+ " ["
					+ ds.getInter1NucDataDataStructure().getDecay()
					+ "]").equals(rateField.getText())){
			
				goodNucData = true;
			
			}
		
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
	private String getNucDataIDString(String setGroup, String set, String reactionString, String nucDataName){
		
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
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		nucDataField.setText(ds.getCurrentNucDataString2Eval());
		rateField.setText(ds.getCurrentRateString2Eval());
		nucDataSetField.setText(ds.getCurrentNucDataSetString2Eval());

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		ds.setCurrentNucDataString2Eval(nucDataField.getText());
		ds.setCurrentRateString2Eval(rateField.getText());
		ds.setCurrentNucDataSetString2Eval(nucDataSetField.getText());
	
	}
	
}