package de.htwsaar.dfs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.htwsaar.dfs.Database;
import de.htwsaar.dfs.can_network.Bootstrap;
import de.htwsaar.dfs.can_network.Peer;

public class PeerService {
	
	private Bootstrap bs = new Bootstrap();
	public static Map<Long, Peer> peers = Database.getPeers();

	public List<Peer> getAllPeers() {
		//bs.getAllPeers();
		return new ArrayList<>(peers.values());
	}

	public Peer getPeer(int pid) {
		return peers.get(pid);
	}

}
