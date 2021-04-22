package com.mygdx.progarksurvive.networking.kryo;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Base class for handling shared Kryonet related behaviour.
 */
public abstract class KryoBase {

    protected static final int TCP_PORT = 54555;
    protected static final int UDP_PORT = 54777;

    /**
     * Register classes which kryo should be able to serialize and send through the network
     * @param kryo
     */
    public static void registerClasses(Kryo kryo){
        kryo.register(Vector2.class);
        kryo.register(HostUpdateEvent.class);
        kryo.register(ClientUpdateEvent.class);
        kryo.register(ArrayList.class);
    }
}
