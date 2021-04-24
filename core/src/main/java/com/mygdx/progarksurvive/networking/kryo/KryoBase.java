package com.mygdx.progarksurvive.networking.kryo;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.mygdx.progarksurvive.model.EntityType;
import com.mygdx.progarksurvive.model.entitycomponents.*;
import com.mygdx.progarksurvive.networking.NetworkedEntityInfo;
import com.mygdx.progarksurvive.networking.events.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        kryo.register(EntityType.class);
        kryo.register(TypeComponent.class);
        kryo.register(Map.class);
        kryo.register(Vector2.class);
        kryo.register(HostNetworkEvent.class);
        kryo.register(HostUpdateEvent.class);
        kryo.register(Component.class);
        kryo.register(NetworkIdComponent.class);
        kryo.register(TransformComponent.class);
        kryo.register(HealthComponent.class);
        kryo.register(ScoreComponent.class);
        kryo.register(NetworkedEntityInfo.class);
        kryo.register(GameStartEvent.class);
        kryo.register(ClientUpdateEvent.class);
        kryo.register(GameOverEvent.class);
        kryo.register(ArrayList.class);
        kryo.register(HashMap.class);
    }
}
