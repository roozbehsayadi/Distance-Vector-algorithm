import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Node2 extends Node {

	private Node2() throws Exception {

		super( 2 );

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
		ServerSocket serverSocket = new ServerSocket( 3002 );
		sockets.put( 0, new Socket( "localhost", 3000 ) );
		sockets.put( 1, new Socket( "localhost", 3001 ) );
		sockets.put( 3, serverSocket.accept() );
	}

	private void initDirectCost() {
		directCost.put( 2, 0 );
		directCost.put( 0, 3 );
		directCost.put( 1, 1 );
		directCost.put( 3, 2 );
	}

	public static void main(String[] args) throws Exception {
		Node2 node = new Node2();

		while ( true );

	}

}
