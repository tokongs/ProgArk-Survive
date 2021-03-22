package com.mygdx.progarksurvive.networking.kryo;

import com.esotericsoftware.kryonet.Serialization;
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

@ExtendWith(MockitoExtension.class)
class KryoHostDiscoveryHandlerTest {

    @Test
    void testOnDiscoverHost(@Mock DatagramChannel channel, @Mock Serialization serialization, @Mock InetSocketAddress address) throws IOException {
        KryoHostDiscoveryHandler handler = new KryoHostDiscoveryHandler("Test session");
        handler.onDiscoverHost(channel, address, serialization);
        verify(channel, times(1)).send(ByteBuffer.wrap("Test session".getBytes(StandardCharsets.UTF_8)),  address);
    }
}