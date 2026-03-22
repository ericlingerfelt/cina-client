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
 * The Class RateManRateGrid2Panel.
 */
public class RateManRateGrid2Panel extends WizardPanel implements ActionListener, TreeExpansionListener{

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
	
	/** The reaction list. */
	private JList reactionList;
	
	/** The list title label. */
	private JLabel listTitleLabel;
	
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
	private Vector rateIDVector, decayVector, homeLibVector, usedTreePaths;
	
	/** The number rate i ds. */
	private int numberRateIDs = 0;
	
	/** The removal reaction string array. */
	private String[] removalReactionStringArray;
	
	/** The ds. */
	private RateManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate man rate grid2 panel.
	 *
	 * @param ds the ds
	 */
	public RateManRateGrid2Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(4);
		Cina.rateManFrame.setCurrentPanelIndex(2);
		addWizardPanel("Rate Manager", "Rates on a Grid", "2", "3");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel treePanel = new JPanel(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		rateIDVector = new Vector();
		decayVector = new Vector();	
		homeLibVector = new Vector();
		usedTreePaths = new Vector();

		JLabel treeTitleLabel = new JLabel("Select reactions from the tree below.");
		
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

		JScrollPane sp1 = new JScrollPane(libTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp1.setPreferredSize(new Dimension(300, 300));
		
		treePanel.add(treeTitleLabel, BorderLayout.NORTH);
		treePanel.add(sp1, BorderLayout.CENTER);
	
		reactionListModel = new DefaultListModel();
		reactionList = new JList(reactionListModel);
		reactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane sp2 = new JScrollPane(reactionList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp2.setPreferredSize(new Dimension(300, 300));
		
		listTitleLabel = new JLabel();
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.add(listTitleLabel, BorderLayout.NORTH);
		listPanel.add(sp2, BorderLayout.CENTER);
			
		removeRateButton = new JButton("Remove Selected Rate");
		removeRateButton.setFont(Fonts.buttonFont);
		removeRateButton.addActionListener(this);
		
		addRateButton = new JButton("Add Selected Rate");
		addRateButton.setFont(Fonts.buttonFont);
		addRateButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(removeRateButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		buttonPanel.add(addRateButton, gbc);
			
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, listPanel);	
		sp.setDividerLocation(250);
		
		mainPanel.add(sp, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
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
		
			Object[] removalArrayObject = reactionList.getSelectedValues();
			removalReactionStringArray = new String[removalArrayObject.length];
			
			for(int i=0; i<removalArrayObject.length; i++){
				
				StringTokenizer st = new StringTokenizer((String)removalArrayObject[i]);
				removalReactionStringArray[i] = "";
				int numberTokens = st.countTokens();
				
				for(int j=0; j<numberTokens-1; j++){
					removalReactionStringArray[i] = removalReactionStringArray[i] + st.nextToken() + " ";
				}
				
				removalReactionStringArray[i] = removalReactionStringArray[i].trim();
			
			}
			
			String[] removalArray = new String[removalArrayObject.length];
			
			for(int i=0; i<removalArrayObject.length; i++){
				removalArray[i] = (String)removalArrayObject[i];
			}
			
			int index = reactionList.getSelectedIndex();
			
			for(int i=0; i<removalArray.length; i++){
				reactionListModel.removeElement(removalArray[i]);
			}
			
			rateIDVector.removeElementAt(index);
			decayVector.removeElementAt(index);
			homeLibVector.removeElementAt(index);
			
			numberRateIDs--;
		
		}else if(ae.getSource()==addRateButton){
			
			if(((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).isLeaf()){
			
				String newListReaction = libTree.getSelectionPath().getLastPathComponent().toString() 
											+ " (" + ((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().toString() + ")";
				String homeLibString = ((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().toString();
				String tempString = "";
	
				boolean goodReaction = true;
	
				if(!reactionListModel.contains(newListReaction)){
				
					reactionListModel.addElement(newListReaction);
			
					tempString = new String(getRateIDString(((DefaultMutableTreeNode)((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent()).getUserObject().toString()
													, ((DefaultMutableTreeNode)((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent()).getUserObject().toString()
													, ((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getUserObject().toString()));
					
					numberRateIDs++;
					
					String reactionString = libTree.getSelectionPath().getLastPathComponent().toString();
				
					if(reactionString.indexOf("[")!=-1){
						decayVector.addElement(new String(reactionString.substring(reactionString.indexOf("[")+1, reactionString.indexOf("]"))));
					}else{
						decayVector.addElement(new String(""));
					}
					
					rateIDVector.addElement(tempString);
					homeLibVector.addElement(homeLibString);
				
				}
			
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
	 * Empty rates.
	 *
	 * @return true, if successful
	 */
	protected boolean emptyRates(){
	
		if(ds.getRateIDVectorRateGrid().size()!=0){
			return false;
		}else{
			return true;
		}
		
	}
	
	/**
	 * Creates the rate data structure array.
	 */
	protected void createRateDataStructureArray(){
	
		int numberRateIDs = rateIDVector.size();
		RateDataStructure[] outputArray = new RateDataStructure[numberRateIDs];

		for(int i=0; i<numberRateIDs; i++){
		
			outputArray[i] = new RateDataStructure();
			outputArray[i].setReactionID((String)(rateIDVector.elementAt(i)));
			outputArray[i].setLibrary((String)(homeLibVector.elementAt(i)));
		
		} 
		
		ds.setCurrentRateDataStructureArray(outputArray);
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		reactionListModel.removeAllElements();
		
		for(int i=0; i<getRateList().length; i++){
			reactionListModel.addElement(getRateList()[i]);
		}
		
		rateIDVector = ds.getRateIDVectorRateGrid();
		decayVector = ds.getDecayVectorRateGrid();
		homeLibVector = ds.getHomeLibVectorRateGrid();
		listTitleLabel.setText("Rate list");
	
	}
	
	/**
	 * Gets the rate list.
	 *
	 * @return the rate list
	 */
	private String[] getRateList(){
	
		String[] stringArray = new String[ds.getRateIDVectorRateGrid().size()];
		
		for(int i=0; i<stringArray.length; i++){
		
			String rateID = (String)ds.getRateIDVectorRateGrid().elementAt(i);
			StringTokenizer st09 = new StringTokenizer(rateID, "\u0009");
			String dummy = st09.nextToken().substring(8);
			String library = (String)ds.getHomeLibVectorRateGrid().elementAt(i);
			String tempString = st09.nextToken();
			String reactionString = tempString.substring(0, tempString.indexOf("\u000b"));
			String decay = (String)ds.getDecayVectorRateGrid().elementAt(i);
			
			if(decay.equals("")){
				stringArray[i] = reactionString + " (" + library + ")";
			}else{
				stringArray[i] = reactionString
									+ " ["
									+ decay
									+ "] "
									+ " (" 
									+ library 
									+ ")";
			}
		
		}
		
		return stringArray;

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
	
		rateIDVector.trimToSize();
		ds.setRateIDVectorRateGrid(rateIDVector);
		
		decayVector.trimToSize();
		ds.setDecayVectorRateGrid(decayVector);
	
		homeLibVector.trimToSize();
		ds.setHomeLibVectorRateGrid(homeLibVector);

	}
	
}