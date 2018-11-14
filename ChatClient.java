
/**
 *	ChatClient
 *
 *	Connect the ChatServer
 *
 *	@author Kelvin (contact@kelvinyin.com)
 */

import scp.*;
import java.io.*;
import java.net.*;

public class ChatClient {

	public static void main(String[] args) {

		// Default hostname
		String hostname = "localhost";

		// Default port number
		int port = 3400;

		// Check if hostname and port number are provided
		// from argument parameters

		if (args.length == 2) {
			hostname = args[0];
			port = Integer.parseInt(args[1]);
		}

		// Start socket network
		try {

			// Connecting to server
			System.out.println("");
			System.out.println("Connecting to " + hostname + ":" + port + "...");

			// Client socket
			Socket socket = new Socket(hostname, port);

			// Output stream for Server output
			//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			// Input stream from Server
			//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataInputStream in = new DataInputStream(socket.getInputStream());

			// Keyboard input
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

			// Get Username
			System.out.println("");
			System.out.print("Enter your username: ");
			String username = keyboard.readLine();

			// Tell client the request has been sent
			System.out.println("");
			System.out.println("Connection request has been sent.");
			System.out.println("Waiting for acceptance...");

			// Create SCPConnect
			SCPConnect scpConnect = new SCPConnect(hostname, port, System.currentTimeMillis(), username);

			// Send connection request
			out.writeUTF(scpConnect.toString());

			while(true) {

				// SCP Message
				String SCPMessage = in.readUTF();

				// Prepare SCP
				SCP scp = new SCP(SCPMessage);

				if (scp.getMessageType() == SCP.ACCEPT) {

					// Tell client that server has accepted
					System.out.println("");
					System.out.println("Your request has been accpeted for the chat.");
					System.out.print("Please press enter to acknowedge the acceptance.");
					keyboard.readLine();

					System.out.println("");
					System.out.println("Chat has began.");

					// Acknowledge message
					SCPAcknowledge scpAcknowledge = new SCPAcknowledge(username, hostname, port, SCP.ACCEPT);

					// Sent acknowledge to server
					out.writeUTF(scpAcknowledge.toString());

				} else if (scp.getMessageType() == SCP.ACKNOWLEDGE) {

					System.out.println("");
					System.out.println("Disconnected");
					System.out.println("");
					break;

				} else if (scp.getMessageType() == SCP.CHAT) {

					// Get the chat
					SCPChat scpChat = scp.getChat();

					// Print out what server said
					System.out.println("");
					System.out.println("\"" + scpChat.getRemoteAddress() + "\" : " + scpChat.getMessageContent());

					// Ask client response
					System.out.print("\"You\" : ");

					String response = keyboard.readLine();

					// Check for keyword "DISCONNECT"
					if (response.equals("DISCONNECT")) {
						
						// Send disconnect message
						out.writeUTF((new SCPDisconnect()).toString());

						// Tells client to wait for acknowledge
						System.out.println("");
						System.out.println("Waiting for " + scpChat.getRemoteAddress() + "'s acknowledge...");

					} else {

						// Create chat message for response
						SCPChat responseChat = new SCPChat(hostname, port, response);

						// Send the response chat to server
						out.writeUTF(responseChat.toString());

						// Tell client to wait for the server response
						System.out.println("");
						System.out.println("Waiting for " + scpChat.getRemoteAddress() + "'s response...");

					}


				} else if (scp.getMessageType() == SCP.DISCONNECT) {

					// Get the disconnect message
					SCPDisconnect scpDisconnect = scp.getDisconnect();

					// Tell host that client is disconnecting
					System.out.println("");
					System.out.println("\"" + hostname + "\" is disconnecting.");
					System.out.print("Please press enter to acknowledge.");
					keyboard.readLine();

					// Send disconnect acknowledge
					out.writeUTF((new SCPAcknowledge(SCP.DISCONNECT)).toString());


				} else if (scp.getMessageType() == SCP.REJECT) {

					System.out.println("");
					System.out.println("Connection timeout. Your request has been rejected.");
					System.out.println("");
					break;

				} else {

					System.out.println("");
					System.out.println(scp.getErrorMessage());
					System.out.println("");
					socket.close();
					break;

				}

			}

		} catch(ConnectException e) {

			System.out.println("");
			System.out.println(e.getMessage());
			System.out.println("");

		} catch(EOFException e) {

			System.out.println("");
			System.out.println("Disconnected.");
			System.out.println("");

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}