package com.mygdx.progarksurvive;

import com.badlogic.ashley.core.Engine;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;
import com.mygdx.progarksurvive.networking.NetworkedGameHost;
import com.mygdx.progarksurvive.networking.kryo.KryoClientDiscoveryHandler;
import com.mygdx.progarksurvive.networking.kryo.KryoNetworkedGameClient;
import com.mygdx.progarksurvive.networking.kryo.KryoNetworkedGameHost;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public interface GameModule {

    @Provides @Singleton
    static Client provideClient() {
        return new Client();
    }

    @Provides @Singleton
    static Server provideServer() {
        return new Server();
    }

    @Provides
    static KryoClientDiscoveryHandler provideKryoClientDiscoveryHandler() {
        return new KryoClientDiscoveryHandler();
    }

    @Binds
    NetworkedGameClient bindNetworkedGameClient(KryoNetworkedGameClient impl);

    @Binds
    NetworkedGameHost bindNetworkedGameHost(KryoNetworkedGameHost impl);


}