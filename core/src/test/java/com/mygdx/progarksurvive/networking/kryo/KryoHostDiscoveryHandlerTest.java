package com.mygdx.progarksurvive.networking.kryo;

import com.esotericsoftware.kryonet.Serialization;
import com.esotericsoftware.kryonet.ServerDiscoveryHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class KryoHostDiscoveryHandlerTest {

    @Test
    void testOnDiscoverHost(@Mock DatagramChannel channel, @Mock Serialization serialization, @Mock InetSocketAddress address) throws IOException {
        KryoHostDiscoveryHandler handler = new KryoHostDiscoveryHandler("Test session");
        handler.onDiscoverHost(channel, address, serialization);
        verify(channel, times(1)).send(ByteBuffer.wrap("Test session".getBytes(StandardCharsets.UTF_8)),  address);
    }

    @Test
    void testEquals(){
        String gameSessionName1 = "Test 1";
        String gameSessionName2 = "Test 2";

        KryoHostDiscoveryHandler handler1 = new KryoHostDiscoveryHandler(gameSessionName1);
        KryoHostDiscoveryHandler handler2 = new KryoHostDiscoveryHandler(gameSessionName2);
        KryoHostDiscoveryHandler handler3 = new KryoHostDiscoveryHandler(gameSessionName1);

        assertEquals(handler1, handler1);
        assertEquals(handler1, handler3);
        assertNotEquals(handler1, handler2);
        assertNotEquals(handler1, mock(ServerDiscoveryHandler.class));
    }
}