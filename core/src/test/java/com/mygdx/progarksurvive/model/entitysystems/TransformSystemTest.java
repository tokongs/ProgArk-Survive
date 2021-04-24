package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.progarksurvive.model.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.model.entitycomponents.PhysicsBodyComponent;
import com.mygdx.progarksurvive.model.entitycomponents.TransformComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransformSystemTest {

    @Test
    void testProcessEntity(@Mock Entity entity, @Mock Body body, @Mock Texture texture) {
        TransformSystem system = new TransformSystem();
        PhysicsBodyComponent pbc = new PhysicsBodyComponent(body);
        TransformComponent pc = new TransformComponent(new Vector2(10, 10));
        ImageComponent ic  = new ImageComponent(texture, new Vector2(10, 10));

        when(entity.getComponent(TransformComponent.class)).thenReturn(pc);
        when(entity.getComponent(PhysicsBodyComponent.class)).thenReturn(pbc);
        when(entity.getComponent(ImageComponent.class)).thenReturn(ic);
        when(body.getPosition()).thenReturn(new Vector2(100, 100));
        system.processEntity(entity, 0.1f);

        assertEquals(pc.position, new Vector2(95, 95));
    }
}