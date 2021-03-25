package com.mygdx.progarksurvive.networking.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.progarksurvive.networking.UpdateEventHandler;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Note that this is not a very good way to unit test something. But with networking code unit testing can be difficult.
 * And being dependant on kryonet makes testing and mocking behaviour difficult, hence the use of Mockito's verify method.
 * This should only be used when results can not be tested.
 */
@ExtendWith(MockitoExtension.class)
class KryoNetworkedGameClientTest {

    @Mock Client client;
    @Mock KryoClientDiscoveryHandler discoveryHandler;

    @BeforeEach
    void setup(){
        when(client.getKryo()).thenReturn(new Kryo());
    }

    @Test
    void testJoinGameSession() throws IOException {
        String address = "127.0.0.1";
        KryoNetworkedGameClient kryoClient = new KryoNetworkedGameClient(client, discoveryHandler);
        kryoClient.joinGameSession(address);
        verify(client, times(1)).connect(anyInt(), eq(address), eq(54555), eq(54777));
    }

    @Test
    void testLeaveGameSession() {
        KryoNetworkedGameClient kryoClient = new KryoNetworkedGameClient(client, discoveryHandler);
        kryoClient.leaveGameSession();
        verify(client, times(1)).close();
    }
    @Test
    void testUpdate() {
        ClientUpdateEvent event = new ClientUpdateEvent();
        KryoNetworkedGameClient kryoClient = new KryoNetworkedGameClient(client, discoveryHandler);
        kryoClient.update(event);

        verify(client, times(1)).sendTCP(event);
    }

    @Test
    void testSetEventHandler(@Mock UpdateEventHandler<HostUpdateEvent> handler1, @Mock UpdateEventHandler<HostUpdateEvent> handler2) {
        KryoNetworkedGameClient kryoClient = new KryoNetworkedGameClient(client, discoveryHandler);
        kryoClient.setEventHandler(handler1);

        verify(client, times(0)).removeListener(any());
        verify(client, times(1)).addListener(eq(new KryoClientListener(handler1)));

        kryoClient.setEventHandler(handler2);

        verify(client, times(1)).removeListener(eq(new KryoClientListener(handler1)));
        verify(client, times(1)).addListener(eq(new KryoClientListener(handler2)));
    }

    @Test
    void findGameSessions(@Mock HashMap<String, InetAddress> hosts) {
        KryoNetworkedGameClient kryoClient = new KryoNetworkedGameClient(client, discoveryHandler);


        when(discoveryHandler.getHosts()).thenReturn(hosts);
        Map<String, InetAddress> discoveredHosts = kryoClient.findGameSessions();
        assertEquals(hosts,discoveredHosts);
        verify(client, times(1)).discoverHosts(54777, 5000);
    }
}