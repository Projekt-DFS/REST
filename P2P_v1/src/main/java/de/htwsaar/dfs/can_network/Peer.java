package de.htwsaar.dfs.can_network;

import java.io.IOException;
import java.net.URI;

import java.awt.geom.Point2D;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;

import java.net.InetAddress;

import java.net.UnknownHostException;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Thomas Spanier
 *
 */
@XmlRootElement
public class Peer {
	//Variablen
	public Zone ownZone;
	public static final int port = 4434;
	public static final String ip_bootstrap = "192.168.2.100";
	//TODO temporary
	// Aktuelle IP-Adresse des Servers
	@XmlTransient
	public  static String ip_adresse;
	@XmlTransient
	public InetAddress inet;
	
	private LinkedList<Peer> routingTable = new LinkedList<Peer>();
	
	
	
	   /**
	    * Splits the Peer's Zone and transfers one half to the new Peer
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
	    
	    updateRoutingTables(newPeer);
	    
	    return newPeer;
	}
	
	// Methods for routingTable updating
	
	/**
	 * updates routingTables of all Peers affected
	 * @param newPeer
	 */
	public void updateRoutingTables(Peer newPeer) {
		// oldPeer becomes neighbour of new Peer
	    newPeer.mergeRoutingTableSinglePeer(this);
	    
	    // newPeer gets the routingTable from oldPeer
	    newPeer.mergeRoutingTableWithList(routingTable);
	    
	    // newPeer becomes neighbour of oldPeer
	    this.mergeRoutingTableSinglePeer(newPeer);
	   
	    /**
	     * each Peer of oldPeer's routingTable gets newPeer as a temporary neighbour
	     * Peers from oldPeer's old routingTable check if oldPeer and newPeer are neighbours
	     * if not, they are removed from the routingTable
	     */
	    
	    for (Peer p : routingTable) {
	    	p.mergeRoutingTableSinglePeer(newPeer);
	    	
	    	if (p.isNeighbour(this) == false) {
	    		p.getRoutingTable().remove(this);
	    	}
	    	
	    	if (p.isNeighbour(newPeer) == false) {
	    		p.getRoutingTable().remove(newPeer);
	    	}
	    }
	    
	    eliminateNeighbours(this);
	    eliminateNeighbours(newPeer);
	}
	
	/**
	 * a single Peer is put into the routingTable
	 * @param potentialNeighbour
	 */
	public void mergeRoutingTableSinglePeer(Peer potentialNeighbour) {
		routingTable.add(potentialNeighbour);
	}
	
	/**
	 * a neighbour's routingTable is merged into the Peer's routingTable
	 * @param neighboursRoutingTable
	 */
	public void mergeRoutingTableWithList(LinkedList<Peer> neighboursRoutingTable) {
		routingTable.addAll(neighboursRoutingTable);
	}
	
	/**
	 * eliminates neighbours from routingTable if isNeighbour() returns false
	 * @param peer
	 */
	public void eliminateNeighbours(Peer peer) {
		peer.getRoutingTable().parallelStream().forEach( p-> {
			if(peer.isNeighbour(p) == false) {
				peer.getRoutingTable().remove(p);
			}
		});
	}
	
	public String routingTableToString() {
		StringBuilder sb = new StringBuilder();
		
		try {
			for (Peer p : routingTable) {
				sb.append(p.getIP()).append(" ").append(p.getZone()).append(System.lineSeparator());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return sb.toString();	
	}
	
	
	public Zone getOwnZone() {
		return ownZone;
	}

	public void setOwnZone(Zone ownZone) {
		this.ownZone = ownZone;
	}

	public static String getIp_adresse() {
		return ip_adresse;
	}

	public static void setIp_adresse(String ip_adresse) {
		Peer.ip_adresse = ip_adresse;
	}

	public InetAddress getInet() {
		return inet;
	}

	public void setInet(InetAddress inet) {
		this.inet = inet;
	}

	public static int getPort() {
		return port;
	}

	public static String getIpBootstrap() {
		return ip_bootstrap;
	}

	public void setNeighbourList(ArrayList<Integer> neighbourList) {
		this.neighbourList = neighbourList;
	}

	private ArrayList<Integer> neighbourList;				//Fill
	
	
	public Zone getZone() {
		return ownZone;
	}
	
	//Constructor
	public Peer(Zone tmpZone) {
		this.ownZone = tmpZone;
			
		try {
			this.inet = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println(inet.getHostAddress());
		//ip_adresse = this.inet.toString();
			
		
	}
	//Constructor
	/**
	 * Creates a new Peer in oldPeer's Zone
	 * @param oldPeer
	 */
	public Peer(Peer oldPeer) {
		try {
			this.inet = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldPeer.splitZone(this);
		
	}
	
	public Peer() {
			
	}
	
	/**
	 * 
	 * @return the local ip-address of the peer
	 * @throws UnknownHostException 
	 */
	
	public String getIP() throws UnknownHostException {
		
		this.inet = InetAddress.getLocalHost();
		return inet.getHostAddress();
	}
	
	public void createNeighbours() {

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
	 * @param i IP-Address as Long 
	 * @return IP-Adress as String
	 */
	public String longToIp(long i) {
		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
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
     * @return randomPoint in the coordinate space
     */
    public Point2D.Double generateRandomPoint() {
    	Point2D.Double randomPoint = new Point2D.Double(Math.random(), Math.random());
    	return randomPoint;
    }
   
    public LinkedList<Peer> getRoutingTable() {
    	return routingTable;
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
}
