package org.nucastrodata.element.elementworkman;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.enums.FolderType;

public class ElementWorkManWorkflowTree extends JTree{

	private DefaultTreeModel model;
	private DefaultTreeSelectionModel selectionModel;
	private DefaultMutableTreeNode node, userNode, publicNode, sharedNode;
	private TreeMap<String, ElementSimWorkDataStructure> map;

	public ElementWorkManWorkflowTree(){
		node = new DefaultMutableTreeNode("Element Synthesis Workflows");
		model = new DefaultTreeModel(node);
		selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setModel(model);
		setEditable(false);
		putClientProperty("JTree.linestyle", "Angled");
		setSelectionModel(selectionModel);
		setShowsRootHandles(true);
		validate();
	}
	
	public ArrayList<ElementSimWorkDataStructure> getSelectedObjects(TreePath[] pathArray){

		ArrayList<ElementSimWorkDataStructure> list = new ArrayList<ElementSimWorkDataStructure>();

		for(int i=0; i<pathArray.length; i++){
		
			TreePath path = pathArray[i];
			
			if(((DefaultMutableTreeNode)(path.getLastPathComponent()))!=null &&
					((DefaultMutableTreeNode)(path.getLastPathComponent())).isLeaf()){
					
				ElementSimWorkDataStructure ds = (ElementSimWorkDataStructure)((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject();
				list.add(ds);
			}
		}
		
		return list;
		
	}
	
	private void createUserNodes(){
	
		userNode = new DefaultMutableTreeNode("User");
		model.insertNodeInto(userNode, node, 0);
		
		Iterator<String> itr = map.keySet().iterator();
		
		while(itr.hasNext()){
		
			ElementSimWorkDataStructure esds = map.get(itr.next());
			
			if(esds.getFolderType()==FolderType.USER){
				
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(esds);
				model.insertNodeInto(node, userNode, userNode.getChildCount());
				
			}
		}
		
		if(userNode.getChildCount()==0){
			node.remove(userNode);
		}
		
	}
	
	private void createSharedNodes(){
	
		sharedNode = new DefaultMutableTreeNode("Shared");
		model.insertNodeInto(sharedNode, node, 0);
		
		Iterator<String> itr = map.keySet().iterator();
		
		while(itr.hasNext()){
		
			ElementSimWorkDataStructure esds = map.get(itr.next());
			
			if(esds.getFolderType()==FolderType.SHARED){
				
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(esds);
				model.insertNodeInto(node, sharedNode, sharedNode.getChildCount());
			
			}
		}
		
		if(sharedNode.getChildCount()==0){
			node.remove(sharedNode);
		}
	
	}
	
	private void createPublicNodes(){
	
		publicNode = new DefaultMutableTreeNode("Public");
		model.insertNodeInto(publicNode, node, 0);
		
		Iterator<String> itr = map.keySet().iterator();
		
		while(itr.hasNext()){
		
			ElementSimWorkDataStructure esds = map.get(itr.next());
			
			if(esds.getFolderType()==FolderType.PUBLIC){
				
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(esds);
				model.insertNodeInto(node, publicNode, publicNode.getChildCount());
			
			}
		}
		
		if(publicNode.getChildCount()==0){
			node.remove(publicNode);
		}
	
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param map the map
	 */
	public void setCurrentState(TreeMap<String, ElementSimWorkDataStructure> map, boolean userOnly){
		this.map = map;
		node.removeAllChildren();
		model.reload();
		
		if(userOnly){
	
			createUserNodes();
	
		}else{
		
			createPublicNodes();
			createSharedNodes();
			createUserNodes();
		
		}
		
		expandPath(new TreePath(node));
		validate();
		repaint();	
	}

}
	

