
package scp;

/**
 *	SCPReject
 *
 *	This class handle SCP Reject message.
 *
 *	@author Kelvin (contact@kelvinyin.com)
 */

public class SCPReject {

	/**
	 *	Difference between requested time and current time
	 */
	private long timeDifferential;

	/**
	 *	Requesting clients hostname
	 */
	private String remoteAddress;

	/**
	 *	Empty constructor
	 *
	 *	Initialise member variables
	 */
	public SCPReject() {
		this.timeDifferential = 0;
		this.remoteAddress = "";
	}

	/**
	 *	Constructor with value of member variables
	 *
	 *	Initialise all member variables
	 */
	public SCPReject(long _timeDifferential, String _remoteAddress) {
		this.timeDifferential = _timeDifferential;
		this.remoteAddress = _remoteAddress;
	}

	/**
	 *	Getter
	 */

	/**
	 *	This method is to get time differential
	 *	
	 *	@return Time differential value
	 */
	public long getTimeDifferential() {
		return this.timeDifferential;
	}

	/**
	 *	This method is to get remote address
	 *
	 *	@return Remote address value
	 */
	public String getRemoteAddress() {
		return this.remoteAddress;
	}

	/**
	 *	Setter
	 */

	/**
	 *	This method is to set time differential
	 *	
	 *	@param _timeDifferential Time difference between requested time and curernt time
	 */
	public void setTimeDifferential(long _timeDifferential) {
		this.timeDifferential = _timeDifferential;
	}

	/**
	 *	This method is to set remote address
	 *
	 *	@param _remoteAddress Clients hostname
	 */
	public void setRemoteAddress(String _remoteAddress) {
		this.remoteAddress = _remoteAddress;
	}

	/**
	 *	Magic Methods
	 */
	public String toString() {
		String message = "";

		message += "SCP REJECT" 								+ "\n";
		message += "TIMEDIFFERENTIAL " + this.timeDifferential 	+ "\n";
		message += "REMOTEADDRESS "	   + this.remoteAddress 	+ "\n";
		message += "SCP END";

		return message;
	}
}