package com.mygdx.progarksurvive.networking.kryo;

import com.esotericsoftware.kryo.Kryo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KryoClientDiscoveryHandlerTest {

    @Test
    void testOnRequestNewDatagramPacket() {
        KryoClientDiscoveryHandler handler = new KryoClientDiscoveryHandler();
        assertEquals(handler.onRequestNewDatagramPacket().getLength(), 64);
    }

    @Test
    void testOnDiscoveredHost(@Mock DatagramPacket host1,
                              @Mock DatagramPacket host2,
                              @Mock InetAddress address1,
                              @Mock InetAddress address2,
                              @Mock Kryo kryo) {
        when(host1.getAddress()).thenReturn(address1);
        when(host2.getAddress()).thenReturn(address2);

        byte[] sessionName1 = new byte[64];
        byte[] sessionName2 = new byte[64];
        int index = 0;
        for (byte b : "Test session 1".getBytes(StandardCharsets.UTF_8)) {
            sessionName1[index] = b;
            index++;
        }
        index = 0;
        for (byte b : "Test session 2".getBytes(StandardCharsets.UTF_8)) {
            sessionName2[index] = b;
            index++;
        }

        when(host1.getData()).thenReturn(sessionName1);
        when(host2.getData()).thenReturn(sessionName2);

        KryoClientDiscoveryHandler handler = new KryoClientDiscoveryHandler();
        handler.onDiscoveredHost(host1, kryo);
        handler.onDiscoveredHost(host2, kryo);

        Map<String, InetAddress> hosts = handler.getHosts();
        assertSame(hosts.get("Test session 1"), address1);
        assertSame(hosts.get("Test session 2"), address2);
        assertEquals(hosts.size(), 2);
    }

}