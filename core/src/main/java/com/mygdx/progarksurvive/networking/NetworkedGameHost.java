package com.mygdx.progarksurvive.networking;

import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostNetworkEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

/**
 * Used for creating a game session and interacting with connected clients.
 */
@Singleton
public interface NetworkedGameHost {

    /**
     * Start a Game session with the given session name
     * @param sessionName
     * @throws IOException Thrown if session cannot be created
     */
    void startGameSession(String sessionName) throws IOException;

    /**
     * Stop an ongoing game session
     */
    void stopGameSession();

    /**
     * Send an update event to the connected clients
     * @param event Event to send
     */
    void update(HostNetworkEvent event);

    /**
     * Set a handler which receives all events from clients.
     * @param eventHandler
     */
    void setEventHandler(UpdateEventHandler<ClientUpdateEvent> eventHandler);

    int numberOfConnections();

    List<Integer> getConnectionIds();

    boolean isActive();
}
