package org.nucastrodata.rate.rateviewer;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateViewerDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import java.awt.event.*;
import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class RateViewerInfoFrame.
 */
public class RateViewerInfoFrame extends JFrame implements WindowListener
															, ActionListener
															, ItemListener{
				
	/** The copy button. */
	JButton saveButton, copyButton;
	
	/** The valid temp range box. */
	JCheckBox parametersBox
				, paramQualityBox
				, biblioCodeBox
				, QValueBox
				, reactionNotesBox
				, creationDateBox
				, refCitationBox
				, validTempRangeBox;
	
	/** The title label. */
	JLabel titleLabel;
	
	/** The text area string. */
	String textAreaString;
	
	/** The output text area. */
	JTextArea outputTextArea;
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The cb panel. */
	JPanel mainPanel, buttonPanel, cbPanel;
    
    /** The c. */
    Container c;  
    
    /** The sp. */
    JScrollPane sp;
    
    /** The param string array. */
    String[] paramStringArray = {"a(1) = ", "a(2) = ", "a(3) = ", "a(4) = ", "a(5) = ", "a(6) = ", "a(7) = "
									, "a(8) = ", "a(9) = ", "a(10)= ", "a(11)= ", "a(12)= ", "a(13)= ", "a(14)= "
									, "a(15)= ", "a(16)= ", "a(17)= ", "a(18)= ", "a(19)= ", "a(20)= ", "a(21)= "
									, "a(22)= ", "a(23)= ", "a(24)= ", "a(25)= ", "a(26)= ", "a(27)= ", "a(28)= "
									, "a(29)= ", "a(30)= ", "a(31)= ", "a(32)= ", "a(33)= ", "a(34)= ", "a(35)= "
									, "a(36)= ", "a(37)= ", "a(38)= ", "a(39)= ", "a(40)= ", "a(41)= ", "a(42)= "
									, "a(43)= ", "a(44)= ", "a(45)= ", "a(46)= ", "a(47)= ", "a(48)= ", "a(49)= "};
									
	/** The ds. */
	private RateViewerDataStructure ds;
	
    /**
     * Instantiates a new rate viewer info frame.
     *
     * @param ds the ds
     */
	public RateViewerInfoFrame(RateViewerDataStructure ds){
		
		this.ds = ds;
		
		c = getContentPane();
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		setSize(700, 500);
		setVisible(true);
		setTitle("Reaction Rate Information");
		addWindowListener(this);
		
		titleLabel = new JLabel("<html>Select Reaction Rate<p>Information for Report: <html>");
		titleLabel.setFont(Fonts.titleFont);
		
		parametersBox = new JCheckBox("Parameters", false);
		parametersBox.addItemListener(this);
		parametersBox.setFont(Fonts.textFont);
		
		paramQualityBox = new JCheckBox("<html>Parameterization<p>Quality</html>", false);
		paramQualityBox.addItemListener(this);
		paramQualityBox.setFont(Fonts.textFont);
		
		biblioCodeBox = new JCheckBox("Biblio Code", false);
		biblioCodeBox.addItemListener(this);
		biblioCodeBox.setFont(Fonts.textFont);
		
		QValueBox = new JCheckBox("Q-Value", false);
		QValueBox.addItemListener(this);
		QValueBox.setFont(Fonts.textFont);
		
		reactionNotesBox = new JCheckBox("Notes", false);
		reactionNotesBox.addItemListener(this);
		reactionNotesBox.setFont(Fonts.textFont);
		
		creationDateBox = new JCheckBox("Creation Date", false);
		creationDateBox.addItemListener(this);
		creationDateBox.setFont(Fonts.textFont);
		
		refCitationBox = new JCheckBox("Reference Citation", false);
		refCitationBox.addItemListener(this);
		refCitationBox.setFont(Fonts.textFont);
		
		validTempRangeBox = new JCheckBox("Valid Temp. Range (T9)", false);
		validTempRangeBox.addItemListener(this);
		validTempRangeBox.setFont(Fonts.textFont);
		
		outputTextArea = new JTextArea("");
		outputTextArea.setFont(Fonts.textFontFixedWidth);
		outputTextArea.setEditable(false);
		
		sp = new JScrollPane(outputTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(275, 300));

		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		
		mainPanel = new JPanel(new GridBagLayout());
		buttonPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(saveButton, gbc);
		gbc.gridx = 1;
		buttonPanel.add(copyButton, gbc);
		
		cbPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 10, 0);
		cbPanel.add(titleLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		cbPanel.add(parametersBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		cbPanel.add(paramQualityBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		cbPanel.add(biblioCodeBox, gbc);	
		gbc.gridx = 0;
		gbc.gridy = 4;
		cbPanel.add(QValueBox, gbc);		
		gbc.gridx = 0;
		gbc.gridy = 5;
		cbPanel.add(reactionNotesBox, gbc);		
		gbc.gridx = 0;
		gbc.gridy = 6;
		cbPanel.add(creationDateBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 7;
		cbPanel.add(refCitationBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 8;
		cbPanel.add(validTempRangeBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		c.add(cbPanel, BorderLayout.WEST);	
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 20, 0, 0);
		c.add(sp, BorderLayout.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 0, 0, 0);
		c.add(buttonPanel, BorderLayout.SOUTH);
		
		
		validate();
	}

	/**
	 * Refresh data.
	 */
	public void refreshData(){
	
		setTitle("Reaction Rate Information");
		outputTextArea.setText("");
		textAreaString = "";
	
		int length = ds.getNumberTotalRates()
						+ ds.getNumberCompRates();
		int numberRates = 0;
		
		for(int i=0; i<ds.getNumberLibraryStructures(); i++){
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
    				boolean flag = false;
    				if(Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.checkBoxArray[numberRates].isSelected()){
						textAreaString = textAreaString + getReactionStringText(ds.getLibraryStructureArray()[i], ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n\n";					
						if(parametersBox.isSelected()){textAreaString = textAreaString + getParametersText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]);}			   				
    					if(paramQualityBox.isSelected()){textAreaString = textAreaString + getParamQualityText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
						if(biblioCodeBox.isSelected()){textAreaString = textAreaString + getBiblioCodeText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
						if(QValueBox.isSelected()){textAreaString = textAreaString + getQValueText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
						if(reactionNotesBox.isSelected()){textAreaString = textAreaString + getReactionNotesText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
    					if(creationDateBox.isSelected()){textAreaString = textAreaString + getCreationDateText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
    					if(refCitationBox.isSelected()){textAreaString = textAreaString + getRefCitationText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
						if(validTempRangeBox.isSelected()){textAreaString = textAreaString + getValidTempRangeText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
    					textAreaString = textAreaString + "\n" 
								+ "______________________________________________________________________________"
								+ "\n";
    					flag = true;
    				}
    		
    				numberRates++;
    		
    				if(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length>1){
	    				for(int k=0; k<ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length; k++){
							if(Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.checkBoxArray[numberRates].isSelected()
									&& !flag){
								textAreaString = textAreaString + getReactionStringText(ds.getLibraryStructureArray()[i], ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n\n";
								if(parametersBox.isSelected()){textAreaString = textAreaString + getParametersText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]);}			
		    					if(paramQualityBox.isSelected()){textAreaString = textAreaString + getParamQualityText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
								if(biblioCodeBox.isSelected()){textAreaString = textAreaString + getBiblioCodeText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
								if(QValueBox.isSelected()){textAreaString = textAreaString + getQValueText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
								if(reactionNotesBox.isSelected()){textAreaString = textAreaString + getReactionNotesText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
		    					if(creationDateBox.isSelected()){textAreaString = textAreaString + getCreationDateText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
		    					if(refCitationBox.isSelected()){textAreaString = textAreaString + getRefCitationText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
								if(validTempRangeBox.isSelected()){textAreaString = textAreaString + getValidTempRangeText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j]) + "\n";}
		    					textAreaString = textAreaString + "\n" 
								+ "______________________________________________________________________________"
								+ "\n";
		    					flag = true;
		    				}
		    				
		    				numberRates++;
		    				
						}	
					}
    			}	
    		}	
    	}
	
		if(!parametersBox.isSelected()
			&& !biblioCodeBox.isSelected()
			&& !QValueBox.isSelected()
			&& !reactionNotesBox.isSelected()
			&& !paramQualityBox.isSelected()
			&& !creationDateBox.isSelected()
			&& !refCitationBox.isSelected()
			&& !validTempRangeBox.isSelected()){
				textAreaString = "";
		}
	
		outputTextArea.setText(textAreaString);
		outputTextArea.setCaretPosition(0);
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){refreshData();}

	/**
	 * Gets the reaction string text.
	 *
	 * @param applds the applds
	 * @param apprds the apprds
	 * @return the reaction string text
	 */
	public String getReactionStringText(LibraryDataStructure applds, RateDataStructure apprds){
		String string = "";
	
		if(apprds.getDecay().equals("")){
			string = "Reaction Rate Information for "
								+ apprds.getReactionString()
								+ " ("
								+ applds.getLibName()
								+ ")";	
		}else{
			string = "Reaction Rate Information for "
								+ apprds.getReactionString()
								+ " ["
								+ apprds.getDecay()
								+ "]"
								+ " ("
								+ applds.getLibName()
								+ ")";
		}
		
		return string;
	}

	/**
	 * Gets the parameters text.
	 *
	 * @param apprds the apprds
	 * @return the parameters text
	 */
	public String getParametersText(RateDataStructure apprds){
	
		String string = "";

		for(int i=0; i<apprds.getResonantInfo().length; i++){
			string = string + apprds.getResonantInfo()[i] + "\n\n";
			for(int j=0; j<7; j++){
				string = string 
							+ paramStringArray[i*7 + j]
							+ NumberFormats.getFormattedParameter(apprds.getParameterCompArray()[i][j])
							+ "\n"; 
			}
			string = string + "\n";
		}
		
		return string;
	}
	
	/**
	 * Gets the param quality text.
	 *
	 * @param apprds the apprds
	 * @return the param quality text
	 */
	public String getParamQualityText(RateDataStructure apprds){
	
		String string = "";

		if(apprds.getMaxPercentDiff()==0){
			string = "Max. of ((Parameterized Rate - Numerical Rate)/Numerical Rate) * 100: Not Entered \n";
		}else{
			string = "Max. of ((Parameterized Rate - Numerical Rate)/Numerical Rate) * 100: " + apprds.getMaxPercentDiff() + "\n";
		}
		
		if(apprds.getChisquared()==0){
			string = string + "Chisquared of Parameterization: Not Entered";
		}else{
			string = string + "Chisquared of Parameterization: " + String.valueOf(apprds.getChisquared());
		}
		
		return string;
	}
	
	/**
	 * Gets the biblio code text.
	 *
	 * @param apprds the apprds
	 * @return the biblio code text
	 */
	public String getBiblioCodeText(RateDataStructure apprds){
	
		String string = "";
		
		if(apprds.getBiblioString().equals("")){
			string = "Biblio Code: Not Entered";
		}else{
			string = "Biblio Code: " + apprds.getBiblioString();
		}
		
		return string;
	}
	
	/**
	 * Gets the q value text.
	 *
	 * @param apprds the apprds
	 * @return the q value text
	 */
	public String getQValueText(RateDataStructure apprds){
	
		String string = "";
		
		if(String.valueOf(apprds.getQValue()).equals("") 
			|| String.valueOf(apprds.getQValue()).equals("0.0")){
				string = "Q-Value (MeV): Not Entered";
		}else{
			string = "Q-Value (MeV): " + String.valueOf(apprds.getQValue());
		}
		
		return string;	
	}
	
	/**
	 * Gets the reaction notes text.
	 *
	 * @param apprds the apprds
	 * @return the reaction notes text
	 */
	public String getReactionNotesText(RateDataStructure apprds){
	
		String string = "";
		
		if(apprds.getReactionNotes().equals("")){
			string = "Reaction Notes: Not Entered";
		}else{
			string = "Reaction Notes: " + apprds.getReactionNotes();
		}
		
		return string;
	}

	/**
	 * Gets the creation date text.
	 *
	 * @param apprds the apprds
	 * @return the creation date text
	 */
	public String getCreationDateText(RateDataStructure apprds){
	
		String string = "";
		
		if(String.valueOf(apprds.getCreationDate()).equals("")){
			string = "Creation Date: Not Entered";
		}else{
			string = "Creation Date: " + String.valueOf(apprds.getCreationDate());
		}
		
		return string;
	}
	
	/**
	 * Gets the ref citation text.
	 *
	 * @param apprds the apprds
	 * @return the ref citation text
	 */
	public String getRefCitationText(RateDataStructure apprds){
	
		String string = "";
		
		if(apprds.getRefCitation().equals("")){
			string = "Reference Citation: Not Entered";
		}else{
			string = "Reference Citation: " + String.valueOf(apprds.getRefCitation());
		}
		
		return string;
	}

	/**
	 * Gets the valid temp range text.
	 *
	 * @param apprds the apprds
	 * @return the valid temp range text
	 */
	public String getValidTempRangeText(RateDataStructure apprds){
	
		String string = "";
		
		if(apprds.getValidTempRange().equals("") 
			|| apprds.getValidTempRange().equals("0,0")){
				string = "Valid Temp. Range: Not Entered";
		}else{
			string = "Valid Temp. Range: " + String.valueOf(apprds.getValidTempRange());
		}
		
		return string;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==saveButton){
			TextSaver.saveText(outputTextArea.getText(), this);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(outputTextArea.getText());
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