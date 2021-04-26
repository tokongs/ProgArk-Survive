package com.mygdx.progarksurvive.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.progarksurvive.entitycomponents.PhysicsBodyComponent;
import com.mygdx.progarksurvive.entitycomponents.PlayerComponent;
import com.mygdx.progarksurvive.entitycomponents.TargetingComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnemyTargetingSystemTest {

    @Test
    void testProcessEntity(@Mock Engine ashley,
                           @Mock Entity enemy,
                           @Mock Entity player1,
                           @Mock Entity player2,
                           @Mock Entity player3,
                           @Mock Body enemyBody,
                           @Mock Body player1Body,
                           @Mock Body player2Body,
                           @Mock Body player3Body) {
        Entity[] playerArray = new Entity[]{player1, player2, player3};
        when(ashley.getEntitiesFor(Family.all(PlayerComponent.class, PhysicsBodyComponent.class).get())).thenReturn(new ImmutableArray<>(new Array<>(playerArray)));

        when(enemyBody.getPosition()).thenReturn(new Vector2(0, 0));
        when(player1Body.getPosition()).thenReturn(new Vector2(2, 0));
        when(player2Body.getPosition()).thenReturn(new Vector2(0, 1));
        when(player3Body.getPosition()).thenReturn(new Vector2(3, 1));
        TargetingComponent targetingComponent = new TargetingComponent();
        when(enemy.getComponent(TargetingComponent.class)).thenReturn(targetingComponent);

        when(enemy.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(enemyBody));
        when(player1.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(player1Body));
        when(player2.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(player2Body));
        when(player3.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(player3Body));

        EnemyTargetingSystem enemyTargetingSystem = new EnemyTargetingSystem(ashley);
        enemyTargetingSystem.processEntity(enemy, 0.1f);

        assertEquals(targetingComponent.target, player2);
        verify(enemyBody, times(1)).setTransform(eq(new Vector2(0, 0)), eq(90.0f));
    }

    @Test
    void testProcessEntityNoPlayers(@Mock Engine ashley,
                                    @Mock Entity enemy,
                                    @Mock Body enemyBody) {
        when(ashley.getEntitiesFor(Family.all(PlayerComponent.class, PhysicsBodyComponent.class).get())).thenReturn(new ImmutableArray<>(new Array<>()));

        when(enemyBody.getPosition()).thenReturn(new Vector2(0, 0));
        TargetingComponent targetingComponent = new TargetingComponent();
        when(enemy.getComponent(TargetingComponent.class)).thenReturn(targetingComponent);

        when(enemy.getComponent(PhysicsBodyComponent.class)).thenReturn(new PhysicsBodyComponent(enemyBody));

        EnemyTargetingSystem enemyTargetingSystem = new EnemyTargetingSystem(ashley);
        enemyTargetingSystem.processEntity(enemy, 0.1f);

        assertNull(targetingComponent.target);
    }
}