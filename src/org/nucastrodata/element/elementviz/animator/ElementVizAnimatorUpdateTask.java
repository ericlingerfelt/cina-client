package org.nucastrodata.element.elementviz.animator;

import org.nucastrodata.Cina;


/**
 * The Class ElementVizAnimatorUpdateTask.
 */
public class ElementVizAnimatorUpdateTask extends java.util.TimerTask{
	
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	public void run(){
		Cina.elementVizFrame.elementVizAnimatorFrame.updateAnimator();
	}
}