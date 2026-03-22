package org.nucastrodata.io;

import java.net.*;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;

import org.nucastrodata.Cina;
import org.nucastrodata.IOUtilities;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.dialogs.DownloadFileDialog;


/**
 * The Class FileGetter.
 */
public class FileGetter {

	public static byte[] getFileByName(String filename){
		try{
			filename = URLEncoder.encode(filename, "UTF-8");
			URL url = null;
			if(Cina.cinaMainDataStructure.getURLType().equals("DEV")){
				url = new URL("https://nucastrodata2.ornl.gov/cina_dev/get_file.php");
			}else{
				url = new URL("https://nucastrodata2.ornl.gov/cina/get_file.php");
			}
			HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlConnection.setDoOutput(true);
			OutputStream os = urlConnection.getOutputStream();
			String string = "FILENAME=" + filename;
			os.write(string.getBytes());
			os.close();
			InputStream inputStream = urlConnection.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtilities.readStream(inputStream, baos);
			return baos.toByteArray();
		}catch(Exception e){
			return null;
		}
	}
	
	public static byte[] getFileByPath(String filepath){
		try{
			filepath = URLEncoder.encode(filepath, "UTF-8");
			URL url = null;
			if(Cina.cinaMainDataStructure.getURLType().equals("DEV")){
				url = new URL("https://nucastrodata2.ornl.gov/cina_dev/get_file.php");
			}else{
				url = new URL("https://nucastrodata2.ornl.gov/cina/get_file.php");
			}
			HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlConnection.setDoOutput(true);
			OutputStream os = urlConnection.getOutputStream();
			String string = "FILEPATH=" + filepath;
			os.write(string.getBytes());
			os.close();
			InputStream inputStream = urlConnection.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtilities.readStream(inputStream, baos);
			return baos.toByteArray();
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Download data harvest file.
	 *
	 * @param filename the filename
	 * @param mds the mds
	 * @param dialog the dialog
	 * @param file the file
	 */
	public static void downloadDataHarvestFile(String filename, MainDataStructure mds, DownloadFileDialog dialog, File file){
		try{
			filename = URLEncoder.encode(filename, "UTF-8");
			URL url = null;
			if(mds.getURLType().equals("DEV")){
				url = new URL("https://nucastrodata2.ornl.gov/cina_dev/get_data_harvest_file.php");
			}else{
				url = new URL("https://nucastrodata2.ornl.gov/cina/get_data_harvest_file.php");
			}
			HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlConnection.setDoOutput(true);
			OutputStream os = urlConnection.getOutputStream();
			String string = "FILENAME=" + filename + "&ID=" + mds.getID() + "&URL_TYPE=" + mds.getURLType().toString();
			os.write(string.getBytes());
			os.close();
			InputStream inputStream = urlConnection.getInputStream();
			FileOutputStream fos = new FileOutputStream(file);
			readStream(inputStream, fos, dialog);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Read stream.
	 *
	 * @param in the in
	 * @param out the out
	 * @param dialog the dialog
	 */
	private static void readStream(InputStream in, OutputStream out, DownloadFileDialog dialog){
		int totalBytesRead = 0;
		synchronized(in){
			synchronized(out){
				byte[] buffer = new byte[256];
				while(true){
					try{
						int bytesRead = in.read(buffer);
						totalBytesRead += bytesRead;
						dialog.setBytesRead(totalBytesRead);
						if(bytesRead==-1){break;}
						out.write(buffer, 0, bytesRead);
					}catch(IOException ioe){
						ioe.printStackTrace();
					}
				}
			}
		}
	}
	
}
