package org.nucastrodata.element.thermoman;

import javax.swing.*;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;

import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.feature.ThermoManDataStructure;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import info.clearthought.layout.*;

public class ThermoManCopy1Panel extends JPanel implements ActionListener{

	private ThermoManDataStructure ds;
	private ThermoManSetTree tree;
	JButton removeSetButton, addSetButton;
	private DefaultListModel listModel;
	private JList list;
	
	/**
	 * Instantiates a new element man info1 panel.
	 *
	 * @param ds the ds
	 */
	public ThermoManCopy1Panel(ThermoManDataStructure ds){
		
		this.ds = ds;
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>With the Copy Thermodynamic Profiles to Shared Folder tool, you can copy "
				 + "sets of thermodynamic profiles from your User storage folder to the Shared storage folder. "
				 + "Thermodynamic profiles in the Shared storage folder can be accessed by all Users of the suite.<br><br>"
				 + "Contact coordinator@nucastrodata.org if you wish to remove any thermodynamic profiles "
				 + "that you have copied into the Shared storage folder.</html>");
				 
		TreeSelectionModel treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		
		JLabel treeTitleLabel = new JLabel("Available Thermodynamic Profiles");
		
		tree = new ThermoManSetTree();
		tree.setSelectionModel(treeSelectionModel);
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane sp1 = new JScrollPane(tree);
		
		JPanel treePanel = new JPanel(new BorderLayout());
		treePanel.add(treeTitleLabel, BorderLayout.NORTH);
		treePanel.add(sp1, BorderLayout.CENTER);
		
		JScrollPane sp2 = new JScrollPane(list);
		
		JLabel listTitleLabel = new JLabel("Selected Thermodynamic Profiles");
		
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.add(listTitleLabel, BorderLayout.NORTH);
		listPanel.add(sp2, BorderLayout.CENTER);
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, listPanel);	
		sp.setDividerLocation(250);
		
		removeSetButton = new JButton("Remove Selected Thermodynamic Profile Sets");
		removeSetButton.setFont(Fonts.buttonFont);
		removeSetButton.addActionListener(this);
		
		addSetButton = new JButton("Add Selected Thermodynamic Profile Sets");
		addSetButton.setFont(Fonts.buttonFont);
		addSetButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		double[] columnButton = {TableLayoutConstants.PREFERRED, 40, TableLayoutConstants.PREFERRED};
		double[] rowButton = {TableLayoutConstants.PREFERRED};
		buttonPanel.setLayout(new TableLayout(columnButton, rowButton));
		buttonPanel.add(removeSetButton, 	  	"0, 0, c, c");
		buttonPanel.add(addSetButton,  			"2, 0, c, c");
		
		double gap = 10;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap};

		setLayout(new TableLayout(column, row));
		
		add(topLabel, 		"1, 1, c, c");
		add(sp, 			"1, 3, f, f");
		add(buttonPanel, 	"1, 5, c, c");
		
	}
	
	public boolean isListEmpty(){
		listModel.trimToSize();
		return listModel.size()==0;
	}
	
	public void setCurrentState(){
		tree.setCurrentState(ds.getSetMap(), true);
		if(ds.getSetMapSelected()!=null){
			for(ThermoProfileSetDataStructure mmds: ds.getSetMapSelected().values()){
				listModel.addElement(mmds);
			}
		}
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		TreeMap<String, ThermoProfileSetDataStructure> map = new TreeMap<String, ThermoProfileSetDataStructure>(new org.nucastrodata.format.StringComparatorIgnoreCase());
		for(int i=0; i<listModel.size(); i++){
			ThermoProfileSetDataStructure mmds = (ThermoProfileSetDataStructure)listModel.get(i);
			map.put(mmds.getPath(), mmds);
		}
		ds.setSetMapSelected(map);
		String string = "";
		Iterator<String> itr = ds.getSetMapSelected().keySet().iterator();
		while(itr.hasNext()){
			string += itr.next();
			if(itr.hasNext()){
				string += ",";
			}
		}
		ds.setPaths(string);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==removeSetButton){
			if(list.getSelectedValues()!=null){
				Object[] array = list.getSelectedValues();
				for(int i=0; i<array.length; i++){
					listModel.removeElement(array[i]);
				}
			}
		}else if(ae.getSource()==addSetButton){
			if(tree.getSelectionPaths().length!=0){
				ArrayList<ThermoProfileSetDataStructure> list = tree.getSelectedObjects(tree.getSelectionPaths());
				for(int i=0; i<list.size(); i++){
					if(!listModel.contains(list.get(i))){
						listModel.addElement(list.get(i));
					}
				}
			}
		}
	}

	public boolean sharedSetExists(){
		for(int i=0; i<listModel.size(); i++){
			ThermoProfileSetDataStructure mmds = (ThermoProfileSetDataStructure)listModel.get(i);
			if(ds.getSetMap().containsKey("SHARED/" + mmds.getName())){
				return true;
			}
		}
		return false;
	}
	
}



