package org.nucastrodata.data.dataviewer;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import javax.swing.text.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataViewerDataStructure;
import org.nucastrodata.datastructure.util.NucDataDataStructure;
import org.nucastrodata.datastructure.util.NucDataSetDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Corner;
import org.nucastrodata.Fonts;
import org.nucastrodata.HelpFrame;
import org.nucastrodata.IsotopeRuler;
import org.nucastrodata.data.dataviewer.DataViewerIsotopePadPanel;
import org.nucastrodata.data.dataviewer.DataViewerPlotFrame;


//This class is the main interface class for the NucData Viewer
/**
 * The Class DataViewerFrame.
 */
public class DataViewerFrame extends JFrame implements ItemListener
													, ActionListener
													, ChangeListener
													, WindowListener{

	//Declare isotope pad panel and the plotting interface frame
    /** The data viewer isotope pad panel. */
	static DataViewerIsotopePadPanel dataViewerIsotopePadPanel;
    
    /** The data viewer plot frame. */
    static DataViewerPlotFrame dataViewerPlotFrame; 
    
    /** The data viewer help frame. */
    public static HelpFrame dataViewerHelpFrame;
    
    //Declare panel
    /** The cbox panel. */
    JPanel cboxPanel = new JPanel();

	//Declare library combo box
	/** The nuc data set combo box. */
	JComboBox nucDataSetComboBox;
	
	//Declare String array for checkbox labels
	/** The check box string array. */
	String[] checkBoxStringArray = {"a-->b"
										, "a-->b+c"
										, "a-->b+c+d"
										, "a+b-->c"
										, "a+b-->c+d"
										, "a+b-->c+d+e"
										, "a+b-->c+d+e+f"
										, "a+b+c-->d(+e)"
										, "All Reaction Types"};
	
	//Declare array for reaction type check boxes
	/** The check box array. */
	JCheckBox[] checkBoxArray = new JCheckBox[9];

	//Declare scrollpane to hold isotope pad
    /** The sp. */
	JScrollPane sp;
  
  	//Declare buttons
    /** The help button. */
	  JButton dataButton, clearButton, helpButton;
    
    //Declare panels
    /** The bot panel c. */
    JPanel p, goPanel, botPanel, botPanelA, botPanelB, tPanel, botPanelC;
    
    //Declare fields
    /** The element field. */
    JTextField zmaxField, nmaxField, aField, zField, elementField;
    
    //Declare sliders
    /** The nmax slider. */
    JSlider zmaxSlider, nmaxSlider;
    
    //Declare frame's container
    /** The c. */
    Container c;
    
    //Declare button group for exit NucData Viewer dialog
    /** The choice button group. */
    ButtonGroup choiceButtonGroup;
	
	//Declare radio buttons for exit NucData Viewer dialog
	/** The save and close radio button. */
	JRadioButton saveAndCloseRadioButton;
	
	/** The close radio button. */
	JRadioButton closeRadioButton;
	
	/** The do not close radio button. */
	JRadioButton doNotCloseRadioButton;
	
	//Declare button for exit NucData Viewer dialog
	/** The submit button. */
	JButton submitButton;
    
    //Declare gbc
    /** The gbc. */
    GridBagConstraints gbc;
    
    //Declare dialogs
    /** The save dialog. */
    JDialog saveDialog;
    
    /** The N ruler. */
    IsotopeRuler ZRuler, NRuler;
    
    /** The ds. */
    private DataViewerDataStructure ds;
    
    //Constructor
    /**
     * Instantiates a new data viewer frame.
     *
     * @param ds the ds
     */
    public DataViewerFrame(DataViewerDataStructure ds){
    	
    	this.ds = ds;
    	
    	//Get the frame's content assigned to container
    	c = getContentPane();
    	
    	//Set container layout
        c.setLayout(new BorderLayout());

		//Instantiate gbc
		gbc = new GridBagConstraints();

		//Set gbc
        GridBagConstraints gs = new GridBagConstraints();
        gs.weightx = 100;
        gs.weighty = 100;
        gs.gridx = 0;
        gs.gridy = 0;
        gs.ipadx = 0;
        gs.ipady = 0;
        gs.fill = GridBagConstraints.HORIZONTAL;
        gs.anchor = GridBagConstraints.NORTH;
        gs.insets = new Insets(10,6,0,0);
		
		//Create panel
        p = new JPanel();
        p.setLayout(new GridBagLayout());

		//Create panel
        tPanel = new JPanel();
        tPanel.setLayout(new GridLayout(1,1,5,5));
        tPanel.setFont(Fonts.titleFont);
        JLabel reacLabel = new JLabel("Reaction Types",JLabel.LEFT);
        tPanel.add(reacLabel);

		//Create library combo box and set properties
		nucDataSetComboBox = new JComboBox();
		nucDataSetComboBox.setFont(Fonts.textFont);
		
		//Set check box panel layout
        cboxPanel.setLayout(new GridLayout(12,1,5,5));
        
        //Create label
        JLabel label1 = new JLabel("<html>Choose Nuclear<p>Data Set :</html>");
        
        //Add labels and combo box to panel
        cboxPanel.add(label1);
        cboxPanel.add(nucDataSetComboBox);
        cboxPanel.add(tPanel);
        
        //Loop over checkbox array
        for(int i=0; i<9; i++){
        
        	//Create check boxes for reaction ytpe and assign properties
        	checkBoxArray[i] = new JCheckBox(checkBoxStringArray[i]);
        	checkBoxArray[i].addItemListener(this);
        	checkBoxArray[i].setFont(Fonts.textFont);
        	cboxPanel.add(checkBoxArray[i]);
        
        }

		//Add check box panel to panel
        p.add(cboxPanel, gs);

		//Create panel
        goPanel = new JPanel();
        goPanel.setLayout(new GridBagLayout());
        gs.insets = new Insets(3,3,3,3);
        gs.ipady = 2;

		//Create plotting rate buttonand clear selections button
        dataButton = new JButton("<html>Nuclear Data<p>Plotting Interface</html>");
        dataButton.setFont(Fonts.buttonFont);
        dataButton.addActionListener(this);
        dataButton.setEnabled(false);

		clearButton = new JButton("<html>Clear All<p>Selections</html>");
        clearButton.setFont(Fonts.buttonFont);
        clearButton.addActionListener(this);

		helpButton = new JButton("<html>Help on<p>This Interface</html>");
        helpButton.setFont(Fonts.buttonFont);
        helpButton.addActionListener(this);

		//Set gbc and add buttons
        gs.gridy = 1;
        goPanel.add(dataButton, gs);
        
        gs.gridy = 2;
        goPanel.add(clearButton, gs);
        
        gs.gridy = 3;
        goPanel.add(helpButton, gs);
        
        //Add the goPanel to the JPanel p
        gs.anchor = GridBagConstraints.NORTH;
        gs.gridy = 1;
        p.add(goPanel, gs);

		//Create panel
        botPanel = new JPanel();
        botPanel.setLayout(new GridBagLayout());
        botPanel.setFont(Fonts.buttonFont);

		//Set gbc
        gs.weightx = 100;
        gs.weighty = 100;
        gs.gridx = 0;
        gs.gridy = 0;
        gs.ipadx = 0;
        gs.ipady = 0;
        gs.fill = GridBagConstraints.NONE;
        gs.anchor = GridBagConstraints.WEST;
        gs.insets = new Insets(4,10,4,0);

		//Create panels and labels for bottom panel
        botPanelB = new JPanel();
        JLabel zmaxLabel = new JLabel("Zmax",JLabel.LEFT);
        zmaxLabel.setFont(Fonts.textFont);
        botPanelB.add(zmaxLabel);

		JLabel elementLabel = new JLabel("Element: ");
		elementLabel.setFont(Fonts.textFont);

		JLabel zLabel = new JLabel("Z: ");
		zLabel.setFont(Fonts.textFont);
		
		JLabel aLabel = new JLabel("A: ");
		aLabel.setFont(Fonts.textFont);

		//Create fields for bottom panel
		elementField = new JTextField();
		elementField.setColumns(3);
		elementField.setEditable(false);
		
		zField = new JTextField();
		zField.setColumns(3);
		zField.setEditable(false);
		
		aField = new JTextField();
		aField.setColumns(3);
		aField.setEditable(false);

        zmaxField = new JTextField();
        zmaxField.setColumns(3);
        zmaxField.setFont(Fonts.textFont);
        
        //Create sliders for Z and N
        zmaxSlider = new JSlider(JSlider.HORIZONTAL, 2, 85, DataViewerIsotopePadPanel.zmax);
		zmaxSlider.addChangeListener(this);
		zmaxField.setText(Integer.toString(DataViewerIsotopePadPanel.zmax));

		zmaxField.setEditable(false);

		botPanelB.add(zmaxSlider);
		botPanelB.add(zmaxField);

        gs.insets = new Insets(4,0,4,0);
        gs.gridx = 1;
        botPanel.add(botPanelB, gs);

        botPanelC = new JPanel();
        JLabel nmaxLabel = new JLabel("Nmax",JLabel.LEFT);
        nmaxLabel.setFont(Fonts.textFont);
        botPanelC.add(nmaxLabel);

        nmaxField = new JTextField();
        nmaxField.setColumns(3);
        nmaxField.setFont(Fonts.textFont);
        nmaxField.setText(Integer.toString(DataViewerIsotopePadPanel.nmax));
        
        nmaxField.setEditable(false);
        
        nmaxSlider = new JSlider(JSlider.HORIZONTAL, 3, 193, DataViewerIsotopePadPanel.nmax);
        nmaxSlider.addChangeListener(this);
        
        botPanelC.add(nmaxSlider);
        
        botPanelC.add(nmaxField);
        
        //Put together bottom panel
        gs.gridx = 2;
        botPanel.add(botPanelC, gs);
        
        gs.gridx = 3;
        botPanel.add(elementLabel, gs);
        
        gs.gridx = 4;
        botPanel.add(elementField, gs);
        
        gs.gridx = 5;
        botPanel.add(zLabel, gs);
        
        gs.gridx = 6;
        botPanel.add(zField, gs);
        
        gs.gridx = 7;
        botPanel.add(aLabel, gs);
        
        gs.gridx = 8;
        botPanel.add(aField, gs);
        
        gs.gridx = 4;
        gs.insets = new Insets(4,0,4,120);
        gs.gridx = 5;

		//Instantiate isotope pad
        dataViewerIsotopePadPanel = new DataViewerIsotopePadPanel(ds);
        
        //Set properties of isotopte pad
        dataViewerIsotopePadPanel.width = (int)(dataViewerIsotopePadPanel.boxWidth*(dataViewerIsotopePadPanel.nmax+1));
        dataViewerIsotopePadPanel.height = (int)(dataViewerIsotopePadPanel.boxHeight*(dataViewerIsotopePadPanel.zmax+1));
        
        dataViewerIsotopePadPanel.setSize(dataViewerIsotopePadPanel.width+2*dataViewerIsotopePadPanel.xoffset,dataViewerIsotopePadPanel.height+2*dataViewerIsotopePadPanel.yoffset);
        
        dataViewerIsotopePadPanel.xmax = (int)(dataViewerIsotopePadPanel.xoffset + dataViewerIsotopePadPanel.width);
        dataViewerIsotopePadPanel.ymax = (int)(dataViewerIsotopePadPanel.yoffset + dataViewerIsotopePadPanel.height);

		dataViewerIsotopePadPanel.setPreferredSize(dataViewerIsotopePadPanel.getSize());
		
		//Add to scroll pane
        sp = new JScrollPane(dataViewerIsotopePadPanel);
		
		JViewport vp = new JViewport();
		
		vp.setView(dataViewerIsotopePadPanel);
		
		sp.setViewport(vp);
		
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)dataViewerIsotopePadPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)dataViewerIsotopePadPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(dataViewerIsotopePadPanel.zmax
								, dataViewerIsotopePadPanel.nmax
								, dataViewerIsotopePadPanel.mouseX
								, dataViewerIsotopePadPanel.mouseY
								, dataViewerIsotopePadPanel.xoffset
								, dataViewerIsotopePadPanel.yoffset
								, dataViewerIsotopePadPanel.crosshairsOn);
								
    	NRuler.setCurrentState(dataViewerIsotopePadPanel.zmax
								, dataViewerIsotopePadPanel.nmax
								, dataViewerIsotopePadPanel.mouseX
								, dataViewerIsotopePadPanel.mouseY
								, dataViewerIsotopePadPanel.xoffset
								, dataViewerIsotopePadPanel.yoffset
								, dataViewerIsotopePadPanel.crosshairsOn);
	
		sp.setColumnHeaderView(ZRuler);
        sp.setRowHeaderView(NRuler);
        
        sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.LOWER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
        sp.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new Corner());
        
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		c.add(sp, BorderLayout.CENTER);
		c.add(p, BorderLayout.EAST);
		c.add(botPanel, BorderLayout.SOUTH);
		
		//Add window listener
        addWindowListener(this);
	
		//Set color formatting
		
		
    } 
    
    //Method for ChangeListener
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent ce){
    
    	//If source is z max slider
    	if(ce.getSource()==zmaxSlider){
    	
    		//Set text of z max field to value fo z max slider 
    		zmaxField.setText(Integer.toString(zmaxSlider.getValue()));
    		
    		//Call reset chart to show change
    		resetChart();
    	
    	//If source is n max slider	
    	}else if(ce.getSource()==nmaxSlider){
    	
    		//Set text of n max field to value fo n max slider 
    		nmaxField.setText(Integer.toString(nmaxSlider.getValue()));
    		
    		//Call reset chart to show change
   			resetChart();
    		
    	}
    
    }
    
    //Method to create String for CGI call to get rate ids
    //String consists of a comma seperated list of types
    /**
     * Gets the type string.
     *
     * @return the type string
     */
    public String getTypeString(){
    
    	//Declare empty string
    	String string = "";
    
    	//Get reaction type array from data structure
    	int[] reactionTypeArray = ds.getReactionTypeArray();

		//Loop over array length
    	for(int i=0; i<reactionTypeArray.length; i++){

    		//If first entry do not put a comma in front
    		if(i==0){
    		
    			string = "0" + String.valueOf(reactionTypeArray[i]);
    		
    		//Else place comma in front
    		}else{
    		
    			string = string + "," + "0" + String.valueOf(reactionTypeArray[i]);
    			
    		}
    	
    	}

    	//Return string
    	return string;
    
    }
    
    //Method to create an array of rate ids
    /**
     * Creates the nuc data id array.
     */
    public void createNucDataIDArray(){
    
    	//Create var to hold number of rate ids
    	int numberNucDataIDs = 0;
	
		//Loop over library data structures
    	for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){

			//If the library has any rate data structures
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
				
				//Loop over the rate data structures
	    		for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
	    		
	    			//Increment number of rate ids
	    			numberNucDataIDs++;

	    		}
    		
    		}
    	
    	}
    
    	//Create String array to hold rate ids
    	String[] outputArray = new String[numberNucDataIDs];
    
    	//Initialize number of rate ids var
    	numberNucDataIDs = 0;
    
    	//Loop over library data structures
    	for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    	
    		//If the library has any rate data structures
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    	
    			//Loop over the rate data structures
	    		for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
	    		
	    			//Assign rate ids to string array
	    			outputArray[numberNucDataIDs] = ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataID();
					
					//Increment number of rate ids var
	    			numberNucDataIDs++;
	    		
	    		}
	    		
	    	}
    	
    	}
    
    	//Assign string array to data structure
    	ds.setNucDataIDArray(outputArray);
    
    }
   
   	//Method to concatenate the rate ids for CGI call
    /**
	    * Parses the nuc data id array.
	    *
	    * @return the string
	    */
	   public String parseNucDataIDArray(){
    	
    	//Set local rate id array
    	String[] nucDataIDArray = ds.getNucDataIDArray();
    	
    	//Create empty string
    	String string = "";
    	
    	//Loop over rate ids 
    	for(int i=0; i<nucDataIDArray.length; i++){
    	
    		//If first entry do not add newline char
    		if(i==0){
    		
    			string = nucDataIDArray[i];
    		
    		}else{
    		
    			string = string + "\n" + nucDataIDArray[i];
    		
    		}

    	}

		//Return string
    	return string;
    	
   	}
    
    //Method for Action listener
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	
    	//If the source is the NucData Plotting Interface button
        if(ae.getSource()==dataButton){
        	
        	//Call egt current state for this class
        	getCurrentState();
        	
        	//Call to initialize rate data structures
        	initializeNucDataDataStructures();
        	
        	//Loop over number of library data structures
        	for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
        	
        		//If this library data structure has a Z list assigned
        		//This is here to prevent ????????
        		if(ds.getNucDataSetStructureArray()[i].getZList()!=null){

					//Create temp library data structure
        			NucDataSetDataStructure appndsds1 = new NucDataSetDataStructure();
					
					//Set the structure to the temp CGI structure
					ds.setCurrentNucDataSetDataStructure(appndsds1);
					
					//Set library name CGI var 
					ds.setNucDataSetName(ds.getNucDataSetStructureArray()[i].getNucDataSetName());

					//Set reaction type string for CGI
					ds.setTypeString(getTypeString());
 					
 					//Create Vector to hold temp rate data structures
 					Vector appnddsViktor = new Vector();
 					
 					//Loop over number of slected isotopes for this library
					for(int j=0; j<ds.getViktorArray()[i].size(); j++){
					
						//Set isotope string CGI var for this library for each isotope chosen
						ds.setIsotopeString((int)((Point)ds.getViktorArray()[i].elementAt(j)).getX() 
																						+ "," 
																						+ ((int)((Point)ds.getViktorArray()[i].elementAt(j)).getX()
																						+ (int)((Point)ds.getViktorArray()[i].elementAt(j)).getY()));
						
						//If you get good rate ids from server
						//CGI parses ids to array of rate data
						//structures in applds1
			        	if(Cina.cinaCGIComm.doCGICall("GET NUC DATA LIST", Cina.dataViewerFrame)){
			        	
			        		//Loop over rate data structures in temp lib data structure 
							for(int m=0; m<appndsds1.getNucDataDataStructures().length; m++){
							
								//Assign each rate data structure to temp vector
								appnddsViktor.addElement(appndsds1.getNucDataDataStructures()[m]);
							
							}
			        	
			        	}

					}
					
					//Trim excess entries from temp rate vector
					appnddsViktor.trimToSize();
					
					//Create an array of rate data structures
					NucDataDataStructure[] appnddsa = new NucDataDataStructure[appnddsViktor.size()];

					//Loop over temp rate vector's size
					for(int j=0; j<appnddsViktor.size(); j++){
					
						//Assign element of temp vector to array
						appnddsa[j] = (NucDataDataStructure)(appnddsViktor.elementAt(j));
					
					}
					
					//Assign array of rate data structures to this library data structure
					ds.getNucDataSetStructureArray()[i].setNucDataDataStructures(appnddsa);
		
				}
				
        	}
        	
        	//Call this method to set rate id array in rate viewer data structure
        	createNucDataIDArray();
        	
        	//Set rates cgi var by calling parse rate id array method
        	ds.setNucData(parseNucDataIDArray());
        	
        	//Set rate properties for CGI call
        	ds.setProperties("Reaction String"
        																+ "\u0009Number of Points"
        																+ "\u0009X points"
        																+ "\u0009Y points"
        																+ "\u0009Reaction Type"
        																+ "\u0009Organization Code"
        																+ "\u0009People Code"
        																+ "\u0009Nuc Data Notes"
        																+ "\u0009Creation Date"
        																+ "\u0009Reference Citation");
        		
        	//If we have at least one rate												
			if(!ds.getNucData().equals("")){

				//If the get rate info cgi call was successful
	            if(Cina.cinaCGIComm.doCGICall("GET NUC DATA INFO", Cina.dataViewerFrame)){
	
	            	//Calculate temp and rate range
	            	setInitEnergyCSRange();
	            	setInitEnergySFRange();
	            	
	            	setInitCSRange();
	            	setInitSFRange();
	            
	            	setMaxNumberCSPoints();
	            	setMaxNumberSFPoints();
	            
	            	//Set number of total and comp rates to this data structure 
	            	ds.setNumberTotalCSNucData(getNumberTotalCSNucData());
					ds.setNumberTotalSFNucData(getNumberTotalSFNucData());

	            	//If plotting interface already exists
	            	if(dataViewerPlotFrame!=null){
	
						//Set plotting interface properties
	            		dataViewerPlotFrame.setFormatPanelState();
	            		dataViewerPlotFrame.setNucDataListPanel();
	            		
	            		//Redraw plot and set visible
	            		dataViewerPlotFrame.redrawPlot();
	            		dataViewerPlotFrame.setVisible(true);
	
					//If plotting interface has not yet been opened
	            	}else{
	            	
	            		dataViewerPlotFrame = new DataViewerPlotFrame(ds);
	            	
	            	}
	            
	            }
            
        	}
         
        //If source is the clear selections button
    	}else if(ae.getSource()==clearButton){
           
           	//Loop over checkboxes
           	for(int i=0; i<9; i++){
           	
           		//Set check box properties
           		checkBoxArray[i].setEnabled(true);
           		
           		//Remove listener from check boxes 
           		checkBoxArray[i].removeItemListener(this);
           		
           		//Unselect check boxes
           		checkBoxArray[i].setSelected(false);
           	
           	}
           	
           	//Create temp vector array and assign it values
           	Vector[] temp = ds.getViktorArray();
           	
           	//Loop over array
           	for(int i=0; i<ds.getViktorArray().length; i++){
           	
           		//Clear all elements in temp array
           		temp[i].clear();
           	
           	}
           	
           	//Assign cleared array back to data structure
           	ds.setViktorArray(temp);
           	
           	//Repaint ioteop pad to reflect changes
           	dataViewerIsotopePadPanel.repaint();
           	
           	//Loop over check boxes
           	for(int i=0; i<9; i++){

				//Add listener back to check boxes
           		checkBoxArray[i].addItemListener(this);
           	
           	}
           	
           	//Disable rate button
           	dataButton.setEnabled(false);
           
        //If source is teh submit button of the exit rate viewer dialog
        }else if(ae.getSource()==submitButton){
			
			if(closeRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
				
				//Close dialog box
				saveDialog.setVisible(false);
				saveDialog.dispose();
				
				ds.initialize();
				
				//Hide the frame
         		setVisible(false);
         		
         		//Remove all Components
        		c.removeAll();
        
        		closeAllFrames();
        
        		//Free system resources 
        		dispose();

        		Cina.dataViewerFrame = null;

			//Else if do not close chosen
			}else if(doNotCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
				
				//Close dialog box
				saveDialog.setVisible(false);
				saveDialog.dispose();
				
			}
		}else if(ae.getSource()==helpButton){
			dataViewerHelpFrame = new HelpFrame("Help on Nuclear Data Viewer"
																	, "DataViewer");
			dataViewerHelpFrame.setVisible(true);
		}
    } 
    
    //Method to set initial temperature range for plotting       	
    /**
     * Sets the init energy cs range.
     */
    public void setInitEnergyCSRange(){
    
    	ds.setEnergyCSmin(getEnergyCSmin());
    	
    	ds.setEnergyCSmax(getEnergyCSmax());
    
    }
     
    //Method to set initial temperature range for plotting       	
    /**
     * Sets the init energy sf range.
     */
    public void setInitEnergySFRange(){
    
    	ds.setEnergySFmin(getEnergySFmin());
    	
    	ds.setEnergySFmax(getEnergySFmax());
    
    }
     
    //Method to set initial rate range for plotting        	
    /**
     * Sets the init cs range.
     */
    public void setInitCSRange(){
    
    	ds.setCSmin(getCSmin());
    	
    	ds.setCSmax(getCSmax());
    
    }
    
    //Method to set initial rate range for plotting        	
    /**
     * Sets the init sf range.
     */
    public void setInitSFRange(){
    
    	ds.setSFmin(getSFmin());
    	
    	ds.setSFmax(getSFmax());
    
    }
    
    //Method to set initial rate range for plotting        	
    /**
     * Sets the max number sf points.
     */
    public void setMaxNumberSFPoints(){
    
    	ds.setMaxNumberSFPoints(getMaxNumberSFPoints());

    }
    
 	//Method to set initial rate range for plotting        	
    /**
	  * Sets the max number cs points.
	  */
	 public void setMaxNumberCSPoints(){
    
    	ds.setMaxNumberCSPoints(getMaxNumberCSPoints());

    }
    
    //Method to calculate rate minimum
    /**
     * Gets the max number cs points.
     *
     * @return the max number cs points
     */
    public int getMaxNumberCSPoints(){
		
		//Create a variable for rate min that is very big
		int newMaxNumberCSPoints = 0;
		
		//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("CS(E)")){

						//Assign new rate min
						newMaxNumberCSPoints = Math.max(newMaxNumberCSPoints, ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getXDataArray().length);
    				
    				}
    		
    			}
    		
    		}
    	
    	}

		//Return magnitude of rate
		return newMaxNumberCSPoints;
	
	}
    
    //Method to calculate rate minimum
    /**
     * Gets the max number sf points.
     *
     * @return the max number sf points
     */
    public int getMaxNumberSFPoints(){
		
		//Create a variable for rate min that is very big
		int newMaxNumberSFPoints = 0;
		
		//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("S(E)")){

						//Assign new rate min
						newMaxNumberSFPoints = Math.max(newMaxNumberSFPoints, ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getXDataArray().length);
    				
    				}
    		
    			}
    		
    		}
    	
    	}

		//Return magnitude of rate
		return newMaxNumberSFPoints;
	
	}
    
    //Method to get the number of total rates
    /**
     * Gets the number total cs nuc data.
     *
     * @return the number total cs nuc data
     */
    public int getNumberTotalCSNucData(){
    
    	//Create counter
    	int total = 0;
    	
    	//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("CS(E)")){
    				
						total++;
    				
    				}
    		
    			}
    		
    		}
    	
    	}

    	//Return counter
    	return total;
    
    }
    
    //Method to get the number of total rates
    /**
     * Gets the number total sf nuc data.
     *
     * @return the number total sf nuc data
     */
    public int getNumberTotalSFNucData(){
    
    	//Create counter
    	int total = 0;
    	
    	//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("S(E)")){
    				
						total++;
    				
    				}
    		
    			}
    		
    		}
    	
    	}

    	//Return counter
    	return total;
    
    }
    
    //Method to calculate rate minimum
    /**
     * Gets the energy c smax.
     *
     * @return the energy c smax
     */
    public double getEnergyCSmax(){
		
		//Create a variable for rate min that is very big
		double newEnergyCSmax = 0.0;
		
		//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("CS(E)")){
    				
	    				//Loop over rate array values
	    				for(int k=0; k<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getXDataArray().length; k++){
	
							//Assign new rate min
							newEnergyCSmax = Math.max(newEnergyCSmax, ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getXDataArray()[k]);
	
	    				}
    				
    				}
    		
    			}
    		
    		}
    	
    	}

		//Return magnitude of rate
		return newEnergyCSmax;
	
	}
	
	//Method to calculate rate minimum
    /**
	 * Gets the energy c smin.
	 *
	 * @return the energy c smin
	 */
	public double getEnergyCSmin(){
		
		//Create a variable for rate min that is very big
		double newEnergyCSmin = 1E30;
		
		//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("CS(E)")){
    				
	    				//Loop over rate array values
	    				for(int k=0; k<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getXDataArray().length; k++){
	
							//Assign new rate min
							newEnergyCSmin = Math.min(newEnergyCSmin, ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getXDataArray()[k]);
	
	    				}
    				
    				}
    		
    			}
    		
    		}
    	
    	}

		//Return magnitude of rate
		return newEnergyCSmin;
	
	}
    
    //Method to calculate rate minimum
    /**
     * Gets the energy s fmax.
     *
     * @return the energy s fmax
     */
    public double getEnergySFmax(){
		
		//Create a variable for rate min that is very big
		double newEnergySFmax = 0.0;
		
		//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("S(E)")){
    				
	    				//Loop over rate array values
	    				for(int k=0; k<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getXDataArray().length; k++){
	
							//Assign new rate min
							newEnergySFmax = Math.max(newEnergySFmax, ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getXDataArray()[k]);
	
	    				}
    				
    				}
    		
    			}
    		
    		}
    	
    	}

		//Return magnitude of rate
		return newEnergySFmax;
	
	}
	
	//Method to calculate rate minimum
    /**
	 * Gets the energy s fmin.
	 *
	 * @return the energy s fmin
	 */
	public double getEnergySFmin(){
		
		//Create a variable for rate min that is very big
		double newEnergySFmin = 1E30;
		
		//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("S(E)")){
    				
	    				//Loop over rate array values
	    				for(int k=0; k<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getXDataArray().length; k++){
	
							//Assign new rate min
							newEnergySFmin = Math.min(newEnergySFmin, ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getXDataArray()[k]);
	
	    				}
    				
    				}
    		
    			}
    		
    		}
    	
    	}

		//Return magnitude of rate
		return newEnergySFmin;
	
	}
    
    //Method to calculate rate minimum
    /**
     * Gets the c smax.
     *
     * @return the c smax
     */
    public int getCSmax(){
		
		//Create a variable for rate min that is very big
		double newCSmax = 0.0;
		
		//Create int var to hold magnitude of rate min
		int CSMax = 0;
		
		//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("CS(E)")){
    				
	    				//Loop over rate array values
	    				for(int k=0; k<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getYDataArray().length; k++){
	
							//Assign new rate min
							newCSmax = Math.max(newCSmax, ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getYDataArray()[k]);
	
	    				}
    				
    				}
    		
    			}
    		
    		}
    	
    	}
		
		//Get magnitude of rate
		CSMax = (int)Math.ceil(0.434294482*Math.log(newCSmax));

		//Return magnitude of rate
		return CSMax;
	
	}
	
	//Method to calculate rate minimum
    /**
	 * Gets the c smin.
	 *
	 * @return the c smin
	 */
	public int getCSmin(){
		
		//Create a variable for rate min that is very big
		double newCSmin = 1e30;
		
		//Create int var to hold magnitude of rate min
		int CSMin = 0;
		
		//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("CS(E)")){
    				
	    				//Loop over rate array values
	    				for(int k=0; k<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getYDataArray().length; k++){
	
							//Assign new rate min
							newCSmin = Math.min(newCSmin, ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getYDataArray()[k]);
	
	    				}
    				
    				}
    		
    			}
    		
    		}
    	
    	}
		
		//Get magnitude of rate
		CSMin = (int)Math.floor(0.434294482*Math.log(newCSmin));

		//Return magnitude of rate
		return CSMin;
	
	}
	
	/**
	 * Gets the s fmax.
	 *
	 * @return the s fmax
	 */
	public double getSFmax(){
		
		//Create a variable for rate min that is very big
		double newSFmax = 0.0;
		
		//Create int var to hold magnitude of rate min
		double SFMax = 0;
		
		//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("S(E)")){
    				
	    				//Loop over rate array values
	    				for(int k=0; k<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getYDataArray().length; k++){
	
							//Assign new rate min
							newSFmax = Math.max(newSFmax, ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getYDataArray()[k]);
	
	    				}
    				
    				}
    		
    			}
    		
    		}
    	
    	}

		//Get magnitude of rate
		SFMax = newSFmax;

		//Return magnitude of rate
		return SFMax;
	
	}
    
    /**
     * Gets the s fmin.
     *
     * @return the s fmin
     */
    public double getSFmin(){
		
		//Create a variable for rate min that is very big
		double newSFmin = 1e30;
		
		//Create int var to hold magnitude of rate min
		double SFMin = 0;
		
		//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals("S(E)")){
    				
	    				//Loop over rate array values
	    				for(int k=0; k<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getYDataArray().length; k++){
	
							//Assign new rate min
							newSFmin = Math.min(newSFmin, ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getYDataArray()[k]);
	
	    				}
    				
    				}
    		
    			}
    		
    		}
    	
    	}

		//Get magnitude of rate
		SFMin = newSFmin;

		//Return magnitude of rate
		return SFMin;
	
	}
    
    //Method to inilialize rate data strcutures
    /**
     * Initialize nuc data data structures.
     */
    public void initializeNucDataDataStructures(){
    
    	//Loop over libraries
    	for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Set those rates to null
    			ds.getNucDataSetStructureArray()[i].setNucDataDataStructures(null);
    		
    		}
    	
    	}
    
    }
    
    //Method for Item Listener
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie) {

		//If all reaction types check box is selected
        if(checkBoxArray[8].isSelected()){
        
        	//Loop over checkboxes
        	for(int i=0; i<8; i++){
        	
        		//Set checked
        		checkBoxArray[i].setSelected(true);
        		
        		//Disable checkboxes
        		checkBoxArray[i].setEnabled(false);
        
        	}
        
        //If all reaction types check box is not selected and the source is the all reaction types check box
        }else if(!checkBoxArray[8].isSelected() && ie.getSource()==checkBoxArray[8]){
        
        	//Loop over check boxes
        	for(int i=0; i<8; i++){

				//Enabled checkboxes
        		checkBoxArray[i].setEnabled(true);
        
        	}
        
        }
        
        //Call get current state method of isotope pad
        dataViewerIsotopePadPanel.getCurrentState();
        
        //Create local boolean to hold whether there are any isotopes selected 
        boolean empty = true;
        
        //Loop over isotope selection vector
		for(int i=0; i<ds.getViktorArray().length; i++){
		
			//If there are some isotopes selected
			if(ds.getViktorArray()[i].size()!=0){
			
				//Then NOT empty
				empty = false;
			
			}

		}
		
		//Create local boolean to hold whether there are any check boxes checked
		boolean checked = false;
		
		//Loop over check boxes
		for(int i=0; i<8; i++){
		
			//If this check box is selected
			if(checkBoxArray[i].isSelected()){
			
				//Then there is at least one check box selected
				checked = true;
			
			}
			
		}
		
		//If there are some isotopes selected and some check boxes
		if(!empty && checked){
		
			//Enable rate button
			dataButton.setEnabled(true);
		
		//Else
		}else{
		
			//Disable rate button
			dataButton.setEnabled(false);
		
		}
        
        //If source is the library combo box
		if(ie.getSource()==nucDataSetComboBox){
		
			//Call get current state method for isotope pad
			dataViewerIsotopePadPanel.getCurrentState();
		
			//Assign library index in data structure
			ds.setNucDataSetIndex(nucDataSetComboBox.getSelectedIndex());
			
			//Set temp lib data structure for CGI Call
	    	ds.setCurrentNucDataSetDataStructure(ds.getNucDataSetStructureArray()[ds.getNucDataSetIndex()]);
			
			//Set library name for CGI Call		
			ds.setNucDataSetName(ds.getCurrentNucDataSetDataStructure().getNucDataSetName());
			
			if(Cina.cinaCGIComm.doCGICall("GET NUC DATA SET ISOTOPES", Cina.dataViewerFrame)){
			
				//Call set current state method for isotope pad
				dataViewerIsotopePadPanel.setCurrentState();
			
			}
		
		}
		
    }
    
    //Method to see whether or not an isotope is used in a library
    /**
     * Contains isotope.
     *
     * @param z the z
     * @param n the n
     * @return true, if successful
     */
    public boolean containsIsotope(int z, int n){
    
    	//Create boolean
    	boolean containsIsotope = false;
    	
    	//Assign library index for this library
    	ds.setNucDataSetIndex(nucDataSetComboBox.getSelectedIndex());
		
		//Assign this library structure to temp CGI structure
		ds.setCurrentNucDataSetDataStructure(ds.getNucDataSetStructureArray()[nucDataSetComboBox.getSelectedIndex()]);	
    	
    	//Create temp library data structure	
    	NucDataSetDataStructure appndsds = ds.getCurrentNucDataSetDataStructure();

		//Loop over library's Z list (list of Z's for that library)
    	for(int i=0; i<appndsds.getZList().length; i++){
    	
    		//If this Z is in the Z list
	    	if(appndsds.getZList()[i]==z){
	    	
	    		//Loop over maximum number of isotopes per Z
	    		for(int j=0; j<200; j++){
		
					//If this isotope A value is NOT -1 (-1 is assigned to fill up the array)
					//(This is done in the CGIComm parser)
					if(appndsds.getIsotopeList()[i][j]!=-1){
			    	
			    		//If this A matches the given N value
			    		if((appndsds.getIsotopeList()[i][j]-appndsds.getZList()[i])==n){
			    		
			    			//Set boolean to true
			    			containsIsotope = true;
			    		
			    		}
			    	
			    	}
			    	
			    }
			    
	    	}
    	
    	}
    	
    	//return boolean
    	return containsIsotope;
    
    }
	
	//Method to set current state of this interface
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		//Remove all libraries from combo box
		nucDataSetComboBox.removeAllItems();
		
		//Remove item listener from combo box
		nucDataSetComboBox.removeItemListener(this);
		
		//Create String array to hold the names 
		String[] sourceLNucDataSetArray = new String[ds.getNumberPublicNucDataSetDataStructures()
										+ ds.getNumberSharedNucDataSetDataStructures()
										+ ds.getNumberUserNucDataSetDataStructures()];
		
		//Create lib data structure array
		NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[ds.getNumberPublicNucDataSetDataStructures()
																											+ ds.getNumberSharedNucDataSetDataStructures()
																											+ ds.getNumberUserNucDataSetDataStructures()];
		//Create counter
		int sourceNucDataSetArrayCounter = 0;
		
		//Loop over public libraries
		for(int i=0; i<ds.getNumberPublicNucDataSetDataStructures(); i++){

			//Add library to combo box
			nucDataSetComboBox.addItem(ds.getPublicNucDataSetDataStructureArray()[i].getNucDataSetName());
			
			//Add lib structure to array
			outputArray[sourceNucDataSetArrayCounter] = ds.getPublicNucDataSetDataStructureArray()[i];
			
			//Increment counter
			sourceNucDataSetArrayCounter++;
	
		}
		
		//Loop over shared libraries
		for(int i=0; i<ds.getNumberSharedNucDataSetDataStructures(); i++){
	
			//Add library to combo box
			nucDataSetComboBox.addItem(ds.getSharedNucDataSetDataStructureArray()[i].getNucDataSetName());
			
			//Add lib structure to array
			outputArray[sourceNucDataSetArrayCounter] = ds.getSharedNucDataSetDataStructureArray()[i];
			
			//Increment counter
			sourceNucDataSetArrayCounter++;
	
		}
		
		//Loop over user libraries
		for(int i=0; i<ds.getNumberUserNucDataSetDataStructures(); i++){
	
			//Add library to combo box
			nucDataSetComboBox.addItem(ds.getUserNucDataSetDataStructureArray()[i].getNucDataSetName());
	
			//Add lib structure to array
			outputArray[sourceNucDataSetArrayCounter] = ds.getUserNucDataSetDataStructureArray()[i];
			
			//Increment counter
			sourceNucDataSetArrayCounter++;
			
		}
		
		//Assign lib array to data structure
		ds.setNucDataSetStructureArray(outputArray);
		
		//Assign number of libraries to data structure
		ds.setNumberNucDataSetStructures(outputArray.length);
		
		//Set library combo box to current library index
		nucDataSetComboBox.setSelectedIndex(ds.getNucDataSetIndex());
		
		//Add item listener back to combo box
		nucDataSetComboBox.addItemListener(this);
		
		//Create a temp lib data structure holding lib data structure corresponding to current library index
		NucDataSetDataStructure appndsds = ds.getNucDataSetStructureArray()[ds.getNucDataSetIndex()];
		
		//Set current library to temp library
		ds.setCurrentNucDataSetDataStructure(appndsds);
		
		//Creat local vector array for isotope points
		Vector[] viktorArray = new Vector[outputArray.length];
		
		//Loop over array
		for(int i=0; i<outputArray.length; i++){
		
			//Create new empty vectors for temp vector array
			viktorArray[i] = new Vector();
		
		}
		
		//Assign new vector array to data structure
		ds.setViktorArray(viktorArray);
		
		//If the reaction type array has been created
		if(ds.getReactionTypeArray()!=null){
		
			//Create a new reaction type array
			int[] reactionTypeArray = ds.getReactionTypeArray();
		
			//Loop over array
			for(int i=0; i<reactionTypeArray.length; i++){
			
				//Set check boxes selected and enabled if included in reaction type array
				checkBoxArray[reactionTypeArray[i]-1].setSelected(true);
				checkBoxArray[reactionTypeArray[i]-1].setEnabled(true);
			
			}
		
		}
		
		//Initialize isotope pad
		dataViewerIsotopePadPanel.initialize();
		
		resetChart();
	
	}
	
	//Method to get current state of this interface
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
	
		//Create counter
		int numTypes = 0;
		
		//Loop over check boxes
		for(int i=0; i<8; i++){
		
			//If check box is selected
			if(checkBoxArray[i].isSelected()){
			
				//Increment counter
				numTypes++;
			
			}
		
		}
	
		//Create array to hold selected reaction types
		int[] reactionTypeArray = new int[numTypes];
		
		//Initialize counter
		numTypes = 0;
		
		//Loop over check boxes
		for(int i=0; i<8; i++){
		
			//If check box is selected
			if(checkBoxArray[i].isSelected()){
			
				//Assign value to reaction type array
				reactionTypeArray[numTypes] = i+1;

				//Increment counter
				numTypes++;
			
			}
		
		}
		
		//Assign reaction type array
		ds.setReactionTypeArray(reactionTypeArray);
	
	}
	
	//Method to close all open windows for this feature
 	/**
	 * Close all frames.
	 */
	public void closeAllFrames(){
	
		//If plotter has been created
		if(dataViewerPlotFrame!=null){
		
			//If table of points has been created
			if(dataViewerPlotFrame.dataViewerTableFrame!=null){
				
				//Close
				dataViewerPlotFrame.dataViewerTableFrame.setVisible(false);
				dataViewerPlotFrame.dataViewerTableFrame.dispose();
			
			}
			
			//If rate info has been created
			if(dataViewerPlotFrame.dataViewerInfoFrame!=null){
				
				//Close
				dataViewerPlotFrame.dataViewerInfoFrame.setVisible(false);
				dataViewerPlotFrame.dataViewerInfoFrame.dispose();
			
			}
			
			//Close
			dataViewerPlotFrame.setVisible(false);
			dataViewerPlotFrame.dispose();
			
		}
		
		if(dataViewerHelpFrame!=null){
			dataViewerHelpFrame.setVisible(false);
			dataViewerHelpFrame.dispose();
		}
		
	}
	
	//Method to reset chart according to interface changes made by user
    /**
	 * Reset chart.
	 */
	public void resetChart(){
        
        //Create local vars to get values of n and z max
        double s1 = zmaxSlider.getValue();
        double s2 = nmaxSlider.getValue();

		//Set z and n max for isotope pad
        dataViewerIsotopePadPanel.zmax = zmaxSlider.getValue();
        dataViewerIsotopePadPanel.nmax = nmaxSlider.getValue();
        
        //Reset values for isotope pad
        dataViewerIsotopePadPanel.width = (int)(dataViewerIsotopePadPanel.boxWidth*(dataViewerIsotopePadPanel.nmax+1));
        dataViewerIsotopePadPanel.height = (int)(dataViewerIsotopePadPanel.boxHeight*(dataViewerIsotopePadPanel.zmax+1));
        
        dataViewerIsotopePadPanel.setSize(dataViewerIsotopePadPanel.width+2*dataViewerIsotopePadPanel.xoffset,dataViewerIsotopePadPanel.height+2*dataViewerIsotopePadPanel.yoffset);
        
        dataViewerIsotopePadPanel.xmax = (int)(dataViewerIsotopePadPanel.xoffset + dataViewerIsotopePadPanel.width);
        dataViewerIsotopePadPanel.ymax = (int)(dataViewerIsotopePadPanel.yoffset + dataViewerIsotopePadPanel.height);
        
        dataViewerIsotopePadPanel.setCurrentState();

		dataViewerIsotopePadPanel.validate();

		dataViewerIsotopePadPanel.setPreferredSize(dataViewerIsotopePadPanel.getSize());
		
		dataViewerIsotopePadPanel.revalidate();
	
		sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(dataViewerIsotopePadPanel.getHeight());
    	
    	sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
	
        ZRuler.setPreferredWidth((int)dataViewerIsotopePadPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)dataViewerIsotopePadPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(dataViewerIsotopePadPanel.zmax
								, dataViewerIsotopePadPanel.nmax
								, dataViewerIsotopePadPanel.mouseX
								, dataViewerIsotopePadPanel.mouseY
								, dataViewerIsotopePadPanel.xoffset
								, dataViewerIsotopePadPanel.yoffset
								, dataViewerIsotopePadPanel.crosshairsOn);
								
    	NRuler.setCurrentState(dataViewerIsotopePadPanel.zmax
								, dataViewerIsotopePadPanel.nmax
								, dataViewerIsotopePadPanel.mouseX
								, dataViewerIsotopePadPanel.mouseY
								, dataViewerIsotopePadPanel.xoffset
								, dataViewerIsotopePadPanel.yoffset
								, dataViewerIsotopePadPanel.crosshairsOn);
			
    }
    
    //Window closing listener
	/* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing(WindowEvent we) {   
	
		//If we are closing the NucData Gen JFrame
		if(we.getSource()==this){  
		   
        	createSaveDialog();
        
        //If we are closing the save dialog
    	}else if(we.getSource()==saveDialog){
    		
    		//Close the dialog
    		saveDialog.setVisible(false);
    		saveDialog.dispose();
    	}
    } 
    
    //Remainder of windowListener methods
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(WindowEvent we){}
    
    /**
     * Creates the save dialog.
     */
    public void createSaveDialog(){
    	
    	//Create a new JDialog object
    	saveDialog = new JDialog(this, "Exit Nuclear Data Viewer?", true);
    	saveDialog.setSize(355, 158);
    	saveDialog.getContentPane().setLayout(new GridBagLayout());
    	saveDialog.setLocationRelativeTo(this);
    	
    	//Check box group for radio buttons
    	choiceButtonGroup = new ButtonGroup();
    	
    	//Create checkboxes and set state and cbGroup
    	saveAndCloseRadioButton = new JRadioButton("Save current Nuclear Data Viewer information and exit the Nuclear Data Viewer.", true);
    	saveAndCloseRadioButton.setFont(Fonts.textFont);
    	
    	closeRadioButton = new JRadioButton("Exit the Nuclear Data Viewer.", true);
   		closeRadioButton.setFont(Fonts.textFont);
   		
    	doNotCloseRadioButton = new JRadioButton("Return to the Nuclear Data Viewer.", false);
		doNotCloseRadioButton.setFont(Fonts.textFont);
		
		//choiceButtonGroup.add(saveAndCloseRadioButton);
		choiceButtonGroup.add(closeRadioButton);
		choiceButtonGroup.add(doNotCloseRadioButton);
		
		//Create submit button and its properties
		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		//Layout the components
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;

		saveDialog.getContentPane().add(closeRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		saveDialog.getContentPane().add(doNotCloseRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		saveDialog.getContentPane().add(submitButton, gbc);
		
		//Cina.setFrameColors(saveDialog);
		
		//Show the dialog
		saveDialog.setVisible(true);

    }
} 