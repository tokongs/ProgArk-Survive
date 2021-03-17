package com.mygdx.progarksurvive.networking;

import com.esotericsoftware.kryonet.Client;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import javax.inject.Inject;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

public class KryoNetworkedGameClient extends KryoBase implements NetworkedGameClient {

    private KryoClientListener listener = null;

    Client client;

    @Inject
    public KryoNetworkedGameClient(Client client) {
        this.client = client;
        registerClasses(client.getKryo());
        client.start();
    }

    @Override
    public void joinGameSession(InetAddress address) throws IOException {
        client.connect(5000, address, TCP_PORT, UDP_PORT);
    }

    @Override
    public void leaveGameSession() {
        client.close();
    }

    @Override
    public void update(ClientUpdateEvent event) {
        client.sendTCP(event);
    }

    @Override
    public void setEventHandler(UpdateEventHandler<HostUpdateEvent> eventHandler) {
        if (listener != null) {
            client.removeListener(listener);
            listener = null;
        }
        listener = new KryoClientListener(eventHandler);
        client.addListener(listener);
    }

    @Override
    public Map<String, InetAddress> findGameSessions() {
        KryoClientDiscoveryHandler handler = new KryoClientDiscoveryHandler();
        client.setDiscoveryHandler(handler);
        client.discoverHosts(UDP_PORT, 5000);
        return handler.getHosts();
    }
}
