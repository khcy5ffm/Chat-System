package server;

import java.net.Socket;

public class ConnectionConstruction extends Connection {
	public ConnectionConstruction (Socket client, Server serverReference) {
		super(client, serverReference);
	}
}
