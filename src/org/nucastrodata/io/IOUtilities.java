package org.nucastrodata.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The Class IOUtilities.
 */
public class IOUtilities {
	
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
	
	/**
	 * Write file.
	 *
	 * @param file the file
	 * @param array the array
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeFile(File file, byte[] array) throws IOException{
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(array);
		fos.close();
	}
	
	/**
	 * Write file.
	 *
	 * @param filename the filename
	 * @param array the array
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeFile(String filename, byte[] array) throws IOException{
		FileOutputStream fos = new FileOutputStream(new File(filename));
		fos.write(array);
		fos.close();
	}
	
	/**
	 * Read stream.
	 *
	 * @param in the in
	 * @param out the out
	 * @param brl the brl
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void readStream(InputStream in, OutputStream out) throws IOException{
		synchronized(in){
			synchronized(out){
				byte[] buffer = new byte[256];
				while(true){
					int bytesRead = in.read(buffer);
					if(bytesRead==-1){break;}
					out.write(buffer, 0, bytesRead);
				}
			}
		}
	}
}
