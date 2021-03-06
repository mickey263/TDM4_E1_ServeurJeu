import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Client basique UDP
 * 
 */
public class ClientUDP
{
	
	//Creation de la socket
	DatagramSocket socket;
	DatagramPacket dpE;
	DatagramPacket dpR;
	
	/**
	 * Le client cree une socket, envoie "JOUER" au serveur
	 * et attend la reponse 
	 * 
	 */
	public void execute(String IPSrv, int PortSrc) throws IOException
	{
		//
		System.out.println("Demarrage du client ...");

		// Creation et envoi du message
		InetSocketAddress adrDest = new InetSocketAddress(IPSrv, PortSrc);
		byte[] bufE = new String("JOUER").getBytes();
		dpE = new DatagramPacket(bufE, bufE.length, adrDest);
		socket = new DatagramSocket();
		socket.send(dpE);
		System.out.println("Message envoy�");
		
		// Attente de la reponse 
		byte[] bufR = new byte[2048];
		dpR = new DatagramPacket(bufR, bufR.length);
		socket.receive(dpR);
		String message = new String(bufR, dpR.getOffset(), dpR.getLength());
		System.out.println("Reponse recue = "+message);
		
		int iSQid = 0; int iEQid = 0;
		int iSNb1 = 0; int iENb1 = 0;
		int iSNb2 = 0; int iENb2 = 0; 
		
		for (int i= 0; i < message.length();i++) {
			switch (message.charAt(i))
			{
				case 'Q':
					iSQid = i+1;
					break;
				case ':':
					iEQid = i;
					iSNb1 = i+1;
					break;
				case '+':
					iENb1 = i;
					iSNb2 = i+1;
					break;
				case '=':
					iENb2 = i;
					break;
				default:
					break;					
			}
		}
		
		System.out.println("id Question "+ message.substring(iSQid, iEQid));
		System.out.println("Valeur A "+ message.substring(iSNb1, iENb1));
		System.out.println("Valeur B "+ message.substring(iSNb2, iENb2));
		
		String QID = message.substring(iSQid, iEQid);
		int A = Integer.valueOf(message.substring(iSNb1, iENb1));
		int B = Integer.valueOf(message.substring(iSNb2, iENb2));
		int resultat = A+B;
		
		// Creation et envoi du message
		byte[] bufE2 = new String("R" + QID + ':' + resultat).getBytes();
		dpE = new DatagramPacket(bufE2, bufE2.length, adrDest);
		socket = new DatagramSocket();
		socket.send(dpE);
		System.out.println("Message envoyé " + "R" + QID + ':' + resultat);
	
		// Attente de la reponse 
		byte[] bufR2 = new byte[2048];
		dpR = new DatagramPacket(bufR2, bufR2.length);
		socket.receive(dpR);
		String message1 = new String(bufR2, dpR.getOffset(), dpR.getLength());
		System.out.println("Reponse recue = "+message1);
		
		// Creation et envoi du message
		byte[] bufE3 = new String("SCORE").getBytes();
		dpE = new DatagramPacket(bufE3, bufE3.length, adrDest);
		socket = new DatagramSocket();
		socket.send(dpE);
		System.out.println("Message envoyé");	
		
		// Attente de la reponse 
		byte[] bufR3 = new byte[2048];
		dpR = new DatagramPacket(bufR3, bufR3.length);
		socket.receive(dpR);
		String message2 = new String(bufR3, dpR.getOffset(), dpR.getLength());
		System.out.println("Reponse recue = "+message2);
		
		// Fermeture de la socket
		socket.close();
		System.out.println("Arret du client .");
	}

}
