package org.nucastrodata.rate.rateparam;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.rate.rateviewer.*;

import java.io.*;
import java.util.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.rate.rateparam.RateParamStartHelpFrame;
import org.nucastrodata.rate.rateviewer.RateViewerFrame;


/**
 * The Class RateParamStartFrame.
 */
public class RateParamStartFrame extends JFrame implements ActionListener, TreeExpansionListener, TreeSelectionListener{

	/** The text area. */
	private JTextArea textArea;
	
	/** The rate button. */
	private JButton submitButton, importButton, pasteButton, helpButton, rateButton;
	
	/** The user lib group node. */
	private DefaultMutableTreeNode libNode, publicLibGroupNode, sharedLibGroupNode, userLibGroupNode;
	
	/** The user lib group node array. */
	private DefaultMutableTreeNode[] publicLibGroupNodeArray, sharedLibGroupNodeArray, userLibGroupNodeArray;
	
	/** The lib tree. */
	private JTree libTree;
	
	/** The tree model. */
	private DefaultTreeModel treeModel;
	
	/** The tree selection model. */
	private TreeSelectionModel treeSelectionModel;
	
	/** The applds temp. */
	private LibraryDataStructure appldsTemp;
	
	/** The used tree paths. */
	private Vector usedTreePaths;
	
	/** The filename field. */
	private JTextField filenameField;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/**
	 * Instantiates a new rate param start frame.
	 *
	 * @param ds the ds
	 */
	public RateParamStartFrame(RateParamDataStructure ds){
	
		this.ds = ds;
		
		setSize(594, 623);
		setTitle("Set Starting Parameters");
	
		Container c = getContentPane();
		c.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
	
		JLabel topLabel = new JLabel("<html>With this tool, you can select starting parameters by:<p>"
											+ " - selecting parameters from an existing rate using the file tree (below left).<p>"
											+ " - importing a parameter set by clicking <i>Import</i> (bottom left).<p>"
											+ " - typing the parameters into the <i>Starting Parameters</i> field (below right).<p>"
											+ " - pasting the parameters by clicking <i>Paste from Clipboard</i> (bottom right).<p>" 
											+ "Click <i>Submit Parameters</i> when finished.<p>"
											+ "Click <i>Help on Format</i> for assistance entering or importing parameters.<p>"
											+ "Click <i>Open Rate Viewer</i> to use the Rate Viewer to find a parameter set.</html>");
											
		JLabel paramLabel = new JLabel("Starting Parameters: ");
		JLabel treeLabel = new JLabel("Reaction Rate Tree: ");
	
		filenameField = new JTextField(15);
		filenameField.setEditable(false);
		filenameField.setFont(Fonts.textFont);
	
		submitButton = new JButton("Submit Parameters");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
	
		importButton = new JButton("Import");
		importButton.setFont(Fonts.buttonFont);
		importButton.addActionListener(this);
	
		pasteButton = new JButton("Paste from Clipboard");
		pasteButton.setFont(Fonts.buttonFont);
		pasteButton.addActionListener(this); 
	
		helpButton = new JButton("Help on Format");
		helpButton.setFont(Fonts.buttonFont);
		helpButton.addActionListener(this); 
	
		rateButton = new JButton("Open Rate Viewer");
		rateButton.setFont(Fonts.buttonFont);
		rateButton.addActionListener(this); 
	
		textArea = new JTextArea();
		
		JScrollPane sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 300));
	
		//TREE////////////////////////////////////////////////////////
		usedTreePaths = new Vector();
		libNode = new DefaultMutableTreeNode("Libraries");
		
		treeModel = new DefaultTreeModel(libNode);
		treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		libTree = new JTree(treeModel);
		libTree.setEditable(false);
		libTree.putClientProperty("JTree.linestyle", "Angled");
		libTree.setSelectionModel(treeSelectionModel);
		libTree.addTreeExpansionListener(this);
		libTree.addTreeSelectionListener(this);
		libTree.setShowsRootHandles(true);

		JScrollPane sp1 = new JScrollPane(libTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp1.setPreferredSize(new Dimension(300, 300));
		
		JPanel bottomPanel = new JPanel(new GridLayout(2, 3, 5, 5));
		
		gbc.gridx = 0; 
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = 3;
		c.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = gbc.WEST;
		c.add(treeLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		c.add(paramLabel, gbc);
	
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = gbc.CENTER;
		c.add(sp1, gbc);
	
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		c.add(sp, gbc);
	
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		c.add(bottomPanel, gbc);
	
		bottomPanel.add(importButton);
		bottomPanel.add(filenameField);
		bottomPanel.add(pasteButton);
		bottomPanel.add(rateButton);
		bottomPanel.add(helpButton);
		bottomPanel.add(submitButton);
	
		gbc.gridwidth = 1;
	
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				dispose();
				setVisible(true);
			}
		});
		
		
		
		validate();
	
	}
	
	/**
	 * Sets the parameters.
	 *
	 * @param array the new parameters
	 */
	public void setParameters(double[] array){
	
		String paramString = "";
	
		for(int i=0; i<array.length; i++){
			
			paramString += NumberFormats.getFormattedParameter(array[i]) + "\n";
		
		}
		
		textArea.setText(paramString);
	
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent tse){
	
		if(((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).isLeaf()){
		
			String tempString = new String(getRateIDString(((DefaultMutableTreeNode)((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent().getParent()).getUserObject().toString()
													, ((DefaultMutableTreeNode)((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent()).getUserObject().toString()
													, ((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getUserObject().toString()));
	
			ds.setRates(tempString);
			ds.setProperties("Parameters");
			ds.setStartFromRate(true);
			ds.setStartLib(((DefaultMutableTreeNode)((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getParent().getParent().getParent()).getUserObject().toString());
			ds.setStartRate(((DefaultMutableTreeNode)(libTree.getSelectionPath().getLastPathComponent())).getUserObject().toString());
			
			final SwingWorker worker = new SwingWorker(){
		
				public Object construct(){
			
					Cina.cinaCGIComm.doCGICall("GET RATE INFO", Cina.rateParamFrame.rateParamStartFrame);
					
					return new Object();
				
				}
		
				public void finished(){
			
					String paramString = "";
					
					double[] array = new double[ds.getStartingParameters().length];
					
					for(int i=0; i<ds.getStartingParameters().length; i++){
					
						paramString += NumberFormats.getFormattedParameter(ds.getStartingParameters()[i]) + "\n";
						array[i] = ds.getStartingParameters()[i];
					
					}
					
					ds.setStartingParametersLib(array);
					
					textArea.setText(paramString);
					textArea.setCaretPosition(0);
					
				}
			
			};
			
			worker.start();
			
		
		}
	
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
	
	/**
	 * Compare parameters to lib.
	 *
	 * @return true, if successful
	 */
	public boolean compareParametersToLib(){
	
		boolean isSame = true;
		
		if(ds.getStartingParametersLib()!=null){
		
			if(ds.getStartingParameters().length==ds.getStartingParametersLib().length){
		
				notSame:	
				for(int i=0; i<ds.getStartingParameters().length; i++){
					
					if(ds.getStartingParameters()[i]!=ds.getStartingParametersLib()[i]){
					
						isSame = false;
						break notSame;
					
					}
					
				}
			
			}else{
				
				isSame = false;
				
			}
			
		}else{
			isSame = false;
		}
	
		return isSame;
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==submitButton){
		
			if(!textArea.getText().trim().equals("")){
		
				StringTokenizer st = new StringTokenizer(textArea.getText());
				
				int length = st.countTokens();
				
				if(length==7 || length==14 || length==21 || length==28){
				
					double[] tempArray = new double[length];
					
					try{
					
						for(int i=0; i<length; i++){
						
							tempArray[i] = Double.valueOf(st.nextToken()).doubleValue();	
						
						}
					
						ds.setStartingParameters(tempArray);
						ds.setNumberStartingParams(tempArray.length);
						ds.setStartFromRate(compareParametersToLib());

						Cina.rateParamFrame.rateParamOptionsStartPanel.startingParamsComboBox.setSelectedItem(String.valueOf(Cina.rateParamDataStructure.getNumberStartingParams()));
						Cina.rateParamFrame.rateParamOptionsStartPanel.setCurrentState();
						
						dispose();
						setVisible(false);
					
					}catch(NumberFormatException nfe){
					
						String string = "All parameters must be numeric values.";
						Dialogs.createExceptionDialog(string, this);
					
					}
				
				}else{
					
					String string = "Please enter either 7, 14, 21, or 28 parameters.";
					Dialogs.createExceptionDialog(string, this);
				
				}
			
			}
			
		}else if(ae.getSource()==importButton){
		
			JFileChooser fileDialog = new JFileChooser(Cina.cinaMainDataStructure.getAbsolutePath());

			int returnVal = fileDialog.showOpenDialog(this); 
			
			if(returnVal==JFileChooser.APPROVE_OPTION){
		
				File file = fileDialog.getSelectedFile();
				
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());

				uploadFile(file);
				
				filenameField.setText(file.getName());
				
			}else if(returnVal==JFileChooser.CANCEL_OPTION){
		
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		
			}
		
		}else if(ae.getSource()==pasteButton){
		
			textArea.paste();
			filenameField.setText("Parameters Pasted");
		
		}else if(ae.getSource()==helpButton){
		
			if(Cina.rateParamFrame.rateParamStartHelpFrame==null){	
				Cina.rateParamFrame.rateParamStartHelpFrame = new RateParamStartHelpFrame();
				Cina.rateParamFrame.rateParamStartHelpFrame.setVisible(true);
			}else{
				Cina.rateParamFrame.rateParamStartHelpFrame.setVisible(true);
			}
		
		}else if(ae.getSource()==rateButton){
		
			if(Cina.rateViewerFrame==null){
				Cina.rateViewerFrame = new RateViewerFrame(Cina.rateViewerDataStructure, true);
				
				Cina.rateViewerDataStructure.setLibGroup("PUBLIC");
				boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateViewerFrame);
				Cina.rateViewerDataStructure.setLibGroup("SHARED");
				boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateViewerFrame);
				Cina.rateViewerDataStructure.setLibGroup("USER");
				boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateViewerFrame);

				if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
					Cina.rateViewerFrame.setCurrentState();
			    	Cina.rateViewerDataStructure.setCurrentLibraryDataStructure(Cina.rateViewerDataStructure.getLibraryStructureArray()[Cina.rateViewerDataStructure.getLibIndex()]);	
					Cina.rateViewerDataStructure.setLibraryName(Cina.rateViewerDataStructure.getCurrentLibraryDataStructure().getLibName());

					if(Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY ISOTOPES", Cina.rateViewerFrame)){
						Cina.rateViewerFrame.setSize(825,600);
						Cina.rateViewerFrame.setResizable(true);
	        			Cina.rateViewerFrame.setTitle("Rate Viewer");
	        			Cina.rateViewerFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
	        			
	        			String reactionName = ds.getRateDataStructure().getReactionString();
						if(!ds.getRateDataStructure().getDecay().equals("")){
							reactionName += " [" + ds.getRateDataStructure().getDecay() + "]";
						}
	        			reactionName += " (added rate)";
	        			
	        			Cina.rateViewerFrame.setAddedRate(ds.getRateDataArray()
	        															, ds.getTempDataArray()
	        															, reactionName);
	        															
	        			Cina.rateViewerFrame.setVisible(true);
					}
				}
			}else{
				
				String reactionName = ds.getRateDataStructure().getReactionString();
				if(!ds.getRateDataStructure().getDecay().equals("")){
					reactionName += " [" + ds.getRateDataStructure().getDecay() + "]";
				}
    			reactionName += " (added rate)";
    			
    			Cina.rateViewerFrame.param = true;
    			Cina.rateViewerFrame.setAddedRate(ds.getRateDataArray()
    															, ds.getTempDataArray()
    															, reactionName);
				Cina.rateViewerFrame.setVisible(true);
				
				if(Cina.rateViewerFrame.rateViewerPlotFrame!=null){
				
					Cina.rateViewerFrame.rateViewerPlotFrame.param = true;
					Cina.rateViewerFrame.rateViewerPlotFrame.setFormatPanelState();
					Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.initialize();
					Cina.rateViewerFrame.rateViewerPlotFrame.redrawPlot();
				
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
	 * Upload file.
	 *
	 * @param file the file
	 */
	public void uploadFile(File file){

		int i = (int)file.length();
		
		byte[] stringBuffer = new byte[i];
		
		try{
		
			FileInputStream fileInputStream = new FileInputStream(file);
			
			fileInputStream.read(stringBuffer);
			
			fileInputStream.close();
		
		}catch(Exception e){
			
			e.printStackTrace();
		
		}
		
		String string = new String(stringBuffer);
		
		textArea.setText(string);
	
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
	
		ds.setLibrary(applds.getLibName());
		ds.setCurrentLibraryDataStructure(applds);
	
		boolean check = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY ISOTOPES", this);
		
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
		ds.setLibrary(applds.getLibName());
		ds.setIsotopeString(String.valueOf(zIndex) + "," + String.valueOf(aIndex));
		ds.setTypeDatabase("");
	
		boolean check = Cina.cinaCGIComm.doCGICall("GET RATE LIST", this);

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
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		String paramString = "";
		
		textArea.setText("");
		
		if(ds.getStartingParameters()!=null){
			
			for(int i=0; i<ds.getStartingParameters().length; i++){
			
				paramString += NumberFormats.getFormattedParameter(ds.getStartingParameters()[i]) + "\n";
			
			}
			
			textArea.setText(paramString);
		
		}
		
	}

}