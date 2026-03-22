package org.nucastrodata.rate.rateman;

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
import org.nucastrodata.datastructure.feature.RateManDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;


import org.nucastrodata.Cina;
import org.nucastrodata.Corner;
import org.nucastrodata.Fonts;
import org.nucastrodata.IsotopeRuler;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rateman.RateManInvestRateIsotopePadPanel;


/**
 * The Class RateManInvestRate2ChartPanel.
 */
public class RateManInvestRate2ChartPanel extends WizardPanel implements ActionListener, ChangeListener{ 
	
    /** The rate man invest rate isotope pad panel. */
    private RateManInvestRateIsotopePadPanel rateManInvestRateIsotopePadPanel;							
    
    /** The remove button. */
    private JButton clearButton, addButton, removeButton;
    
    /** The sp. */
    private JScrollPane sp;
    
    /** The rate field. */
    protected JTextField zmaxField, nmaxField, aField, zField, elementField, rateField;
    
    /** The nmax slider. */
    private JSlider zmaxSlider, nmaxSlider;
    
    /** The rate list. */
    private JList rateList;
    
    /** The rate list model. */
    protected DefaultListModel rateListModel;
	
	/** The N ruler. */
	protected IsotopeRuler ZRuler, NRuler;
	
	/** The ds. */
	private RateManDataStructure ds;

    /**
     * Instantiates a new rate man invest rate2 chart panel.
     *
     * @param ds the ds
     */
    public RateManInvestRate2ChartPanel(RateManDataStructure ds){
    	
    	this.ds = ds;
    	
    	//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(6);
		Cina.rateManFrame.setCurrentPanelIndex(2);

		GridBagConstraints gbc = new GridBagConstraints();
		
		addWizardPanel("Rate Manager", "Rate Locator", "2", "3");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel rightPanel = new JPanel(new GridBagLayout());

		JLabel topLabel = new JLabel("Available Rates :");
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

		rateListModel = new DefaultListModel();
		
		rateList = new JList(rateListModel);
		rateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane rateSP = new JScrollPane(rateList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		rateSP.setPreferredSize(new Dimension(50, 200));

		rateField = new JTextField(20);
		rateField.setEditable(false);

		JLabel rateLabel = new JLabel("Selected Rate: ");
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
		
        JPanel botPanel = new JPanel();
        botPanel.setLayout(new GridBagLayout());

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

		rateManInvestRateIsotopePadPanel = new RateManInvestRateIsotopePadPanel(ds);

        zmaxField = new JTextField();
        zmaxField.setColumns(3);
        zmaxField.setFont(Fonts.textFont);
        zmaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 85, rateManInvestRateIsotopePadPanel.zmax);
		zmaxSlider.addChangeListener(this);
		zmaxField.setText(Integer.toString(rateManInvestRateIsotopePadPanel.zmax));
		zmaxField.setEditable(false);

		botPanelB.add(zmaxSlider);
		botPanelB.add(zmaxField);

        JPanel botPanelC = new JPanel();
        JLabel nmaxLabel = new JLabel("Nmax",JLabel.LEFT);
        nmaxLabel.setFont(Fonts.textFont);
        botPanelC.add(nmaxLabel);

        nmaxField = new JTextField();
        nmaxField.setColumns(3);
        nmaxField.setFont(Fonts.textFont);
        nmaxField.setText(Integer.toString(rateManInvestRateIsotopePadPanel.nmax));
        nmaxField.setEditable(false);
        nmaxSlider = new JSlider(JSlider.HORIZONTAL, 1, 193, rateManInvestRateIsotopePadPanel.nmax);
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
        
        rateManInvestRateIsotopePadPanel.width = (int)(rateManInvestRateIsotopePadPanel.boxWidth*(rateManInvestRateIsotopePadPanel.nmax+1));
        rateManInvestRateIsotopePadPanel.height = (int)(rateManInvestRateIsotopePadPanel.boxHeight*(rateManInvestRateIsotopePadPanel.zmax+1));
        
        rateManInvestRateIsotopePadPanel.setSize(rateManInvestRateIsotopePadPanel.width+2*rateManInvestRateIsotopePadPanel.xoffset,rateManInvestRateIsotopePadPanel.height+2*rateManInvestRateIsotopePadPanel.yoffset);
        
        rateManInvestRateIsotopePadPanel.xmax = (int)(rateManInvestRateIsotopePadPanel.xoffset + rateManInvestRateIsotopePadPanel.width);
        rateManInvestRateIsotopePadPanel.ymax = (int)(rateManInvestRateIsotopePadPanel.yoffset + rateManInvestRateIsotopePadPanel.height);

		rateManInvestRateIsotopePadPanel.setPreferredSize(rateManInvestRateIsotopePadPanel.getSize());
		
        sp = new JScrollPane(rateManInvestRateIsotopePadPanel);
		
		JViewport vp = new JViewport();
		
		vp.setView(rateManInvestRateIsotopePadPanel);
		
		sp.setViewport(vp);
		
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)rateManInvestRateIsotopePadPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)rateManInvestRateIsotopePadPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(rateManInvestRateIsotopePadPanel.zmax
								, rateManInvestRateIsotopePadPanel.nmax
								, rateManInvestRateIsotopePadPanel.mouseX
								, rateManInvestRateIsotopePadPanel.mouseY
								, rateManInvestRateIsotopePadPanel.xoffset
								, rateManInvestRateIsotopePadPanel.yoffset
								, rateManInvestRateIsotopePadPanel.crosshairsOn);
								
    	NRuler.setCurrentState(rateManInvestRateIsotopePadPanel.zmax
								, rateManInvestRateIsotopePadPanel.nmax
								, rateManInvestRateIsotopePadPanel.mouseX
								, rateManInvestRateIsotopePadPanel.mouseY
								, rateManInvestRateIsotopePadPanel.xoffset
								, rateManInvestRateIsotopePadPanel.yoffset
								, rateManInvestRateIsotopePadPanel.crosshairsOn);
	
		sp.setColumnHeaderView(ZRuler);
        sp.setRowHeaderView(NRuler);
		
		sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.LOWER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
		sp.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new Corner());
		
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		mainPanel.add(sp, BorderLayout.CENTER);
		mainPanel.add(rightPanel, BorderLayout.EAST);
		mainPanel.add(botPanel, BorderLayout.SOUTH);
		
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());

        add(mainPanel, BorderLayout.CENTER);

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
			ds.setIsotope(Cina.rateManFrame.rateManInvestRate2ChartPanel.getIsotopes());
			ds.setTypeDatabase("");
	        
	        if(Cina.cinaCGIComm.doCGICall("GET RATE LIST", Cina.rateManFrame)){
	        
	        	//Cina.rateManFrame.rateManInvestRate2ChartPanel.setRateList();
	        
	        }
	        
			String newReaction = (String)rateList.getSelectedValue();

			String mainLibrary = "ReaclibV2.2";
			
			String tempString = new String(getRateIDString("Shared", mainLibrary, newReaction));

			ds.getInvestRateDataStructure().setReactionID(tempString);
			
			ds.getInvestRateDataStructure().setReactionString(newReaction);
			
			ds.getInvestRateDataStructure().parseIDForDecayType();
			
        }else if(ae.getSource()==removeButton){
        	
        	rateField.setText("");
         
      	}else if(ae.getSource()==clearButton){
           	
           	ds.setIsotopeViktor(new Vector());
           	
 			ds.setRateViktor(new Vector());
           	
           	rateManInvestRateIsotopePadPanel.setCurrentState();
           	
           	rateManInvestRateIsotopePadPanel.repaint();
           	
           	rateListModel.clear();
           	
           	rateField.setText("");
           	
           	setCurrentState();
           	
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

        rateManInvestRateIsotopePadPanel.zmax = zmaxSlider.getValue();
        rateManInvestRateIsotopePadPanel.nmax = nmaxSlider.getValue();
        
        rateManInvestRateIsotopePadPanel.width = (int)(rateManInvestRateIsotopePadPanel.boxWidth*(rateManInvestRateIsotopePadPanel.nmax+1));
        rateManInvestRateIsotopePadPanel.height = (int)(rateManInvestRateIsotopePadPanel.boxHeight*(rateManInvestRateIsotopePadPanel.zmax+1));
        
        rateManInvestRateIsotopePadPanel.setSize(rateManInvestRateIsotopePadPanel.width+2*rateManInvestRateIsotopePadPanel.xoffset,rateManInvestRateIsotopePadPanel.height+2*rateManInvestRateIsotopePadPanel.yoffset);
        
        rateManInvestRateIsotopePadPanel.xmax = (int)(rateManInvestRateIsotopePadPanel.xoffset + rateManInvestRateIsotopePadPanel.width);
        rateManInvestRateIsotopePadPanel.ymax = (int)(rateManInvestRateIsotopePadPanel.yoffset + rateManInvestRateIsotopePadPanel.height);
        
        rateManInvestRateIsotopePadPanel.setCurrentState();

		rateManInvestRateIsotopePadPanel.setPreferredSize(rateManInvestRateIsotopePadPanel.getSize());
		
		rateManInvestRateIsotopePadPanel.revalidate();
        
        sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(rateManInvestRateIsotopePadPanel.getHeight());
    	
    	sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
        
        ZRuler.setPreferredWidth((int)rateManInvestRateIsotopePadPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)rateManInvestRateIsotopePadPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(rateManInvestRateIsotopePadPanel.zmax
								, rateManInvestRateIsotopePadPanel.nmax
								, rateManInvestRateIsotopePadPanel.mouseX
								, rateManInvestRateIsotopePadPanel.mouseY
								, rateManInvestRateIsotopePadPanel.xoffset
								, rateManInvestRateIsotopePadPanel.yoffset
								, rateManInvestRateIsotopePadPanel.crosshairsOn);
								
    	NRuler.setCurrentState(rateManInvestRateIsotopePadPanel.zmax
								, rateManInvestRateIsotopePadPanel.nmax
								, rateManInvestRateIsotopePadPanel.mouseX
								, rateManInvestRateIsotopePadPanel.mouseY
								, rateManInvestRateIsotopePadPanel.xoffset
								, rateManInvestRateIsotopePadPanel.yoffset
								, rateManInvestRateIsotopePadPanel.crosshairsOn);
			
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
		
		String[] tempArray = ds.getInvestRateIDs();
		
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

		ds.setInvestRateDataStructureArray(apprdsa);

		return string;
	
	}
	
	/**
	 * Gets the rate id list.
	 *
	 * @return the rate id list
	 */
	public String getRateIDList(){
	
		String string = "";
	
		String currentRateID = ds.getInvestRateDataStructure().getReactionID();
		
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
    
    	Vector viktor = ds.getInvestRateChartRateListVector();
    	
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
    
    	ds.setCurrentRateStringInvest(rateField.getText());
    	ds.setInvestRateChartRateListVector(getRateListVector());
    
    }
    
    /**
     * Sets the current state.
     */
    public void setCurrentState(){
    
    	rateField.setText(ds.getCurrentRateStringInvest());
    	setRateListModel();
    	rateList.setSelectedValue(rateField.getText(), true);
    	rateManInvestRateIsotopePadPanel.initialize();
    	
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