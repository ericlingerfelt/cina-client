package org.nucastrodata.eval.wizard.reactionselector;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*; 

import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.RateLibDataStructure;
import org.nucastrodata.datastructure.util.ReactionDataStructure;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import info.clearthought.layout.*;


/**
 * The Class ReactionSelectorTreeDialog.
 */
public class ReactionSelectorTreeDialog extends JDialog implements ActionListener, TreeExpansionListener{

	/** The tree. */
	private JTree tree;
	
	/** The model. */
	private DefaultTreeModel model;
	
	/** The node. */
	private DefaultMutableTreeNode node;
	
	/** The close button. */
	private JButton selectButton, submitButton, closeButton;
	
	/** The reaction field. */
	private JTextField reactionField;
	
	/** The rds. */
	private ReactionDataStructure rds;
	
	/** The rstl. */
	private ReactionSelectorTreeListener rstl;
	
	/** The used tree paths. */
	private ArrayList<String> usedTreePaths = new ArrayList<String>();
	
	/**
	 * Instantiates a new reaction selector tree dialog.
	 *
	 * @param owner the owner
	 */
	public ReactionSelectorTreeDialog(JFrame owner){
		
		super(owner, true);
		
		Container c = getContentPane();
		
		setSize(540, 350);
		setTitle("Select Reaction from a Tree");
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, 10, TableLayoutConstants.PREFERRED, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL, gap};
		c.setLayout(new TableLayout(column, row));
		
		node = new DefaultMutableTreeNode("Available Reactions");
		model = new DefaultTreeModel(node);
		DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree = new JTree();
		tree.setModel(model);
		tree.setEditable(false);
		tree.putClientProperty("JTree.linestyle", "Angled");
		tree.setSelectionModel(selectionModel);
		tree.setShowsRootHandles(true);
		tree.addTreeExpansionListener(this);
		JScrollPane pane = new JScrollPane(tree);
		
		selectButton = new JButton("Select Reaction");
		selectButton.setFont(Fonts.buttonFont);
		selectButton.addActionListener(this);
		
		submitButton = new JButton("Submit Reaction");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		submitButton.setEnabled(false);
		
		closeButton = new JButton("Close Window");
		closeButton.setFont(Fonts.buttonFont);
		closeButton.addActionListener(this);
		
		reactionField = new JTextField();
		reactionField.setEditable(false);
		
		JLabel topLabel = new JLabel("<html>Select a reaction from the tree"
									+ " <p>at the left by highlighting the reaction"
									+ " <p>and clicking <i>Select</i> <i>Reaction</i>.</html>");
		
		JLabel reactionLabel = new JLabel("Selected Reaction"); 
		reactionLabel.setFont(Fonts.textFont);
		
		c.add(pane, "1, 1, 1, 13, f, f");
		c.add(topLabel, "3, 1, l, c");
		c.add(reactionLabel, "3, 3, l, c");
		c.add(reactionField, "3, 5, f, c");
		c.add(selectButton, "3, 7, f, c");
		c.add(submitButton, "3, 9, f, c");
		c.add(closeButton, "3, 11, f, c");

	}
	
	/**
	 * Sets the reaction nodes.
	 *
	 * @param node the node
	 * @param list the list
	 */
	public void setReactionNodes(DefaultMutableTreeNode node, ArrayList<ReactionDataStructure> list){
		int index = 0;
		for(ReactionDataStructure rds: list){
			DefaultMutableTreeNode rateNode = new DefaultMutableTreeNode(rds);
			model.insertNodeInto(rateNode, node, index);
			index++;
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeExpansionListener#treeExpanded(javax.swing.event.TreeExpansionEvent)
	 */
	public void treeExpanded(TreeExpansionEvent tee){
		TreePath path = tee.getPath();
		if(!usedTreePaths.contains(path.toString())){
			if(path.getPathCount()==3){
				usedTreePaths.add(path.toString());
				DefaultMutableTreeNode isotopeNode = (DefaultMutableTreeNode)tee.getPath().getLastPathComponent();
				DefaultMutableTreeNode dummy = isotopeNode.getFirstLeaf();
				if(dummy.toString().equals("DUMMY")){
					model.removeNodeFromParent(dummy);
				}
				rstl.fireIsotopeSelected(isotopeNode);
				tree.expandPath(path);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeExpansionListener#treeCollapsed(javax.swing.event.TreeExpansionEvent)
	 */
	public void treeCollapsed(TreeExpansionEvent tee){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==selectButton){
			if(getSelectedObject()!=null){
				rds = getSelectedObject();
				reactionField.setText(rds.toString());
				submitButton.setEnabled(true);
			}
		}else if(ae.getSource()==closeButton){
			setVisible(false);
			dispose();
		}else if(ae.getSource()==submitButton){
			setVisible(false);
			dispose();
			rstl.fireReactionSubmitted(rds);
		}
	}
	
	/**
	 * Gets the selected object.
	 *
	 * @return the selected object
	 */
	public ReactionDataStructure getSelectedObject(){
		try{
			if(((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject() instanceof ReactionDataStructure){
				return (ReactionDataStructure)((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject();
			}
			return null;
		}catch(NullPointerException npe){
			return null;
		}
	}
	
	/**
	 * Check data fields.
	 *
	 * @return true, if successful
	 */
	public boolean checkDataFields(){
		return !reactionField.getText().equals("");
	}
	
	/**
	 * Gets the reaction data structure.
	 *
	 * @return the reaction data structure
	 */
	public ReactionDataStructure getReactionDataStructure(){
		return this.rds;
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param rds the rds
	 * @param rlds the rlds
	 * @param rstl the rstl
	 */
	public void setCurrentState(ReactionDataStructure rds, RateLibDataStructure rlds, ReactionSelectorTreeListener rstl){
		this.rds = rds;
		this.rstl = rstl;
		setIsotopeNodes(rlds.getIsotopeMapAvailable());
		if(!rds.getIsotopePoint().equals(new IsotopePoint(0, 0))){
			DefaultMutableTreeNode[] pathArray = new DefaultMutableTreeNode[4];
			pathArray[0] = node;
			Enumeration<?> enumElements = node.children();
			while(enumElements.hasMoreElements()){
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)enumElements.nextElement();
				if(child.getUserObject().toString().equals(MainDataStructure.getElementSymbol(rds.getIsotopePoint().getZ()))){
					pathArray[1] = child;
					break;
				}
			}
			if(pathArray[1]!=null){
				Enumeration<?> enumIsotopes = pathArray[1].children();
				while(enumIsotopes.hasMoreElements()){
					DefaultMutableTreeNode child = (DefaultMutableTreeNode)enumIsotopes.nextElement();
					if(child.getUserObject().toString().equals(rds.getIsotopePoint().toString())){
						pathArray[2] = child;
						DefaultMutableTreeNode[] pathArrayTemp = new DefaultMutableTreeNode[3];
						pathArrayTemp[0] = pathArray[0];
						pathArrayTemp[1] = pathArray[1];
						pathArrayTemp[2] = pathArray[2];
						TreePath pathTemp = new TreePath(pathArrayTemp);
						usedTreePaths.add(pathTemp.toString());
						DefaultMutableTreeNode dummy = child.getFirstLeaf();
						if(dummy.toString().equals("DUMMY")){
							model.removeNodeFromParent(dummy);
						}
						rstl.fireIsotopeSelected(child);
						break;
					}
				}
				if(pathArray[2]!=null){
					Enumeration<?> enumReactions = pathArray[2].children();
					while(enumReactions.hasMoreElements()){
						DefaultMutableTreeNode child = (DefaultMutableTreeNode)enumReactions.nextElement();
						if(child.getUserObject().toString().equals(rds.toString())){
							pathArray[3] = child;
							break;
						}
					}
					if(pathArray[3]!=null){
						TreePath path = new TreePath(pathArray);
						tree.expandPath(path);
						tree.setSelectionPath(path);
						tree.scrollPathToVisible(path);
					}
				}
			}
			reactionField.setText(rds.toString());
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
				isotopeNode.add(new DefaultMutableTreeNode("DUMMY"));
				model.insertNodeInto(isotopeNode, elementNode, indexIsotope);
				indexIsotope++;
			}
			indexElement++;
		}
		
	} 
	
}
