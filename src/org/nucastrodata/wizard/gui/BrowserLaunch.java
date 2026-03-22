package org.nucastrodata.wizard.gui;

import javax.swing.*;

import org.nucastrodata.dialogs.*;

import org.nucastrodata.dialogs.GeneralDialog;


/**
 * The Class BrowserLaunch.
 */
public class BrowserLaunch {

	/**
	 * Clean url for mac.
	 *
	 * @param url the url
	 * @return the string
	 */
	private static String cleanUrlForMac(String url){
		url = url.substring(url.indexOf("<")+1);
		return "mailto:" + url.substring(0, url.indexOf(">"));
	}
	
   /**
    * Open url.
    *
    * @param url the url
    * @param parent the parent
    */
   public static void openURL(String url, JFrame parent) {
	   
      String osName = System.getProperty("os.name");
      try {
         if (osName.startsWith("Mac OS")) {
        	 if(url.indexOf("mailto:")!=-1){
        		 url = cleanUrlForMac(url);
        	 }
        	 Runtime.getRuntime().exec("open " + url);
            }
         else if (osName.startsWith("Windows")){
        	 if(url.startsWith("http") || url.startsWith("mailto")){
        		 Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        	 }else{
        		 Runtime.getRuntime().exec("cmd /c " + url);
        	 }
         }else { //assume Unix or Linux
            String[] browsers = {
               "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
            String browser = null;
            for (int count = 0; count < browsers.length && browser == null; count++)
               if (Runtime.getRuntime().exec(
                     new String[] {"which", browsers[count]}).waitFor() == 0)
                  browser = browsers[count];
            if (browser == null){
               throw new Exception("Could not find web browser");
            }
			Runtime.getRuntime().exec(new String[] {browser, url});
         }
      }catch (Exception e) {
    	  e.printStackTrace();
	    	  GeneralDialog dialog = new GeneralDialog(parent, "An error has occurred opening this file or link on your computer.", "Error!");
	    	  dialog.setVisible(true);
         }
      }
   
   /**
    * Open url.
    *
    * @param url the url
    * @param dlg the dlg
    */
   public static void openURL(String url, JDialog dlg) {
	   
	      String osName = System.getProperty("os.name");
	      try {
	    	  if (osName.startsWith("Mac OS")) {
	         	 if(url.indexOf("mailto:")!=-1){
	         		 url = cleanUrlForMac(url);
	         	 }
	         	 Runtime.getRuntime().exec("open " + url);
	             }
	         else if (osName.startsWith("Windows")){
	        	 if(url.startsWith("http") || url.startsWith("mailto")){
	        		 Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
	        	 }else{
	        		 Runtime.getRuntime().exec("cmd /c " + url);
	        	 }
	         }else { //assume Unix or Linux
	            String[] browsers = {
	               "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
	            String browser = null;
	            for (int count = 0; count < browsers.length && browser == null; count++)
	               if (Runtime.getRuntime().exec(
	                     new String[] {"which", browsers[count]}).waitFor() == 0)
	                  browser = browsers[count];
	            if (browser == null){
	               throw new Exception("Could not find web browser");
	            }
				Runtime.getRuntime().exec(new String[] {browser, url});
	         }
	      }catch (Exception e) {
		    	  GeneralDialog dialog = new GeneralDialog(dlg, "An error has occurred opening this file or link on your computer.", "Error!");
		    	  dialog.setVisible(true);
	         }
	      }

}
