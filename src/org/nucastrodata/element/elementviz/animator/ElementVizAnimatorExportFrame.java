package org.nucastrodata.element.elementviz.animator;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


public class ElementVizAnimatorExportFrame extends JFrame implements ActionListener, WindowListener, ChangeListener, ItemListener{

	JDialog exceptionDialog, movieDialog, cautionDialog;
	JTextField timeStepminField, timeStepmaxField, titleField;
	JTextField widthField, hiteField;
	JLabel timeStepminLabel, timeStepmaxLabel, frameLabel
			, widthLabel, hiteLabel, titleLabel, includeLabel, sizeLabel;
	WordWrapLabel topLabel;
	JSlider sizeSlider;
	JCheckBox timeBox, timestepBox, densityBox, tempBox, titleBox;
	JButton beginButton, okButton, yesButton, noButton;
	int fullWidth;
	int fullHite;
	private ElementVizDataStructure ds;
	JSpinner frameSpinner;
	SpinnerNumberModel frameModel;

	/**
	 * Instantiates a new element viz animator export frame.
	 *
	 * @param ds the ds
	 */
	public ElementVizAnimatorExportFrame(ElementVizDataStructure ds){
	
		this.ds = ds;
		
		setSize(620, 420);
		setTitle("Element Synthesis Movie Maker");
		
		//FIELDS/////////////////////////////////////////////////////FIELDS/////////////////////
		timeStepminField = new JTextField(4);
		timeStepmaxField = new JTextField(4);

		titleField = new JTextField(30);
		titleField.setText(" ");

		widthField = new JTextField(4);
		widthField.setEditable(false);

		hiteField = new JTextField(4);
		hiteField.setEditable(false);
		
		frameModel = new SpinnerNumberModel();
		
		frameSpinner = new JSpinner(frameModel);
		frameSpinner.setFont(Fonts.textFont);
		((JSpinner.DefaultEditor)(frameSpinner.getEditor())).getTextField().setEditable(false);
		((JSpinner.DefaultEditor)(frameSpinner.getEditor())).getTextField().setColumns(4);

		
		//LABELS////////////////////////////////////////////////////LABELS//////////////////////
		timeStepminLabel = new JLabel("Timestep min: ");
		timeStepminLabel.setFont(Fonts.textFont);
		
		timeStepmaxLabel = new JLabel("Timestep max: ");
		timeStepmaxLabel.setFont(Fonts.textFont);
		
		topLabel = new WordWrapLabel();
		topLabel.setText("Welcome to the Element Synthesis Movie Maker."
							+ "<br><br>This tool will allow you to create a movie of "
							+ "this Element Synthesis Simulation. "
							+ "When movie generation is complete, "
							+ "you will receive an email containing "
							+ "a hyperlink for movie download.");
		
		widthLabel = new JLabel("Width (in pixels): ");
		widthLabel.setFont(Fonts.textFont);
		
		hiteLabel = new JLabel("Height (in pixels): ");
		hiteLabel.setFont(Fonts.textFont);
		
		frameLabel = new JLabel("Number of timesteps to skip per frame: ");
		frameLabel.setFont(Fonts.textFont);
		
		includeLabel = new JLabel("Include sliderbar for: ");
		includeLabel.setFont(Fonts.textFont);
		
		titleLabel = new JLabel("Title : ");
		titleLabel.setFont(Fonts.textFont);

		sizeLabel = new JLabel("Use slider to adjust image size: ");
		sizeLabel.setFont(Fonts.textFont);

		sizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 100);
		sizeSlider.addChangeListener(this);

		//CHECKBOXES//////////////////////////////////////////////////////////////////////////////
		timeBox = new JCheckBox("Time", true);
		timeBox.setFont(Fonts.textFont);
		
		timestepBox = new JCheckBox("Timestep", true);
		timestepBox.setFont(Fonts.textFont);
		
		densityBox = new JCheckBox("Density", true);
		densityBox.setFont(Fonts.textFont);
		
		tempBox = new JCheckBox("Temp", true);
		tempBox.setFont(Fonts.textFont);
		
		titleBox = new JCheckBox("Title", true);
		titleBox.setFont(Fonts.textFont);
		titleBox.addItemListener(this);

		//BUTTONS///////////////////////////////////////////////////BUTTONS/////////////////////////
		beginButton = new JButton("Create Movie");
		beginButton.addActionListener(this);
		beginButton.setFont(Fonts.buttonFont);
		
		JPanel timePanel = new JPanel();
		double[] columnTime = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL};
		double[] rowTime = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED};
		timePanel.setLayout(new TableLayout(columnTime, rowTime));
		timePanel.add(timeStepminLabel, 	"0, 0, r, c");
		timePanel.add(timeStepminField, 	"2, 0, f, c");
		timePanel.add(timeStepmaxLabel, 	"4, 0, r, c");
		timePanel.add(timeStepmaxField, 	"6, 0, f, c");
		timePanel.add(widthLabel, 			"0, 2, r, c");
		timePanel.add(widthField, 			"2, 2, f, c");
		timePanel.add(hiteLabel, 			"4, 2, r, c");
		timePanel.add(hiteField, 			"6, 2, f, c");
		
		JPanel sliderPanel = new JPanel();
		double[] columnSlider = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL};
		double[] rowSlider = {TableLayoutConstants.FILL};
		sliderPanel.setLayout(new TableLayout(columnSlider, rowSlider));
		sliderPanel.add(sizeLabel, 		"0, 0, r, c");
		sliderPanel.add(sizeSlider, 	"2, 0, 6, 0, f, c");
		
		JPanel framePanel = new JPanel();
		double[] columnFrame = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL};
		double[] rowFrame = {TableLayoutConstants.FILL};
		framePanel.setLayout(new TableLayout(columnFrame, rowFrame));
		framePanel.add(frameLabel, 		"0, 0, l, c");
		framePanel.add(frameSpinner, 	"2, 0, 6, 0, f, c");
		
		JPanel titlePanel = new JPanel();
		double[] columnTitle = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL};
		double[] rowTitle = {TableLayoutConstants.FILL};
		titlePanel.setLayout(new TableLayout(columnTitle, rowTitle));
		titlePanel.add(titleLabel, 	"0, 0, l, c");
		titlePanel.add(titleField, 	"2, 0, 6, 0, f, c");
		
		JPanel includePanel = new JPanel();
		double[] columnInclude = {TableLayoutConstants.FILL
									, 10, TableLayoutConstants.FILL
									, 10, TableLayoutConstants.FILL
									, 10, TableLayoutConstants.FILL};
		double[] rowInclude = {TableLayoutConstants.PREFERRED
									, 10, TableLayoutConstants.PREFERRED};
		includePanel.setLayout(new TableLayout(columnInclude, rowInclude));
		includePanel.add(includeLabel, 	"0, 0, 6, 0, l, c");
		includePanel.add(timeBox, 		"0, 2, f, c");
		includePanel.add(timestepBox, 	"2, 2, f, c");
		includePanel.add(densityBox, 	"4, 2, f, c");
		includePanel.add(tempBox, 		"6, 2, f, c");
		
		double[] column = {20, TableLayoutConstants.FILL, 20};
		double[] row = {20, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL, 20};
		setLayout(new TableLayout(column, row));
		add(topLabel, 		"1, 1, f, c");
		add(timePanel, 		"1, 3, c, c");
		add(sliderPanel, 	"1, 5, f, c");
		add(framePanel, 	"1, 7, f, c");
		add(titlePanel, 	"1, 9, f, c");
		add(includePanel, 	"1, 11, f, c");
		add(beginButton, 	"1, 13, c, c");
		
		addWindowListener(this);
		validate();
	
	}

	/**
	 * Close all frames.
	 */
	public void closeAllFrames(){

	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==titleBox){
			
			if(titleBox.isSelected()){
			
				titleField.setEditable(true);
			
			}else{
			
				titleField.setEditable(false);
			
			}
			
		}
	
	}

	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		timeStepminField.setText(Cina.elementVizFrame.elementVizAnimatorFrame.timeStepminField.getText());
		timeStepmaxField.setText(Cina.elementVizFrame.elementVizAnimatorFrame.timeStepmaxField.getText());
		titleField.setText("");
		
		frameModel.setValue(new Integer(0));
		frameModel.setStepSize(new Integer(1));
		frameModel.setMinimum(new Integer(0));
		frameModel.setMaximum(new Integer(50));
		
		int width = (int)(29*(Cina.elementVizFrame.elementVizAnimatorFrame.elementVizAnimatorPanel.nmax+1));
        int height = (int)(29*(Cina.elementVizFrame.elementVizAnimatorFrame.elementVizAnimatorPanel.zmax+1));
        
        int xmax = (int)(Cina.elementVizFrame.elementVizAnimatorFrame.elementVizAnimatorPanel.xoffset + width);
        int ymax = (int)(Cina.elementVizFrame.elementVizAnimatorFrame.elementVizAnimatorPanel.yoffset + height);
    	    
		fullWidth = xmax+Cina.elementVizFrame.elementVizAnimatorFrame.elementVizAnimatorPanel.xoffset;
		fullHite = ymax+Cina.elementVizFrame.elementVizAnimatorFrame.elementVizAnimatorPanel.yoffset;
		
		if(fullWidth % 2 != 0){
			fullWidth += 1;
		}
		
		if(fullHite % 2 != 0){
			fullHite += 1;
		}
			
		widthField.setText(String.valueOf(fullWidth));
		hiteField.setText(String.valueOf(fullHite));
		
		sizeSlider.removeChangeListener(this);
		sizeSlider.setValue(Cina.elementVizFrame.elementVizAnimatorFrame.zoomSlider.getValue());
		sizeSlider.addChangeListener(this);
	
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
	
		if(ce.getSource()==sizeSlider){
	
			int width = (int)((double)fullWidth*(double)sizeSlider.getValue()/100.0);
			int hite = (int)((double)fullHite*(double)sizeSlider.getValue()/100.0);
	
			if(width % 2 != 0){
				width += 1;
			}
			
			if(hite % 2 != 0){
				hite += 1;
			}
	
			widthField.setText(String.valueOf(width));
			hiteField.setText(String.valueOf(hite));
		
		}
	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){

		if(ae.getSource()==beginButton){
		
			if(checkFields()){
				
				String string = "You are about to submit a request to generate a movie of this element synthesis animation. It may take up to 20 minutes to create the movie. "
									+ "This process will run in the background on our workstations. You can continue to use the Computational Infrastructure for Nuclear Astrophysics, or log off, during this time. "
									+ "Please confirm this action by clicking the YES button below. Click NO if you do not want to create this movie.";
				
				createCautionDialog(string, this);
				

			}
		
		}else if(ae.getSource()==okButton){
		
			movieDialog.setVisible(false);
			movieDialog.dispose();
			
			setVisible(false);
			dispose();
			
		}else if(ae.getSource()==yesButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();

			if(goodMakeMovie()){

				String string = ds.getElementSynthMovieReport();
				createMovieDialog(string, this);
		
			}
		
		}else if(ae.getSource()==noButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}
		
	}

	/**
	 * Check fields.
	 *
	 * @return true, if successful
	 */
	public boolean checkFields(){
	
		boolean checkFields = true;
		
		try{
		
			if(Integer.valueOf(timeStepmaxField.getText()).intValue()<=Integer.valueOf(timeStepminField.getText()).intValue()){
			
				checkFields = false;
			
				String string = "Timestep minimum must be less than Timestep maximum.";
		
				Dialogs.createExceptionDialog(string, this);
			
			}
		
		}catch(NumberFormatException nfe){
		
			nfe.printStackTrace();
		
			String string = "Values for Timestep minimum and maximum must be integer values.";
		
			Dialogs.createExceptionDialog(string, this);
			
			checkFields = false;
		
		}
		
		return checkFields;
	
	}

	//Method to make call to abort element synthesis calc
	/**
	 * Good make movie.
	 *
	 * @return true, if successful
	 */
	public boolean goodMakeMovie(){
	
		ds.setArguments(getArguments());
		ds.setSimulation(ds.getAnimatorNucSimDataStructure().getPath());
		ds.setCompFactor("0");
		ds.setScaleFactor(new Double((double)sizeSlider.getValue()/100.0).toString());
		ds.setFrame_skip_interval(String.valueOf(frameModel.getNumber().intValue()));
	
		//Create local boolean
		boolean goodMakeMovie = false;
		
		//Make CGI call for ABORT ELEMENT SYNTHESIS action
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MAKE ELEMENT SYNTHESIS MOVIE", this);
		
		//If no errors
		if(!flagArray[0]){
		
			//If no reasons
			if(!flagArray[2]){
			
				//If no cautions
				if(!flagArray[1]){
				
					//Set boolean to true
					goodMakeMovie = true;
				
				}
				
			}
			
		}
		
		//Return the boolean						
		return goodMakeMovie;
	
	}

	/**
	 * Gets the arguments.
	 *
	 * @return the arguments
	 */
	public String getArguments(){
	
		String type = Cina.elementVizFrame.elementVizAnimatorFrame.type;
	
		String string = "";
		
		string += Cina.cinaMainDataStructure.getURLType();
		string += " ";
		//INCLUDE///////////////////////////////////////////////////////////////////////////////////
		//TIME
		string += String.valueOf(timeBox.isSelected());
		string += " ";
		//TIMESTEP
		string += String.valueOf(timestepBox.isSelected());
		string += " ";
		//DENSITY
		string += String.valueOf(densityBox.isSelected());
		string += " ";
		//TEMPERATURE
		string += String.valueOf(tempBox.isSelected());
		string += " ";
		//TITLE
		string += String.valueOf(titleBox.isSelected());
		string += " ";	
		//STABLES
		string += String.valueOf(Cina.elementVizFrame.elementVizAnimatorFrame.showStableCheckBox.isSelected());
		string += " ";
		//LABELS
		string += String.valueOf(Cina.elementVizFrame.elementVizAnimatorFrame.showLabelsCheckBox.isSelected());
		string += " ";
		
		//INCLUDE
		if(type.equals("Abundance")){
		
			string += ds.getAbundIncludeValues();
			string += " ";
		
		}else if(type.equals("Derivative")){
		
			string += ds.getDerIncludeValues();
			string += " ";
		
		}else if(type.equals("Reaction Flux")){
		
			string += ds.getFluxIncludeValues();
			string += " ";
		
		}
		
		//SHOW ISOTOPE OUTLINE
		string += String.valueOf(Cina.elementVizFrame.elementVizAnimatorFrame.showOutlineCheckBox.isSelected());
		string += " ";
		//SUM FLUXES
		string += ds.getSum();
		string += " ";
		string += String.valueOf(ds.getAnimatorNucSimDataStructure().getZone());
		string += " ";
		//TITLESTRING
		String titleString = titleField.getText().trim().replaceAll("\"", "\"\"");
		string += "\"" + titleString + "\"";
		string += " ";
		//TIMESTEPMIN
		string += timeStepminField.getText();
		string += " ";
		//TIMESTEPMAX
		string += timeStepmaxField.getText();
		string += " ";
		//DATATYPE
		string += "\"" + type + "\"";
		string += " ";
		//ZMIN
		string += Cina.elementVizFrame.elementVizAnimatorFrame.zmin;
		string += " ";
		//ZMAX
		string += Cina.elementVizFrame.elementVizAnimatorFrame.zmax;
		string += " ";
		String scheme = "";
		
		//SCHEME
		if(type.equals("Abundance")){
			
			scheme = ds.getAbundScheme();
		
			string += scheme;
			string += " ";
		
			if(scheme.equals("Continuous")){
			
				//XOR
				string += String.valueOf(ds.getAbundColorValues()[0]);
				string += " ";
				//XOG
				string += String.valueOf(ds.getAbundColorValues()[1]);
				string += " ";
				//XOB
				string += String.valueOf(ds.getAbundColorValues()[2]);
				string += " ";
				//AR
				string += String.valueOf(ds.getAbundColorValues()[3]);
				string += " ";
				//AG
				string += String.valueOf(ds.getAbundColorValues()[4]);
				string += " ";
				//AB
				string += String.valueOf(ds.getAbundColorValues()[5]);
				string += " ";
				//ABUNDMIN
				string += String.valueOf(Cina.elementVizFrame.elementVizAnimatorFrame.abundMin);
				string += " ";
				//ABUNDMAX
				string += String.valueOf(Cina.elementVizFrame.elementVizAnimatorFrame.abundMax);
				string += " ";
				
			}else if(scheme.equals("Binned")){
			
				Vector dataVector = ds.getAbundBinData();
				
				for(int i=0; i<dataVector.size(); i++){
					
					string += String.valueOf(((Integer)((Vector)dataVector.elementAt(i)).elementAt(0)).intValue());
 					string += " ";
 					
 					string += String.valueOf(((Integer)((Vector)dataVector.elementAt(i)).elementAt(1)).intValue());
 					string += " ";
 					
 					string += String.valueOf(((Boolean)((Vector)dataVector.elementAt(i)).elementAt(2)).booleanValue());
 					string += " ";
 					
					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(3)).getRed());
					string += " ";
 					
 					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(3)).getGreen());
					string += " ";
 				
 					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(3)).getBlue());
					string += " ";
 				
				}
			
			}
		
		}else if(type.equals("Derivative")){
			
			scheme = ds.getDerScheme();
			
			string += scheme;
			string += " ";
			
			if(scheme.equals("Continuous")){
			
				//XOR
				string += String.valueOf(ds.getDerColorValues()[0]);
				string += " ";
				//XOG
				string += String.valueOf(ds.getDerColorValues()[1]);
				string += " ";
				//XOB
				string += String.valueOf(ds.getDerColorValues()[2]);
				string += " ";
				//AR
				string += String.valueOf(ds.getDerColorValues()[3]);
				string += " ";
				//AG
				string += String.valueOf(ds.getDerColorValues()[4]);
				string += " ";
				//AB
				string += String.valueOf(ds.getDerColorValues()[5]);
				string += " ";
				//DERMIN
				string += String.valueOf(Cina.elementVizFrame.elementVizAnimatorFrame.derAbundMin);
				string += " ";
				//DERMAX
				string += String.valueOf(Cina.elementVizFrame.elementVizAnimatorFrame.derAbundMax);
				string += " ";
				//DERTHRESHOLD
				string += String.valueOf(Cina.elementVizFrame.elementVizAnimatorFrame.derAbundMag);
				string += " ";
				
			}else if(scheme.equals("Binned")){
			
				Vector dataVector = ds.getDerBinData();
				
				for(int i=0; i<dataVector.size(); i++){
					
					string += String.valueOf(((Integer)((Vector)dataVector.elementAt(i)).elementAt(0)).intValue());
 					string += " ";
 					
 					string += String.valueOf(((Integer)((Vector)dataVector.elementAt(i)).elementAt(1)).intValue());
 					string += " ";
 					
 					string += String.valueOf(((Boolean)((Vector)dataVector.elementAt(i)).elementAt(2)).booleanValue());
 					string += " ";
 					
					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(3)).getRed());
					string += " ";
 					
 					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(3)).getGreen());
					string += " ";
 				
 					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(3)).getBlue());
					string += " ";
					
					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(4)).getRed());
					string += " ";
 					
 					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(4)).getGreen());
					string += " ";
 				
 					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(4)).getBlue());
					string += " ";
 				
				}
			
			}
			
		}else if(type.equals("Reaction Flux")){
		
			scheme = ds.getFluxScheme();
		
			string += scheme;
			string += " ";
			
			if(scheme.equals("Continuous")){
			
				//XOR
				string += String.valueOf(ds.getFluxColorValues()[0]);
				string += " ";
				//XOG
				string += String.valueOf(ds.getFluxColorValues()[1]);
				string += " ";
				//XOB
				string += String.valueOf(ds.getFluxColorValues()[2]);
				string += " ";
				//AR
				string += String.valueOf(ds.getFluxColorValues()[3]);
				string += " ";
				//AG
				string += String.valueOf(ds.getFluxColorValues()[4]);
				string += " ";
				//AB
				string += String.valueOf(ds.getFluxColorValues()[5]);
				string += " ";
				//FLUXMIN
				string += String.valueOf(Cina.elementVizFrame.elementVizAnimatorFrame.fluxMin);
				string += " ";
				//FLUXMAX
				string += String.valueOf(Cina.elementVizFrame.elementVizAnimatorFrame.fluxMax);
				string += " ";
				
			}else if(scheme.equals("Binned")){
			
				Vector dataVector = ds.getFluxBinData();
				
				for(int i=0; i<dataVector.size(); i++){
					
					string += String.valueOf(((Integer)((Vector)dataVector.elementAt(i)).elementAt(0)).intValue());
 					string += " ";
 					
 					string += String.valueOf(((Integer)((Vector)dataVector.elementAt(i)).elementAt(1)).intValue());
 					string += " ";
 					
 					string += String.valueOf(((Boolean)((Vector)dataVector.elementAt(i)).elementAt(2)).booleanValue());
 					string += " ";
 					
					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(3)).getRed());
					string += " ";
 					
 					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(3)).getGreen());
					string += " ";
 				
 					string += String.valueOf(((Color)((Vector)dataVector.elementAt(i)).elementAt(3)).getBlue());
					string += " ";
					
					string += String.valueOf(((Integer)((Vector)dataVector.elementAt(i)).elementAt(4)).intValue());
					string += " ";
 				
 					string += String.valueOf(((Integer)((Vector)dataVector.elementAt(i)).elementAt(5)).intValue());
					string += " ";
 				
				}
			
			}
		
		}
		
		return string;
	
	}

	//Window closing listener
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent we) {   
	
		if(we.getSource()==this){  

		   setVisible(false);
		   dispose();
		   
    	}
    	
    } 
    
    //Remainder of windowListener methods
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(WindowEvent we){}
    
    //Method to create rate save report
	/**
     * Creates the movie dialog.
     *
     * @param string the string
     * @param frame the frame
     */
    public void createMovieDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	movieDialog = new JDialog(frame, "Movie creation started successfully", true);
    	movieDialog.setSize(350, 210);
    	movieDialog.getContentPane().setLayout(new GridBagLayout());
		movieDialog.setLocationRelativeTo(frame);
		
		JTextArea movieTextArea = new JTextArea("", 5, 30);
		movieTextArea.setLineWrap(true);
		movieTextArea.setWrapStyleWord(true);
		movieTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(movieTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		movieTextArea.setText(string);
		movieTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(this);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		movieDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		movieDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(movieDialog);
		
		//Show the dialog
		movieDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the caution dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createCautionDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
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
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");
		cautionLabel.setFont(Fonts.textFont);	
		
		yesButton = new JButton("Yes");
		yesButton.setFont(Fonts.buttonFont);
		yesButton.addActionListener(this);
		
		noButton = new JButton("No");
		noButton.setFont(Fonts.buttonFont);
		noButton.addActionListener(this);
		
		JPanel cautionButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionButtonPanel.add(yesButton, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		cautionButtonPanel.add(noButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		cautionDialog.getContentPane().add(cautionButtonPanel, gbc1);

		cautionDialog.setVisible(true);

	}

}