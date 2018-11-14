
package scp;

/**
 *	SCPDisconnect
 *
 *	This class handle SCP Disconnect message.
 *
 *	@author Kelvin (contact@kelvinyin.com)
 */

public class SCPDisconnect {

	/**
	 *	Constructor
	 */
	public SCPDisconnect() {

	}

	/**
	 *	Magic Method
	 */
	public String toString() {
		String message = "";

		message += "SCP DISCONNECT" + "\n";
		message += "SCP END";

		return message;
	}
}