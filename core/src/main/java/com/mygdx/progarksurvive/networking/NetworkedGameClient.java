package com.mygdx.progarksurvive.networking;

import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostNetworkEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

/**
 * Used for interacting with a remote host.
 */
@Singleton
public interface NetworkedGameClient {

    /**
     *  Discover available game sessions and get a map of game session name to ip
     * @return Mapping from game session name to address
     */
    Map<String, InetAddress> findGameSessions();

    /**
     *  Join a game on the provided address
     */
    void joinGameSession(String address) throws IOException;

    /**
     * Leave the active game session.
     */
    void leaveGameSession();

    /**
     * Send an update event to the game host.
     * @param event to send
     */
    void update(ClientUpdateEvent event);

    /**
     * Set a handler which receives all events from host.
     * @param eventHandler
     */
    void setEventHandler(UpdateEventHandler<HostNetworkEvent> eventHandler);

}
