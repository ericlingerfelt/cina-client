package org.nucastrodata.element.elementviz.util;

import java.awt.Color;
import java.util.Vector;


/**
 * The Class AbundRowData.
 */
public class AbundRowData extends Vector{

	/**
	 * Instantiates a new abund row data.
	 *
	 * @param min the min
	 * @param max the max
	 * @param show the show
	 * @param color the color
	 */
	public AbundRowData(int min, int max, boolean show, Color color){
	
		addElement(new Integer(min));
		addElement(new Integer(max));
		addElement(new Boolean(show));
		addElement(color);
	
	}

}
