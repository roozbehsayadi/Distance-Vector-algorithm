import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class DVReaderThread implements Runnable {

	private Node node;
	private int socketIndex;
	private DataInputStream dis;

	public DVReaderThread( Node node, int socketIndex ) throws Exception {
		this.node = node;
		this.socketIndex = socketIndex;
		this.dis = new DataInputStream( node.sockets.get( socketIndex ).getInputStream() );
	}

	private void rUpdate( Map<Integer, Integer> neighborDV ) {
	}

	@Override
	public void run() {
		try {
			Gson gson = new Gson();
			while (true) {
				String temp = this.dis.readUTF();
				Type type = new TypeToken<HashMap<Integer, Integer>>(){}.getType();
				Map<Integer, Integer> neighborDV = gson.fromJson( temp, type );
				rUpdate( neighborDV );
			}
		} catch ( Exception e ) {
			System.out.println( "Error in readUTF" );
			System.exit( 1 );
		}
	}
}
