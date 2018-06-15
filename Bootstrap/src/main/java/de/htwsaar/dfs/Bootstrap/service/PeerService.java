package de.htwsaar.dfs.Bootstrap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.htwsaar.dfs.Bootstrap.Database;
import de.htwsaar.dfs.Bootstrap.can.Bootstrap;
import de.htwsaar.dfs.Bootstrap.can.Peer;

public class PeerService {
	
	private Bootstrap bs = new Bootstrap();
	public static Map<Long, Peer> peers = Database.getPeers();

	public List<Peer> getAllPeers() {
		//bs.getAllPeers();
		List<Peer> list= new ArrayList<>(peers.values());
		return list;
	}
	
	

	public Peer getPeer(int pid) {
		return peers.get(pid);
	}

}
