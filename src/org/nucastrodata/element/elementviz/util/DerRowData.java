package org.nucastrodata.element.elementviz.util;

import java.awt.Color;
import java.util.Vector;


/**
 * The Class DerRowData.
 */
public class DerRowData extends Vector{

	/**
	 * Instantiates a new der row data.
	 *
	 * @param min the min
	 * @param max the max
	 * @param show the show
	 * @param posColor the pos color
	 * @param negColor the neg color
	 */
	public DerRowData(int min, int max, boolean show, Color posColor, Color negColor){
	
		addElement(new Integer(min));
		addElement(new Integer(max));
		addElement(new Boolean(show));
		addElement(posColor);
		addElement(negColor);
	
	}

}
