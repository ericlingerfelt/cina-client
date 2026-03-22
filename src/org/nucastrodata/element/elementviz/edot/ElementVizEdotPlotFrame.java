package org.nucastrodata.element.elementviz.edot;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import java.awt.event.*;
import java.awt.print.*;
import org.nucastrodata.*;

public class ElementVizEdotPlotFrame extends JFrame implements ItemListener, ActionListener, WindowListener{
    
    private JPanel controlPanel;
    private JComboBox edotminComboBox, edotmaxComboBox;
    public ElementVizNucSimListPanel elementVizNucSimListPanel;
    public ElementVizEdotPlotPanel elementVizEdotPlotPanel;
    public ElementVizEdotTableFrame elementVizEdotTableFrame;
    private JButton printButton, saveButton, tableButton, applyButton;
    private JCheckBox majorXCheckBox, majorYCheckBox, minorXCheckBox, minorYCheckBox;
    private JTextField timemaxField, timeminField;
    public JCheckBox legendBox;
    private JLabel xmaxLabel, xminLabel, ymaxLabel, yminLabel;
    private int edotminComboBoxInit;
    private int edotmaxComboBoxInit;
    private Container c;
    private JScrollPane sp, sp1;
    private GridBagConstraints gbc;
    private ElementVizDataStructure ds;
    
    public ElementVizEdotPlotFrame(ElementVizDataStructure ds){
	
		this.ds = ds;
	
		edotminComboBoxInit = setInitialEdotminComboBoxInit();
		edotmaxComboBoxInit = setInitialEdotmaxComboBoxInit();
	
		c = getContentPane();
	
		setSize(834, 665);
		
		setVisible(true);
		
		addWindowListener(this);
		
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();

		createNucSimListPanel();
		setNucSimListPanel();
		createFormatPanel();
		setFormatPanelState();
		createResultsPlot();
		validate();
		
	}
	
    public void closeAllFrames(){
    	if(elementVizEdotTableFrame!=null){
			elementVizEdotTableFrame.setVisible(false);
			elementVizEdotTableFrame.dispose();
		}
    }
	public void setFormatPanelState(){
	
		setTitle("Nuclear Energy Generation Plotting Interface");
	
		edotminComboBoxInit = setInitialEdotminComboBoxInit();
		edotmaxComboBoxInit = setInitialEdotmaxComboBoxInit();

		edotminComboBox.removeItemListener(this);
		edotmaxComboBox.removeItemListener(this);
		
		edotminComboBox.setSelectedItem(String.valueOf(edotminComboBoxInit));
        edotmaxComboBox.setSelectedItem(String.valueOf(edotmaxComboBoxInit));
        
        edotminComboBox.addItemListener(this);
		edotmaxComboBox.addItemListener(this);
        
        timeminField.setText(String.valueOf(ds.getTimeminEdot()));
        timemaxField.setText(String.valueOf(ds.getTimemaxEdot()));
        
	}
	
    public void createNucSimListPanel(){
    
    	JPanel nucSimListPanel = new JPanel();
    	
        nucSimListPanel.setLayout(new GridBagLayout());
		
		elementVizNucSimListPanel = new ElementVizNucSimListPanel(ds);
	
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);  
        
        nucSimListPanel.add(elementVizNucSimListPanel, gbc);   
          
        sp = new JScrollPane(nucSimListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension(200, 400));
    
    }
    
    public void setNucSimListPanel(){
    	elementVizNucSimListPanel.initialize();
    }
    
    public void createFormatPanel(){  
               
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);   
		
		JPanel buttonPanel = new JPanel();
        
        buttonPanel.setLayout(new GridLayout(1, 5, 5, 5));
		
		/////BUTTONS/////////////////////////////////////////////////////BUTTONS////////////////////
        printButton = new JButton("Print");
        printButton.addActionListener(this);
        printButton.setFont(Fonts.buttonFont);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setFont(Fonts.buttonFont);

        tableButton = new JButton("Table of Points");
        tableButton.setFont(Fonts.buttonFont);
        tableButton.addActionListener(this);
        
		applyButton = new JButton("Apply Time Range");
        applyButton.setFont(Fonts.buttonFont);
        applyButton.addActionListener(this);

        buttonPanel.add(printButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(tableButton);

        legendBox = new JCheckBox("Show Legend?", false);
    	legendBox.setFont(Fonts.textFont);
    	legendBox.addActionListener(this);
        
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());

        //Create ComboBoxs///////////////////////////////////////////////CHOICES//////////
        edotminComboBox = new JComboBox();
        edotminComboBox.setFont(Fonts.textFont);
        for(int i=29; i>=-30; i--) {edotminComboBox.addItem(Integer.toString(i));}
        edotminComboBox.setSelectedItem(String.valueOf(edotminComboBoxInit));
		edotminComboBox.addItemListener(this);

        edotmaxComboBox = new JComboBox();
        edotmaxComboBox.setFont(Fonts.textFont);
        for(int i=30; i>=-29; i--) {edotmaxComboBox.addItem(Integer.toString(i));}
        edotmaxComboBox.setSelectedItem(String.valueOf(edotmaxComboBoxInit));
		edotmaxComboBox.addItemListener(this);
        
        //CREATE FIELDS/////////////////////////////////////////////////////////FIELDS/////////////////////
        timemaxField = new JTextField(10);
        timeminField = new JTextField(10);
        
        //LABELS///////////////////////////////////////////////////////////////LABELS/////////////////
        xmaxLabel = new JLabel("Max");
        xmaxLabel.setFont(Fonts.textFont);
        
        xminLabel = new JLabel("Time Min");
        xminLabel.setFont(Fonts.textFont);
        
        ymaxLabel = new JLabel("Max");
        ymaxLabel.setFont(Fonts.textFont);
        
        yminLabel = new JLabel("Energy Min");
        yminLabel.setFont(Fonts.textFont);
        
        JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
        
        //CHECKBOXES///////////////////////////////////////////////////////CHECKBOXES////////////////////
        majorXCheckBox = new JCheckBox("Major Gridlines", true);
        minorXCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorYCheckBox = new JCheckBox("Major Gridlines", true);
        minorYCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorXCheckBox.addItemListener(this);
        minorXCheckBox.addItemListener(this);
        majorYCheckBox.addItemListener(this);
        minorYCheckBox.addItemListener(this);

		majorXCheckBox.setFont(Fonts.textFont);
        minorXCheckBox.setFont(Fonts.textFont);
        majorYCheckBox.setFont(Fonts.textFont);
        minorYCheckBox.setFont(Fonts.textFont);

  		///////////////////////////////////////////////PUT IT ALL TOGETHER//////////////////////////////////////////////////////////////////
  		
  		gbc.insets = new Insets(3, 3, 3, 3);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 7;
  		controlPanel.add(plotControlsLabel, gbc);
  		
  		gbc.gridwidth = 1;
  		
  		gbc.gridx = 0;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(yminLabel, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(edotminComboBox, gbc);
  
  		gbc.gridx = 2;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(ymaxLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(edotmaxComboBox, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(majorYCheckBox, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorYCheckBox, gbc);
  		
  		gbc.gridx = 6;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(legendBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(xminLabel, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(timeminField, gbc);
  
  		gbc.gridx = 2;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(xmaxLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(timemaxField, gbc);
  		
  		gbc.gridx = 4;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(majorXCheckBox, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorXCheckBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 7;
  		controlPanel.add(buttonPanel, gbc);

		gbc.gridwidth = 1;

        c.add(controlPanel, BorderLayout.SOUTH);
             
    }
   
    public void createResultsPlot(){
     
    	elementVizEdotPlotPanel = new ElementVizEdotPlotPanel(edotminComboBoxInit 
																, edotmaxComboBoxInit
																, ds);
		
		elementVizEdotPlotPanel.setPreferredSize(elementVizEdotPlotPanel.getSize());
		
		elementVizEdotPlotPanel.revalidate();
		
		sp1 = new JScrollPane(elementVizEdotPlotPanel);
		
		JViewport vp = new JViewport();
		
		vp.setView(elementVizEdotPlotPanel);
		
		sp1.setBackground(Color.white);
		
		sp1.setViewport(vp);
		
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp);
       	
       	jsp.setDividerLocation(550);
       	
       	c.add(jsp, BorderLayout.CENTER);
		
		setVisible(true);
		
		elementVizEdotPlotPanel.setPreferredSize(elementVizEdotPlotPanel.getSize());
		
		elementVizEdotPlotPanel.revalidate();
    	
    }
    
    public int setInitialEdotminComboBoxInit(){
	
		int min = 0;

		min = ds.getEdotmin();
		
		if(min < -30){
			
			min = -30;
		
		}
		
		return min;
	
	}
	
	public int setInitialEdotmaxComboBoxInit(){
	
		int max = 0;
		
		max = ds.getEdotmax();
		
		if(max > 30){
			
			max = 30;
		
		}
		
		return max;
	
	}
    
    public void itemStateChanged(ItemEvent ie){
        
      	if(ie.getSource()==majorXCheckBox){
      		
            if(majorXCheckBox.isSelected()){
            	
                minorXCheckBox.setEnabled(true);
                
            }else{
            	
                minorXCheckBox.setSelected(false);
                minorXCheckBox.setEnabled(false);
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==majorYCheckBox){
        	
            if(majorYCheckBox.isSelected()){
            	
                minorYCheckBox.setEnabled(true);
                
            }else{

                minorYCheckBox.setSelected(false);
                minorYCheckBox.setEnabled(false);  
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==minorXCheckBox){
        	
            redrawPlot();
            
        }else if(ie.getSource()==minorYCheckBox){
        	
            redrawPlot();
               
        }else if(ie.getSource()==edotminComboBox || ie.getSource()==edotmaxComboBox){
	
       		redrawPlot();

        }

    }

    public double getEdotmin(){return Double.valueOf((String)edotminComboBox.getSelectedItem()).doubleValue();} 
	public double getEdotmax(){return Double.valueOf((String)edotmaxComboBox.getSelectedItem()).doubleValue();}
	public double getTimemin(){return Double.valueOf(timeminField.getText()).doubleValue();} 
	public double getTimemax(){return Double.valueOf(timemaxField.getText()).doubleValue();}
	public boolean getMinorX(){return minorXCheckBox.isSelected();} 
	public boolean getMajorX(){return majorXCheckBox.isSelected();} 
	public boolean getMinorY(){return minorYCheckBox.isSelected();}
	public boolean getMajorY(){return majorYCheckBox.isSelected();} 
    
    public void redrawPlot(){

   		try{
   		
   			if(Integer.valueOf(edotminComboBox.getSelectedItem().toString()).intValue()
   				>= Integer.valueOf(edotmaxComboBox.getSelectedItem().toString()).intValue()){
   		
	   			String string = "Nuclear energy generation minimum must be less than nuclear energy generation maximum.";
	   			
	   			Dialogs.createExceptionDialog(string, this);
   			
   			}else if(Double.valueOf(timeminField.getText()).doubleValue() >= Double.valueOf(timemaxField.getText()).doubleValue()){
   			
   				String string = "Time minimum must be less than time maximum.";
   				
   				Dialogs.createExceptionDialog(string, this);
   				
   			}else{
   			
   				elementVizEdotPlotPanel.setPreferredSize(elementVizEdotPlotPanel.getSize());
	
   				elementVizEdotPlotPanel.setPlotState();
		   		
   				elementVizEdotPlotPanel.repaint();
   			
   			}
   			
   		}catch(NumberFormatException nfe){
   			
   			String string = "Values for time minimum and maximum must be numeric values.";
   	
   			Dialogs.createExceptionDialog(string, this);
   	
   		}

    }
    
    public boolean noneChecked(){
    
    	boolean noneChecked = true;
    	
    	for(int i=0; i<elementVizNucSimListPanel.checkBoxArray.length; i++){
    	
    		if(elementVizNucSimListPanel.checkBoxArray[i].isSelected()){
    		
    			noneChecked = false;
    		
    		}
    	
    	}
    	
    	return noneChecked;
    
    }

    public void actionPerformed(ActionEvent ae){
    	
       if(ae.getSource()==tableButton){
       	
       		if(!noneChecked()){
       	
	       		if(elementVizEdotTableFrame==null){
	       	
	       			elementVizEdotTableFrame = new ElementVizEdotTableFrame(ds);
	       		
		       	}else{
		       	
		       		elementVizEdotTableFrame.setTableText();
		       		elementVizEdotTableFrame.setVisible(true);
		       	
		       	}
		       	
		 	}else{
	       	
	       		String string = "Please select simulations for the Table of Points from the checkbox list."; 
	       		
	       		Dialogs.createExceptionDialog(string, this);
	       		
	       	}
	       	
	    }else if(ae.getSource()==applyButton){
	       	
	       	redrawPlot();
	       	
       	}else if(ae.getSource()==printButton){
       	
       		PlotPrinter.print(new ElementVizEdotPlotFramePrintable(), this);
       	
       	}else if(ae.getSource()==saveButton){
       	
       		PlotSaver.savePlot(elementVizEdotPlotPanel, this);
       		
       	}else if(ae.getSource()==legendBox){
       		redrawPlot();
       	}
    }
    public void windowClosing(WindowEvent we){
    	
        setVisible(false);
        dispose();
   
    }
    
    public void windowActivated(WindowEvent we){}
    public void windowClosed(WindowEvent we){}
    public void windowDeactivated(WindowEvent we){}
    public void windowDeiconified(WindowEvent we){}
    public void windowIconified(WindowEvent we){}
    public void windowOpened(WindowEvent we){}

}  

class ElementVizEdotPlotFramePrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.elementVizFrame.elementVizEdotPlotFrame.elementVizEdotPlotPanel.paintMe(g);

        return PAGE_EXISTS;
		
	}

}