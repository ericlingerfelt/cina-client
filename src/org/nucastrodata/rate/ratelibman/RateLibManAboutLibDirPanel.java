package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.datastructure.util.LibraryDirectoryDataStructure;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.event.*;
import java.util.Iterator;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


public class RateLibManAboutLibDirPanel extends WizardPanel implements ActionListener{
	
	private JPanel mainPanel, boxPanel; 
	private JLabel label1, label2, label; 
	private WordWrapLabel topLabel;
	private JTextArea area1, area2;
	private JScrollPane sp1, sp2;
	public JComboBox dirComboBox;
	private RateLibManDataStructure ds;

	public RateLibManAboutLibDirPanel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		Cina.rateLibManFrame.setCurrentFeatureIndex(10);
		Cina.rateLibManFrame.setCurrentPanelIndex(1);

		label = new JLabel("Choose rate library directory: ");
		label.setFont(Fonts.textFont);

		dirComboBox = new JComboBox();
		dirComboBox.addActionListener(this);
		dirComboBox.setFont(Fonts.textFont);

		label1 = new JLabel("Specific comments: ");
		label1.setFont(Fonts.textFont);
		
		label2 = new JLabel("Library directory information: ");
		label2.setFont(Fonts.textFont);

		topLabel = new WordWrapLabel(true);
		topLabel.setText("There are currently no rate library directories associated with your account.");

		String string = "Contents: reaction identifier, Q value, biblio information, parameters"
							 + ", descriptors (resonant vs. nonresonant, forward vs. reverse). Rates are parameterized as functions of temperature.)"
							 + "\n\nR = exp(a1 + a2/T9 + a3/T9^(1/3) + a4*T9^(1/3) + a5*T9 + a6*T9^(5/3) + a7*ln(T9))\n"
							 + "+ [if needed] exp(a8 + a9/T9 + a10/T9^(1/3) + a11*T9^(1/3) + a12*T9 + a13*T9^(5/3) + a14*ln(T9)) \n"
							 + "+ [if needed]exp(a15 + a16/T9 + a17/T9^(1/3) + a18*T9^(1/3) + a19*T9 + a20*T9^(5/3) + a21*ln(T9)) \n"
							 + "\nFormat: plain text online library";
		
		area1 = new JTextArea("Use the dropdown menu to select a rate library directory.");
		area1.setFont(Fonts.textFont);
		area1.setLineWrap(true);
		area1.setWrapStyleWord(true);
		
		sp1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp1.setPreferredSize(new Dimension(500, 80));

		area2 = new JTextArea(string);
		area2.setFont(Fonts.textFont);
		area2.setLineWrap(true);
		area2.setWrapStyleWord(true);
		
		sp2 = new JScrollPane(area2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		boxPanel = new JPanel();
		double[] columnBox = {TableLayoutConstants.PREFERRED, 20, TableLayoutConstants.FILL};
		double[] rowBox = {TableLayoutConstants.PREFERRED};
		boxPanel.setLayout(new TableLayout(columnBox, rowBox));
		boxPanel.add(label, "0, 0, r, c");
		boxPanel.add(dirComboBox, "2, 0, f, c");
		
		mainPanel = new JPanel();
		
		addWizardPanel("Rate Library Manager", "Library Directory Info", "1", "1");
		
		add(mainPanel, BorderLayout.CENTER);

		validate();
		
	}
	
	public void updateAfterGetLibDirInfo(){
		area1.setText(getLibDirDesc(ds.getLibDirDS()));
		area1.setCaretPosition(0);
	}
	
	public void getCurrentState(){}
	public String getLibDirDesc(LibraryDirectoryDataStructure ldds){
	
		String string = "";
		string = "Library directory name: " + ldds.getName() + "\n";
		string = string + "Number of libraries: " + ldds.getLibList().size() + "\n";
		string = string + "List of libraries: \n";
		Iterator<String> itr = ldds.getLibList().iterator();
		while(itr.hasNext()){
			String lib = itr.next();
			string = string + lib + "\n";
		}
		string = string + "Library notes: " + ldds.getNotes() + "\n";
		string = string + "Creation date: " + ldds.getCreationDate() + "\n";
		return string;
	
	}

	public void setCurrentState(){
		
		mainPanel.removeAll();
		
		if(ds.getLibDirList().size()==0){
		
			double[] column = {20, TableLayoutConstants.FILL, 20};
			double[] row = {20, TableLayoutConstants.FILL, 20};
			mainPanel.setLayout(new TableLayout(column, row));
			mainPanel.add(topLabel, "1, 1, c, c");

		}else{
		
			double[] column = {20, TableLayoutConstants.FILL, 20};
			double[] row = {20, TableLayoutConstants.PREFERRED, 20
									, TableLayoutConstants.PREFERRED, 20
									, TableLayoutConstants.FILL, 20
									, TableLayoutConstants.PREFERRED, 20
									, TableLayoutConstants.FILL, 20};
			mainPanel.setLayout(new TableLayout(column, row));
			mainPanel.add(boxPanel, 	"1, 1, f, c");
			mainPanel.add(label2, 	"1, 3, l, c");
			mainPanel.add(sp2, 		"1, 5, f, f");
			mainPanel.add(label1, 	"1, 7, l, c");
			mainPanel.add(sp1, 		"1, 9, f, f");
		
			dirComboBox.removeAllItems();
			dirComboBox.removeActionListener(this);

			Iterator<String> itr = ds.getLibDirList().iterator();
			while(itr.hasNext()){
				dirComboBox.addItem(itr.next());
			}
			dirComboBox.setSelectedIndex(0);
			dirComboBox.addActionListener(this);
		
			area1.setText(getLibDirDesc(ds.getLibDirDS()));
			area1.setCaretPosition(0);

		}

		validate();
		repaint();

	}

	public void actionPerformed(ActionEvent ae) {	
		if(ae.getSource()==dirComboBox){
			PleaseWaitDialog dialog = new PleaseWaitDialog(Cina.rateLibManFrame, "Please wait while rate library directory information is loaded.");
			dialog.open();
			RateLibManGetLibDirInfoWorker worker = new RateLibManGetLibDirInfoWorker(ds, this, dialog);
			worker.execute();
		}
		
	}
	
}

class RateLibManGetLibDirInfoWorker extends SwingWorker<Void, Void>{

	private RateLibManDataStructure ds;
	private RateLibManAboutLibDirPanel parent;
	private PleaseWaitDialog dialog;

	public RateLibManGetLibDirInfoWorker(RateLibManDataStructure ds
											, RateLibManAboutLibDirPanel parent
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
			LibraryDirectoryDataStructure ldds = new LibraryDirectoryDataStructure();
			
			ds.setLibDir(parent.dirComboBox.getSelectedItem().toString());
			ds.setLibDirDS(ldds);
			
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.GET_LIB_DIR_INFO
												, Cina.rateLibManFrame);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		dialog.close();
		parent.updateAfterGetLibDirInfo();
	}
	
}