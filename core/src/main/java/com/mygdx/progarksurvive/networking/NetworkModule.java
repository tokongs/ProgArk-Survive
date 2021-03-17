package com.mygdx.progarksurvive.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public interface NetworkModule {
    @Provides
    static Client provideClient() {
        return new Client();
    }

    @Provides
    static Server provideServer() {
        return new Server();
    }

    @Binds
    NetworkedGameClient bindINetworkedGameClient(KryoNetworkedGameClient impl);
    @Binds
    NetworkedGameHost bindINetworkedGameHost(KryoNetworkedGameHost impl);
}
