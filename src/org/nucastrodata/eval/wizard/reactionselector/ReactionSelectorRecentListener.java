package org.nucastrodata.eval.wizard.reactionselector;

import org.nucastrodata.eval.wizard.reactionselector.ReactionSelectorListener;


/**
 * The listener interface for receiving reactionSelectorRecent events.
 * The class that is interested in processing a reactionSelectorRecent
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addReactionSelectorRecentListener<code> method. When
 * the reactionSelectorRecent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ReactionSelectorRecentEvent
 */
public interface ReactionSelectorRecentListener extends ReactionSelectorListener{
	
	/**
	 * Fire rate selected.
	 *
	 * @param rds the rds
	 */
	public void fireRateSelected(org.nucastrodata.datastructure.util.ReactionDataStructure rds);
}
