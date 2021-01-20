import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class DVReaderThread implements Runnable {

	private Node node;
	private DataInputStream dis;

	public DVReaderThread( Node node, int socketIndex ) throws Exception {
		this.node = node;
		this.dis = new DataInputStream( node.sockets.get( socketIndex ).getInputStream() );
	}

	@Override
	public void run() {
		Gson gson = new Gson();
		while ( true ) {
			String temp = null;
			try {
				temp = this.dis.readUTF();
			} catch (EOFException e) {
				System.out.println( "A socket is closed unexpectedly. Exiting..." );
				System.exit( 1 );
			}catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			Packet pkt = gson.fromJson( temp, Packet.class );
			try {
				node.rUpdate( pkt );
			} catch (Exception e) {
				System.out.println( "Exception in rUpdate" + e );
				System.exit( 1 );
			}
		}
	}
}
