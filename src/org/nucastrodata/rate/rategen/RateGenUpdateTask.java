package org.nucastrodata.rate.rategen;

import org.nucastrodata.*;

import org.nucastrodata.Cina;


/**
 * The Class RateGenUpdateTask.
 */
public class RateGenUpdateTask extends java.util.TimerTask{
	
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	public void run(){Cina.cinaCGIComm.doCGICall("RATE GENERATION UPDATE", Cina.rateGenFrame);}
}