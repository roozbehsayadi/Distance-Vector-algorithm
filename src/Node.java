import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public abstract class Node {

	int index;

	Map<Integer, Integer> DV;
	Map<Integer, Integer> directCost;
	Map<Integer, Map<Integer, Integer>> neighborDV;
	Map<Integer, Socket> sockets;

	Node( int index ) throws Exception {
		directCost = new HashMap<>();
		DV = new HashMap<>();
		neighborDV = new HashMap<>();
		sockets = new HashMap<>();
		this.index = index;

		rinit();
		for ( Map.Entry<Integer, Socket> e : sockets.entrySet() )
			new Thread( new DVReaderThread( this, e.getKey() ) ).start();
	}

	void rinit() throws Exception {
		initDirectCost();
		initDV();
		initNeighborDV();
		initSockets();
		sendDVToNeighbors();
		System.out.println( "DV After rinit in node " + this.index + ": " + (new Gson()).toJson( this.DV ) );
	}

	void rUpdate( Packet pkt ) throws Exception {
		boolean flag = false;
		synchronized ( this ) {
			this.neighborDV.replace( pkt.source, pkt.DV );
			for ( int i = 0; i < 4; i++ ) {
				int previousValue = this.DV.get( i );
				for ( Map.Entry<Integer, Map<Integer, Integer>> e : neighborDV.entrySet() ) {
					int v = e.getKey();
					int temp = (int) Math.min( Integer.MAX_VALUE, (long) directCost.getOrDefault( v, Integer.MAX_VALUE ) + e.getValue().get( i ) );
					this.DV.replace( i, Math.min( this.DV.get( i ), temp ) );
				}
				System.out.println( "DV After update in node " + this.index + ": " + (new Gson()).toJson( this.DV ) );
				if ( previousValue > this.DV.get( i ) )
					flag = true;
			}
		}
		if ( flag )
			sendDVToNeighbors();
	}

	void sendDVToNeighbors() throws IOException {
		Packet pkt = new Packet();
		pkt.source = this.index;
		for ( Map.Entry<Integer, Socket> i : sockets.entrySet() ) {
			pkt.destination = i.getKey();
			synchronized (this) {
				pkt.DV = DV;
				DataOutputStream temp = new DataOutputStream(i.getValue().getOutputStream());
				Gson gson = new Gson();
				temp.writeUTF(gson.toJson(pkt));
			}
		}
	}

	void initDV() {
		for ( int i = 0; i < 4; i++ )
			DV.put( i, directCost.getOrDefault( i, Integer.MAX_VALUE ) );
	}

	protected abstract void initNeighborDV();
	protected abstract void initSockets() throws IOException;
	protected abstract void initDirectCost();

}
