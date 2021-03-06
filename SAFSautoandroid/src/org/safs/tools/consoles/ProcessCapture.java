/** Copyright (C) SAS Institute All rights reserved.
 ** General Public License: http://www.opensource.org/licenses/gpl-license.php
 **/
package org.safs.tools.consoles;

import java.lang.Process;

/**
 * Attempts to capture out and err streams from a process for use by other processes.
 * The process data is available via a Vector retrieved from getData().<br>
 * By default, this subclass disables the out and err streams from appearing in the debug/console output.
 * <p>
 * This subclass writes to org.safs.Log (SAFS Debug Log) instead of to System.out 
 * <p>
 * Normal usage might be something like below:
 * <pre> 
 *	Process process = runtime.exec(procstr);
 *	ProcessCapture console = new ProcessCapture(process);
 *	Thread athread = new Thread(console);
 *	athread.start();
 *  //we can wait until process is finished
 *	try{ athread.join();}catch(InterruptedException x){;}
 *	console.shutdown();//precaution
 *	Vector data = console.getData();
 * </pre> 
 * Alternatively, we can autostart the capture thread. We don't have to wait for 
 * it to end if we don't want to.  The autostarted thread can be acquired, though:
 * <pre> 
 *	Process process = runtime.exec(procstr);
 *	ProcessCapture console = new ProcessCapture(process, null, true, true);
 *  //we can wait until process is finished if we want
 *	try{ console.thread.join();}catch(InterruptedException x){;}
 *	Vector data = console.getData();
 *	console.shutdown();//precautionary force shutdown
 * </pre> 
 * Data for the Error and Output streams is captured into the single Vector storage.
 * The Error stream data is prefixed with the ERR_PREFIX, while the Output data is 
 * prefixed with the OUT_PREFIX.
 * <p>
 * In the latter example the out and err streams will also appear in any active debug() sink/console.
 * 
 * @author canagl
 */
public class ProcessCapture extends GenericProcessCapture{

	
	/**
	 * Default Constructor for ProcessCapture.
	 * This sets up a default console that does not monitor any secondary processes,  
	 * does NOT autostart its own capture thread, and does not copy out and err streams 
	 * to the debug() sink/console.
	 * @param process to capture IO in, out, and err streams.
	 * @see #setShowOutStream(boolean)
	 * @see #setShowErrStream(boolean)
	 */
	public ProcessCapture(Process process) {
		this(process, null, false);
	}

	/**
	 * Alternative constructor suggesting the IO thread should remain open as long as 
	 * a secondary process/pid remains running.  This instance also does not copy out and err 
	 * streams to the debug() sink/console.
	 * @param process -- to capture IO in, out, and err streams.
	 * @param monitor -- optional secondary process name or pid to monitor for continued 
	 * IO output.
	 * @param autostart -- true if we should automatically start the separate capture thread.
	 * @see #setShowOutStream(boolean)
	 * @see #setShowErrStream(boolean)
	 */
	public ProcessCapture(Process process, String monitor, boolean autostart) {
		this(process, monitor, autostart, false);
	}
	
	/**
	 * Alternative constructor suggesting the IO thread should remain open as long as 
	 * a secondary process/pid remains running.  This constructor allows the caller to change 
	 * the default behavior for the out and err streams being written to the debug() sink/console.
	 * @param process -- to capture IO in, out, and err streams.
	 * @param monitor -- optional secondary process name or pid to monitor for continued 
	 * IO output.
	 * @param autostart -- true if we should automatically start the separate capture thread.
	 * @param debug2console -- true if out and err streams should appear in the debug sink/console.
	 * The streams do NOT appear in the debug sink/console by default.
	 */
	public ProcessCapture(Process process, String monitor, boolean autostart, boolean debug2console) {
		super(process, monitor, autostart, debug2console);
	}
	
}

