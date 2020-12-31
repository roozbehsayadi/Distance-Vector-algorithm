import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Node3 {

	private Map<Integer, Integer> DV;
	private Map<Integer, Integer> directCost;
	private Map<Integer, Map<Integer, Integer>> neighborDV;
	private Map<Integer, Socket> sockets;

	private Node3() throws Exception {

		directCost = new HashMap<>();
		directCost.put( 3, 0 );
		directCost.put( 0, 7 );
		directCost.put( 2, 2 );

		DV = new HashMap<>();
		for ( int i = 0; i < 4; i++ )
			DV.put( i, directCost.getOrDefault( i, Integer.MAX_VALUE ) );

		neighborDV = new HashMap<>();
		for ( int i = 0; i < 4; i++ ) {
			if ( i == 3 ) continue;
			neighborDV.put( i, new HashMap<Integer, Integer>() );
			for (int j = 0; j < 4; j++) {
				neighborDV.get(i).put(j, Integer.MAX_VALUE);
			}
		}

		sockets = new HashMap<>();
		ServerSocket serverSocket = new ServerSocket( 3003 );
		sockets.put( 0, new Socket( "localhost", 3000 ) );
		sockets.put( 2, new Socket( "localhost", 3002 ) );

		for ( Map.Entry<Integer, Socket> i : sockets.entrySet() ) {
			DataOutputStream temp = new DataOutputStream( i.getValue().getOutputStream() );
			Gson gson = new Gson();
			temp.writeUTF( gson.toJson( DV ) );
		}

		System.out.println( "Done!" );

	}

	public static void main(String[] args) throws Exception {
		Node3 node = new Node3();

		while ( true );

	}

}
