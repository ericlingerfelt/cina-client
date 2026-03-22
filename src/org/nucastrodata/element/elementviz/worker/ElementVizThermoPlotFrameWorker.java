package org.nucastrodata.element.elementviz.worker;

import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.ElementVizFrame;
import org.nucastrodata.element.elementviz.ElementVizToolsPanel;
import org.nucastrodata.dialogs.ProgressBarDialog;

public class ElementVizThermoPlotFrameWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private ElementVizToolsPanel parent;
	private ProgressBarDialog dialog;
	private ElementVizFrame frame;
	
	boolean allGoodTimeMappings
			, allGoodThermoProfiles;	

	public ElementVizThermoPlotFrameWorker(ElementVizDataStructure ds
											, ElementVizFrame frame
											, ElementVizToolsPanel parent){
		this.ds  = ds;
		this.parent = parent;
		this.frame = frame;
		
		dialog = new ProgressBarDialog(frame, "", this);
	}

	protected Void doInBackground(){
	
		allGoodThermoProfiles = true;
    	allGoodTimeMappings = true;
	
    	dialog.open();
    	dialog.setCurrentValue(0);
    	dialog.setMaximum(ds.getNumberNucSimDataStructures());
	
    	for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

    		NucSimDataStructure nsds = ds.getNucSimDataStructureArray()[i];
			ds.setPath(nsds.getPath());
			ds.setZone(String.valueOf(nsds.getZone()));
			ds.setZones(String.valueOf(nsds.getZone()));
			ds.setCurrentNucSimDataStructure(nsds);
			
			dialog.appendText("Downloading thermodynamic profile data for " + nsds.getNucSimName() + "...");
			
			if(isCancelled()){break;}
			
			if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS THERMO PROFILE", frame)){
				dialog.appendText("DONE!\n");
    			dialog.setCurrentValue(i+1);
    			allGoodThermoProfiles = true;
			}else{
				allGoodThermoProfiles = false;
			}
			
			if(isCancelled()){break;}
			
			boolean flag = Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS TIME MAPPING", frame);
			if(!flag){
				allGoodTimeMappings = false;
			}

    	}
	
		return null;
	}

	protected void done(){
	
		dialog.close();
		
  		if(allGoodThermoProfiles 
  				&& allGoodTimeMappings
  				&& !this.isCancelled()){
  		
  			ds.setNormalTimeArray(getNormalTimeArray(ds.getNucSimDataStructureArray()));
  			ds.setMaxNumberTimePoints(getMaxNumberTimePoints());
  			ds.setTimeDataArrayThermo(getTimeDataArrayThermo());
  			ds.setTempDataArray(getTempDataArray());
  			ds.setDensityDataArray(getDensityDataArray());
        	ds.setTimemaxThermo(500);
			ds.setTimeminThermo(-500);
			ds.setTempmin(getTempmin() - 1);
			ds.setTempmax(getTempmax() + 1);
			ds.setDensitymin(getDensitymin() - 1);
			ds.setDensitymax(getDensitymax() + 1);
			
			parent.openThermoPlotFrame();
			
  		}

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
    
	private double[][] getTimeDataArrayThermo(){

  		double[][] tempArray = new double[ds.getNumberNucSimDataStructures()][ds.getMaxNumberTimePoints()];
	
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

			tempArray[i] = getNormalTimeArray(ds.getNucSimDataStructureArray()[i].getTimeArray(), i);
					
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
    
	private double[][] getTempDataArray(){
		  
  		double[][] tempArray = new double[ds.getNumberNucSimDataStructures()][ds.getMaxNumberTimePoints()];
 
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			
			tempArray[i] = ds.getNucSimDataStructureArray()[i].getTempArray();
			
		}
       
    	return tempArray;
       
	}
	
	private double[][] getDensityDataArray(){

		double[][] tempArray = new double[ds.getNumberNucSimDataStructures()][ds.getMaxNumberTimePoints()];

		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

			tempArray[i] = ds.getNucSimDataStructureArray()[i].getDensityArray();

		}

		return tempArray;

	}
	
	public double getTempmin(){
	    
   		double newTempmin = 1e30;
		
		double tempMin = 0.0;
		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
    		
			for(int j=0; j<ds.getTempDataArray()[i].length; j++){

				newTempmin = Math.min(newTempmin, ds.getTempDataArray()[i][j]);
		
			}
	    	
    	}
		
		tempMin = newTempmin;

		return tempMin;
   
  	}
	
	public double getTempmax(){
    
   		double newTempmax = 0.0;
		
		double tempMax = 0.0;
		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
    		
			for(int j=0; j<ds.getTempDataArray()[i].length; j++){

				newTempmax = Math.max(newTempmax, ds.getTempDataArray()[i][j]);

			}
	    	
    	}
		
		tempMax = newTempmax;

		return tempMax;
   
  	}
    
    public int getDensitymin(){
    
   		double newDensitymin = 1e30;
		
		int densityMin = 0;
		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
    		
			for(int j=0; j<ds.getDensityDataArray()[i].length; j++){

				newDensitymin = Math.min(newDensitymin, ds.getDensityDataArray()[i][j]);
		
			}
	    	
    	}
		
		densityMin = (int)Math.floor(0.434294482*Math.log(newDensitymin));

		return densityMin;
   
  	}

	public int getDensitymax(){
    
   		double newDensitymax = 0.0;
		
		int densityMax = 0;
		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
    		
			for(int j=0; j<ds.getDensityDataArray()[i].length; j++){
				newDensitymax = Math.max(newDensitymax, ds.getDensityDataArray()[i][j]);
		
			}
	    	
    	}
		
		densityMax = (int)Math.ceil(0.434294482*Math.log(newDensitymax));

		return densityMax;
   
  	}
	
}