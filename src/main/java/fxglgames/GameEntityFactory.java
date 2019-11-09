package fxglgames;

import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import fxglgames.components.BulletComponent;
import fxglgames.components.EnemyComponent;
import fxglgames.components.MoveComponent;
import fxglgames.components.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.paint.Color;


import static com.almasb.fxgl.dsl.FXGL.*;

public class GameEntityFactory implements EntityFactory {

  @Spawns("wall")
  public Entity newWall(SpawnData data) {
    return entityBuilder().from(data)
                          .type(EntityType.WALL)
                          .viewWithBBox(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.RED))
                          .with(new PhysicsComponent())
                          .with(new CollidableComponent(true))
                          .build();
  }

  @Spawns("player")
  public Entity newPlayer(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);
    
    return entityBuilder().from(data)
                          .type(EntityType.PLAYER)
                          .bbox(new HitBox(BoundingShape.box(24,30)))
                          .with(physics)
                          .with(new MoveComponent())
                          .with(new PlayerComponent())
                          .with(new CollidableComponent(true))
                          .build();
  }

  @Spawns("enemy")
  public Entity newEnemy(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);
    
    return entityBuilder().from(data)
                          .type(EntityType.ENEMY)
                          .viewWithBBox(texture("skeleton/SkeletonIdle.png")
                              .toAnimatedTexture(11, Duration.seconds(1)).loop())
                          .with(physics)
                          .with(new EnemyComponent())
                          .with(new CollidableComponent(true))
                          .build();
  }

  @Spawns("bullet")
  public Entity newBullet(SpawnData data) {
    Point2D dir = new Point2D(getInput().getMouseXWorld() - data.getX(),
                             getInput().getMouseYWorld() - data.getY());
    return entityBuilder().from(data)
                          .type(EntityType.BULLET)
                          .viewWithBBox("bullet/bullet.png")
                          .with(new ProjectileComponent(dir, 500))
                          .with(new OffscreenCleanComponent())
                          .with(new BulletComponent())
                          .with(new CollidableComponent(true))
                          .build();
  }
  @Spawns("background")
  public Entity newBackground(SpawnData data) {
    return entityBuilder().from(data)
                          .view(new Rectangle(5744,3240, Color.BLACK))
                          .zIndex(-1)
                          .with(new IrremovableComponent())
                          .build();
  }
}
