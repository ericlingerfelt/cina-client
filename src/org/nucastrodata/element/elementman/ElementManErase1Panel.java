package org.nucastrodata.element.elementman;

import javax.swing.*;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;

import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.feature.ElementManDataStructure;
import org.nucastrodata.datastructure.util.ElementSimDataStructure;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import info.clearthought.layout.*;

public class ElementManErase1Panel extends JPanel implements ActionListener{

	private ElementManDataStructure ds;
	private ElementManSimTree tree;
	private DefaultListModel listModel;
	private JList list;
	JButton removeSimButton, addSimButton;
	
	/**
	 * Instantiates a new element man info1 panel.
	 *
	 * @param ds the ds
	 */
	public ElementManErase1Panel(ElementManDataStructure ds){
		
		this.ds = ds;
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>With the Erase Element Synthesis Simulations tool, you can delete element synthesis simulations from your User storage folder.</html>");
		
		TreeSelectionModel treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		
		JLabel treeTitleLabel = new JLabel("Available Simulations");
		
		tree = new ElementManSimTree();
		tree.setSelectionModel(treeSelectionModel);
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane sp1 = new JScrollPane(tree);
		
		JPanel treePanel = new JPanel(new BorderLayout());
		treePanel.add(treeTitleLabel, BorderLayout.NORTH);
		treePanel.add(sp1, BorderLayout.CENTER);
		
		JScrollPane sp2 = new JScrollPane(list);
		
		JLabel listTitleLabel = new JLabel("Selected Simulations");
		
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.add(listTitleLabel, BorderLayout.NORTH);
		listPanel.add(sp2, BorderLayout.CENTER);
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, listPanel);	
		sp.setDividerLocation(250);
		
		removeSimButton = new JButton("Remove Selected Simulations");
		removeSimButton.setFont(Fonts.buttonFont);
		removeSimButton.addActionListener(this);
		
		addSimButton = new JButton("Add Selected Simulations");
		addSimButton.setFont(Fonts.buttonFont);
		addSimButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		double[] columnButton = {TableLayoutConstants.PREFERRED, 40, TableLayoutConstants.PREFERRED};
		double[] rowButton = {TableLayoutConstants.PREFERRED};
		buttonPanel.setLayout(new TableLayout(columnButton, rowButton));
		buttonPanel.add(removeSimButton, 	  	"0, 0, c, c");
		buttonPanel.add(addSimButton,  			"2, 0, c, c");
		
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
	
	/**
	 * Checks if is list empty.
	 *
	 * @return true, if is list empty
	 */
	public boolean isListEmpty(){
		listModel.trimToSize();
		return listModel.size()==0;
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		tree.setCurrentState(ds.getSimMap(), true);
		if(ds.getSimMapSelected()!=null){
			for(ElementSimDataStructure mmds: ds.getSimMapSelected().values()){
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
		TreeMap<String, ElementSimDataStructure> map = new TreeMap<String, ElementSimDataStructure>(new org.nucastrodata.format.StringComparatorIgnoreCase());
		for(int i=0; i<listModel.size(); i++){
			ElementSimDataStructure mmds = (ElementSimDataStructure)listModel.get(i);
			map.put(mmds.getPath(), mmds);
		}
		ds.setSimMapSelected(map);
		String string = "";
		Iterator<String> itr = ds.getSimMapSelected().keySet().iterator();
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
		if(ae.getSource()==removeSimButton){
			if(list.getSelectedValues()!=null){
				Object[] array = list.getSelectedValues();
				for(int i=0; i<array.length; i++){
					listModel.removeElement(array[i]);
				}
			}
		}else if(ae.getSource()==addSimButton){
			if(tree.getSelectionPaths().length!=0){
				if(tree.getSelectionPaths().length!=0){
					ArrayList<ElementSimDataStructure> list = tree.getSelectedObjects(tree.getSelectionPaths());
					for(int i=0; i<list.size(); i++){
						if(!listModel.contains(list.get(i))){
							listModel.addElement(list.get(i));
						}
					}
				}
				
			}

		}
		
	}
}

