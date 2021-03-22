package com.mygdx.progarksurvive;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.progarksurvive.networking.kryo.KryoNetworkedGameClient;
import com.mygdx.progarksurvive.networking.kryo.KryoNetworkedGameHost;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;
import com.mygdx.progarksurvive.networking.NetworkedGameHost;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public interface GameModule {
    @Provides
    static Client provideClient() {
        return new Client();
    }
    @Provides
    static Server provideServer() {
        return new Server();
    }
    @Binds
    NetworkedGameClient bindNetworkedGameClient(KryoNetworkedGameClient impl);
    @Binds
    NetworkedGameHost bindNetworkedGameHost(KryoNetworkedGameHost impl);
}