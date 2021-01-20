
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Node0 extends Node {

	private Node0() throws Exception {
		super( 0 );
	}

	@Override
	protected void initSockets() throws IOException {
		ServerSocket serverSocket = new ServerSocket( 3000 );
		sockets.put( 1, serverSocket.accept() );
		sockets.put( 2, serverSocket.accept() );
		sockets.put( 3, serverSocket.accept() );
	}

	@Override
	protected void initDirectCost() {
		directCost.put( 0, 0 );
		directCost.put( 1, 1 );
		directCost.put( 2, 3 );
		directCost.put( 3, 7 );
	}

	@Override
	protected void initNeighborDV() {
		Set<Integer> neighbors = new HashSet<>();
		neighbors.add( 1 ); neighbors.add( 2 ); neighbors.add( 3 );
		for ( Integer i : neighbors ) {
			neighborDV.put( i, new HashMap<>() );
			for (int j = 0; j < 4; j++) {
				neighborDV.get(i).put(j, Integer.MAX_VALUE);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new Node0();
	}

}
