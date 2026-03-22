package org.nucastrodata.element.elementviz.sum;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.awt.event.*;
import java.text.*;
import java.util.Iterator;
import java.util.TreeMap;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class ElementVizSumTableFrame.
 */
public class ElementVizSumTableFrame extends JFrame implements WindowListener, ActionListener{
    
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
	
    ElementVizSumPlotFrame parent;
    
    String typeName;
    
    /**
     * Instantiates a new element viz Sum table frame.
     *
     * @param ds the ds
     */
    public ElementVizSumTableFrame(ElementVizSumPlotFrame parent){
    	
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
  
    	String title = "";
    	
    	if(parent.elementVizNucSimListPanel.abundButton.isSelected()){
    		title += "Final Abundance vs. ";
    	}else{
    		title += "Fractional Abundance Difference vs. ";
    	}
    	
    	String xType = parent.getXType();

   		typeName = "";
   		if(xType=="Z"){
   			typeName = "Atomic Number";
   		}else if(xType=="N"){
   			typeName = "Neutron Number";
   		}else if(xType=="A"){
   			typeName = "Mass Number";
   		}
    	
   		title += typeName;
   		
    	setTitle(title);
    	
   		tableTextArea.setText("");
   		
   		if(parent.elementVizNucSimListPanel.abundButton.isSelected()){
   		
			for(JCheckBox box: parent.elementVizNucSimListPanel.boxList){
			
				if(box.isSelected()){
	
					String name = box.getText();
					
					tableTextArea.append("Final Abundance vs. " + typeName + " for: " 
											+ name
											+ "\n\n");
				
					TreeMap<Integer, Double> map = parent.getFinalAbundMap().get(name).get(xType);
					Iterator<Integer> itr = map.keySet().iterator();
					while(itr.hasNext()){
						int x = itr.next();
						double abund = map.get(x);
						tableTextArea.append(String.format("%1$d %2$6.7E", x, abund) + "\n");
					}
					
					tableTextArea.append("\n\n");
					
				}
	
			}
		
   		}else{
   			
   			String name = parent.getFracDiffMap().keySet().iterator().next();
   			
   			tableTextArea.append("Fractional Abundance Difference vs. " + typeName + " for: " 
					+ name
					+ "\n\n");
			
			TreeMap<Integer, Double> map = parent.getFracDiffMap().get(name).get(xType);
			Iterator<Integer> itr = map.keySet().iterator();
			while(itr.hasNext()){
				int x = itr.next();
				double diff = map.get(x);
				tableTextArea.append(String.format("%1$d %2$6.7E", x, diff) + "\n");
			}
   			
   		}

    	tableTextArea.setCaretPosition(0);
		
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	
    	String string = "";
   		
        string += tableTextArea.getText();
    
    	if(ae.getSource()==saveButton){
    	
			TextSaver.saveText(string, this);	
    	
    	}else if(ae.getSource()==copyButton){
    	
    		TextCopier.copyText(string);
    	
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

