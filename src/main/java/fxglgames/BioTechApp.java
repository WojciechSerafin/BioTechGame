package fxglgames;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsWorld;
import fxglgames.components.MoveComponent;
import fxglgames.components.PlayerComponent;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;


import static com.almasb.fxgl.dsl.FXGL.*;

public class BioTechApp extends GameApplication {
  private Entity player;
  //private PlayerComponent playerComponent;
  private MoveComponent mc;
  
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
    mc = player.getComponent(MoveComponent.class);
    //playerComponent = player.getComponent(PlayerComponent.class);
    
    getGameScene().getViewport().bindToEntity(player, 1000, 500);
  }
  
  @Override
  protected void initPhysics() {
    PhysicsWorld physics = getPhysicsWorld();
    physics.setGravity(0,0);
  }
  
  public static void main(String[] args) {
    launch(args);
  }
  
  
  private void initPlayerMovement() {
    getInput().addAction(new UserAction("Left") {
      @Override
      protected void onAction() {
        mc.moveLeft();
        //playerComponent.setLeft(true);
      }
    
      @Override
      protected void onActionEnd() {
        mc.stopHorizontal();
        //playerComponent.setLeft(false);
      }
    }, KeyCode.A);
  
  
    getInput().addAction(new UserAction("Right") {
      @Override
      protected void onAction() {
        mc.moveRight();
        //playerComponent.setRight(true);
      }
    
      @Override
      protected void onActionEnd() {
        mc.stopHorizontal();
        //playerComponent.setRight(false);
      }
    }, KeyCode.D);
  
  
    getInput().addAction(new UserAction("Up") {
      @Override
      protected void onAction() {
        mc.moveUp();
        //playerComponent.setUp(true);
      }
    
      @Override
      protected void onActionEnd() {
        mc.stopVertical();
        //playerComponent.setUp(false);
      }
    }, KeyCode.W);
  
  
    getInput().addAction(new UserAction("Down") {
      @Override
      protected void onAction() {
        mc.moveDown();
        //playerComponent.setDown(true);
      }
    
      @Override
      protected void onActionEnd() {
        mc.stopVertical();
        //playerComponent.setDown(false);
      }
    }, KeyCode.S);
  }
  
}
