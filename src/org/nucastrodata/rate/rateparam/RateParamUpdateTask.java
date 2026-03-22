package org.nucastrodata.rate.rateparam;

import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;

import org.nucastrodata.Cina;


/**
 * The Class RateParamUpdateTask.
 */
public class RateParamUpdateTask extends java.util.TimerTask{

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	public void run(){Cina.cinaCGIComm.doCGICall("RATE PARAMETERIZATION UPDATE", Cina.rateParamFrame);}

}