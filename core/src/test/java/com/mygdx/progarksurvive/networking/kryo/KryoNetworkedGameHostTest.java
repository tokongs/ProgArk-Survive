package com.mygdx.progarksurvive.networking.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.progarksurvive.networking.NetworkedGameHost;
import com.mygdx.progarksurvive.networking.UpdateEventHandler;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class KryoNetworkedGameHostTest {

    @Mock
    Server server;

    @BeforeEach
    void setup(){
        when(server.getKryo()).thenReturn(new Kryo());
    }

    @Test
    void startGameSession() throws IOException {
        String sessionName = "Test session";
        KryoNetworkedGameHost host = new KryoNetworkedGameHost(server);
        host.startGameSession(sessionName);
        verify(server, times(1)).start();
        verify(server, times(1)).setDiscoveryHandler(eq(new KryoHostDiscoveryHandler(sessionName)));
        verify(server, times(1)).bind(54555, 54777);
    }

    @Test
    void stopGameSession() {
        KryoNetworkedGameHost host = new KryoNetworkedGameHost(server);
        host.stopGameSession();
        verify(server, times(1)).stop();
        verify(server, times(1)).close();
    }

    @Test
    void update(@Mock HostUpdateEvent event) {
        KryoNetworkedGameHost host = new KryoNetworkedGameHost(server);
        host.update(event);
        verify(server, times(1)).sendToAllTCP(event);
    }

    @Test
    void setEventHandler(@Mock UpdateEventHandler<ClientUpdateEvent> handler1,
                         @Mock UpdateEventHandler<ClientUpdateEvent> handler2) {

        KryoNetworkedGameHost kryoClient = new KryoNetworkedGameHost(server);
        kryoClient.setEventHandler(handler1);

        verify(server, times(0)).removeListener(any());
        verify(server, times(1)).addListener(eq(new KryoHostListener(handler1)));

        kryoClient.setEventHandler(handler2);

        verify(server, times(1)).removeListener(eq(new KryoHostListener(handler1)));
        verify(server, times(1)).addListener(eq(new KryoHostListener(handler2)));
    }
}