package org.nucastrodata.rate.rateviewer;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateViewerDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;


import org.nucastrodata.Cina;
import org.nucastrodata.Corner;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.HelpFrame;
import org.nucastrodata.IsotopeRuler;
import org.nucastrodata.rate.rateviewer.RateViewerIsotopePadPanel;
import org.nucastrodata.rate.rateviewer.RateViewerPlotFrame;


/**
 * The Class RateViewerFrame.
 */
public class RateViewerFrame extends JFrame implements ItemListener
														, ActionListener
														, ChangeListener
														, WindowListener{
	
    /** The rate viewer isotope pad panel. */
    private RateViewerIsotopePadPanel rateViewerIsotopePadPanel;
    
    /** The rate viewer plot frame. */
    public static RateViewerPlotFrame rateViewerPlotFrame; 
    
    /** The rate viewer help frame. */
    public static HelpFrame rateViewerHelpFrame;
	
	/** The lib combo box. */
	private JComboBox libComboBox;
	
	/** The check box array. */
	protected JCheckBox[] checkBoxArray = new JCheckBox[9];
    
    /** The sp. */
    private JScrollPane sp;
    
    /** The submit button. */
    private JButton clearButton, helpButton, submitButton;
    
    /** The rate button. */
    protected JButton rateButton;
    
    /** The element field. */
    protected  JTextField zmaxField, nmaxField, aField, zField, elementField;
    
    /** The nmax slider. */
    private JSlider zmaxSlider, nmaxSlider;
	
	/** The do not close radio button. */
	private JRadioButton saveAndCloseRadioButton, closeRadioButton, doNotCloseRadioButton;
    
    /** The save dialog. */
    private JDialog saveDialog;
    
    /** The N ruler. */
    protected IsotopeRuler ZRuler, NRuler;
    
    /** The ds. */
    private RateViewerDataStructure ds;
    
    /** The param. */
    public boolean param;
    
    /**
     * Instantiates a new rate viewer frame.
     *
     * @param ds the ds
     * @param param the param
     */
    public RateViewerFrame(RateViewerDataStructure ds, boolean param){
    	
    	this.param = param;
    	this.ds = ds;
    	
    	Container c = getContentPane();
        c.setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();

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

        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());

        JPanel tPanel = new JPanel();
        tPanel.setLayout(new GridLayout(1,1,5,5));
        tPanel.setFont(Fonts.titleFont);
        JLabel reacLabel = new JLabel("Reaction Types",JLabel.LEFT);
        tPanel.add(reacLabel);

		libComboBox = new JComboBox();
		libComboBox.setFont(Fonts.textFont);

		JPanel cboxPanel = new JPanel(new GridLayout(12,1,8,8));
        
        cboxPanel.add(new JLabel("Choose Library: "));
        cboxPanel.add(libComboBox);
        cboxPanel.add(tPanel);

		String[] checkBoxStringArray = {"a-->b"
										, "a-->b+c"
										, "a-->b+c+d"
										, "a+b-->c"
										, "a+b-->c+d"
										, "a+b-->c+d+e"
										, "a+b-->c+d+e+f"
										, "a+b+c-->d(+e)"
										, "All Reaction Types"};

        for(int i=0; i<9; i++){
        	checkBoxArray[i] = new JCheckBox(checkBoxStringArray[i]);
        	checkBoxArray[i].addItemListener(this);
        	checkBoxArray[i].setFont(Fonts.textFont);
        	cboxPanel.add(checkBoxArray[i]);
        }

        p.add(cboxPanel, gs);
        
        JPanel goPanel = new JPanel();
        goPanel.setLayout(new GridBagLayout());
        gs.insets = new Insets(5,3,7,3);
        gs.ipady = 2;
        
        rateButton = new JButton("<html>Reaction Rate<p>Plotting Interface</html>");
        rateButton.setFont(Fonts.buttonFont);
        rateButton.addActionListener(this);
        rateButton.setEnabled(false);

		clearButton = new JButton("<html>Clear All<p>Selections</html>");
        clearButton.setFont(Fonts.buttonFont);
        clearButton.addActionListener(this);

		helpButton = new JButton("<html>Help on<p>This Interface</html>");
        helpButton.setFont(Fonts.buttonFont);
        helpButton.addActionListener(this);

        gs.gridy = 1;
        goPanel.add(rateButton, gs);
        gs.gridy = 2;
        goPanel.add(clearButton, gs);
        gs.gridy = 3;
        goPanel.add(helpButton, gs);
        
        gs.anchor = GridBagConstraints.NORTH;
        gs.gridy = 1;
        p.add(goPanel, gs);
        
        JPanel botPanel = new JPanel();
        botPanel.setLayout(new GridBagLayout());
        botPanel.setFont(Fonts.buttonFont);

        gs.weightx = 100;
        gs.weighty = 100;
        gs.gridx = 0;
        gs.gridy = 0;
        gs.ipadx = 0;
        gs.ipady = 0;
        gs.fill = GridBagConstraints.NONE;
        gs.anchor = GridBagConstraints.WEST;
        gs.insets = new Insets(4,10,4,0);

        JPanel botPanelB = new JPanel();
        JLabel zmaxLabel = new JLabel("Zmax",JLabel.LEFT);
        zmaxLabel.setFont(Fonts.textFont);
        botPanelB.add(zmaxLabel);

		JLabel elementLabel = new JLabel("Element: ");
		elementLabel.setFont(Fonts.textFont);
		JLabel zLabel = new JLabel("Z: ");
		zLabel.setFont(Fonts.textFont);
		JLabel aLabel = new JLabel("A: ");
		aLabel.setFont(Fonts.textFont);

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
        
        rateViewerIsotopePadPanel = new RateViewerIsotopePadPanel(ds);
        
        zmaxSlider = new JSlider(JSlider.HORIZONTAL, 2, 85, rateViewerIsotopePadPanel.getZmax());
		zmaxSlider.addChangeListener(this);
		zmaxField.setText(Integer.toString(rateViewerIsotopePadPanel.getZmax()));
		zmaxField.setEditable(false);

		botPanelB.add(zmaxSlider);
		botPanelB.add(zmaxField);

        gs.insets = new Insets(4,0,4,0);
        gs.gridx = 1;
        botPanel.add(botPanelB, gs);
        JPanel botPanelC = new JPanel();
        JLabel nmaxLabel = new JLabel("Nmax",JLabel.LEFT);
        nmaxLabel.setFont(Fonts.textFont);
        botPanelC.add(nmaxLabel);

        nmaxField = new JTextField();
        nmaxField.setColumns(3);
        nmaxField.setFont(Fonts.textFont);
        nmaxField.setText(Integer.toString(rateViewerIsotopePadPanel.getNmax()));
        nmaxField.setEditable(false);
        
        nmaxSlider = new JSlider(JSlider.HORIZONTAL, 3, 193, rateViewerIsotopePadPanel.getNmax());
        nmaxSlider.addChangeListener(this);
        
        botPanelC.add(nmaxSlider);
        botPanelC.add(nmaxField);
        
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

        sp = new JScrollPane(rateViewerIsotopePadPanel);
        
		JViewport vp = new JViewport();
		vp.setView(rateViewerIsotopePadPanel);
		sp.setViewport(vp);
		
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
		ZRuler.setPreferredWidth((int)rateViewerIsotopePadPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)rateViewerIsotopePadPanel.getSize().getHeight());
		ZRuler.setCurrentState(rateViewerIsotopePadPanel.getZmax()
								, rateViewerIsotopePadPanel.getNmax()
								, rateViewerIsotopePadPanel.getMouseX()
								, rateViewerIsotopePadPanel.getMouseY()
								, rateViewerIsotopePadPanel.getXoffset()
								, rateViewerIsotopePadPanel.getYoffset()
								, rateViewerIsotopePadPanel.getCrosshairsOn());						
    	NRuler.setCurrentState(rateViewerIsotopePadPanel.getZmax()
								, rateViewerIsotopePadPanel.getNmax()
								, rateViewerIsotopePadPanel.getMouseX()
								, rateViewerIsotopePadPanel.getMouseY()
								, rateViewerIsotopePadPanel.getXoffset()
								, rateViewerIsotopePadPanel.getYoffset()
								, rateViewerIsotopePadPanel.getCrosshairsOn());
	
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
		
        addWindowListener(this);
		
    } 
    
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent ce){
    
    	if(ce.getSource()==zmaxSlider){
    		zmaxField.setText(Integer.toString(zmaxSlider.getValue()));
    		resetChart();
    	}else if(ce.getSource()==nmaxSlider){
    		nmaxField.setText(Integer.toString(nmaxSlider.getValue()));
   			resetChart();	
    	}
		
    }
    
    /**
     * Gets the type database string.
     *
     * @return the type database string
     */
    public String getTypeDatabaseString(){
    	String string = "";
    
    	int[] reactionTypeArray = ds.getReactionTypeArray();

    	for(int i=0; i<reactionTypeArray.length; i++){
    		if(i==0){
    			string = "0" + String.valueOf(reactionTypeArray[i]);
    		}else{
    			string = string + "," + "0" + String.valueOf(reactionTypeArray[i]);
    		}
    	}

    	return string;
    }
    
    /**
     * Creates the rate id array.
     */
    public void createRateIDArray(){
    
    	//Create var to hold number of rate ids
    	int numberRateIDs = 0;
	
		//First find the number of rate IDs that were chosen by the user
		//Loop over library data structures
    	for(int i=0; i<ds.getNumberLibraryStructures(); i++){
			//If the library has any rate data structures
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
				//Loop over the rate data structures
	    		for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
	    			//Increment number of rate ids
	    			numberRateIDs++;
	    		}
    		}
    	}
    
    	//Create String array to hold rate ids
    	String[] outputArray = new String[numberRateIDs];
    
    	//Initialize number of rate ids var
    	numberRateIDs = 0;
    
    	//Next assign rate IDs to a String array of size numberRateIDs
    	//Loop over library data structures
    	for(int i=0; i<ds.getNumberLibraryStructures(); i++){
    		//If the library has any rate data structures
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			//Loop over the rate data structures
	    		for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
	    			//Assign rate ids to string array
	    			outputArray[numberRateIDs] = ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getReactionID();
					//Increment number of rate ids var
	    			numberRateIDs++;
	    		}	
	    	}
    	}
    
    	//Assign string array to data structure
    	ds.setRateIDArray(outputArray);

    }
   
   	/**
	    * Parses the rate id array.
	    *
	    * @return the string
	    */
    public String parseRateIDArray(){
    	
    	//Set local rate id array
    	String[] rateIDArray = ds.getRateIDArray();
    	//Create empty string
    	String string = "";
    	
    	//Loop over rate ids 
    	for(int i=0; i<rateIDArray.length; i++){
    		//If first entry do not add newline char
    		if(i==0){
    			string = rateIDArray[i];
    		}else{
    			string = string + "\n" + rateIDArray[i];
    		}

    	}

		//Return string
    	return string;
   	}
    
    /**
     * Gets the number total rates.
     *
     * @return the number total rates
     */
    public int getNumberTotalRates(){
    
    	//Create counter
    	int total = 0;
    	//Loop over library data structures
    	for(int i=0; i<ds.getNumberLibraryStructures(); i++){
    		//If library data structure has any rates
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			//Add number of rates for this library to counter
    			total = total + ds.getLibraryStructureArray()[i].getRateDataStructures().length;
    		}
    	}
    	
    	//Return counter
    	return total;
    }
    
    /**
     * Gets the number comp rates.
     *
     * @return the number comp rates
     */
    public int getNumberCompRates(){
    
    	//Create counter
    	int comp = 0;
    	//Loop over library data structures
    	for(int i=0; i<ds.getNumberLibraryStructures(); i++){
    		//If library data structure has any rates
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			//Loop over the rate data structures
    			for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
    				//If rate has more than one component
    				if(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length>1){
    					//Add number of rate's components to counter
    					comp = comp + ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length;
    				}
    			}
    		}
    	}
    	
    	//Return counter
    	return comp;
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    
        if(ae.getSource()==rateButton){
        	getCurrentState();
        	
        	//Call to initialize rate data structures
        	initializeRateDataStructures();
        	
        	//Loop over number of library data structures
        	for(int i=0; i<ds.getNumberLibraryStructures(); i++){
        	
        		//If this library data structure has a Z list assigned
        		//This is here to prevent ????????
        		if(ds.getLibraryStructureArray()[i].getZList()!=null){

					//Create temp library data structure
        			LibraryDataStructure applds1 = new LibraryDataStructure();
					//Set the structure to the temp CGI structure
					ds.setCurrentLibraryDataStructure(applds1);
					//Set library name CGI var 
					ds.setLibraryName(ds.getLibraryStructureArray()[i].getLibName());
					//Set reaction type string for CGI
					ds.setTypeDatabaseString(getTypeDatabaseString());
 					
 					//Create Vector to hold temp rate data structures
 					Vector apprdsViktor = new Vector();
 					
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
			        	if(Cina.cinaCGIComm.doCGICall("GET RATE LIST", Cina.rateViewerFrame)){
			        		//Loop over rate data structures in temp lib data structure 
							for(int m=0; m<applds1.getRateDataStructures().length; m++){
								//Assign each rate data structure to temp vector
								apprdsViktor.addElement(applds1.getRateDataStructures()[m]);
							}		        	
			        	}
					}
					
					//Trim excess entries from temp rate vector
					apprdsViktor.trimToSize();
					
					//Create an array of rate data structures
					RateDataStructure[] apprdsa = new RateDataStructure[apprdsViktor.size()];

					//Loop over temp rate vector's size
					for(int j=0; j<apprdsViktor.size(); j++){
						//Assign element of temp vector to array
						apprdsa[j] = (RateDataStructure)(apprdsViktor.elementAt(j));
					}
					
					//Assign array of rate data structures to this library data structure
					ds.getLibraryStructureArray()[i].setRateDataStructures(apprdsa);
				}	
        	}
        	
        	//Call this method to set rate id array in rate viewer data structure
        	createRateIDArray();
        	
        	//Set rates cgi var by calling parse rate id array method
        	ds.setRates(parseRateIDArray());
        	
        	//Set rate properties for CGI call
        	ds.setProperties("Reaction String"
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
        		
        	//If we have at least one rate												
			if(!ds.getRates().equals("")){

				//If the get rate info cgi call was successful
	            if(Cina.cinaCGIComm.doCGICall("GET RATE INFO", Cina.rateViewerFrame)){
	
					//Calculate temp and rate data arrays
	            	setTempDataArrays();
	            	setRateDataArrays();
	            	
	            	//Calculate temp and rate range
	            	setInitTempRange();
	            	setInitRateRange();
	            
	            	//Set number of total and comp rates to this data structure 
	            	ds.setNumberTotalRates(getNumberTotalRates());
	            	ds.setNumberCompRates(getNumberCompRates());
	
					//Set the units array to have units for each rateid
	            	setUnitsArray();
	            	
	            	//If plotting interface already exists
	            	if(rateViewerPlotFrame!=null){
	
						//Set plotting interface properties
	            		rateViewerPlotFrame.setFormatPanelState();
	            		rateViewerPlotFrame.setReactionListPanel();
	            		
	            		//Redraw plot and set visible
	            		rateViewerPlotFrame.redrawPlot();
	            		rateViewerPlotFrame.setVisible(true);
	
					//If plotting interface has not yet been opened
	            	}else{
	            		rateViewerPlotFrame = new RateViewerPlotFrame(ds, param);
	            	}
	            }
            
            //If we have no rates tell user
        	}else{
        		String string = "There are no rates matching your selection.";
        		Dialogs.createExceptionDialog(string, this);
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
			//Clear all elements in temp array
           	for(int i=0; i<ds.getViktorArray().length; i++){temp[i].clear();}
           	
           	//Assign cleared array back to data structure
           	ds.setViktorArray(temp);
           	//Repaint ioteop pad to reflect changes
           	rateViewerIsotopePadPanel.repaint();
           	
           	//Loop over check boxes
           	for(int i=0; i<9; i++){checkBoxArray[i].addItemListener(this);}
           	
           	//Disable rate button
           	rateButton.setEnabled(false);
           
        //If source is the submit button of the exit rate viewer dialog
        }else if(ae.getSource()==submitButton){
			if(closeRadioButton.isSelected()){
				setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
				saveDialog.setVisible(false);
				saveDialog.dispose();
         		setVisible(false);
         		ds.initialize();
        		closeAllFrames();
        		dispose();
        		Cina.rateViewerFrame = null;
			//Else if do not close chosen
			}else if(doNotCloseRadioButton.isSelected()){
				setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
				saveDialog.setVisible(false);
				saveDialog.dispose();
			}
		}else if(ae.getSource()==helpButton){
			rateViewerHelpFrame = new HelpFrame("Help on Rate Viewer", "RateViewer");
			rateViewerHelpFrame.setVisible(true);
		}
    } 
    
    /**
     * Sets the units array.
     */
    public void setUnitsArray(){
    
    	String[] units = {"", "reactions/sec", "reactions/sec", "reactions/sec", "cm^3/(mole*s)"
                            , "cm^3/(mole*s)", "cm^3/(mole*s)", "cm^3/(mole*s)"
                            , "cm^6/(mole^2 * s)"};
    
    	//Create output array for units
    	String[] outputArray = new String[ds.getNumberTotalRates()
    										+ ds.getNumberCompRates()];
    
    	//Initialize counter
    	int numberRates = 0;
    
    	//Loop over library structures
    	for(int i=0; i<ds.getNumberLibraryStructures(); i++){
			//If library has any rates
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			//Loop over rates
    			for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
    				//Assign units for total rate
    				outputArray[numberRates] = units[ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getReactionType()];
					//Increment counter
					numberRates++;
					//If rate has more than one component
					if(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length>1){
						//Loop over components
	    				for(int k=0; k<ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length; k++){
	    					//Assign units for component rates
	    					outputArray[numberRates] = units[ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getReactionType()];
	    					//Increment counter
	    					numberRates++;
	    				}	
	    			}
    			}
    		}
    	}
    	//Assign rate units array to data structure
    	ds.setUnitsArray(outputArray);
    }
    
    /**
     * Sets the temp data arrays.
     */
    public void setTempDataArrays(){
    
    	//Loop over libraries
    	for(int i=0; i<ds.getNumberLibraryStructures(); i++){
    		//If library has any rates
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			//Loop over rates
    			for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
    				//Set temp grid to total temp data array 
    				ds.getLibraryStructureArray()[i].getRateDataStructures()[j].setTempDataArrayTotal(ds.getTempGrid());
    				//Create array for temp data for each component
    				double[][] outputArray = new double[ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length][ds.getTempGrid().length];
    				//Loop over components
    				for(int k=0; k<ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length; k++){
    					//Assign temp grid to temp data array for components
    					outputArray[k] = ds.getTempGrid();
    				}
    				//Set temp data array for components
    				ds.getLibraryStructureArray()[i].getRateDataStructures()[j].setTempDataArrayComp(outputArray);
    			}
    		}
    	}
    }
    
    /**
     * Sets the rate data arrays.
     */
    public void setRateDataArrays(){
    
    	//Loop over libraries
    	for(int i=0; i<ds.getNumberLibraryStructures(); i++){
    		//If library has any rates
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			//Loop over rates
    			for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
    				//Create temp array to store temperature data for calculating rates
    				double[] tempArray = ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getTempDataArrayTotal();
    				//Get the number of parameters
    				int numberParameters = ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getNumberParameters();
    				//Create a parameter array for this rate
    				double[] parameters = ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getParameters();
    				//Create an array to hold rate values
    				double[] rateArray = new double[tempArray.length];
    				//Loop over rate array values
    				for(int k=0; k<rateArray.length; k++){
    					//Calculate rate at this temperature
    					rateArray[k] = Cina.cinaMainDataStructure.calcRate(tempArray[k], parameters, numberParameters);
						//If rate is below 1e-100 or equal to zero set to 1e-100
						if(rateArray[k]<1e-100 || rateArray[k]==0){rateArray[k] = 1e-100;}
						//If rate is greater than 1e+100 or equal to zero set to 1e+100
						if(rateArray[k]>1e100 || rateArray[k]==0){rateArray[k] = 1e100;}
    				}
    				
    				//Set rate array to data structure
    				ds.getLibraryStructureArray()[i].getRateDataStructures()[j].setRateDataArrayTotal(rateArray);
    				//Create array to hold rate values for components
    				double[][] outputArray = new double[ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length][ds.getTempGrid().length];
    				//Create array to hold parameters for components
    				double[][] parameterCompArray = ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getParameterCompArray();
    		 		
    		 		//If rate has more than one component
    				if(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length>1){
						//Loop over components
	    				for(int k=0; k<ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length; k++){
							//Loop over temperature grid
	    					for(int l=0; l<ds.getTempGrid().length; l++){
								//Calculate rate at this temperature
	    						outputArray[k][l] = Cina.cinaMainDataStructure.calcRate(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getTempDataArrayComp()[k][l], parameterCompArray[k], 7);
	    						//If rate is below 1e-100 or equal to zero set to 1e-100
	    						if(outputArray[k][l]<1e-100 || outputArray[k][l]==0){outputArray[k][l] = 1e-100;}
								//If rate is greater than 1e+100 or equal to zero set to 1e+100
								if(outputArray[k][l]>1e100 || outputArray[k][l]==0){outputArray[k][l] = 1e100;}
	    					}
	    				}
	    				//Assign rate data arrays for components to data structure
	    				ds.getLibraryStructureArray()[i].getRateDataStructures()[j].setRateDataArrayComp(outputArray);
    				}
    			}
    		}
    	}
    }
    
    /**
     * Sets the init temp range.
     */     	
    public void setInitTempRange(){
    	ds.setTempmin(-2);
    	ds.setTempmax(1);
    }
      
    /**
     * Sets the init rate range.
     */      	
    public void setInitRateRange(){
    	ds.setRatemin(getRatemin());
    	ds.setRatemax(getRatemax());
    }
    
    /**
     * Gets the ratemin.
     *
     * @return the ratemin
     */
    public int getRatemin(){
		
		//Create a variable for rate min that is very big
		double newRatemin = 1e30;
		//Create int var to hold magnitude of rate min
		int rateMin = 0;
		
		//Loop over libraries
		for(int i=0; i<ds.getNumberLibraryStructures(); i++){
    		//If library has any rates
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			//Loop over rates
    			for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
    				//Loop over rate array values
    				for(int k=0; k<ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getRateDataArrayTotal().length; k++){
						//Assign new rate min
						newRatemin = Math.min(newRatemin, ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getRateDataArrayTotal()[k]);
    				}
    			}
    		}
    	}
		
		//Get magnitude of rate
		rateMin = (int)Math.floor(0.434294482*Math.log(newRatemin));
		//Return magnitude of rate
		return rateMin;
	}
	
	/**
	 * Gets the ratemax.
	 *
	 * @return the ratemax
	 */
	public int getRatemax(){
		
		//Create a variable for rate max that is very big
		double newRatemax = 0.0;
		//Create int var to hold magnitude of rate max
		int rateMax = 0;
		//Loop over libraries
		for(int i=0; i<ds.getNumberLibraryStructures(); i++){
    		//If library has any rates
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			//Loop over rates
    			for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
    				//Loop over rate array values
    				for(int k=0; k<ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getRateDataArrayTotal().length; k++){
    					//Assign new rate min
    					newRatemax = Math.max(newRatemax, ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getRateDataArrayTotal()[k]);
    				}
    			}
    		}
    	}
		
		//Get magnitude of rate		
		rateMax = (int)Math.ceil(0.434294482*Math.log(newRatemax));
		//Return magnitude of rate
		return rateMax;
	}
    
    /**
     * Initialize rate data structures.
     */
    public void initializeRateDataStructures(){
    
    	//Loop over libraries
    	for(int i=0; i<ds.getNumberLibraryStructures(); i++){
    		//If library has any rates
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			//Set those rates to null
    			ds.getLibraryStructureArray()[i].setRateDataStructures(null);
    		}
    	}
    }
    
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
        rateViewerIsotopePadPanel.getCurrentState(); 
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
			rateButton.setEnabled(true);
		}else{
			rateButton.setEnabled(false);
		}
        
        //If source is the library combo box
		if(ie.getSource()==libComboBox){
			//Call get current state method for isotope pad
			rateViewerIsotopePadPanel.getCurrentState();
			//Assign library index in data structure
			ds.setLibIndex(libComboBox.getSelectedIndex());
			//Assign this library structure to temp CGI structure
			ds.setCurrentLibraryDataStructure(ds.getLibraryStructureArray()[libComboBox.getSelectedIndex()]);
			ds.setLibraryName((String)libComboBox.getSelectedItem());
			//If you get the isotope list of this library successfully
			if(Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY ISOTOPES", Cina.rateViewerFrame)){
				//Call set current state method for isotope pad
				rateViewerIsotopePadPanel.setCurrentState();
			}
		}
    }
    
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
    	ds.setLibIndex(libComboBox.getSelectedIndex());
		//Assign this library structure to temp CGI structure
		ds.setCurrentLibraryDataStructure(ds.getLibraryStructureArray()[libComboBox.getSelectedIndex()]);	
    	//Create temp library data structure	
    	LibraryDataStructure applds = ds.getCurrentLibraryDataStructure();

		//Loop over library's Z list (list of Z's for that library)
    	for(int i=0; i<applds.getZList().length; i++){
    		//If this Z is in the Z list
	    	if(applds.getZList()[i]==z){
	    		//Loop over maximum number of isotopes per Z
	    		for(int j=0; j<200; j++){
					//If this isotope A value is NOT -1 (-1 is assigned to fill up the array)
					//(This is done in the CGOComm parser)
					if(applds.getIsotopeList()[i][j]!=-1){
			    		//If this A matches the given N value
			    		if((applds.getIsotopeList()[i][j]-applds.getZList()[i])==n){
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
  
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		//Remove all libraries from combo box
		libComboBox.removeAllItems();
		//Remove item listener from combo box
		libComboBox.removeItemListener(this);
		//Create String array to hold the names 
		String[] sourceLibArray = new String[ds.getNumberPublicLibraryDataStructures()
										+ ds.getNumberSharedLibraryDataStructures()
										+ ds.getNumberUserLibraryDataStructures()];
		//Create lib data structure array
		LibraryDataStructure[] outputArray = new LibraryDataStructure[ds.getNumberPublicLibraryDataStructures()
																											+ ds.getNumberSharedLibraryDataStructures()
																											+ ds.getNumberUserLibraryDataStructures()];
		//Create counter
		int sourceLibArrayCounter = 0;
		
		//Loop over public libraries
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
			//Add library to combo box
			libComboBox.addItem(ds.getPublicLibraryDataStructureArray()[i].getLibName());
			//Add lib structure to array
			outputArray[sourceLibArrayCounter] = ds.getPublicLibraryDataStructureArray()[i];
			//Increment counter
			sourceLibArrayCounter++;
		}
		
		//Loop over shared libraries
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
			//Add library to combo box
			libComboBox.addItem(ds.getSharedLibraryDataStructureArray()[i].getLibName());
			//Add lib structure to array
			outputArray[sourceLibArrayCounter] = ds.getSharedLibraryDataStructureArray()[i];
			//Increment counter
			sourceLibArrayCounter++;
		}
		
		//Loop over user libraries
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
			//Add library to combo box
			libComboBox.addItem(ds.getUserLibraryDataStructureArray()[i].getLibName());
			//Add lib structure to array
			outputArray[sourceLibArrayCounter] = ds.getUserLibraryDataStructureArray()[i];
			//Increment counter
			sourceLibArrayCounter++;	
		}
		
		//Assign lib array to data structure
		ds.setLibraryStructureArray(outputArray);
		//Assign number of libraries to data structure
		ds.setNumberLibraryStructures(outputArray.length);
		
		//Set library combo box to current library index
		libComboBox.setSelectedIndex(ds.getLibIndex());
		//Add item listener back to combo box
		libComboBox.addItemListener(this);
		
		//Create a temp lib data structure holding lib data structure corresponding to current library index
		LibraryDataStructure applds = ds.getLibraryStructureArray()[ds.getLibIndex()];
		//Set current library to temp library
		ds.setCurrentLibraryDataStructure(applds);
		
		//Creat local vector array for isotope points
		Vector[] viktorArray = new Vector[outputArray.length];
		
		//Create new empty vectors for temp vector array
		for(int i=0; i<outputArray.length; i++){viktorArray[i] = new Vector();}
		
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
		rateViewerIsotopePadPanel.initialize();
		
		resetChart();

	}
	
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
	
	/**
	 * Close all frames.
	 */
 	public void closeAllFrames(){
		if(rateViewerPlotFrame!=null){
			if(rateViewerPlotFrame.rateViewerTableFrame!=null){
				rateViewerPlotFrame.rateViewerTableFrame.setVisible(false);
				rateViewerPlotFrame.rateViewerTableFrame.dispose();
			}
			if(rateViewerPlotFrame.rateViewerInfoFrame!=null){
				rateViewerPlotFrame.rateViewerInfoFrame.setVisible(false);
				rateViewerPlotFrame.rateViewerInfoFrame.dispose();
			}
			if(rateViewerPlotFrame.rateViewerRateFrame!=null){
				rateViewerPlotFrame.rateViewerRateFrame.setVisible(false);
				rateViewerPlotFrame.rateViewerRateFrame.dispose();
			}
			rateViewerPlotFrame.setVisible(false);
			rateViewerPlotFrame.dispose();
		}
		
		if(rateViewerHelpFrame!=null){
			rateViewerHelpFrame.setVisible(false);
			rateViewerHelpFrame.dispose();
		}
	}
	
	/**
	 * Reset chart.
	 */
    public void resetChart(){
        
        //Create local vars to get values of n and z max
        double s1 = zmaxSlider.getValue();
        double s2 = nmaxSlider.getValue();

		//Set z and n max for isotope pad
        rateViewerIsotopePadPanel.setZmax(zmaxSlider.getValue());
        rateViewerIsotopePadPanel.setNmax(nmaxSlider.getValue());
        
        //Reset values for isotope pad
        rateViewerIsotopePadPanel.setChartSize();
        rateViewerIsotopePadPanel.setCurrentState();
		rateViewerIsotopePadPanel.validate();
		rateViewerIsotopePadPanel.setPreferredSize(rateViewerIsotopePadPanel.getSize());	
		rateViewerIsotopePadPanel.revalidate();
        
        sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(rateViewerIsotopePadPanel.getHeight());
    	
    	sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
        
        ZRuler.setPreferredWidth((int)rateViewerIsotopePadPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)rateViewerIsotopePadPanel.getSize().getHeight());
       	
		ZRuler.setCurrentState(rateViewerIsotopePadPanel.getZmax()
								, rateViewerIsotopePadPanel.getNmax()
								, rateViewerIsotopePadPanel.getMouseX()
								, rateViewerIsotopePadPanel.getMouseY()
								, rateViewerIsotopePadPanel.getXoffset()
								, rateViewerIsotopePadPanel.getYoffset()
								, rateViewerIsotopePadPanel.getCrosshairsOn());						
    	NRuler.setCurrentState(rateViewerIsotopePadPanel.getZmax()
								, rateViewerIsotopePadPanel.getNmax()
								, rateViewerIsotopePadPanel.getMouseX()
								, rateViewerIsotopePadPanel.getMouseY()
								, rateViewerIsotopePadPanel.getXoffset()
								, rateViewerIsotopePadPanel.getYoffset()
								, rateViewerIsotopePadPanel.getCrosshairsOn());
    }
    
    /**
     * Sets the rate button enabled.
     *
     * @param state the new rate button enabled
     */
    public void setRateButtonEnabled(boolean state){rateButton.setEnabled(state);}
    
    /**
     * Sets the added rate.
     *
     * @param addRateArray the add rate array
     * @param addTempArray the add temp array
     * @param addRateName the add rate name
     */
    public void setAddedRate(double[] addRateArray, double[] addTempArray, String addRateName){
    	
    	ds.setAddRateArray(addRateArray);
    	ds.setAddTempArray(addTempArray);
    	ds.setRateAdded(true);
    	ds.setAddRateName(addRateName);
    	
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
	public void windowClosing(WindowEvent we) {   
		if(we.getSource()==this){
			createSaveDialog();
    	}else if(we.getSource()==saveDialog){
    		saveDialog.setVisible(false);
    		saveDialog.dispose();
    	}
    } 
    
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
    	GridBagConstraints gbc = new GridBagConstraints();
    	saveDialog = new JDialog(this, "Exit Rate Viewer?", true);
    	saveDialog.setSize(355, 158);
    	saveDialog.getContentPane().setLayout(new GridBagLayout());
  		saveDialog.setLocationRelativeTo(this);
  
    	ButtonGroup choiceButtonGroup = new ButtonGroup();
    	closeRadioButton = new JRadioButton("Exit the Rate Viewer.", true);
   		closeRadioButton.setFont(Fonts.textFont);
    	doNotCloseRadioButton = new JRadioButton("Return to the Rate Viewer.", false);
		doNotCloseRadioButton.setFont(Fonts.textFont);
		choiceButtonGroup.add(closeRadioButton);
		choiceButtonGroup.add(doNotCloseRadioButton);
		
		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
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
		saveDialog.setVisible(true);
    }
} 