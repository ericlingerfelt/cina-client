package org.nucastrodata.element.thermoman;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import org.nucastrodata.Fonts;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;

public class ThermoManDataFileFrame extends JFrame implements ActionListener{
	
    private JTextArea fileTextArea;
    private JButton saveButton, copyButton;
    
    public ThermoManDataFileFrame(){
    	
    	Container c = getContentPane();
        c.setLayout(new BorderLayout());
        setSize(500, 500);
        GridBagConstraints gbc = new GridBagConstraints();
        addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {
	            setVisible(false);
	            dispose();
    		} 
		
		});

      	JPanel buttonPanel = new JPanel(new GridBagLayout());
      	
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);

      	copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
      
      	fileTextArea = new JTextArea("");
      	fileTextArea.setEditable(false);
		fileTextArea.setFont(Fonts.textFontFixedWidth);
		fileTextArea.setWrapStyleWord(true);
		fileTextArea.setLineWrap(true);

      	JScrollPane sp = new JScrollPane(fileTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 100));
        
        fileTextArea.setCaretPosition(0);
      	gbc.gridx = 0;
        gbc.gridy = 0;
      	gbc.insets = new Insets(5, 0, 5, 5);
      	buttonPanel.add(saveButton, gbc);
      	gbc.gridx = 1;
        gbc.gridy = 0;
      	gbc.insets = new Insets(5, 0, 5, 0);
		buttonPanel.add(copyButton, gbc);
      	gbc.insets = new Insets(0, 0, 0, 0);
		c.add(sp, BorderLayout.CENTER);
        c.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void initialize(String title, Frame owner){
    	fileTextArea.setText("Please wait while file is loaded.");
    	setTitle(title);
		setLocationRelativeTo(owner);
		setVisible(true);
    }
    
    public void setText(String string){
		fileTextArea.setText(getTitle() + "\n\n" + string);
		fileTextArea.setCaretPosition(0);
    }
    
    public void actionPerformed(ActionEvent ae){
    
    	if(ae.getSource()==saveButton){
			TextSaver.saveText(fileTextArea.getText(), this);
    	}else if(ae.getSource()==copyButton){
    		TextCopier.copyText(fileTextArea.getText());
    	}
    }
    
}

