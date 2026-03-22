package org.nucastrodata.element.elementviz;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundPlotFrame;
import org.nucastrodata.element.elementviz.abundtable.ElementVizAbundTableFrame;
import org.nucastrodata.element.elementviz.animator.ElementVizAnimatorFrame;
import org.nucastrodata.element.elementviz.edot.ElementVizEdotPlotFrame;
import org.nucastrodata.element.elementviz.finalabund.ElementVizFinalAbundFrame;
import org.nucastrodata.element.elementviz.intflux.ElementVizIntFluxFrame;
import org.nucastrodata.element.elementviz.scale.ElementVizScalePlotFrame;
import org.nucastrodata.element.elementviz.sum.ElementVizSumPlotFrame;
import org.nucastrodata.element.elementviz.thermo.ElementVizThermoPlotFrame;
import org.nucastrodata.element.elementviz.weight.ElementVizWeightPlotFrame;
import org.nucastrodata.element.elementviz.worker.ElementVizAnimatorFrameWorker;
import org.nucastrodata.element.elementviz.worker.ElementVizEdotPlotFrameWorker;
import org.nucastrodata.element.elementviz.worker.ElementVizFinalAbundFrameWorker;
import org.nucastrodata.element.elementviz.worker.ElementVizIntFluxFrameWorker;
import org.nucastrodata.element.elementviz.worker.ElementVizScalePlotFrameInitWorker;
import org.nucastrodata.element.elementviz.worker.ElementVizSumPlotFrameWorker;
import org.nucastrodata.element.elementviz.worker.ElementVizThermoPlotFrameWorker;
import org.nucastrodata.io.CGICom;

import java.awt.event.*;
import org.nucastrodata.ColorFormat;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;

import java.util.*;

public class ElementVizToolsPanel extends WizardPanel implements ActionListener, ItemListener{

	GridBagConstraints gbc;
	JButton abundPlotButton, thermoPlotButton, animatorButton, intFluxButton, weightPlotButton, scalePlotButton
				, finalAbundButton, okIntFluxButton, okAnimatorButton, sampleButton, sumPlotButton, abundTableButton, edotPlotButton;

	JScrollPane sp;
	JTextArea descTextArea;
	JLabel oneDLabel, twoDLabel, topLabel, tableLabel;
	JCheckBox abundBox, fluxBox, sumFluxBox, sumFluxBoxInt;
	
	final String defaultDescString = "Roll your mouse over a button to get a description of each tools capabilities.";
	final String sumPlotString = "- Create 1-D plots of final abundance vs. mass number, atomic number or neutron number.\n\n- Create 1-D plots of fractional abundance difference vs. mass number, atomic number or neutron number.";
	final String abundPlotString = "- Create 1-D plots of abundance vs. time for selected isotopes.\n\n- Create 1-D plots of abundance ratio vs. mass number for selected isotopes.";
	final String thermoPlotString = "- Create 1-D plots of temperature vs. time.\n\n-Create 1-D plots of density vs. time.";
	final String animatorString = "- Create animated 2-D plots on a nuclide chart of "
									+ "abundance, time derivative of abundance, or normalized reaction flux vs. time.\n\n- Generate exportable animations.";
	final String intFluxString = "- Create 2-D plots on a nuclide chart of integrated reaction flux and final abundance.";
	final String weightPlotString = "- Create 1-D plots of final weighted abundance vs. zone number.\n\n- Create 1-D plots of summed final weighted abundance vs. mass number.";
	final String scalePlotString = "- Create 1-D plots of final weighted abundance vs. rate scale factor.";
	final String finalAbundString = "- Create 2-D plots on a nuclide chart of summed final weighted abundance.";
	final String abundTableString = "- Create a table of points of final abundance for selected isotopes.";
	final String edotPlotString = "- Create 1-D plots of nuclear energy generation vs. time.";
	
	JDialog delayDialog, intFluxDialog, progressBarDialog, animatorDialog;

	public JProgressBar progressBar;

	public JTextArea progressBarTextArea;
	
	boolean allGoodTimeMappings
    			, allGoodIsotopeMappings
    			, allGoodReactionMappings
    			, allGoodAbundProfiles
    			, allGoodFluxProfiles
    			, allGoodThermoProfiles
    			, allGoodFinalAbundProfiles
    			, noFluxesAvailable
    			, sensSimsFound;
	
	int sampleIndex = 0;
	private ElementVizDataStructure ds;
	private CGICom cgiCom;
	private ElementVizFrame frame;
	
	public ElementVizToolsPanel(ElementVizDataStructure ds, ElementVizFrame frame, CGICom cgiCom){
	
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		frame.setCurrentPanelIndex(2);

		JPanel mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		addWizardPanel("Element Synthesis Visualizer", "Visualization Tools", "2", "2");
	
		oneDLabel = new JLabel("1-D Plots");
		twoDLabel = new JLabel("2-D Plots");
		tableLabel = new JLabel("Point Tables");
	
		topLabel = new JLabel("<html></html>");
	
		abundTableButton = new JButton("<html>Final Abundance<p>Table of Points</html>");
		abundTableButton.setHorizontalTextPosition(SwingConstants.LEFT);
		abundTableButton.setFont(Fonts.buttonFont);
		abundTableButton.addActionListener(this);
		abundTableButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(abundTableString);
				descTextArea.setCaretPosition(0);
				sampleIndex = 0;
				setButtonForegrounds(abundTableButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
	
		sumPlotButton = new JButton("<html>Final Abundance<p>Plotting Interface</html>");
		sumPlotButton.setHorizontalTextPosition(SwingConstants.LEFT);
		sumPlotButton.setFont(Fonts.buttonFont);
		sumPlotButton.addActionListener(this);
		sumPlotButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(sumPlotString);
				descTextArea.setCaretPosition(0);
				sampleIndex = 0;
				setButtonForegrounds(sumPlotButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		abundPlotButton = new JButton("<html>Abundance<p>Plotting Interface</html>");
		abundPlotButton.setHorizontalTextPosition(SwingConstants.LEFT);
		abundPlotButton.setFont(Fonts.buttonFont);
		abundPlotButton.addActionListener(this);
		abundPlotButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(abundPlotString);
				descTextArea.setCaretPosition(0);
				sampleIndex = 0;
				setButtonForegrounds(abundPlotButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		thermoPlotButton = new JButton("<html>Thermodynamic Profile<p>Plotting Interface</html>");
		thermoPlotButton.setFont(Fonts.buttonFont);
		thermoPlotButton.setHorizontalTextPosition(SwingConstants.LEFT);
		thermoPlotButton.addActionListener(this);
		thermoPlotButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(thermoPlotString);
				descTextArea.setCaretPosition(0);
				sampleIndex = 2;
				setButtonForegrounds(thermoPlotButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		edotPlotButton = new JButton("<html>Nuclear Energy Generation<p>Plotting Interface</html>");
		edotPlotButton.setFont(Fonts.buttonFont);
		edotPlotButton.setHorizontalTextPosition(SwingConstants.LEFT);
		edotPlotButton.addActionListener(this);
		edotPlotButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(edotPlotString);
				descTextArea.setCaretPosition(0);
				sampleIndex = 2;
				setButtonForegrounds(edotPlotButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		animatorButton = new JButton("<html>Element Synthesis<p>Animator</html>");
		animatorButton.setFont(Fonts.buttonFont);
		animatorButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		animatorButton.addActionListener(this);
		animatorButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(animatorString);
				descTextArea.setCaretPosition(0);
				sampleIndex = 3;
				setButtonForegrounds(animatorButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		intFluxButton = new JButton("<html>Integrated Flux<p>Nuclide Chart</html>");
		intFluxButton.setFont(Fonts.buttonFont);
		intFluxButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		intFluxButton.addActionListener(this);
		intFluxButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(intFluxString);
				descTextArea.setCaretPosition(0);
				sampleIndex = 5;
				setButtonForegrounds(intFluxButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		weightPlotButton = new JButton("<html>Final Weighted Abund<p>Plotting Interface</html>");
		weightPlotButton.setFont(Fonts.buttonFont);
		weightPlotButton.setHorizontalTextPosition(SwingConstants.LEFT);
		weightPlotButton.addActionListener(this);
		weightPlotButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(weightPlotString);
				descTextArea.setCaretPosition(0);
				sampleIndex = 1;
				setButtonForegrounds(weightPlotButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		scalePlotButton = new JButton("<html>Sensitivity Study<p>Plotting Interface</html>");
		scalePlotButton.setFont(Fonts.buttonFont);
		scalePlotButton.setHorizontalTextPosition(SwingConstants.LEFT);
		scalePlotButton.addActionListener(this);
		scalePlotButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(scalePlotString);
				descTextArea.setCaretPosition(0);
				sampleIndex = 6;
				setButtonForegrounds(scalePlotButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		finalAbundButton = new JButton("<html>Final Weighted Abund<p>Nuclide Chart</html>");
		finalAbundButton.setFont(Fonts.buttonFont);
		finalAbundButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		finalAbundButton.addActionListener(this);
		finalAbundButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(finalAbundString);
				descTextArea.setCaretPosition(0);
				sampleIndex = 4;
				setButtonForegrounds(finalAbundButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		sampleButton = new JButton("View Sample");
		sampleButton.setFont(Fonts.buttonFont);
		sampleButton.addActionListener(this);
		
		descTextArea = new JTextArea(defaultDescString);
		descTextArea.setFont(Fonts.textFont);
		descTextArea.setLineWrap(true);
		descTextArea.setWrapStyleWord(true);
		
		sp = new JScrollPane(descTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(175, 165));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		mainPanel.add(oneDLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(abundPlotButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		mainPanel.add(sumPlotButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		mainPanel.add(scalePlotButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		
		mainPanel.add(weightPlotButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		
		mainPanel.add(thermoPlotButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		
		mainPanel.add(edotPlotButton, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 4;
		
		mainPanel.add(sp, gbc);
		
		gbc.gridheight = 1;
		
		gbc.gridx = 2;
		gbc.gridy = 0;

		mainPanel.add(twoDLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(animatorButton, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		
		mainPanel.add(finalAbundButton, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		
		mainPanel.add(intFluxButton, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 2;
		gbc.gridy = 4;
		
		mainPanel.add(tableLabel, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 2;
		gbc.gridy = 5;
		
		mainPanel.add(abundTableButton, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		
		gbc.gridwidth = 1;
		
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}

	public void setButtonForegrounds(JButton button){
		
	      String osName = System.getProperty("os.name");
	      if (!osName.startsWith("Mac OS")) {
	    	sumPlotButton.setForeground(Color.white);
	    	abundPlotButton.setForeground(Color.white);
	  		thermoPlotButton.setForeground(Color.white);
	  		edotPlotButton.setForeground(Color.white);
	  		animatorButton.setForeground(Color.white);
	  		intFluxButton.setForeground(Color.white);
	  		weightPlotButton.setForeground(Color.white);
	  		scalePlotButton.setForeground(Color.white);
	  		finalAbundButton.setForeground(Color.white);
	  		abundTableButton.setForeground(Color.white);
	      }else{
	    	sumPlotButton.setForeground(ColorFormat.backColor);
	    	abundPlotButton.setForeground(ColorFormat.backColor);
	  		thermoPlotButton.setForeground(ColorFormat.backColor);
	  		edotPlotButton.setForeground(ColorFormat.backColor);
	  		animatorButton.setForeground(ColorFormat.backColor);
	  		intFluxButton.setForeground(ColorFormat.backColor);
	  		weightPlotButton.setForeground(ColorFormat.backColor);
	  		scalePlotButton.setForeground(ColorFormat.backColor);
	  		finalAbundButton.setForeground(ColorFormat.backColor);
	  		abundTableButton.setForeground(ColorFormat.backColor);
	      }
		
		button.setForeground(Color.red);
	
	}

	public void openSampleFrame(){
		
		if(frame.elementVizSampleFrame==null){
		
			frame.elementVizSampleFrame = new ElementVizSampleFrame();
			frame.elementVizSampleFrame.setCurrentState(sampleIndex);
			frame.elementVizSampleFrame.setVisible(true);
		
		}else{
		
			frame.elementVizSampleFrame.setCurrentState(sampleIndex);
			frame.elementVizSampleFrame.setVisible(true);
			
		}
	
	}

	public void itemStateChanged(ItemEvent ie){
    
    	if(ie.getSource()==fluxBox){
    	
    		if(fluxBox.isSelected()){
    		
    			sumFluxBox.setEnabled(true);
    		
    		}else{
    		
    			sumFluxBox.setEnabled(false);
    			sumFluxBox.setSelected(false);
    			    		
    		}
    	
    	}
    
    } 

	public void openAbundTableFrame(){
	
		if(frame.elementVizAbundTableFrame==null){
			
			frame.elementVizAbundTableFrame = new ElementVizAbundTableFrame(ds);
			frame.elementVizAbundTableFrame.setCurrentState();
			frame.elementVizAbundTableFrame.setSize(900,760);
			frame.elementVizAbundTableFrame.setResizable(true);
			frame.elementVizAbundTableFrame.setTitle("Final Abundance Table Interface");
			frame.elementVizAbundTableFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
			frame.elementVizAbundTableFrame.setVisible(true);
			
		}else{
			
			frame.elementVizAbundTableFrame.setCurrentState();
			frame.elementVizAbundTableFrame.setVisible(true);
			
		}
	
	}

	public void openThermoPlot(){
	
		ElementVizThermoPlotFrameWorker worker  = new ElementVizThermoPlotFrameWorker(ds, frame, this);
		worker.execute();
	
	}
	
	public void openEdotPlot(){
		
		ElementVizEdotPlotFrameWorker worker  = new ElementVizEdotPlotFrameWorker(ds, frame, this);
		worker.execute();
	
	}

	public void openThermoPlotFrame(){
	
		if(frame.elementVizThermoPlotFrame!=null){
			
    		frame.elementVizThermoPlotFrame.setFormatPanelState();
    		frame.elementVizThermoPlotFrame.setNucSimListPanel();
    		frame.elementVizThermoPlotFrame.redrawPlot();
    		frame.elementVizThermoPlotFrame.setVisible(true);

    	}else{
    	
    		frame.elementVizThermoPlotFrame = new ElementVizThermoPlotFrame(ds);
    		frame.elementVizThermoPlotFrame.setVisible(true);
    	
    	}
	
	}
	
	public void openEdotPlotFrame(){
		
		if(frame.elementVizEdotPlotFrame!=null){
			
    		frame.elementVizEdotPlotFrame.setFormatPanelState();
    		frame.elementVizEdotPlotFrame.setNucSimListPanel();
    		frame.elementVizEdotPlotFrame.redrawPlot();
    		frame.elementVizEdotPlotFrame.setVisible(true);

    	}else{
    	
    		frame.elementVizEdotPlotFrame = new ElementVizEdotPlotFrame(ds);
    		frame.elementVizEdotPlotFrame.setVisible(true);
    	
    	}
	
	}

	public void openSumPlot(){

		ElementVizSumPlotFrameWorker worker  = new ElementVizSumPlotFrameWorker(ds, frame, this);
		worker.execute();
		
	}
	
	public void openSumPlotFrame(TreeMap<String, HashMap<String, TreeMap<Integer, Double>>> finalAbundMap){
	
		if(frame.elementVizSumPlotFrame!=null){
			
  			frame.elementVizSumPlotFrame.setFinalAbundMap(finalAbundMap);
    		frame.elementVizSumPlotFrame.initializeFormatPanel();
    		frame.elementVizSumPlotFrame.initializeNucSimListPanel();
    		frame.elementVizSumPlotFrame.redrawPlot();
    		frame.elementVizSumPlotFrame.setVisible(true);

    	}else{
    	
    		frame.elementVizSumPlotFrame = new ElementVizSumPlotFrame(finalAbundMap);
    		frame.elementVizSumPlotFrame.setVisible(true);
    	
    	}
	
	}
	
	public void openAnimator(){

		animatorDialog.setVisible(false);
		animatorDialog.dispose();
	
		ElementVizAnimatorFrameWorker worker  = new ElementVizAnimatorFrameWorker(ds, 
																					frame, 
																					this, 
																					abundBox.isSelected(), 
																					fluxBox.isSelected(), 
																					sumFluxBox.isSelected());
		worker.execute();
	
	}

	public void openIntFlux(){
		
		intFluxDialog.setVisible(false);
		intFluxDialog.dispose();
		
		ElementVizIntFluxFrameWorker worker  = new ElementVizIntFluxFrameWorker(ds, frame, this, sumFluxBoxInt.isSelected());
		worker.execute();
	
	}

	public void openIntFluxFrame(){
	
		if(frame.elementVizIntFluxFrame!=null){
			
			frame.elementVizIntFluxFrame.initialize();
    		frame.elementVizIntFluxFrame.setVisible(true);
    		
    		Vector noFluxesVector = new Vector();
    		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
				if(!ds.getNucSimDataStructureArrayIntFlux()[i].getHasFluxes()){
					noFluxesVector.addElement(ds.getNucSimDataStructureArrayIntFlux()[i].getNucSimName());
				}	
    		}
    		
    		noFluxesVector.trimToSize();
    		
    		if(noFluxesVector.size()>0){
    			String string = "The following simulations do not have reaction flux data and will not be available in the Integrated Flux Nuclide Chart.\n\n";
    			for(int i=0; i<noFluxesVector.size(); i++){
    				string += noFluxesVector.elementAt(i).toString() + "\n";
    			}
    			Dialogs.createExceptionDialog(string, frame.elementVizIntFluxFrame);
    		}

    	}else{

    		frame.elementVizIntFluxFrame = new ElementVizIntFluxFrame(ds);
    		frame.elementVizIntFluxFrame.initialize();
    		frame.elementVizIntFluxFrame.setVisible(true);
    		
    		Vector noFluxesVector = new Vector();
    		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
				if(!ds.getNucSimDataStructureArrayIntFlux()[i].getHasFluxes()){
					noFluxesVector.addElement(ds.getNucSimDataStructureArrayIntFlux()[i].getNucSimName());
				}
			}

    		noFluxesVector.trimToSize();
    		
    		if(noFluxesVector.size()>0){
    			String string = "The following simulations do not have reaction flux data and will not be available in the Integrated Flux Nuclide Chart.\n\n";
    			for(int i=0; i<noFluxesVector.size(); i++){
    				string += noFluxesVector.elementAt(i).toString() + "\n";
    			}
    			Dialogs.createExceptionDialog(string, frame.elementVizIntFluxFrame);
    		}
    	
    	}

	}

	public void openAbundPlotFrame(){
		
		if(frame.elementVizAbundPlotFrame==null){
				
				frame.elementVizAbundPlotFrame = new ElementVizAbundPlotFrame(ds);
				frame.elementVizAbundPlotFrame.setCurrentState();
				frame.elementVizAbundPlotFrame.setSize(900,760);
				frame.elementVizAbundPlotFrame.setResizable(true);
    			frame.elementVizAbundPlotFrame.setTitle("Abundance Plotting Interface");
    			frame.elementVizAbundPlotFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
    			frame.elementVizAbundPlotFrame.setVisible(true);
		}else{
			
			frame.elementVizAbundPlotFrame.setCurrentState();
			frame.elementVizAbundPlotFrame.setVisible(true);
			
		}
	
	}
	
	public void openAnimatorFrame(){
		
		if(abundBox.isSelected() && !fluxBox.isSelected()){
	
			if(frame.elementVizAnimatorFrame!=null){
				
				frame.elementVizAnimatorFrame.initialize();
	    		frame.elementVizAnimatorFrame.setVisible(true);
	
	    	}else{
	    	
	    		frame.elementVizAnimatorFrame = new ElementVizAnimatorFrame(ds);
	    		frame.elementVizAnimatorFrame.initialize();
	    		frame.elementVizAnimatorFrame.setVisible(true);
	    	
	    	}
	    	
		}else if(!abundBox.isSelected() && fluxBox.isSelected()){
		
			if(frame.elementVizAnimatorFrame!=null){
				
				frame.elementVizAnimatorFrame.initialize();
	    		frame.elementVizAnimatorFrame.setVisible(true);
	    		
	    		Vector noFluxesVector = new Vector();

	    		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

					if(!ds.getNucSimDataStructureArrayAnimator()[i].getHasFluxes()){
		
						noFluxesVector.addElement(ds.getNucSimDataStructureArrayAnimator()[i].getNucSimName());
		
					}	
		
				}
	    		
	    		noFluxesVector.trimToSize();
	    		
	    		if(noFluxesVector.size()>0){
	    		
	    			String string = "The following simulations do not have reaction flux data and will not be available in the Element Synthesis Animator.\n\n";
	    			
	    			for(int i=0; i<noFluxesVector.size(); i++){
	    			
	    				string += noFluxesVector.elementAt(i).toString() + "\n";
	    			
	    			}
	    			
	    			Dialogs.createExceptionDialog(string, frame.elementVizAnimatorFrame);
	    		
	    		}
	
	    	}else{
	    	
	    		frame.elementVizAnimatorFrame = new ElementVizAnimatorFrame(ds);
	    		frame.elementVizAnimatorFrame.initialize();
	    		frame.elementVizAnimatorFrame.setVisible(true);

				Vector noFluxesVector = new Vector();

	    		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
	
					if(!ds.getNucSimDataStructureArrayAnimator()[i].getHasFluxes()){
		
						noFluxesVector.addElement(ds.getNucSimDataStructureArrayAnimator()[i].getNucSimName());
		
					}	
		
				}
	    		
	    		noFluxesVector.trimToSize();
	    		
	    		if(noFluxesVector.size()>0){
	    		
	    			String string = "The following simulations do not have reaction flux data and will not be available in the Element Synthesis Animator.\n\n";
	    			
	    			for(int i=0; i<noFluxesVector.size(); i++){
	    			
	    				string += noFluxesVector.elementAt(i).toString() + "\n";
	    			
	    			}
	    			
	    			Dialogs.createExceptionDialog(string, frame.elementVizAnimatorFrame);
	    		
	    		}
	    		
	    	}
		
		}else if(abundBox.isSelected() && fluxBox.isSelected()){
		
			if(frame.elementVizAnimatorFrame!=null){
				
				frame.elementVizAnimatorFrame.initialize();
	    		frame.elementVizAnimatorFrame.setVisible(true);
	    		
	    		Vector noFluxesVector = new Vector();

	    		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

					if(!ds.getNucSimDataStructureArrayAnimator()[i].getHasFluxes()){
		
						noFluxesVector.addElement(ds.getNucSimDataStructureArrayAnimator()[i].getNucSimName());
		
					}	
		
				}
	    		
	    		noFluxesVector.trimToSize();
	    		
	    		if(noFluxesVector.size()>0){
	    		
	    			String string = "The following simulations do not have reaction flux data and will not be available in the Element Synthesis Animator.\n\n";
	    			
	    			for(int i=0; i<noFluxesVector.size(); i++){
	    			
	    				string += noFluxesVector.elementAt(i).toString() + "\n";
	    			
	    			}
	    			
	    			Dialogs.createExceptionDialog(string, frame.elementVizAnimatorFrame);
	    		
	    		}
	
	    	}else{
	    	

	    		frame.elementVizAnimatorFrame = new ElementVizAnimatorFrame(ds);
	    		frame.elementVizAnimatorFrame.initialize();
	    		frame.elementVizAnimatorFrame.setVisible(true);
	    		
	    		Vector noFluxesVector = new Vector();

	    		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
	
					if(!ds.getNucSimDataStructureArrayAnimator()[i].getHasFluxes()){
		
						noFluxesVector.addElement(ds.getNucSimDataStructureArrayAnimator()[i].getNucSimName());
		
					}	
		
				}
	    		
	    		noFluxesVector.trimToSize();
	    		
	    		if(noFluxesVector.size()>0){
	    		
	    			String string = "The following simulations do not have reaction flux data and will not be available in the Element Synthesis Animator.\n\n";
	    			
	    			for(int i=0; i<noFluxesVector.size(); i++){
	    			
	    				string += noFluxesVector.elementAt(i).toString() + "\n";
	    			
	    			}
	    			
	    			Dialogs.createExceptionDialog(string, frame.elementVizAnimatorFrame);
	    		
	    		}
	    	
	    	}
		
		}
	
	}
	
	public void openScalePlot(){
		
		ElementVizScalePlotFrameInitWorker worker  = new ElementVizScalePlotFrameInitWorker(ds, frame, this, cgiCom);
		worker.execute();
	
	}
	
	public void openScalePlotFrame(){
	
		if(frame.elementVizScalePlotFrame==null){
			
			frame.elementVizScalePlotFrame = new ElementVizScalePlotFrame(ds);
			frame.elementVizScalePlotFrame.setCurrentState();
			frame.elementVizScalePlotFrame.setSize(900,760);
			frame.elementVizScalePlotFrame.setResizable(true);
			frame.elementVizScalePlotFrame.setTitle("Sensitivity Study Plotting Interface");
			frame.elementVizScalePlotFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
			frame.elementVizScalePlotFrame.setVisible(true);
			
		}else{
			
			frame.elementVizScalePlotFrame.setCurrentState();
			frame.elementVizScalePlotFrame.setVisible(true);
			
		}
	}
	
	public void openWeightPlotFrame(){
	
		if(frame.elementVizWeightPlotFrame==null){
			
			frame.elementVizWeightPlotFrame = new ElementVizWeightPlotFrame(ds);
			frame.elementVizWeightPlotFrame.setCurrentState();
			frame.elementVizWeightPlotFrame.setSize(900,760);
			frame.elementVizWeightPlotFrame.setResizable(true);
			frame.elementVizWeightPlotFrame.setTitle("Weighted Abundance Plotting Interface");
			frame.elementVizWeightPlotFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
			frame.elementVizWeightPlotFrame.setVisible(true);
		}else{
			
			frame.elementVizWeightPlotFrame.setCurrentState();
			frame.elementVizWeightPlotFrame.setVisible(true);
			
		}
	
	}

	public void openFinalAbund(){
	
		ElementVizFinalAbundFrameWorker worker  = new ElementVizFinalAbundFrameWorker(ds, frame, this);
		worker.execute();
	
	}

	public void openFinalAbundFrame(){
	
		if(frame.elementVizFinalAbundFrame!=null){
			
			frame.elementVizFinalAbundFrame.initialize();
    		frame.elementVizFinalAbundFrame.setVisible(true);

    	}else{
    	
    		frame.elementVizFinalAbundFrame = new ElementVizFinalAbundFrame(ds);
    		frame.elementVizFinalAbundFrame.initialize();
    		frame.elementVizFinalAbundFrame.setVisible(true);
    	
    	}
	
	}

	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==sampleButton){
			
			openSampleFrame();
			
		}else if(ae.getSource()==abundTableButton){
			
			openAbundTableFrame();	
		
		}else if(ae.getSource()==abundPlotButton){
		
			openAbundPlotFrame();
			
		}else if(ae.getSource()==sumPlotButton){
			
			openSumPlot();
			
		}else if(ae.getSource()==thermoPlotButton){
			
			openThermoPlot();
			
		}else if(ae.getSource()==edotPlotButton){
			
			openEdotPlot();
		
		}else if(ae.getSource()==animatorButton){
		
			createAnimatorDialog(frame);
			
		}else if(ae.getSource()==okAnimatorButton){
		
			openAnimator();
		
		}else if(ae.getSource()==intFluxButton){
		
			createIntFluxDialog(frame);
			
		}else if(ae.getSource()==okIntFluxButton){
		
			openIntFlux();
		
		}else if(ae.getSource()==weightPlotButton){
			
			openWeightPlotFrame();
			
		}else if(ae.getSource()==scalePlotButton){
			
			openScalePlot();
		
		}else if(ae.getSource()==finalAbundButton){
		
			openFinalAbund();
		
		}
	
	}

	public void setCurrentState(){descTextArea.setText(defaultDescString);}
	
	public void getCurrentState(){}
	
	public void createAnimatorDialog(Frame frame){

    	animatorDialog = new JDialog(frame, "Select Simulation Data Types", true);
    	animatorDialog.setSize(355, 158);
    	animatorDialog.getContentPane().setLayout(new GridBagLayout());
    	animatorDialog.setLocationRelativeTo(frame);
    	
    	JPanel boxPanel = new JPanel(new GridBagLayout());

		abundBox = new JCheckBox("Abundance", true);
		abundBox.setFont(Fonts.textFont);
		
		fluxBox = new JCheckBox("Reaction Flux", true);
		fluxBox.setFont(Fonts.textFont);
		fluxBox.addItemListener(this);
		
		sumFluxBox = new JCheckBox("Sum Reaction Fluxes?", true);
		sumFluxBox.setFont(Fonts.textFont);
		
		okAnimatorButton = new JButton("Submit");
		okAnimatorButton.setFont(Fonts.buttonFont);
		okAnimatorButton.addActionListener(this);
		
		JLabel label = new JLabel("Please select simulation data types for animation.");
		label.setFont(Fonts.textFont);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;

		animatorDialog.getContentPane().add(label, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		boxPanel.add(abundBox, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		boxPanel.add(fluxBox, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		boxPanel.add(sumFluxBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		boxPanel.add(okAnimatorButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		animatorDialog.getContentPane().add(boxPanel, gbc);
		
		gbc.gridwidth = 1;
		animatorDialog.setVisible(true);

    }

	public void createIntFluxDialog(Frame frame){
    	
    	intFluxDialog = new JDialog(frame, "Sum Reaction Flux?", true);
    	intFluxDialog.setSize(355, 158);
    	intFluxDialog.getContentPane().setLayout(new GridBagLayout());
    	intFluxDialog.setLocationRelativeTo(frame);
    	
		sumFluxBoxInt = new JCheckBox("Sum Reaction Fluxes before integrating?", true);
		sumFluxBoxInt.setFont(Fonts.textFont);
		
		okIntFluxButton = new JButton("Submit");
		okIntFluxButton.setFont(Fonts.buttonFont);
		okIntFluxButton.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.CENTER;
		intFluxDialog.getContentPane().add(sumFluxBoxInt, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		intFluxDialog.getContentPane().add(okIntFluxButton, gbc);
		
		gbc.gridwidth = 1;
		intFluxDialog.setVisible(true);

    }
    
}
