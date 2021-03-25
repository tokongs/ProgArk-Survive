package com.mygdx.progarksurvive.networking.events;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ClientUpdateEventTest {

    @Test
    void testEquals(@Mock Vector2 playerPosition) {
        ClientUpdateEvent event1 = new ClientUpdateEvent(playerPosition);
        ClientUpdateEvent event2 = new ClientUpdateEvent();
        ClientUpdateEvent event3 = new ClientUpdateEvent();

        event3.playerPosition = playerPosition;

        assertEquals(event1, event1);
        assertEquals(event1, event3);
        assertNotEquals(event1, event2);
        assertNotEquals(event1, mock(HostUpdateEvent.class));
    }
}