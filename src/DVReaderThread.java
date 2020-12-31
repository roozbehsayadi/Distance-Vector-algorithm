import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
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
//			System.out.println( temp );
			Packet pkt = gson.fromJson( temp, Packet.class );
			try {
				node.rUpdate( pkt );
			} catch (Exception e) {
				System.out.println( "Exception in rUpdate" + e );
				System.exit( 1 );
			}
		}
//		try {
//			Gson gson = new Gson();
//			while (true) {
//				String temp = this.dis.readUTF();
//				System.out.println( temp );
//				Packet pkt = gson.fromJson( temp, Packet.class );
//				node.rUpdate( pkt );
//			}
//		} catch ( Exception e ) {
//			System.out.println( e );
//			System.exit( 1 );
//		}
	}
}
