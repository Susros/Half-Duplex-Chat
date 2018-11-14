
package scp;

/**
 *	SCPAcknowledge
 *
 *	This class handle SCP Acknowledge message for acceptance and disconnect.
 *
 *	@author Kelvin (contact@kelvinyin.com)
 */

public class SCPAcknowledge {

	/**
	 *	Name of user
	 */
	private String username;

	/**
	 *	Server hostname
	 */
	private String serverAddress;

	/**
	 *	Server port number
	 */
	private int serverPort;

	/**
	 *	What is it acknowledge for
	 */
	private int messageType;

	/**
	 *	Empty constructor
	 *
	 *	Initialise all member variables
	 */
	public SCPAcknowledge() {
		this.username = "";
		this.serverAddress = "";
		this.serverPort = 0;
		this.messageType = 0;
	}

	/**
	 *	Constructor with message type
	 *
	 *	Initialise member variables.
	 *
	 *	@param _messageType   Type of message that is acknowleging for
	 */
	public SCPAcknowledge(int _messageType) {
		this.username = "";
		this.serverAddress = "";
		this.serverPort = 0;
		this.messageType = _messageType;
	}

	/**
	 *	Constructor with value of member variables
	 *
	 *	Initialise member variables.
	 *
	 *	@param _username 	  Name of user
	 *	@param _serverAddress Hostname of server
	 *	@param _serverPort 	  Port number of server
	 *	@param _messageType   Type of message that is acknowleging for
	 */
	public SCPAcknowledge(String _username, String _serverAddress, int _serverPort, int _messageType) {
		this.username = _username;
		this.serverAddress = _serverAddress;
		this.serverPort = _serverPort;
		this.messageType = _messageType;
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
	 *	@return Server hostname
	 */
	public String getServerAddress() {
		return this.serverAddress;
	}

	/**
	 *	This method is to get the port number of server
	 *
	 *	@return Server Port number
	 */
	public int getServerPort() {
		return this.serverPort;
	}

	/**
	 *	This method is to get the type of message it is acknowledging for
	 *
	 *	@return int Type of message
	 */
	public int getMessageType() {
		return this.messageType;
	}

	/**
	 *	Setter
	 */

	/**
	 *	This method is to set the name of user
	 *
	 *	@param _username Username of server
	 */
	public void setUsername(String _username) {
		this.username = _username;
	}

	/**
	 *	This method is to set the hostname of server
	 *
	 *	@param _serverAddress Hostname of server
	 */
	public void setServerAddress(String _serverAddress) {
		this.serverAddress = _serverAddress;
	}

	/**
	 *	This method is to set the port number of server
	 *
	 *	@param _serverPort Port number of server
	 */
	public void setServerPort(int _serverPort) {
		this.serverPort = _serverPort;
	}

	/**
	 *	This method is to set the type of message it is acknowledging for
	 *
	 *	@param _messageType Type of message
	 */
	public void setMessageType(int _messageType) {
		this.messageType = _messageType;
	}

	/**
	 *	Magic Method
	 */	

	public String toString() {
		String message = "";

		String quoteUsername = "\"" + this.username + "\"";

		message += "SCP ACKNOWLEDGE" 						    + "\n";

		if (this.messageType == SCP.ACCEPT) {
			message += "USERNAME " 		+ quoteUsername 		+ "\n";
			message += "SERVERADDRESS " + this.serverAddress 	+ "\n";
			message += "SERVERPORT " 	+ this.serverPort 		+ "\n";
		}

		message += "SCP END";

		return message;
	}
}
