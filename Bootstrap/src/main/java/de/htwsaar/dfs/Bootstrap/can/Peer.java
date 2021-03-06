package de.htwsaar.dfs.Bootstrap.can;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.awt.geom.Point2D;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;


/**
 * @author Thomas Spanier
 *
 */
@XmlRootElement
public class Peer {
	
	
	
	//Variablen
		private Zone ownZone;
		public static final int port = 4434;
		public static final String ip_bootstrap = "192.168.2.109";
	
		// Aktuelle IP-Adresse des Servers
		private String ipAddress;
		private static LinkedList<Peer> routingTable = new LinkedList<>();
	
		//Constructor
			public Peer(Zone tmpZone) {
					this.ownZone = tmpZone;
					
				 try {
					this.ipAddress = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
				
			}
		//Constructor
		/**
		 * Creates a new Peer in oldPeer's Zone
		 * @param oldPeer
		 */
		public Peer(Peer oldPeer) {
			try {
				this.ipAddress = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			oldPeer.splitZone(this);
			
		}
		
		

		/**
		 * Creates a new Peer and generates a Random Point
		 * Peer will split the oldPeers Zone
		 */
		public Peer() {
			
			//generateRandomPoint();	
			
		}
		
		public String getIpAddress() {
			return ipAddress;
		}
		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}
		public static LinkedList<Peer> getRoutingTable() {
			return routingTable;
		}
		public static void setRoutingTable(LinkedList<Peer> routingTable) {
			Peer.routingTable = routingTable;
		}
		public static int getPort() {
			return port;
		}
		public static String getIpBootstrap() {
			return ip_bootstrap;
		}
		/**
		 * 
		 * @return the local ip-adress of the peer
		 */
		public String getIpAdress() {
			return ipAddress;
		}
		
		
		/**
		 * Put values in the Hashmap coordinates
		 * @param key IP-Adresse from the a neighbor of the peer
		 * @param zone zone-responsibility of the neighbor
		 */
		public void createRoutingTable( Peer peer) {
			routingTable.add( peer);
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
				tmp_square = ownZone.distanz(ownZone.getCenter().getX(), ownZone.getCenter().getY(), x, y);
			
				
				if(x >= ownZone.getBottomLeft().getX() && x <= ownZone.getBottomRight().getX() 
						&& y >= ownZone.getBottomRight().getY() && y <= ownZone.getUpperRight().getY()) {
				 return ipAddress;
				}
				else
				{
			
					for(Peer peer : routingTable) {
						smalest_square = ownZone.distanz(peer.getOwnZone().getCenter().getX(), peer.getOwnZone().getCenter().getY(), x, y);
						if(smalest_square < tmp_square) {
							tmp_square = smalest_square;
							baseUrl ="http://"+ longToIp(peer.getIpAddress())+":4434/start/";
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
		 * Convert a IP-Address(String) to long
		 * @param i IP-Address as String 
		 * @return IP-Adress as long
		 */	
		public long ipToLong(String ipAddress) {

			// ipAddressInArray[0] = 192
			String[] ipAddressInArray = ipAddress.split("\\.");

			long result = 0;
			for (int i = 0; i < ipAddressInArray.length; i++) {

				int power = 3 - i;
				int ip = Integer.parseInt(ipAddressInArray[i]);

				// 1. 192 * 256^3
				// 2. 168 * 256^2
				// 3. 1 * 256^1
				// 4. 2 * 256^0
				result += ip * Math.pow(256, power);

			}

			return result;

		}
			
		
		/**
		 * Convert a IP-Address(Long) to String
		 * @param string IP-Address as Long 
		 * @return IP-Adress as String
		 */
		public String longToIp(String string) {
			//return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
		return null;
		}
			

	   
	   //Zone functions
	   
	   /**
	    * Creates a new Zone
	    * @param bottomLeft Point in the Coordinate system
	    * @param upperRight Point in the Coordinate system
	    */
	   public void createZone(Point2D.Double bottomLeft, Point2D.Double upperRight) {
	        ownZone = new Zone();
	        ownZone.setZone(bottomLeft, upperRight);
	    }
	    
	   /**
	    * Splits the Peer's Zone and transfers an half to the new Peer
	    * @param newPeer
	    */
	    public Peer splitZone(Peer newPeer) {
	        if (ownZone.isSquare()) {
	        
	            newPeer.createZone(new Point2D.Double(ownZone.calculateCentrePoint().getX(), ownZone.getBottomRight().getY()), ownZone.getUpperRight());
	            ownZone.setZone(ownZone.getBottomLeft(), new Point2D.Double(ownZone.calculateCentrePoint().getX(), ownZone.getUpperLeft().getY()));    
	        } else {
	            newPeer.createZone(ownZone.getBottomLeft(), (new Point2D.Double(ownZone.getBottomRight().getX(), ownZone.calculateCentrePoint().getY())));
	            ownZone.setZone(new Point2D.Double(ownZone.getUpperLeft().getX(), ownZone.calculateCentrePoint().getY()), ownZone.getUpperRight());    
	        }
	        return newPeer;
	    }
	    
	    /**
	     * Prints the Peer's Zone
	     * @return
	     */
	    public String toStringZone() {
	        return ownZone.toString();
	    }
	    
	    /**
	     * Calculates, if the Zone is square
	     * @return true, if the Zone is square, otherwise false
	     */
	    public boolean hasSquareZone() {
	        return ownZone.isSquare();
	    }
	    
	    /**
	     * 
	     * @return the Zone's volume
	     */
	    public double getZoneVolume() {
	    	return ownZone.getZoneVolume();
	    }
	    
	   
	    
	    /**
	     * Generates a random Point in the Coordinate system
	     * @return
	     */
	    public Point2D.Double generateRandomPoint() {
	    	Point2D.Double randomPoint = new Point2D.Double(Math.random(), Math.random());
	    	return randomPoint;
	    }
	   
	    
	 
	    
	    public boolean isNeighbour(Peer potentialNeighbour) {
	    	
	    	if (ownZone.getLeftY().intersects(potentialNeighbour.ownZone.getRightY()) 
	    	    || ownZone.getRightY().intersects(potentialNeighbour.ownZone.getLeftY())
	    	    || ownZone.getUpperX().intersects(potentialNeighbour.ownZone.getBottomX())
	    	    || ownZone.getBottomX().intersects(potentialNeighbour.ownZone.getUpperX())) {
	    		return true;
	    	} else {
	    		return false;
	    	}
	    	
	    	
	    }
	    
		/**
		 * Send a request to Bootstrap to join a new Peer
		 * @param newPeer peer-object of the new Peer
		 */
		public static void join(Peer newPeer) {
			String webContextPath="getroutingTbl";
			Client c = ClientBuilder.newClient();
			String baseUrl = "http://"+ ip_bootstrap+":4434/start/getroutingTbl";
			WebTarget  target = c.target( baseUrl );
			routingTable = (target.path(webContextPath).request().get(LinkedList.class));
	/*
		    ausgabe_ip = (target.path(webContextPath).queryParam("x",x).queryParam("y", y).request( MediaType.TEXT_PLAIN ).get( String.class ));
		    System.out.println( target.path( webContextPath ));
		*/	
		}
		
		/**
		 * Check if the peer can split his zone with the new peer
		 * @param x Coordinate in the Zone
		 * @param y Coordinate in the Zone
		 */
		 
		public Peer joinRequest(double x, double y) {
			if(x >= ownZone.getBottomLeft().getX() && x <= ownZone.getBottomRight().getX() && y >= ownZone.getBottomRight().getY() && y <= ownZone.getUpperRight().getY()) {
				Peer newPeer = new Peer();
				newPeer = splitZone(newPeer);
				return newPeer;
			} else {
				
				String baseUrl ="";
				String webContextPath="joinPeers";
				double smalest_square=0d;			
				double tmp_square;
				
				tmp_square = ownZone.distanz(ownZone.getCenter().getX(), ownZone.getCenter().getY(), x, y);
				for(Peer peer : routingTable) {
					smalest_square = ownZone.distanz(peer.getOwnZone().getCenter().getX(),peer.getOwnZone().getCenter().getY(), x, y);
					if(smalest_square < tmp_square) {
						tmp_square = smalest_square;
						baseUrl ="http://"+ longToIp(peer.ipAddress)+":4434/start/";
					     // String baseUrl        = "http://"+ip_adresse+":"+port;
					}
				}

				      Client c = ClientBuilder.newClient();
				      WebTarget  target = c.target( baseUrl );

				      return (target.path(webContextPath).queryParam("x",x).queryParam("y", y).request( MediaType.TEXT_PLAIN ).get( Peer.class ));
				     
		
				}
				
			}
		public Zone getOwnZone() {
			return ownZone;
		}
		public void setOwnZone(Zone ownZone) {
			this.ownZone = ownZone;
		}
			
		
	   

	}