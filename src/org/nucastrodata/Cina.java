package org.nucastrodata;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import org.nucastrodata.info.AboutFrame;
import org.nucastrodata.info.RegisterFrame;
import org.nucastrodata.io.CGICom;
import java.net.URL;
import java.util.Locale;
import info.clearthought.layout.*;
import javax.help.HelpSet;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataEvalDataStructure;
import org.nucastrodata.datastructure.feature.DataManDataStructure;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;
import org.nucastrodata.datastructure.feature.DataViewerDataStructure;
import org.nucastrodata.datastructure.feature.ElementManDataStructure;
import org.nucastrodata.datastructure.feature.ElementMovieManDataStructure;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.feature.ElementWorkManDataStructure;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.datastructure.feature.RateManDataStructure;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;
import org.nucastrodata.datastructure.feature.RateViewerDataStructure;
import org.nucastrodata.datastructure.feature.RegisterDataStructure;
import org.nucastrodata.datastructure.feature.ThermoManDataStructure;
import org.nucastrodata.exception.UncaughtExceptionHandler;
import org.nucastrodata.data.datamass.DataMassFrame;
import org.nucastrodata.data.dataeval.DataEvalFrame;
import org.nucastrodata.data.dataman.DataManFrame;
import org.nucastrodata.data.dataviewer.DataViewerFrame;
import org.nucastrodata.rate.rategen.RateGenFrame;
import org.nucastrodata.rate.rateparam.RateParamFrame;
import org.nucastrodata.rate.rateman.RateManFrame;
import org.nucastrodata.rate.ratelibman.RateLibManFrame;
import org.nucastrodata.rate.rateviewer.RateViewerFrame;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementman.ElementManFrame;
import org.nucastrodata.element.elementviz.ElementVizFrame;
import org.nucastrodata.element.elementworkman.ElementWorkManFrame;
import org.nucastrodata.element.thermoman.ThermoManFrame;
import org.nucastrodata.worker.CinaLogoutWorker;
import org.nucastrodata.worker.CinaLogoutWorker2;

public class Cina extends JFrame implements ActionListener, KeyListener, ItemListener{
	
	private JTextField userField;
	private JPasswordField passwordField; 
	private JRadioButton guestRadioButton, userRadioButton;
	private JLabel userLabel, passwordLabel;
	private JPanel introButtonPanel;
	private Container c;
	public static String system = "";
	private GridBagConstraints gbc = new GridBagConstraints();
	IntroPanel cinaIntroPanel = new IntroPanel();
	public static CinaCGIComm cinaCGIComm;
	public static CGICom cgiCom;
	public static RateGenFrame rateGenFrame;
	public static RateParamFrame rateParamFrame;
	public static RateManFrame rateManFrame;
	public static RateViewerFrame rateViewerFrame;
	public static ElementSynthFrame elementSynthFrame;
	public static ElementManFrame elementManFrame;
	public static ElementWorkManFrame elementWorkManFrame;
	public static ThermoManFrame thermoManFrame;
	public static ElementVizFrame elementVizFrame;
	public static RateLibManFrame rateLibManFrame;
	public static AboutFrame aboutFrame;
	public static RegisterFrame registerFrame;
	public static DataManFrame dataManFrame;
	public static DataViewerFrame dataViewerFrame;
	public static DataEvalFrame dataEvalFrame;
	public static DataMassFrame dataMassFrame;
	public static MainDataStructure cinaMainDataStructure = new MainDataStructure();
	public static RateGenDataStructure rateGenDataStructure = new RateGenDataStructure();
	public static RateParamDataStructure rateParamDataStructure = new RateParamDataStructure();
	public static RateManDataStructure rateManDataStructure = new RateManDataStructure();
	public static RateLibManDataStructure rateLibManDataStructure = new RateLibManDataStructure();
	public static RateViewerDataStructure rateViewerDataStructure = new RateViewerDataStructure();
	public static RegisterDataStructure registerDataStructure = new RegisterDataStructure();
	public static ElementSynthDataStructure elementSynthDataStructure = new ElementSynthDataStructure();
	public static ElementManDataStructure elementManDataStructure = new ElementManDataStructure();
	public static ElementWorkManDataStructure elementWorkManDataStructure = new ElementWorkManDataStructure();
	public static ThermoManDataStructure thermoManDataStructure = new ThermoManDataStructure();
	public static ElementMovieManDataStructure elementMovieManDataStructure = new ElementMovieManDataStructure();
	public static ElementVizDataStructure elementVizDataStructure = new ElementVizDataStructure();
	public static DataManDataStructure dataManDataStructure = new DataManDataStructure();
	public static DataViewerDataStructure dataViewerDataStructure = new DataViewerDataStructure();
	public static DataEvalDataStructure dataEvalDataStructure = new DataEvalDataStructure();
	public static DataMassDataStructure dataMassDataStructure = new DataMassDataStructure();
	public static JButton beginButton, submitButton, logButton, okButton, yesButton, noButton;
	public static JDialog passwordDialog, noticeDialog, cautionDialog;

	public Cina(String urlType){
		
		setSize(625, 625);
		setTitle("Computational Infrastructure for Nuclear Astrophysics");

		cinaMainDataStructure.initialize();
		rateGenDataStructure.initialize();
		rateParamDataStructure.initialize();
		rateLibManDataStructure.initialize(); 
		rateViewerDataStructure.initialize();
		registerDataStructure.initialize();
		rateManDataStructure.initialize();
		elementSynthDataStructure.initialize();
		elementManDataStructure.initialize();
		elementWorkManDataStructure.initialize();
		thermoManDataStructure.initialize();
		elementMovieManDataStructure.initialize();
		elementVizDataStructure.initialize();
		dataManDataStructure.initialize();
		dataViewerDataStructure.initialize();
		dataEvalDataStructure.initialize();
		dataMassDataStructure.initialize();
		
		cinaMainDataStructure.setURLType(urlType);
		
		cinaCGIComm = new CinaCGIComm();
		cgiCom = new CGICom();

		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED, gap};
		
		c = getContentPane();
		c.setLayout(new TableLayout(column, row));

		beginButton = new JButton("Begin");
		beginButton.addActionListener(this);
		beginButton.setFont(Fonts.buttonFont);
		beginButton.setEnabled(false);

		logButton = new JButton("Log In");
		logButton.addActionListener(this);
		logButton.setFont(Fonts.buttonFont);
	
		introButtonPanel = new JPanel();
		introButtonPanel.add(logButton);
		introButtonPanel.add(beginButton);
		cinaIntroPanel.initialize();
		c.add(cinaIntroPanel, "1, 1, f, f");
		c.add(introButtonPanel, "1, 3, c, c");
		setVisible(true);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {   
				if(logButton.getText().equals("Log Out")){
					CinaLogoutWorker worker = new CinaLogoutWorker(elementSynthDataStructure, Cina.this, elementSynthFrame);
					worker.execute();
				}		
		    } 
		});
		
		createPasswordDialog();
		
		if(cinaMainDataStructure.getURLType().equals("DEV")){
			logInForDev();
		}
		
	}
	
	private void logInForDev(){
		cinaMainDataStructure.setUser(userField.getText());
		cinaMainDataStructure.setPW(String.valueOf(passwordField.getPassword()));

		boolean[] flagArray = cinaCGIComm.doCGIComm("GET ID", this);
		
		if(!flagArray[0] && !flagArray[1]){
			
			if(cgiCom.doCGICall(cinaMainDataStructure, cinaMainDataStructure, CGICom.IS_MASTER_USER, this)){

				beginButton.setEnabled(true);
				cinaIntroPanel.RateGenRadioButton.setEnabled(true);
				cinaIntroPanel.RateParamRadioButton.setEnabled(true);
				cinaIntroPanel.ElementSynthRadioButton.setEnabled(true);
				cinaIntroPanel.RateViewerRadioButton.setEnabled(true);
				cinaIntroPanel.RateLibManRadioButton.setEnabled(true);
				cinaIntroPanel.HelpRadioButton.setEnabled(true);
				cinaIntroPanel.AboutRadioButton.setEnabled(true);
				cinaIntroPanel.RegisterRadioButton.setEnabled(true);
				cinaIntroPanel.RateManRadioButton.setEnabled(true);
				cinaIntroPanel.ElementVizRadioButton.setEnabled(true);
				cinaIntroPanel.ElementManRadioButton.setEnabled(true);
				cinaIntroPanel.ElementWorkManRadioButton.setEnabled(true);
				cinaIntroPanel.ThermoManRadioButton.setEnabled(true);
				cinaIntroPanel.DataMassRadioButton.setEnabled(true);
				cinaIntroPanel.DataEvalRadioButton.setEnabled(true);
				cinaIntroPanel.DataViewerRadioButton.setEnabled(true);
				cinaIntroPanel.DataManRadioButton.setEnabled(true);
				
				logButton.setText("Log Out");

			}
			
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		
		Locale.setDefault(Locale.US);
		
		try{
			String osName = System.getProperty("os.name").toLowerCase();
			if(osName.indexOf("mac")!=-1){
				system = "MAC";
			}else{
				system = "PC";
			}
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        if(system.equals("MAC")){
        	UIManager.put("Button.background", ColorFormat.frontColor);
        	UIManager.put("Button.foreground", ColorFormat.backColor);
        }else{
        	UIManager.put("Button.background", ColorFormat.backColor);
        	UIManager.put("Button.foreground", ColorFormat.frontColor);
        }
		
        UIManager.put("ComboBox.foreground", ColorFormat.backColor);
		UIManager.put("ComboBox.background", ColorFormat.frontColor);
		
  		UIManager.put("CheckBox.background", ColorFormat.backColor);
    	UIManager.put("CheckBox.foreground", ColorFormat.frontColor);
		
		UIManager.put("OptionPane.foreground", ColorFormat.frontColor);
		UIManager.put("OptionPane.background", ColorFormat.backColor);

		UIManager.put("Panel.background", ColorFormat.backColor);
		UIManager.put("Panel.foreground", ColorFormat.frontColor);

		UIManager.put("Label.background", ColorFormat.backColor);
		UIManager.put("Label.foreground", ColorFormat.frontColor);

		UIManager.put("RadioButton.background", ColorFormat.backColor);
		UIManager.put("RadioButton.foreground", ColorFormat.frontColor);

		UIManager.put("List.background", ColorFormat.frontColor);
		UIManager.put("List.foreground", ColorFormat.backColor);
		
		UIManager.put("Tree.background", ColorFormat.frontColor);
		UIManager.put("Tree.foreground", ColorFormat.backColor);
		
		UIManager.put("ScrollPane.background", ColorFormat.frontColor);
		UIManager.put("ScrollPane.foreground", ColorFormat.frontColor);
		
		UIManager.put("Table.background", ColorFormat.frontColor);
		UIManager.put("Table.foreground", ColorFormat.backColor);
	
		UIManager.put("ToolBar.background", ColorFormat.frontColor);
    	UIManager.put("ToolBar.foreground", ColorFormat.frontColor);
    	
		UIManager.put("TextArea.background", ColorFormat.frontColor);
		UIManager.put("TextArea.foreground", ColorFormat.backColor);
		
		UIManager.put("TextPane.background", ColorFormat.frontColor);
		UIManager.put("TextPane.foreground", ColorFormat.backColor);
		
		UIManager.put("TextField.background", ColorFormat.frontColor);
		UIManager.put("TextField.foreground", ColorFormat.backColor);
		
		UIManager.put("EditorPane.background", ColorFormat.frontColor);
		UIManager.put("EditorPane.foreground", ColorFormat.backColor);
		
		UIManager.put("Slider.background", ColorFormat.backColor);
		UIManager.put("Slider.foreground", ColorFormat.frontColor);
		
		UIManager.put("ComboBox.font", new FontUIResource(Fonts.textFont));
		UIManager.put("TabbedPane.font", new FontUIResource(Fonts.textFont));
		UIManager.put("Panel.font", new FontUIResource(Fonts.textFont));
		UIManager.put("Label.font", new FontUIResource(Fonts.textFont));
		UIManager.put("RadioButton.font", new FontUIResource(Fonts.textFont));
		UIManager.put("List.font", new FontUIResource(Fonts.textFont));
		UIManager.put("Tree.font", new FontUIResource(Fonts.textFont));
		UIManager.put("ScrollPane.font", new FontUIResource(Fonts.textFont));
		UIManager.put("Table.font", new FontUIResource(Fonts.textFont));
		UIManager.put("ToolBar.font", new FontUIResource(Fonts.textFont));
		UIManager.put("TextArea.font", new FontUIResource(Fonts.textFont));
		UIManager.put("TextPane.font", new FontUIResource(Fonts.textFont));
		UIManager.put("TextField.font", new FontUIResource(Fonts.textFont));
		UIManager.put("EditorPane.font", new FontUIResource(Fonts.textFont));
		UIManager.put("Slider.font", new FontUIResource(Fonts.textFont));
		UIManager.put("OptionPane.font", new FontUIResource(Fonts.textFont));
    	UIManager.put("CheckBox.font", new FontUIResource(Fonts.textFont));
    	UIManager.put("Button.font", new FontUIResource(Fonts.buttonFont));
		
    	Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
		
		new Cina(args[0]);
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent ke){
		
		if(ke.getKeyCode()==KeyEvent.VK_ENTER){
			if(passwordDialog.isVisible()){
				if(userRadioButton.isSelected()){
					
					cinaMainDataStructure.setUser(userField.getText());
					cinaMainDataStructure.setPW(String.valueOf(passwordField.getPassword()));

					boolean[] flagArray = cinaCGIComm.doCGIComm("GET ID", this);
					
					if(!flagArray[0] && !flagArray[1]){
						
						if(cgiCom.doCGICall(cinaMainDataStructure, cinaMainDataStructure, CGICom.IS_MASTER_USER, this)){

							beginButton.setEnabled(true);
							cinaIntroPanel.RateGenRadioButton.setEnabled(true);
							cinaIntroPanel.RateParamRadioButton.setEnabled(true);
							cinaIntroPanel.ElementSynthRadioButton.setEnabled(true);
							cinaIntroPanel.RateViewerRadioButton.setEnabled(true);
							cinaIntroPanel.RateLibManRadioButton.setEnabled(true);
							cinaIntroPanel.HelpRadioButton.setEnabled(true);
							cinaIntroPanel.AboutRadioButton.setEnabled(true);
							cinaIntroPanel.RegisterRadioButton.setEnabled(true);
							cinaIntroPanel.RateManRadioButton.setEnabled(true);
							cinaIntroPanel.ElementVizRadioButton.setEnabled(true);
							cinaIntroPanel.ElementManRadioButton.setEnabled(true);
							cinaIntroPanel.ElementWorkManRadioButton.setEnabled(true);
							cinaIntroPanel.ThermoManRadioButton.setEnabled(true);
							cinaIntroPanel.DataMassRadioButton.setEnabled(true);
							cinaIntroPanel.DataEvalRadioButton.setEnabled(true);
							cinaIntroPanel.DataViewerRadioButton.setEnabled(true);
							cinaIntroPanel.DataManRadioButton.setEnabled(true);
							
							logButton.setText("Log Out");
							
			    			passwordDialog.setVisible(false);
			    			passwordDialog.dispose();
			    			
			    			createNoticeDialog();
						}
							
					}
				}else if(guestRadioButton.isSelected()){
					
					cinaMainDataStructure.setUser("guest");
					cinaMainDataStructure.setPW("guest");
					
					boolean[] flagArray = cinaCGIComm.doCGIComm("GET ID", this);

					if(!flagArray[0]){
						if(!flagArray[1]){
							beginButton.setEnabled(true);
							
							cinaIntroPanel.RateGenRadioButton.setEnabled(true);
							cinaIntroPanel.RateParamRadioButton.setEnabled(true);
							cinaIntroPanel.ElementSynthRadioButton.setEnabled(true);
							cinaIntroPanel.RateViewerRadioButton.setEnabled(true);
							cinaIntroPanel.RateLibManRadioButton.setEnabled(true);
							cinaIntroPanel.HelpRadioButton.setEnabled(true);
							cinaIntroPanel.AboutRadioButton.setEnabled(true);
							cinaIntroPanel.RegisterRadioButton.setEnabled(true);
							cinaIntroPanel.RateManRadioButton.setEnabled(true);
							cinaIntroPanel.ElementVizRadioButton.setEnabled(true);
							cinaIntroPanel.ElementManRadioButton.setEnabled(true);
							cinaIntroPanel.ElementWorkManRadioButton.setEnabled(true);
							cinaIntroPanel.ThermoManRadioButton.setEnabled(true);
							cinaIntroPanel.DataMassRadioButton.setEnabled(true);
							cinaIntroPanel.DataEvalRadioButton.setEnabled(true);
							cinaIntroPanel.DataViewerRadioButton.setEnabled(true);
							cinaIntroPanel.DataManRadioButton.setEnabled(true);
							
							logButton.setText("Log Out");

							passwordDialog.setVisible(false);
		    				passwordDialog.dispose();
				    		createNoticeDialog();
				    		
						}	
					}
				}
			}else if(noticeDialog.isVisible()){
				noticeDialog.setVisible(false);
				noticeDialog.dispose();
			}
		}	
	}

	public void keyReleased(KeyEvent ke){}
	public void keyTyped(KeyEvent ke){}
	
	public void openDataEval(){
	
		if(dataEvalFrame==null){
			dataEvalFrame = new DataEvalFrame(dataEvalDataStructure);
			dataEvalFrame.setResizable(true);
			dataEvalFrame.setSize(580, 420);
			dataEvalFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
			dataEvalFrame.setVisible(true);
			dataEvalFrame.setTitle("Nuclear Data Evaluator's Toolkit");
		}else{
			dataEvalFrame.setVisible(true);
		}
		
	}
	
	public void openDataMass(){
		
		if(dataMassFrame==null){
			dataMassFrame = new DataMassFrame(dataMassDataStructure);
			dataMassFrame.setResizable(true);
			dataMassFrame.setSize(580, 420);
			dataMassFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
			dataMassFrame.setVisible(true);
			dataMassFrame.setTitle("Mass Model Evaluator");
		}else{
			dataMassFrame.setVisible(true);
		}
	
	}

	public void openDataMan(){
	
		if(dataManFrame==null){
			dataManFrame = new DataManFrame(dataManDataStructure);
			dataManFrame.setResizable(true);
			dataManFrame.setSize(580, 420);
			dataManFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
			dataManFrame.setVisible(true);
			dataManFrame.setTitle("Nuclear Data Manager");
		}else{
			dataManFrame.setVisible(true);
		}
	
	}
	
	public void openDataViewer(){
	
		if(dataViewerFrame==null){
			dataViewerFrame = new DataViewerFrame(dataViewerDataStructure);
			
			dataViewerDataStructure.setNucDataSetGroup("PUBLIC");
			boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", dataViewerFrame);
			dataViewerDataStructure.setNucDataSetGroup("SHARED");
			boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", dataViewerFrame);
			dataViewerDataStructure.setNucDataSetGroup("USER");
			boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST",dataViewerFrame);
			
			if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
				dataViewerFrame.setCurrentState();
		    	dataViewerDataStructure.setCurrentNucDataSetDataStructure(dataViewerDataStructure.getNucDataSetStructureArray()[dataViewerDataStructure.getNucDataSetIndex()]);	
				dataViewerDataStructure.setNucDataSetName(dataViewerDataStructure.getCurrentNucDataSetDataStructure().getNucDataSetName());

				if(Cina.cinaCGIComm.doCGICall("GET NUC DATA SET ISOTOPES", dataViewerFrame)){
					dataViewerFrame.setSize(825,600);
					dataViewerFrame.setResizable(true);
        			dataViewerFrame.setTitle("Nuclear Data Viewer");
        			dataViewerFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
        			dataViewerFrame.setVisible(true);	
				}
			}	
		}else{
			dataViewerFrame.setVisible(true);
		}
	
	}
	
	public void openRateGen(){
		
		if(rateGenFrame==null){
			rateGenFrame = new RateGenFrame(rateGenDataStructure);
			rateGenFrame.setResizable(true);
			rateGenFrame.setSize(635, 490);
			rateGenFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
			rateGenFrame.setVisible(true);
			rateGenFrame.setTitle("Rate Generator");
		}else{
			rateGenFrame.setVisible(true);
		}
		
	}
	
	public void openRateParam(){
	
		if(rateParamFrame==null){
			rateParamFrame = new RateParamFrame(rateParamDataStructure);
			rateParamFrame.setResizable(true);
			rateParamDataStructure.setRateSubmitted(false);
			rateParamFrame.setSize(700, 450);
			rateParamFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
			rateParamFrame.setVisible(true);
			rateParamFrame.setTitle("Rate Parameterizer");
		}else{
			rateParamDataStructure.setRateSubmitted(false);
			rateParamFrame.setVisible(true);
		}
			
	}

	public void openRateMan(){
	
		if(rateManFrame==null){
			rateManFrame = new RateManFrame(rateManDataStructure);
			rateManFrame.setResizable(true);
			rateManFrame.setSize(610, 435);
			rateManFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
			rateManFrame.setVisible(true);
			rateManFrame.setTitle("Rate Manager");
		}else{
			rateManFrame.setVisible(true);
		}
	
	}
	
	public void openRateLibMan(){
	
		if(rateLibManFrame==null){
			rateLibManFrame = new RateLibManFrame(rateLibManDataStructure);
			rateLibManFrame.setResizable(true);
			rateLibManFrame.setSize(580, 420);
			rateLibManFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
			rateLibManFrame.setVisible(true);
			rateLibManFrame.setTitle("Rate Library Manager");
		}else{
			rateLibManFrame.setVisible(true);
		}
	
	}
	
	public void openRateViewer(){
	
		if(rateViewerFrame==null){
			rateViewerFrame = new RateViewerFrame(rateViewerDataStructure, false);
			
			rateViewerDataStructure.setLibGroup("PUBLIC");
			boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", rateViewerFrame);
			rateViewerDataStructure.setLibGroup("SHARED");
			boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", rateViewerFrame);
			rateViewerDataStructure.setLibGroup("USER");
			boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", rateViewerFrame);

			if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
				rateViewerFrame.setCurrentState();
		    	rateViewerDataStructure.setCurrentLibraryDataStructure(rateViewerDataStructure.getLibraryStructureArray()[rateViewerDataStructure.getLibIndex()]);	
				rateViewerDataStructure.setLibraryName(rateViewerDataStructure.getCurrentLibraryDataStructure().getLibName());

				if(Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY ISOTOPES", Cina.rateViewerFrame)){
					rateViewerFrame.setSize(825,600);
					rateViewerFrame.setResizable(true);
        			rateViewerFrame.setTitle("Rate Viewer");
        			rateViewerFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
        			rateViewerFrame.setVisible(true);
				}
			}
		}else{
			rateViewerFrame.param = false;
			if(rateViewerFrame.rateViewerPlotFrame!=null){
				rateViewerFrame.rateViewerPlotFrame.param = false;
				rateViewerDataStructure.setRateAdded(false);
				rateViewerFrame.rateViewerPlotFrame.setFormatPanelState();
				rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.initialize();
				rateViewerFrame.rateViewerPlotFrame.redrawPlot();
			}
			rateViewerFrame.setVisible(true);
		}
	
	}

	public void openElementSynth(){
	
		if(elementSynthFrame==null){
			elementSynthFrame = new ElementSynthFrame(cinaMainDataStructure, cgiCom, this, elementSynthDataStructure);
			elementSynthFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		elementSynthFrame.setContinueEnabled(true);
		elementSynthFrame.setVisible(true);
	
	}

	public void openElementViz(){
	
		if(elementVizFrame==null){
			elementVizFrame = new ElementVizFrame(elementVizDataStructure, cgiCom);
			elementVizFrame.setSize(600,450);
			elementVizFrame.setResizable(true);
			elementVizFrame.setTitle("Element Synthesis Visualizer");
			elementVizFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
			elementVizFrame.setVisible(true);
		}else{
			elementVizFrame.setVisible(true);
		}
	
	}

	public void openElementMan(){
		if(elementManFrame==null){
			elementManFrame = new ElementManFrame(cinaMainDataStructure, cgiCom, cinaCGIComm, this, elementManDataStructure);
			elementManFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		elementManFrame.setVisible(true);
	}
	
	public void openElementWorkMan(){
		if(elementWorkManFrame==null){
			elementWorkManFrame = new ElementWorkManFrame(cinaMainDataStructure, cgiCom, cinaCGIComm, this, elementWorkManDataStructure);
			elementWorkManFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		elementWorkManFrame.setVisible(true);
	}
	
	public void openThermoMan(){
		if(thermoManFrame==null){
			thermoManFrame = new ThermoManFrame(cinaMainDataStructure, cgiCom, cinaCGIComm, this, thermoManDataStructure);
			thermoManFrame.setLocation((int)(getLocation().getX()) + 25
									, (int)(getLocation().getY()) + 25);
		}
		thermoManFrame.setVisible(true);
	}
	
	public void openAbout(){
	
		if(aboutFrame==null){
			aboutFrame = new AboutFrame();
			aboutFrame.setTitle("General Information");
		}else{
			aboutFrame.setVisible(true);
		}
	
	} 
	
	public static void openRegister(){
		
		if(registerFrame==null){
			registerFrame = new RegisterFrame(registerDataStructure);
			registerFrame.setTitle("REGISTER!");
		}else{
			registerFrame.setVisible(true);
		}
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==submitButton){
			if(userRadioButton.isSelected()){
				
				cinaMainDataStructure.setUser(userField.getText());
				cinaMainDataStructure.setPW(String.valueOf(passwordField.getPassword()));
				
				boolean[] flagArray = cinaCGIComm.doCGIComm("GET ID", this);
					
				if(!flagArray[0] && !flagArray[1]){
							
					if(cgiCom.doCGICall(cinaMainDataStructure, cinaMainDataStructure, CGICom.IS_MASTER_USER, this)){
						
						beginButton.setEnabled(true);
						
						cinaIntroPanel.RateGenRadioButton.setEnabled(true);
						cinaIntroPanel.RateParamRadioButton.setEnabled(true);
						cinaIntroPanel.ElementSynthRadioButton.setEnabled(true);
						cinaIntroPanel.RateViewerRadioButton.setEnabled(true);
						cinaIntroPanel.RateLibManRadioButton.setEnabled(true);
						cinaIntroPanel.HelpRadioButton.setEnabled(true);
						cinaIntroPanel.AboutRadioButton.setEnabled(true);
						cinaIntroPanel.RegisterRadioButton.setEnabled(true);
						cinaIntroPanel.RateManRadioButton.setEnabled(true);
						cinaIntroPanel.ElementVizRadioButton.setEnabled(true);
						cinaIntroPanel.ElementManRadioButton.setEnabled(true);
						cinaIntroPanel.ElementWorkManRadioButton.setEnabled(true);
						cinaIntroPanel.ThermoManRadioButton.setEnabled(true);
						cinaIntroPanel.DataMassRadioButton.setEnabled(true);
						cinaIntroPanel.DataEvalRadioButton.setEnabled(true);
						cinaIntroPanel.DataViewerRadioButton.setEnabled(true);
						cinaIntroPanel.DataManRadioButton.setEnabled(true);

						logButton.setText("Log Out");
						
			    		passwordDialog.setVisible(false);
			    		passwordDialog.dispose();
						createNoticeDialog();
						
					}
					
				}
			}else if(guestRadioButton.isSelected()){
				cinaMainDataStructure.setUser("guest");
				cinaMainDataStructure.setPW("guest");
				boolean[] flagArray = cinaCGIComm.doCGIComm("GET ID", this);
				
				if(!flagArray[0]){
					if(!flagArray[1]){
						beginButton.setEnabled(true);
						
						cinaIntroPanel.RateGenRadioButton.setEnabled(true);
						cinaIntroPanel.RateParamRadioButton.setEnabled(true);
						cinaIntroPanel.ElementSynthRadioButton.setEnabled(true);
						cinaIntroPanel.RateViewerRadioButton.setEnabled(true);
						cinaIntroPanel.RateLibManRadioButton.setEnabled(true);
						cinaIntroPanel.HelpRadioButton.setEnabled(true);
						cinaIntroPanel.AboutRadioButton.setEnabled(true);
						cinaIntroPanel.RegisterRadioButton.setEnabled(true);
						cinaIntroPanel.RateManRadioButton.setEnabled(true);
						cinaIntroPanel.ElementVizRadioButton.setEnabled(true);
						cinaIntroPanel.ElementManRadioButton.setEnabled(true);
						cinaIntroPanel.ElementWorkManRadioButton.setEnabled(true);
						cinaIntroPanel.ThermoManRadioButton.setEnabled(true);
						cinaIntroPanel.DataMassRadioButton.setEnabled(true);
						cinaIntroPanel.DataEvalRadioButton.setEnabled(true);
						cinaIntroPanel.DataViewerRadioButton.setEnabled(true);
						cinaIntroPanel.DataManRadioButton.setEnabled(true);
						
						logButton.setText("Log Out");
						
						passwordDialog.setVisible(false);
	    				passwordDialog.dispose();
						createNoticeDialog();		
						
					}	
				}	
			}
			
		}else if(ae.getSource()==okButton){
				
			noticeDialog.setVisible(false);
			noticeDialog.dispose();
			
		}else if(ae.getSource()==beginButton){
			
			if(cinaIntroPanel.RateManRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("rate")){
				
				openRateMan();
				
			}else if(cinaIntroPanel.RateGenRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("rate")){
						
				openRateGen();
				
			}else if(cinaIntroPanel.RateParamRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("rate")){
						
				openRateParam();
				
			}else if(cinaIntroPanel.RateViewerRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("rate")){
						
				openRateViewer();
				
			}else if(cinaIntroPanel.RateLibManRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("rate")){
				
				openRateLibMan();
				
			}else if(cinaIntroPanel.ElementSynthRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("element")){
				
				openElementSynth();
				
			}else if(cinaIntroPanel.ElementManRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("element")){
				openElementMan();
				
			}else if(cinaIntroPanel.ElementWorkManRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("element")){
				openElementWorkMan();
				
			}else if(cinaIntroPanel.ThermoManRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("element")){
				openThermoMan();
				
			}else if(cinaIntroPanel.HelpRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("suite")){
				HelpSet hs;
				String helpsetName = "help/help.hs";
				try{
					URL hsURL = getClass().getClassLoader().getResource(helpsetName);
					hs = new HelpSet(null, hsURL);
					hs.createHelpBroker().setDisplayed(true);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}else if(cinaIntroPanel.AboutRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("suite")){
				
				openAbout();
				
			}else if(cinaIntroPanel.RegisterRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("suite")){
				
				openRegister();
				
			}else if(cinaIntroPanel.ElementVizRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("element")){
				
				openElementViz();
				
			}else if(cinaIntroPanel.DataManRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("data")){
				
				openDataMan();
				
			}else if(cinaIntroPanel.DataEvalRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("data")){
				
				openDataEval();
			
			}else if(cinaIntroPanel.DataMassRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("data")){
				
				openDataMass();
			
			}else if(cinaIntroPanel.DataViewerRadioButton.isSelected()
					&& cinaMainDataStructure.getCurrentFeatureSet().equals("data")){
			
				openDataViewer();
				
			}

		}else if(ae.getSource()==logButton){
			if(logButton.getText().equals("Log In")){
				passwordDialog.setLocationRelativeTo(this);
				passwordDialog.setVisible(true);
				passwordField.setText("");
				userField.setText("");
			}else if(logButton.getText().equals("Log Out")){
				if(!windowsOpen()){
					CinaLogoutWorker2 worker = new CinaLogoutWorker2(elementSynthDataStructure, Cina.this, elementSynthFrame);
					worker.execute();
				}else{
					String string = "Logging out will close all open windows. Do you want to log out?";
					createCautionDialog(string, this);
				}
			}
		
		}else if(ae.getSource()==yesButton){
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			
			CinaLogoutWorker2 worker = new CinaLogoutWorker2(elementSynthDataStructure, Cina.this, elementSynthFrame);
			worker.execute();
			
		}else if(ae.getSource()==noButton){
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		}
 	}	
	
	public void logOut(){
	
		beginButton.setEnabled(false);
		
		cinaIntroPanel.RateGenRadioButton.setEnabled(false);
		cinaIntroPanel.RateParamRadioButton.setEnabled(false);
		cinaIntroPanel.ElementSynthRadioButton.setEnabled(false);
		cinaIntroPanel.RateViewerRadioButton.setEnabled(false);
		cinaIntroPanel.RateLibManRadioButton.setEnabled(false);
		cinaIntroPanel.HelpRadioButton.setEnabled(false);
		cinaIntroPanel.AboutRadioButton.setEnabled(false);
		cinaIntroPanel.RegisterRadioButton.setEnabled(false);
		cinaIntroPanel.RateManRadioButton.setEnabled(false);
		cinaIntroPanel.ElementVizRadioButton.setEnabled(false);
		cinaIntroPanel.ElementManRadioButton.setEnabled(false);
		cinaIntroPanel.ElementWorkManRadioButton.setEnabled(false);
		cinaIntroPanel.ThermoManRadioButton.setEnabled(false);
		cinaIntroPanel.DataEvalRadioButton.setEnabled(false);
		cinaIntroPanel.DataViewerRadioButton.setEnabled(false);
		cinaIntroPanel.DataManRadioButton.setEnabled(false);
		cinaIntroPanel.DataMassRadioButton.setEnabled(false);
		cinaIntroPanel.initialize();
		
		logButton.setText("Log In");
		
		if(rateGenFrame!=null){
			rateGenFrame.closeAllFrames();
			rateGenFrame.setVisible(false);
			rateGenFrame.dispose();
		}
		
		if(rateParamFrame!=null){
			rateParamFrame.closeAllFrames();
			rateParamFrame.setVisible(false);
			rateParamFrame.dispose();
		}
		
		if(rateManFrame!=null){
			rateManFrame.closeAllFrames();
			rateManFrame.setVisible(false);
			rateManFrame.dispose();
		}
		
		if(rateViewerFrame!=null){
			rateViewerFrame.closeAllFrames();
			rateViewerFrame.setVisible(false);
			rateViewerFrame.dispose();
		}
		
		if(rateLibManFrame!=null){
			rateLibManFrame.setVisible(false);
			rateLibManFrame.dispose();
		}
		
		if(elementSynthFrame!=null){
			elementSynthFrame.setVisible(false);
			elementSynthFrame.dispose();
		}	
		
		if(elementManFrame!=null){
			elementManFrame.setVisible(false);
			elementManFrame.dispose();
		}
		
		if(elementWorkManFrame!=null){
			elementWorkManFrame.setVisible(false);
			elementWorkManFrame.dispose();
		}
		
		if(thermoManFrame!=null){
			thermoManFrame.setVisible(false);
			thermoManFrame.dispose();
		}
		
		if(elementVizFrame!=null){
			elementVizFrame.closeAllFrames();
			elementVizFrame.setVisible(false);
			elementVizFrame.dispose();
		}
		
		if(aboutFrame!=null){
			aboutFrame.setVisible(false);
			aboutFrame.dispose();
		}
		
		if(registerFrame!=null){
			registerFrame.setVisible(false);
			registerFrame.dispose();
		}
		
		if(dataManFrame!=null){
			dataManFrame.closeAllFrames();
			dataManFrame.setVisible(false);
			dataManFrame.dispose();
		}
		
		if(dataViewerFrame!=null){
			dataViewerFrame.closeAllFrames();
			dataViewerFrame.setVisible(false);
			dataViewerFrame.dispose();	
		}
		
		if(dataEvalFrame!=null){
			dataEvalFrame.closeAllFrames();
			dataEvalFrame.setVisible(false);
			dataEvalFrame.dispose();
		}
		
		if(dataMassFrame!=null){
			dataMassFrame.closeAllFrames();
			dataMassFrame.setVisible(false);
			dataMassFrame.dispose();
		}
		
		rateGenFrame = null;
		rateParamFrame = null;
		rateManFrame = null;
		rateViewerFrame = null;
		elementSynthFrame = null;
		elementManFrame = null;
		elementWorkManFrame = null;
		thermoManFrame = null;
		elementVizFrame = null;
		rateLibManFrame = null;
		aboutFrame = null;
		registerFrame = null;
		dataManFrame = null;
		dataViewerFrame = null;
		dataEvalFrame = null;
		dataMassFrame = null;
		
		//Initialize data structures
		cinaMainDataStructure.initialize();
		rateParamDataStructure.initialize();
		rateManDataStructure.initialize();
		rateLibManDataStructure.initialize();
		rateGenDataStructure.initialize();
		rateViewerDataStructure.initialize();
		registerDataStructure.initialize();
		elementSynthDataStructure.initialize();
		elementManDataStructure.initialize();
		elementWorkManDataStructure.initialize();
		thermoManDataStructure.initialize();
		elementMovieManDataStructure.initialize();
		elementVizDataStructure.initialize();
		dataManDataStructure.initialize();
		dataViewerDataStructure.initialize();
		dataEvalDataStructure.initialize();
		dataMassDataStructure.initialize();
	
	}
	
	/**
	 * Creates the caution dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createCautionDialog(String string, Frame frame){
	
    	cautionDialog = new JDialog(frame, "Attention!", true);
    	cautionDialog.setSize(320, 215);
    	cautionDialog.getContentPane().setLayout(new GridBagLayout());
		cautionDialog.setLocationRelativeTo(frame);
		
		JTextArea cautionTextArea = new JTextArea("", 5, 30);
		cautionTextArea.setLineWrap(true);
		cautionTextArea.setWrapStyleWord(true);
		cautionTextArea.setFont(Fonts.textFont);
		cautionTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(cautionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String cautionString = string;
		cautionTextArea.setText(cautionString);
		cautionTextArea.setCaretPosition(0);
		
		yesButton = new JButton("Yes");
		yesButton.setFont(Fonts.buttonFont);
		yesButton.addActionListener(this);
		
		noButton = new JButton("No");
		noButton.setFont(Fonts.buttonFont);
		noButton.addActionListener(this);
		
		JPanel cautionButtonPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 0, 3);
		cautionButtonPanel.add(yesButton, gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		cautionButtonPanel.add(noButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 0, 3);
		cautionDialog.getContentPane().add(sp, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		cautionDialog.getContentPane().add(cautionButtonPanel, gbc);
		//Cina.setFrameColors(cautionDialog);
		cautionDialog.setVisible(true);
	
	}
	
	/**
	 * Windows open.
	 *
	 * @return true, if successful
	 */
	public boolean windowsOpen(){
	
		boolean windowsOpen = false;
		
		if(rateGenFrame!=null){if(rateGenFrame.isVisible()){windowsOpen = true;}}
		if(rateParamFrame!=null){if(rateParamFrame.isVisible()){windowsOpen = true;}}
		if(rateManFrame!=null){if(rateManFrame.isVisible()){windowsOpen = true;}}
		if(rateViewerFrame!=null){if(rateViewerFrame.isVisible()){windowsOpen = true;}}	
		if(rateLibManFrame!=null){if(rateLibManFrame.isVisible()){windowsOpen = true;}}	
		if(elementSynthFrame!=null){if(elementSynthFrame.isVisible()){windowsOpen = true;}}		
		if(elementManFrame!=null){if(elementManFrame.isVisible()){windowsOpen = true;}}	
		if(elementWorkManFrame!=null){if(elementWorkManFrame.isVisible()){windowsOpen = true;}}	
		if(thermoManFrame!=null){if(thermoManFrame.isVisible()){windowsOpen = true;}}	
		if(elementVizFrame!=null){if(elementVizFrame.isVisible()){windowsOpen = true;}}		
		if(aboutFrame!=null){if(aboutFrame.isVisible()){windowsOpen = true;}}	
		if(registerFrame!=null){if(registerFrame.isVisible()){windowsOpen = true;}}	
		if(dataManFrame!=null){if(dataManFrame.isVisible()){windowsOpen = true;}}	
		if(dataEvalFrame!=null){if(dataEvalFrame.isVisible()){windowsOpen = true;}}
		if(dataMassFrame!=null){if(dataMassFrame.isVisible()){windowsOpen = true;}}
		if(dataViewerFrame!=null){if(dataViewerFrame.isVisible()){windowsOpen = true;}}
		
		return windowsOpen;
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		
		
		//If guest radio button is selected from password dialog
		if(guestRadioButton.isSelected()){
		
			//Remove components for registered user password dialog interface
			passwordDialog.getContentPane().remove(submitButton);
			passwordDialog.getContentPane().remove(userLabel);
			passwordDialog.getContentPane().remove(userField);
			passwordDialog.getContentPane().remove(passwordLabel);
			passwordDialog.getContentPane().remove(passwordField);	
			
			//Set gbc
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			
			//Reformat password dialog for guest
			passwordDialog.getContentPane().add(submitButton, gbc);
			passwordDialog.setSize(355, 140);
			//Cina.setFrameColors(passwordDialog);
			passwordDialog.validate();		
		
		//If user radio button is selected from password dialog
		}else if(userRadioButton.isSelected()){
		
			//Remove components for registered guest password dialog interface
			passwordDialog.getContentPane().remove(submitButton);
		
			//Set gbc
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridwidth = 1;
			
			//Reformat password dialog for user
			passwordDialog.getContentPane().add(userLabel, gbc);
			gbc.gridx = 1;
			gbc.gridy = 2;
			passwordDialog.getContentPane().add(userField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			passwordDialog.getContentPane().add(passwordLabel, gbc);
			gbc.gridx = 1;
			gbc.gridy = 3;
			passwordDialog.getContentPane().add(passwordField, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.gridwidth = 2;
			passwordDialog.getContentPane().add(submitButton, gbc);
			passwordDialog.setSize(355, 230);
			//Cina.setFrameColors(passwordDialog);
			passwordDialog.validate();
		
		}
	
	}
	
	/**
	 * Creates the notice dialog.
	 */
	public void createNoticeDialog(){

    	//Create a new JDialog object
    	noticeDialog = new JDialog(this, "Notice", true);
    	noticeDialog.setSize(355, 200);
    	noticeDialog.getContentPane().setLayout(new BorderLayout());
		noticeDialog.setFocusable(true);
		noticeDialog.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				noticeDialog.setVisible(false);
				noticeDialog.dispose();
			}
		});
		noticeDialog.setLocationRelativeTo(this);
		noticeDialog.addKeyListener(this);
		
		//Create submit button and its properties
		okButton = new JButton("OK");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		buttonPanel.add(okButton, gbc);
		
		String string = "Notice to Users: "
							+ "Use of this system constitutes consent to security monitoring. "
							+ "Improper use could lead to appropriate disciplinary or legal action. \n\n"
							+ "Disclaimer to Users: This software suite is in development and updated almost daily. "
							+ "Please contact coordinator@nucastrodata.org to report bugs or problems. Thank you.";
		
		JTextArea noticeTextArea = new JTextArea(string);
		noticeTextArea.setFont(Fonts.textFont);
		noticeTextArea.setEditable(false);
		noticeTextArea.setLineWrap(true);
		noticeTextArea.setWrapStyleWord(true);
		
		JScrollPane sp = new JScrollPane(noticeTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(200, 200));
		
		noticeTextArea.setCaretPosition(0);
		noticeDialog.getContentPane().add(sp, BorderLayout.CENTER);
		noticeDialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		noticeDialog.setVisible(true);
		noticeDialog.validate();
    }
	
	/**
	 * Creates the password dialog.
	 */
	public void createPasswordDialog(){

    	//Create a new JDialog object
    	passwordDialog = new JDialog(this, "Log on", true);
    	passwordDialog.setSize(355, 140);
    	passwordDialog.getContentPane().setLayout(new GridBagLayout());
		passwordDialog.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				passwordDialog.setVisible(false);
    			passwordDialog.dispose();
    		}
    	});
		passwordDialog.addKeyListener(this);
		
		userLabel = new JLabel("Username: ");
		userLabel.setFont(Fonts.textFont);
		
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(Fonts.textFont); 
		
		userField = new JTextField(10);
		userField.setFont(Fonts.textFont);
		userField.addKeyListener(this);
		if(cinaMainDataStructure.getURLType().equals("DEV")){
			userField.setText("a");
		}
		
		passwordField = new JPasswordField(10);
		passwordField.setFont(Fonts.textFont);
		passwordField.addKeyListener(this);
		if(cinaMainDataStructure.getURLType().equals("DEV")){
			passwordField.setText("s");
		}
		
		//Create submit button and its properties
		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		guestRadioButton = new JRadioButton("Log in as guest (limited access)", true);
		guestRadioButton.addItemListener(this);
		
		userRadioButton = new JRadioButton("Log in as registered user (full access)", false);
		userRadioButton.addItemListener(this);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		buttonGroup.add(guestRadioButton);
		buttonGroup.add(userRadioButton);

		//Layout the components
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 2;
		passwordDialog.getContentPane().add(guestRadioButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		passwordDialog.getContentPane().add(userRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		passwordDialog.getContentPane().add(submitButton, gbc);
	
		passwordDialog.getContentPane().validate();
		
    }
}