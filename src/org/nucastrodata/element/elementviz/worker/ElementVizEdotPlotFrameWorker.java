package org.nucastrodata.element.elementviz.worker;

import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.ElementVizFrame;
import org.nucastrodata.element.elementviz.ElementVizToolsPanel;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.dialogs.ProgressBarDialog;

public class ElementVizEdotPlotFrameWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private ElementVizToolsPanel parent;
	private ProgressBarDialog dialog;
	private ElementVizFrame frame;
	
	boolean allGoodTimeMappings
			, allGoodEdotValues
			, allGoodThermoProfiles;	

	public ElementVizEdotPlotFrameWorker(ElementVizDataStructure ds
											, ElementVizFrame frame
											, ElementVizToolsPanel parent){
		this.ds  = ds;
		this.parent = parent;
		this.frame = frame;
		
		dialog = new ProgressBarDialog(frame, "", this);
	}

	protected Void doInBackground(){
	
		allGoodEdotValues = true;
    	allGoodTimeMappings = true;
    	allGoodThermoProfiles = true;
	
    	dialog.open();
    	dialog.setCurrentValue(0);
    	dialog.setMaximum(ds.getNumberNucSimDataStructures());
	
    	for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

    		NucSimDataStructure nsds = ds.getNucSimDataStructureArray()[i];
			ds.setPath(nsds.getPath());
			ds.setZone(String.valueOf(nsds.getZone()));
			ds.setZones(String.valueOf(nsds.getZone()));
			ds.setCurrentNucSimDataStructure(nsds);
			
			dialog.appendText("Downloading nuclear energy generation data for " + nsds.getNucSimName() + "...");
			
			if(isCancelled()){break;}

			if(isCancelled()){break;}
			boolean flag = Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS TIME MAPPING", frame);
			if(!flag){
				allGoodTimeMappings = false;
			}

			if(isCancelled()){break;}
			
			if(isCancelled()){break;}
			flag = Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS THERMO PROFILE", frame);
			if(!flag){
				allGoodTimeMappings = false;
			}

			if(Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds, CGICom.GET_ELEMENT_SYNTHESIS_EDOT_VALUES, frame)){
				dialog.appendText("DONE!\n");
    			dialog.setCurrentValue(i+1);
    			allGoodEdotValues = true;
			}else{
				allGoodEdotValues = false;
			}

			

    	}
	
		return null;
	}

	protected void done(){
	
		dialog.close();
		
  		if(allGoodEdotValues 
  				&& allGoodTimeMappings
  				&& !this.isCancelled()){
  		
  			if(edotExists()){
  		
	  			ds.setNormalTimeArray(getNormalTimeArray(ds.getNucSimDataStructureArray()));
	  			ds.setMaxNumberTimePoints(getMaxNumberTimePoints());
	  			ds.setTimeDataArrayEdot(getTimeDataArrayEdot());
	  			ds.setEdotDataArray(getEdotDataArray());
	        	ds.setTimemaxEdot(500);
				ds.setTimeminEdot(-500);
				ds.setEdotmin(getEdotmin());
				ds.setEdotmax(getEdotmax());
				
				parent.openEdotPlotFrame();
				
  			}else{
  			
  				String error = "Nuclear energy generation data is not available for any of the selected element synthesis simulations.";
  				AttentionDialog.createDialog(frame, error);
  			
  			}
			
  		}

	}
	
	private boolean edotExists(){
		boolean flag = false;
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			if(ds.getNucSimDataStructureArray()[i].getEdotArray().length > 1){
				return true;
			}
		}
		return flag;
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
    
	private double[][] getTimeDataArrayEdot(){

  		/*double[][] tempArray = new double[ds.getNumberNucSimDataStructures()][];
	
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

			tempArray[i] = getNormalTimeArray(ds.getNucSimDataStructureArray()[i].getTimeArray(), i);
					
		}
       
		double[][] tempArray2 = new double[ds.getNumberNucSimDataStructures()][getMaxNumberTimePoints()-1];
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			for(int j=1; j<tempArray[i].length; j++){
				tempArray2[i][j-1] = tempArray[i][j];
			}
		}

		return tempArray;*/
		
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
    
	private double[][] getEdotDataArray(){
	
		double[][] tempArray = new double[ds.getNumberNucSimDataStructures()][];

		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

			tempArray[i] = ds.getNucSimDataStructureArray()[i].getEdotArray();

		}

		return tempArray;

	}
    
    public int getEdotmin(){
    
   		double newEdotmin = 1e30;
		
		int edotMin = 0;
		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
    		
    		if(ds.getEdotDataArray()[i].length > 1){
    		
				for(int j=0; j<ds.getEdotDataArray()[i].length; j++){
	
					if(ds.getEdotDataArray()[i][j] > 1.0){
	
						newEdotmin = Math.min(newEdotmin, ds.getEdotDataArray()[i][j]);
					
					}
			
				}
				
    		}
	    	
    	}
		
		edotMin = (int)Math.floor(0.434294482*Math.log(newEdotmin));

		return edotMin;
   
  	}

	public int getEdotmax(){
    
   		double newEdotmax = 0.0;
		
		int edotMax = 0;
		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
    		
			if(ds.getEdotDataArray()[i].length > 1){
    		
				for(int j=0; j<ds.getEdotDataArray()[i].length; j++){
					newEdotmax = Math.max(newEdotmax, ds.getEdotDataArray()[i][j]);
			
				}
			
			}
	    	
    	}
		
		edotMax = (int)Math.ceil(0.434294482*Math.log(newEdotmax));

		return edotMax;
   
  	}
	
}