package com.mygdx.progarksurvive.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.progarksurvive.entitycomponents.EnemyComponent;
import com.mygdx.progarksurvive.entitycomponents.PhysicsBodyComponent;
import com.mygdx.progarksurvive.entitycomponents.PlayerComponent;
import com.mygdx.progarksurvive.entitycomponents.TargetingComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerTargetingSystemTest {

    @Test
    void testProcessEntity(@Mock Engine ashley,
                           @Mock Entity player,
                           @Mock Entity enemy1,
                           @Mock Entity enemy2,
                           @Mock Entity enemy3,
                           @Mock Body playerBody,
                           @Mock Body enemy1Body,
                           @Mock Body enemy2Body,
                           @Mock Body enemy3Body) {
        Entity[] playerArray = new Entity[]{enemy1, enemy2, enemy3};
        when(ashley.getEntitiesFor(Family.all(EnemyComponent.class, PhysicsBodyComponent.class).get())).thenReturn(new ImmutableArray<>(new Array<>(playerArray)));

        when(playerBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        when(playerBody.getPosition()).thenReturn(new Vector2(0, 0));
        when(enemy1Body.getPosition()).thenReturn(new Vector2(2, 0));
        when(enemy2Body.getPosition()).thenReturn(new Vector2(0, 1));
        when(enemy3Body.getPosition()).thenReturn(new Vector2(3, 1));
        TargetingComponent targetingComponent = new TargetingComponent();
        when(player.getComponent(TargetingComponent.class)).thenReturn(targetingComponent);

        when(player.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(playerBody));
        when(enemy1.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(enemy1Body));
        when(enemy2.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(enemy2Body));
        when(enemy3.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(enemy3Body));

        PlayerTargetingSystem system = new PlayerTargetingSystem(ashley);
        system.processEntity(player, 0.1f);

        assertEquals(targetingComponent.target, enemy2);
        verify(playerBody, times(1)).setTransform(eq(new Vector2(0, 0)), eq(90.0f));
    }

    @Test
    void testProcessEntityWhileMoving(@Mock Engine ashley,
                           @Mock Entity player,
                           @Mock Entity enemy1,
                           @Mock Entity enemy2,
                           @Mock Entity enemy3,
                           @Mock Body playerBody,
                           @Mock Body enemy1Body,
                           @Mock Body enemy2Body,
                           @Mock Body enemy3Body) {
        Entity[] playerArray = new Entity[]{enemy1, enemy2, enemy3};
        when(ashley.getEntitiesFor(Family.all(EnemyComponent.class, PhysicsBodyComponent.class).get())).thenReturn(new ImmutableArray<>(new Array<>(playerArray)));

        when(playerBody.getLinearVelocity()).thenReturn(new Vector2(100, 0));
        when(playerBody.getPosition()).thenReturn(new Vector2(0, 0));
        when(enemy1Body.getPosition()).thenReturn(new Vector2(2, 0));
        when(enemy2Body.getPosition()).thenReturn(new Vector2(0, 1));
        when(enemy3Body.getPosition()).thenReturn(new Vector2(3, 1));
        TargetingComponent targetingComponent = new TargetingComponent();
        when(player.getComponent(TargetingComponent.class)).thenReturn(targetingComponent);

        when(player.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(playerBody));
        when(enemy1.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(enemy1Body));
        when(enemy2.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(enemy2Body));
        when(enemy3.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(enemy3Body));

        PlayerTargetingSystem system = new PlayerTargetingSystem(ashley);
        system.processEntity(player, 0.1f);

        assertEquals(targetingComponent.target, enemy2);
        verify(playerBody, times(0)).setTransform(any(), anyFloat());
    }

    @Test
    void testProcessEntityWithNoEnemies(@Mock Engine ashley,
                                      @Mock Entity player,
                                      @Mock Body playerBody) {
        when(ashley.getEntitiesFor(Family.all(EnemyComponent.class, PhysicsBodyComponent.class).get())).thenReturn(new ImmutableArray<>(new Array<>()));

        when(playerBody.getLinearVelocity()).thenReturn(new Vector2(0, 0));
        when(playerBody.getPosition()).thenReturn(new Vector2(0, 0));
        TargetingComponent targetingComponent = new TargetingComponent();
        when(player.getComponent(TargetingComponent.class)).thenReturn(targetingComponent);

        when(player.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(playerBody));

        PlayerTargetingSystem system = new PlayerTargetingSystem(ashley);
        system.processEntity(player, 0.1f);

        assertEquals(targetingComponent.target, null);
        verify(playerBody, times(0)).setTransform(any(), anyFloat());
    }

}