import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Node1 extends Node {

	private Node1() throws Exception {

		super( 1 );

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
		ServerSocket serverSocket = new ServerSocket( 3001 );
		sockets.put( 0, new Socket( "localhost", 3000 ) );
		sockets.put( 2, serverSocket.accept() );
	}

	private void initDirectCost() {
		directCost.put( 1, 0 );
		directCost.put( 0, 1 );
		directCost.put( 2, 1 );
	}

	public static void main(String[] args) throws Exception {
		Node1 node = new Node1();

		while ( true );

	}

}
