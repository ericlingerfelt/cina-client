package org.nucastrodata.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.datastructure.feature.RateManDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.HelpFrame;
import org.nucastrodata.WizardPanel;

/**
 * The Class RateManInvestRate3Panel.
 */
public class RateManInvestRate3Panel extends WizardPanel implements ActionListener{
	
	//Declare gbc
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The main panel. */
	JPanel mainPanel;
	
	/** The dup label. */
	JLabel topLabel, uniqueLabel, dupLabel;
	
	/** The help button. */
	JButton plotButton, infoButton, listButton, helpButton;
	
	/** The button panel. */
	JPanel buttonPanel;
	
	/** The sp. */
	JScrollPane sp;
	
	/** The rate man invest rate list panel. */
	RateManInvestRateListPanel rateManInvestRateListPanel;
	
	/** The found home vector. */
	Vector foundHomeVector;
	
	/** The compared rate vector. */
	Vector comparedRateVector;
	
	/** The ds. */
	private RateManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate man invest rate3 panel.
	 *
	 * @param ds the ds
	 */
	public RateManInvestRate3Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(6);
		Cina.rateManFrame.setCurrentPanelIndex(3);

		//Create main panel to hold all other inner panels and components
		mainPanel = new JPanel(new GridBagLayout());
		
		//Instantiate gbc
		gbc = new GridBagConstraints();
		
		addWizardPanel("Rate Manager", "Rate Locator", "3", "3");
		
		plotButton = new JButton("<html>Plot Selected<p>Distinct Rates</html>");
		plotButton.setFont(Fonts.buttonFont);
		plotButton.addActionListener(this);
		
		infoButton = new JButton("Rate Info");
		infoButton.setFont(Fonts.buttonFont);
		infoButton.addActionListener(this);

		listButton = new JButton("<html>List of<p>Distinct Rates</html>");
		listButton.setFont(Fonts.buttonFont);
		listButton.addActionListener(this);
		
		helpButton = new JButton("<html>Help on<p>This Interface</html>");
		helpButton.setFont(Fonts.buttonFont);
		helpButton.addActionListener(this);
		
		rateManInvestRateListPanel = new RateManInvestRateListPanel(ds);
		
		sp = new JScrollPane(rateManInvestRateListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(250, 270));
		
		String reactionString = "";
		
		if(ds.getInvestRateDataStructure().getDecay().equals("")){
        	
        	reactionString = ds.getInvestRateDataStructure().getReactionString();
        
        }else{
        
        	reactionString = ds.getInvestRateDataStructure().getReactionString()
        						+ " ["
        						+ ds.getInvestRateDataStructure().getDecay()
        						+ "]";
        
        }
		
		topLabel = new JLabel("<html> You have selected "
								+ reactionString
								+ " for investigation."
								+ "<p>Select rates by library name from the checkbox list below.</html>");
		
		buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
		buttonPanel.add(plotButton);
		buttonPanel.add(infoButton);
		buttonPanel.add(listButton);
		buttonPanel.add(helpButton);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = 2;
		mainPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 15, 15, 15);
		
		mainPanel.add(sp, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(15, 15, 15, 15);
		
		mainPanel.add(buttonPanel, gbc);
		
		add(mainPanel, BorderLayout.CENTER);

		validate();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==infoButton){
		
			if(!noneChecked()){
       		
	       		if(Cina.rateManFrame.rateManInvestRateInfoFrame==null){
	       	
	       			Cina.rateManFrame.rateManInvestRateInfoFrame = new RateManInvestRateInfoFrame(ds);
	       		
	       		}else{
	
					Cina.rateManFrame.rateManInvestRateInfoFrame.refreshData();
	       			Cina.rateManFrame.rateManInvestRateInfoFrame.setVisible(true);
	       		
	       		}
	       		
	       	}else{
	       	
	       		String string = "Please select reaction rates for Rate Info from the checkbox list."; 
	       		
	       		Dialogs.createExceptionDialog(string, Cina.rateManFrame);
	       		
	       	}
		
		}else if(ae.getSource()==plotButton){
		
			if(!noneChecked()){
			
				//Calculate temp and rate data arrays
	        	setTempDataArrays();
	        	setRateDataArrays();
	        	
	        	//Calculate temp and rate range
	        	setInitTempRange();
	        	setInitRateRange();
	        
	        	//Set number of total and comp rates to this data structure 
	        	ds.setNumberTotalRates(getNumberTotalRates());
	        	ds.setNumberCompRates(getNumberCompRates());
	
	        	//If plotting interface already exists
	        	if(Cina.rateManFrame.rateManInvestRatePlotFrame!=null){
	
					//Set plotting interface properties
	        		Cina.rateManFrame.rateManInvestRatePlotFrame.setFormatPanelState();
	        		Cina.rateManFrame.rateManInvestRatePlotFrame.setReactionListPanel();
	        		
	        		//Redraw plot and set visible
	        		Cina.rateManFrame.rateManInvestRatePlotFrame.redrawPlot();
	        		Cina.rateManFrame.rateManInvestRatePlotFrame.setVisible(true);
	
				//If plotting interface has not yet been opened
	        	}else{
	        	
	        		Cina.rateManFrame.rateManInvestRatePlotFrame = new RateManInvestRatePlotFrame(ds);
	        	
	        	}
	        	
	        }else{
	        
	        
	       		String string = "Please select reaction rates for plot from the checkbox list."; 
	       		
	       		Dialogs.createExceptionDialog(string, Cina.rateManFrame);
	       		
	        }
			
		}else if(ae.getSource()==listButton){
		
			if(Cina.rateManFrame.rateManInvestRateListFrame==null){
	       	
       			Cina.rateManFrame.rateManInvestRateListFrame = new RateManInvestRateListFrame(ds);
       		
       		}else{

				Cina.rateManFrame.rateManInvestRateListFrame.setTableText();
       			Cina.rateManFrame.rateManInvestRateListFrame.setVisible(true);
       		
       		}
		
		}else if(ae.getSource()==helpButton){
			
			Cina.rateManFrame.rateManInvestRate3HelpFrame = new HelpFrame("Help on Rate Locator", "RateLocator");
			Cina.rateManFrame.rateManInvestRate3HelpFrame.setVisible(true);
			
		}
	
	}
	
	
	//Method to create temp data arrays
    /**
	 * Sets the temp data arrays.
	 */
	public void setTempDataArrays(){
    
    	//Loop over libraries
    	for(int i=0; i<ds.getInvestRateVectorArray().length; i++){

			boolean rateUsed = false;

			//Loop over rates
			for(int j=0; j<ds.getInvestRateVectorArray()[i].size(); j++){	
		
				if(((JCheckBox)Cina.rateManFrame.rateManInvestRate3Panel.rateManInvestRateListPanel.checkBoxVectorArray[i].elementAt(j)).isSelected()){
	
    				rateUsed = true;
    				
    			}
    		
    		}
    		
    		if(rateUsed){
    			
				//Set temp grid to total temp data array 
				((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).setTempDataArrayTotal(ds.getRateTempGrid());
		
				//Create array for temp data for each component
				double[][] outputArray = new double[((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getResonantInfo().length][ds.getRateTempGrid().length];
		
				//Loop over components
				for(int j=0; j<((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getResonantInfo().length; j++){
				
					//Assign temp grid to temp data array for components
					outputArray[j] = ds.getRateTempGrid();
				
				}
				
				//Set temp data array for components
				((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).setTempDataArrayComp(outputArray);
		
			}
			
    	
    	}
    
    }
    
    //Method to create rate data arrays	
    /**
     * Sets the rate data arrays.
     */
    public void setRateDataArrays(){
    
    //Loop over libraries
    	for(int i=0; i<ds.getInvestRateVectorArray().length; i++){

			boolean rateUsed = false;

			//Loop over rates
			for(int j=0; j<ds.getInvestRateVectorArray()[i].size(); j++){	
		
				if(((JCheckBox)Cina.rateManFrame.rateManInvestRate3Panel.rateManInvestRateListPanel.checkBoxVectorArray[i].elementAt(j)).isSelected()){
	
    				rateUsed = true;
    				
    			}
    		
    		}
    		
    		if(rateUsed){
    			
				//Create temp array to store temperature data for calculating rates
				double[] tempArray = ((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getTempDataArrayTotal();
		
				//Get the number of parameters
				int numberParameters = ((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getNumberParameters();
				
				//Create a parameter array for this rate
				double[] parameters = ((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getParameters();
		
				//Create an array to hold rate values
				double[] rateArray = new double[tempArray.length];
				
				//Loop over rate array values
				for(int j=0; j<rateArray.length; j++){
				
					//Calculate rate at this temperature
					rateArray[j] = Cina.cinaMainDataStructure.calcRate(tempArray[j], parameters, numberParameters);

					//If rate is below 1e-100 or equal to zero set to 1e-100
					if(rateArray[j]<1e-100 || rateArray[j]==0){
					
						rateArray[j] = 1e-100;
					
					}
					
					//If rate is greater than 1e+100 or equal to zero set to 1e+100
					if(rateArray[j]>1e100 || rateArray[j]==0){
					
						rateArray[j] = 1e100;
					
					}

				}
				
				//Set rate array to data structure
				((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).setRateDataArrayTotal(rateArray);
		
				//Create array to hold rate values for components
				double[][] outputArray = new double[((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getResonantInfo().length][ds.getRateTempGrid().length];
				
				//Create array to hold parameters for components
				double[][] parameterCompArray = ((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getParameterCompArray();
		 		
		 		//If rate has more than one component
				if(((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getResonantInfo().length>1){

					//Loop over components
    				for(int j=0; j<((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getResonantInfo().length; j++){

						//Loop over temperature grid
    					for(int k=0; k<ds.getRateTempGrid().length; k++){

							//Calculate rate at this temperature
    						outputArray[j][k] = Cina.cinaMainDataStructure.calcRate(((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getTempDataArrayComp()[j][k], parameterCompArray[j], 7);
    					
    						//If rate is below 1e-100 or equal to zero set to 1e-100
    						if(outputArray[j][k]<1e-100 || outputArray[j][k]==0){
					
								outputArray[j][k] = 1e-100;
							
							}
							
							//If rate is greater than 1e+100 or equal to zero set to 1e+100
							if(outputArray[j][k]>1e100 || outputArray[j][k]==0){
					
								outputArray[j][k] = 1e100;
							
							}
    					
    					}
    				
    				}
    				
    				//Assign rate data arrays for components to data structure
    				((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).setRateDataArrayComp(outputArray);
    				
				}
				
			}
	
    	}
    
    }
    
    //Method to set initial temperature range for plotting       	
    /**
     * Sets the init temp range.
     */
    public void setInitTempRange(){
    
    	ds.setTempmin(-2);
    	
    	ds.setTempmax(1);
    
    }
      
    //Method to set initial rate range for plotting        	
    /**
     * Sets the init rate range.
     */
    public void setInitRateRange(){
    
    	ds.setRatemin(getRatemin());
    	
    	ds.setRatemax(getRatemax());
    
    }
    
    //Method to calculate rate minimum
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
    	for(int i=0; i<ds.getInvestRateVectorArray().length; i++){

			boolean rateUsed = false;

			//Loop over rates
			for(int j=0; j<ds.getInvestRateVectorArray()[i].size(); j++){	
		
				if(((JCheckBox)Cina.rateManFrame.rateManInvestRate3Panel.rateManInvestRateListPanel.checkBoxVectorArray[i].elementAt(j)).isSelected()){
	
    				rateUsed = true;
    				
    			}
    		
    		}
    		
    		if(rateUsed){
    		
				//Loop over rate array values
				for(int j=0; j<((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getRateDataArrayTotal().length; j++){
	
					//Assign new rate min
					newRatemin = Math.min(newRatemin, ((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getRateDataArrayTotal()[j]);
	
				}
			
			}
    	
    	}
		
		//Get magnitude of rate
		rateMin = (int)Math.floor(0.434294482*Math.log(newRatemin));

		//Return magnitude of rate
		return rateMin;
	
	}
	
	//Method to calculate rate maximum
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
    	for(int i=0; i<ds.getInvestRateVectorArray().length; i++){

			boolean rateUsed = false;

			//Loop over rates
			for(int j=0; j<ds.getInvestRateVectorArray()[i].size(); j++){	
		
				if(((JCheckBox)Cina.rateManFrame.rateManInvestRate3Panel.rateManInvestRateListPanel.checkBoxVectorArray[i].elementAt(j)).isSelected()){
	
    				rateUsed = true;
    				
    			}
    		
    		}
    		
    		if(rateUsed){
    		
				//Loop over rate array values
				for(int j=0; j<((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getRateDataArrayTotal().length; j++){
	
					//Assign new rate min
    				newRatemax = Math.max(newRatemax, ((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getRateDataArrayTotal()[j]);

				}
			
			}
    	
    	}
		
		//Get magnitude of rate		
		rateMax = (int)Math.ceil(0.434294482*Math.log(newRatemax));

		//Return magnitude of rate
		return rateMax;
	
	}
	
	//Method to get the number of total rates
    /**
	 * Gets the number total rates.
	 *
	 * @return the number total rates
	 */
	public int getNumberTotalRates(){
    
    	//Create counter
    	int total = 0;
    	
    	//Loop over libraries
    	for(int i=0; i<ds.getInvestRateVectorArray().length; i++){

			boolean rateUsed = false;

			//Loop over rates
			for(int j=0; j<ds.getInvestRateVectorArray()[i].size(); j++){	
		
				if(((JCheckBox)Cina.rateManFrame.rateManInvestRate3Panel.rateManInvestRateListPanel.checkBoxVectorArray[i].elementAt(j)).isSelected()){
	
    				rateUsed = true;
    				
    			}
    		
    		}
    		
    		if(rateUsed){
    			
    			//Add number of rates for this library to counter
    			total++;
    			
    		}
    	
    	}
    	
    	//Return counter
    	return total;
    
    }
    
    //Method to get the number of component rates
    /**
     * Gets the number comp rates.
     *
     * @return the number comp rates
     */
    public int getNumberCompRates(){
    
    	//Create counter
    	int comp = 0;
    	
    	//Loop over libraries
    	for(int i=0; i<ds.getInvestRateVectorArray().length; i++){

			boolean rateUsed = false;

			//Loop over rates
			for(int j=0; j<ds.getInvestRateVectorArray()[i].size(); j++){	
		
				if(((JCheckBox)Cina.rateManFrame.rateManInvestRate3Panel.rateManInvestRateListPanel.checkBoxVectorArray[i].elementAt(j)).isSelected()){
	
    				rateUsed = true;
    				
    			}
    		
    		}
    		
    		if(rateUsed){
    			
				//If rate has more than one component
				if(((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getResonantInfo().length>1){
				
					//Add number of rate's components to counter
					comp = comp + ((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getResonantInfo().length;
				
				}
			
			}
    	
    	}
    	
    	//Return counter
    	return comp;
    
    }
	
	/**
	 * None checked.
	 *
	 * @return true, if successful
	 */
	public boolean noneChecked(){
    
    	boolean noneChecked = true;
    	
 		for(int i=0; i<ds.getInvestRateVectorArray().length; i++){
		
			for(int j=0; j<ds.getInvestRateVectorArray()[i].size(); j++){
			
				if(((JCheckBox)Cina.rateManFrame.rateManInvestRate3Panel.rateManInvestRateListPanel.checkBoxVectorArray[i].elementAt(j)).isSelected()){
				
					noneChecked = false;
				
   				}
   				
			}
			
		}
		
    	return noneChecked;
    
    }
	
	/**
	 * Sets the invest rate vector array.
	 */
	public void setInvestRateVectorArray(){
	
		int numberRates = ds.getInvestRateDataStructureArray().length;
	
		int numberUniqueRateGroups = 2;
	
		comparedRateVector = new Vector();
		comparedRateVector.trimToSize();
		comparedRateVector.addElement(ds.getInvestRateDataStructureArray()[0].getReactionID());
	
		for(int i=0; i<numberRates; i++){

			if(checkForUniqueRate(ds.getInvestRateDataStructureArray()[i], numberRates)){
			
				numberUniqueRateGroups++;
			
			}
		
		}
			
		Vector[] viktorArray = new Vector[numberUniqueRateGroups];
		
		for(int i=0; i<numberUniqueRateGroups; i++){
		
			viktorArray[i] = new Vector();
		
		}
		
		ds.setInvestRateVectorArray(getInvestRateVectorArray(viktorArray));
	
	}
	
	/**
	 * Check for unique rate.
	 *
	 * @param apprds the apprds
	 * @param numberRates the number rates
	 * @return true, if successful
	 */
	public boolean checkForUniqueRate(RateDataStructure apprds, int numberRates){
	
		boolean unique = false;
	
		for(int i=0; i<numberRates; i++){
		
			if((apprds.getNumberParameters()==ds.getInvestRateDataStructureArray()[i].getNumberParameters())
					&& !comparedRateVector.contains(apprds.getReactionID())){
			
				unique = compareParameters(apprds, ds.getInvestRateDataStructureArray()[i]);
				
				comparedRateVector.addElement(apprds.getReactionID());
				
			}
		
		}
		
		return unique;
	
	}
	
	/**
	 * Compare parameters.
	 *
	 * @param apprds1 the apprds1
	 * @param apprds2 the apprds2
	 * @return true, if successful
	 */
	public boolean compareParameters(RateDataStructure apprds1, RateDataStructure apprds2){
	
		boolean diffParameters = false;
		
		foundAnswer:
		for(int i=0; i<apprds1.getNumberParameters(); i++){
		
			if(apprds1.getParameters()[i]!=apprds2.getParameters()[i]){
			
				diffParameters = true;
				
				break foundAnswer;
			
			}
		
		}
	
		return diffParameters;
	
	}
	
	/**
	 * Gets the invest rate vector array.
	 *
	 * @param tempArray the temp array
	 * @return the invest rate vector array
	 */
	public Vector[] getInvestRateVectorArray(Vector[] tempArray){
	
		tempArray[0].addElement(ds.getInvestRateDataStructureArray()[0]);
		
		foundHomeVector = new Vector();
		foundHomeVector.trimToSize();
		foundHomeVector.addElement(ds.getInvestRateDataStructureArray()[0].getReactionID());
		
		for(int i=0; i<ds.getInvestRateDataStructureArray().length; i++){

			RateDataStructure apprds = ds.getInvestRateDataStructureArray()[i];
	
			boolean alreadyThere = false;
			
			for(int j=0; j<tempArray.length; j++){

				tempArray[j].trimToSize();

				int size = tempArray[j].size();
			
				if(foundHomeVector.contains(apprds.getReactionID())){
				
					alreadyThere = true;
					
				}
			
			}
			
			if(!alreadyThere){
			
				tempArray = findRateHome(apprds, tempArray);
			
			}
		
		}
	
		int blankCounter = 0;
		
		for(int i=0; i<tempArray.length; i++){
		
			tempArray[i].trimToSize();
		
			if(tempArray[i].size()!=0){
			
				blankCounter++;
			
			}
		
		}
	
		Vector[] tempArrayNew = new Vector[blankCounter];
	
		for(int i=0; i<tempArrayNew.length; i++){
		
			tempArrayNew[i] = tempArray[i];
		
		}
	
		return tempArrayNew;
	
	}
	
	/**
	 * Find rate home.
	 *
	 * @param apprds the apprds
	 * @param tempArray the temp array
	 * @return the vector[]
	 */
	public Vector[] findRateHome(RateDataStructure apprds, Vector[] tempArray){
	
		boolean foundHome = false;

		for(int i=0; i<tempArray.length; i++){
			
			tempArray[i].trimToSize();
		
			int size = tempArray[i].size();
		
			for(int j=0; j<size; j++){
			
				if(((RateDataStructure)tempArray[i].elementAt(j)).getNumberParameters()
					== apprds.getNumberParameters()){
				
					boolean sameRate = true;
				
					for(int k=0; k<apprds.getNumberParameters(); k++){
					
						if(((RateDataStructure)tempArray[i].elementAt(j)).getParameters()[k]!=apprds.getParameters()[k]){
						
							sameRate = false;
						
						}
					
					}
					
					if(sameRate && !foundHomeVector.contains(apprds.getReactionID())){
	
						foundHomeVector.addElement(apprds.getReactionID());
	
						tempArray[i].addElement(apprds);
						
						foundHome = true;
					
					}
				
				}
			
			}
		
		}
	
		breakOut:
		if(!foundHome){
		
			for(int i=0; i<tempArray.length; i++){
			
				tempArray[i].trimToSize();
		
				int size = tempArray[i].size();
			
				if(size==0){
					
					tempArray[i].addElement(apprds);
					break breakOut;
				
				}
				
			}
		
		}
	
		return tempArray;
	
	}
	
	//Method to get the state of this object
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
	
	}
	
	//Method to set the state of this object
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		rateManInvestRateListPanel.initialize();
		
		sp.revalidate();
		
		validate();
		
	}

}