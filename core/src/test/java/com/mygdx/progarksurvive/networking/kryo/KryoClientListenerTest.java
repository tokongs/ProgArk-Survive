package com.mygdx.progarksurvive.networking.kryo;

import com.esotericsoftware.kryonet.Connection;
import com.mygdx.progarksurvive.networking.UpdateEventHandler;
import com.mygdx.progarksurvive.networking.events.HostUpdateEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KryoClientListenerTest {

    @Test
    void testReceived(@Mock UpdateEventHandler<HostUpdateEvent> handler, @Mock HostUpdateEvent event, @Mock Connection connection) {
        KryoClientListener listener = new KryoClientListener(handler);
        listener.received(connection, event);
        verify(handler, times(1)).handleEvent(event);
    }
}