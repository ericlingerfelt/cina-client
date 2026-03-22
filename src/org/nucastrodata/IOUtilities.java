package org.nucastrodata;

import java.io.*;


/**
 * The Class IOUtilities.
 */
public class IOUtilities{
	
	/**
	 * Read stream.
	 *
	 * @param in the in
	 * @param out the out
	 */
	public static void readStream(InputStream in, OutputStream out){
		synchronized(in){
			synchronized(out){
				byte[] buffer = new byte[256];
				while(true){
					try{
						int bytesRead = in.read(buffer);
						if(bytesRead==-1){break;}
						out.write(buffer, 0, bytesRead);
					}catch(IOException ioe){
						ioe.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * Write file.
	 *
	 * @param array the array
	 * @param file the file
	 */
	public static void writeFile(byte[] array, File file){
		try{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(array);
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Read file.
	 *
	 * @param file the file
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] readFile(File file) throws IOException{
		int i = (int)file.length();
		byte[] buffer = new byte[i];
		FileInputStream fis= new FileInputStream(file);
		fis.read(buffer);
		fis.close();
		return buffer;
	}
} 