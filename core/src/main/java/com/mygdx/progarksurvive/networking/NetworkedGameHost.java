package com.mygdx.progarksurvive.networking;

import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public interface NetworkedGameHost {
    void startGameSession(String sessionName) throws IOException;
    void stopGameSession();
    void update(HostUpdateEvent event);
    void setEventHandler(UpdateEventHandler<ClientUpdateEvent> eventHandler);
}
