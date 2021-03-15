package com.mygdx.progarksurvive.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;

public class HostListener extends Listener {
    public void received(Connection connection, Object object) {
        if (object instanceof ClientUpdateEvent) {
            //Do something real with the event
            System.out.println(((ClientUpdateEvent) object).playerPosition);
        }
    }
}

