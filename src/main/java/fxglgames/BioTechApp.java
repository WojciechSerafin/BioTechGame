package fxglgames;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.ui.UI;
import fxglgames.UI.GameMenu;
import fxglgames.UI.MainMenu;
import fxglgames.components.*;
import fxglgames.subscenes.ListOrMessageSubScene;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;


import static com.almasb.fxgl.dsl.FXGL.*;

public class BioTechApp extends GameApplication {
  public static final boolean DEBUG = true;
  
  private Entity player;
  private MoveComponent moveComponent;
  private PlayerComponent playerComponent;
  private AttacksComponent attacksComponent;
  private BioTechController uiController;
  
  public static String readableFileName;
  
  @Override
  protected void initSettings(GameSettings gameSettings) {
    gameSettings.setHeight(540);
    gameSettings.setWidth(960);
    gameSettings.setFullScreenAllowed(true);
    gameSettings.setFullScreenFromStart(true);
    gameSettings.setTitle("The Bio Tech Game");
    gameSettings.setManualResizeEnabled(false);
    gameSettings.setVersion("0.1");
    gameSettings.setGameMenuEnabled(true);
    gameSettings.setMainMenuEnabled(true);
    gameSettings.setSceneFactory(new SceneFactory() {
      @Override
      public FXGLMenu newMainMenu() {
        return new MainMenu();
      }
      @Override
      public FXGLMenu newGameMenu() {
        return new GameMenu();
      }
    });
  }

  @Override
  protected void initInput() {
    initPlayerMovement();
  }

  @Override
  protected void initGame() {
    Image image = new Image("assets/textures/cursor.png");
    getGameScene().setCursor(image,new Point2D(3D,3D));
    getGameWorld().addEntityFactory(new GameEntityFactory());
    setLevelFromMap("tmx/test.tmx");
    spawn("background", 0, 0);
    //spawn("upperBackground", 0, 0);
    player = getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0);
    moveComponent = player.getComponent(MoveComponent.class);
    playerComponent = player.getComponent(PlayerComponent.class);
    attacksComponent = player.getComponent(AttacksComponent.class);
    
    getGameScene().getViewport().bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
    getGameScene().getViewport().setLazy(true);
    getGameScene().getViewport().setBounds(0, 0, 5760, 5760);
  }

  @Override
  protected void initUI() {
    uiController = new BioTechController(getGameScene());
    UI ui = getAssetLoader().loadUI("main.fxml", uiController);
  
    StringProperty sp = new SimpleStringProperty();
    sp.setValue("Nazwa lokacji");
    uiController.getNazwaLokacji().textProperty().bind(sp);
    
    getGameScene().addUI(ui);
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
    physics.addCollisionHandler(new CollisionHandler(EntityType.ENEMY_BULLET, EntityType.WALL) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        a.removeFromWorld();
      }
    });
    physics.addCollisionHandler(new CollisionHandler(EntityType.BOUNCING_BULLET, EntityType.WALL) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        ProjectileComponent component = a.getComponent(ProjectileComponent.class);
        Point2D dir = new Point2D(player.getCenter().getX() - a.getX() - a.getWidth() / 2,
          player.getCenter().getY() - a.getY() - a.getHeight() / 2);
        component.setDirection(dir);
      }
    });
    physics.addCollisionHandler(new CollisionHandler(EntityType.BOUNCING_BULLET, EntityType.FAKE_WALL) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        ProjectileComponent component = a.getComponent(ProjectileComponent.class);
        Point2D dir = new Point2D(player.getCenter().getX() - a.getX() - a.getWidth() / 2,
          player.getCenter().getY() - a.getY() - a.getHeight() / 2);
        component.setDirection(dir);
      }
    });
    physics.addCollisionHandler(new CollisionHandler(EntityType.OIL_POOL, EntityType.PLAYER) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        a.getComponent(OilPoolComponent.class).dealDamage(b);
      }
    });
    physics.addCollisionHandler(new CollisionHandler(EntityType.BOUNCING_BULLET, EntityType.PLAYER) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        Integer damage = a.getComponent(BulletComponent.class).getDamage();
        b.getComponent(HPComponent.class).hit(damage);
        a.removeFromWorld();
        spawn("OilPool", player.getCenter());
      }
    });
    physics.addCollisionHandler(new CollisionHandler(EntityType.BULLET, EntityType.BOUNCING_BULLET) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        a.removeFromWorld();
        b.removeFromWorld();
      }
    });
    physics.addCollisionHandler(new CollisionHandler(EntityType.BULLET, EntityType.FAKE_WALL) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        a.removeFromWorld();
        b.getComponent(FakeWallComponent.class).setToDelete(Boolean.TRUE);
      }
    });
    physics.addCollisionHandler(new CollisionHandler(EntityType.ENEMY_BULLET, EntityType.FAKE_WALL) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        a.removeFromWorld();
      }
    });
    physics.addCollisionHandler(new CollisionHandler(EntityType.BULLET, EntityType.ENEMY) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        Integer damage = a.getComponent(BulletComponent.class).getDamage();
        b.getComponent(HPComponent.class).hit(damage);
        a.removeFromWorld();
      }
    });
    physics.addCollisionHandler(new CollisionHandler(EntityType.ENEMY_BULLET, EntityType.PLAYER) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        Integer damage = a.getComponent(BulletComponent.class).getDamage();
        b.getComponent(HPComponent.class).hit(damage);
        a.removeFromWorld();
      }
    });
    physics.addCollisionHandler(new CollisionHandler(EntityType.NOTE, EntityType.PLAYER) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        readableFileName = a.getComponent(NoteMessageComponent.class).getMessageFileName();
      }
  
      @Override
      protected void onCollisionEnd(Entity a, Entity b) {
        readableFileName = null;
      }
    });
    physics.addCollisionHandler(new CollisionHandler(EntityType.MESSAGE, EntityType.PLAYER) {
      @Override
      protected void onCollisionBegin(Entity a, Entity b) {
        readableFileName = a.getComponent(NoteMessageComponent.class).getMessageFileName();
      }
    
      @Override
      protected void onCollisionEnd(Entity a, Entity b) {
        readableFileName = null;
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
    
    getInput().addAction(new UserAction("Read") {
      @Override
      protected void onActionBegin() {
        if (readableFileName != null) {
          getSceneService().pushSubScene(new ListOrMessageSubScene(readableFileName));
        }
      }
    }, KeyCode.E);
  }
  
}
