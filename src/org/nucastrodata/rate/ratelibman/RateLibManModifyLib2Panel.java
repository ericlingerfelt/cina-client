package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateLibManModifyLib2Panel.
 */
public class RateLibManModifyLib2Panel extends WizardPanel implements ActionListener, TreeExpansionListener{
	
	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The button panel. */
	JPanel mainPanel, buttonPanel; 
	
	/** The user lib group node. */
	DefaultMutableTreeNode libNode, publicLibGroupNode, sharedLibGroupNode, userLibGroupNode;
	
	/** The user lib group node array. */
	DefaultMutableTreeNode[] publicLibGroupNodeArray, sharedLibGroupNodeArray, userLibGroupNodeArray;
	
	/** The lib tree. */
	JTree libTree;
	
	/** The sp2. */
	JScrollPane sp1, sp2;
	
	/** The delay dialog. */
	JDialog delayDialog;
	
	/** The reaction list. */
	JList reactionList;
	
	/** The list title label. */
	JLabel listTitleLabel;
	
	/** The tree title label. */
	JLabel treeTitleLabel;
	
	/** The list panel. */
	JPanel listPanel;
	
	/** The tree panel. */
	JPanel treePanel;
	
	/** The reaction list model. */
	DefaultListModel reactionListModel; 
	
	/** The tree model. */
	DefaultTreeModel treeModel;
	
	/** The sp. */
	JSplitPane sp;
	
	/** The tree selection model. */
	TreeSelectionModel treeSelectionModel;
	
	/** The add rate button. */
	JButton removeRateButton, addRateButton;
	
	/** The applds temp. */
	LibraryDataStructure appldsTemp;
	
	/** The rate id vector. */
	Vector rateIDVector;
	
	/** The decay vector. */
	Vector decayVector;
	
	/** The home lib vector. */
	Vector homeLibVector;
	
	/** The number rate i ds. */
	int numberRateIDs = 0;
	
	/** The used tree paths. */
	Vector usedTreePaths;
	
	/** The removal reaction string array. */
	String[] removalReactionStringArray;
	
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
	
	/** The good rate merge. */
	boolean goodRateMerge;
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate lib man modify lib2 panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManModifyLib2Panel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateLibManFrame.setCurrentFeatureIndex(2);
		Cina.rateLibManFrame.setCurrentPanelIndex(2);

		mainPanel = new JPanel(new BorderLayout());
		gbc = new GridBagConstraints();
		
		rateIDVector = new Vector();
		
		decayVector = new Vector();
		
		homeLibVector = new Vector();
		
		usedTreePaths = new Vector();
		
		addWizardPanel("Rate Library Manager", "Create and Modify Library", "2", "3");
		
		treePanel = new JPanel(new BorderLayout());
		
		treeTitleLabel = new JLabel("Select reactions from the tree below.");
		
		libNode = new DefaultMutableTreeNode("Libraries");
		
		treeModel = new DefaultTreeModel(libNode);
		
		createLibGroupNodes();
		
		treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		libTree = new JTree(treeModel);
		libTree.setEditable(false);
		libTree.putClientProperty("JTree.linestyle", "Angled");
		libTree.setSelectionModel(treeSelectionModel);
		libTree.addTreeExpansionListener(this);
		libTree.setShowsRootHandles(true);

		sp1 = new JScrollPane(libTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp1.setPreferredSize(new Dimension(300, 300));
		
		treePanel.add(treeTitleLabel, BorderLayout.NORTH);
		
		treePanel.add(sp1, BorderLayout.CENTER);
		
		reactionListModel = new DefaultListModel();
		
		reactionList = new JList(reactionListModel);
		reactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sp2 = new JScrollPane(reactionList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp2.setPreferredSize(new Dimension(300, 300));
		
		listTitleLabel = new JLabel();
		
		listPanel = new JPanel(new BorderLayout());
		
		listPanel.add(listTitleLabel, BorderLayout.NORTH);
		
		listPanel.add(sp2, BorderLayout.CENTER);
			
		removeRateButton = new JButton("Remove Selected Rate");
		removeRateButton.setFont(Fonts.buttonFont);
		removeRateButton.addActionListener(this);
		
		addRateButton = new JButton("Add Selected Rate");
		addRateButton.setFont(Fonts.buttonFont);
		addRateButton.addActionListener(this);
		
		buttonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(removeRateButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		buttonPanel.add(addRateButton, gbc);
			
		sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, listPanel);	
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
	
		sourceBreak:
	
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
				
				if(removalArray[i].equals(ds.getSourceLib())){
				
					String string = "You cannot delete the source library from the reaction list.";
					
					Dialogs.createExceptionDialog(string, Cina.rateLibManFrame);
					
					break sourceBreak;
				
				}

			}
			
			int index = reactionList.getSelectedIndex();
			
			for(int i=0; i<removalArray.length; i++){
			
				reactionListModel.removeElement(removalArray[i]);
			
			}
			
			rateIDVector.removeElementAt(index-1);
			
			decayVector.removeElementAt(index-1);
			
			homeLibVector.removeElementAt(index-1);
			
			numberRateIDs--;
		
		}else if(ae.getSource()==addRateButton){
			
			if(((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).isLeaf()){
			
				String newListReaction = libTree.getSelectionPath().getLastPathComponent().toString() 
											+ " (" + ((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().toString() + ")";
				
				String homeLibString = ((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().toString();
				
				String tempString = "";
	
				boolean goodReaction = true;
	
				if(!reactionListModel.contains(newListReaction)){
				
					ds.setReaction(libTree.getSelectionPath().getLastPathComponent().toString());
					
					ds.setReaction_type(getReaction_type());
	
					goodInverseRate();
				
					if(!checkInvRatePresent()){
				
						for(int i=0; i<reactionListModel.size(); i++){
					
							if(reactionListModel.elementAt(i).toString().startsWith(libTree.getSelectionPath().getLastPathComponent().toString())){
						
								goodReaction = false;
						
							}
							
						}
						
						if(goodReaction){
					
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
						
						}else{
						
							String string = "The reaction rate you have chosen is already present in the reaction list.";
							
							Dialogs.createExceptionDialog(string, Cina.rateLibManFrame);
						
						}
					
					}else{
					
						String string = "The reaction rate you have chosen is the inverse rate of a rate currently in the reaction list.";
							
						Dialogs.createExceptionDialog(string, Cina.rateLibManFrame);
					
					}
				
				}
			
			}
		
		}
	
	}
	
	/**
	 * Gets the reaction_type.
	 *
	 * @return the reaction_type
	 */
	public String getReaction_type(){
		
		String string = "";
		
		String reactionString = libTree.getSelectionPath().getLastPathComponent().toString();
					
		if(reactionString.indexOf("[")!=-1){

			string = reactionString.substring(reactionString.indexOf("[")+1, reactionString.indexOf("]"));
			
		}else{
		
			string = "";
		
		}
		
		return string;
	
	}
	
	/**
	 * Check inv rate present.
	 *
	 * @return true, if successful
	 */
	public boolean checkInvRatePresent(){
	
		boolean invRatePresent = false;

		for(int i=0; i<reactionListModel.size(); i++){

			if(reactionListModel.elementAt(i).toString().startsWith(ds.getInverseReaction())){
		
				invRatePresent = true;
		
			}
			
		}
		
		return invRatePresent;
	
	}
	
	/**
	 * Good inverse rate.
	 *
	 * @return true, if successful
	 */
	public boolean goodInverseRate(){
	
		boolean goodInverseRate = false;

		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET INVERSE REACTION", Cina.rateLibManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
					
					goodInverseRate = true;	
					
				}
			}
		}
		
		return goodInverseRate;
	
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
	public void createLibGroupNodes(){
	
		publicLibGroupNode = new DefaultMutableTreeNode("Public");
	
		sharedLibGroupNode = new DefaultMutableTreeNode("Shared");
	
		userLibGroupNode = new DefaultMutableTreeNode("User");
		
		if(ds.getSourceLibGroup().equals("Public")){
		
			if(ds.getNumberPublicLibraryDataStructures()>1){
					
				treeModel.insertNodeInto(publicLibGroupNode, libNode, 0);
				
				createPublicLibraryDataStructureNodes();
				
			}
			
			if(ds.getNumberSharedLibraryDataStructures()!=0){
	
				treeModel.insertNodeInto(sharedLibGroupNode, libNode, 0);
				
				createSharedLibraryDataStructureNodes();
			
			}
			
			if(ds.getNumberUserLibraryDataStructures()==1
				&& ds.getUserLibraryDataStructureArray()[0].getLibName().equals(ds.getDestLibName())){
			
				//do nothing
			
			}else if(ds.getNumberUserLibraryDataStructures()==1
				&& !ds.getUserLibraryDataStructureArray()[0].getLibName().equals(ds.getDestLibName())){

				treeModel.insertNodeInto(userLibGroupNode, libNode, 0);
				
				createUserLibraryDataStructureNodes();

			}else if(ds.getNumberUserLibraryDataStructures()!=0){
	
				treeModel.insertNodeInto(userLibGroupNode, libNode, 0);
				
				createUserLibraryDataStructureNodes();
			
			}
			
		}else if(ds.getSourceLibGroup().equals("Shared")){
		
			if(ds.getNumberPublicLibraryDataStructures()!=0){
	
				treeModel.insertNodeInto(publicLibGroupNode, libNode, 0);
				
				createPublicLibraryDataStructureNodes();
				
			}
			
			if(ds.getNumberSharedLibraryDataStructures()>1){
	
				treeModel.insertNodeInto(sharedLibGroupNode, libNode, 0);
				
				createSharedLibraryDataStructureNodes();
			
			}
			
			if(ds.getNumberUserLibraryDataStructures()==1
				&& ds.getUserLibraryDataStructureArray()[0].getLibName().equals(ds.getDestLibName())){
			
				//do nothing
			
			}else if(ds.getNumberUserLibraryDataStructures()==1
				&& !ds.getUserLibraryDataStructureArray()[0].getLibName().equals(ds.getDestLibName())){

				treeModel.insertNodeInto(userLibGroupNode, libNode, 0);
				
				createUserLibraryDataStructureNodes();

			}else if(ds.getNumberUserLibraryDataStructures()!=0){
	
				treeModel.insertNodeInto(userLibGroupNode, libNode, 0);
				
				createUserLibraryDataStructureNodes();
			
			}
		
		}else if(ds.getSourceLibGroup().equals("User")){
		
			if(ds.getNumberPublicLibraryDataStructures()!=0){
	
				treeModel.insertNodeInto(publicLibGroupNode, libNode, 0);
				
				createPublicLibraryDataStructureNodes();
				
			}
			
			if(ds.getNumberSharedLibraryDataStructures()!=0){
	
				treeModel.insertNodeInto(sharedLibGroupNode, libNode, 0);
				
				createSharedLibraryDataStructureNodes();
			
			}
			
			if(ds.getNumberUserLibraryDataStructures()==1){
			
				//do nothing
			
			}else if(ds.getNumberUserLibraryDataStructures()==2
						&& (ds.getUserLibraryDataStructureArray()[0].getLibName().equals(ds.getDestLibName())  
								||  ds.getUserLibraryDataStructureArray()[1].getLibName().equals(ds.getDestLibName()))){
			
				//do nothing
				
			}else if(ds.getNumberUserLibraryDataStructures()>1){
			
				treeModel.insertNodeInto(userLibGroupNode, libNode, 0);
				
				createUserLibraryDataStructureNodes();
			
			}
		
		}
	
	}
	
	/**
	 * Creates the public library data structure nodes.
	 */
	public void createPublicLibraryDataStructureNodes(){

		publicLibGroupNodeArray = new DefaultMutableTreeNode[ds.getNumberPublicLibraryDataStructures()];
		
		int nodeCounter = 0;
		
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
		
			publicLibGroupNodeArray[i] = new DefaultMutableTreeNode(ds.getPublicLibraryDataStructureArray()[i].getLibName());
		
			if(!publicLibGroupNodeArray[i].getUserObject().toString().equals(ds.getSourceLib())){
				
				treeModel.insertNodeInto(publicLibGroupNodeArray[i], publicLibGroupNode, nodeCounter);
				
				nodeCounter++;
				
				treeModel.insertNodeInto(new DefaultMutableTreeNode("Not available."), publicLibGroupNodeArray[i], 0);
				
				//createZNodes(ds.getPublicLibraryDataStructureArray()[i], publicLibGroupNodeArray[i]);
				
			}
			
		}

	}
	
	/**
	 * Creates the shared library data structure nodes.
	 */
	public void createSharedLibraryDataStructureNodes(){

		sharedLibGroupNodeArray = new DefaultMutableTreeNode[ds.getNumberSharedLibraryDataStructures()];
		
		int nodeCounter = 0;
		
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
		
			sharedLibGroupNodeArray[i] = new DefaultMutableTreeNode(ds.getSharedLibraryDataStructureArray()[i].getLibName());
		
			if(!sharedLibGroupNodeArray[i].getUserObject().toString().equals(ds.getSourceLib())){
				
				treeModel.insertNodeInto(sharedLibGroupNodeArray[i], sharedLibGroupNode, nodeCounter);
				
				nodeCounter++;
				
				treeModel.insertNodeInto(new DefaultMutableTreeNode("Not available."), sharedLibGroupNodeArray[i], 0);
				
				//createZNodes(ds.getSharedLibraryDataStructureArray()[i], sharedLibGroupNodeArray[i]);
				
			}
			
		}

	}
	
	/**
	 * Creates the user library data structure nodes.
	 */
	public void createUserLibraryDataStructureNodes(){
	
		userLibGroupNodeArray = new DefaultMutableTreeNode[ds.getNumberUserLibraryDataStructures()];
		
		int nodeCounter = 0;
		
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
		
			userLibGroupNodeArray[i] = new DefaultMutableTreeNode(ds.getUserLibraryDataStructureArray()[i].getLibName());
		
			if(!userLibGroupNodeArray[i].getUserObject().toString().equals(ds.getSourceLib())
					&& !userLibGroupNodeArray[i].getUserObject().toString().equals(ds.getDestLibName())){
		
				treeModel.insertNodeInto(userLibGroupNodeArray[i], userLibGroupNode, nodeCounter);
				
				nodeCounter++;
				
				treeModel.insertNodeInto(new DefaultMutableTreeNode("Not available."), userLibGroupNodeArray[i], 0);
				
				//createZNodes(ds.getUserLibraryDataStructureArray()[i], userLibGroupNodeArray[i]);
			
			}
			
		}
		
	}
	
	/**
	 * Creates the z nodes.
	 *
	 * @param applds the applds
	 * @param parentNode the parent node
	 */
	public void createZNodes(LibraryDataStructure applds, DefaultMutableTreeNode parentNode){
	
		ds.setLibraryName(applds.getLibName());
	
		ds.setCurrentLibraryDataStructure(applds);
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIBRARY ISOTOPES", Cina.rateLibManFrame);
		
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
		
					for(int i=0; i<applds.getZList().length; i++){
					
						DefaultMutableTreeNode tempZNode = new DefaultMutableTreeNode(getElementSymbol(applds.getZList()[i]));
						
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
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIST", Cina.rateLibManFrame);
	
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
	 * Gets the element symbol.
	 *
	 * @param z the z
	 * @return the element symbol
	 */
	public String getElementSymbol(int z){return elementStringArray[z];}
	
	/**
	 * Good rate merge.
	 *
	 * @return true, if successful
	 */
	public boolean goodRateMerge(){
		
		goodRateMerge = false;
		
		final SwingWorker worker = new SwingWorker(){
		
			public Object construct(){
		
				ds.setDeleteSourceLib("NO");
				
				ds.setCHK_TEMP_BEHAVIOR("NO");
				ds.setCHK_OVERFLOW("NO");
				ds.setCHK_INVERSE("NO");
				
				boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY RATE LIBRARY", Cina.rateLibManFrame);
			
				if(!flagArray[0]){
				
					if(!flagArray[2]){
					
						if(!flagArray[1]){
							
							Cina.rateLibManFrame.rateLibManModifyLib2Panel.goodRateMerge = true;
							
						}
					}
				}
				
				ds.setProperties("");
				ds.setMake_inverse("YES");
				
				flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY RATES", Cina.rateLibManFrame);
				
				if(Cina.rateLibManFrame.rateLibManModifyLib2Panel.goodRateMerge){
				
					Cina.rateLibManFrame.rateLibManModifyLib2Panel.goodRateMerge = false;
				
					if(!flagArray[0]){
					
						if(!flagArray[2]){
						
							if(!flagArray[1]){
								
								Cina.rateLibManFrame.rateLibManModifyLib2Panel.goodRateMerge = true;
								
							}
						}
					}
				}
				
				return new Object();
				
			}
		
			public void finished(){
				
				closeDelayDialog();
				
				Cina.rateLibManFrame.modifyLib2GoodRateMerge();
				
				//Cina.setFrameColors(Cina.rateLibManFrame);
				
				Cina.rateLibManFrame.validate();
				
			}
			
		};
		
		worker.start();
		
		return goodRateMerge;
	
	}
	
	/**
	 * Open delay dialog.
	 *
	 * @param frame the frame
	 */
	public void openDelayDialog(Frame frame){
		
		delayDialog = new JDialog(frame, "Please wait.", false);
    	delayDialog.setSize(340, 200);
    	delayDialog.getContentPane().setLayout(new GridBagLayout());
		delayDialog.setLocationRelativeTo(frame);
		
		JTextArea delayTextArea = new JTextArea("", 5, 30);
		delayTextArea.setLineWrap(true);
		delayTextArea.setWrapStyleWord(true);
		delayTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(delayTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		String delayString = "Please be patient while new library is constructed. DO NOT operate the Rate Library Manager at this time!";
		
		delayTextArea.setText(delayString);
		
		delayTextArea.setCaretPosition(0);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		
		delayDialog.getContentPane().add(sp, gbc);
		
		//Cina.setFrameColors(delayDialog);	
		
		delayDialog.validate();
		
		delayDialog.setVisible(true);
		
	}
	
	/**
	 * Close delay dialog.
	 */
	public void closeDelayDialog(){
		
		delayDialog.setVisible(false);
		delayDialog.dispose();
		delayDialog=null;
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		reactionListModel.removeAllElements();
		reactionListModel.addElement(ds.getSourceLib());
		
		String[] rateList = getRateList();
		
		for(int i=0; i<rateList.length; i++){
		
			reactionListModel.addElement(rateList[i]);
		
		}
		
		rateIDVector = ds.getRateIDVector();
		
		decayVector = ds.getDecayVector();
		
		homeLibVector = ds.getHomeLibVector();
		
		listTitleLabel.setText("Rate list for modified base library " + ds.getDestLibName());
	
	}
	
	/**
	 * Gets the rate list.
	 *
	 * @return the rate list
	 */
	public String[] getRateList(){
	
		String[] stringArray = new String[ds.getRateIDVector().size()];
		
		for(int i=0; i<stringArray.length; i++){
		
			String rateID = (String)ds.getRateIDVector().elementAt(i);
			
			StringTokenizer st09 = new StringTokenizer(rateID, "\u0009");
			
			String dummy = st09.nextToken().substring(8);
			
			String library = (String)ds.getHomeLibVector().elementAt(i);
			
			String tempString = st09.nextToken();
			
			String reactionString = tempString.substring(0, tempString.indexOf("\u000b"));
			
			String decay = (String)ds.getDecayVector().elementAt(i);
			
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
	
		ds.setRateIDVector(rateIDVector);
		
		decayVector.trimToSize();
	
		ds.setDecayVector(decayVector);
		
		homeLibVector.trimToSize();
	
		ds.setHomeLibVector(homeLibVector);
		
		ds.setDestLibGroup("USER");
	
	}

}