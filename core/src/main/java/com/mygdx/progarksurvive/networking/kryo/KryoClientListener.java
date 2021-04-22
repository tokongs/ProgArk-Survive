package com.mygdx.progarksurvive.networking.kryo;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.progarksurvive.networking.UpdateEventHandler;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

public class KryoClientListener extends Listener {

    private final UpdateEventHandler<HostUpdateEvent> handler;

    public KryoClientListener(UpdateEventHandler<HostUpdateEvent> handler) {
        this.handler = handler;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof HostUpdateEvent) {
            handler.handleEvent(connection.getID(), (HostUpdateEvent) object);
        } else {
            Gdx.app.debug("ClientListener", "Received object of unknown type");
        }
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof KryoClientListener)) return false;

        return handler.equals(((KryoClientListener) o).handler);
    }
}
