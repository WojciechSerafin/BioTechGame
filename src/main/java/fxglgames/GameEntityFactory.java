package fxglgames;

import com.almasb.fxgl.dsl.FXGL;
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
import fxglgames.components.*;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.paint.Color;


import static com.almasb.fxgl.dsl.FXGL.*;

public class GameEntityFactory implements EntityFactory {

  @Spawns("wall")
  public Entity newWall(SpawnData data) {
    Rectangle r = new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"));
    r.setStroke(Color.RED);
    r.setFill(Color.TRANSPARENT);
    return entityBuilder().from(data)
                          .type(EntityType.WALL)
                          .view(r)
                          .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
                          .with(new PhysicsComponent())
                          .with(new CollidableComponent(true))
                          .build();
  }
  @Spawns("")
  public Entity newNothing(SpawnData data) {
    return entityBuilder().from(data)
            .type(EntityType.NOTHING)
            //.viewWithBBox(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.RED))
            //.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
            //.with(new PhysicsComponent())
            //.with(new CollidableComponent(true))
            .build();
  }
  @Spawns("fakeWall")
  public Entity newFakeWall(SpawnData data) {
    return entityBuilder().from(data)
            .type(EntityType.FAKE_WALL)
            //.viewWithBBox(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.RED))
            .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
            .with(new PhysicsComponent())
            .with(new CollidableComponent(true))
            .build();
  }

  @Spawns("player")
  public Entity newPlayer(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);

    HPComponent hpComponent = new HPComponent(-18, -20, Color.YELLOW,0.0,100.0);
    //hpComponent.getBar().setScaleX(0.3d);
    //hpComponent.getBar().setScaleY(0.3d);
    
    return entityBuilder().from(data)
                          .type(EntityType.PLAYER)
                          .viewWithBBox("robot/robot64.png")
                          //.viewWithBBox(new Rectangle(64,64, Color.BLUE))
                          //.bbox(new HitBox(BoundingShape.box(24,30)))
                          .with(physics)
                          .with(hpComponent)
                          .with(new MoveComponent())
                          .with(new PlayerComponent())
                          .with(new AttacksComponent(-35,35,Color.WHITE))
                          .with(new CollidableComponent(true))
                          .build();
  }

  @Spawns("enemy")
  public Entity newEnemy(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);
  
    HPComponent hpComponent = new HPComponent(-18, -20,Color.RED,0.0,100.0);

    return entityBuilder().from(data)
                          .type(EntityType.ENEMY)
                          .viewWithBBox("robot/robot64.png")
                          //.viewWithBBox(new Rectangle(64,64, Color.RED))
                          //.viewWithBBox(texture("skeleton/SkeletonIdle.png")
                              //.toAnimatedTexture(11, Duration.seconds(1)).loop())
                          .with(physics)
                          .with(hpComponent)
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
                          .with(new ProjectileComponent(data.get("dir"), (Double)data.get("bulletSpeed")))
                          .with(new OffscreenCleanComponent())
                          .with(new BulletComponent(30))
                          .with(new CollidableComponent(true))
                          .build();
  }
  @Spawns("background")
  public Entity newBackground(SpawnData data) {
    return entityBuilder().from(data)
                          .view("background/Background5760.png")
                          //.view(new Rectangle(5744 + FXGL.getAppWidth(),5744 + FXGL.getAppHeight(), Color.BLACK))
                          .zIndex(-1)
                          .with(new IrremovableComponent())
                          .build();
  }

  @Spawns("upperBackground")
  public Entity newUpperBackground(SpawnData data) {
    return entityBuilder().from(data)
            .view("background/UpperBackground5760.png")
            //.view(new Rectangle(5744 + FXGL.getAppWidth(),5744 + FXGL.getAppHeight(), Color.BLACK))
            .zIndex(2)
            .with(new IrremovableComponent())
            .build();
  }

  @Spawns("AttackIndicator")
  public  Entity newAttackIndicator(SpawnData data) {
    return entityBuilder().from(data)
                          .view(new Rectangle(100,10, Color.WHITE))
                          .zIndex(1)
                          .with(new IrremovableComponent())
                          .build();
  }
}
