
package scp;

/**
 *	SCPChat
 *
 *	This class handle SCP Chat message.
 *
 *	@author Kelvin (contact@kelvinyin.com)
 */

public class SCPChat {

	/**
	 *	Remote hostname
	 */
	private String remoteAddress;

	/**
	 *	Remote port number
	 */
	private int remotePort;

	/**
	 *	Message content sent from client and server.
	 */
	private String messageContent;

	/**
	 *	Empty constructor
	 *
	 *	Initialise all member variables
	 */
	public SCPChat() {
		this.remoteAddress = "";
		this.remotePort = 0;
		this.messageContent = "";
	}

	/**
	 *	Constructor with value of member variables
	 *
	 *	Initialise member variables.
	 *
	 *	@param _remoteAddress  Hostname of remote
	 *	@param _remotePort 	   Port number of remote
	 *	@param _messageContent Message sent from client or server
	 */
	public SCPChat(String _remoteAddress, int _remotePort, String _messageContent) {
		this.remoteAddress = _remoteAddress;
		this.remotePort = _remotePort;
		this.messageContent = _messageContent;
	}

	/**
	 *	Getter
	 */

	/**
	 *	This method is to get hostname
	 *
	 *	@return Remote hostname
	 */
	public String getRemoteAddress() {
		return this.remoteAddress;
	}

	/**
	 *	This method is to get the port number of remote
	 *
	 *	@return Remote Port number
	 */
	public int getRemotePort() {
		return this.remotePort;
	}

	/**
	 *	This method is to get message Content
	 *
	 *	@return Message content
	 */	
	public String getMessageContent() {
		return this.messageContent;
	}

	/**
	 *	Setter
	 */

	/**
	 *	This method is to set the hostname of remote
	 *
	 *	@param _remoteAddress Hostname of remote
	 */
	public void setRemoteAddress(String _remoteAddress) {
		this.remoteAddress = _remoteAddress;
	}

	/**
	 *	This method is to set the port number of remote
	 *
	 *	@param _remotePort Port number of remote
	 */
	public void setRemotePort(int _remotePort) {
		this.remotePort = _remotePort;
	}

	/**
	 *	This method is to set the message content
	 *
	 *	@param _messageContent message sent from client or server.
	 */
	public void setMessageContent(String _messageContent) {
		this.messageContent = _messageContent;
	}

	/**
	 *	Magic Method
	 */	

	public String toString() {
		String message = "";

		message += "SCP CHAT" 							    + "\n";
		message += "REMOTEADDRESS " + this.remoteAddress 	+ "\n";
		message += "REMOTEPORT " 	+ this.remotePort 		+ "\n";

		message += "MESSAGECONTENT" 						+ "\n";
		message += "\n\n"; // Linefeed
		message += this.messageContent 						+ "\n";

		message += "SCP END";

		return message;
	}
}