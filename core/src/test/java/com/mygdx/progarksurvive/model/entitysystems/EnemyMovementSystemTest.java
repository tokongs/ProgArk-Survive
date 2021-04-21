package com.mygdx.progarksurvive.model.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.progarksurvive.model.entitycomponents.PhysicsBodyComponent;
import com.mygdx.progarksurvive.model.entitycomponents.TargetingComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnemyMovementSystemTest {

    @Test
    void testProcessEntityNoTarget(@Mock Entity entity,  @Mock Body body) {
        PhysicsBodyComponent pc = new PhysicsBodyComponent(body);
        TargetingComponent tc = new TargetingComponent();
        when(entity.getComponent(PhysicsBodyComponent.class)).thenReturn(pc);
        when(entity.getComponent(TargetingComponent.class)).thenReturn(tc);

        EnemyMovementSystem system = new EnemyMovementSystem();
        system.processEntity(entity, 0.01f);

        verify(body, times(1)).setLinearVelocity(0, 0);
    }

    @Test
    void testProcessEntityWithTarget(@Mock Entity entity, @Mock Body body, @Mock Entity target, @Mock Body targetBody) {
        PhysicsBodyComponent pc = new PhysicsBodyComponent(body);
        TargetingComponent tc = new TargetingComponent();
        PhysicsBodyComponent targetPc = new PhysicsBodyComponent(targetBody);
        tc.target = target;

        when(body.getPosition()).thenReturn(new Vector2(0, 0));
        when(targetBody.getPosition()).thenReturn(new Vector2(1, 0));

        when(entity.getComponent(PhysicsBodyComponent.class)).thenReturn(pc);
        when(entity.getComponent(TargetingComponent.class)).thenReturn(tc);
        when(target.getComponent(PhysicsBodyComponent.class)).thenReturn(targetPc);

        EnemyMovementSystem system = new EnemyMovementSystem();
        system.processEntity(entity, 0.01f);

        verify(body, times(1)).setLinearVelocity(new Vector2(50, 0));
    }
}