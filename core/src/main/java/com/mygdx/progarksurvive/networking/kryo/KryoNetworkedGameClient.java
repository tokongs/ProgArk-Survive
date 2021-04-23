package com.mygdx.progarksurvive.networking.kryo;

import com.esotericsoftware.kryonet.Client;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;
import com.mygdx.progarksurvive.networking.UpdateEventHandler;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostNetworkEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

/**
 * Implementation of {@link com.mygdx.progarksurvive.networking.NetworkedGameClient NetworkedGameClient} based
 * on Kryonet.
 */
@Singleton
public class KryoNetworkedGameClient extends KryoBase implements NetworkedGameClient {

    private KryoClientListener listener = null;
    private boolean active = false;
    KryoClientDiscoveryHandler discoveryHandler;

    Client client;

    @Inject
    public KryoNetworkedGameClient(Client client, KryoClientDiscoveryHandler discoveryHandler) {
        this.client = client;
        this.discoveryHandler = discoveryHandler;
        client.setDiscoveryHandler(discoveryHandler);
        registerClasses(client.getKryo());
        client.start();
    }

    @Override
    public void joinGameSession(String address) throws IOException {
        client.connect(5000, address, TCP_PORT, UDP_PORT);
        active = true;
    }

    @Override
    public void leaveGameSession() {
        client.close();
        active = false;
    }

    @Override
    public void update(ClientUpdateEvent event) {
        client.sendTCP(event);
    }

    @Override
    public void setEventHandler(UpdateEventHandler<HostNetworkEvent> eventHandler) {
        if (listener != null) {
            client.removeListener(listener);
            listener = null;
        }
        listener = new KryoClientListener(eventHandler);
        client.addListener(listener);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public Map<String, InetAddress> findGameSessions() {
        discoveryHandler.reset();
        client.discoverHosts(UDP_PORT, 5000);
        return discoveryHandler.getHosts();
    }
}
