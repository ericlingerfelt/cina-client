package org.nucastrodata.wizard.isotopeselector;


/**
 * The listener interface for receiving isotopeSelectorChart events.
 * The class that is interested in processing a isotopeSelectorChart
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addIsotopeSelectorChartListener<code> method. When
 * the isotopeSelectorChart event occurs, that object's appropriate
 * method is invoked.
 *
 * @see IsotopeSelectorChartEvent
 */
public interface IsotopeSelectorChartListener{
	
	/**
	 * Fire isotope selected.
	 *
	 * @param ip the ip
	 */
	public void fireIsotopeSelected(org.nucastrodata.datastructure.util.IsotopePoint ip);
}
