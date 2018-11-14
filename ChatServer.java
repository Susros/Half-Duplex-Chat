
/**
 *	ChatServer
 *
 *	Host the server for ChatClient
 *
 *	@author Kelvin (contact@kelvinyin.com)
 */

import scp.*;
import java.io.*;
import java.net.*;

public class ChatServer extends Thread {

	/**
	 *	Server socket
	 */
	private ServerSocket serverSocket;

	/**
	 *	Welcome message from server
	 */
	private String welcomeMessage;

	/**
	 *	Client username
	 */
	private String clientUsername;

	/**
	 *	Constructor
	 *
	 *	Create server socket connection
	 *
	 *	@param hostname Hostname of server
	 *	@param port     Port number to listen to
	 *	@param msg 		Welcome message
	 */
	public ChatServer(String hostname, int port, String msg) throws IOException {
		this.serverSocket = new ServerSocket(port, 1, InetAddress.getByName(hostname));
		this.welcomeMessage = msg;
	}

	/**
	 *	When thread starts
	 */
	public void run() {
		while(true) {
			try {

				// Let the host knows that server is waiting for client
				System.out.println("Waiting for client user on port " + serverSocket.getLocalPort());

				// Get client socket when connected
				Socket socket = serverSocket.accept();

				// Output stream for Client output
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

				// Input stream from Client
				DataInputStream in = new DataInputStream(socket.getInputStream());
				//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				// Keyboard input
				BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

				// Check message type
				int messageType = 0;

				while(true) {

					// SCP Message
					String SCPMessage = in.readUTF();

					// Prepare SCP
					SCP scp = new SCP(SCPMessage);

					if (scp.getMessageType() == SCP.CONNECT) {

						// Get SCP Connect Object
						SCPConnect scpConnect = scp.getConnect();

						// Check the time differential
						if ((System.currentTimeMillis() - scpConnect.getRequestedTime()) > 5000) {

							// SCP Reject message
							SCPReject scpReject = new SCPReject((System.currentTimeMillis() - scpConnect.getRequestedTime()), scpConnect.getServerAddress());

							// Send the reject message
							out.writeUTF(scpReject.toString());

							// Close socket
							socket.close();

							// Break this loop
							break;
						} 

						// SCP Accept message
						SCPAccept scpAccept = new SCPAccept(scpConnect.getUsername(), scpConnect.getServerAddress(), scpConnect.getServerPort());

						// Send the acceptance message
						out.writeUTF(scpAccept.toString());

						// Tell the host that client connection has been accepted
						System.out.println("");
						System.out.println("\"" + scpConnect.getUsername() + "\" joined the chat room.");
						System.out.println("Waiting for acknowledge...");


					} else if (scp.getMessageType() == SCP.ACKNOWLEDGE) {

						// Client has acknowledged.
						SCPAcknowledge scpAcknowledge = scp.getAcknowledge();

						// Acknowledge for acceptance
						if (scpAcknowledge.getMessageType() == SCP.ACCEPT) {

							// Save the client username
							this.clientUsername = scpAcknowledge.getUsername();
							
							// Initialise the chat with welcom message
							SCPChat scpChat = new SCPChat(scpAcknowledge.getServerAddress(), scpAcknowledge.getServerPort(), this.welcomeMessage);

							// Send the chat
							out.writeUTF(scpChat.toString());

							// Tell host to wait for the client response
							System.out.println("");
							System.out.println("Chat has been initialised with the message: \"" + this.welcomeMessage + "\"");
							System.out.println("Waiting for " + scpAcknowledge.getUsername() + "'s response...");

						} else {

							// Close socket
							socket.close();
						}

					} else if (scp.getMessageType() == SCP.CHAT) {

						// Get the chat
						SCPChat scpChat = scp.getChat();

						// Print out what server said
						System.out.println("");
						System.out.println("\"" + this.clientUsername + "\" : " + scpChat.getMessageContent());

						// Ask client response
						System.out.print("\"You\" : ");

						String response = keyboard.readLine();

						// Check for keyword "DISCONNECT"
						if (response.equals("DISCONNECT")) {
							
							// Send disconnect message
							out.writeUTF((new SCPDisconnect()).toString());

							// Tells client to wait for acknowledge
							System.out.println("");
							System.out.println("Waiting for " + this.clientUsername + "'s acknowledge...");

						} else {

							// Create chat message for response
							SCPChat responseChat = new SCPChat(scpChat.getRemoteAddress(), scpChat.getRemotePort(), response);

							// Send the response chat to server
							out.writeUTF(responseChat.toString());

							// Tell client to wait for the server response
							System.out.println("");
							System.out.println("Waiting for " + this.clientUsername + "'s response...");
						}

					} else if (scp.getMessageType() == SCP.DISCONNECT) {

						// Get the disconnect message
						SCPDisconnect scpDisconnect = scp.getDisconnect();

						// Tell host that client is disconnecting
						System.out.println("");
						System.out.println(this.clientUsername + " is disconnecting.");
						System.out.print("Please press enter to acknowledge.");
						keyboard.readLine();

						// Send disconnect acknowledge
						out.writeUTF((new SCPAcknowledge(SCP.DISCONNECT)).toString());

						// Close the client socket
						socket.close();
					} else {

						System.out.println("");
						System.out.println(scp.getErrorMessage());
						System.out.println("");
						
						socket.close();
						serverSocket.close();
						break;

					}
				}

			} catch(EOFException e) {
				System.out.println("");
				System.out.println("Disconnected.");
				System.out.println("");

			} catch(SocketException e) {

				if (e.getMessage().equals("Socket closed")) {
					System.out.println("");
					System.out.println("Disconnected.");
					System.out.println("");
				}

			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		// Default hostname
		String hostname = "localhost";

		// Default port number
		int port = 3400;

		// Default welcom message
		String msg = "Welcome to SCP";

		// From arguments
		if (args.length == 3) {
			hostname = args[0];
			port = Integer.parseInt(args[1]);
			msg = args[2];
		}

		try {

			// Start Chat Server
			Thread t = new ChatServer(hostname, port, msg);
			t.start();

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}