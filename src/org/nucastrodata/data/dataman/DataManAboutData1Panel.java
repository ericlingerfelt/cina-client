package org.nucastrodata.data.dataman;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataManDataStructure;
import org.nucastrodata.datastructure.util.NucDataDataStructure;
import org.nucastrodata.datastructure.util.NucDataSetDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class DataManAboutData1Panel.
 */
public class DataManAboutData1Panel extends WizardPanel implements ActionListener, TreeExpansionListener{

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
	
	/** The nuc data list. */
	private JList nucDataList;
	
	/** The list title label. */
	private JLabel listTitleLabel;
	
	/** The nuc data list model. */
	private DefaultListModel nucDataListModel; 
	
	/** The tree model. */
	private DefaultTreeModel treeModel;
	
	/** The tree selection model. */
	private TreeSelectionModel treeSelectionModel;
	
	/** The add nuc data button. */
	private JButton removeNucDataButton, addNucDataButton;
	
	/** The appndsds temp. */
	private NucDataSetDataStructure appndsdsTemp;
	
	/** The used tree paths. */
	private Vector nucDataIDVector, decayVector, homeNucDataSetVector
																, usedTreePaths;
	
	/** The number nuc data i ds. */
	private int numberNucDataIDs = 0;
	
	/** The removal nuc data string array. */
	private String[] removalNucDataStringArray;
	
	/** The ds. */
	private DataManDataStructure ds;
	
	/**
	 * Instantiates a new data man about data1 panel.
	 *
	 * @param ds the ds
	 */
	public DataManAboutData1Panel(DataManDataStructure ds){
		
		this.ds = ds;
		
		Cina.dataManFrame.setCurrentFeatureIndex(1);
		Cina.dataManFrame.setCurrentPanelIndex(1);
		addWizardPanel("Nuclear Data Manager", "Nuclear Data Info", "1", "2");
		JPanel mainPanel = new JPanel(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		nucDataIDVector = new Vector();
		decayVector = new Vector();
		homeNucDataSetVector = new Vector();
		usedTreePaths = new Vector();

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

		JScrollPane sp1 = new JScrollPane(setTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp1.setPreferredSize(new Dimension(300, 300));
		
		treePanel.add(treeTitleLabel, BorderLayout.NORTH);
		treePanel.add(sp1, BorderLayout.CENTER);
		
		nucDataListModel = new DefaultListModel();
		nucDataList = new JList(nucDataListModel);
		nucDataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane sp2 = new JScrollPane(nucDataList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp2.setPreferredSize(new Dimension(300, 300));
		
		listTitleLabel = new JLabel();
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.add(listTitleLabel, BorderLayout.NORTH);
		listPanel.add(sp2, BorderLayout.CENTER);
			
		removeNucDataButton = new JButton("Remove Selected Nuclear Data");
		removeNucDataButton.setFont(Fonts.buttonFont);
		removeNucDataButton.addActionListener(this);
		
		addNucDataButton = new JButton("Add Selected Nuclear Data");
		addNucDataButton.setFont(Fonts.buttonFont);
		addNucDataButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(removeNucDataButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		buttonPanel.add(addNucDataButton, gbc);
			
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

		if(tee.getPath().getPathCount()==5 && !usedTreePaths.contains(tee.getPath())){
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
			Object[] removalArrayObject = nucDataList.getSelectedValues();
			removalNucDataStringArray = new String[removalArrayObject.length];
			
			for(int i=0; i<removalArrayObject.length; i++){
				StringTokenizer st = new StringTokenizer((String)removalArrayObject[i]);
				removalNucDataStringArray[i] = "";
				int numberTokens = st.countTokens();
				
				for(int j=0; j<numberTokens-1; j++){
					removalNucDataStringArray[i] = removalNucDataStringArray[i] + st.nextToken() + " ";
				}
				
				removalNucDataStringArray[i] = removalNucDataStringArray[i].trim();
			}
			
			String[] removalArray = new String[removalArrayObject.length];

			for(int i=0; i<removalArrayObject.length; i++){
				removalArray[i] = (String)removalArrayObject[i];
			}
			
			int index = nucDataList.getSelectedIndex();
			
			for(int i=0; i<removalArray.length; i++){
				nucDataListModel.removeElement(removalArray[i]);
			}
			
			nucDataIDVector.removeElementAt(index);
			decayVector.removeElementAt(index);
			homeNucDataSetVector.removeElementAt(index);
			numberNucDataIDs--;
		
		}else if(ae.getSource()==addNucDataButton){
			
			if(((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).isLeaf()){
				String newListNucData = setTree.getSelectionPath().getLastPathComponent().toString() 
											+ " (" 
											+ ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().toString() 
											+ ", "
											+ ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent().getParent().toString()
											+ ")";
				String homeNucDataSetString = " (" 
											+ ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().toString() 
											+ ", "
											+ ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent().getParent().toString()
											+ ")";			
				String tempString = "";
	
				if(!nucDataListModel.contains(newListNucData)){
					
					if(newListNucData.indexOf("Reaction does not")==-1){
						
						nucDataListModel.addElement(newListNucData);
						tempString = new String(getNucDataIDString(((DefaultMutableTreeNode)((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent().getParent().getParent()).getUserObject().toString()
														, ((DefaultMutableTreeNode)((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent().getParent()).getUserObject().toString()
														, ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().toString()
														, ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getUserObject().toString()));
						numberNucDataIDs++;
						
						String reactionString = ((DefaultMutableTreeNode)(setTree.getSelectionPath().getLastPathComponent())).getParent().getParent().toString();
					
						if(reactionString.indexOf("[")!=-1){
							decayVector.addElement(new String(reactionString.substring(reactionString.indexOf("[")+1, reactionString.indexOf("]"))));
						}else{
							decayVector.addElement(new String(""));
						}
						
						nucDataIDVector.addElement(tempString);
						homeNucDataSetVector.addElement(homeNucDataSetString);
					
					}
				}
			}
		}
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
	
		boolean check = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET ISOTOPES", Cina.dataManFrame);
		
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
	
		boolean check = Cina.cinaCGIComm.doCGICall("GET NUC DATA LIST", Cina.dataManFrame);
	
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
	
	/**
	 * Creates the data type nodes.
	 *
	 * @param appndsds the appndsds
	 * @param parentNode the parent node
	 */
	private void createDataTypeNodes(NucDataSetDataStructure appndsds, DefaultMutableTreeNode parentNode){
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
	private void createNucDataNodes(NucDataSetDataStructure appndsds, DefaultMutableTreeNode parentNode){
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
	 * Empty nuc data.
	 *
	 * @return true, if successful
	 */
	protected boolean emptyNucData(){
		if(ds.getNucDataIDVectorAboutData().size()!=0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * Creates the nuc data data structure array.
	 */
	protected void createNucDataDataStructureArray(){
		int numberNucDataIDs = nucDataIDVector.size();
		NucDataDataStructure[] outputArray = new NucDataDataStructure[numberNucDataIDs];
		
		for(int i=0; i<numberNucDataIDs; i++){
			outputArray[i] = new NucDataDataStructure();
			outputArray[i].setNucDataID((String)(nucDataIDVector.elementAt(i)));
			outputArray[i].setNucDataSet((String)(homeNucDataSetVector.elementAt(i)));
		} 
		
		ds.setCurrentNucDataDataStructureArray(outputArray);
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		nucDataListModel.removeAllElements();
		for(int i=0; i<getNucDataList().length; i++){nucDataListModel.addElement(getNucDataList()[i]);}
		nucDataIDVector = ds.getNucDataIDVectorAboutData();
		decayVector = ds.getDecayVectorAboutData();
		homeNucDataSetVector = ds.getHomeNucDataSetVectorAboutData();
		listTitleLabel.setText("Nuclear data list");
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		nucDataIDVector.trimToSize();
		ds.setNucDataIDVectorAboutData(nucDataIDVector);
		decayVector.trimToSize();
		ds.setDecayVectorAboutData(decayVector);
		homeNucDataSetVector.trimToSize();
		ds.setHomeNucDataSetVectorAboutData(homeNucDataSetVector);
	}
	
	/**
	 * Gets the nuc data list.
	 *
	 * @return the nuc data list
	 */
	private String[] getNucDataList(){
		String[] stringArray = new String[ds.getNucDataIDVectorAboutData().size()];
		
		for(int i=0; i<stringArray.length; i++){
			String nucDataID = (String)ds.getNucDataIDVectorAboutData().elementAt(i);
			String nucDataSet = (String)ds.getHomeNucDataSetVectorAboutData().elementAt(i);
			String reactionString = nucDataID.substring(nucDataID.lastIndexOf("\u000b")+1);
			stringArray[i] = reactionString + "" + nucDataSet;
		}
		
		return stringArray;
	}
}