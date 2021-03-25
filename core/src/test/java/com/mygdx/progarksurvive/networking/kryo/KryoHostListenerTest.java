package com.mygdx.progarksurvive.networking.kryo;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.progarksurvive.networking.UpdateEventHandler;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
class KryoHostListenerTest {

    @Test
    void testReceived(@Mock UpdateEventHandler<ClientUpdateEvent> handler,
                      @Mock ClientUpdateEvent event,
                      @Mock HostUpdateEvent notClientUpdateEvent,
                      @Mock Connection connection) {
        KryoHostListener listener = new KryoHostListener(handler);
        listener.received(connection, event);

        verify(handler, times(1)).handleEvent(event);

        reset(handler);

        listener.received(connection, notClientUpdateEvent);
        verify(handler, times(0)).handleEvent(any());
    }

    @Test
    void testEquals(@Mock UpdateEventHandler<ClientUpdateEvent> handler1,
                    @Mock UpdateEventHandler<ClientUpdateEvent> handler2) {
        KryoHostListener listener1 = new KryoHostListener(handler1);
        KryoHostListener listener2 = new KryoHostListener(handler2);
        KryoHostListener listener3 = new KryoHostListener(handler1);

        assertEquals(listener1, listener1);
        assertEquals(listener1, listener3);
        assertNotEquals(listener1, listener2);
        assertNotEquals(listener1, new Listener());
    }
}