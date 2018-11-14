# Half-Duplex Chat with SCP

This is the assignment from SENG3400 - Network and Distributed Computing course of University of Newcastle, 2018. This assignment was completed individually.

## What is SCP

SCP (Simple Chat Protocol) is a very simple chat protocol. It functions similarly to a 'walkie talkie' where communication is one-way, with only one user capable of sending messages at a time. Users take turns in sending messages. A user must wait for a response before they can send another message.

A SCP server only supports one connection at a time. If the server is busy with another client, the request will be ignored or eventually timed-out by the Socket API. The SCP protocol is aware of 6 message types which the server and client must be aware of:
* CONNECT
* REJECT
* ACCEPT
* ACKNOWLEDGE
* CHAT
* DISCONNECT

The operation of the protocol is as followed:

1. The client will send a SCP connection request structured as followed:

```
	SCP CONNECT
	SERVERADDRESS <server hostname>
	SERVERPORT <server port>
	REQUESTCREATED <time since epoch>
	USERNAME <username as String with quotes>
	SCP END
```

For example

```
	SCP CONNECT
	SERVERADDRESS 127.0.0.1
	SERVERPORT 3400
	REQUESTCREATED 1533018800
	USERNAME "Kelvin"
	SCP END
```

2. The server will read and process this request.

If the passed epoch time is more than 5 seconds different to the current epoch time, the server will send a response message rejecting the connection, and then proceed to close the accepted client socket. The rejection message is structured as followed.

```
	SCP REJECT
	TIMEDIFFERENTIAL <time difference>
	REMOTEADDRESS <requesting clients hostname>
	SCP END
```

Otherwise the server will accept the connection and send message:

```
	SCP ACCEPT
	USERNAME <username as String with quotes>
	CLIENTADDRESS <client hostname>
	CLIENTPORT <client port>
	SCP END
```

3. The client will acknowledge the acceptance of the request by sending message:

```
	SCP ACKNOWLEDGE
	USERNAME <username as String with quotes>
	SERVERADDRESS <server hostname>
	SERVERPORT <server port>
	SCP END
```

4. The server will register the acknowledgement and initiate a chat. The server will send the first message of the chat (the welcome message passed upon start-up). The client and server will then each take turns sending CHAT messages.

```
	SCP CHAT
	REMOTEADDRESS <remote hostname>
	REMOTEPORT <remote port>
	MESSAGECONTENT
	<line feed>
	<message contents>
	SCP END
```

<line feed> is 2 new lines i.e: 2 '\n'

5. When either the server or the client receives user input “DISCONNECT”, the application will send the following message.

```
	SCP DISCONNECT
	SCP END
```

The receiver will then send a modified ACKNOWLEDGE message back to the client

```
	SCP ACKNOWLEDGE
	SCP END
```

When the DISCONNECT sequence has finished, the client will close its socket. The server will close the connection to the client and resume waiting for a new client.

## Setup

Clone the git

```
	git@github.com:Susros/Half-Duplex-Chat.git
```

Then compile all source codes

```
	javac *.java
```

## Usage

User A will start the ChatServer application:

```
	java ChatServer "localhost" 3400 "Welcom to my Chat!"
```

User B will start ChatClient application:

```
	java ChatClient "localhost" 3400
```

The ChatClient will initiate a connection to the ChatServer. The client will open a Socket connection to the passed hostname & port. It will begin by sending a valid CONNECT message by the socket. This will require asking the user their screen name, entered by console input. The user does not have to type the SCP protocol CONNECT message manually, it will be automatically formatted by the client and transmitted to the server.

The ChatServer will then read the transmission and send an appropriate response (i.e. a REJECT or ACCEPT message). If an ACCEPT response is received, the ChatClient will send an ACKNOWLEDGE message. This is to inform the server that the client is aware and ready to start a chat. The ChatServer will then send the first CHAT message, containing the welcome message specified at startup.

The ChatClient and ChatServer will begin sending CHAT messages in a half-duplex manner. The ChatClient will start sending messages, followed by the ChatServer, and repeating in this loop until either User A or User B enters “DISCONNECT” into the console to terminate the chat. When the user has their turn to type, they will be prompted to enter a message. When the user is awaiting a response, they will be informed that the other user is typing.
When the special “DISCONNECT” message is sent, the recipient application will send an acknowledge message, and close their Socket connection. The ChatServer will begin waiting for a new client to connect in order to begin a new SCP chat session.