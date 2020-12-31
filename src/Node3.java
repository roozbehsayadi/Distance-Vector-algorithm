import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Node3 extends Node {

	private Node3() throws Exception {

		super( 3 );

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
		ServerSocket serverSocket = new ServerSocket( 3003 );
		sockets.put( 0, new Socket( "localhost", 3000 ) );
		sockets.put( 2, new Socket( "localhost", 3002 ) );
	}

	private void initDirectCost() {
		directCost.put( 3, 0 );
		directCost.put( 0, 7 );
		directCost.put( 2, 2 );
	}

	public static void main(String[] args) throws Exception {
		Node3 node = new Node3();

		while ( true );

	}

}
