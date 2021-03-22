package com.mygdx.progarksurvive.networking.kryo;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.progarksurvive.networking.UpdateEventHandler;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KryoNetworkedGameClientTest {

    Client client;
    @Spy Server server;
    
    @BeforeEach
    public void beforeEach() throws IOException {
        client = new Client();
        server = new Server();
        server.start();
        KryoBase.registerClasses(server.getKryo());
        server.bind(54555, 54777);
    }

    @AfterEach
    public void afterEach() throws IOException {
        client.stop();
        client.dispose();
        server.stop();
        server.dispose();
    }
    
    @Test
    void testJoinGameSession() throws IOException {
        KryoNetworkedGameClient kryoClient = new KryoNetworkedGameClient(client);
        kryoClient.joinGameSession("127.0.0.1");
        assertTrue(client.isConnected());
        assertEquals(server.getConnections().length, 1);
    }

    @Test
    void testLeaveGameSession() throws IOException {
        KryoNetworkedGameClient kryoClient = new KryoNetworkedGameClient(client);
        kryoClient.joinGameSession("127.0.0.1");
        kryoClient.leaveGameSession();
        assertFalse(client.isConnected());
        assertEquals(server.getConnections().length, 0);
    }

    @Test
    void testUpdate(@Mock Listener.QueuedListener serverListener) throws IOException {
        ClientUpdateEvent event = new ClientUpdateEvent();

        server.addListener(serverListener);

        KryoNetworkedGameClient kryoClient = new KryoNetworkedGameClient(client);
        kryoClient.joinGameSession("127.0.0.1");

        kryoClient.update(event);
        kryoClient.update(event);
        kryoClient.update(event);
    }

    @Test
    void testSetEventHandler(@Mock UpdateEventHandler<HostUpdateEvent> handler) throws IOException {
        KryoNetworkedGameClient kryoClient = new KryoNetworkedGameClient(client);
        kryoClient.joinGameSession("127.0.0.1");
        kryoClient.setEventHandler(handler);
        server.sendToAllTCP(new HostUpdateEvent());
        verify(handler, times(1)).handleEvent(any(HostUpdateEvent.class));
    }

    @Test
    void testFindGameSessions() {
    }
}