import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Node3 extends Node {

	private Node3() throws Exception {
		super( 3 );
	}

	@Override
	protected void initSockets() throws IOException {
		ServerSocket serverSocket = new ServerSocket( 3003 );
		sockets.put( 0, new Socket( "localhost", 3000 ) );
		sockets.put( 2, new Socket( "localhost", 3002 ) );
	}

	@Override
	protected void initDirectCost() {
		directCost.put( 3, 0 );
		directCost.put( 0, 7 );
		directCost.put( 2, 2 );
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
		Node3 node = new Node3();
	}

}
