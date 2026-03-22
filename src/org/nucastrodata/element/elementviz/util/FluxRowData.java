package org.nucastrodata.element.elementviz.util;

import java.awt.Color;
import java.util.Vector;


/**
 * The Class FluxRowData.
 */
public class FluxRowData extends Vector{

	/**
	 * Instantiates a new flux row data.
	 *
	 * @param min the min
	 * @param max the max
	 * @param show the show
	 * @param color the color
	 * @param linestyle the linestyle
	 * @param linewidth the linewidth
	 */
	public FluxRowData(int min, int max, boolean show, Color color, int linestyle, int linewidth){
	
		addElement(new Integer(min));
		addElement(new Integer(max));
		addElement(new Boolean(show));
		addElement(color);
		addElement(new Integer(linestyle));
		addElement(new Integer(linewidth));
	
	}

}
