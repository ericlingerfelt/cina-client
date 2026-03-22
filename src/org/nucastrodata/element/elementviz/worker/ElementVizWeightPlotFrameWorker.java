package org.nucastrodata.element.elementviz.worker;

import java.awt.Point;
import java.util.Vector;
import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.datastructure.util.NucSimSetDataStructure;
import org.nucastrodata.element.elementviz.weight.ElementVizWeightPlotFrame;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.dialogs.ProgressBarDialog;

public class ElementVizWeightPlotFrameWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private ElementVizWeightPlotFrame parent;
	private ProgressBarDialog dialog;
	
	boolean allGoodWeightAbundProfiles
			, allGoodIsotopeMappings;	

	public ElementVizWeightPlotFrameWorker(ElementVizDataStructure ds
											, ElementVizWeightPlotFrame parent){
		this.ds  = ds;
		this.parent = parent;
		
		dialog = new ProgressBarDialog(parent, "", this);
	}

	protected Void doInBackground(){
	
		allGoodIsotopeMappings = true;
    	allGoodWeightAbundProfiles = true;
    	
    	dialog.open();
    	dialog.setCurrentValue(0);
    	dialog.setMaximum(ds.getNumberNucSimDataStructures());
	
		ds.setIsotopes(getIsotopes());

		String lastSubPath = "";
		NucSimDataStructure lastNSDS = null;

		for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){
		
			NucSimSetDataStructure nssds = ds.getZoneNucSimSetDataStructureArray()[i];
			NucSimDataStructure nsds = new NucSimDataStructure();
			nsds.setNucSimName(nssds.getPath().substring(nssds.getPath().lastIndexOf("/")+1));
			nsds.setPath(nssds.getPath());
			
			nssds.setZoneAbundArray(null);
			nssds.setFinalAbundArray(null);

			ds.setPath(nssds.getPath());
			ds.setCurrentNucSimSetDataStructure(nssds);
			ds.setCurrentNucSimDataStructure(nsds);
			
			if(isCancelled()){break;}
			
			String currentPath = nsds.getPath();
			if(currentPath.contains("_dir_")){
				String currentSubPath = currentPath.substring(0, currentPath.lastIndexOf("_dir_"));
				if(!currentSubPath.equals(lastSubPath)){
					if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", parent)){allGoodIsotopeMappings = false;}
					lastSubPath = currentSubPath;
					lastNSDS = nsds;
				}else{
					nsds.setZAMapArray(lastNSDS.getZAMapArray().clone());
					nsds.setIsotopeLabelMapArray(lastNSDS.getIsotopeLabelMapArray().clone());
				}
			}else{
				if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", parent)){allGoodIsotopeMappings = false;}
			}

			nssds.setZAMapArray(nsds.getZAMapArray());
			nssds.setIsotopeLabelMapArray(nsds.getIsotopeLabelMapArray());

			if(isCancelled()){break;}

			dialog.appendText("Downloading final weighted abundance data for " + nsds.getNucSimName() + "...");
			
			if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS WEIGHTED ABUNDANCES", parent)){
				dialog.appendText("DONE!\n");
				dialog.setCurrentValue(i+1);
    			allGoodWeightAbundProfiles = true;
    		}else{
    			allGoodWeightAbundProfiles = false;
    		}
			
			if(isCancelled()){break;}
			
    		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, nssds, CGICom.GET_TOTAL_WEIGHTS, parent);

        }
    	
		return null;
	}

	protected void done(){
	
		dialog.close();

		if(allGoodWeightAbundProfiles
				&& allGoodIsotopeMappings
				&& !this.isCancelled()){

			ds.setMaxNumberZonePoints(getMaxNumberZonePoints());
			ds.setNumberIsotopeRunsWeight(getNumberIsotopeRunsWeight());
			
			if(ds.getNumberIsotopeRunsWeight()>=1){
		
				ds.setZoneDataArray(getZoneDataArray());
				ds.setWeightDataArray(getWeightDataArray());
				ds.setWeightNonZeroArray(getWeightNonZeroArray());
				
				if(ds.getNumberNucSimSetDataStructures()>1){
					
					ds.setMassDataArrayWeight(getMassDataArrayWeight());
					ds.setRatioDataArrayWeight(getRatioDataArrayWeight());
					ds.setRatioNonZeroArrayWeight(getRatioNonZeroArrayWeight());
				
				}
					
		        ds.setZonemax(getZonemax());
				ds.setZonemin(getZonemin());
					
	        	ds.setWeightmax(getWeightmax());
				ds.setWeightmin(getWeightmin());
	        	
	        	if(ds.getNumberNucSimSetDataStructures()>1){

		        	ds.setMassmaxWeight(getMassmaxWeight());
					ds.setMassminWeight(getMassminWeight());
					
		        	ds.setRatiomaxWeight(getRatiomaxWeight());
					ds.setRatiominWeight(getRatiominWeight());
					
		        	ds.setRatioNonZeroArrayWeight(getRatioNonZeroArrayWeight());
	        	
	        	}

	        	parent.gotoControlsPanel();
    	
    		}else{
    		
  
    			String string = "The isotopes you have selected are not present in the simulations you have selected.";
    			
    			Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizWeightPlotFrame);
    		
    		}
    		
		}
    		
	}
	
	private String getIsotopes(){
		Vector viktor = ds.getIsotopeViktorWeight();
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
	
	private int getMaxNumberZonePoints(){
	    
    	int numberPoints = 0;
    		
		for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){
	
			numberPoints = Math.max(numberPoints, ds.getZoneNucSimSetDataStructureArray()[i].getZoneArray().length);

		}

    	return numberPoints;
    
    }
	
	private int getNumberIsotopeRunsWeight(){
	    
    	int numberIsotopeRunsWeight = 0;
    	
    	for(int i=0; i<ds.getZoneNucSimSetDataStructureArray().length; i++){
    	
    		Vector tempViktor = new Vector();
    	
    		numberIsotopeRunsWeight += ds.getZoneNucSimSetDataStructureArray()[i].getIndexArray().length;
			
			for(int j=0; j<ds.getZoneNucSimSetDataStructureArray()[i].getIndexArray().length; j++){
			
				tempViktor.addElement(ds.getZoneNucSimSetDataStructureArray()[i].getIsotopeLabelMapArray()[ds.getZoneNucSimSetDataStructureArray()[i].getIndexArray()[j]]);
				
			
			}
			
			tempViktor.trimToSize();
			ds.getZoneNucSimSetDataStructureArray()[i].setLabelViktor(tempViktor);
		
		}

    	return numberIsotopeRunsWeight;
    
    }
	
	private double[][] getZoneDataArray(){
		  
  		int index1 = 0;

  		double[][] tempArray = new double[ds.getNumberIsotopeRunsWeight()][ds.getMaxNumberZonePoints()];

		for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){

			for(int j=0; j<ds.getZoneNucSimSetDataStructureArray()[i].getIndexArray().length; j++){
			
				for(int k=0; k<ds.getZoneNucSimSetDataStructureArray()[i].getZoneArray().length; k++){
			
					tempArray[index1][k] = (double)ds.getZoneNucSimSetDataStructureArray()[i].getZoneArray()[k];
				
				}
				
				index1++;
			
			}
			
		}
       
    	return tempArray;
       
    }
	
	private double[][] getWeightDataArray(){
    	
    	int index1 = 0;
    	
    	double[][] tempArray = new double[ds.getNumberIsotopeRunsWeight()][ds.getMaxNumberZonePoints()];

		for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){

			for(int j=0; j<ds.getZoneNucSimSetDataStructureArray()[i].getZoneAbundArray().length; j++){
			
				for(int k=0; k<ds.getZoneNucSimSetDataStructureArray()[i].getZoneAbundArray()[j].length; k++){
					
					tempArray[index1][k] = ds.getZoneNucSimSetDataStructureArray()[i].getZoneAbundArray()[j][k] / ds.getZoneNucSimSetDataStructureArray()[i].getTotalWeights();
				
				}

				index1++;
			
			}
			
		}
       
       	for(int i=0; i<tempArray.length; i++){
       	
       		for(int j=0; j<tempArray[i].length; j++){
       		
       			if(tempArray[i][j]==0.0){
       			
       				tempArray[i][j] = 1E-100;
       			
       			}
       		
       		}
       	
       	}
       
    	return tempArray;

	}
	
	private boolean[] getWeightNonZeroArray(){
		
		boolean[] nonZeroArray = new boolean[ds.getNumberIsotopeRunsWeight()];
	
		boolean temp = false;
	
		for(int i=0; i<ds.getNumberIsotopeRunsWeight(); i++){
		
			foundNonZero:
			for(int j=0; j<ds.getWeightDataArray()[i].length; j++){
			
				temp = false;
				if(ds.getWeightDataArray()[i][j]!=1E-100){
				
					temp = true;
					break foundNonZero;
				
				}
			
			}
			
			nonZeroArray[i] = temp;
		
		}
		
		return nonZeroArray;
	
	}
	
	private double[][] getMassDataArrayWeight(){

  		double[][] tempArray = new double[ds.getIsotopeViktorWeight().size()][1];
     
       	for(int i=0; i<ds.getIsotopeViktorWeight().size(); i++){
       	
       		tempArray[i][0] = ((Point)ds.getIsotopeViktorWeight().elementAt(i)).getX()
       							+ ((Point)ds.getIsotopeViktorWeight().elementAt(i)).getY();

       	}	
       		
    	return tempArray;
       
	}
	
	private double[][] getRatioDataArrayWeight(){

  		double[][] tempArray = new double[ds.getIsotopeViktorWeight().size()][1];
     
     	String numNucSimSet = "";
     	String denomNucSimSet = "";

		Point[] numNucSimZAMapArray = new Point[0];
		Point[] denomNucSimZAMapArray = new Point[0];

		int[] numIndexArray = new int[0];
		int[] denomIndexArray = new int[0];
	
		double[] numFinalAbundArray = new double[0];
		double[] denomFinalAbundArray = new double[0];
	
     	if(Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel!=null){
     	
     		numNucSimSet = (String)Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.numComboBox.getSelectedItem();
     		denomNucSimSet = (String)Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.denomComboBox.getSelectedItem();
     	
     	}else{
     		
     		String stringNum = ds.getZoneNucSimSetDataStructureArray()[0].getPath();
     		String stringDenom = ds.getZoneNucSimSetDataStructureArray()[1].getPath();
     		
     		numNucSimSet = stringNum.substring(stringNum.lastIndexOf("/")+1);
     		denomNucSimSet = stringDenom.substring(stringDenom.lastIndexOf("/")+1);
     		
     	}

 		for(int i=0; i<ds.getZoneNucSimSetDataStructureArray().length; i++){
 		
 			String string = ds.getZoneNucSimSetDataStructureArray()[i].getPath();
 		
 			if(string.substring(string.lastIndexOf("/")+1).equals(numNucSimSet)){
 				
 				numNucSimZAMapArray = ds.getZoneNucSimSetDataStructureArray()[i].getZAMapArray();
 				numIndexArray = ds.getZoneNucSimSetDataStructureArray()[i].getIndexArray();
 				numFinalAbundArray = ds.getZoneNucSimSetDataStructureArray()[i].getFinalAbundArray();
 				
 			}
 			
 			string = ds.getZoneNucSimSetDataStructureArray()[i].getPath();
 		
 			if(string.substring(string.lastIndexOf("/")+1).equals(denomNucSimSet)){
 				
 				denomNucSimZAMapArray = ds.getZoneNucSimSetDataStructureArray()[i].getZAMapArray();
 				denomIndexArray = ds.getZoneNucSimSetDataStructureArray()[i].getIndexArray();
				denomFinalAbundArray = ds.getZoneNucSimSetDataStructureArray()[i].getFinalAbundArray();
				
 			}
 			
 		}
 		
 		for(int i=0; i<ds.getIsotopeViktorWeight().size(); i++){
 			
 			int currentZ = (int)((Point)ds.getIsotopeViktorWeight().elementAt(i)).getX();
 			
 			int currentA = (int)((Point)ds.getIsotopeViktorWeight().elementAt(i)).getX()
 							+ (int)((Point)ds.getIsotopeViktorWeight().elementAt(i)).getY();

 			boolean inNumPoint = false;
 			boolean inDenomPoint = false;
 			
 			double numAbund = 0.0;
 			double denomAbund = 0.0;

 			for(int j=0; j<numIndexArray.length; j++){
 			
 				int currentZNum = (int)(numNucSimZAMapArray[numIndexArray[j]].getX());
 				int currentANum = (int)(numNucSimZAMapArray[numIndexArray[j]].getY());

 				if((currentZNum==currentZ) && (currentANum==currentA)){
 				
 					inNumPoint = true;
 					numAbund += numFinalAbundArray[j];
 				}
 			
 			}
 			
 			for(int j=0; j<denomIndexArray.length; j++){
 			
				int currentZDenom = (int)(denomNucSimZAMapArray[denomIndexArray[j]].getX());
				int currentADenom = (int)(denomNucSimZAMapArray[denomIndexArray[j]].getY());

 				if((currentZDenom==currentZ) && (currentADenom==currentA)){
 				
 					inDenomPoint = true;
 					denomAbund += denomFinalAbundArray[j];
 		
 				}
 			
 			}

 			if(inNumPoint && inDenomPoint && numAbund!=0.0 && denomAbund!=0.0){
 			
 				tempArray[i][0] = numAbund/denomAbund;

 			}else{
 			
 				tempArray[i][0] = 0.0;
 			
 			}

 		}

    	return tempArray;
       
	}
	
	private boolean[] getRatioNonZeroArrayWeight(){
		
		boolean[] nonZeroArray = new boolean[ds.getIsotopeViktorWeight().size()];

		for(int i=0; i<ds.getIsotopeViktorWeight().size(); i++){
		
			boolean temp = false;
			
			if(ds.getRatioDataArrayWeight()[i][0]!=0){
			
				temp = true;
			
			}
			
			nonZeroArray[i] = temp;
			
		}
		
		return nonZeroArray;
	
	}
	
	private int getWeightmin(){
	    
   		double newWeightmin = 1e30;
		
		int weightMin = 0;
		
		for(int i=0; i<ds.getNumberIsotopeRunsWeight(); i++){
    		
    		for(int j=0; j<ds.getWeightDataArray()[i].length; j++){
    		
				newWeightmin = Math.min(newWeightmin, ds.getWeightDataArray()[i][j]);
    		
    		}
    	
    	}
		
		weightMin = (int)Math.floor(0.434294482*Math.log(newWeightmin));

		if(weightMin<-30){
		
			weightMin = -30;
		
		}

		return weightMin;
   
  	}
	
	private int getWeightmax(){
    
   		double newWeightmax = 0.0;
		
		int weightMax = 0;

		for(int i=0; i<ds.getNumberIsotopeRunsWeight(); i++){
    		
    		for(int j=0; j<ds.getWeightDataArray()[i].length; j++){
    		
				newWeightmax = Math.max(newWeightmax, ds.getWeightDataArray()[i][j]);
    		
    		}
    	
    	}
		
		weightMax = (int)Math.ceil(0.434294482*Math.log(newWeightmax));

		if(weightMax>30){
		
			weightMax = 30;
		
		}

		return weightMax;
   
  	}
	
	private int getMassminWeight(){
	    
    	double[][] tempArray = ds.getMassDataArrayWeight();
    
    	double min = 1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			min = Math.min(min, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	return (int)min;
    
    }
    
	private int getMassmaxWeight(){
    
    	double[][] tempArray = ds.getMassDataArrayWeight();
    
    	double max = -1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			max = Math.max(max, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	return (int)max;
    
    }
	
	private int getZonemin(){
	    
    	double[][] tempArray = ds.getZoneDataArray();
    
    	double min = 1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			min = Math.min(min, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	return (int)min;
    
    }
    
	private int getZonemax(){
    
    	double[][] tempArray = ds.getZoneDataArray();
    
    	double max = -1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			max = Math.max(max, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	if(max==getZonemin()){
    		max++;
    	}
    	
    	return (int)max;
    	
    }
	
	private double getRatiominWeight(){
	    
    	double[][] tempArray = ds.getRatioDataArrayWeight();
    
    	double min = 1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			min = Math.min(min, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	min = Math.floor(min);
    	
    	return min;
    
    }

	private double getRatiomaxWeight(){
    
    	double[][] tempArray = ds.getRatioDataArrayWeight();
    
    	double max = -1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			max = Math.max(max, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	max = Math.ceil(max);
    	
    	return max;
    
    }
	
}