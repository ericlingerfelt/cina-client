package org.nucastrodata.element.elementviz.edot;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import java.awt.event.*;
import org.nucastrodata.*;

public class ElementVizEdotTableFrame extends JFrame implements WindowListener, ActionListener{
    
    private JTextArea tableTextArea;
    private JPanel buttonPanel;
    private JButton saveButton, copyButton;
    private GridBagConstraints gbc;   
    private  Container c;    
    private JScrollPane sp;
	private ElementVizDataStructure ds;
	
    public ElementVizEdotTableFrame(ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
    	c = getContentPane();
    	
        setSize(349, 400);
        setVisible(true);
          
        setTitle("Nuclear Energy Generation vs. Time Table of Points");
        
        gbc = new GridBagConstraints();
        
        addWindowListener(this);

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

    public void setTableText(){
   
   		setTitle("Nuclear Energy Generation vs. Time Table of Points");
   		
   		tableTextArea.setText("");

		for(int i=0; i<ds.getNucSimDataStructureArray().length; i++){
		
			if(Cina.elementVizFrame.elementVizEdotPlotFrame.elementVizNucSimListPanel.checkBoxArray[i].isSelected()){

				tableTextArea.append("Nuclear Energy Generation vs. Time Data for: " 
										+ Cina.elementVizFrame.elementVizEdotPlotFrame.elementVizNucSimListPanel.checkBoxArray[i].getText() 
										+ "\n\n");
				
				tableTextArea.append("Time(sec)   Nuclear Energy Generation(Erg/Gm*Sec)\n\n");
			
				for(int j=0; j<ds.getTimeDataArrayEdot()[i].length; j++){
				
					if(ds.getTimeDataArrayEdot()[i][j]<=Cina.elementVizFrame.elementVizEdotPlotFrame.getTimemax() 
							&& ds.getTimeDataArrayEdot()[i][j]>=Cina.elementVizFrame.elementVizEdotPlotFrame.getTimemin()){
				
						int timeSpaces = getTimeSpaces(NumberFormats.getFormattedTime(ds.getTimeDataArrayEdot()[i][j]));
				
						for(int k=0; k<timeSpaces; k++){
				
							tableTextArea.append(" ");
				
						}		
				
						tableTextArea.append(NumberFormats.getFormattedTime(ds.getTimeDataArrayEdot()[i][j])
												+ "     "
												+ NumberFormats.getFormattedValueLong(ds.getEdotDataArray()[i][j])
												+ "\n");
											
					}
				
				}
				
				tableTextArea.append("\n\n");
				
			}
   		
   		}

    	tableTextArea.setCaretPosition(0);
		
    }
    
    public int getTimeSpaces(String string){

		int numSpaces = 0;	
		
		int decimalIndex = string.indexOf(".");
		
		numSpaces = 5 - decimalIndex;
		
		return numSpaces;
	
	}

    public void actionPerformed(ActionEvent ae){
    	
    	String string = "";
    	
   		string += "Nuclear Energy Generation vs. Time Table of Points\n\n";

        string += tableTextArea.getText();
    
    	if(ae.getSource()==saveButton){
    	
			TextSaver.saveText(string, this);	
    	
    	}else if(ae.getSource()==copyButton){
    	
    		TextCopier.copyText(string);
    	
    	}
   
    }
    
    public void windowClosing(WindowEvent we) {
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

