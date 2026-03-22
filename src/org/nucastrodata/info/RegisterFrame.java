package org.nucastrodata.info;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RegisterDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


/**
 * The Class RegisterFrame.
 */
public class RegisterFrame extends JFrame implements ActionListener
																, ItemListener{
		
	/** The other radio button. */
	private JRadioButton nucRadioButton, otherRadioButton;
	
	/** The submit button. */
	private JButton submitButton;
	
	/** The add info text area. */
	private JTextArea addressTextArea, hearTextArea, addInfoTextArea;
	
	/** The other field. */
	private JTextField firstNameField, lastNameField, emailField
												, institutionField, otherField;
    
    /** The register dialog. */
    private JDialog registerDialog;
    
    /** The ds. */
    private RegisterDataStructure ds;
    
    /**
     * Instantiates a new register frame.
     *
     * @param ds the ds
     */
    public RegisterFrame(RegisterDataStructure ds){
    	
    	this.ds = ds;
    	
    	Container c = getContentPane();
        setSize(771, 593);
        setVisible(true);
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent we) {
	            setVisible(false);
	            dispose();
		    } 	
        });
		
		firstNameField = new JTextField(40);
		lastNameField = new JTextField(40);
		emailField = new JTextField(40);
		institutionField = new JTextField(40);
		otherField = new JTextField(40);
		otherField.setEditable(false);
		
		addressTextArea = new JTextArea("", 40, 40);
		addressTextArea.setLineWrap(true);
		addressTextArea.setWrapStyleWord(true);
		addressTextArea.setFont(Fonts.textFont);
		JScrollPane sp = new JScrollPane(addressTextArea
									, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
									, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(444, 50));

		hearTextArea = new JTextArea("", 40, 40);
		hearTextArea.setLineWrap(true);
		hearTextArea.setWrapStyleWord(true);
		hearTextArea.setFont(Fonts.textFont);
		JScrollPane sp2 = new JScrollPane(hearTextArea
									, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
									, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp2.setPreferredSize(new Dimension(444, 50));
		
		addInfoTextArea = new JTextArea("", 40, 40);
		addInfoTextArea.setLineWrap(true);
		addInfoTextArea.setWrapStyleWord(true);
		addInfoTextArea.setFont(Fonts.textFont);
		JScrollPane sp3 = new JScrollPane(addInfoTextArea
									, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
									, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp3.setPreferredSize(new Dimension(444, 50));

		JLabel noticeLabel = new JLabel("<html>Notice to Users: "
							+ "<p>Use of this system constitutes consent to "
							+ "security monitoring and testing. "
							+ "<p>All activity is logged with your host name "
							+ "and IP address.</html>");
		noticeLabel.setFont(Fonts.textFont);
		
		JLabel responseLabel = new JLabel("<html>The responses to these "
								+ "questions will be archived on our system."
								+ "<p>Send all questions to "
								+ "coordinator@nucastrodata.org</html>");
		responseLabel.setFont(Fonts.textFont);

		String[] stringArray = {"First Name: "
			, "Last Name: "
			, "Email Address: "
			, "Institution: "
			, "Mailing Address: "
			, "Description of research requiring full access: "
			, "<html>Where did you<p>hear of this suite?: </html>"
			, "<html>Additional Information"
			+ "<p>(supervisor/research mentor): </html>"};	

		JLabel[] labelArray = new JLabel[8];

		for(int i=0; i<8; i++){
			labelArray[i] = new JLabel(stringArray[i]);
			labelArray[i].setFont(Fonts.textFont);
		}        
		
		submitButton = new JButton("Submit Registration");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);

		nucRadioButton = new JRadioButton("nuclear astrophysics research"
																		, true);
		nucRadioButton.addItemListener(this);
		nucRadioButton.setFont(Fonts.textFont);
		
		otherRadioButton = new JRadioButton("other: ", false);
		otherRadioButton.addItemListener(this);
		otherRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(nucRadioButton);
		buttonGroup.add(otherRadioButton);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		c.add(labelArray[0], gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		c.add(firstNameField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		c.add(labelArray[1], gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		c.add(lastNameField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		c.add(labelArray[2], gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		c.add(emailField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		c.add(labelArray[3], gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		c.add(institutionField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		c.add(labelArray[4], gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		c.add(sp, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.WEST;
		c.add(labelArray[6], gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.CENTER;
		c.add(sp2, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.WEST;
		c.add(labelArray[7], gbc);
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.CENTER;
		c.add(sp3, gbc);
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2; 
		gbc.anchor = GridBagConstraints.WEST;
		c.add(labelArray[5], gbc);
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 1; 
		gbc.anchor = GridBagConstraints.WEST;
		c.add(nucRadioButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.anchor = GridBagConstraints.WEST;
		c.add(otherRadioButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.anchor = GridBagConstraints.CENTER;
		c.add(otherField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 2;
		c.add(noticeLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 2;
		c.add(submitButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 12;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 2;
		c.add(responseLabel, gbc);

		
        c.validate();
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
    	if(nucRadioButton.isSelected()){
    		otherField.setEditable(false);
    	}else if(otherRadioButton.isSelected()){
    		otherField.setEditable(true);
    	}
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
   	public void actionPerformed(ActionEvent ae){
   		if(ae.getSource()==submitButton){
   			if(firstNameField.getText().equals("")
   				|| lastNameField.getText().equals("")
   				|| emailField.getText().equals("")
   				|| institutionField.getText().equals("")
   				|| addressTextArea.getText().equals("")
   				|| (otherRadioButton.isSelected() 
   					&& otherField.getText().equals(""))
   				|| hearTextArea.getText().equals("")
   				|| addInfoTextArea.getText().equals("")){

   				String string = "One or more registration fields are empty. "
   									+ "Please fill in all fields.";
	   			Dialogs.createExceptionDialog(string, this);			

   			}else{
   				getCurrentState(ds);
	   			if(Cina.cinaCGIComm.doCGICall(
										"REGISTER", this)){
	   				String string = "Your information has been sent to "
	   								+ "nucastrodata.org. You will be emailed a "
	   								+ "username and password usually within 24 "
	   								+ "hours. Thank you.";
	   				createRegisterDialog(string, this);
	   			}
   			}
   		}
   	}
    
    /**
     * Gets the current state.
     *
     * @param ds the ds
     * @return the current state
     */
    private void getCurrentState(RegisterDataStructure ds){
    	ds.setFirstName(firstNameField.getText());
    	ds.setLastName(lastNameField.getText());
    	ds.setEmailString(emailField.getText());
    	ds.setInstitutionString(institutionField.getText());
    	ds.setAddressString(addressTextArea.getText());
    	ds.setHearOfString(hearTextArea.getText());
    	ds.setAddInfoString(addInfoTextArea.getText());
    	
    	if(nucRadioButton.isSelected()){
    		ds.setResearchString("nuclear astrophysics research");	
    	}else if(otherRadioButton.isSelected()){
    		ds.setResearchString(otherField.getText());
    	}	
    }
    
    /**
     * Creates the register dialog.
     *
     * @param string the string
     * @param frame the frame
     */
    private void createRegisterDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	registerDialog = new JDialog(frame, "Registration Successful!", true);
    	registerDialog.setSize(350, 210);
    	registerDialog.getContentPane().setLayout(new GridBagLayout());
		registerDialog.setLocationRelativeTo(frame);
		
		JTextArea registerTextArea = new JTextArea("", 5, 30);
		registerTextArea.setLineWrap(true);
		registerTextArea.setWrapStyleWord(true);
		registerTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(registerTextArea
									, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
									, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		registerTextArea.setText(string);
		registerTextArea.setCaretPosition(0);
		
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				registerDialog.setVisible(false);
	   			registerDialog.dispose();
	   			setVisible(false);
	   			dispose();
			}
		
		});
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		registerDialog.getContentPane().add(sp, gbc1);
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.insets = new Insets(5, 3, 0, 3);
		registerDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(registerDialog);
		registerDialog.setVisible(true);
	}
}

