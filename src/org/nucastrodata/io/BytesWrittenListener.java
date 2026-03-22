package org.nucastrodata.io;


/**
 * The listener interface for receiving bytesWritten events.
 * The class that is interested in processing a bytesWritten
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addBytesWrittenListener<code> method. When
 * the bytesWritten event occurs, that object's appropriate
 * method is invoked.
 *
 * @see BytesWrittenEvent
 */
public interface BytesWrittenListener {
	
	/**
	 * Sets the bytes written.
	 *
	 * @param bytesWritten the new bytes written
	 */
	public void setBytesWritten(int bytesWritten);
}
