package org.nucastrodata.wizard.gui.filefilter;

import java.io.File;
import javax.swing.filechooser.*;

import org.nucastrodata.wizard.gui.filefilter.FilterType;
import org.nucastrodata.wizard.gui.filefilter.FilterUtils;


/**
 * The Class CustomFilter.
 */
public class CustomFilter extends FileFilter {
	
	/** The filter type. */
	private FilterType filterType;
	
	/**
	 * Instantiates a new custom filter.
	 *
	 * @param filterType the filter type
	 */
	public CustomFilter(FilterType filterType){
		this.filterType = filterType;
	}
	
	/**
	 * Gets the filter type.
	 *
	 * @return the filter type
	 */
	public FilterType getFilterType(){
		return this.filterType;
	}
	
    /* (non-Javadoc)
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = FilterUtils.getExtension(f);
        if (extension != null) {
            if (filterType.containsValue(extension)) {
            	return true;
            }
			return false;
        }

        return false;
    }
    
    //The description of this filter
    /* (non-Javadoc)
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    public String getDescription() {
        return filterType.toString();
    }
}