package org.nucastrodata.format;

import java.util.*;


/**
 * The Class StringComparatorIgnoreCase.
 */
public class StringComparatorIgnoreCase implements Comparator<String>{
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(String s1, String s2){
		return s1.compareToIgnoreCase(s2);
	}
}
