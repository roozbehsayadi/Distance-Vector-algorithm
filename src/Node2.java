import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Node2 {

	private Map<Integer, Integer> DV;
	private Map<Integer, Integer> directCost;
	private Map<Integer, Map<Integer, Integer>> neighborDV;
	private Map<Integer, Socket> sockets;

	private Node2() throws Exception {

		directCost = new HashMap<>();
		DV = new HashMap<>();
		neighborDV = new HashMap<>();
		sockets = new HashMap<>();

		rinit2();

		System.out.println( "Done!" );

	}

	private void rinit2() throws Exception {
		initDirectCost();
		initDV();
		initNeighborDV();
		initSockets();
		sendDVToNeighbors();
	}

	private void sendDVToNeighbors() throws IOException {
		for ( Map.Entry<Integer, Socket> i : sockets.entrySet() ) {
			DataOutputStream temp = new DataOutputStream( i.getValue().getOutputStream() );
			Gson gson = new Gson();
			temp.writeUTF( gson.toJson( DV ) );
		}
	}

	private void initSockets() throws IOException {
		ServerSocket serverSocket = new ServerSocket( 3002 );
		sockets.put( 0, new Socket( "localhost", 3000 ) );
		sockets.put( 1, new Socket( "localhost", 3001 ) );
		sockets.put( 3, serverSocket.accept() );
	}

	private void initNeighborDV() {
		for ( int i = 0; i < 4; i++ ) {
			if ( i == 2 ) continue;
			neighborDV.put( i, new HashMap<Integer, Integer>() );
			for (int j = 0; j < 4; j++) {
				neighborDV.get(i).put(j, Integer.MAX_VALUE);
			}
		}
	}

	private void initDV() {
		for ( int i = 0; i < 4; i++ )
			DV.put( i, directCost.getOrDefault( i, Integer.MAX_VALUE ) );
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