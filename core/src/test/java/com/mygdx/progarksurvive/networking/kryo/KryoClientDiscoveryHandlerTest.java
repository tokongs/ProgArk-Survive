package com.mygdx.progarksurvive.networking.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.ClientDiscoveryHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.byteThat;
import static org.mockito.Mockito.mock;
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

        KryoClientDiscoveryHandler handler = new KryoClientDiscoveryHandler();

        when(host1.getAddress()).thenReturn(address1);
        when(host2.getAddress()).thenReturn(address2);

        byte[] sessionName1 = getByteBuffer("Test session 1");
        byte[] sessionName2 = getByteBuffer("Test session 2");

        when(host1.getData()).thenReturn(sessionName1);
        when(host2.getData()).thenReturn(sessionName2);

        handler.onDiscoveredHost(host1, kryo);
        handler.onDiscoveredHost(host2, kryo);

        Map<String, InetAddress> hosts = handler.getHosts();
        assertSame(hosts.get("Test session 1"), address1);
        assertSame(hosts.get("Test session 2"), address2);
        assertEquals(hosts.size(), 2);
    }


    @Test
    void testOnDiscoveredHostDuplicate(@Mock DatagramPacket host1,
                              @Mock DatagramPacket host2,
                              @Mock InetAddress address1,
                              @Mock Kryo kryo) {

        KryoClientDiscoveryHandler handler = new KryoClientDiscoveryHandler();

        when(host1.getAddress()).thenReturn(address1);

        byte[] sessionName1 = getByteBuffer("Test session 1");

        when(host1.getData()).thenReturn(sessionName1);
        when(host2.getData()).thenReturn(sessionName1);

        handler.onDiscoveredHost(host1, kryo);
        handler.onDiscoveredHost(host2, kryo);

        Map<String, InetAddress> hosts = handler.getHosts();
        assertSame(hosts.get("Test session 1"), address1);
        assertEquals(1,  hosts.size());
    }

    @Test
    void testOnDiscoveredHostNullBytesAtEndOfSessionName(@Mock DatagramPacket host,
                                       @Mock InetAddress address,
                                       @Mock Kryo kryo) {
        KryoClientDiscoveryHandler handler = new KryoClientDiscoveryHandler();

        when(host.getAddress()).thenReturn(address);

        byte[] sessionName = new byte[64];
        byte[] sessionNameBuffer = getByteBuffer("Test session");
        for(int i = 0; i < sessionNameBuffer.length; i++){
            sessionName[i] = sessionNameBuffer[i];
        }

        when(host.getData()).thenReturn(sessionName);

        handler.onDiscoveredHost(host, kryo);

        Map<String, InetAddress> hosts = handler.getHosts();
        assertSame(hosts.get("Test session"), address);
        assertEquals(1,  hosts.size());
    }

    @Test
    void testReset(@Mock DatagramPacket host1,
                   @Mock DatagramPacket host2,
                   @Mock InetAddress address1,
                   @Mock InetAddress address2,
                   @Mock Kryo kryo){
        KryoClientDiscoveryHandler handler = new KryoClientDiscoveryHandler();

        when(host1.getAddress()).thenReturn(address1);
        when(host2.getAddress()).thenReturn(address2);

        byte[] sessionName1 = getByteBuffer("Test session 1");
        byte[] sessionName2 = getByteBuffer("Test session 2");

        when(host1.getData()).thenReturn(sessionName1);
        when(host2.getData()).thenReturn(sessionName2);

        handler.onDiscoveredHost(host1, kryo);
        handler.onDiscoveredHost(host2, kryo);


        assertEquals(2, handler.getHosts().size());

        handler.reset();

        assertEquals(0, handler.getHosts().size());

    }

    @Test
    void testEquals(@Mock DatagramPacket host1,
                    @Mock DatagramPacket host2,
                    @Mock InetAddress address1,
                    @Mock InetAddress address2,
                    @Mock Kryo kryo){

        KryoClientDiscoveryHandler handler1 = new KryoClientDiscoveryHandler();
        KryoClientDiscoveryHandler handler2 = new KryoClientDiscoveryHandler();
        KryoClientDiscoveryHandler handler3 = new KryoClientDiscoveryHandler();

        when(host1.getAddress()).thenReturn(address1);
        when(host2.getAddress()).thenReturn(address2);

        byte[] sessionName1 = getByteBuffer("Test session 1");
        byte[] sessionName2 = getByteBuffer("Test session 2");

        when(host1.getData()).thenReturn(sessionName1);
        when(host2.getData()).thenReturn(sessionName2);

        handler1.onDiscoveredHost(host1, kryo);
        handler1.onDiscoveredHost(host2, kryo);

        handler2.onDiscoveredHost(host1, kryo);

        handler3.onDiscoveredHost(host1, kryo);
        handler3.onDiscoveredHost(host2, kryo);

        assertEquals(handler1, handler1);
        assertEquals(handler1, handler3);
        assertNotEquals(handler1, handler2);
        assertNotEquals(handler1, mock(ClientDiscoveryHandler.class));
    }

    private byte[] getByteBuffer(String string){
        byte[] result = new byte[string.length()];
        int index = 0;
        for (byte b : string.getBytes(StandardCharsets.UTF_8)) {
            result[index] = b;
            index++;
        }

        return result;
    }

}