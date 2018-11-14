
package scp;

/**
 *	SCPDisconnect
 *
 *	This class handle SCP Acknowledge message for disconnect.
 *
 *	@author Kelvin (contact@kelvinyin.com)
 */

 public class SCP {

 	/**
 	 *	SCP Message type
 	 */
 	public static final int CONNECT 			  = 1;
 	public static final int ACCEPT 				  = 2;
 	public static final int ACKNOWLEDGE           = 3;
 	public static final int CHAT 				  = 4;
 	public static final int REJECT 				  = 5;
 	public static final int DISCONNECT 			  = 6;
 	public static final int DISCONNECTACKNOWLEDGE = 7;

 	/**
 	 *	Detected message type
 	 */
 	private int messageType;

 	/**
 	 *	SCP Message
 	 */
 	private String message;

 	/**
 	 *	Each line of message
 	 */
 	private String lines[];

 	/**
 	 *	Error Message
 	 */
 	private String errorMessage;

 	/**
 	 *	Empty constructor
 	 *
 	 *	Initialise member variables
 	 */
 	public SCP() {
 		this.messageType = 0;
 		this.message = "";
 		this.errorMessage = "";
 	}

 	/**
 	 *	Constructor
 	 *
 	 *	This constructor accept scp message.
 	 *  The message is read and detect the type of message.
 	 *
 	 *	@param _message Message to be read
 	 */
 	public SCP(String _message) {
 		this.feedMessage(_message);
 	}

 	/**
 	 *	This method read the message and detect the type
 	 *
 	 *	@param _message Message to be read
 	 */
 	public void feedMessage(String _message) {
 		this.errorMessage = "";
 		this.message = _message;

 		// Get each lines of message
 		this.lines = this.message.trim().split("\r\n|\r|\n");

 		if (this.isValid() == true) {

	 		// Get first line of message
	 		String fline = this.lines[0];

	 		// Get SCP message type
	 		String type = fline.trim().split(" ")[1].toLowerCase();

	 		if (type.equals("connect")) {
	 			this.messageType = SCP.CONNECT;
	 		} else if (type.equals("accept")) {
	 			this.messageType = SCP.ACCEPT;
	 		} else if (type.equals("acknowledge")) {
	 			this.messageType = SCP.ACKNOWLEDGE;
	 		} else if (type.equals("chat")) {
	 			this.messageType = SCP.CHAT;
	 		} else if (type.equals("disconnect")) {
	 			this.messageType = SCP.DISCONNECT;
	 		} else if (type.equals("reject")) {
	 			this.messageType = SCP.REJECT;
	 		}
 		} else {
 			this.messageType = 0;
 		}
 	}

 	/**
 	 *	This method is to get the message type
 	 *
 	 *	@return Type of message
 	 */
 	public int getMessageType() {
 		return this.messageType;
 	}

 	/**
 	 *	This method create SCPConnect object and return it
 	 *
 	 *	@return SCPConnect object
 	 */
 	public SCPConnect getConnect() {

 		// Extract values from message
 		// Ignore first and last line: SCP CONNECT and SCP END

 		// Server address
 		String serverAddress = this.lines[1].trim().split(" ")[1];

 		// Server port
 		int port = Integer.parseInt(this.lines[2].trim().split(" ")[1]);

 		// Requested time
 		long requestedTime = Long.parseLong(this.lines[3].trim().split(" ")[1]);

 		// Username
 		String username = this.lines[4].trim().split(" ")[1].replaceAll("\"", "");

 		// Create SCPConnect object
		return (new SCPConnect(serverAddress, port, requestedTime, username));

 	}

 	/**
 	 *	This method create SCPReject object and return it
 	 *
 	 *	@return SCPReject object
 	 */
 	public SCPReject getReject() {

 		// Extract values from message
 		// Ignore first and last line: SCP CONNECT and SCP END

 		// Time Differential
 		long timeDifferential = Long.parseLong(this.lines[1].trim().split(" ")[1]);

 		// Remote address
 		String remoteAddress = this.lines[2].trim().split(" ")[1];

 		// Create SCPReject
 		return (new SCPReject(timeDifferential, remoteAddress));
 	}

 	/**
 	 *	This method create SCPAccept object and return it
 	 *
 	 *	@return SCPAccept object
 	 */
 	public SCPAccept getAccept() {

 		// Extract values from message
 		// Ignore first and last line: SCP CONNECT and SCP END

 		// Username
 		String username = this.lines[1].trim().split(" ")[1].replaceAll("\"", "");

 		// Client Address
 		String clientAddress = this.lines[2].trim().split(" ")[1];

 		// Client Port
 		int clientPort = Integer.parseInt(this.lines[3].trim().split(" ")[1]);

 		// Create SCPAccept
 		return (new SCPAccept(username, clientAddress, clientPort));
 	}

 	/**
 	 *	This method create SCPAcknowledge object and return it
 	 *
 	 *	@return SCPAcknowledge object
 	 */
 	public SCPAcknowledge getAcknowledge() {

 		// Extract values from message
 		// Ignore first and last line: SCP CONNECT and SCP END

 		// Check if it is acknowledge for disconnect
 		if (this.lines[1].equals("SCP END") == true) {
 			return (new SCPAcknowledge(SCP.DISCONNECT));
 		}

 		// Acknowledge for acceptance

 		// Username
 		String username = this.lines[1].trim().split(" ")[1].replaceAll("\"", "");

 		// Server Address
 		String serverAddress = this.lines[2].trim().split(" ")[1];

 		// Server Port
 		int serverPort = Integer.parseInt(this.lines[3].trim().split(" ")[1]);

 		return (new SCPAcknowledge(username, serverAddress, serverPort, SCP.ACCEPT));
 	}

 	/**
 	 *	This method create SCPChat object and return it
 	 *
 	 *	@return SCPChat object
 	 */
 	public SCPChat getChat() {

 		// Extract values from message
 		// Ignore first and last line: SCP CONNECT and SCP END

 		// Remote address
 		String remoteAddress = this.lines[1].trim().split(" ")[1];

 		// Remote port
 		int remotePort = Integer.parseInt(this.lines[2].trim().split(" ")[1]);

 		// Message
 		String message = this.lines[6];

 		return (new SCPChat(remoteAddress, remotePort, message));
 	}

 	/**
 	 *	This method creates SCPDisconnect object and return it
 	 *
 	 *	@return SCPDisconnect object
 	 */
 	public SCPDisconnect getDisconnect() {

 		// Extract values from message
 		// Ignore first and last line: SCP CONNECT and SCP END

 		return (new SCPDisconnect());
 	}

 	/**
 	 *	This method validate SCP Message
 	 *
 	 *	@return True if it is valid, false otherwise
 	 */
 	public boolean isValid() {

 		if (this.lines.length >= 2) {

 			// Check SCP opening and end format
 			if (this.lines[0].split(" ").length == 2 && this.lines[0].split(" ")[0].equals("SCP") && this.lines[this.lines.length - 1].equals("SCP END")) {

 				// Message type
 				String type = this.lines[0].split(" ")[1];

 				// Validate SCP Message

 				if (type.equals("CONNECT")) {

 					// Connect message must have four parameters in order as follow:
 					// 1) SERVERADDRESS
 					// 2) SERVERPORT
 					// 3) REQUESTCREATE
 					// 4) USERNAME
 					String connectParam[] = {"SERVERADDRESS", "SERVERPORT", "REQUESTCREATED", "USERNAME"};

 					if ((this.lines.length - 2) == 4) {

 						// Check if parameter are in order
 						for (int i = 0; i < (this.lines.length - 2); i++) {
 							if (this.lines[i + 1].split(" ")[0].equals(connectParam[i]) == false) {
 								
 								this.errorMessage = "ERROR: Your SCP CONNECT message is not formatted correctly\n\n";
 								this.errorMessage += "The following message was received:\n";
 								this.errorMessage += this.message + "\n\n";
 								this.errorMessage += "CONNECT Parameters are not in order.\n";
 								this.errorMessage += "CONNECT parameter 1 must be SERVERADDRESS\n";
 								this.errorMessage += "CONNECT parameter 2 must be SERVERPORT\n";
 								this.errorMessage += "CONNECT parameter 3 must be REQUESTCREATED\n";
 								this.errorMessage += "CONNECT parameter 4 must be USERNAME";

 								return false;
 							}
 						}

 						// Check if values missing
 						String connectMissingMsg = "";

 						for(int i = 0; i < (this.lines.length - 2); i++) {
 							if (this.lines[i + 1].split(" ").length != 2) {
 								if (connectMissingMsg.equals("") == false) {
 									connectMissingMsg += "\n";
 								}

 								connectMissingMsg += "The value of parameter " + connectParam[i] + " is expected.";
 							}
 						}

 						if (connectMissingMsg.equals("") == false) {

 							this.errorMessage = "ERROR: Your SCP CONNECT message is not formatted correctly\n\n";
 							this.errorMessage += "The following message was received:\n";
 							this.errorMessage += this.message + "\n\n";
 							this.errorMessage += "The value of parameters are missing:\n";
 							this.errorMessage += connectMissingMsg;

 							return false;
 						}

 						// No error at this point
 						return true;

 					} else {

 						this.errorMessage = "ERROR: Your SCP CONNECT message is not formatted correctly\n\n";
 						this.errorMessage += "The following message was received:\n";
 						this.errorMessage += this.message + "\n\n";
 						this.errorMessage += "Expected 4 parameters listed below:\n";
 						this.errorMessage += "SERVERADDRESS\nSERVERPORT\nREQUESTCREATED\nUSERNAME";

 						return false;

 					}
 				} else if (type.equals("REJECT")) {

 					// Reject message must have four parameters in order as follow:
 					// 1) TIMEDIFFERENTIAL
 					// 2) REMOTEADDRESS
 					String rejectParam[] = {"TIMEDIFFERENTIAL", "REMOTEADDRESS"};

 					if ((this.lines.length - 2) == 2) {

 						// Check if parameter are in order
 						for (int i = 0; i < (this.lines.length - 2); i++) {
 							if (this.lines[i + 1].split(" ")[0].equals(rejectParam[i]) == false) {
 								
 								this.errorMessage = "ERROR: Your SCP REJECT message is not formatted correctly\n\n";
 								this.errorMessage += "The following message was received:\n";
 								this.errorMessage += this.message + "\n\n";
 								this.errorMessage += "REJECT Parameters are not in order.\n";
 								this.errorMessage += "REJECT parameter 1 must be TIMEDIFFERENTIAL\n";
 								this.errorMessage += "REJECT parameter 2 must be REMOTEADDRESS";

 								return false;
 							}
 						}

 						// Check if values missing
 						String rejectMissingMsg = "";

 						for(int i = 0; i < (this.lines.length - 2); i++) {
 							if (this.lines[i + 1].split(" ").length != 2) {
 								if (rejectMissingMsg.equals("") == false) {
 									rejectMissingMsg += "\n";
 								}

 								rejectMissingMsg += "The value of parameter " + rejectParam[i] + " is expected.";
 							}
 						}

 						if (rejectMissingMsg.equals("") == false) {

 							this.errorMessage = "ERROR: Your SCP REJECT message is not formatted correctly\n\n";
 							this.errorMessage += "The following message was received:\n";
 							this.errorMessage += this.message + "\n\n";
 							this.errorMessage += "The value of parameters are missing:\n";
 							this.errorMessage += rejectMissingMsg;

 							return false;
 						}

 						// No error at this point
 						return true;

 					} else {

 						this.errorMessage = "ERROR: Your SCP REJECT message is not formatted correctly\n\n";
 						this.errorMessage += "The following message was received:\n";
 						this.errorMessage += this.message + "\n\n";
 						this.errorMessage += "Expected 2 parameters listed below:\n";
 						this.errorMessage += "TIMEDIFFERENTIAL\nEMOTEADDRESS";

 						return false;

 					}
 				} else if (type.equals("ACCEPT")) {

 					// Accept message must have four parameters in order as follow:
 					// 1) USERNAME
 					// 2) CLIENTADDRESS
 					// 3) CLIENTPORT
 					String acceptParam[] = {"USERNAME", "CLIENTADDRESS", "CLIENTPORT"};

 					if ((this.lines.length - 2) == 3) {

 						// Check if parameter are in order
 						for (int i = 0; i < (this.lines.length - 2); i++) {
 							if (this.lines[i + 1].split(" ")[0].equals(acceptParam[i]) == false) {
 								
 								this.errorMessage = "ERROR: Your SCP ACCEPT message is not formatted correctly\n\n";
 								this.errorMessage += "The following message was received:\n";
 								this.errorMessage += this.message + "\n\n";
 								this.errorMessage += "ACCEPT Parameters are not in order.\n";
 								this.errorMessage += "ACCEPT parameter 1 must be USERNAME\n";
 								this.errorMessage += "ACCEPT parameter 2 must be CLIENTADDRESS\n";
 								this.errorMessage += "ACCEPT parameter 3 must be CLIENTPORT";

 								return false;
 							}
 						}

 						// Check if values missing
 						String acceptMissingMsg = "";

 						for(int i = 0; i < (this.lines.length - 2); i++) {
 							if (this.lines[i + 1].split(" ").length != 2) {
 								if (acceptMissingMsg.equals("") == false) {
 									acceptMissingMsg += "\n";
 								}

 								acceptMissingMsg += "The value of parameter " + acceptParam[i] + " is expected.";
 							}
 						}

 						if (acceptMissingMsg.equals("") == false) {

 							this.errorMessage = "ERROR: Your SCP ACCEPT message is not formatted correctly\n\n";
 							this.errorMessage += "The following message was received:\n";
 							this.errorMessage += this.message + "\n\n";
 							this.errorMessage += "The value of parameters are missing:\n";
 							this.errorMessage += acceptMissingMsg;

 							return false;
 						}

 						// No error at this point
 						return true;

 					} else {

 						this.errorMessage = "ERROR: Your SCP ACCEPT message is not formatted correctly\n\n";
 						this.errorMessage += "The following message was received:\n";
 						this.errorMessage += this.message + "\n\n";
 						this.errorMessage += "Expected 3 parameters listed below:\n";
 						this.errorMessage += "USERNAME\nCLIENTADDRESS\nCLIENTPORT";

 						return false;

 					}
 				} else if (type.equals("ACKNOWLEDGE")) {

 					// Acknowledge for disconnect doesn't have any parameter
 					if (this.lines[1].equals("SCP END")) {
 						return true;
 					}

 					// Accept message must have four parameters in order as follow:
 					// 1) USERNAME
 					// 2) SERVERADDRESS
 					// 3) SERVERPORT
 					String acknowledgeParam[] = {"USERNAME", "SERVERADDRESS", "SERVERPORT"};

 					if ((this.lines.length - 2) == 3) {

 						// Check if parameter are in order
 						for (int i = 0; i < (this.lines.length - 2); i++) {
 							if (this.lines[i + 1].split(" ")[0].equals(acknowledgeParam[i]) == false) {
 								
 								this.errorMessage = "ERROR: Your SCP ACKNOWLEDGE message is not formatted correctly\n\n";
 								this.errorMessage += "The following message was received:\n";
 								this.errorMessage += this.message + "\n\n";
 								this.errorMessage += "ACKNOWLEDGE Parameters are not in order.\n";
 								this.errorMessage += "ACKNOWLEDGE parameter 1 must be USERNAME\n";
 								this.errorMessage += "ACKNOWLEDGE parameter 2 must be SERVERADDRESS\n";
 								this.errorMessage += "ACKNOWLEDGE parameter 3 must be SERVERPORT";

 								return false;
 							}
 						}

 						// Check if values missing
 						String acknowledgeMissingMsg = "";

 						for(int i = 0; i < (this.lines.length - 2); i++) {
 							if (this.lines[i + 1].split(" ").length != 2) {
 								if (acknowledgeMissingMsg.equals("") == false) {
 									acknowledgeMissingMsg += "\n";
 								}

 								acknowledgeMissingMsg += "The value of parameter " + acknowledgeParam[i] + " is expected.";
 							}
 						}

 						if (acknowledgeMissingMsg.equals("") == false) {

 							this.errorMessage = "ERROR: Your SCP ACKNOWLEDGE message is not formatted correctly\n\n";
 							this.errorMessage += "The following message was received:\n";
 							this.errorMessage += this.message + "\n\n";
 							this.errorMessage += "The value of parameters are missing:\n";
 							this.errorMessage += acknowledgeMissingMsg;

 							return false;
 						}

 						// No error at this point
 						return true;

 					} else {

 						this.errorMessage = "ERROR: Your SCP ACKNOWLEDGE message is not formatted correctly\n\n";
 						this.errorMessage += "The following message was received:\n";
 						this.errorMessage += this.message + "\n\n";
 						this.errorMessage += "Expected 3 parameters listed below:\n";
 						this.errorMessage += "USERNAME\nSERVERADDRESS\nSERVERPORT";

 						return false;

 					}
 				} else if (type.equals("CHAT")) {

 					// Chat message must have four parameters in order as follow:
 					// 1) REMOTEADDRESS
 					// 2) REMOTEPORT
 					// 3) MESSAGECONTENT
 					String chatParam[] = {"REMOTEADDRESS", "REMOTEPORT", "MESSAGECONTENT"};

 					if ((this.lines.length - 2) >= 3) {

 						// Check if parameter are in order
 						for (int i = 0; i < 3; i++) {
 							if (this.lines[i + 1].split(" ")[0].equals(chatParam[i]) == false) {
 								
 								this.errorMessage = "ERROR: Your SCP CHAT message is not formatted correctly\n\n";
 								this.errorMessage += "The following message was received:\n";
 								this.errorMessage += this.message + "\n\n";
 								this.errorMessage += "CHAT Parameters are not in order.\n";
 								this.errorMessage += "CHAT parameter 1 must be REMOTEADDRESS\n";
 								this.errorMessage += "CHAT parameter 2 must be REMOTEPORT\n";
 								this.errorMessage += "CHAT parameter 3 must be MESSAGECONTENT";

 								return false;
 							}
 						}

 						// Check if values missing
 						String chatMissingMsg = "";

 						for(int i = 0; i < 2; i++) {
 							if (this.lines[i + 1].split(" ").length != 2) {
 								if (chatMissingMsg.equals("") == false) {
 									chatMissingMsg += "\n";
 								}

 								chatMissingMsg += "The value of parameter " + chatParam[i] + " is expected.";
 							}
 						}

 						// Check for message content
 						if (this.lines[4].equals("") == false || this.lines[5].equals("") == false) {

 							chatMissingMsg += "The value of parameter MESSAGECONTENT is expected to have line feed (2 new lines).";

 						} else {

 							if (this.lines[6].equals("SCP END")) {
 								chatMissingMsg += "The value of parameter MESSAGECONTENT is expected to have message content after line feed (2 new lines).";
 							}

 						}

 						if (chatMissingMsg.equals("") == false) {

 							this.errorMessage = "ERROR: Your SCP CHAT message is not formatted correctly\n\n";
 							this.errorMessage += "The following message was received:\n";
 							this.errorMessage += this.message + "\n\n";
 							this.errorMessage += "The value of parameters are missing:\n";
 							this.errorMessage += chatMissingMsg;

 							return false;
 						}

 						// No error at this point
 						return true;

 					} else {

 						this.errorMessage = "ERROR: Your SCP CHAT message is not formatted correctly\n\n";
 						this.errorMessage += "The following message was received:\n";
 						this.errorMessage += this.message + "\n\n";
 						this.errorMessage += "Expected 4 parameters listed below:\n";
 						this.errorMessage += "REMOTEADDRESS\nREMOTECLIENT\nMESSAGECONTENT";

 						return false;

 					}
 				} else if (type.equals("DISCONNECT")) {
 					if (this.lines[1].equals("SCP END")) {
 						return true;
 					} else {
 						this.errorMessage = "ERROR: Your SCP DISCONNECT message is not formatted correctly\n\n";
 						this.errorMessage += "The following message was received:\n";
 						this.errorMessage += this.message + "\n\n";
 						this.errorMessage += "DISCONNECT message does not require any parameter.";
 						return false;
 					}
 				} else {

 					this.errorMessage = "ERROR: Your SCP message is not formatted correctly\n\n";
 					this.errorMessage += "The following message was received:\n";
 					this.errorMessage += this.message + "\n\n";
 					this.errorMessage += "Unknown message type: " + type;

 					return false;
 				}

 			}

 		}
		
		this.errorMessage = "SCP Message Format Error\n\n";
		this.errorMessage += "The following message was received:\n";
		this.errorMessage += this.message + "\n\n";
		this.errorMessage += "SCP message should start with SCP [MESSAGE TYPE] and end with SCP END";
		return false;
 	}

 	/**
 	 *	Get error message
 	 *
 	 *	@return Error message
 	 */
 	public String getErrorMessage() {
 		return this.errorMessage;
 	}


 }