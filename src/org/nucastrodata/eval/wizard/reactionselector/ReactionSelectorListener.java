package org.nucastrodata.eval.wizard.reactionselector;


/**
 * The listener interface for receiving reactionSelector events.
 * The class that is interested in processing a reactionSelector
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addReactionSelectorListener<code> method. When
 * the reactionSelector event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ReactionSelectorEvent
 */
public interface ReactionSelectorListener{
	
	/**
	 * Fire reaction submitted.
	 *
	 * @param rds the rds
	 */
	public void fireReactionSubmitted(org.nucastrodata.datastructure.util.ReactionDataStructure rds);
}
