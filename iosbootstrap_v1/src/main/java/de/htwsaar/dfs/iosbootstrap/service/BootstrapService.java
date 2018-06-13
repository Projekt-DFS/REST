package de.htwsaar.dfs.iosbootstrap.service;

import java.util.HashMap;

import de.htwsaar.dfs.iosbootstrap.can_network.*;

public class BootstrapService {

	private Bootstrap bootstrap = new Bootstrap();
	
	public HashMap<Long, Zone> getRouting() {
		return bootstrap.getRoutingTbl();
	}

}
