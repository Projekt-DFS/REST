package de.htwsaar.dfs.resource;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.htwsaar.dfs.can_network.Zone;
import de.htwsaar.dfs.service.*;

@Path("/")
public class BootstrapResource {
private BootstrapService bootstrapService = new BootstrapService();
	
	@GET
	@Path("/getroutingTbl")
	public HashMap <Long, Zone> getrouting() {
		return bootstrapService.getRouting();
	}
	@GET
	@Path("/routing")  
	public String routing(@QueryParam("x") double x, @QueryParam("y") double y)
	{
	 //tmpPeer.checkZone(x, y);
		return bootstrapService.checkZone(x, y);	 
	  
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/joinpeers")
	public String joinPeer(@QueryParam("x") double x, @QueryParam("y") double y)
	{
		return bootstrapService.joinRequest(x, y);
	}
}
