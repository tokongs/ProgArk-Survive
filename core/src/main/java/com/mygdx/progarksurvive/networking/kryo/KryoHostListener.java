package com.mygdx.progarksurvive.networking.kryo;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.progarksurvive.networking.UpdateEventHandler;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;

public class KryoHostListener extends Listener {

    private UpdateEventHandler<ClientUpdateEvent> handler;
    public KryoHostListener(UpdateEventHandler<ClientUpdateEvent> handler){
        this.handler = handler;
    }

    @Override
    public void received(Connection connection, Object object) {

        if (object instanceof ClientUpdateEvent) {
            handler.handleEvent(connection.getID(), (ClientUpdateEvent) object);
        }
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof KryoHostListener)) return false;

        return handler.equals(((KryoHostListener) o).handler);
    }
}
