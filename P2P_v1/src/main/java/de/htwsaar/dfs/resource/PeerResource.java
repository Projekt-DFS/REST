package de.htwsaar.dfs.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.htwsaar.dfs.can_network.Peer;
import de.htwsaar.dfs.service.PeerService;

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
	
	@GET
	   @Path("/routing")  
	   public String routing(@QueryParam("x") double x, @QueryParam("y") double y)
	   {
		 //tmpPeer.checkZone(x, y);
		 return ps.checkZone(x, y);	 
		  
	   }

	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Path("/joinpeers")
	  public String joinPeer(@QueryParam("x") double x, @QueryParam("y") double y)
	  {
		  return ps.joinRequest(x, y);
	  }
	  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Path("/join")
	  public void joinNewPeer() {
		  ps.join();
	  }
	

}
