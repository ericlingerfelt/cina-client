package org.nucastrodata.element.elementviz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.io.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.io.FileGetter;


/**
 * The Class ElementVizSampleFrame.
 */
public class ElementVizSampleFrame extends JFrame{

	/** The top label. */
	JLabel picLabel, topLabel;
	
	/** The c. */
	Container c;
	
	/** The close button. */
	JButton closeButton;
	
	/**
	 * Instantiates a new element viz sample frame.
	 */
	public ElementVizSampleFrame(){
	
		setSize(550, 643);
		setTitle("Element Synthesis Visualizer Sample Picture");
	
		c = getContentPane();
		c.setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();	
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		
		closeButton = new JButton("Close");
		closeButton.setFont(Fonts.buttonFont);
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				dispose();
			}
		});
		
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		bottomPanel.add(closeButton, gbc);
		
		picLabel = new JLabel();
		
		JScrollPane picPane = new JScrollPane(picLabel
									, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
									, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		
		JPanel topPanel = new JPanel(new GridBagLayout());
		topLabel = new JLabel();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		topPanel.add(topLabel, gbc);
		
		c.add(topPanel, BorderLayout.NORTH);					
		c.add(picPane, BorderLayout.CENTER);
		c.add(bottomPanel, BorderLayout.SOUTH);
		
		
		c.validate();
	
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param index the new current state
	 */
	public void setCurrentState(int index){
		
		try{
		
			switch(index){
			
				case 0:
					topLabel.setText("Abundance Plotting Interface Sample Plot");
					picLabel.setIcon(new ImageIcon(FileGetter.getFileByName("samples/sample0.png")));
					break;
			
				case 1:
					topLabel.setText("Final Weighted Abund Plotting Interface Sample Plot");
					picLabel.setIcon(new ImageIcon(FileGetter.getFileByName("samples/sample1.png")));
					break;
					
				case 2:
					topLabel.setText("Thermo Profile Plotting Interface Sample Plot");
					picLabel.setIcon(new ImageIcon(FileGetter.getFileByName("samples/sample2.png")));
					break;
					
				case 3:
					topLabel.setText("Element Synthesis Animator Sample Frame");
					picLabel.setIcon(new ImageIcon(FileGetter.getFileByName("samples/sample3.png")));
					break;
			
				case 4:
					topLabel.setText("Final Weighted Abund Nuclide Chart Sample Chart");
					picLabel.setIcon(new ImageIcon(FileGetter.getFileByName("samples/sample4.png")));
					break;
					
				case 5:
					topLabel.setText("Integrated Flux Nuclide Chart Sample Chart");
					picLabel.setIcon(new ImageIcon(FileGetter.getFileByName("samples/sample5.png")));
					break;
					
				case 6:
					topLabel.setText("Sensitivity Study Plotting Interface Sample Plot");
					picLabel.setIcon(new ImageIcon(FileGetter.getFileByName("samples/sample6.png")));
					break;
			
			}
		
			
		
		}catch(Exception e){
		
			e.printStackTrace();
			
		}
		
		c.validate();
	
	}
	
}