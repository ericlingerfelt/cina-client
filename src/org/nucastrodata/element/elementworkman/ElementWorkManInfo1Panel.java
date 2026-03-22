package org.nucastrodata.element.elementworkman;

import javax.swing.*;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;

import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.feature.ElementWorkManDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import info.clearthought.layout.*;

public class ElementWorkManInfo1Panel extends JPanel implements ActionListener{

	private ElementWorkManDataStructure ds;
	private ElementWorkManWorkflowTree tree;
	private DefaultListModel listModel;
	private JList list;
	
	JButton removeSimButton, addSimButton;
	
	/**
	 * Instantiates a new element man info1 panel.
	 *
	 * @param ds the ds
	 */
	public ElementWorkManInfo1Panel(ElementWorkManDataStructure ds){
		
		this.ds = ds;
		
		TreeSelectionModel treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		
		JLabel treeTitleLabel = new JLabel("Available Workflows");
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>With the Element Synthesis Workflow Info tool, you can view information regarding element synthesis workflows selected below.</html>");
		
		tree = new ElementWorkManWorkflowTree();
		tree.setSelectionModel(treeSelectionModel);
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane sp1 = new JScrollPane(tree);
		
		JPanel treePanel = new JPanel(new BorderLayout());
		treePanel.add(treeTitleLabel, BorderLayout.NORTH);
		treePanel.add(sp1, BorderLayout.CENTER);
		
		JScrollPane sp2 = new JScrollPane(list);
		
		JLabel listTitleLabel = new JLabel("Selected Workflows");
		
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.add(listTitleLabel, BorderLayout.NORTH);
		listPanel.add(sp2, BorderLayout.CENTER);
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, listPanel);	
		sp.setDividerLocation(250);
		
		removeSimButton = new JButton("Remove Selected Workflows");
		removeSimButton.setFont(Fonts.buttonFont);
		removeSimButton.addActionListener(this);
		
		addSimButton = new JButton("Add Selected Workflows");
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
		tree.setCurrentState(ds.getSimWorkMap(), false);
		if(ds.getSimWorkMapSelected()!=null){
			for(ElementSimWorkDataStructure mmds: ds.getSimWorkMapSelected().values()){
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
		TreeMap<String, ElementSimWorkDataStructure> map = new TreeMap<String, ElementSimWorkDataStructure>(new org.nucastrodata.format.StringComparatorIgnoreCase());
		for(int i=0; i<listModel.size(); i++){
			ElementSimWorkDataStructure mmds = (ElementSimWorkDataStructure)listModel.get(i);
			map.put(mmds.getPath(), mmds);
		}
		ds.setSimWorkMapSelected(map);
		String string = "";
		Iterator<ElementSimWorkDataStructure> itr = ds.getSimWorkMapSelected().values().iterator();
		while(itr.hasNext()){
			string += itr.next().getIndex();
			if(itr.hasNext()){
				string += ",";
			}
		}
		ds.setSimWorkflowIndices(string);
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
				ArrayList<ElementSimWorkDataStructure> list = tree.getSelectedObjects(tree.getSelectionPaths());
				for(int i=0; i<list.size(); i++){
					if(!listModel.contains(list.get(i))){
						listModel.addElement(list.get(i));
					}
				}
				
			}

		}
		
	}
}

