import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Node2 extends Node {

	private Node2() throws Exception {
		super( 2 );
	}

	@Override
	protected void initSockets() throws IOException {
		ServerSocket serverSocket = new ServerSocket( 3002 );
		sockets.put( 0, new Socket( "localhost", 3000 ) );
		sockets.put( 1, new Socket( "localhost", 3001 ) );
		sockets.put( 3, serverSocket.accept() );
	}

	@Override
	protected void initDirectCost() {
		directCost.put( 2, 0 );
		directCost.put( 0, 3 );
		directCost.put( 1, 1 );
		directCost.put( 3, 2 );
	}

	@Override
	protected void initNeighborDV() {
		Set<Integer> neighbors = new HashSet<>();
		neighbors.add( 0 ); neighbors.add( 1 ); neighbors.add( 3 );
		for ( Integer i : neighbors ) {
			neighborDV.put( i, new HashMap<>() );
			for (int j = 0; j < 4; j++) {
				neighborDV.get(i).put(j, Integer.MAX_VALUE);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Node2 node = new Node2();
	}

}
