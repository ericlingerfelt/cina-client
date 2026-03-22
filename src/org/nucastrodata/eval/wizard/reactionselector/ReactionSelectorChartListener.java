package org.nucastrodata.eval.wizard.reactionselector;


/**
 * The listener interface for receiving reactionSelectorChart events.
 * The class that is interested in processing a reactionSelectorChart
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addReactionSelectorChartListener<code> method. When
 * the reactionSelectorChart event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ReactionSelectorChartEvent
 */
public interface ReactionSelectorChartListener{
	
	/**
	 * Fire isotope selected.
	 *
	 * @param ip the ip
	 */
	public void fireIsotopeSelected(org.nucastrodata.datastructure.util.IsotopePoint ip);
}
