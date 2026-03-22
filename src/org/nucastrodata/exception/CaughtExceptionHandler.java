package org.nucastrodata.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.util.UncaughtException;
import org.nucastrodata.io.CGICom;

import java.awt.*;

public class CaughtExceptionHandler {

	public static void handleException(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		UncaughtException ueds = new UncaughtException();
		ueds.setStackTrace(sw.toString());
		
		new CGICom().doCGICall(Cina.cinaMainDataStructure
				, ueds
				, CGICom.LOG_JAVA_EXCEPTION
				, new Frame());

	}

}
