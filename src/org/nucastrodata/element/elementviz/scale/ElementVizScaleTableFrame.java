package org.nucastrodata.element.elementviz.scale;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.awt.event.*;
import java.text.DecimalFormat;

import org.nucastrodata.Fonts;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;
import org.nucastrodata.element.elementviz.scale.ElementVizScalePlotControlsPanel;

/**
 * The Class ElementVizScaleTableFrame.
 */
public class ElementVizScaleTableFrame extends JFrame implements WindowListener, ActionListener{
	
    /** The table text area. */
    JTextArea tableTextArea;
    
    /** The top panel. */
    JPanel buttonPanel, topPanel;
    
    /** The copy button. */
    JButton saveButton, copyButton;
    
    /** The y. */
    double[] x, y;
    
    /** The units. */
    String units;
	
	/** The index. */
	int index;
    
    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The c. */
    Container c;    
    
    /** The sp. */
    JScrollPane sp;
	
	/** The parent. */
	private ElementVizScalePlotControlsPanel parent;
	
	private DecimalFormat df = new DecimalFormat("0.000E0");
	
    /**
     * Instantiates a new element viz scale table frame.
     *
     * @param ds the ds
     * @param parent the parent
     */
    public ElementVizScaleTableFrame(ElementVizScalePlotControlsPanel parent){
    	
    	this.parent = parent;
    	
    	c = getContentPane();
    	
        setSize(349, 400);
        setVisible(true);
          
        gbc = new GridBagConstraints();
        
        addWindowListener(this);

		topPanel = new JPanel(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;

      	buttonPanel = new JPanel(new GridBagLayout());
      	
      	//Create buttons///////////////////////////////////////////////BUTTONS////////////////
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
	
      	copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		 
      	//Create textArea/////////////////////////////////////////////TEXTAREA///////////////////
      	tableTextArea = new JTextArea("", 100, 50);
		tableTextArea.setFont(Fonts.textFontFixedWidth);
		tableTextArea.setEditable(false);
		
      	setTableText();
      	
      	tableTextArea.setCaretPosition(0);
      	
      	sp = new JScrollPane(tableTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 100));
      	
      	gbc.gridx = 0;
        gbc.gridy = 0;
      	gbc.insets = new Insets(5, 0, 5, 5);
      	buttonPanel.add(saveButton, gbc);
      	
      	gbc.gridx = 1;
        gbc.gridy = 0;
      	gbc.insets = new Insets(5, 0, 5, 0);
		buttonPanel.add(copyButton, gbc);
      	
      	gbc.insets = new Insets(0, 0, 0, 0);
		
		//c.add(topPanel, BorderLayout.NORTH);
		c.add(sp, BorderLayout.CENTER);
        c.add(buttonPanel, BorderLayout.SOUTH);
        
        
        
        validate();

    }

    /**
     * Sets the table text.
     */
    public void setTableText(){
   
    	tableTextArea.setText("");
    	
    	if(parent.mode==ElementVizScalePlotControlsPanel.Mode.ABUND){
   			setTitle("Final Abundance vs. Rate Scale Factor Table of Points");
   		}else if(parent.mode==ElementVizScalePlotControlsPanel.Mode.NORM){
   			setTitle("Normalized Final Abundance vs. Rate Scale Factor Table of Points");
   		}
   		
		if(parent.mode==ElementVizScalePlotControlsPanel.Mode.ABUND){
			
			for(int i=0; i<parent.x.length; i++){
				if(parent.listPanel.boxList.get(i).isSelected()){
					tableTextArea.append("Final Abundance vs. Rate Scale Factor for "
											+ parent.listPanel.boxList.get(i).getText()
											+ "\n"
											+ parent.setName 
											+ " (" 
											+ parent.getMap().values().iterator().next().getReactionRate() 
											+ ")"
											+ "\n\n");
					tableTextArea.append("Rate Scale Factor   Final Abundance\n\n");
					for(int j=0; j<parent.x[i].length; j++){
						if(parent.x[i][j]<=parent.getScaleMax()
								&& parent.x[i][j]>=parent.getScaleMin()
								&& parent.y[i][j]<=parent.getAbundMax()
								&& parent.y[i][j]>=parent.getAbundMin()){
							tableTextArea.append(String.format("%1$15f %2$15E", parent.x[i][j], parent.y[i][j]) + "\n");
						}
					}
					tableTextArea.append("\n\n");
				}
			}
			
		}else if(parent.mode==ElementVizScalePlotControlsPanel.Mode.NORM){

			for(int i=0; i<parent.x.length; i++){
				if(parent.listPanel.boxList.get(i).isSelected()){
					tableTextArea.append("Normalized Final Abundance vs. Rate Scale Factor at Rate Scale Factor = " + parent.scaleFactor + " for " 
											+ parent.listPanel.boxList.get(i).getText()
											+ "\n"				
											+ parent.setName 
											+ " (" 
											+ parent.getMap().values().iterator().next().getReactionRate()
											+ ": Norm @ Rate Scale Factor="
											+ parent.scaleFactor
											+ ")"
											+ "\n\n");
					tableTextArea.append("Rate Scale Factor    Normalized Final Abundance\n\n");
					for(int j=0; j<parent.x[i].length; j++){
						if(parent.x[i][j]<=parent.getScaleMax()
								&& parent.x[i][j]>=parent.getScaleMin()
								&& parent.y[i][j]<=parent.getAbundMax()
								&& parent.y[i][j]>=parent.getAbundMin()){
							tableTextArea.append(String.format("%1$15f %2$15E", parent.x[i][j], parent.ynorm[i][j]) + "\n");
						}
					}
					tableTextArea.append("\n\n");
				}
			}
   		}

    	tableTextArea.setCaretPosition(0);

	}
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	if(ae.getSource()==saveButton){
			TextSaver.saveText(tableTextArea.getText(), this);	
    	}else if(ae.getSource()==copyButton){
    		TextCopier.copyText(tableTextArea.getText());
    	}
    }
    
   
	
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing(WindowEvent we) {
            setVisible(false);
            dispose();
    } 
    
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
    
}


