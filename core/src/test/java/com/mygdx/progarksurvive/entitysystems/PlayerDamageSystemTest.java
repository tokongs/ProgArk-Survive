package com.mygdx.progarksurvive.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.utils.Collision;
import com.mygdx.progarksurvive.entitycomponents.CollisionComponent;
import com.mygdx.progarksurvive.entitycomponents.EnemyComponent;
import com.mygdx.progarksurvive.entitycomponents.HealthComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerDamageSystemTest {

    @Test
    void processEntityWithoutCollision(@Mock Entity entity) {
        HealthComponent healthComponent = new HealthComponent(1000);
        when(entity.getComponent(CollisionComponent.class)).thenReturn(new CollisionComponent());
        lenient().when(entity.getComponent(HealthComponent.class)).thenReturn(healthComponent);
        PlayerDamageSystem system = new PlayerDamageSystem();
        system.processEntity(entity, 0.1f);
        assertEquals(1000, healthComponent.health);
    }

    @Test
    void processEntityCollisionWithNonEnemyEntity(@Mock Entity entity, @Mock Entity otherEntity) {
        HealthComponent healthComponent = new HealthComponent(1000);
        CollisionComponent collisionComponent = new CollisionComponent();
        collisionComponent.collisionEntity = otherEntity;
        when(entity.getComponent(CollisionComponent.class)).thenReturn(collisionComponent);
        lenient().when(entity.getComponent(HealthComponent.class)).thenReturn(healthComponent);
        when(otherEntity.getComponent(EnemyComponent.class)).thenReturn(null);

        PlayerDamageSystem system = new PlayerDamageSystem();
        system.processEntity(entity, 0.1f);
        assertEquals(1000, healthComponent.health);
    }

    @Test
    void processEntityCollisionWithEnemyEntity(@Mock Entity entity, @Mock Entity otherEntity) {
        HealthComponent healthComponent = new HealthComponent(1000);
        CollisionComponent collisionComponent = new CollisionComponent();
        collisionComponent.collisionEntity = otherEntity;
        when(entity.getComponent(CollisionComponent.class)).thenReturn(collisionComponent);
        lenient().when(entity.getComponent(HealthComponent.class)).thenReturn(healthComponent);
        when(otherEntity.getComponent(EnemyComponent.class)).thenReturn(new EnemyComponent());

        PlayerDamageSystem system = new PlayerDamageSystem();
        system.processEntity(entity, 0.1f);
        assertEquals(900, healthComponent.health);
    }
}