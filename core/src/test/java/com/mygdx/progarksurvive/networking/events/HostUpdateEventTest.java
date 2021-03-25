package com.mygdx.progarksurvive.networking.events;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class HostUpdateEventTest {

    @Test
    void testEquals(@Mock Map<String, Vector2> playerPositions) {
        HostUpdateEvent event1 = new HostUpdateEvent();
        HostUpdateEvent event2 = new HostUpdateEvent();
        HostUpdateEvent event3 = new HostUpdateEvent();

        event1.playerPositions = playerPositions;
        event3.playerPositions = playerPositions;

        assertEquals(event1, event1);
        assertEquals(event1, event3);
        assertNotEquals(event1, event2);
        assertNotEquals(event1, mock(ClientUpdateEvent.class));
    }
}