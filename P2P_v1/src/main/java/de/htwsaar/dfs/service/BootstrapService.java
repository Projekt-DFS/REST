package de.htwsaar.dfs.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.awt.geom.Point2D;

import de.htwsaar.dfs.can_network.*;


public class BootstrapService {

	private Bootstrap bootstrap = new Bootstrap();
	
	public HashMap<Long, Zone> getRouting() {
		return bootstrap.getRoutingTbl();
	}
	/**
	 * Routing-algorithmus
	 * @param x Koordinate des gesuchten Punktes
	 * @param y Koordinate des gesuchten Punktes
	 * @return IP-Adresse vom zonen-verantwortlichen Peer
	 */
	public  String checkZone (double x, double y) {
		//	if(tmpZone.bottomLeft.getX() >= ownZone.bottomLeft.getX() && tmpZone.bottomRight.getX() <= ownZone.bottomRight.getX() && tmpZone.bottomRight.getY() >= ownZone.bottomRight.getY() && tmpZone.upperRight.getY() <= ownZone.upperRight.getY()) {
			
			String ausgabe_ip ="";
			String webContextPath="routing";
			String baseUrl = "";
			double smalest_square=0d;
			
			double tmp_square;
			tmp_square = bootstrap.ownZone.distanz(bootstrap.ownZone.getCenter().getX(), bootstrap.ownZone.getCenter().getY(), x, y);
		
			
			if(x >= bootstrap.ownZone.getBottomLeft().getX() && x <= bootstrap.ownZone.getBottomRight().getX() && y >= bootstrap.ownZone.getBottomRight().getY() && y <= bootstrap.ownZone.getUpperRight().getY()) {
			 return bootstrap.ip_adresse;
			}
			else
			{
		
				for(Map.Entry<Long, Zone> entry : bootstrap.coordinates.entrySet()) {
					smalest_square = bootstrap.ownZone.distanz(entry.getValue().getCenter().getX(), entry.getValue().getCenter().getY(), x, y);
					if(smalest_square < tmp_square) {
						tmp_square = smalest_square;
						baseUrl ="http://"+ bootstrap.longToIp(entry.getKey())+":4434/start/";
					     // String baseUrl        = "http://"+ip_adresse+":"+port;
					}
				}

				      Client c = ClientBuilder.newClient();
				      WebTarget  target = c.target( baseUrl );

				      ausgabe_ip = (target.path(webContextPath).queryParam("x",x).queryParam("y", y).request( MediaType.TEXT_PLAIN ).get( String.class ));
				      System.out.println( target.path( webContextPath ));
		
				}
			
			return ausgabe_ip;
		}
	
	/**
	 * Check if the peer can split his zone with the new peer
	 * @param x Coordinate in the Zone
	 * @param y Coordinate in the Zone
	 * @throws JsonProcessingException 
	 */
	 
	public String joinRequest(double x, double y) {
		if(x >= bootstrap.ownZone.getBottomLeft().getX() && x <= bootstrap.ownZone.getBottomRight().getX() && y >= bootstrap.ownZone.getBottomRight().getY() && y <= bootstrap.ownZone.getUpperRight().getY()) {
		String jsonStr = "";
			/*	ObjectMapper mapper = new ObjectMapper();
			
			Peer newPeer = new Peer();
		
			newPeer = bootstrap.splitZone(newPeer);
			String jsonStr ="";
			try {
				jsonStr = mapper.writeValueAsString(newPeer);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	*/	
			
			return jsonStr;
			
		} else {
			
			String baseUrl ="";
			String webContextPath="joinPeers";
			double smalest_square=0d;			
			double tmp_square;
			
			tmp_square = bootstrap.ownZone.distanz(bootstrap.ownZone.getCenter().getX(), bootstrap.ownZone.getCenter().getY(), x, y);
			for(Map.Entry<Long, Zone> entry : bootstrap.coordinates.entrySet()) {
				smalest_square = bootstrap.ownZone.distanz(entry.getValue().getCenter().getX(), entry.getValue().getCenter().getY(), x, y);
				if(smalest_square < tmp_square) {
					tmp_square = smalest_square;
					baseUrl ="http://"+ bootstrap.longToIp(entry.getKey())+":4434/start/";
				     // String baseUrl        = "http://"+ip_adresse+":"+port;
				}
			}

			     Client c = ClientBuilder.newClient();
			     WebTarget  target = c.target( baseUrl );

			     return (target.path(webContextPath).queryParam("x",x).queryParam("y", y).request( MediaType.TEXT_PLAIN ).get( String.class ));
			     
	
			}
			
		}

	/**
	 * Send a request to Bootstrap to join a new Peer
	 * @param newPeer peer-object of the new Peer
	 */
	/* public void join(Peer newPeer) {
		Point2D.Double randomPoint = new Point2D.Double();
		randomPoint = bootstrap.generateRandomPoint();
		
		String webContextPath="getroutingTbl";
		Client c = ClientBuilder.newClient();
		String baseUrl = "http://localhost:8080/iosbootstrap/v1/";
		WebTarget  target = c.target( baseUrl );
		bootstrap.coordinates = (target.path(webContextPath).request().get(HashMap.class));
		
		webContextPath = "joinpeers";
		Peer generatePeer = new Peer();
		ObjectMapper mapper = new ObjectMapper();
		String peerStr ="";
		peerStr =  (target.path(webContextPath).request().get(String.class));
		
		try {
			generatePeer = mapper.readValue(peerStr, Peer.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		

	    ausgabe_ip = (target.path(webContextPath).queryParam("x",x).queryParam("y", y).request( MediaType.TEXT_PLAIN ).get( String.class ));
	    System.out.println( target.path( webContextPath ));
		
	}*/
}

