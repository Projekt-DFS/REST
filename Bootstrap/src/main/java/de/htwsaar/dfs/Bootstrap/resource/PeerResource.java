package de.htwsaar.dfs.Bootstrap.resource;

import java.util.List;

import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.htwsaar.dfs.Bootstrap.can.Peer;
import de.htwsaar.dfs.Bootstrap.service.PeerService;


/**
 * 
 * @author Aude Nana
 *
 */
@Path("/peers")
public class PeerResource {

	private PeerService ps = new PeerService();
	
	//ein peer kennt alle Peers
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Peer> getAllPeers(){
		return ps.getAllPeers();
	}
	
	
	@GET
	@Path("/{peerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Peer getPeer(@PathParam("peerId") int pid){
		return ps.getPeer(pid);
	}
	

}
