package org.nucastrodata.element.elementman;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import org.nucastrodata.datastructure.util.ElementSimDataStructure;
import org.nucastrodata.enums.FolderType;

public class ElementManSimTree extends JTree{

	private DefaultTreeModel model;
	private DefaultTreeSelectionModel selectionModel;
	private DefaultMutableTreeNode node, userNode, publicNode, sharedNode;
	private TreeMap<String, DefaultMutableTreeNode> userDirNodeMap, publicDirNodeMap, sharedDirNodeMap;
	private TreeMap<String, ElementSimDataStructure> map;

	public ElementManSimTree(){
		node = new DefaultMutableTreeNode("Element Synthesis Simulations");
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
	
	public ArrayList<ElementSimDataStructure> getSelectedObjects(TreePath[] pathArray){

		ArrayList<ElementSimDataStructure> list = new ArrayList<ElementSimDataStructure>();

		for(int i=0; i<pathArray.length; i++){
		
			TreePath path = pathArray[i];


			if(((DefaultMutableTreeNode)(path.getLastPathComponent()))!=null &&
					((DefaultMutableTreeNode)(path.getLastPathComponent())).isLeaf()){
					
				if(path.getPathCount()==3){
					
					String simPath = ((DefaultMutableTreeNode)path.getLastPathComponent()).getParent().toString()
										+ "/" 
										+ ((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject().toString();
					
					
					list.add(getElementSimDataStructure(simPath));
				
				}else if(path.getPathCount()==4){
				
					String simPath = ((DefaultMutableTreeNode)path.getLastPathComponent()).getParent().getParent().toString()
							+ "/" 
							+ ((DefaultMutableTreeNode)path.getLastPathComponent()).getParent().toString()
							+ "_dir_"
							+ ((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject().toString();
				
					list.add(getElementSimDataStructure(simPath));
	
				}
					
			}else if(path.getPathCount()==3){
			
				Enumeration e = ((DefaultMutableTreeNode)(path.getLastPathComponent())).children();
				while(e.hasMoreElements()){
				
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
					String simPath = ((DefaultMutableTreeNode)path.getLastPathComponent()).getParent().toString()
							+ "/"  
							+ ((DefaultMutableTreeNode)(path.getLastPathComponent())).toString() 
							+ "_dir_"
							+ node.toString();

					list.add(getElementSimDataStructure(simPath));
				
				}
			
			}
		
		}
		
		return list;
		
	}
	
	private ElementSimDataStructure getElementSimDataStructure(String path){
		return map.get(path);
	}
	
	private void createUserNodes(){
	
		userNode = new DefaultMutableTreeNode("User");
		model.insertNodeInto(userNode, node, 0);
		
		Iterator<String> itr = map.keySet().iterator();
		userDirNodeMap = new TreeMap<String, DefaultMutableTreeNode>();
		
		while(itr.hasNext()){
		
			ElementSimDataStructure esds = map.get(itr.next());
			
			if(esds.getFolderType()==FolderType.USER){
				
				String name = esds.getName();
				
				if(name.indexOf("_dir_")!=-1){
					
					String dirName = name.substring(0,  name.lastIndexOf("_dir_"));
					String subName = name.substring(name.lastIndexOf("_dir_") + 5);
				
					DefaultMutableTreeNode mainNode = null;
				
					if(!userDirNodeMap.containsKey(dirName)){
						mainNode = new DefaultMutableTreeNode(dirName);
						model.insertNodeInto(mainNode, userNode, userNode.getChildCount());
						userDirNodeMap.put(dirName, mainNode);
					}
					mainNode = userDirNodeMap.get(dirName);
					
					DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(subName);
					model.insertNodeInto(subNode, mainNode, mainNode.getChildCount());
					
				}else{
				
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
					model.insertNodeInto(node, userNode, userNode.getChildCount());
				
				}
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
		sharedDirNodeMap = new TreeMap<String, DefaultMutableTreeNode>();
		
		while(itr.hasNext()){
		
			ElementSimDataStructure esds = map.get(itr.next());
			
			if(esds.getFolderType()==FolderType.SHARED){
				
				String name = esds.getName();
				
				if(name.indexOf("_dir_")!=-1){
					
					String dirName = name.substring(0,  name.lastIndexOf("_dir_"));
					String subName = name.substring(name.lastIndexOf("_dir_") + 5);
				
					DefaultMutableTreeNode mainNode = null;
				
					if(!sharedDirNodeMap.containsKey(dirName)){
						mainNode = new DefaultMutableTreeNode(dirName);
						model.insertNodeInto(mainNode, sharedNode, sharedNode.getChildCount());
						sharedDirNodeMap.put(dirName, mainNode);
					}
					mainNode = sharedDirNodeMap.get(dirName);
					
					DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(subName);
					model.insertNodeInto(subNode, mainNode, mainNode.getChildCount());
					
				}else{
				
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
					model.insertNodeInto(node, sharedNode, sharedNode.getChildCount());
				
				}
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
		publicDirNodeMap = new TreeMap<String, DefaultMutableTreeNode>();
		
		while(itr.hasNext()){
		
			ElementSimDataStructure esds = map.get(itr.next());
			
			if(esds.getFolderType()==FolderType.PUBLIC){
				
				String name = esds.getName();
				
				if(name.indexOf("_dir_")!=-1){
					
					String dirName = name.substring(0,  name.lastIndexOf("_dir_"));
					String subName = name.substring(name.lastIndexOf("_dir_") + 5);
				
					DefaultMutableTreeNode mainNode = null;
				
					if(!publicDirNodeMap.containsKey(dirName)){
						mainNode = new DefaultMutableTreeNode(dirName);
						model.insertNodeInto(mainNode, publicNode, publicNode.getChildCount());
						publicDirNodeMap.put(dirName, mainNode);
					}
					mainNode = publicDirNodeMap.get(dirName);
					
					DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(subName);
					model.insertNodeInto(subNode, mainNode, mainNode.getChildCount());
					
				}else{
				
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
					model.insertNodeInto(node, publicNode, publicNode.getChildCount());
				
				}
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
	public void setCurrentState(TreeMap<String, ElementSimDataStructure> map, boolean userOnly){
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
	

