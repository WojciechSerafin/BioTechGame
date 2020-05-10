package fxglgames;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.OffscreenPauseComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsUnitConverter;
import com.almasb.fxgl.physics.box2d.collision.shapes.Shape;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import fxglgames.components.*;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;


import static com.almasb.fxgl.dsl.FXGL.*;

public class GameEntityFactory implements EntityFactory {

  @Spawns("wall")
  public Entity newWall(SpawnData data) {
    EntityBuilder entityBuilder = entityBuilder().from(data)
      .type(EntityType.WALL)
      .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
      .with(new PhysicsComponent())
      .with(new CollidableComponent(true));
    
    if (BioTechApp.DEBUG) {
      Rectangle r = new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"));
      r.setStroke(Color.RED);
      r.setFill(Color.TRANSPARENT);
      //entityBuilder.view(r);
    }
    
    return entityBuilder.build();
  }

  @Spawns("fakeWall")
  public Entity newFakeWall(SpawnData data) {
    return entityBuilder().from(data)
      .type(EntityType.FAKE_WALL)
      //.viewWithBBox(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.RED))
      .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
      .with(new PhysicsComponent())
      .with(new CollidableComponent(true))
      .with(new FakeWallComponent())
      .build();
  }

  @Spawns("chest")
  public Entity newChest(SpawnData data) {
    String textureName = data.get("textureName");
    return entityBuilder().from(data)
      .type(EntityType.CHEST)
      .viewWithBBox("furniture/chest_" + textureName + ".png")
      //.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
      .with(new PhysicsComponent())
      .with(new CollidableComponent(true))
      .build();
  }
  
  @Spawns("furniture")
  public Entity newFurniture(SpawnData data) {
    String textureName = data.get("textureName");
    return entityBuilder().from(data)
      .type(EntityType.FURNITURE)
      .viewWithBBox("furniture/furniture_" + textureName + ".png")
      //.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
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

  @Spawns("player")
  public Entity newPlayer(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);

    HPComponent hpComponent = new HPComponent(-18, -20, Color.YELLOW,0.0,100.0);
  
    //String textureName = data.get("textureName");
    return entityBuilder().from(data)
                          .zIndex(2)
                          .type(EntityType.PLAYER)
                          //.viewWithBBox("robot/player_" + textureName + ".png")
                          //.viewWithBBox("robot/enemy_02.png")
                          .bbox(new HitBox(BoundingShape.box(58,64)))
                          .with(physics)
                          .with(hpComponent)
                          .with(new MoveComponent())
                          .with(new PlayerComponent())
                          .with(new AttacksComponent(-35,35,Color.WHITE))
                          .with(new CollidableComponent(true))
                          .with(new ShadowAndLightComponent())
                          .build();
  }

  @Spawns("BoxerBot")
  public Entity newBoxerBot(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);
  
    HPComponent hpComponent = new HPComponent(-18, -20,Color.RED,0.0,100.0);
    //String textureName = data.get("textureName");
    return entityBuilder().from(data)
                          .type(EntityType.ENEMY)
                          //.viewWithBBox("robot/enemy_" + textureName + ".png")
                          //.viewWithBBox("robot/enemy_02.png")
                          .bbox(new HitBox(BoundingShape.box(58,64)))
                          .with(physics)
                          .with(hpComponent)
                          .with((EnemyComponent)new BoxerBotComponent())
                          .with(new CollidableComponent(true))
                          .build();
  }

  @Spawns("AlertBot")
  public Entity newAlertBot(SpawnData data) {
    PhysicsComponent physics = new PhysicsComponent();
    physics.setBodyType(BodyType.DYNAMIC);

    HPComponent hpComponent = new HPComponent(-18, -20,Color.RED,0.0,100.0);
    //String textureName = data.get("textureName");
    return entityBuilder().from(data)
      .type(EntityType.ENEMY)
      //.viewWithBBox("robot/enemy_" + textureName + ".png")
      //.viewWithBBox("robot/enemy_02.png")
      .bbox(new HitBox(BoundingShape.box(58,64)))
      .with(physics)
      .with(hpComponent)
      .with((EnemyComponent)new AlertBotComponent())
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
                          .with(new ProjectileComponent(data.get("dir"), data.get("bulletSpeed")))
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
            .zIndex(3)
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
