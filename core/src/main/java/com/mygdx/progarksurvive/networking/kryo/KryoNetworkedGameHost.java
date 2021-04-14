package com.mygdx.progarksurvive.networking.kryo;

import com.esotericsoftware.kryonet.ClientDiscoveryHandler;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.ServerDiscoveryHandler;
import com.mygdx.progarksurvive.networking.NetworkedGameHost;
import com.mygdx.progarksurvive.networking.UpdateEventHandler;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

/**
 * Implementation of {@link com.mygdx.progarksurvive.networking.NetworkedGameHost NetworkedGameHost} based
 * on Kryonet.
 */
@Singleton
public class KryoNetworkedGameHost extends KryoBase implements NetworkedGameHost {

    private final Server server;
    private Listener listener;

    @Inject
    public KryoNetworkedGameHost(Server server){
        this.server = server;
        registerClasses(server.getKryo());
    }


    @Override
    public void startGameSession(String sessionName) throws IOException {
        server.start();
        server.setDiscoveryHandler(new KryoHostDiscoveryHandler(sessionName));
        server.bind(TCP_PORT, UDP_PORT);
    }

    @Override
    public void stopGameSession() {
        server.close();
        server.stop();
    }

    @Override
    public void update(HostUpdateEvent event) {
        server.sendToAllTCP(event);
    }

    @Override
    public void setEventHandler(UpdateEventHandler<ClientUpdateEvent> eventHandler) {
        if(listener != null){
            server.removeListener(listener);
            listener = null;
        }
        listener = new KryoHostListener(eventHandler);
        server.addListener(listener);
    }
}