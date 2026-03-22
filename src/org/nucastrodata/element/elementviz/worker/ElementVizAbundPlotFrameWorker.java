package org.nucastrodata.element.elementviz.worker;

import java.awt.Point;
import java.util.Vector;
import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.AbundTimestepDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundPlotFrame;
import org.nucastrodata.dialogs.ProgressBarDialog;

public class ElementVizAbundPlotFrameWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private ElementVizAbundPlotFrame parent;
	private ProgressBarDialog dialog;
	
	boolean allGoodTimeMappings
			, allGoodIsotopeMappings
			, allGoodAbundProfiles
			, allGoodThermoProfiles;	

	public ElementVizAbundPlotFrameWorker(ElementVizDataStructure ds
											, ElementVizAbundPlotFrame parent){
		this.ds  = ds;
		this.parent = parent;
		
		dialog = new ProgressBarDialog(parent, "", this);
	}

	protected Void doInBackground(){
	
		allGoodTimeMappings = true;
    	allGoodIsotopeMappings = true;
    	allGoodAbundProfiles = true;
    	allGoodThermoProfiles = true;
    	
    	dialog.open();
    	dialog.setCurrentValue(0);
    	dialog.setMaximum(ds.getNumberNucSimDataStructures());
		
    	ds.setIsotopes(getIsotopes());
		ds.setFinal_step("N");
		
		String lastSubPath = "";
		NucSimDataStructure lastNSDS = null;
		
    	for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
    	
    		NucSimDataStructure nsds = ds.getNucSimDataStructureArray()[i];
			ds.setPath(nsds.getPath());
			ds.setZone(String.valueOf(nsds.getZone()));
			ds.setZones(String.valueOf(nsds.getZone()));
			ds.setCurrentNucSimDataStructure(nsds);

			if(isCancelled()){break;}

			if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS TIME MAPPING", parent)){allGoodTimeMappings = false;}
			
			if(isCancelled()){break;}
			
			if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS THERMO PROFILE", parent)){allGoodThermoProfiles = false;}
    		
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
    		
			if(isCancelled()){break;}
    		
			dialog.appendText("Downloading abundance data for " + nsds.getNucSimName() + "...");

    		if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ABUNDANCES", parent, dialog)){
    			dialog.appendText("DONE!\n");
    			dialog.setCurrentValue(i+1);
    			allGoodAbundProfiles = true;
    		}else{
    			allGoodAbundProfiles = false;
    		}
		}
		return null;
	}

	protected void done(){
	
		dialog.close();

		if(allGoodAbundProfiles 
			&& allGoodTimeMappings 
			&& allGoodIsotopeMappings 
			&& allGoodThermoProfiles
			&& !this.isCancelled()){

			ds.setNormalTimeArray(getNormalTimeArray(ds.getNucSimDataStructureArray()));
			ds.setMaxNumberTimePoints(getMaxNumberTimePoints());
			ds.setNumberIsotopeRunsAbund(getNumberIsotopeRunsAbund());
		
			if(ds.getNumberIsotopeRunsAbund()>=1){
		
				ds.setTimeDataArrayAbund(getTimeDataArrayAbund());
				ds.setAbundDataArray(getAbundDataArray());
				ds.setAbundNonZeroArray(getAbundNonZeroArray());
				
				if(ds.getNucSimVector().size()>1){
				
					ds.setMassDataArrayAbund(getMassDataArrayAbund());
					ds.setRatioDataArrayAbund(getRatioDataArrayAbundInit());
					ds.setRatioNonZeroArrayAbund(getRatioNonZeroArrayAbund());
				
				}
					
		        ds.setTimemaxAbund(10);
				ds.setTimeminAbund(-10);
					
	        	ds.setAbundmax(getAbundmax());
				ds.setAbundmin(getAbundmin());
	        	
	        	if(ds.getNucSimVector().size()>1){
	        	
		        	ds.setMassmaxAbund(getMassmaxAbund());
					ds.setMassminAbund(getMassminAbund());
					
		        	ds.setRatiomaxAbund(getRatiomaxAbund());
					ds.setRatiominAbund(getRatiominAbund());
					
		        	ds.setRatioNonZeroArrayAbund(getRatioNonZeroArrayAbund());
	        	
	        	}

	        	parent.gotoControlsPanel();
    	
    		}else{
    	
    			String string = "The isotopes you have selected are not present in the simulations you have selected.";
    			Dialogs.createExceptionDialog(string, parent);
    		
    		}
    		
		}
		
	}
	
	private String getIsotopes(){
    	Vector viktor = ds.getIsotopeViktorAbund();
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
    
	private double[] getNormalTimeArray(NucSimDataStructure[] appnsdsa){
	    
    	double[] normalTimeArray = new double[appnsdsa.length];

		for(int i=0; i<normalTimeArray.length; i++){

			double currentTemp = 0.0;
			double previousTemp = 0.0;
			boolean foundDifferentTemp = false;
			
			foundDifferentTemp:
			for(int j=1; j<appnsdsa[i].getTempArray().length; j++){
			
				previousTemp = appnsdsa[i].getTempArray()[j-1];
				currentTemp = appnsdsa[i].getTempArray()[j];
				
				if(previousTemp!=currentTemp){
					foundDifferentTemp = true;
					break foundDifferentTemp;
				}
				
			}
			
			if(foundDifferentTemp){
			
				double maxTemp = 0.0;
				
				for(int j=0; j<appnsdsa[i].getTempArray().length; j++){
				
					maxTemp = Math.max(maxTemp, appnsdsa[i].getTempArray()[j]);
					
					if(maxTemp==appnsdsa[i].getTempArray()[j]){
					
						normalTimeArray[i] = appnsdsa[i].getTimeArray()[j];
					
					}
					
				}
			
			}else{
				
				normalTimeArray[i] = appnsdsa[i].getTimeArray()[0];
				
			}
			
		}	
	
		return normalTimeArray;
    
    }
    
	private int getMaxNumberTimePoints(){
	    
    	int numberPoints = 0;
    		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
	
			numberPoints = Math.max(numberPoints, ds.getNucSimDataStructureArray()[i].getTimeArray().length);

		}

    	return numberPoints;
    
    }
    
	private int getNumberIsotopeRunsAbund(){
	    
    	int numberIsotopeRunsAbund = 0;
    	
    	for(int i=0; i<ds.getNucSimDataStructureArray().length; i++){
    	
			Vector tempViktor = new Vector();
			Vector tempViktor2 = new Vector();
			
    		for(int k=0; k<ds.getIsotopeViktorAbund().size(); k++){

				double ZSelected = ((Point)ds.getIsotopeViktorAbund().elementAt(k)).getX();
    			double ASelected = ((Point)ds.getIsotopeViktorAbund().elementAt(k)).getY() + ZSelected;
				
    			for(int l=0; l<ds.getNucSimDataStructureArray()[i].getZAMapArray().length; l++){
    			
    				double Z = ds.getNucSimDataStructureArray()[i].getZAMapArray()[l].getX();
    				double A = ds.getNucSimDataStructureArray()[i].getZAMapArray()[l].getY();

    				if(ZSelected==Z && ASelected==A){
    					
    					tempViktor.addElement(new Integer(l));
    					tempViktor2.addElement(new String(ds.getNucSimDataStructureArray()[i].getIsotopeLabelMapArray()[l]));

    					numberIsotopeRunsAbund++;

    				}
    			
    			}
    			
    			tempViktor.trimToSize();
    			tempViktor2.trimToSize();

    			ds.getNucSimDataStructureArray()[i].setIndexViktor(tempViktor);
    			ds.getNucSimDataStructureArray()[i].setLabelViktor(tempViktor2);
    		
    		}
		
		}

    	return numberIsotopeRunsAbund;
    
    }
    
	private double[][] getTimeDataArrayAbund(){
		  
  		int index1 = 0;

  		double[][] tempArray = new double[ds.getNumberIsotopeRunsAbund()][ds.getMaxNumberTimePoints()];

		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

			for(int j=0; j<ds.getNucSimDataStructureArray()[i].getIndexViktor().size(); j++){
			
				tempArray[index1] = getNormalTimeArray(ds.getNucSimDataStructureArray()[i].getTimeArray(), i);
				
				index1++;
			
			}
			
		}
       
    	return tempArray;
       
    }
    
	private double[] getNormalTimeArray(double[] timeArray, int index){
	    
    	double[] normalTimeArray = new double[timeArray.length];
    
    	for(int i=0; i<timeArray.length; i++){
    	
    		normalTimeArray[i] = timeArray[i] - ds.getNormalTimeArray()[index];
    	
    	}
    
    	return normalTimeArray;
    
    }
    
	private double[][] getAbundDataArray(){
		  
  		int index1 = 0;

  		double[][] tempArray = new double[ds.getNumberIsotopeRunsAbund()][ds.getMaxNumberTimePoints()];
	
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			
			for(int j=0; j<ds.getNucSimDataStructureArray()[i].getIndexViktor().size(); j++){
				
				tempArray[index1] = getIsotopeRunDataAbund(ds.getNucSimDataStructureArray()[i], ((Integer)ds.getNucSimDataStructureArray()[i].getIndexViktor().elementAt(j)).intValue());

				index1++;

			}
			
		}
       
    	return tempArray;
       
	}
	
	private double[] getIsotopeRunDataAbund(NucSimDataStructure appnsds, int index){
	    
    	double[] tempArray = new double[appnsds.getTimeArray().length];

    	for(int i=0; i<appnsds.getAbundTimestepDataStructureArray().length; i++){

    		isotopeFound:
    		for(int j=0; j<appnsds.getAbundTimestepDataStructureArray()[i].getIndexArray().length; j++){

    			if(appnsds.getAbundTimestepDataStructureArray()[i].getIndexArray()[j]==index){
    			
    				tempArray[i] = appnsds.getAbundTimestepDataStructureArray()[i].getAbundArray()[j];

    				break isotopeFound;
    				    			
    			}else{
  					
    				tempArray[i] = 1E-100;

    			}

    		}
    	
    	}
    	
    	return tempArray;
    
    }
    
	private boolean[] getAbundNonZeroArray(){
		
		boolean[] nonZeroArray = new boolean[ds.getNumberIsotopeRunsAbund()];
	
		boolean temp = false;
	
		for(int i=0; i<ds.getNumberIsotopeRunsAbund(); i++){
		
			foundNonZero:
			for(int j=0; j<ds.getAbundDataArray()[i].length; j++){
			
				temp = false;
				if(ds.getAbundDataArray()[i][j]!=1E-100){
				
					temp = true;
					break foundNonZero;
				
				}
			
			}
			
			nonZeroArray[i] = temp;
		
		}
		
		return nonZeroArray;
	
	}
	
	private double[][] getMassDataArrayAbund(){

  		double[][] tempArray = new double[ds.getIsotopeViktorAbund().size()][1];
     
       	for(int i=0; i<ds.getIsotopeViktorAbund().size(); i++){
       	
       		tempArray[i][0] = ((Point)ds.getIsotopeViktorAbund().elementAt(i)).getX()
       							+ ((Point)ds.getIsotopeViktorAbund().elementAt(i)).getY();

       	}	
       		
    	return tempArray;
       
	}
	
	private double[][] getRatioDataArrayAbundInit(){
		

  		double[][] tempArray = new double[ds.getIsotopeViktorAbund().size()][1];
     
     	String numNucSim = "";
     	String denomNucSim = "";

		Point[] numNucSimZAMapArray = new Point[0];
		Point[] denomNucSimZAMapArray = new Point[0];

 		numNucSim = (String)ds.getNucSimVector().elementAt(0);
 		denomNucSim = (String)ds.getNucSimVector().elementAt(1);

 		AbundTimestepDataStructure appatdsNum = new AbundTimestepDataStructure();
 		AbundTimestepDataStructure appatdsDenom = new AbundTimestepDataStructure();

 		for(int i=0; i<ds.getNucSimDataStructureArray().length; i++){
 		
 			if(ds.getNucSimDataStructureArray()[i].getNucSimName().equals(numNucSim)){
 				
 				numNucSimZAMapArray = ds.getNucSimDataStructureArray()[i].getZAMapArray();
 			
 				appatdsNum.setAbundArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray().length-1].getAbundArray());
 				appatdsNum.setIndexArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray().length-1].getIndexArray());
 			
 			}
 			
 			if(ds.getNucSimDataStructureArray()[i].getNucSimName().equals(denomNucSim)){
 			
 				denomNucSimZAMapArray = ds.getNucSimDataStructureArray()[i].getZAMapArray();
 			
 				appatdsDenom.setAbundArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray().length-1].getAbundArray());
 				appatdsDenom.setIndexArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray().length-1].getIndexArray());
 			
 			}
 		
 		}
 		
 		for(int i=0; i<ds.getIsotopeViktorAbund().size(); i++){
 			
 			int currentZ = (int)((Point)ds.getIsotopeViktorAbund().elementAt(i)).getX();
 			
 			int currentA = (int)((Point)ds.getIsotopeViktorAbund().elementAt(i)).getX()
 							+ (int)((Point)ds.getIsotopeViktorAbund().elementAt(i)).getY();

 			boolean inNumPoint = false;
 			boolean inDenomPoint = false;
 			
 			double numAbund = 0.0;
 			double denomAbund = 0.0;

 			for(int j=0; j<appatdsNum.getIndexArray().length; j++){
 			
 				int currentZNum = -1;
 				int currentANum = -1;
 				
 				if(appatdsNum.getIndexArray()[j]!=-1){
 			
	 				currentZNum = (int)(numNucSimZAMapArray[appatdsNum.getIndexArray()[j]].getX());
	 				currentANum = (int)(numNucSimZAMapArray[appatdsNum.getIndexArray()[j]].getY());
 				
 				}
 				
 				if((currentZNum==currentZ) && (currentANum==currentA)){
 				
 					inNumPoint = true;
 					numAbund += appatdsNum.getAbundArray()[j];
 				
 				}
 			
 			}
 			
 			for(int j=0; j<appatdsDenom.getIndexArray().length; j++){
 			
 				int currentZDenom = -1;
 				int currentADenom = -1;
 			
 				if(appatdsDenom.getIndexArray()[j]!=-1){
 			
 					currentZDenom = (int)(denomNucSimZAMapArray[appatdsDenom.getIndexArray()[j]].getX());
 					currentADenom = (int)(denomNucSimZAMapArray[appatdsDenom.getIndexArray()[j]].getY());
 				
 				}
 				
 				if((currentZDenom==currentZ) && (currentADenom==currentA)){
 				
 					inDenomPoint = true;
 					denomAbund += appatdsDenom.getAbundArray()[j];
 				
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
	
	private boolean[] getRatioNonZeroArrayAbund(){
		
		boolean[] nonZeroArray = new boolean[ds.getIsotopeViktorAbund().size()];

		for(int i=0; i<ds.getIsotopeViktorAbund().size(); i++){
		
			boolean temp = false;
			
			if(ds.getRatioDataArrayAbund()[i][0]!=0){
			
				temp = true;
			
			}
			
			nonZeroArray[i] = temp;
		
		}
		
		return nonZeroArray;
	
	}
	
	private int getAbundmin(){
    
   		double newAbundmin = 1e30;
		
		int abundMin = 0;
		
		for(int i=0; i<ds.getNumberIsotopeRunsAbund(); i++){
    		
    		for(int j=0; j<ds.getAbundDataArray()[i].length; j++){
    		
				newAbundmin = Math.min(newAbundmin, ds.getAbundDataArray()[i][j]);
    		
    		}
    	
    	}
		
		abundMin = (int)Math.floor(0.434294482*Math.log(newAbundmin));

		if(abundMin<-30){
		
			abundMin = -30;
		
		}

		return abundMin;
   
  	}
	
	private int getAbundmax(){
    
   		double newAbundmax = 0.0;
		
		int abundMax = 0;

		for(int i=0; i<ds.getNumberIsotopeRunsAbund(); i++){
    		
    		for(int j=0; j<ds.getAbundDataArray()[i].length; j++){
    		
				newAbundmax = Math.max(newAbundmax, ds.getAbundDataArray()[i][j]);
    		
    		}
    	
    	}
		
		abundMax = (int)Math.ceil(0.434294482*Math.log(newAbundmax));

		if(abundMax>30){
		
			abundMax = 30;
		
		}

		return abundMax;
   
  	}
  	
	private int getMassminAbund(){
    
    	double[][] tempArray = ds.getMassDataArrayAbund();
    
    	double min = 1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			min = Math.min(min, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	return (int)min;
    
    }
    
    private int getMassmaxAbund(){
    
    	double[][] tempArray = ds.getMassDataArrayAbund();
    
    	double max = -1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			max = Math.max(max, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	return (int)max;
    
    }
    
	private double getRatiominAbund(){
    
    	double[][] tempArray = ds.getRatioDataArrayAbund();
    
    	double min = 1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			min = Math.min(min, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	//min = Double.valueOf(CinaNumberFormats.getFormattedRatio(min)).doubleValue();
    	
    	min = Math.floor(min);
    	
    	return min;
    
    }
    
    private double getRatiomaxAbund(){
    
    	double[][] tempArray = ds.getRatioDataArrayAbund();
    
    	double max = -1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			max = Math.max(max, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	//max = Double.valueOf(CinaNumberFormats.getFormattedRatio(max)).doubleValue();
    	
    	max = Math.ceil(max);
    	
    	return max;
    
    }
	
}