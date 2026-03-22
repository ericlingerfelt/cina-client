package org.nucastrodata.element.elementviz;

import javax.swing.*;
import javax.swing.SwingWorker;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.AbundTimestepDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;
import org.nucastrodata.CinaCGIComm;
import org.nucastrodata.PrintfFormat;
import org.nucastrodata.element.elementviz.ElementVizPointDialog;
import org.nucastrodata.element.elementviz.ElementVizPointFrame;
import org.nucastrodata.element.elementviz.ExportFileTask;
import org.nucastrodata.element.elementviz.OpenTask;
import org.nucastrodata.wizard.gui.CustomFileFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import info.clearthought.layout.*;

import java.util.*;


/**
 * The Class ElementVizPointFrame.
 */
public class ElementVizPointFrame extends JFrame implements ActionListener{

	/** The nmax. */
	private JTextField timeField1, timeField2, zmin, zmax, nmin, nmax;
	
	/** The export button. */
	private JButton exportButton;
	
	/** The file. */
	private File file;
	
	/** The dialog. */
	private ElementVizPointDialog dialog;
	
	/** The task. */
	private ExportFileTask task;
	
	/** The ds. */
	private ElementVizDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CinaCGIComm cgiCom;
	
	/** The box. */
	private JComboBox box;
	
	/** The check. */
	private JCheckBox check; 
	
	/** The flux button. */
	private JRadioButton rateButton, fluxButton;
	
	/**
	 * Instantiates a new element viz point frame.
	 *
	 * @param ds the ds
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 */
	public ElementVizPointFrame(ElementVizDataStructure ds
									, MainDataStructure mds
									, CinaCGIComm cgiCom){
		
		this.ds = ds;
		this.mds = mds;
		this.cgiCom = cgiCom;
		
		setTitle("Waiting Point File Exporter");
		setSize(260, 400);
		
		Container c = getContentPane();
		
		double[] col = {20, TableLayoutConstants.PREFERRED, 20, TableLayoutConstants.PREFERRED, 20};
		double[] row = {20, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED, 20};
		c.setLayout(new TableLayout(col, row));
		
		timeField1 = new JTextField();
		timeField2 = new JTextField();
		zmin = new JTextField();
		zmax = new JTextField();
		nmin = new JTextField();
		nmax = new JTextField();
		
		JLabel timeLabel1 = new JLabel("Timestep min: ");
		timeLabel1.setFont(Fonts.textFont);
		
		JLabel timeLabel2 = new JLabel("Timestep max: ");
		timeLabel2.setFont(Fonts.textFont);
		
		JLabel zminLabel = new JLabel("Z min: ");
		zminLabel.setFont(Fonts.textFont);
		
		JLabel zmaxLabel = new JLabel("Z max: ");
		zmaxLabel.setFont(Fonts.textFont);
		
		JLabel  nminLabel = new JLabel("N min: ");
		nminLabel.setFont(Fonts.textFont);
		
		JLabel  nmaxLabel = new JLabel("N max: ");
		nmaxLabel.setFont(Fonts.textFont);
		
		exportButton = new JButton("Export File");
		exportButton.setFont(Fonts.buttonFont);
		exportButton.addActionListener(this);
		
		box = new JComboBox();
		box.setFont(Fonts.textFont);
		
		check = new JCheckBox("Write Rates at All Timesteps", false);
		check.setFont(Fonts.textFont);
		check.setEnabled(false);
		
		rateButton = new JRadioButton("Export Rates", false);
		rateButton.setFont(Fonts.textFont);
		rateButton.addActionListener(this);
		
		fluxButton = new JRadioButton("Export Fluxes", true);
		fluxButton.setFont(Fonts.textFont);
		fluxButton.addActionListener(this);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rateButton);
		group.add(fluxButton);
		
		c.add(box, "1, 1, 3, 1, f, c");
		c.add(fluxButton, "1, 3, 3, 3, f, c");
		c.add(rateButton, "1, 5, 3, 5, f, c");
		c.add(check, "1, 7, 3, 7, f, c");
		c.add(timeLabel1, "1, 9");
		c.add(timeField1, "3, 9");
		c.add(timeLabel2, "1, 11");
		c.add(timeField2, "3, 11");
		c.add(zminLabel, "1, 13");
		c.add(zmin, "3, 13");
		c.add(zmaxLabel, "1, 15");
		c.add(zmax, "3, 15");
		c.add(nminLabel, "1, 17");
		c.add(nmin, "3, 17");
		c.add(nmaxLabel, "1, 19");
		c.add(nmax, "3, 19");
		c.add(exportButton, "1, 21, 3, 21");
		
		String string = "";
		dialog = new ElementVizPointDialog(this, string);
		
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param timestepMin the timestep min
	 * @param timestepMax the timestep max
	 */
	public void setCurrentState(int timestepMin, int timestepMax){
		timeField1.setText(String.valueOf(timestepMin));
		timeField2.setText(String.valueOf(timestepMax));
		
		Point[] array = ds.getAnimatorNucSimDataStructure().getZAMapArray();
		zmin.setText(String.valueOf((int)array[0].getX()));
		zmax.setText(String.valueOf((int)array[array.length-1].getX()));
		nmin.setText(String.valueOf((int)(array[0].getY()-array[0].getX())));
		nmax.setText(String.valueOf((int)(array[array.length-1].getY() - array[array.length-1].getX())));
		
		box.removeAllItems();
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
			box.addItem(ds.getPublicLibraryDataStructureArray()[i].getLibName());
		}
		
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
			box.addItem(ds.getSharedLibraryDataStructureArray()[i].getLibName());
		}
		
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
			box.addItem(ds.getUserLibraryDataStructureArray()[i].getLibName());
		}
		file = null;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==exportButton){
			JFileChooser chooser = new JFileChooser();
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.addChoosableFileFilter(new CustomFileFilter("txt", "Text Document (*.txt)"));
			int returnVal = chooser.showDialog(this, "Export Waiting Point File");
			if(returnVal==JFileChooser.APPROVE_OPTION){
				Calendar c = Calendar.getInstance();
				chooser.getSelectedFile();
				file = chooser.getSelectedFile();
				dialog.open();
				(task = new ExportFileTask(dialog, ds, mds, cgiCom, file
										, this
										, Integer.valueOf(timeField1.getText())
										, Integer.valueOf(timeField2.getText())
										, Integer.valueOf(zmin.getText())
										, Integer.valueOf(zmax.getText())
										, Integer.valueOf(nmin.getText())
										, Integer.valueOf(nmax.getText())
										, box.getSelectedItem().toString()
										, rateButton.isSelected()
										, check.isSelected())).execute();
			}
		}else if(ae.getSource()==rateButton || ae.getSource()==fluxButton){
			check.setEnabled(rateButton.isSelected());
		}
	}
}

class ExportFileTask extends SwingWorker<Void, Void>{
	
	private ElementVizPointDialog dialog;
	private ElementVizDataStructure ds;
	private MainDataStructure mds;
	private CinaCGIComm cgiCom;
	private File file;
	private ElementVizPointFrame parent;
	private int time1, time2, zmin, zmax, nmin, nmax, type;
	public static final int NO_RATES = 0;
	public static final int YES_RATES = 1;
	public static final int ALL_RATES = 2;
	public static final int YES_FLUX = 3;
	private String library;
	private boolean allRates, doRates;
	
	public ExportFileTask(ElementVizPointDialog dialog
							, ElementVizDataStructure ds
							, MainDataStructure mds
							, CinaCGIComm cgiCom
							, File file
							, ElementVizPointFrame parent
							, int time1
							, int time2
							, int zmin
							, int zmax
							, int nmin
							, int nmax
							, String library
							, boolean doRates
							, boolean allRates){
		
		this.dialog = dialog;
		this.ds = ds;
		this.mds = mds;
		this.cgiCom = cgiCom;
		this.parent = parent;
		this.file = file;
		this.time1 = time1;
		this.time2 = time2;
		this.zmin = zmin;
		this.zmax = zmax;
		this.nmin = nmin;
		this.nmax = nmax;
		this.library = library;
		this.allRates = allRates;
		this.doRates = doRates;
	}	
	
	protected Void doInBackground(){
		String string = "";
		try{
			FileOutputStream fos;
			if(doRates){
				if(!allRates){
					type = NO_RATES;
					fos = new FileOutputStream(file);
					getFileString(fos);
					fos.close();
					dialog.addText("DONE!");
					type = YES_RATES;
					setRateMap();
					fos = new FileOutputStream(new File(file.getAbsolutePath() + "_with_rates"));
					getFileString(fos);
					fos.close();
					dialog.addText("DONE!");
				}else{
					type = ALL_RATES;
					setRateMap();
					fos = new FileOutputStream(new File(file.getAbsolutePath() + "_with_all_rates"));
					getFileString(fos);
					fos.close();
					dialog.addText("DONE!");
				}
			}else{
				type = YES_FLUX;
				setRateMapFlux();
				fos = new FileOutputStream(file);
				getFileStringFlux(fos);
				fos.close();
				dialog.addText("DONE!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private void setRateMapFlux(){
		HashMap<Integer, HashMap<String, RateDataStructure>> map = new HashMap<Integer, HashMap<String, RateDataStructure>>();
		ds.map = map;
		NucSimDataStructure nsds = ds.getAnimatorNucSimDataStructure();
		dialog.addText("\nImporting reaction rate parameters from database...");
		dialog.bar.setMaximum(nsds.getZAMapArray().length-1-2);
		for(int zaIndex=0; zaIndex<nsds.getZAMapArray().length; zaIndex++){
			int z = (int)nsds.getZAMapArray()[zaIndex].getX();
			int n = (int)(nsds.getZAMapArray()[zaIndex].getY()-nsds.getZAMapArray()[zaIndex].getX());
			if(z>=zmin && z<=zmax && n>=nmin && n<=nmax && !((z==1 && n==0) || (z==0 && n==1))){
				HashMap<String, RateDataStructure> rateMap = new HashMap<String, RateDataStructure>();
				map.put(zaIndex, rateMap);
				HashSet<String> rateIDList = new HashSet<String>();
				
				int type=0;
				int reactionType = getReactionType(type);
				Point newZAPoint = getZAPoint(z, z+n, type);
				String reactionString = getReactionString(z, z+n, type);
				String decay = getDecay(type);
				String rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, decay);
				rateIDList.add(rateID);
				if(decay.equals("bet+")){
					rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "ec");
					rateIDList.add(rateID);
					rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
					rateIDList.add(rateID);
				}
				
				type=7;
				reactionType = getReactionType(type);
				newZAPoint = getZAPoint(z, z+n, type);
				reactionString = getReactionString(z, z+n, type);
				decay = getDecay(type);
				rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, decay);
				rateIDList.add(rateID);
				if(decay.equals("bet+")){
					rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "ec");
					rateIDList.add(rateID);
					rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
					rateIDList.add(rateID);
				}
				
				String rates = "";
				Iterator<String> itrList = rateIDList.iterator();
				while(itrList.hasNext()){
					rates += itrList.next();
					if(itrList.hasNext()){
						rates += "\n";
					}
				}
				ds.zaIndex = zaIndex;
				ds.setRates(rates);
				if(Cina.cinaCGIComm.doCGICall("RATES EXIST", parent)){
					rates = "";
					Iterator<String> itr = rateMap.keySet().iterator();
					ds.setProperties("Number of Parameters\u0009Parameters\u0009Q-value");
					while(itr.hasNext()){
						rates += itr.next();
						if(itr.hasNext()){
							rates += "\n";
						}
					}
					ds.setRates(rates);
					Cina.cinaCGIComm.doCGICall("GET RATE INFO", parent);
					dialog.bar.setValue(zaIndex);
				}
			}
		}
		dialog.addText("DONE!");
	}
	
	private String getFileStringFlux(FileOutputStream fos) throws IOException{
		String doubleFormat = "%13.6E"; 
		NucSimDataStructure nsds = ds.getAnimatorNucSimDataStructure();
		StringBuilder sb = new StringBuilder();
		dialog.bar.setMaximum(time2-time1+1);
		dialog.bar.setValue(0);
		dialog.addText("\nWriting file...");
		//NUMBER OF ISOTOPES
		sb.append(nsds.getZAMapArray().length-2);
		sb.append("\n");
		//NUMBER OF TIMESTEPS
		sb.append(time2-time1+1);
		sb.append("\n");
		fos.write(sb.toString().getBytes());
		sb = new StringBuilder();
		for(int timeIndex=time1; timeIndex<time2+1; timeIndex++){
			//TIMESTEP
			if(timeIndex<10){
				sb.append("   " + timeIndex);
			}else if(timeIndex>9 && timeIndex<100){
				sb.append("  " + timeIndex);
			}else if(timeIndex>99 && timeIndex<1000){
				sb.append(" " + timeIndex);
			}else if(timeIndex>999){
				sb.append(timeIndex);
			}
			sb.append(" ");
			//TIME
			sb.append(new PrintfFormat("%13.12E").sprintf(nsds.getTimeArray()[timeIndex] - nsds.getTimeArray()[0]).trim());
			sb.append(" ");
			//TEMPERATURE
			double temp = Double.valueOf(new PrintfFormat(doubleFormat).sprintf(nsds.getTempArray()[timeIndex]).trim());
			sb.append(new PrintfFormat(doubleFormat).sprintf(nsds.getTempArray()[timeIndex]).trim());
			sb.append(" ");
			//DENSITY
			sb.append(new PrintfFormat(doubleFormat).sprintf(nsds.getDensityArray()[timeIndex]).trim());
			sb.append(" ");
			//Y_p
			sb.append(new PrintfFormat(doubleFormat).sprintf(getAbundance(nsds, timeIndex, 1, 1)).trim());
			sb.append(" ");
			//Y_4He
			sb.append(new PrintfFormat(doubleFormat).sprintf(getAbundance(nsds, timeIndex, 2, 4)).trim());
			sb.append(" ");
			//FLUX_NORM
			sb.append(new PrintfFormat(doubleFormat).sprintf(nsds.getFluxNorm()).trim());
			sb.append(" ");
			sb.append("\n");
			//START ZN.....
			for(int zaIndex=0; zaIndex<nsds.getZAMapArray().length; zaIndex++){
				//Z
				int z = (int)nsds.getZAMapArray()[zaIndex].getX();
				//N
				int n = (int)(nsds.getZAMapArray()[zaIndex].getY()-nsds.getZAMapArray()[zaIndex].getX());
				if(z>=zmin && z<=zmax && n>=nmin && n<=nmax && !((z==1 && n==0) || (z==0 && n==1))){
					if(z<10){
						sb.append("  ");
					}else if(z>=10 && z<100){
						sb.append(" ");
					}
					sb.append(z);
					sb.append(" ");
					if(n<10){
						sb.append("  ");
					}else if(n>=10 && n<100){
						sb.append(" ");
					}
					sb.append(n);
					sb.append(" ");
					//ABUNDANCE
					sb.append(new PrintfFormat(doubleFormat).sprintf(getAbundance(zaIndex, nsds.getAbundTimestepDataStructureArray()[timeIndex])).trim());
					sb.append(" ");
					//RATES
					HashMap<String, RateDataStructure> rateMap = ds.map.get(zaIndex);
					int type=0;
					int reactionType = getReactionType(type);
					Point newZAPoint = getZAPoint(z, z+n, type);
					String reactionString = getReactionString(z, z+n, type);
					String decay = getDecay(type);
					String rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, decay);
					RateDataStructure rds = rateMap.get(rateID);
					double rate = 0.0;
					if(rds!=null && decay.equals("")
							|| rds!=null && decay.equals("bet+")){
						rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
						
						//check for other rates
						rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "ec");
						rds = rateMap.get(rateID);
						if(rds!=null){
							rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
						}
						
						rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
						rds = rateMap.get(rateID);
						if(rds!=null){
							rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
						}
						
					}else if(rds==null && decay.equals("bet+")){
						rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "ec");
						rds = rateMap.get(rateID);
						if(rds!=null){
							rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
							
							rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
							rds = rateMap.get(rateID);
							if(rds!=null){
								rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
							}
						}else{
							rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
							rds = rateMap.get(rateID);
							if(rds!=null){
								rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
							}
						}
					}
					if(rate<1E-30){
						rate = 0.0;
					}
					sb.append(new PrintfFormat(doubleFormat).sprintf(rate));
					sb.append(" ");
					//FLUXES//////////////////////////////////////////////////////////////////////////////
					for(type=1; type<7; type++){
						double flux = 0.0;
						Point outZAPoint = getZAPoint(z, z+n, type);
						int zaIndexOut = getIsotopeIndex(nsds.getZAMapArray(), outZAPoint);
						if(zaIndexOut!=-1){
							int reactionIndex = getReactionIndex(nsds.getReactionMapArray(), zaIndex, zaIndexOut);
							if(reactionIndex!=-1){
								int fluxIndex = getFluxIndex(nsds.getFluxTimestepDataStructureArray()[timeIndex].getIndexArray(), reactionIndex);
								if(fluxIndex!=-1){
									flux = -1*nsds.getFluxTimestepDataStructureArray()[timeIndex].getFluxArray()[fluxIndex];
								}
							}else{
								reactionIndex = getReactionIndex(nsds.getReactionMapArray(), zaIndexOut, zaIndex);
								if(reactionIndex!=-1){
									int fluxIndex = getFluxIndex(nsds.getFluxTimestepDataStructureArray()[timeIndex].getIndexArray(), reactionIndex);
									if(fluxIndex!=-1){
										flux = nsds.getFluxTimestepDataStructureArray()[timeIndex].getFluxArray()[fluxIndex];
									}
								}
							}
						}
						sb.append(new PrintfFormat(doubleFormat).sprintf(flux));
						sb.append(" ");
					}
					/////////////////////////////////////////////////////////////////////////////////////////
					type=7;
					reactionType = getReactionType(type);
					newZAPoint = getZAPoint(z, z+n, type);
					reactionString = getReactionString(z, z+n, type);
					decay = getDecay(type);
					rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, decay);
					rds = rateMap.get(rateID);
					rate = 0.0;
					if(rds!=null && decay.equals("")
							|| rds!=null && decay.equals("bet+")){
						rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
						
						//check for other rates
						rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "ec");
						rds = rateMap.get(rateID);
						if(rds!=null){
							rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
						}
						
						rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
						rds = rateMap.get(rateID);
						if(rds!=null){
							rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
						}
						
					}else if(rds==null && decay.equals("bet+")){
						rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "ec");
						rds = rateMap.get(rateID);
						if(rds!=null){
							rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
							
							rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
							rds = rateMap.get(rateID);
							if(rds!=null){
								rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
							}
						}else{
							rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
							rds = rateMap.get(rateID);
							if(rds!=null){
								rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
							}
						}
					}
					if(rate<1E-30){
						rate = 0.0;
					}
					sb.append(new PrintfFormat(doubleFormat).sprintf(rate));
					sb.append("\n");
				}
				
			}
			
			dialog.bar.setValue(timeIndex-time1);
			fos.write(sb.toString().getBytes());
			sb = new StringBuilder();
		}
		
		return sb.toString();
	} 
	
	private int getFluxIndex(int[] array, int index){
		for(int i=0; i<array.length; i++){
			if(array[i]==index){
				return i;
			}
		}
		return -1;
	}
	
	private int getReactionIndex(Point[] array, int in, int out){
		for(int i=0; i<array.length; i++){
			if(array[i].getX()==in && array[i].getY()==out){
				return i;
			}
		}
		return -1;
	}
	
	private int getIsotopeIndex(Point[] array, Point point){
		for(int i=0; i<array.length; i++){
			if(array[i].getX()==point.getX() && array[i].getY()==point.getY()){
				return i;
			}
		}
		return -1;
	}
	
	private void setRateMap(){
		HashMap<Integer, HashMap<String, RateDataStructure>> map = new HashMap<Integer, HashMap<String, RateDataStructure>>();
		ds.map = map;
		NucSimDataStructure nsds = ds.getAnimatorNucSimDataStructure();
		dialog.addText("\nImporting reaction rate parameters from database...");
		dialog.bar.setMaximum(nsds.getZAMapArray().length-1-2);
		for(int zaIndex=0; zaIndex<nsds.getZAMapArray().length; zaIndex++){
			int z = (int)nsds.getZAMapArray()[zaIndex].getX();
			int n = (int)(nsds.getZAMapArray()[zaIndex].getY()-nsds.getZAMapArray()[zaIndex].getX());
			if(z>=zmin && z<=zmax && n>=nmin && n<=nmax && !((z==1 && n==0) || (z==0 && n==1))){
				HashMap<String, RateDataStructure> rateMap = new HashMap<String, RateDataStructure>();
				map.put(zaIndex, rateMap);
				HashSet<String> rateIDList = new HashSet<String>();
				for(int type=0; type<17; type++){
					int reactionType = getReactionType(type);
					Point newZAPoint = getZAPoint(z, z+n, type);
					String reactionString = getReactionString(z, z+n, type);
					String decay = getDecay(type);
					String rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, decay);
					rateIDList.add(rateID);
					if(decay.equals("bet+")){
						rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "ec");
						rateIDList.add(rateID);
						rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
						rateIDList.add(rateID);
					}
				}
				String rates = "";
				Iterator<String> itrList = rateIDList.iterator();
				while(itrList.hasNext()){
					rates += itrList.next();
					if(itrList.hasNext()){
						rates += "\n";
					}
				}
				ds.zaIndex = zaIndex;
				ds.setRates(rates);
				if(Cina.cinaCGIComm.doCGICall("RATES EXIST", parent)){
					rates = "";
					Iterator<String> itr = rateMap.keySet().iterator();
					ds.setProperties("Number of Parameters\u0009Parameters\u0009Q-value");
					while(itr.hasNext()){
						rates += itr.next();
						if(itr.hasNext()){
							rates += "\n";
						}
					}
					ds.setRates(rates);
					Cina.cinaCGIComm.doCGICall("GET RATE INFO", parent);
					dialog.bar.setValue(zaIndex);
				}
			}
		}
		dialog.addText("DONE!");
	}
	
	private String getFileString(FileOutputStream fos) throws IOException{
		String doubleFormat = "%13.6E"; 
		NucSimDataStructure nsds = ds.getAnimatorNucSimDataStructure();
		StringBuilder sb = new StringBuilder();
		dialog.bar.setMaximum(time2-time1+1);
		dialog.bar.setValue(0);
		dialog.addText("\nWriting file...");
		//NUMBER OF ISOTOPES
		sb.append(nsds.getZAMapArray().length-2);
		sb.append("\n");
		//NUMBER OF TIMESTEPS
		sb.append(time2-time1+1);
		sb.append("\n");
		fos.write(sb.toString().getBytes());
		sb = new StringBuilder();
		int centralTimeIndex = time1 + (time2-time1+1)/2;
		for(int timeIndex=time1; timeIndex<time2+1; timeIndex++){
			if(type==YES_RATES && timeIndex!=centralTimeIndex){continue;}
			//TIMESTEP
			if(timeIndex<10){
				sb.append("   " + timeIndex);
			}else if(timeIndex>9 && timeIndex<100){
				sb.append("  " + timeIndex);
			}else if(timeIndex>99 && timeIndex<1000){
				sb.append(" " + timeIndex);
			}else if(timeIndex>999){
				sb.append(timeIndex);
			}
			sb.append(" ");
			//TIME
			sb.append(new PrintfFormat("%13.12E").sprintf(nsds.getTimeArray()[timeIndex] - nsds.getTimeArray()[0]).trim());
			sb.append(" ");
			//TEMPERATURE
			double temp = Double.valueOf(new PrintfFormat(doubleFormat).sprintf(nsds.getTempArray()[timeIndex]).trim());
			sb.append(new PrintfFormat(doubleFormat).sprintf(nsds.getTempArray()[timeIndex]).trim());
			sb.append(" ");
			//DENSITY
			sb.append(new PrintfFormat(doubleFormat).sprintf(nsds.getDensityArray()[timeIndex]).trim());
			sb.append(" ");
			//Y_p
			sb.append(new PrintfFormat(doubleFormat).sprintf(getAbundance(nsds, timeIndex, 1, 1)).trim());
			sb.append(" ");
			//Y_4He
			sb.append(new PrintfFormat(doubleFormat).sprintf(getAbundance(nsds, timeIndex, 2, 4)).trim());
			sb.append(" ");
			sb.append("\n");
			//START ZN.....
			for(int zaIndex=0; zaIndex<nsds.getZAMapArray().length; zaIndex++){
				//Z
				int z = (int)nsds.getZAMapArray()[zaIndex].getX();
				//N
				int n = (int)(nsds.getZAMapArray()[zaIndex].getY()-nsds.getZAMapArray()[zaIndex].getX());
				if(z>=zmin && z<=zmax && n>=nmin && n<=nmax && !((z==1 && n==0) || (z==0 && n==1))){
					if(z<10){
						sb.append("  ");
					}else if(z>=10 && z<100){
						sb.append(" ");
					}
					sb.append(z);
					sb.append(" ");
					if(n<10){
						sb.append("  ");
					}else if(n>=10 && n<100){
						sb.append(" ");
					}
					sb.append(n);
					sb.append(" ");
					//ABUNDANCE
					sb.append(new PrintfFormat(doubleFormat).sprintf(getAbundance(zaIndex, nsds.getAbundTimestepDataStructureArray()[timeIndex])).trim());
					sb.append(" ");
					//RATES
					if((type==YES_RATES && timeIndex==centralTimeIndex)
							|| type==ALL_RATES){
						HashMap<String, RateDataStructure> rateMap = ds.map.get(zaIndex);
						for(int type=0; type<14; type++){
							int reactionType = getReactionType(type);
							Point newZAPoint = getZAPoint(z, z+n, type);
							String reactionString = getReactionString(z, z+n, type);
							String decay = getDecay(type);
							String rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, decay);
							RateDataStructure rds = rateMap.get(rateID);
							double rate = 0.0;
							if(rds!=null && decay.equals("")
									|| rds!=null && decay.equals("bet+")){
								rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
								
								//check for other rates
								rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "ec");
								rds = rateMap.get(rateID);
								if(rds!=null){
									rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
								}
								
								rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
								rds = rateMap.get(rateID);
								if(rds!=null){
									rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
								}
								
							}else if(rds==null && decay.equals("bet+")){
								rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "ec");
								rds = rateMap.get(rateID);
								if(rds!=null){
									rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
									
									rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
									rds = rateMap.get(rateID);
									if(rds!=null){
										rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
									}
								}else{
									rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
									rds = rateMap.get(rateID);
									if(rds!=null){
										rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
									}
								}
							}
							if(rate<1E-30){
								rate = 0.0;
							}
							sb.append(new PrintfFormat(doubleFormat).sprintf(rate));
							sb.append(" ");
						}
						//QB_C
						int type = 8;
						int reactionType = getReactionType(type);
						Point newZAPoint = getZAPoint(z, z+n, type);
						String reactionString = getReactionString(z, z+n, type);
						String decay = getDecay(type);
						String rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, decay);
						RateDataStructure rds = rateMap.get(rateID);
						if(rds!=null){
							sb.append(new PrintfFormat(doubleFormat).sprintf(rds.getQValue()));
						}else{
							sb.append(new PrintfFormat(doubleFormat).sprintf(0.0));
						}
						sb.append(" ");
						//Q_X9 to Z+3,n-1
						type = 14;
						reactionType = getReactionType(type);
						newZAPoint = getZAPoint(z, z+n, type);
						reactionString = getReactionString(z, z+n, type);
						decay = getDecay(type);
						rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, decay);
						rds = rateMap.get(rateID);
						if(rds!=null){
							sb.append(new PrintfFormat(doubleFormat).sprintf(rds.getQValue()));
						}else{
							sb.append(new PrintfFormat(doubleFormat).sprintf(0.0));
						}
						sb.append(" ");
						//Q_X10 to z+1, n
						type = 15;
						reactionType = getReactionType(type);
						newZAPoint = getZAPoint(z, z+n, type);
						reactionString = getReactionString(z, z+n, type);
						decay = getDecay(type);
						rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, decay);
						rds = rateMap.get(rateID);
						if(rds!=null){
							sb.append(new PrintfFormat(doubleFormat).sprintf(rds.getQValue()));
						}else{
							sb.append(new PrintfFormat(doubleFormat).sprintf(0.0));
						}
						sb.append(" ");
						//LIFETIME OF X9
						type = 16;
						reactionType = getReactionType(type);
						newZAPoint = getZAPoint(z, z+n, type);
						reactionString = getReactionString(z, z+n, type);
						decay = getDecay(type);
						rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, decay);
						rds = rateMap.get(rateID);
						double rate = 0.0;
						if(rds!=null){
							rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
							
							//check for other rates
							rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "ec");
							rds = rateMap.get(rateID);
							if(rds!=null){
								rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
							}
							
							rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
							rds = rateMap.get(rateID);
							if(rds!=null){
								rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
							}
						}else if(rds==null && decay.equals("bet+")){
							rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "ec");
							rds = rateMap.get(rateID);
							if(rds!=null){
								rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
								
								rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
								rds = rateMap.get(rateID);
								if(rds!=null){
									rate += mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
								}
							}else{
								rateID = getRateID(reactionType, (int)newZAPoint.getX(), (int)newZAPoint.getY(), reactionString, library, "bec");
								rds = rateMap.get(rateID);
								if(rds!=null){
									rate = mds.calcRate(temp, rds.getParameters(), rds.getParameters().length);
								}
							}
						}
						if(rate<1E-30){
							rate = 0.0;
						}
						sb.append(new PrintfFormat(doubleFormat).sprintf(getLifetime(rate)));
					}
					sb.append("\n");
				}
				
			}
			
			dialog.bar.setValue(timeIndex-time1);
			fos.write(sb.toString().getBytes());
			sb = new StringBuilder();
		}
		
		return sb.toString();
	} 
	
	private double getAbundance(NucSimDataStructure nsds, int timestep, int z, int a){
		
		int zaIndex = -1;
		foundZA:
		for(int i=0; i<nsds.getZAMapArray().length; i++){
			if(z==(int)nsds.getZAMapArray()[i].getX()
					&& a==(int)nsds.getZAMapArray()[i].getY()){
				zaIndex = i;
				break foundZA;
			}
		}
		if(zaIndex==-1){
			return 0.0;
		}
		
		int abundIndex = -1;
		foundAbund:
		for(int i=0; i<nsds.getAbundTimestepDataStructureArray()[timestep].getIndexArray().length; i++){
			if(nsds.getAbundTimestepDataStructureArray()[timestep].getIndexArray()[i]==zaIndex){
				abundIndex = i;
				break foundAbund;
			}
		}
		if(abundIndex==-1){
			return 0.0;
		}
		return nsds.getAbundTimestepDataStructureArray()[timestep].getAbundArray()[abundIndex];
	}
	
	private double getLifetime(double input){
		return Math.log(2)/Math.exp(input);
	}
	
	private String getReactionString(int z, int a, int type){
		String string = "";
		switch(type){
			case 0:
				string = a + MainDataStructure.getElementSymbol(z+1) + " --> " + a + MainDataStructure.getElementSymbol(z);
				break;
			case 1:
				if(z>0 && a>1){
					string = "p + " + (a-1) + MainDataStructure.getElementSymbol(z-1) + " --> " + a + MainDataStructure.getElementSymbol(z);
				}
				break;
			case 2:
				if(z>0 && a>3){
					string = "4He + " + (a-3) + MainDataStructure.getElementSymbol(z-1) + " --> p + " + a + MainDataStructure.getElementSymbol(z);
				}
				break;
			case 3:
				if(z>1 && a>4){
					string = "4He + " + (a-4) + MainDataStructure.getElementSymbol(z-2) + " --> " + a + MainDataStructure.getElementSymbol(z);
				}
				break;
			case 4:
				string = "p + " + (a+3) + MainDataStructure.getElementSymbol(z+1) + " --> 4He + " + a + MainDataStructure.getElementSymbol(z);
				break;
			case 5:
				string = (a+1) + MainDataStructure.getElementSymbol(z+1) + " --> p + " + a + MainDataStructure.getElementSymbol(z);
				break;
			case 6:
				string = (a+4) + MainDataStructure.getElementSymbol(z+2) + " --> 4He + " + a + MainDataStructure.getElementSymbol(z);
				break;
			case 7:
				if(z>0){
					string = a + MainDataStructure.getElementSymbol(z) + " --> " + a + MainDataStructure.getElementSymbol(z-1);
				}
				break;
			case 8:
				string = "p + " + a + MainDataStructure.getElementSymbol(z) + " --> " + (a+1) + MainDataStructure.getElementSymbol(z+1);
				break;
			case 9:
				string = "4He + " + a + MainDataStructure.getElementSymbol(z) + " --> p + " + (a+3) + MainDataStructure.getElementSymbol(z+1);
				break;
			case 10:
				string = "4He + " + a + MainDataStructure.getElementSymbol(z) + " --> " + (a+4) + MainDataStructure.getElementSymbol(z+2);
				break;
			case 11:
				if(z>0 && a>3){
					string = "p + " + a + MainDataStructure.getElementSymbol(z) + " --> 4He + " + (a-3) + MainDataStructure.getElementSymbol(z-1);
				}
				break;
			case 12:
				if(z>0 && a>1){
					string = a + MainDataStructure.getElementSymbol(z) + " --> p + " + (a-1) + MainDataStructure.getElementSymbol(z-1);
				}
				break;
			case 13:
				if(a>4 && z>1){
					string = a + MainDataStructure.getElementSymbol(z) + " --> 4He + " + (a-4) + MainDataStructure.getElementSymbol(z-2);
				}
				break;
			case 14:
				string = "p + " + (a+1) + MainDataStructure.getElementSymbol(z+2) + " --> " + (a+2) + MainDataStructure.getElementSymbol(z+3);
				break;
			case 15:
				if(a>2){
					string = "4He + " + (a-2) + MainDataStructure.getElementSymbol(z) + " --> p + " + (a+1) + MainDataStructure.getElementSymbol(z+1);
				}
				break;
			case 16:
				string = (a+1) + MainDataStructure.getElementSymbol(z+2) + " --> " + (a+1) + MainDataStructure.getElementSymbol(z+1);
				break;
				
		}
		string = string.replaceAll("1H", "p");
		string = string.replaceAll("1n", "n");
		string = string.replaceAll("2H", "d");
		string = string.replaceAll("3H", "t");
		
		///CORRECT BAD REPLACEMENTS
		string = string.replaceAll("pe", "1He");
		string = string.replaceAll("de", "2He");
		string = string.replaceAll("te", "3He");
		
		return string;
	}
	
	private String getDecay(int type){
		String string = "";
		switch(type){
			case 0:
				string = "bet+";
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				string = "bet+";
				break;
			case 8:
				break;
			case 9:
				break;
			case 10:
				break;
			case 11:
				break;
			case 12:
				break;
			case 13:
				break;
			case 14:
				break;
			case 15:
				break;
			case 16:
				string = "bet+";
				break;
		}
		return string;
	}
	
	private String getRateID(int type, int z, int a, String reactionString, String library, String decay){
		StringBuffer sb = new StringBuffer();
		sb.append("0" + type);
		if(z<10){
			sb.append("00" + z);
		}else if(z>=10 && z<100){
			sb.append("0" + z);
		}else if(z>=100){
			sb.append(z);
		}
		if(a<10){
			sb.append("00" + a);
		}else if(a>=10 && a<100){
			sb.append("0" + a);
		}else if(a>=100){
			sb.append(a);
		}
		sb.append(library);
		sb.append("\u0009");
		sb.append(reactionString);
		sb.append("\u000b");
		if(!decay.trim().equals("")){
			sb.append(decay);
		}
		return sb.toString();
	}
	
	private Point getZAPoint(int z, int a, int type){
		int n = a-z;
		int zout = 0;
		int nout = 0;
		switch(type){
			case 0:
				zout = z+1;
				nout = n-1;
				break;
			case 1:
				zout = z-1;
				nout = n;
				break;
			case 2:
				zout = z-1;
				nout = n-2;
				break;
			case 3:
				zout = z-2;
				nout = n-2;
				break;
			case 4:
				zout = z+1;
				nout = n+2;
				break;
			case 5:
				zout = z+1;
				nout = n;
				break;
			case 6:
				zout = z+2;
				nout = n+2;
				break;
			case 7:
				zout = z;
				nout = n;
				break;
			case 8:
				zout = z;
				nout = n;
				break;
			case 9:
				zout = z;
				nout = n;
				break;
			case 10:
				zout = z;
				nout = n;
				break;
			case 11:
				zout = z;
				nout = n;
				break;
			case 12:
				zout = z;
				nout = n;
				break;
			case 13:
				zout = z;
				nout = n;
				break;
			case 14:
				zout = z+2;
				nout = n-1;
				break;
			case 15:
				zout = z;
				nout = n-2;
				break;
			case 16:
				zout = z+2;
				nout = n-1;
				break;
		}
		return new Point(zout, zout+nout);
	}
	
	private int getReactionType(int type){
		int reaclibType = 0;
		switch(type){
			case 0:
				reaclibType =  1;
				break;
			case 1:
				reaclibType =  4;
				break;
			case 2:
				reaclibType =  5;
				break;
			case 3:
				reaclibType =  4;
				break;
			case 4:
				reaclibType =  5;
				break;
			case 5:
				reaclibType =  2;
				break;
			case 6:
				reaclibType =  2;
				break;
			case 7:
				reaclibType =  1;
				break;
			case 8:
				reaclibType =  4;
				break;
			case 9:
				reaclibType =  5;
				break;
			case 10:
				reaclibType =  4;
				break;
			case 11:
				reaclibType =  5;
				break;
			case 12:
				reaclibType =  2;
				break;
			case 13:
				reaclibType =  2;
				break;
			case 14:
				reaclibType =  4;
				break;
			case 15:
				reaclibType =  5;
				break;
			case 16:
				reaclibType =  1;
				break;
		}
		return reaclibType;
	}
	
	private double getAbundance(int zaIndex, AbundTimestepDataStructure atds){
		for(int i=0; i<atds.getIndexArray().length; i++){
			if(atds.getIndexArray()[i]>zaIndex){
				return 0.0;
			}
			if(atds.getIndexArray()[i]==zaIndex){
				return atds.getAbundArray()[i];
			}
		}
		return 0.0;
	}
	
	protected void done(){
		dialog.close();
	}
	
	private void writeFile(String string, File file){
		dialog.addText("\nWriting file...");
		try{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(string.getBytes());
			fos.close();
			dialog.addText("DONE!");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
}

class ElementVizPointDialog extends JDialog{
	
	private OpenTask task;
	private Frame owner;
	public JProgressBar bar;
	private JTextArea textArea;
	
	public ElementVizPointDialog(Frame owner, String string){
		
		super(owner, "Please Wait...", Dialog.ModalityType.APPLICATION_MODAL);

		this.owner = owner;
		
		setSize(400, 300);
		
    	double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.FILL, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		setLocationRelativeTo(owner);
		
		textArea = new JTextArea();
		textArea.setFont(Fonts.textFont);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setCaretPosition(0);
		
		bar = new JProgressBar();
		bar.setStringPainted(true);
		
		JScrollPane sp = new JScrollPane(textArea
							, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
							, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(200, 200));
		
		c.add(sp, "1, 1, f, f");
		c.add(bar, "1, 3, f, c");
		
	}
	
	public void addText(String string){
		textArea.append(string);
		textArea.repaint();
		textArea.validate();
	}
	
	public void open(){
		textArea.setText("");
		(task = new OpenTask(this, owner)).execute();
	}
	
	public void close(){
		task.cancel(true);
		setVisible(false);
		dispose();
	}
	
}

class OpenTask extends SwingWorker<Void, Void>{
	
	private JDialog dialog;
	private Frame owner;
	
	public OpenTask(JDialog dialog, Frame owner){
		this.dialog = dialog;
		this.owner = owner;
	}
	
	protected Void doInBackground(){
		dialog.setLocationRelativeTo(owner);
		dialog.validate();
		dialog.setVisible(true);
		return null;
	}

}
