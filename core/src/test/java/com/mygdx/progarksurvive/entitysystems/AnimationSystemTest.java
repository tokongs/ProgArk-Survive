package com.mygdx.progarksurvive.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.progarksurvive.entitycomponents.AnimationComponent;
import com.mygdx.progarksurvive.entitycomponents.ImageComponent;
import com.mygdx.progarksurvive.entitycomponents.PhysicsBodyComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimationSystemTest {

    @Test
    void testProcessEntityWithNoVelocity(@Mock Entity entity, @Mock Texture texture1, @Mock Texture texture2, @Mock Body body) {
        AnimationComponent animationComponent = new AnimationComponent(0.5f, Arrays.asList(texture1, texture2), 0);
        ImageComponent imageComponent = new ImageComponent(texture1, new Vector2(10, 10));
        PhysicsBodyComponent physicsBodyComponent = new PhysicsBodyComponent(body);

        when(entity.getComponent(AnimationComponent.class)).thenReturn(animationComponent);
        when(entity.getComponent(ImageComponent.class)).thenReturn(imageComponent);
        when(entity.getComponent(PhysicsBodyComponent.class)).thenReturn(physicsBodyComponent);
        when(body.getLinearVelocity()).thenReturn(new Vector2(0, 0));

        AnimationSystem animationSystem = new AnimationSystem();
        animationSystem.processEntity(entity, 0.2f);
        assertEquals(animationComponent.timePast, 0);
        assertEquals(animationComponent.textureIndex, 0);
        assertEquals(imageComponent.sprite.getTexture(), texture1);
    }

    @Test
    void testProcessEntityWithVelocity(@Mock Entity entity, @Mock Texture texture1, @Mock Texture texture2, @Mock Body body) {
        AnimationComponent animationComponent = new AnimationComponent(0.5f, Arrays.asList(texture1, texture2), 0);
        ImageComponent imageComponent = new ImageComponent(texture1, new Vector2(10, 10));
        PhysicsBodyComponent physicsBodyComponent = new PhysicsBodyComponent(body);

        when(entity.getComponent(AnimationComponent.class)).thenReturn(animationComponent);
        when(entity.getComponent(ImageComponent.class)).thenReturn(imageComponent);
        when(entity.getComponent(PhysicsBodyComponent.class)).thenReturn(physicsBodyComponent);
        when(body.getLinearVelocity()).thenReturn(new Vector2(100, 0));

        AnimationSystem animationSystem = new AnimationSystem();
        animationSystem.processEntity(entity, 0.2f);
        assertEquals(animationComponent.timePast, 0.2f);
        assertEquals(animationComponent.textureIndex, 0);
        assertEquals(imageComponent.sprite.getTexture(), texture1);

        animationSystem.processEntity(entity, 0.4f);
        assertEquals(animationComponent.timePast, 0.0f);
        assertEquals(animationComponent.textureIndex, 1);
        assertEquals(imageComponent.sprite.getTexture(), texture2);
    }
}