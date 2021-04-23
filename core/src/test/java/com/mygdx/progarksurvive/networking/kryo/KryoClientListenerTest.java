package com.mygdx.progarksurvive.networking.kryo;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.progarksurvive.networking.UpdateEventHandler;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostNetworkEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KryoClientListenerTest {

    @Test
    void testReceived(@Mock UpdateEventHandler<HostNetworkEvent> handler,
                      @Mock HostNetworkEvent event,
                      @Mock Connection connection,
                      @Mock ClientUpdateEvent notHostUpdateEvent) {

        Gdx.app = mock(Application.class);
        KryoClientListener listener = new KryoClientListener(handler);

        when(connection.getID()).thenReturn(1);

        listener.received(connection, event);
        verify(handler, times(1)).handleEvent(1, event);
        reset(handler);

        listener.received(connection, notHostUpdateEvent);
        verify(handler, times(0)).handleEvent(anyInt(), any());
    }

    @Test
    void testEquals(@Mock UpdateEventHandler<HostNetworkEvent> handler1,
                    @Mock UpdateEventHandler<HostNetworkEvent> handler2) {

        KryoClientListener listener1 = new KryoClientListener(handler1);
        KryoClientListener listener2 = new KryoClientListener(handler2);
        KryoClientListener listener3 = new KryoClientListener(handler1);

        assertEquals(listener1, listener1);
        assertEquals(listener1, listener3);
        assertNotEquals(listener1, listener2);
        assertNotEquals(listener1, new Listener());
    }
}