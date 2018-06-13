package de.htwsaar.dfs.iosbootstrap.resource;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import de.htwsaar.dfs.iosbootstrap.can_network.*;

@Path("/")
public class BootstrapResource {
	
	private Bootstrap bootstrap = new Bootstrap();
	
	@GET
	@Path("/getroutingTbl")
	public HashMap <Long, Zone> getrouting() {
		return bootstrap.getRoutingTbl();
	}
	

}
