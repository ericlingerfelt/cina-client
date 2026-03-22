package org.nucastrodata.wizard.isotopeselector;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*; 

import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.RateLibDataStructure;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import info.clearthought.layout.*;


/**
 * The Class IsotopeSelectorTreeDialog.
 */
public class IsotopeSelectorTreeDialog extends JDialog implements ActionListener, TreeSelectionListener{

	/** The tree. */
	private JTree tree;
	
	/** The model. */
	private DefaultTreeModel model;
	
	/** The node. */
	private DefaultMutableTreeNode node;
	
	/** The close button. */
	private JButton submitButton, closeButton;
	
	/** The isotope field. */
	private JTextField isotopeField;
	
	/** The ip. */
	private IsotopePoint ip;
	
	/** The isl. */
	private IsotopeSelectorListener isl;
	
	/**
	 * Instantiates a new isotope selector tree dialog.
	 *
	 * @param owner the owner
	 */
	public IsotopeSelectorTreeDialog(JFrame owner){
		
		super(owner, true);
		
		Container c = getContentPane();
		
		setSize(540, 350);
		setTitle("Select Isotope from a Tree");
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, 10, TableLayoutConstants.PREFERRED, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL, gap};
		c.setLayout(new TableLayout(column, row));
		
		node = new DefaultMutableTreeNode("Available Isotopes");
		model = new DefaultTreeModel(node);
		DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree = new JTree();
		tree.setModel(model);
		tree.setEditable(false);
		tree.putClientProperty("JTree.linestyle", "Angled");
		tree.setSelectionModel(selectionModel);
		tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener(this);
		JScrollPane pane = new JScrollPane(tree);
		
		submitButton = new JButton("Submit Isotope");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		submitButton.setEnabled(false);
		
		closeButton = new JButton("Close Window");
		closeButton.setFont(Fonts.buttonFont);
		closeButton.addActionListener(this);
		
		isotopeField = new JTextField();
		isotopeField.setEditable(false);
		
		JLabel topLabel = new JLabel("<html>Select an isotope from the tree"
									+ " <p>at the left by highlighting the isotope"
									+ " <p>and clicking <i>Submit</i> <i>Isotope</i>.</html>");
		
		JLabel isotopeLabel = new JLabel("Selected Isotope"); 
		isotopeLabel.setFont(Fonts.textFont);
		
		c.add(pane, "1, 1, 1, 11, f, f");
		c.add(topLabel, "3, 1, l, c");
		c.add(isotopeLabel, "3, 3, l, c");
		c.add(isotopeField, "3, 5, f, c");
		c.add(submitButton, "3, 7, f, c");
		c.add(closeButton, "3, 9, f, c");

	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==closeButton){
			setVisible(false);
			dispose();
		}else if(ae.getSource()==submitButton){
			setVisible(false);
			dispose();
			isl.fireIsotopeSubmitted(ip);
		}
	}
	
	/**
	 * Gets the selected object.
	 *
	 * @return the selected object
	 */
	public IsotopePoint getSelectedObject(){
		try{
			if(((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject() instanceof IsotopePoint){
				return (IsotopePoint)((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject();
			}
			return null;
		}catch(NullPointerException npe){
			return null;
		}
	}
	
	/**
	 * Gets the isotope point.
	 *
	 * @return the isotope point
	 */
	public IsotopePoint getIsotopePoint(){
		return this.ip;
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param ip the ip
	 * @param rlds the rlds
	 * @param isl the isl
	 */
	public void setCurrentState(IsotopePoint ip, RateLibDataStructure rlds, IsotopeSelectorListener isl){
		this.ip = ip;
		this.isl = isl;
		
		node = new DefaultMutableTreeNode("Available Isotopes");
		model = new DefaultTreeModel(node);
		tree.setModel(model);
		
		setIsotopeNodes(rlds.getIsotopeMapFull());
		tree.expandPath(new TreePath(node.getPath()));
		if(!ip.equals(new IsotopePoint(0, 0))){
			DefaultMutableTreeNode[] pathArray = new DefaultMutableTreeNode[4];
			pathArray[0] = node;
			Enumeration<?> enumElements = node.children();
			while(enumElements.hasMoreElements()){
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)enumElements.nextElement();
				if(child.getUserObject().toString().equals(MainDataStructure.getElementSymbol(ip.getZ()))){
					pathArray[1] = child;
					break;
				}
			}
			if(pathArray[1]!=null){
				Enumeration<?> enumIsotopes = pathArray[1].children();
				while(enumIsotopes.hasMoreElements()){
					DefaultMutableTreeNode child = (DefaultMutableTreeNode)enumIsotopes.nextElement();
					if(child.getUserObject().toString().equals(ip.toString())){
						TreePath path = new TreePath(child.getPath());
						tree.expandPath(path);
						tree.setSelectionPath(path);
						tree.scrollPathToVisible(path);
						break;
					}
				}
			}
			isotopeField.setText(ip.toString());
			submitButton.setEnabled(true);
		}
	}
	
	/**
	 * Sets the isotope nodes.
	 *
	 * @param isotopeMap the isotope map
	 */
	private void setIsotopeNodes(TreeMap<Integer, ArrayList<IsotopePoint>> isotopeMap){
		Iterator<Integer> itr = isotopeMap.keySet().iterator();
		int indexElement = 0;
		while(itr.hasNext()){
			Integer z = itr.next();
			DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode(MainDataStructure.getElementSymbol(z));
			model.insertNodeInto(elementNode, node, indexElement);
			ArrayList<IsotopePoint> list = isotopeMap.get(z);
			int indexIsotope = 0;
			for(IsotopePoint ip: list){
				DefaultMutableTreeNode isotopeNode = new DefaultMutableTreeNode(ip);
				model.insertNodeInto(isotopeNode, elementNode, indexIsotope);
				indexIsotope++;
			}
			indexElement++;
		}
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent tse){
		if(getSelectedObject()!=null){
			ip = getSelectedObject();
			isotopeField.setText(ip.toString());
			submitButton.setEnabled(true);
		}
	}
	
}
