import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Node1 extends Node {

	private Node1() throws Exception {
		super( 1 );
	}

	@Override
	protected void initSockets() throws IOException {
		ServerSocket serverSocket = new ServerSocket( 3001 );
		sockets.put( 0, new Socket( "localhost", 3000 ) );
		sockets.put( 2, serverSocket.accept() );
	}

	@Override
	protected void initDirectCost() {
		directCost.put( 1, 0 );
		directCost.put( 0, 1 );
		directCost.put( 2, 1 );
	}

	@Override
	protected void initNeighborDV() {
		Set<Integer> neighbors = new HashSet<>();
		neighbors.add( 0 ); neighbors.add( 2 );
		for ( Integer i : neighbors ) {
			neighborDV.put( i, new HashMap<>() );
			for (int j = 0; j < 4; j++) {
				neighborDV.get(i).put(j, Integer.MAX_VALUE);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new Node1();
	}

}
