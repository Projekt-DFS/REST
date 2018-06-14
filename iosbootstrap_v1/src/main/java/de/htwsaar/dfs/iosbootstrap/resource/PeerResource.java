package de.htwsaar.dfs.iosbootstrap.resource;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.htwsaar.dfs.iosbootstrap.can_network.*;
import de.htwsaar.dfs.iosbootstrap.service.PeerService;


@Path("/")
public class PeerResource {
	public PeerService peerService = new PeerService();
	
	  @GET
	   @Path("/routing")  
	   public String routing(@QueryParam("x") double x, @QueryParam("y") double y)
	   {
		 //tmpPeer.checkZone(x, y);
		 return peerService.checkZone(x, y);	 
		  
	   }

	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Path("/joinpeers")
	  public String joinPeer(@QueryParam("x") double x, @QueryParam("y") double y)
	  {
		  return peerService.joinRequest(x, y);
	  }
	  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Path("/join")
	  public void joinNewPeer() {
		  peerService.join();
	  }
	  }