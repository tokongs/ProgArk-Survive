package com.mygdx.progarksurvive.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

public class NetworkManager {

    private static final int TCP_PORT = 54555;
    private static final int UDP_PORT = 54777;

    private static NetworkManager instance = null;

    private Client client = new Client();
    private Server server = new Server();

    private NetworkManager() {
        registerClasses(server.getKryo());
        registerClasses(client.getKryo());

        server.addListener(new HostListener());
        client.addListener(new ClientListener());

        server.start();
        client.start();
    }

    /**
     * Register classes which kryo should be able to serialize and send through the network
     * @param kryo
     */
    private void registerClasses(Kryo kryo){
        kryo.register(HostUpdateEvent.class);
        kryo.register(ClientUpdateEvent.class);
    }

    public void hostGameSession(String gameSessionName) throws IOException {
        server.bind(TCP_PORT, UDP_PORT);
        server.setDiscoveryHandler(new HostDiscoveryHandler(gameSessionName));
    }

    public void connectToGameSession(InetAddress address) throws IOException {
        client.connect(5000, address, TCP_PORT, UDP_PORT);
    }

    /**
     *  Discover available game sessions and get a map of game session name to ip
     * @return Mapping from game session name to address
     */
    public Map<String, InetAddress> findAvailableHosts() {
        ClientDiscoveryHandler handler = new ClientDiscoveryHandler();
        client.setDiscoveryHandler(handler);
        client.discoverHosts(UDP_PORT, 5000);
        return handler.getHosts();
    }

    public static NetworkManager getInstance() {
        if(instance == null)
            instance = new NetworkManager();

        return instance;
    }
}
