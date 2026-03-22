package org.nucastrodata.element.elementviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.*;

import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.datastructure.util.NucSimSetDataStructure;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.format.StringComparatorIgnoreCase;
import org.nucastrodata.io.CGICom;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class ElementVizSelectSimsPanel extends WizardPanel implements ActionListener, TreeExpansionListener{
	
	private JPanel mainPanel, buttonPanel; 
	private DefaultMutableTreeNode setNode, sharedSetNode, userSetNode;
	private JTree setTree;
	private JScrollPane sp1, sp2;
	private JList simList;
	private JLabel listTitleLabel;
	private JLabel treeTitleLabel;
	private JPanel listPanel;
	private JPanel treePanel;
	private DefaultListModel simListModel; 
	private DefaultTreeModel treeModel;
	private JSplitPane sp;
	private TreeSelectionModel treeSelectionModel;
	private JButton removeSimButton, addSimButton;
	private Vector nucSimVector;
	private ElementVizDataStructure ds;
	private TreeMap<String, DefaultMutableTreeNode> userDirNodeMap, sharedDirNodeMap;
	private ArrayList<String> usedTreePaths = new ArrayList<String>();
	private TreeMap<String, NucSimSetDataStructure> sharedMap;
	private TreeMap<String, NucSimSetDataStructure> userMap;
	
	public ElementVizSelectSimsPanel(ElementVizDataStructure ds){
		
		this.ds = ds;
		
		Cina.elementVizFrame.setCurrentPanelIndex(1);
		
		nucSimVector = new Vector();
		
		addWizardPanel("Element Synthesis Visualizer", "Select Simulations", "1", "2");
		
		treePanel = new JPanel(new BorderLayout());
		
		treeTitleLabel = new JLabel("Available Simulations");
		
		setNode = new DefaultMutableTreeNode("Element Synthesis Simulations");
		
		treeModel = new DefaultTreeModel(setNode);
		
		treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		
		setTree = new JTree(treeModel);
		setTree.setEditable(false);
		setTree.putClientProperty("JTree.linestyle", "Angled");
		setTree.setSelectionModel(treeSelectionModel);
		setTree.setShowsRootHandles(true);
		setTree.addTreeExpansionListener(this);

		sp1 = new JScrollPane(setTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp1.setPreferredSize(new Dimension(300, 300));
		
		treePanel.add(treeTitleLabel, BorderLayout.NORTH);

		treePanel.add(sp1, BorderLayout.CENTER);
		
		simListModel = new DefaultListModel();
		
		simList = new JList(simListModel);
		simList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		sp2 = new JScrollPane(simList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp2.setPreferredSize(new Dimension(300, 300));
		
		listTitleLabel = new JLabel("Selected Simulations");
		
		listPanel = new JPanel(new BorderLayout());
		listPanel.add(listTitleLabel, BorderLayout.NORTH);
		listPanel.add(sp2, BorderLayout.CENTER);
			
		removeSimButton = new JButton("Remove Selected Simulations");
		removeSimButton.setFont(Fonts.buttonFont);
		removeSimButton.addActionListener(this);
		
		addSimButton = new JButton("Add Selected Simulations");
		addSimButton.setFont(Fonts.buttonFont);
		addSimButton.addActionListener(this);
		
		buttonPanel = new JPanel();
		double[] columnButton = {TableLayoutConstants.PREFERRED, 40, TableLayoutConstants.PREFERRED};
		double[] rowButton = {TableLayoutConstants.PREFERRED};
		buttonPanel.setLayout(new TableLayout(columnButton, rowButton));
		buttonPanel.add(removeSimButton, 	  "0, 0, c, c");
		buttonPanel.add(addSimButton,  			"2, 0, c, c");
			
		sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, listPanel);	
		sp.setDividerLocation(250);
		
		mainPanel = new JPanel();
		double[] column = {10, TableLayoutConstants.FILL, 10};
		double[] row = {10, TableLayoutConstants.FILL, 10
				 			, TableLayoutConstants.PREFERRED, 10};
		mainPanel.setLayout(new TableLayout(column, row));
		mainPanel.add(sp, "1, 1, f, f");
		mainPanel.add(buttonPanel, "1, 3, c, c");
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	public void treeExpanded(TreeExpansionEvent tee){
	
		TreePath path = tee.getPath();
		
		if(!usedTreePaths.contains(path.toString())){
		
			if(path.getPathCount()==3){
			
				usedTreePaths.add(path.toString());
				
				DefaultMutableTreeNode selectedGroupNode = (DefaultMutableTreeNode)tee.getPath().getPathComponent(1);
				String selectedGroup = selectedGroupNode.toString();
				
				DefaultMutableTreeNode selectedSimNode = (DefaultMutableTreeNode)tee.getPath().getPathComponent(2);
				String selectedSim = selectedSimNode.toString();
			
				DefaultMutableTreeNode dummyNode = (DefaultMutableTreeNode) selectedSimNode.getChildAt(0);
				
				if(dummyNode.toString().equals("DUMMY")){
					String simPath = selectedGroup.toUpperCase() + "/" + selectedSim;
					replaceDummyNode(simPath, selectedGroup, selectedSim, selectedSimNode, dummyNode);				
					setTree.expandPath(path);
				}
				
			}else if(path.getPathCount()==4){
			
				usedTreePaths.add(path.toString());
				
				DefaultMutableTreeNode selectedGroupNode = (DefaultMutableTreeNode)tee.getPath().getPathComponent(1);
				String selectedGroup = selectedGroupNode.toString();
				
				DefaultMutableTreeNode selectedSimNode = (DefaultMutableTreeNode)tee.getPath().getPathComponent(2);
				String selectedSim = selectedSimNode.toString();
				
				DefaultMutableTreeNode selectedLibNode = (DefaultMutableTreeNode)tee.getPath().getPathComponent(3);
				String selectedLib = selectedLibNode.toString();
				
				DefaultMutableTreeNode dummyNode = (DefaultMutableTreeNode) selectedLibNode.getChildAt(0);
				
				if(dummyNode.toString().equals("DUMMY")){
					String simPath = selectedGroup.toUpperCase() + "/" + selectedSim + "_dir_" + selectedLib;
					replaceDummyNode(simPath, selectedGroup, selectedSim + "_dir_" + selectedLib, selectedLibNode, dummyNode);				
					setTree.expandPath(path);
				}
			}
		}
	}
	
	public void treeCollapsed(TreeExpansionEvent tee){}
	
	public void replaceDummyNode(String simPath, String selectedGroup, String selectedSim, DefaultMutableTreeNode parentNode, DefaultMutableTreeNode dummyNode){
	
		treeModel.removeNodeFromParent(dummyNode);
		ds.setPaths(simPath);
		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.GET_ELEMENT_SYNTHESIS_ZONES
												, Cina.elementVizFrame);
												
		NucSimSetDataStructure nssds = null;
		if(selectedGroup.equals("User")){
			nssds = userMap.get(selectedSim);
		}else if(selectedGroup.equals("Shared")){
			nssds = sharedMap.get(selectedSim);
		}
		
		NucSimDataStructure[] nsdsArray = nssds.getNucSimDataStructureArray();
		for(int i=0; i<nsdsArray.length; i++){
			String zoneString = "zone_";
			DefaultMutableTreeNode zoneNode = new DefaultMutableTreeNode(zoneString + nsdsArray[i].getZone());
			treeModel.insertNodeInto(zoneNode, parentNode, parentNode.getChildCount());
		}
	
	}
	
	public void replaceDummyNodes(ArrayList<String> simPathList, 
									String selectedGroup, 
									ArrayList<String> selectedSimList, 
									ArrayList<DefaultMutableTreeNode> parentNodeList){
		
		
		
		ds.setPaths(simPathList.get(0) + "," + simPathList.get(1));
		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.GET_ELEMENT_SYNTHESIS_ZONES
												, Cina.elementVizFrame);
												
		String testSim1 = selectedSimList.get(0);
		String testSim2 = selectedSimList.get(1);
		NucSimSetDataStructure nssds1 = null;
		NucSimSetDataStructure nssds2 = null;
		if(selectedGroup.equals("User")){
			nssds1 = userMap.get(testSim1);
			nssds2 = userMap.get(testSim2);
		}else if(selectedGroup.equals("Shared")){
			nssds1 = sharedMap.get(testSim1);
			nssds2 = sharedMap.get(testSim2);
		}
		
		boolean isRepeated = nssds1.getNucSimDataStructureArray().length == nssds2.getNucSimDataStructureArray().length
								&& nssds1.getNucSimDataStructureArray()[0].getZone() == nssds2.getNucSimDataStructureArray()[0].getZone();
								
		boolean isSingleLooping = nssds1.getNucSimDataStructureArray().length == nssds2.getNucSimDataStructureArray().length
									&& nssds1.getNucSimDataStructureArray()[0].getZone() == (nssds2.getNucSimDataStructureArray()[0].getZone() - 1);
		
		if(!isRepeated && !isSingleLooping){
			String simPaths = "";
			for(String simPath: simPathList){
				simPaths += simPath + ",";
			}
			simPaths = simPaths.substring(0, simPaths.length()-1);
			ds.setPaths(simPaths);
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
													, ds
													, CGICom.GET_ELEMENT_SYNTHESIS_ZONES
													, Cina.elementVizFrame);
		}
											
		for(int i=0; i<simPathList.size(); i++){
		
			DefaultMutableTreeNode selectedLibNode = parentNodeList.get(i);
		
			DefaultMutableTreeNode dummyNode = (DefaultMutableTreeNode) selectedLibNode.getChildAt(0);
			treeModel.removeNodeFromParent(dummyNode);
			
			String selectedSim = null;
			if(isRepeated || isSingleLooping){
				selectedSim = selectedSimList.get(0);
			}else{
				selectedSim = selectedSimList.get(i);
			}
			
			NucSimSetDataStructure nssds = null;
			if(selectedGroup.equals("User")){
				nssds = userMap.get(selectedSim);
			}else if(selectedGroup.equals("Shared")){
				nssds = sharedMap.get(selectedSim);
			}
			
			NucSimDataStructure[] nsdsArray = nssds.getNucSimDataStructureArray();
			if(isSingleLooping){
				String zoneString = "zone_";
				DefaultMutableTreeNode zoneNode = new DefaultMutableTreeNode(zoneString + (i+1));
				treeModel.insertNodeInto(zoneNode, selectedLibNode, selectedLibNode.getChildCount());
			}else{
				for(int j=0; j<nsdsArray.length; j++){
					String zoneString = "zone_";
					DefaultMutableTreeNode zoneNode = new DefaultMutableTreeNode(zoneString + nsdsArray[j].getZone());
					treeModel.insertNodeInto(zoneNode, selectedLibNode, selectedLibNode.getChildCount());
				}
			}
		}
	
	}
	
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==removeSimButton){
		
			if(simList.getSelectedValues()!=null){
				
				Object[] values = simList.getSelectedValues();
				
				for(int i=0; i<values.length; i++){
				
					nucSimVector.removeElement(values[i].toString());
					simListModel.removeElement(values[i].toString());
				
				}

			}

		}else if(ae.getSource()==addSimButton){
			
			if(setTree.getSelectionPaths()!=null && setTree.getSelectionPaths().length > 0){
			
				for(int i=0; i<setTree.getSelectionPaths().length; i++){
				
					TreePath path = setTree.getSelectionPaths()[i];
					
					if(((DefaultMutableTreeNode)(path.getLastPathComponent()))!=null &&
							((DefaultMutableTreeNode)(path.getLastPathComponent())).isLeaf()){
						
						if(path.getPathCount()==4){

							DefaultMutableTreeNode selectedGroupNode = (DefaultMutableTreeNode)path.getPathComponent(1);
							String selectedGroup = selectedGroupNode.toString();
							
							DefaultMutableTreeNode selectedSimNode = (DefaultMutableTreeNode)path.getPathComponent(2);
							String selectedSim = selectedSimNode.toString();
						
							DefaultMutableTreeNode dummyNode = (DefaultMutableTreeNode) selectedSimNode.getChildAt(0);
						
							if(dummyNode.toString().equals("DUMMY")){
							
								String simPath = selectedGroup.toUpperCase() + "/" + selectedSim;
								replaceDummyNode(simPath, selectedGroup, selectedSim, selectedSimNode, dummyNode);
							
							}
						
							String newListSim = ((DefaultMutableTreeNode)(path.getLastPathComponent())).getParent().toString()
													+ " (" + path.getLastPathComponent().toString() + ")";
		
							if(!simListModel.contains(newListSim)){
								simListModel.addElement(newListSim);
								nucSimVector.addElement(newListSim);
							}
						
						}else if(path.getPathCount()==5){
						

							DefaultMutableTreeNode selectedGroupNode = (DefaultMutableTreeNode)path.getPathComponent(1);
							String selectedGroup = selectedGroupNode.toString();
							
							DefaultMutableTreeNode selectedSimNode = (DefaultMutableTreeNode)path.getPathComponent(2);
							String selectedSim = selectedSimNode.toString();
							
							DefaultMutableTreeNode selectedLibNode = (DefaultMutableTreeNode)path.getPathComponent(3);
							String selectedLib = selectedLibNode.toString();
							
							DefaultMutableTreeNode dummyNode = (DefaultMutableTreeNode) selectedLibNode.getChildAt(0);
						
							if(path.getLastPathComponent().toString().equals("DUMMY")){
								
								String simPath = selectedGroup.toUpperCase() + "/" + selectedSim + "_dir_" + selectedLib;
								replaceDummyNode(simPath, selectedGroup, selectedSim + "_dir_" + selectedLib, selectedLibNode, dummyNode);	
							}
						
							String newListSim = ((DefaultMutableTreeNode)(path.getLastPathComponent())).getParent().getParent().toString() 
									+ "_dir_"
									+ ((DefaultMutableTreeNode)(path.getLastPathComponent())).getParent().toString() 
									+ " (" + path.getLastPathComponent().toString() + ")";
	
							if(!simListModel.contains(newListSim)){
								simListModel.addElement(newListSim);
								nucSimVector.addElement(newListSim);
							}
							
						}
											
					}else if(path.getPathCount()==3){
					
						if(isLibDir(path)){
							
							DefaultMutableTreeNode selectedGroupNode = (DefaultMutableTreeNode)path.getPathComponent(1);
							String selectedGroup = selectedGroupNode.toString();
							
							DefaultMutableTreeNode selectedSimNode = (DefaultMutableTreeNode)path.getPathComponent(2);
							String selectedSim = selectedSimNode.toString();

							ArrayList<String> simPathList = new ArrayList<String>();
							ArrayList<String> selectedSimList = new ArrayList<String>();
							ArrayList<DefaultMutableTreeNode> parentNodeList = new ArrayList<DefaultMutableTreeNode>();

							for(int j=0; j<selectedSimNode.getChildCount(); j++){
							
								DefaultMutableTreeNode selectedLibNode = (DefaultMutableTreeNode)selectedSimNode.getChildAt(j);
								String selectedLib = selectedLibNode.toString();
								
								DefaultMutableTreeNode dummyNode = (DefaultMutableTreeNode) selectedLibNode.getChildAt(0);
								
								if(selectedLibNode.getChildAt(0).toString().equals("DUMMY")){
									
									String simPath = selectedGroup.toUpperCase() + "/" + selectedSim + "_dir_" + selectedLib;
									simPathList.add(simPath);
									selectedSimList.add(selectedSim + "_dir_" + selectedLib);
									parentNodeList.add(selectedLibNode);

								}
							}
							
							replaceDummyNodes(simPathList, selectedGroup, selectedSimList, parentNodeList);	
						
							Enumeration e = ((DefaultMutableTreeNode)(path.getLastPathComponent())).children();
							
							while(e.hasMoreElements()){
							
								DefaultMutableTreeNode libNode = (DefaultMutableTreeNode) e.nextElement();
								String simName = path.getLastPathComponent() + "_dir_" + libNode.getUserObject().toString();
								
								Enumeration eZone = libNode.children();
								while(eZone.hasMoreElements()){
									
									DefaultMutableTreeNode zoneNode = (DefaultMutableTreeNode) eZone.nextElement();
									String newListSim = simName + " (" + zoneNode.getUserObject().toString() + ")";
									
									if(!simListModel.contains(newListSim)){
										simListModel.addElement(newListSim);
										nucSimVector.addElement(newListSim);
									}
									
								}
										
							}
						
						}else{
						
							DefaultMutableTreeNode selectedGroupNode = (DefaultMutableTreeNode)path.getPathComponent(1);
							String selectedGroup = selectedGroupNode.toString();
							
							DefaultMutableTreeNode selectedSimNode = (DefaultMutableTreeNode)path.getPathComponent(2);
							String selectedSim = selectedSimNode.toString();
						
							DefaultMutableTreeNode dummyNode = (DefaultMutableTreeNode) selectedSimNode.getChildAt(0);
						
							if(dummyNode.toString().equals("DUMMY")){
							
								String simPath = selectedGroup.toUpperCase() + "/" + selectedSim;
								replaceDummyNode(simPath, selectedGroup, selectedSim, selectedSimNode, dummyNode);
							
							}
						
							Enumeration e = ((DefaultMutableTreeNode)(path.getLastPathComponent())).children();
							
							while(e.hasMoreElements()){
							
								DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
									
								String newListSim = path.getLastPathComponent().toString()
													+ " (" + node.getUserObject().toString() + ")";
	
								if(!simListModel.contains(newListSim)){
									simListModel.addElement(newListSim);
									nucSimVector.addElement(newListSim);
								}
							}
							
						}
					
					}else if(path.getPathCount()==4){

						DefaultMutableTreeNode selectedGroupNode = (DefaultMutableTreeNode)path.getPathComponent(1);
						String selectedGroup = selectedGroupNode.toString();
						
						DefaultMutableTreeNode selectedSimNode = (DefaultMutableTreeNode)path.getPathComponent(2);
						String selectedSim = selectedSimNode.toString();
						
						DefaultMutableTreeNode selectedLibNode = (DefaultMutableTreeNode)path.getPathComponent(3);
						String selectedLib = selectedLibNode.toString();
						
						DefaultMutableTreeNode dummyNode = (DefaultMutableTreeNode) selectedLibNode.getChildAt(0);
						
						if(dummyNode.toString().equals("DUMMY")){
							
							String simPath = selectedGroup.toUpperCase() + "/" + selectedSim + "_dir_" + selectedLib;
							replaceDummyNode(simPath, selectedGroup, selectedSim + "_dir_" + selectedLib, selectedLibNode, dummyNode);	
							
						}
						
						Enumeration e = ((DefaultMutableTreeNode)(path.getLastPathComponent())).children();
						
						while(e.hasMoreElements()){
						
							DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
							
							String newListSim = ((DefaultMutableTreeNode)(path.getLastPathComponent())).getParent().toString() 
									+ "_dir_"
									+ path.getLastPathComponent().toString()
									+ " (" + node.getUserObject().toString() + ")";
	
							if(!simListModel.contains(newListSim)){
							
								simListModel.addElement(newListSim);
								nucSimVector.addElement(newListSim);
							}
							
						}
					
					}
				
				}
			
			}else{
			
				String string = "Please select at least one simulation from the <i>Element Synthesis Simulations</i> tree on the left.";
				AttentionDialog.createDialog(Cina.elementVizFrame, string);
			
			}
		
		}
	
	}
		
	private boolean isLibDir(TreePath path){
		Enumeration e = ((DefaultMutableTreeNode)(path.getLastPathComponent())).children();
		while(e.hasMoreElements()){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
			if(node.isLeaf()){
				return false;
			}
		}
		return true;
	}
		
	/**
	 * Creates the set nodes.
	 */
	public void createSetNodes(){
	
		sharedSetNode = new DefaultMutableTreeNode("Shared");
		userSetNode = new DefaultMutableTreeNode("User");

		if(ds.getNumberSharedNucSimSetDataStructures()>0){
				
			treeModel.insertNodeInto(sharedSetNode, setNode, 0);
			createSharedNucSimSetDataStructureNodes();
			
		}
		
		if(ds.getNumberUserNucSimSetDataStructures()>0){

			treeModel.insertNodeInto(userSetNode, setNode, 0);
			createUserNucSimSetDataStructureNodes();
		
		}
	
	}
	
	public void createSharedNucSimSetDataStructureNodes(){

		sharedMap = new TreeMap<String, NucSimSetDataStructure>();
		ArrayList<String> array = new ArrayList<String>();
		
		for(int i=0; i<ds.getNumberSharedNucSimSetDataStructures(); i++){
			NucSimSetDataStructure nssds = ds.getSharedNucSimSetDataStructureArray()[i];
			String name = nssds.getPath();
			array.add(name.substring(name.lastIndexOf("/")+1));
			sharedMap.put(name.substring(name.lastIndexOf("/")+1), nssds);
		}
		
		Collections.sort(array, new StringComparatorIgnoreCase());
		
		sharedDirNodeMap = new TreeMap<String, DefaultMutableTreeNode>();
		
		for(String name: array){
		
			if(name.indexOf("_dir_")!=-1){
			
				String dirName = name.substring(0,  name.lastIndexOf("_dir_"));
				String subName = name.substring(name.lastIndexOf("_dir_") + 5);
			
				DefaultMutableTreeNode mainNode = null;
			
				if(!sharedDirNodeMap.containsKey(dirName)){
					mainNode = new DefaultMutableTreeNode(dirName);
					treeModel.insertNodeInto(mainNode, sharedSetNode, sharedSetNode.getChildCount());
					sharedDirNodeMap.put(dirName, mainNode);
				}
				mainNode = sharedDirNodeMap.get(dirName);
				
				DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(subName);
				treeModel.insertNodeInto(subNode, mainNode, mainNode.getChildCount());
				treeModel.insertNodeInto(new DefaultMutableTreeNode("DUMMY"), subNode, 0);
				
			}else{
			
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
				treeModel.insertNodeInto(node, sharedSetNode, sharedSetNode.getChildCount());
				treeModel.insertNodeInto(new DefaultMutableTreeNode("DUMMY"), node, 0);
				
			}

		}

	}
	
	public void createUserNucSimSetDataStructureNodes(){
	
		userMap = new TreeMap<String, NucSimSetDataStructure>();
		ArrayList<String> array = new ArrayList<String>();
		
		for(int i=0; i<ds.getNumberUserNucSimSetDataStructures(); i++){
			NucSimSetDataStructure nssds = ds.getUserNucSimSetDataStructureArray()[i];
			String name = nssds.getPath();
			array.add(name.substring(name.lastIndexOf("/")+1));
			userMap.put(name.substring(name.lastIndexOf("/")+1), nssds);
		}
		
		Collections.sort(array, new StringComparatorIgnoreCase());
		
		userDirNodeMap = new TreeMap<String, DefaultMutableTreeNode>();
		
		for(String name: array){
		
			if(name.indexOf("_dir_")!=-1){
			
				String dirName = name.substring(0,  name.lastIndexOf("_dir_"));
				String subName = name.substring(name.lastIndexOf("_dir_") + 5);
			
				DefaultMutableTreeNode mainNode = null;
			
				if(!userDirNodeMap.containsKey(dirName)){
					mainNode = new DefaultMutableTreeNode(dirName);
					treeModel.insertNodeInto(mainNode, userSetNode, userSetNode.getChildCount());
					userDirNodeMap.put(dirName, mainNode);
				}
				mainNode = userDirNodeMap.get(dirName);
				
				DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(subName);
				treeModel.insertNodeInto(subNode, mainNode, mainNode.getChildCount());
				treeModel.insertNodeInto(new DefaultMutableTreeNode("DUMMY"), subNode, 0);
				
			}else{
			
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
				treeModel.insertNodeInto(node, userSetNode, userSetNode.getChildCount());
				treeModel.insertNodeInto(new DefaultMutableTreeNode("DUMMY"), node, 0);
		
			}

		}
		
	}
	
	public boolean emptyNucSims(){
	
		boolean emptyNucSims = true;
		
		if(ds.getNucSimVector().size()!=0){emptyNucSims = false;}
		
		return emptyNucSims;
	
	}
	
	public void setCurrentState(){
		
		nucSimVector = ds.getNucSimVector();
		
		simListModel.removeAllElements();
		
		for(int i=0; i<nucSimVector.size(); i++){
		
			simListModel.addElement(nucSimVector.elementAt(i).toString());
		
		}	
	
	}
	
	public void getCurrentState(){
	
		nucSimVector.trimToSize();
	
		ds.setNucSimVector(nucSimVector);

	}
	
	public void createElementVizNucSimSetDataStructures(){
		
		Vector setPathVector = new Vector();
		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
		
			String currentPath = ds.getNucSimDataStructureArray()[i].getPath();
		
			if(!setPathVector.contains(currentPath)){
			
				setPathVector.addElement(currentPath);
					
			}
		
		}
		
		setPathVector.trimToSize();
		
		NucSimSetDataStructure[] appnssdsa = new NucSimSetDataStructure[setPathVector.size()];
		NucSimSetDataStructure[] appnssdsa2 = new NucSimSetDataStructure[setPathVector.size()];
		
		for(int i=0; i<setPathVector.size(); i++){
		
			appnssdsa[i] = new NucSimSetDataStructure();
			appnssdsa[i].setPath(setPathVector.elementAt(i).toString());
			
			appnssdsa2[i] = new NucSimSetDataStructure();
			appnssdsa2[i].setPath(setPathVector.elementAt(i).toString());
		
		}
		
		ds.setNumberNucSimSetDataStructures(appnssdsa.length);
		ds.setFinalNucSimSetDataStructureArray(appnssdsa);
		ds.setZoneNucSimSetDataStructureArray(appnssdsa2);
	
	}
	
	public void createElementVizNucSimDataStructures(){
		
		NucSimDataStructure[] appnsdsArray = new NucSimDataStructure[ds.getNucSimVector().size()];

		
		for(int i=0; i<ds.getNucSimVector().size(); i++){
		
			appnsdsArray[i] = new NucSimDataStructure();
			appnsdsArray[i].setNucSimName(ds.getNucSimVector().elementAt(i).toString());
			appnsdsArray[i].setZone(getZone(appnsdsArray[i].getNucSimName()));
			appnsdsArray[i].setPath(getPath(appnsdsArray[i].getNucSimName()));
		
		}
		
		ds.setNumberNucSimDataStructures(ds.getNucSimVector().size());
		ds.setNucSimDataStructureArray(appnsdsArray);
		ds.setNucSimDataStructureArrayAnimator(appnsdsArray);
		ds.setNucSimDataStructureArrayIntFlux(appnsdsArray);
		ds.setNucSimDataStructureArrayScale(appnsdsArray);
		ds.setNucSimDataStructureArrayFinalAbund(appnsdsArray);
		
	}
	
	public int getZone(String string){
		return Integer.valueOf(string.substring(string.lastIndexOf("zone_")+5, string.lastIndexOf(")"))).intValue();
	}
	
	public String getPath(String string){
	
		String path = "";
		String group = "";
		String set = string.substring(0, string.lastIndexOf("_") - 5).trim();
		
		for(int i=0; i<ds.getNumberSharedNucSimSetDataStructures(); i++){
		
			String tempSet = ds.getSharedNucSimSetDataStructureArray()[i].getPath();
		
			if(tempSet.substring(tempSet.lastIndexOf("/")+1).equals(set)){
				
				group = "SHARED/";
				path = group + set;
				
			}
		
		}
		
		for(int i=0; i<ds.getNumberUserNucSimSetDataStructures(); i++){
		
			String tempSet = ds.getUserNucSimSetDataStructureArray()[i].getPath();
	
			if(tempSet.substring(tempSet.lastIndexOf("/")+1).equals(set)){
				
				group = "USER/";
				path = group + set;
				
			}
		
		}

		return path;
	
	}
	
}