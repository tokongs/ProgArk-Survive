package com.mygdx.progarksurvive.networking;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

public class HostUpdateEventHandler implements UpdateEventHandler<HostUpdateEvent> {
    @Override
    public void handleEvent(HostUpdateEvent event) {

    }
}
