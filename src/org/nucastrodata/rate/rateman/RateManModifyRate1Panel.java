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
 * The Class RateManModifyRate1Panel.
 */
public class RateManModifyRate1Panel extends WizardPanel implements ActionListener, TreeExpansionListener{
	
	/** The user lib group node. */
	private DefaultMutableTreeNode libNode
									, publicLibGroupNode
									, sharedLibGroupNode
									, userLibGroupNode;
		
	/** The user lib group node array. */
	private DefaultMutableTreeNode[] publicLibGroupNodeArray
										, sharedLibGroupNodeArray
										, userLibGroupNodeArray;
	
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
	
	/** The ds. */
	private RateManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate man modify rate1 panel.
	 *
	 * @param ds the ds
	 */
	public RateManModifyRate1Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(3);
		Cina.rateManFrame.setCurrentPanelIndex(1);

		JPanel mainPanel = new JPanel(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		usedTreePaths = new Vector();
		addWizardPanel("Rate Manager", "Modify Existing Rate", "1", "3");
		
		JPanel treePanel = new JPanel(new BorderLayout());
		JLabel treeTitleLabel = new JLabel("Select reaction from the tree below.");
		
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
		
		JLabel topLabel = new JLabel("<html>Welcome to the Modify Existing Rate tool.<p><br>With this tool you can modify an existing<p>rate. "
										+ "In Step 1, choose a reaction from the<p>tree. In Steps 2 and 3, modify properties<p>of the reaction rate. Also in Step 3, "
										+ "save<p>the rate to an existing library or a new<p>library and view the results of the rate<p>modification.</html>");
				
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
		gbc.insets = new Insets(3, 3, 3, 3);
		buttonPanel.add(topLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		buttonPanel.add(rateLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		buttonPanel.add(rateField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		buttonPanel.add(libLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		buttonPanel.add(libField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		buttonPanel.add(addRateButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		buttonPanel.add(removeRateButton, gbc);

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
		
			ds.getModifyRateDataStructure().setReactionID("");
		
		}else if(ae.getSource()==addRateButton){
			
			if(((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).isLeaf()){
			
				String newReaction = libTree.getSelectionPath().getLastPathComponent().toString();
	
				String newLibrary = ((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().toString();
	
				String tempString = new String(getRateIDString(((DefaultMutableTreeNode)((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent()).getUserObject().toString()
													, ((DefaultMutableTreeNode)((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent()).getUserObject().toString()
													, ((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getUserObject().toString()));
	
				ds.getModifyRateDataStructure().setReactionID(tempString);
				
				ds.getModifyRateDataStructure().parseIDForDecayType();
	
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
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIBRARY ISOTOPES", Cina.rateManFrame);
		
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
		
					for(int i=0; i<applds.getZList().length; i++){
					
						DefaultMutableTreeNode tempZNode = new DefaultMutableTreeNode(Cina.cinaMainDataStructure.getElementSymbol(applds.getZList()[i]));
						
						treeModel.insertNodeInto(tempZNode, parentNode, i);
						
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
	private void createANodes(LibraryDataStructure applds, DefaultMutableTreeNode parentNode, int zIndex){
			
		for(int j=0; j<200; j++){
		
			if(applds.getIsotopeList()[zIndex][j]!=-1){
			
				DefaultMutableTreeNode tempANode = new DefaultMutableTreeNode(String.valueOf(applds.getIsotopeList()[zIndex][j]) + (String)(parentNode.getUserObject()));

				treeModel.insertNodeInto(tempANode, parentNode, j);
				
				treeModel.insertNodeInto(new DefaultMutableTreeNode("Not avaliable."), tempANode, 0);
			
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
	protected boolean checkRateField(){
	
		if(!rateField.getText().equals("")){
			return true;
		}else{
			return false;
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

		rateField.setText(ds.getCurrentRateStringModify());
		libField.setText(ds.getCurrentLibraryStringModify());

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
	
		ds.setCurrentRateStringModify(rateField.getText());
		ds.setCurrentLibraryStringModify(libField.getText());
	
	}

}