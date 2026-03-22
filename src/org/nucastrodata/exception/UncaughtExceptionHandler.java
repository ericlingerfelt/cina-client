package org.nucastrodata.exception;

import java.awt.Frame;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.util.UncaughtException;
import org.nucastrodata.dialogs.MessageDialog;
import org.nucastrodata.io.CGICom;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
	
	public void uncaughtException(Thread t, Throwable e){
		if(e instanceof java.lang.ThreadDeath
				|| e instanceof java.lang.IllegalThreadStateException
				|| e instanceof java.util.NoSuchElementException){
			return;
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		UncaughtException ueds = new UncaughtException();
		ueds.setStackTrace(sw.toString());
		
		new CGICom().doCGICall(Cina.cinaMainDataStructure
								, ueds
								, CGICom.LOG_JAVA_EXCEPTION
								, new Frame());

		MessageDialog.createMessageDialog(new Frame()
				, "An error has occurred completing your request. "
				+ "The appropriate staff have been notified."
				, "Error!");
	}

}
