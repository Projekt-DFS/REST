package de.htwsaar.dfs.iosbootstrap.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import de.htwsaar.dfs.iosbootstrap.can_network.Peer;
import de.htwsaar.dfs.iosbootstrap.can_network.Zone;
import de.htwsaar.dfs.iosbootstrap.model.*;

public class PeerService {
	
	private Peer tmpPeer = new Peer();
	
	public PeerService() {
		
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
			tmp_square = tmpPeer.ownZone.distanz(tmpPeer.ownZone.getCenter().getX(), tmpPeer.ownZone.getCenter().getY(), x, y);
		
			
			if(x >= tmpPeer.ownZone.getBottomLeft().getX() && x <= tmpPeer.ownZone.getBottomRight().getX() && y >= tmpPeer.ownZone.getBottomRight().getY() && y <= tmpPeer.ownZone.getUpperRight().getY()) {
			 return tmpPeer.ip_adresse;
			}
			else
			{
		
				for(Map.Entry<Long, Zone> entry : tmpPeer.coordinates.entrySet()) {
					smalest_square = tmpPeer.ownZone.distanz(entry.getValue().getCenter().getX(), entry.getValue().getCenter().getY(), x, y);
					if(smalest_square < tmp_square) {
						tmp_square = smalest_square;
						baseUrl ="http://"+ tmpPeer.longToIp(entry.getKey())+":4434/start/";
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
	 */
	 
	public Peer joinRequest(double x, double y) {
		if(x >= tmpPeer.ownZone.getBottomLeft().getX() && x <= tmpPeer.ownZone.getBottomRight().getX() && y >= tmpPeer.ownZone.getBottomRight().getY() && y <= tmpPeer.ownZone.getUpperRight().getY()) {
			Peer newPeer = new Peer();
			newPeer = tmpPeer.splitZone(newPeer);
			return newPeer;
		} else {
			
			String baseUrl ="";
			String webContextPath="joinPeers";
			double smalest_square=0d;			
			double tmp_square;
			
			tmp_square = tmpPeer.ownZone.distanz(tmpPeer.ownZone.getCenter().getX(), tmpPeer.ownZone.getCenter().getY(), x, y);
			for(Map.Entry<Long, Zone> entry : tmpPeer.coordinates.entrySet()) {
				smalest_square = tmpPeer.ownZone.distanz(entry.getValue().getCenter().getX(), entry.getValue().getCenter().getY(), x, y);
				if(smalest_square < tmp_square) {
					tmp_square = smalest_square;
					baseUrl ="http://"+ tmpPeer.longToIp(entry.getKey())+":4434/start/";
				     // String baseUrl        = "http://"+ip_adresse+":"+port;
				}
			}

			     Client c = ClientBuilder.newClient();
			     WebTarget  target = c.target( baseUrl );

			     return (target.path(webContextPath).queryParam("x",x).queryParam("y", y).request( MediaType.TEXT_PLAIN ).get( Peer.class ));
			     
	
			}
			
		}

	/**
	 * Send a request to Bootstrap to join a new Peer
	 * @param newPeer peer-object of the new Peer
	 */
	public void join(Peer newPeer) {
		String webContextPath="getroutingTbl";
		Client c = ClientBuilder.newClient();
		String baseUrl = "http://localhost:8080/iosbootstrap/v1/getroutingTbl";
		WebTarget  target = c.target( baseUrl );
		tmpPeer.coordinates = (target.path(webContextPath).request().get(HashMap.class));
/*
	    ausgabe_ip = (target.path(webContextPath).queryParam("x",x).queryParam("y", y).request( MediaType.TEXT_PLAIN ).get( String.class ));
	    System.out.println( target.path( webContextPath ));
	*/	
	}
}