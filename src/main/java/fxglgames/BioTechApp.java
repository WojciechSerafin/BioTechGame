package fxglgames;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import fxglgames.components.AttacksComponent;
import fxglgames.components.MoveComponent;
import fxglgames.components.PlayerComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;


import static com.almasb.fxgl.dsl.FXGL.*;

public class BioTechApp extends GameApplication {
  private Entity player;
  private MoveComponent moveComponent;
  private PlayerComponent playerComponent;
  private AttacksComponent attacksComponent;

  @Override
  protected void initSettings(GameSettings gameSettings) {
    gameSettings.setHeight(1080);
    gameSettings.setWidth(1920);
    gameSettings.setTitle("The Game");
    gameSettings.setManualResizeEnabled(false);
    gameSettings.setFullScreenAllowed(true);
    gameSettings.setVersion("0.1");
  }

  @Override
  protected void initInput() {
    initPlayerMovement();
  }

  @Override
  protected void initGame() {
    getGameWorld().addEntityFactory(new GameEntityFactory());
    setLevelFromMap("tmx/TestTiled.tmx");
    spawn("background");
    player = getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0);
    moveComponent = player.getComponent(MoveComponent.class);
    playerComponent = player.getComponent(PlayerComponent.class);
    attacksComponent = player.getComponent(AttacksComponent.class);
    
    getGameScene().getViewport().bindToEntity(player, 1000, 500);
  }

  @Override
  protected void initUI() {

  }

  @Override
  protected void initPhysics() {
    PhysicsWorld physics = getPhysicsWorld();
    physics.setGravity(0,0);
    
    physics.addCollisionHandler(new CollisionHandler(EntityType.BULLET, EntityType.WALL) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        a.removeFromWorld();
      }
    });
    
  }
  
  
  
  public static void main(String[] args) {
    launch(args);
  }
  
  
  private void initPlayerMovement() {
    getInput().addAction(new UserAction("Left") {
      @Override
      protected void onAction() {
        moveComponent.moveLeft();
      }
    
      @Override
      protected void onActionEnd() {
        moveComponent.stopHorizontal();
      }
    }, KeyCode.A);
  
  
    getInput().addAction(new UserAction("Right") {
      @Override
      protected void onAction() {
        moveComponent.moveRight();
      }
    
      @Override
      protected void onActionEnd() {
        moveComponent.stopHorizontal();
      }
    }, KeyCode.D);
  
  
    getInput().addAction(new UserAction("Up") {
      @Override
      protected void onAction() {
        moveComponent.moveUp();
      }
    
      @Override
      protected void onActionEnd() {
        moveComponent.stopVertical();
      }
    }, KeyCode.W);
  
  
    getInput().addAction(new UserAction("Down") {
      @Override
      protected void onAction() {
        moveComponent.moveDown();
      }
    
      @Override
      protected void onActionEnd() {
        moveComponent.stopVertical();
      }
    }, KeyCode.S);
    
    getInput().addAction(new UserAction("BasicAttack") {
      @Override
      protected void onAction() {
        super.onAction();
        attacksComponent.attack();
      }
  
      @Override
      protected void onActionBegin() {
        super.onActionBegin();
      }
  
      @Override
      protected void onActionEnd() {
        super.onActionEnd();
      }
    }, MouseButton.PRIMARY);

    getInput().addAction(new UserAction("ResetAttack") {
      @Override
      protected void onAction() {
        super.onAction();
        attacksComponent.resetAttack();
      }

      @Override
      protected void onActionBegin() {
        super.onActionBegin();
      }

      @Override
      protected void onActionEnd() {
        super.onActionEnd();
      }
    }, MouseButton.SECONDARY);
  }
  
}
