package org.nucastrodata.wizard.isotopeselector;


/**
 * The listener interface for receiving isotopeSelector events.
 * The class that is interested in processing a isotopeSelector
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addIsotopeSelectorListener<code> method. When
 * the isotopeSelector event occurs, that object's appropriate
 * method is invoked.
 *
 * @see IsotopeSelectorEvent
 */
public interface IsotopeSelectorListener{
	
	/**
	 * Fire isotope submitted.
	 *
	 * @param ip the ip
	 */
	public void fireIsotopeSubmitted(org.nucastrodata.datastructure.util.IsotopePoint ip);
}
