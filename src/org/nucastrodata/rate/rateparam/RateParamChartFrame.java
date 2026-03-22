package org.nucastrodata.rate.rateparam;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import org.nucastrodata.datastructure.feature.RateParamDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Corner;
import org.nucastrodata.Fonts;
import org.nucastrodata.IsotopeRuler;
import org.nucastrodata.rate.rateparam.RateParamIsotopePadPanel;


/**
 * The Class RateParamChartFrame.
 */
public class RateParamChartFrame extends JFrame implements ActionListener, ChangeListener{

    /** The rate param isotope pad panel. */
    static RateParamIsotopePadPanel rateParamIsotopePadPanel;

    /** The rate sp. */
    JScrollPane sp, rateSP;
  
    /** The submit button. */
    JButton clearButton, addButton, removeButton, submitButton;
    
    /** The hopper panel. */
    JPanel botPanel, botPanelB, botPanelC, rightPanel, buttonPanel, hopperPanel;
    
    /** The rate field. */
    JTextField zmaxField, nmaxField, aField, zField, elementField, rateField;
    
    /** The nmax slider. */
    JSlider zmaxSlider, nmaxSlider;
    
    /** The c. */
    Container c;
    
    /** The choice button group. */
    ButtonGroup choiceButtonGroup;
	
	/** The save and close radio button. */
	JRadioButton saveAndCloseRadioButton;
	
	/** The close radio button. */
	JRadioButton closeRadioButton;

	/** The do not close radio button. */
	JRadioButton doNotCloseRadioButton;
	
    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The save dialog. */
    JDialog saveDialog;
    
    /** The rate list. */
    JList rateList;
    
    /** The rate list model. */
    DefaultListModel rateListModel;
    
    /** The rate label. */
    JLabel topLabel, rateLabel;

	/** The N ruler. */
	IsotopeRuler ZRuler, NRuler;

	/** The ds. */
	private RateParamDataStructure ds;

    /**
     * Instantiates a new rate param chart frame.
     *
     * @param ds the ds
     */
    public RateParamChartFrame(RateParamDataStructure ds){
    	
    	this.ds = ds;

		setSize(825, 625);
		setTitle("Select Reaction from Nuclide Chart");	

		c = getContentPane();

		gbc = new GridBagConstraints();
		
	    c.setLayout(new BorderLayout());

		rightPanel = new JPanel(new GridBagLayout());

		topLabel = new JLabel("Available Rates :");
		topLabel.setFont(Fonts.textFont);

		clearButton = new JButton("Clear All Selections");
        clearButton.setFont(Fonts.buttonFont);
        clearButton.addActionListener(this);

		addButton = new JButton("Add Selected Rate");
		addButton.setFont(Fonts.buttonFont);
		addButton.addActionListener(this);
		
		removeButton = new JButton("Remove Selected Rate");
		removeButton.setFont(Fonts.buttonFont);
		removeButton.addActionListener(this);

		submitButton = new JButton("Submit Selected Rate");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);

		rateListModel = new DefaultListModel();
		
		rateList = new JList(rateListModel);
		rateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		rateSP = new JScrollPane(rateList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		rateSP.setPreferredSize(new Dimension(50, 200));

		rateField = new JTextField(20);
		rateField.setEditable(false);

		rateLabel = new JLabel("Selected Rate: ");
		rateLabel.setFont(Fonts.textFont);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		rightPanel.add(rateSP, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		rightPanel.add(rateLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		rightPanel.add(rateField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		
		rightPanel.add(addButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		
		rightPanel.add(removeButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		
		rightPanel.add(clearButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		
		rightPanel.add(submitButton, gbc);
		
        botPanel = new JPanel();
        botPanel.setLayout(new GridBagLayout());

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
        
        zmaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 85, rateParamIsotopePadPanel.zmax);
		zmaxSlider.addChangeListener(this);
		zmaxField.setText(Integer.toString(rateParamIsotopePadPanel.zmax));

		zmaxField.setEditable(false);

		botPanelB.add(zmaxSlider);
		botPanelB.add(zmaxField);

        botPanelC = new JPanel();
        JLabel nmaxLabel = new JLabel("Nmax",JLabel.LEFT);
        nmaxLabel.setFont(Fonts.textFont);
        botPanelC.add(nmaxLabel);

        nmaxField = new JTextField();
        nmaxField.setColumns(3);
        nmaxField.setFont(Fonts.textFont);
        nmaxField.setText(Integer.toString(rateParamIsotopePadPanel.nmax));
        
        nmaxField.setEditable(false);
        
        nmaxSlider = new JSlider(JSlider.HORIZONTAL, 1, 193, rateParamIsotopePadPanel.nmax);
        nmaxSlider.addChangeListener(this);
        
        botPanelC.add(nmaxSlider);
        
        botPanelC.add(nmaxField);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(1, 1, 1, 1);
        botPanel.add(botPanelB, gbc);
        
        gbc.gridx = 2;
        botPanel.add(botPanelC, gbc);
        
        gbc.gridx = 3;
        botPanel.add(elementLabel, gbc);
        
        gbc.gridx = 4;
        botPanel.add(elementField, gbc);
        
        gbc.gridx = 5;
        botPanel.add(zLabel, gbc);
        
        gbc.gridx = 6;
        botPanel.add(zField, gbc);
        
        gbc.gridx = 7;
        botPanel.add(aLabel, gbc);
        
        gbc.gridx = 8;
        botPanel.add(aField, gbc);

		gbc.fill = GridBagConstraints.NONE;

        rateParamIsotopePadPanel = new RateParamIsotopePadPanel(ds);
        
        rateParamIsotopePadPanel.width = (int)(rateParamIsotopePadPanel.boxWidth*(rateParamIsotopePadPanel.nmax+1));
        rateParamIsotopePadPanel.height = (int)(rateParamIsotopePadPanel.boxHeight*(rateParamIsotopePadPanel.zmax+1));
        
        rateParamIsotopePadPanel.setSize(rateParamIsotopePadPanel.width+2*rateParamIsotopePadPanel.xoffset,rateParamIsotopePadPanel.height+2*rateParamIsotopePadPanel.yoffset);
        
        rateParamIsotopePadPanel.xmax = (int)(rateParamIsotopePadPanel.xoffset + rateParamIsotopePadPanel.width);
        rateParamIsotopePadPanel.ymax = (int)(rateParamIsotopePadPanel.yoffset + rateParamIsotopePadPanel.height);

		rateParamIsotopePadPanel.setPreferredSize(rateParamIsotopePadPanel.getSize());
		
        sp = new JScrollPane(rateParamIsotopePadPanel);
		
		JViewport vp = new JViewport();
		
		vp.setView(rateParamIsotopePadPanel);
		
		sp.setViewport(vp);
		
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)rateParamIsotopePadPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)rateParamIsotopePadPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(rateParamIsotopePadPanel.zmax
								, rateParamIsotopePadPanel.nmax
								, rateParamIsotopePadPanel.mouseX
								, rateParamIsotopePadPanel.mouseY
								, rateParamIsotopePadPanel.xoffset
								, rateParamIsotopePadPanel.yoffset
								, rateParamIsotopePadPanel.crosshairsOn);
								
    	NRuler.setCurrentState(rateParamIsotopePadPanel.zmax
								, rateParamIsotopePadPanel.nmax
								, rateParamIsotopePadPanel.mouseX
								, rateParamIsotopePadPanel.mouseY
								, rateParamIsotopePadPanel.xoffset
								, rateParamIsotopePadPanel.yoffset
								, rateParamIsotopePadPanel.crosshairsOn);
	
		sp.setColumnHeaderView(ZRuler);
        sp.setRowHeaderView(NRuler);
		
		sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.LOWER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
		sp.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new Corner());
		
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		c.add(sp, BorderLayout.CENTER);
		c.add(rightPanel, BorderLayout.EAST);
		c.add(botPanel, BorderLayout.SOUTH);
		
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());

		

		validate();
		
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
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	
        if(ae.getSource()==addButton){
         
         	if(rateList.getSelectedValue()!=null){
         
        		rateField.setText((String)rateList.getSelectedValue());
        	
        	}
        		
			ds.setLibrary("ReaclibV2.2");
			ds.setIsotope(getIsotopes());
			ds.setTypeDatabase("");
	        
	        if(Cina.cinaCGIComm.doCGICall("GET RATE LIST", this)){
	        
	        	//Cina.rateManFrame.rateManInvestRate2ChartPanel.setRateList();
	        
	        }
	        
			String newReaction = (String)rateList.getSelectedValue();

			String mainLibrary = "ReaclibV2.2";
			
			String tempString = new String(getRateIDString("Shared", mainLibrary, newReaction));

			ds.getRateDataStructure().setReactionID(tempString);
			
			ds.getRateDataStructure().setReactionString(newReaction);
			
			ds.getRateDataStructure().parseIDForDecayType();
			
        }else if(ae.getSource()==removeButton){
        	
        	rateField.setText("");
         
      	}else if(ae.getSource()==clearButton){
           	
           	ds.setIsotopeViktor(new Vector());
           	
 			ds.setRateViktor(new Vector());
           	
           	rateParamIsotopePadPanel.setCurrentState();
           	
           	rateParamIsotopePadPanel.repaint();
           	
           	rateListModel.clear();
           	
           	rateField.setText("");
           	
           	setCurrentState();
           	
		}else if(ae.getSource()==submitButton){
		
			String reactionString = rateField.getText();
			
			if(reactionString.indexOf("[")!=-1){
				ds.setReaction(reactionString.substring(0, reactionString.indexOf("[")-1));
			}else{
				ds.setReaction(reactionString);
			}
			
			ds.setReaction_type(ds.getRateDataStructure().getDecay());
		
			if(Cina.cinaCGIComm.doCGICall("CHECK REACTION", this)){
		
				Vector vector = parseReactionStringInt(ds.getRateDataStructure().getReactionStringInt());
				ds.getRateDataStructure().setIsoIn((Point[])vector.elementAt(0));
				ds.getRateDataStructure().setIsoOut((Point[])vector.elementAt(1));
				
				String idString = ds.getRateDataStructure().getReactionID();
				ds.getRateDataStructure().setReactionType(Integer.valueOf(idString.substring(0, 2)).intValue());
				ds.getRateDataStructure().setReactionNotes(Cina.rateParamFrame.rateParamIDPanel.notesTextArea.getText());
				
				Cina.rateParamFrame.rateParamIDPanel.setCurrentState();
				
				dispose();
				setVisible(false);
				
			}
		
		}
		
    } 
    
    /**
     * Parses the reaction string int.
     *
     * @param string the string
     * @return the vector
     */
    public Vector parseReactionStringInt(String string){
    
    	Vector vector = new Vector();
    	
    	StringTokenizer stArrow = new StringTokenizer(string, "-->");
    	
    	Point[] isoIn = new Point[3];
		Point[] isoOut = new Point[4];
    	
    	for(int i=0; i<2; i++){
    	
    		String currentTokenArrow = stArrow.nextToken().trim();
    		StringTokenizer stPlus = new StringTokenizer(currentTokenArrow, "+");
    		
    		int numberPlus = stPlus.countTokens();
    		
    		for(int j=0; j<numberPlus; j++){
    		
    			String currentTokenPlus = stPlus.nextToken().trim();
    			StringTokenizer stComma = new StringTokenizer(currentTokenPlus, ",");
    			
    			if(i==0){
    			
    				isoIn[j] = new Point(Integer.valueOf(stComma.nextToken().trim()).intValue()
    										, Integer.valueOf(stComma.nextToken().trim()).intValue());
    										
    			}else{
    			
    				isoOut[j] = new Point(Integer.valueOf(stComma.nextToken().trim()).intValue()
    										, Integer.valueOf(stComma.nextToken().trim()).intValue());
    										
    			}
    		
    		}
    	
    	}
    	
    	vector.addElement(isoIn);
    	vector.addElement(isoOut);
    	return vector;
    	
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
		
		for(int j=0; j<ds.getCurrentLibraryDataStructure().getRateDataStructures().length; j++){
		
			if(reactionString.equals(ds.getCurrentLibraryDataStructure().getRateDataStructures()[j].getReactionString())
				&& decay.equals(ds.getCurrentLibraryDataStructure().getRateDataStructures()[j].getDecay())){
			
				rateID = ds.getCurrentLibraryDataStructure().getRateDataStructures()[j].getReactionID();
			
			}
		
		}
					
		return rateID;	
		
	}
    
    /**
     * Gets the isotopes.
     *
     * @return the isotopes
     */
    public String getIsotopes(){
    
    	Vector viktor = ds.getIsotopeViktor();
    	
    	String string = "";
    	
    	for(int i=0; i<viktor.size(); i++){
    	
    		Point point = (Point)viktor.elementAt(i);
    	
    		String Z = String.valueOf((int)point.getX());
    	
    		String A = String.valueOf((int)point.getX() + (int)point.getY());
    	
    		if(i!=(viktor.size()-1)){
    	
    			string = string + Z + "," + A + "\u0009";
    		
    		}else{
    		
    			string = string + Z + "," + A;
    		
    		}
    	
    	}
    	
    	return string;
    
    }
	
    /**
     * Reset chart.
     */
    public void resetChart(){
        
        double s1 = zmaxSlider.getValue();
        double s2 = nmaxSlider.getValue();

        rateParamIsotopePadPanel.zmax = zmaxSlider.getValue();
        rateParamIsotopePadPanel.nmax = nmaxSlider.getValue();
        
        rateParamIsotopePadPanel.width = (int)(rateParamIsotopePadPanel.boxWidth*(rateParamIsotopePadPanel.nmax+1));
        rateParamIsotopePadPanel.height = (int)(rateParamIsotopePadPanel.boxHeight*(rateParamIsotopePadPanel.zmax+1));
        
        rateParamIsotopePadPanel.setSize(rateParamIsotopePadPanel.width+2*rateParamIsotopePadPanel.xoffset,rateParamIsotopePadPanel.height+2*rateParamIsotopePadPanel.yoffset);
        
        rateParamIsotopePadPanel.xmax = (int)(rateParamIsotopePadPanel.xoffset + rateParamIsotopePadPanel.width);
        rateParamIsotopePadPanel.ymax = (int)(rateParamIsotopePadPanel.yoffset + rateParamIsotopePadPanel.height);
        
        rateParamIsotopePadPanel.setCurrentState();

		rateParamIsotopePadPanel.setPreferredSize(rateParamIsotopePadPanel.getSize());
		
		rateParamIsotopePadPanel.revalidate();
        
        sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(rateParamIsotopePadPanel.getHeight());
    	
    	sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
        
        ZRuler.setPreferredWidth((int)rateParamIsotopePadPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)rateParamIsotopePadPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(rateParamIsotopePadPanel.zmax
								, rateParamIsotopePadPanel.nmax
								, rateParamIsotopePadPanel.mouseX
								, rateParamIsotopePadPanel.mouseY
								, rateParamIsotopePadPanel.xoffset
								, rateParamIsotopePadPanel.yoffset
								, rateParamIsotopePadPanel.crosshairsOn);
								
    	NRuler.setCurrentState(rateParamIsotopePadPanel.zmax
								, rateParamIsotopePadPanel.nmax
								, rateParamIsotopePadPanel.mouseX
								, rateParamIsotopePadPanel.mouseY
								, rateParamIsotopePadPanel.xoffset
								, rateParamIsotopePadPanel.yoffset
								, rateParamIsotopePadPanel.crosshairsOn);
			
	}
    
    /**
     * Sets the rate list.
     */
    public void setRateList(){
    
    	rateListModel.clear();
    
    	for(int i=0; i<ds.getCurrentLibraryDataStructure().getRateDataStructures().length; i++){
    	
    		String string = "";
    	
    		if(ds.getCurrentLibraryDataStructure().getRateDataStructures()[i].getDecay().equals("")){
    		
    			rateListModel.addElement(ds.getCurrentLibraryDataStructure().getRateDataStructures()[i].getReactionString());

    		}else{
    		
    			string = ds.getCurrentLibraryDataStructure().getRateDataStructures()[i].getReactionString()
    						+ " ["
    						+ ds.getCurrentLibraryDataStructure().getRateDataStructures()[i].getDecay()
    						+ "]";
    						
    			rateListModel.addElement(string);
    		
    		}
    		
    	}
    
    }
    
    /**
     * Gets the good rate id list.
     *
     * @return the good rate id list
     */
    public String getGoodRateIDList(){
	
		String string = "";
		
		Vector rateIDVector = new Vector();
		
		String[] tempArray = ds.getRateIDs();
		
		for(int i=0; i<tempArray.length; i++){
		
			StringTokenizer st09 = new StringTokenizer(tempArray[i], "\u0009");
		
			String firstPart = st09.nextToken();
			String secondPart = st09.nextToken();
		
			if(st09.nextToken().equals("EXIST=YES")){
			
				rateIDVector.addElement(firstPart + "\u0009" + secondPart);	

			}
		
		}

		rateIDVector.trimToSize();

		for(int i=0; i<rateIDVector.size(); i++){
		
			if(i==0){
			
				string += (String)rateIDVector.elementAt(i);
			
			}else{
				
				string += "\n" + (String)rateIDVector.elementAt(i);
			
			}
		
		}

		RateDataStructure[] apprdsa = new RateDataStructure[rateIDVector.size()];

		for(int i=0; i<rateIDVector.size(); i++){
		
			apprdsa[i] = new RateDataStructure();
			apprdsa[i].setReactionID((String)rateIDVector.elementAt(i));
		
		}

		ds.setRateDataStructureArray(apprdsa);

		return string;
	
	}
	
	/**
	 * Gets the rate id list.
	 *
	 * @return the rate id list
	 */
	public String getRateIDList(){
	
		String string = "";
	
		String currentRateID = ds.getRateDataStructure().getReactionID();
		
		String firstPart = currentRateID.substring(0,8);

		String secondPart = currentRateID.substring(currentRateID.indexOf("\u0009"));

		String[] libraryNameArray = new String[ds.getNumberPublicLibraryDataStructures()
												+ ds.getNumberSharedLibraryDataStructures()
												+ ds.getNumberUserLibraryDataStructures()];
		int counter = 0;
		
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
		
			libraryNameArray[counter] = ds.getPublicLibraryDataStructureArray()[i].getLibName();
			
			counter++;
		
		}	
		
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
		
			libraryNameArray[counter] = ds.getSharedLibraryDataStructureArray()[i].getLibName();
			
			counter++;
		
		}									
		
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
		
			libraryNameArray[counter] = ds.getUserLibraryDataStructureArray()[i].getLibName();
			
			counter++;
		
		}
		
		for(int i=0; i<libraryNameArray.length; i++){
			
			if(i==0){
			
				string += firstPart + libraryNameArray[i] + secondPart;
			
			}else{
			
				string += "\n" + firstPart + libraryNameArray[i] + secondPart;
			
			}
			
		}
	
		return string;
		
	}
    
    /**
     * Gets the rate list vector.
     *
     * @return the rate list vector
     */
    public Vector getRateListVector(){
    	
    	Vector viktor = new Vector();
    
    	for(int i=0; i<rateListModel.size(); i++){
    	
    		viktor.addElement(rateListModel.elementAt(i));
    	
    	}
    	
    	return viktor;
    
    }
    
    /**
     * Sets the rate list model.
     */
    public void setRateListModel(){
    
    	Vector viktor = ds.getChartRateListVector();
    	
    	for(int i=0; i<viktor.size(); i++){
    	
    		rateListModel.addElement((String)viktor.elementAt(i));
    	
    	}
    
    }
    
    /**
     * Gets the current state.
     *
     * @return the current state
     */
    public void getCurrentState(){
    
    	ds.setCurrentRateString(rateField.getText());
    	ds.setChartRateListVector(getRateListVector());
    
    }
    
    /**
     * Sets the current state.
     */
    public void setCurrentState(){
    
    	rateField.setText(ds.getCurrentRateString());
    	setRateListModel();
    	rateList.setSelectedValue(rateField.getText(), true);
    	rateParamIsotopePadPanel.initialize();
    	
    	resetChart();
    
    }
    
    /**
     * Check rate field.
     *
     * @return true, if successful
     */
    public boolean checkRateField(){
	
		boolean goodRate = false;
	
		if(!rateField.getText().equals("")){
		
			goodRate = true;
		
		}
	
		return goodRate;
	
	}
    
} 