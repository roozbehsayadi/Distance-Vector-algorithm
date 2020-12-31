import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public abstract class Node {

	protected int index;

	protected Map<Integer, Integer> DV;
	protected Map<Integer, Integer> directCost;
	protected Map<Integer, Map<Integer, Integer>> neighborDV;
	protected Map<Integer, Socket> sockets;

	protected Node( int index ) throws Exception {
		directCost = new HashMap<>();
		DV = new HashMap<>();
		neighborDV = new HashMap<>();
		sockets = new HashMap<>();
		this.index = index;

		rinit();
		for ( Map.Entry<Integer, Socket> e : sockets.entrySet() )
			new Thread( new DVReaderThread( this, e.getKey() ) ).start();
	}

	protected abstract void rinit() throws Exception;

	protected void sendDVToNeighbors() throws IOException {
		for ( Map.Entry<Integer, Socket> i : sockets.entrySet() ) {
			DataOutputStream temp = new DataOutputStream( i.getValue().getOutputStream() );
			Gson gson = new Gson();
			temp.writeUTF( gson.toJson( DV ) );
		}
	}

	protected void initNeighborDV() {
		for ( int i = 0; i < 4; i++ ) {
			if ( i == this.index ) continue;
			neighborDV.put( i, new HashMap<Integer, Integer>() );
			for (int j = 0; j < 4; j++) {
				neighborDV.get(i).put(j, Integer.MAX_VALUE);
			}
		}
	}

	protected void initDV() {
		for ( int i = 0; i < 4; i++ )
			DV.put( i, directCost.getOrDefault( i, Integer.MAX_VALUE ) );
	}

}
