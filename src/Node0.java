import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Node0 extends Node {

	private Node0() throws Exception {

		super( 0 );

		System.out.println( "Done!" );

	}

	@Override
	protected void rinit() throws Exception {
		initDirectCost();
		initDV();
		initNeighborDV();
		initSockets();
		sendDVToNeighbors();
	}

	private void initSockets() throws IOException {
		ServerSocket serverSocket = new ServerSocket( 3000 );
		sockets.put( 1, serverSocket.accept() );
		sockets.put( 2, serverSocket.accept() );
		sockets.put( 3, serverSocket.accept() );
	}

	private void initDirectCost() {
		directCost.put( 0, 0 );
		directCost.put( 1, 1 );
		directCost.put( 2, 3 );
		directCost.put( 3, 7 );
	}

	public static void main(String[] args) throws Exception {
		Node0 node = new Node0();

		while ( true ) {

		}

	}

}
