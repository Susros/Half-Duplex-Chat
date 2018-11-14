
package scp;

/**
 *	SCPConnect
 *
 *	This class handle SCP Connect message.
 *
 *	@author Kelvin (contact@kelvinyin.com)
 */

public class SCPConnect {

	/**
	 *	Server host name.
	 */
	private String serverAddress;

	/**
	 *	Server port number.
	 */
	private int serverPort;

	/**
	 *	Connection requested time in millisecond.
	 */
	private long requestedTime;

	/**
	 *	Name of user
	 */
	private String username;

	/**
	 *	Constructor
	 *
	 *	This constructor initialise all member variables.
	 */
	public SCPConnect() {
		this.serverAddress = "";
		this.serverPort = 0;
		this.requestedTime = 0;
		this.username = "";
	}

	/**
	 *	Constructor with values of host name and port number
	 *
	 *	Initialise all member variables
	 *
	 *	@param _serverAddress Host name of server
	 *	@param _port 		  Port number that server is listening to
	 *	@param _requestedTime Connection requested time in millisecond
	 *	@param _username      Client username
	 */
	public SCPConnect(String _serverAddress, int _port, long _requestedTime, String _username) {
		this.serverAddress = _serverAddress;
		this.serverPort = _port;
		this.requestedTime = _requestedTime;
		this.username = _username;
	}

	/**
	 *	Getter
	 */

	/**
	 *	This method is to get server host name
	 *
	 *	@return Host name of server
	 */
	public String getServerAddress() {
		return this.serverAddress;
	}

	/**
	 *	This method is to get port number that server is listening to
	 *	
	 *	@return Port number that server is listening to
	 */
	public int getServerPort() {
		return this.serverPort;
	}

	/**
	 *	This method is to get the requested time
	 *
	 *	@return Connection requested time in millisecond.
	 */
	public long getRequestedTime() {
		return this.requestedTime;
	}

	/**
	 *	This method is to get the name of user.
	 *
	 *	@return Name of user
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 *	Setter
	 */

	/**
	 *	This method is to set the host name of server
	 *
	 *	@param _serverAddress Host name of server
	 */
	public void setServerAddress(String _serverAddress) {
		this.serverAddress = _serverAddress;
	}

	/**	
	 *	This method is to set the port number that server is listening to
	 *
	 *	@param _port Port number that server is listening to
	 */
	public void setServerPort(int _port) {
		this.serverPort = _port;
	}

	/**
	 *	This method is to set the requested time
	 *
	 *	@param _requestedTime Connection requested time in millisecond
	 */
	public void setRequestedTime(long _requestedTime) {
		this.requestedTime = _requestedTime;
	}

	/**
	 *	This method is to set the name of user
	 *
	 *	@param _username Name of user
	 */
	public void setUsername(String _username) {
		this.username = _username;
	}

	/**
	 *	Magic methods
	 */

	/**
	 *	Stringify this class
	 */
	public String toString() {
		String message = "";

		String quoteUsername = "\"" + this.username + "\"";

		message += "SCP CONNECT"     						+ "\n";
		message += "SERVERADDRESS "  + this.serverAddress 	+ "\n";
		message += "SERVERPORT "     + this.serverPort 		+ "\n";
		message += "REQUESTCREATED " + this.requestedTime 	+ "\n";
		message += "USERNAME "       + quoteUsername 		+ "\n";
		message += "SCP END";

		return message;
	}
}