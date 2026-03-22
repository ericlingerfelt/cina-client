package org.nucastrodata.eval.wizard.reactionselector;

import javax.swing.tree.*;

import org.nucastrodata.eval.wizard.reactionselector.ReactionSelectorListener;


/**
 * The listener interface for receiving reactionSelectorTree events.
 * The class that is interested in processing a reactionSelectorTree
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addReactionSelectorTreeListener<code> method. When
 * the reactionSelectorTree event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ReactionSelectorTreeEvent
 */
public interface ReactionSelectorTreeListener extends ReactionSelectorListener{
	
	/**
	 * Fire isotope selected.
	 *
	 * @param node the node
	 */
	public void fireIsotopeSelected(DefaultMutableTreeNode node); 
}
