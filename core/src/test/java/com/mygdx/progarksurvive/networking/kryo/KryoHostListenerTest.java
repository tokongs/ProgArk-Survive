package com.mygdx.progarksurvive.networking.kryo;

import com.esotericsoftware.kryonet.Connection;
import com.mygdx.progarksurvive.networking.UpdateEventHandler;
import com.mygdx.progarksurvive.networking.events.ClientUpdateEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KryoHostListenerTest {

    @Test
    void testReceived(@Mock UpdateEventHandler<ClientUpdateEvent> handler, @Mock ClientUpdateEvent event, @Mock Connection connection) {
        KryoHostListener listener = new KryoHostListener(handler);
        listener.received(connection, event);

        verify(handler, times(1)).handleEvent(event);
    }
}