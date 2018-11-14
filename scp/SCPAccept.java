
package scp;

/**
 *	SCPAccept
 *
 *	This class handle SCP Accept message.
 *
 *	@author Kelvin (contact@kelvinyin.com)
 */

public class SCPAccept {

	/**
	 *	Name of user
	 */
	private String username;

	/**
	 *	Client hostname
	 */
	private String clientAddress;

	/**
	 *	Client port number
	 */
	private int clientPort;

	/**
	 *	Empty constructor
	 *
	 *	Initialise all member variables
	 */
	public SCPAccept() {
		this.username = "";
		this.clientAddress = "";
		this.clientPort = 0;
	}

	/**
	 *	Constructor with value of member variables
	 *
	 *	Initialise member variables.
	 *
	 *	@param _username 	  Name of user
	 *	@param _clientAddress Hostname of client
	 *	@param _clientPort 	  Port number of client
	 */
	public SCPAccept(String _username, String _clientAddress, int _clientPort) {
		this.username = _username;
		this.clientAddress = _clientAddress;
		this.clientPort = _clientPort;
	}

	/**
	 *	Getter
	 */

	/**
	 *	This method is to get username
	 *
	 *	@return Name of user
	 */	
	public String getUsername() {
		return this.username;
	}

	/**
	 *	This method is to get hostname
	 *
	 *	@return Client hostname
	 */
	public String getClientAddress() {
		return this.clientAddress;
	}

	/**
	 *	This method is to get the port number of client
	 *
	 *	@return Client Port number
	 */
	public int getClientPort() {
		return this.clientPort;
	}

	/**
	 *	Setter
	 */

	/**
	 *	This method is to set the name of user
	 *
	 *	@param _username Username of client
	 */
	public void setUsername(String _username) {
		this.username = _username;
	}

	/**
	 *	This method is to set the hostname of client
	 *
	 *	@param _clientAddress Hostname of client
	 */
	public void setClientAddress(String _clientAddress) {
		this.clientAddress = _clientAddress;
	}

	/**
	 *	This method is to set the port number of client
	 *
	 *	@param _clientPort Port number of client
	 */
	public void setClientPort(int _clientPort) {
		this.clientPort = _clientPort;
	}

	/**
	 *	Magic Method
	 */	

	public String toString() {
		String message = "";

		String quoteUsername = "\"" + this.username + "\"";

		message += "SCP ACCEPT" 							+ "\n";
		message += "USERNAME " 	    + quoteUsername  		+ "\n";
		message += "CLIENTADDRESS " + this.clientAddress 	+ "\n";
		message += "CLIENTPORT " 	+ this.clientPort 		+ "\n";
		message += "SCP END";

		return message;
	}
}