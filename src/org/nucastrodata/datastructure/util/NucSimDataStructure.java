package org.nucastrodata.datastructure.util;

import java.awt.*; 
import java.util.*;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.feature.BottleDataStructure;
import org.nucastrodata.datastructure.feature.FluxDataStructure;
import org.nucastrodata.datastructure.feature.SumDataStructure;
import org.nucastrodata.datastructure.feature.WaitingDataStructure;

public class NucSimDataStructure implements DataStructure{
	
	private String path, nucSimName, reactionRate;
	private int zone;
	private double fluxNorm, scaleFactor;
	private double[] timeArray, tempArray, densityArray, intFluxArray, edotArray;
	private AbundTimestepDataStructure[] abundTimestepDataStructureArray;
	private DerAbundTimestepDataStructure[] derAbundTimestepDataStructureArray;
	private FluxTimestepDataStructure[] fluxTimestepDataStructureArray;
	private AbundTimestepDataStructure currentAbundTimestepDataStructure;
	private Point[] ZAMapArray, reactionMapArray;
	private String[] isotopeLabelMapArray;
	private Vector indexViktor, labelViktor;
	private boolean hasFluxes, isSimSens;
	private short[] intFluxRedArray, intFluxGreenArray, intFluxBlueArray;
	private byte[] intFluxLinestyleArray, intFluxLinewidthArray;
	private WaitingDataStructure waitingDataStructure;
	private BottleDataStructure bottleDataStructure;
	private SumDataStructure sumDataStructure;
	private FluxDataStructure fluxDataStructure;

	public NucSimDataStructure(){initialize();}
	
	public void initialize(){
		setWaitingDataStructure(null);
		setBottleDataStructure(null);
		setSumDataStructure(null);
		setFluxDataStructure(null);
		
		setReactionRate("");
		setPath("");
		setZone(-1);
		setNucSimName("");
		setFluxNorm(0.0);
		setScaleFactor(0.0);
		
		double[] initTimeArray = {0.0};
		setTimeArray(initTimeArray);
		double[] initTempArray = {0.0};
		setTempArray(initTempArray);
		double[] initDensityArray = {0.0};
		setDensityArray(initDensityArray);
		double[] initEdotArray = {0.0};
		setEdotArray(initEdotArray);
		
		setAbundTimestepDataStructureArray(null);
		setDerAbundTimestepDataStructureArray(null);
		setFluxTimestepDataStructureArray(null);
		setCurrentAbundTimestepDataStructure(null);
		
		Point[] initZAMapArray = {new Point(0, 0)};
		setZAMapArray(initZAMapArray);
		Point[] initReactionMapArray = {new Point(0, 0)};
		setReactionMapArray(initReactionMapArray);

		setIsotopeLabelMapArray(null);
		setIndexViktor(new Vector());
		setLabelViktor(new Vector());
		setHasFluxes(true);
		setIsSimSens(false);

		setIntFluxArray(null);
		setIntFluxLinestyleArray(null);
		setIntFluxLinewidthArray(null);
		setIntFluxRedArray(null);
		setIntFluxGreenArray(null);
		setIntFluxBlueArray(null);
		
	}
	
	public double getScaleFactor(){return scaleFactor;}
	public void setScaleFactor(double scaleFactor){this.scaleFactor = scaleFactor;}
	
	public boolean getIsSimSens(){return isSimSens;}
	public void setIsSimSens(boolean newIsSimSens){isSimSens = newIsSimSens;}
	
	public SumDataStructure getSumDataStructure(){return sumDataStructure;}
	public void setSumDataStructure(SumDataStructure sumDataStructure){
		this.sumDataStructure = sumDataStructure;
		if(this.sumDataStructure!=null){
			this.sumDataStructure.setSim(this);
		}
	}
	
	public FluxDataStructure getFluxDataStructure(){return fluxDataStructure;}
	public void setFluxDataStructure(FluxDataStructure fluxDataStructure){
		this.fluxDataStructure = fluxDataStructure;
		if(this.fluxDataStructure!=null){
			this.fluxDataStructure.setSim(this);
		}
	}
	
	public BottleDataStructure getBottleDataStructure(){return bottleDataStructure;}
	public void setBottleDataStructure(BottleDataStructure bottleDataStructure){this.bottleDataStructure = bottleDataStructure;}

	public WaitingDataStructure getWaitingDataStructure(){return waitingDataStructure;}
	public void setWaitingDataStructure(WaitingDataStructure waitingDataStructure){this.waitingDataStructure = waitingDataStructure;}
	
	public String getPath(){return path;}
	public void setPath(String newPath){path = newPath;}

	public String getReactionRate(){return reactionRate;}
	public void setReactionRate(String reactionRate){this.reactionRate = reactionRate;}

	public double getFluxNorm(){return fluxNorm;}
	public void setFluxNorm(double fluxNorm){this.fluxNorm = fluxNorm;}

	public int getZone(){return zone;}
	public void setZone(int newZone){zone = newZone;}

	public String getNucSimName(){return nucSimName;}
	public void setNucSimName(String newNucSimName){nucSimName = newNucSimName;}
	
	public double[] getTimeArray(){return timeArray;}
	public void setTimeArray(double[] newTimeArray){timeArray = newTimeArray;}

	public double[] getTempArray(){return tempArray;}
	public void setTempArray(double[] newTempArray){tempArray = newTempArray;}
	
	public double[] getDensityArray(){return densityArray;}
	public void setDensityArray(double[] newDensityArray){densityArray = newDensityArray;}
	
	public double[] getEdotArray(){return edotArray;}
	public void setEdotArray(double[] newEdotArray){edotArray = newEdotArray;}
	
	public AbundTimestepDataStructure[] getAbundTimestepDataStructureArray(){return abundTimestepDataStructureArray;}
	public void setAbundTimestepDataStructureArray(AbundTimestepDataStructure[] newAbundTimestepDataStructureArray){abundTimestepDataStructureArray = newAbundTimestepDataStructureArray;}
	
	public DerAbundTimestepDataStructure[] getDerAbundTimestepDataStructureArray(){return derAbundTimestepDataStructureArray;}
	public void setDerAbundTimestepDataStructureArray(DerAbundTimestepDataStructure[] newDerAbundTimestepDataStructureArray){derAbundTimestepDataStructureArray = newDerAbundTimestepDataStructureArray;}

	public FluxTimestepDataStructure[] getFluxTimestepDataStructureArray(){return fluxTimestepDataStructureArray;}
	public void setFluxTimestepDataStructureArray(FluxTimestepDataStructure[] newFluxTimestepDataStructureArray){fluxTimestepDataStructureArray = newFluxTimestepDataStructureArray;}
	
	public AbundTimestepDataStructure getCurrentAbundTimestepDataStructure(){return currentAbundTimestepDataStructure;}
	public void setCurrentAbundTimestepDataStructure(AbundTimestepDataStructure newCurrentAbundTimestepDataStructure){currentAbundTimestepDataStructure = newCurrentAbundTimestepDataStructure;}
	
	public Point[] getZAMapArray(){return ZAMapArray;}
	public void setZAMapArray(Point[] newZAMapArray){ZAMapArray = newZAMapArray;}
	
	public Point[] getReactionMapArray(){return reactionMapArray;}
	public void setReactionMapArray(Point[] newReactionMapArray){reactionMapArray = newReactionMapArray;}
	
	public String[] getIsotopeLabelMapArray(){return isotopeLabelMapArray;}
	public void setIsotopeLabelMapArray(String[] newIsotopeLabelMapArray){isotopeLabelMapArray = newIsotopeLabelMapArray;}
	
	public Vector getIndexViktor(){return indexViktor;}
	public void setIndexViktor(Vector newIndexViktor){indexViktor = newIndexViktor;}
	
	public Vector getLabelViktor(){return labelViktor;}
	public void setLabelViktor(Vector newLabelViktor){labelViktor = newLabelViktor;}
	
	public boolean getHasFluxes(){return hasFluxes;}
	public void setHasFluxes(boolean newHasFluxes){hasFluxes = newHasFluxes;}
	
	public double[] getIntFluxArray(){return intFluxArray;}
	public void setIntFluxArray(double[] newIntFluxArray){intFluxArray = newIntFluxArray;}
	
	public short[] getIntFluxRedArray(){return intFluxRedArray;}
	public void setIntFluxRedArray(short[] newIntFluxRedArray){intFluxRedArray = newIntFluxRedArray;}
	
	public short[] getIntFluxGreenArray(){return intFluxGreenArray;}
	public void setIntFluxGreenArray(short[] newIntFluxGreenArray){intFluxGreenArray = newIntFluxGreenArray;}

	public short[] getIntFluxBlueArray(){return intFluxBlueArray;}
	public void setIntFluxBlueArray(short[] newIntFluxBlueArray){intFluxBlueArray = newIntFluxBlueArray;}
	
	public byte[] getIntFluxLinestyleArray(){return intFluxLinestyleArray;}
	public void setIntFluxLinestyleArray(byte[] newIntFluxLinestyleArray){intFluxLinestyleArray = newIntFluxLinestyleArray;}
	
	public byte[] getIntFluxLinewidthArray(){return intFluxLinewidthArray;}
	public void setIntFluxLinewidthArray(byte[] newIntFluxLinewidthArray){intFluxLinewidthArray = newIntFluxLinewidthArray;}
	
}