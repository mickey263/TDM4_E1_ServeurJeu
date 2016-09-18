import java.io.IOException;

public class Game {

	// 
	static ClientUDP clientUDP;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String ipSrv	= args[0];
		int portSrv 	= Integer.valueOf(args[1]);
		int portClient 	= Integer.valueOf(args[2]);
		
		clientUDP = new ClientUDP();
		clientUDP.execute(ipSrv,portSrv,portClient);			
	}
}
