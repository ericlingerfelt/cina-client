package org.nucastrodata;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import org.nucastrodata.io.FileGetter;

public class HelpFrame extends JFrame{

	private String urlString;
	private JTextArea helpTextArea;

    public HelpFrame(String title, String id){
    	
    	Container c = getContentPane();
    	
        setSize(349, 400);
        setVisible(true); 
        setTitle(title);
        c.setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent we) {
		        setVisible(false);
		        dispose();
		    } 	
        });
        
        helpTextArea = new JTextArea();
      	helpTextArea.setEditable(false);
		helpTextArea.setLineWrap(true);
		helpTextArea.setWrapStyleWord(true);
		helpTextArea.setFont(Fonts.textFont);
		
      	JScrollPane sp = new JScrollPane(helpTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 100));
        
        helpTextArea.setCaretPosition(0);
		c.add(sp, BorderLayout.CENTER);
		
		setHelpString(id);
		
		
        c.validate();
    }
    
   	/**
	    * Sets the help string.
	    *
	    * @param id the new help string
	    */
	   private void setHelpString(String id){
   	
   		if(id.equals("RateViewer")){
   	
	   		final SwingWorker worker = new SwingWorker(){
			
				public Object construct(){
					

							String helpString = new String(FileGetter.getFileByName("/var/www/html/helpfiles/RateViewerHelp.txt"));
				
							Cina.rateViewerFrame.rateViewerHelpFrame.helpTextArea.setText(helpString);
				
					
		
					return new Object();
					
				}
				
				public void finished(){}
				
			};
			
			worker.start();
		
		}else if(id.equals("DataViewer")){
   	
	   		final SwingWorker worker = new SwingWorker(){
			
				public Object construct(){
					
				
							String helpString = new String(FileGetter.getFileByName("/var/www/html/helpfiles/DataViewerHelp.txt"));
				
							Cina.dataViewerFrame.dataViewerHelpFrame.helpTextArea.setText(helpString);

		
					return new Object();
					
				}
				
				public void finished(){}
				
			};
			
			worker.start();
		
		}else if(id.equals("ElementViz")){

	   		final SwingWorker worker = new SwingWorker(){
			
				public Object construct(){
					

							
							String helpString = new String(FileGetter.getFileByName("/var/www/html/helpfiles/ElementVizHelp.txt"));
				
							//Cina.elementVizFrame.elementVizHelpFrame.helpTextArea.setText(helpString);

		
					return new Object();
					
				}
				
				public void finished(){}
				
			};
			
			worker.start();
		
		}else if(id.equals("ElementVizColorSettings")){

	   		final SwingWorker worker = new SwingWorker(){
			
				public Object construct(){
					
						
							String helpString = new String(FileGetter.getFileByName("/var/www/html/helpfiles/ElementVizAnimatorColorSettingsHelp.txt"));
				
							Cina.elementVizFrame.elementVizAnimatorFrame.elementVizAnimatorColorSettingsFrame.elementVizAnimatorColorSettingsHelpFrame.helpTextArea.setText(helpString);

					return new Object();
					
				}
				
				public void finished(){}
				
			};
			
			worker.start();
			
		}else if(id.equals("ElementVizAnimatorExport")){

	   		final SwingWorker worker = new SwingWorker(){
			
				public Object construct(){
					

								
							String helpString = new String(FileGetter.getFileByName("/var/www/html/helpfiles/ElementVizAnimatorExportHelp.txt"));
				
							//Cina.elementVizFrame.elementVizAnimatorFrame.elementVizAnimatorExportFrame.elementVizAnimatorExportHelpFrame.helpTextArea.setText(helpString);
				

					return new Object();
					
				}
				
				public void finished(){}
				
			};
			
			worker.start();
			
		}else if(id.equals("RateManInvestRateView")){

	   		final SwingWorker worker = new SwingWorker(){
			
				public Object construct(){
					
							String helpString = new String(FileGetter.getFileByName("/var/www/html/helpfiles/RateManInvestRateViewHelp.txt"));
				
							Cina.rateManFrame.rateManInvestRateViewHelpFrame.helpTextArea.setText(helpString);

		
					return new Object();
					
				}
				
				public void finished(){}
				
			};
			
			worker.start();
			
		}else if(id.equals("RateLocator")){

	   		final SwingWorker worker = new SwingWorker(){
			
				public Object construct(){
				
							String helpString = new String(FileGetter.getFileByName("/var/www/html/helpfiles/RateManInvestRate3Help.txt"));
				
							Cina.rateManFrame.rateManInvestRate3HelpFrame.helpTextArea.setText(helpString);
				
		
					return new Object();
					
				}
				
				public void finished(){}
				
			};
			
			worker.start();
			
		}else if(id.equals("DataMassColorSettings")){

	   		final SwingWorker worker = new SwingWorker(){
			
				public Object construct(){
					
					
							String helpString = new String(FileGetter.getFileByName("helpfiles/DataMassChartColorSettingsHelp.txt"));
							Cina.dataMassFrame.dataMassChartColorSettingsFrame.dataMassChartColorSettingsHelpFrame.helpTextArea.setText(helpString);

					return new Object();
					
				}
				
				public void finished(){}
				
			};
			
			worker.start();
			
		}
   	
   	}
    
}

