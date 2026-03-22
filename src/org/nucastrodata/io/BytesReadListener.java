package org.nucastrodata.io;


/**
 * The listener interface for receiving bytesRead events.
 * The class that is interested in processing a bytesRead
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addBytesReadListener<code> method. When
 * the bytesRead event occurs, that object's appropriate
 * method is invoked.
 *
 * @see BytesReadEvent
 */
public interface BytesReadListener {
	
	/**
	 * Sets the bytes read.
	 *
	 * @param totalBytesRead the new bytes read
	 */
	public void setBytesRead(long totalBytesRead);
	
	public void setContentLength(long contentLength);
}
