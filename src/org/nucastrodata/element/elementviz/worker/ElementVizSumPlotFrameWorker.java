package org.nucastrodata.element.elementviz.worker;

import java.awt.Point;
import java.util.HashMap;
import java.util.TreeMap;
import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.AbundTimestepDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.ElementVizFrame;
import org.nucastrodata.element.elementviz.ElementVizToolsPanel;
import org.nucastrodata.io.FileGetter;
import org.nucastrodata.dialogs.ProgressBarDialog;

public class ElementVizSumPlotFrameWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private ElementVizFrame frame;
	private ElementVizToolsPanel parent;
	private ProgressBarDialog dialog;
	
	private TreeMap<String, HashMap<String, TreeMap<Integer, Double>>> finalAbundMap;
	private boolean allGoodIsotopeMappings
			, allGoodAbundProfiles
			, allGoodTimeMappings;	

	public ElementVizSumPlotFrameWorker(ElementVizDataStructure ds
											, ElementVizFrame frame
											, ElementVizToolsPanel parent){
											
		this.ds  = ds;
		this.parent = parent;
		this.frame = frame;
		
		dialog = new ProgressBarDialog(frame, "", this);
	}

	protected Void doInBackground(){
	
		allGoodIsotopeMappings = true;
		allGoodAbundProfiles = true;
		allGoodTimeMappings = true;
    	
    	dialog.open();
    	dialog.setCurrentValue(0);
    	dialog.setMaximum(ds.getNumberNucSimDataStructures());
    	ds.setIsotopes("");
		ds.setFinal_step("Y");
		
		String lastSubPath = "";
		NucSimDataStructure lastNSDS = null;
		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

			NucSimDataStructure nsds = ds.getNucSimDataStructureArrayFinalAbund()[i];

			nsds.setAbundTimestepDataStructureArray(null);

			ds.setPath(nsds.getPath());
			ds.setZone(String.valueOf(nsds.getZone()));
			ds.setZones(String.valueOf(nsds.getZone()));
			ds.setCurrentNucSimDataStructure(nsds);
			
			if(isCancelled()){break;}
			
			if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS TIME MAPPING", frame)){allGoodTimeMappings = false;}
			
			if(isCancelled()){break;}
			
			String currentPath = nsds.getPath();
			if(currentPath.contains("_dir_")){
				String currentSubPath = currentPath.substring(0, currentPath.lastIndexOf("_dir_"));
				if(!currentSubPath.equals(lastSubPath)){
					if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", frame)){allGoodIsotopeMappings = false;}
					lastSubPath = currentSubPath;
					lastNSDS = nsds;
				}else{
					nsds.setZAMapArray(lastNSDS.getZAMapArray().clone());
					nsds.setIsotopeLabelMapArray(lastNSDS.getIsotopeLabelMapArray().clone());
				}
			}else{
				if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", frame)){allGoodIsotopeMappings = false;}
			}

			if(isCancelled()){break;}

			dialog.setCurrentValue(0);
			dialog.appendText("Downloading final abundance data for "
										+ ds.getNucSimDataStructureArrayFinalAbund()[i].getNucSimName()
										+ "...");
    		
    		if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ABUNDANCES", frame, dialog)){
    			dialog.appendText("DONE!\n");
    		}else{
    			allGoodAbundProfiles = false;
    		}	

        }
        
		finalAbundMap = new TreeMap<String, HashMap<String, TreeMap<Integer, Double>>>();
		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			
			String name = ds.getNucSimDataStructureArrayFinalAbund()[i].getNucSimName();
			
			finalAbundMap.put(name, new HashMap<String, TreeMap<Integer, Double>>());
			finalAbundMap.get(name).put("A", new TreeMap<Integer, Double>());
			finalAbundMap.get(name).put("Z", new TreeMap<Integer, Double>());
			finalAbundMap.get(name).put("N", new TreeMap<Integer, Double>());
			
			getFinalAbundMapsForNucSim(ds.getNucSimDataStructureArrayFinalAbund()[i], finalAbundMap.get(name));
			
		}

		String solarListContents = new String(FileGetter.getFileByName("AbundanceData/Solar/list"));
		String[] solarArray = solarListContents.split("\\n");
		
		for(int i=0; i<solarArray.length; i++){
			getFinalAbundMapForDataset("Solar", solarArray[i], finalAbundMap);
		}
		
		String rprocessListContents = new String(FileGetter.getFileByName("AbundanceData/R_Process/list"));
		String[] rprocessArray = rprocessListContents.split("\\n");
		
		for(int i=0; i<rprocessArray.length; i++){
			getFinalAbundMapForDataset("R_Process", rprocessArray[i], finalAbundMap);
		}
		
		return null;
	}

	protected void done(){
	
		dialog.close();

		if(allGoodAbundProfiles 
				&& allGoodIsotopeMappings
				&& allGoodTimeMappings
				&& !this.isCancelled()){

			parent.openSumPlotFrame(finalAbundMap);
    		
		}
		
	}
    
	private void getFinalAbundMapForDataset(String type, String name, TreeMap<String, HashMap<String, TreeMap<Integer, Double>>> finalAbundMap){
		
		finalAbundMap.put(name, new HashMap<String, TreeMap<Integer, Double>>());
		HashMap<String, TreeMap<Integer, Double>> map = finalAbundMap.get(name);
		
		TreeMap<Integer, Double> AMap = new TreeMap<Integer, Double>();
		TreeMap<Integer, Double> ZMap = new TreeMap<Integer, Double>();
		TreeMap<Integer, Double> NMap = new TreeMap<Integer, Double>();
		
		map.put("Z", ZMap);
		map.put("N", NMap);
		map.put("A", AMap);
		
		String data = new String(FileGetter.getFileByName("AbundanceData/" + type + "/" + name + ".dat"));
		String[] lineArray = data.split("\\n");
		for(int i=0; i<lineArray.length; i++){
			String line = lineArray[i];
			if(line.indexOf("#")==-1){
			
				String[] valueArray = line.split("    ");

				if(valueArray.length==3){
					int z = Integer.valueOf(valueArray[0]);
					int a = Integer.valueOf(valueArray[1]);
					int n = a - z;
					double abund = Double.valueOf(valueArray[2]);
					
					if(!AMap.containsKey(a)){
						AMap.put(a, abund);
					}else{
						AMap.put(a, AMap.get(a) + abund);
					}
					
					if(!ZMap.containsKey(z)){
						ZMap.put(z, abund);
					}else{
						ZMap.put(z, ZMap.get(z) + abund);
					}
					
					if(!NMap.containsKey(n)){
						NMap.put(n, abund);
					}else{
						NMap.put(n, NMap.get(n) + abund);
					}
					
				}else{
					
					int z = Integer.valueOf(valueArray[0]);
					double abund = Double.valueOf(valueArray[1]);
					
					if(!ZMap.containsKey(z)){
						ZMap.put(z, abund);
					}else{
						ZMap.put(z, ZMap.get(z) + abund);
					}
					
				}
			}
		}
	}

	private void getFinalAbundMapsForNucSim(NucSimDataStructure sim, HashMap<String, TreeMap<Integer, Double>> map){

		AbundTimestepDataStructure atds = sim.getAbundTimestepDataStructureArray()[0];
		int[] indexArray = atds.getIndexArray();
		float[] abundArray = atds.getAbundArray();
		Point[] pointArray = sim.getZAMapArray();
		
		for(int i=0; i<indexArray.length; i++){
			
			int z = (int) pointArray[indexArray[i]].getX();
			int a = (int) pointArray[indexArray[i]].getY();
			int n = a - z;
			Double abund =  (double) abundArray[i];
			
			TreeMap<Integer, Double> AMap = map.get("A");
			TreeMap<Integer, Double> ZMap = map.get("Z");
			TreeMap<Integer, Double> NMap = map.get("N");
			
			if(!AMap.containsKey(a)){
				AMap.put(a, abund);
			}else{
				AMap.put(a, AMap.get(a) + abund);
			}
			
			if(!ZMap.containsKey(z)){
				ZMap.put(z, abund);
			}else{
				ZMap.put(z, ZMap.get(z) + abund);
			}
			
			if(!NMap.containsKey(n)){
				NMap.put(n, abund);
			}else{
				NMap.put(n, NMap.get(n) + abund);
			}
			
		}
		
	}
	
}