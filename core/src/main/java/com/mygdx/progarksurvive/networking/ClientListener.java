package com.mygdx.progarksurvive.networking;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import java.util.Map;

public class ClientListener extends Listener {
    public void received(Connection connection, Object object) {
        if (object instanceof HostUpdateEvent) {
            // Do something real
            Map<String, Vector2> playerPositions = ((HostUpdateEvent) object).PlayerPositions;
            playerPositions.forEach((key, value) -> System.out.println(String.format("%s: %s", key, value)));
        }
    }
}
