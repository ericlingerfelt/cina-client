package org.nucastrodata.element.thermoman;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.enums.FolderType;

public class ThermoManSetTree extends JTree{

	private DefaultTreeModel model;
	private DefaultTreeSelectionModel selectionModel;
	private DefaultMutableTreeNode node, userNode, publicNode, sharedNode;
	private TreeMap<String, ThermoProfileSetDataStructure> map;

	public ThermoManSetTree(){
		node = new DefaultMutableTreeNode("Thermodynamic Profile Sets");
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
	
	public ArrayList<ThermoProfileSetDataStructure> getSelectedObjects(TreePath[] pathArray){

		ArrayList<ThermoProfileSetDataStructure> list = new ArrayList<ThermoProfileSetDataStructure>();

		for(int i=0; i<pathArray.length; i++){
		
			TreePath path = pathArray[i];

			if(((DefaultMutableTreeNode)(path.getLastPathComponent()))!=null &&
					((DefaultMutableTreeNode)(path.getLastPathComponent())).isLeaf()){

					list.add((ThermoProfileSetDataStructure)((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject());
			
					
			}
		
		}
		
		return list;
		
	}
	
	private void createUserNodes(){
	
		userNode = new DefaultMutableTreeNode("User");
		model.insertNodeInto(userNode, node, 0);
		
		Iterator<String> itr = map.keySet().iterator();
		
		while(itr.hasNext()){
		
			ThermoProfileSetDataStructure tpsds = map.get(itr.next());
			
			if(tpsds.getFolderType()==FolderType.USER){

				DefaultMutableTreeNode node = new DefaultMutableTreeNode(tpsds);
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
		
			ThermoProfileSetDataStructure tpsds = map.get(itr.next());
			
			if(tpsds.getFolderType()==FolderType.SHARED){

				DefaultMutableTreeNode node = new DefaultMutableTreeNode(tpsds);
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
			
			ThermoProfileSetDataStructure tpsds = map.get(itr.next());
			
			if(tpsds.getFolderType()==FolderType.PUBLIC){

				DefaultMutableTreeNode node = new DefaultMutableTreeNode(tpsds);
				model.insertNodeInto(node, publicNode, publicNode.getChildCount());
				
			}
		}
		
		if(publicNode.getChildCount()==0){
			node.remove(publicNode);
		}
	
	}
	
	public void setCurrentState(TreeMap<String, ThermoProfileSetDataStructure> map, boolean userOnly){
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
	

