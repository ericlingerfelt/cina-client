package org.nucastrodata.data.dataviewer;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataViewerDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;
import org.nucastrodata.data.dataviewer.DataViewerTableFramePrintable;


/**
 * The Class DataViewerTableFrame.
 */
public class DataViewerTableFrame extends JFrame implements WindowListener, ActionListener{

   
    /** The table text area. */
    JTextArea tableTextArea;
    
   
    
    /** The top panel. */
    JPanel buttonPanel, topPanel;
    
    /** The print button. */
    JButton saveButton, copyButton, printButton;
    
    /** The reaction string. */
    String reactionString;
    
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
    
    /** The ds. */
    private DataViewerDataStructure ds;
    
    /**
     * Instantiates a new data viewer table frame.
     *
     * @param ds the ds
     */
    public DataViewerTableFrame(DataViewerDataStructure ds){
    	
    	this.ds = ds;
    	
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

      
      	copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this); 
		
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
      	
      	gbc.gridx = 2;
        gbc.gridy = 0;
      	gbc.insets = new Insets(5, 0, 5, 0);
		//buttonPanel.add(printButton, gbc);
      	
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
   
   		if(Cina.dataViewerFrame.dataViewerPlotFrame.SFRadioButton.isSelected()){
	        	
			setTitle("S Factor vs. Energy Table of Points");
		
		}else if(Cina.dataViewerFrame.dataViewerPlotFrame.CSRadioButton.isSelected()){
		
			setTitle("Cross Section vs. Energy Table of Points");
		
		}
   
   		tableTextArea.setText("");

		int numberNucData = 0;
		
		String type = "";
		
		if(Cina.dataViewerFrame.dataViewerPlotFrame.SFRadioButton.isSelected()){
	        	
			type = "S(E)";
				
		}else if(Cina.dataViewerFrame.dataViewerPlotFrame.CSRadioButton.isSelected()){
		
			type = "CS(E)";
		
		}

		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    		
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals(type)){
    		
	    				if(Cina.dataViewerFrame.dataViewerPlotFrame.dataViewerNucDataListPanel.checkBoxArray[numberNucData].isSelected()){
	
							if(type.equals("CS(E)")){
	
								tableTextArea.append("Cross Section vs. Energy Data for: " 
											+ Cina.dataViewerFrame.dataViewerPlotFrame.dataViewerNucDataListPanel.checkBoxArray[numberNucData].getText() 
											+ "\n\n");   
											
		       					tableTextArea.append("Energy (keV)     " 
		       								+ "Cross Section (b)" 
		       								+ "\n\n");
	
							}else if(type.equals("S(E)")){
							
								tableTextArea.append("S Factor vs. Energy Data for: " 
											+ Cina.dataViewerFrame.dataViewerPlotFrame.dataViewerNucDataListPanel.checkBoxArray[numberNucData].getText() 
											+ "\n\n");   
											
		       					tableTextArea.append("Energy (keV)     " 
		       								+ "S Factor (keV-b)" 
		       								+ "\n\n");
							
							}
	
	    					for(int k=0; k<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNumberPoints(); k++){
								
								tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getXDataArray()[k]));
								
								tableTextArea.append("        ");
								
								tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getYDataArray()[k]) + "\n");
										
							}
							
							tableTextArea.append("\n");
	    				
	    				}
    		
    				numberNucData++;
    				
    				}
    			}	
    		}	
    	}

    	tableTextArea.setCaretPosition(0);
		
		
		
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    
    	String string = "";
    
    	if(Cina.dataViewerFrame.dataViewerPlotFrame.SFRadioButton.isSelected()){
	        	
			string += "S Factor vs. Energy Table of Points\n\n";
		
		}else if(Cina.dataViewerFrame.dataViewerPlotFrame.CSRadioButton.isSelected()){
		
			string += "Cross Section vs. Energy Table of Points\n\n";
		
		}
       
        string += tableTextArea.getText();
    
    	if(ae.getSource()==saveButton){
    	
			TextSaver.saveText(string, this);	
    	
    	}else if(ae.getSource()==copyButton){
    	
    		TextCopier.copyText(string);
    	
    	}else if(ae.getSource()==printButton){
    	
    		PrinterJob printerJob = PrinterJob.getPrinterJob();
    		printerJob.setPrintable(new DataViewerTableFramePrintable());
	    	if(printerJob.printDialog()){
			    try { printerJob.print(); 
			    }catch (Exception PrinterExeption) { 
			    PrinterExeption.printStackTrace();
			    }
			}
			
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


class DataViewerTableFramePrintable extends JTextArea implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex>=1){return NO_SUCH_PAGE;}

		Graphics2D g2 = (Graphics2D)Cina.dataViewerFrame.dataViewerPlotFrame.dataViewerTableFrame.tableTextArea.getGraphics();
		
		g2.translate(pf.getImageableX(), 
		                 pf.getImageableY());

		paint(g2);

        return PAGE_EXISTS;
		
	}

}

