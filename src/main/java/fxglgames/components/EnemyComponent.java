package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import fxglgames.BioTechApp;
import fxglgames.EnemyType;
import fxglgames.EntityType;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.security.spec.ECField;
import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class EnemyComponent extends Component {
  private Boolean alive = true;
  private Entity player;
  private PhysicsComponent physics;
  private int velX = 0, velY = 0;
  protected int playerFollowingSpeed = 200;
  protected int checkForPlayerRadius = 300;
  protected Double attackRange = 70D;
  protected Boolean isAttacking = false;
  private double distanceToPlayer;
  private Line l;
  protected AnimatedTexture texture;
  protected HashMap<String, AnimationChannel> animations = new HashMap<String, AnimationChannel>();
  
  Logger logger = Logger.getLogger(EnemyComponent.class);
  public EnemyComponent() {
    try {
      loadAnimations();
      initializeEnemy();
    } catch (Exception e) {
      logger.error("Błąd przy tworzeniu Enemy", e);
    }
    texture = new AnimatedTexture(animations.get("animIdle"));
    texture.loop();
  }
  
  @Override
  public void onUpdate(double tpf) {
    distanceToPlayer = getPlayer().distance(entity);
    if (!alive) {
      velX = 0;
      velY = 0;
    } else if (distanceToPlayer <= checkForPlayerRadius) {
      if (getPlayer().isWithin(getAttackAreaR2D())) {
        velX = 0;
        velY = 0;
        if (texture.getAnimationChannel() != animations.get("animAttack"))
          isAttacking = true;
      } else {
        double dx = getPlayer().getCenter().getX() - entity.getCenter().getX();
        double dy = getPlayer().getCenter().getY() - entity.getCenter().getY();
  
        l = new Line(0 + entity.getWidth() / 2, 0 + entity.getHeight() / 2,
          getPlayer().getX() - entity.getX() + getPlayer().getWidth() / 2,
          getPlayer().getY() - entity.getY() + getPlayer().getHeight() / 2);
  
        int followDirection = 1;
        for (Entity e : getGameWorld().getEntitiesByType(EntityType.WALL, EntityType.FAKE_WALL)) {
          if (l.intersects(e.getX() - entity.getX(), e.getY() - entity.getY(), e.getWidth(), e.getHeight())) {
            followDirection = 0;
          }
        }
  
        velX = (int) (Math.sin(dx / distanceToPlayer) * playerFollowingSpeed) * followDirection;
        velY = (int) (Math.sin(dy / distanceToPlayer) * playerFollowingSpeed) * followDirection;
      }
    } else {
      if (random(0, 1000) < 10) {
        velX = random(-100, 100);
        velY = random(-100, 100);
      }
    }
    physics.setVelocityY(velY);
    physics.setVelocityX(velX);
    updateAnimation();
  }
  public int getFacing() {
    if (physics.getVelocityY() > 0)
      return 1;
    else if (physics.getVelocityY() < 0)
      return -1;
    else
      return 0;
  }
  private void updateAnimation() {
    if (!alive) {
      return;
    }
    if (isAttacking) {
      if (texture.getAnimationChannel() != animations.get("animAttack")) {
        texture.playAnimationChannel(animations.get("animAttack"));
        try {
          attack();
        } catch (Exception e) {
          logger.error("Błąd przy ataku", e);
        }
      }
    } else if (physics.isMoving()) {
      int facing = getFacing();
      if (facing == -1) {
        if (texture.getAnimationChannel() != animations.get("animWalkUp")) {
          texture.loopAnimationChannel(animations.get("animWalkUp"));
        }
      } else {
        if (texture.getAnimationChannel() != animations.get("animWalkDown")) {
          texture.loopAnimationChannel(animations.get("animWalkDown"));
        }
      }
    } else {
      if (texture.getAnimationChannel() != animations.get("animIdle")) {
        texture.loopAnimationChannel(animations.get("animIdle"));
      }
    }
    if (!isAttacking && texture.getAnimationChannel() == animations.get("animAttack")) {
      texture.loopAnimationChannel(animations.get("animIdle"));
    }
  }
  
  @Override
  public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(entity.getCenter());
    if (BioTechApp.DEBUG) {
      entity.getViewComponent().addChild(getFollowRangeCircle());
      entity.getViewComponent().addChild(getAttackArea());
    }
    entity.getViewComponent().addChild(texture);
    super.onAdded();
  }
  
  protected Entity getPlayer() {
    if (player == null) {
      player = getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0);
    }
    return player;
  }
  
  protected Circle getFollowRangeCircle() {
    Circle c = new Circle(entity.getWidth()/2, entity.getHeight()/2, this.checkForPlayerRadius);
    c.setStroke(Color.YELLOW);
    c.setFill(Color.TRANSPARENT);
    return c;
  }
  
  protected Shape getAttackArea() {
    Rectangle r = new Rectangle(entity.getWidth()/2 - this.attackRange/2,
      entity.getHeight()/2 - this.attackRange/2,this.attackRange,
      this.attackRange);
    r.setStroke(Color.RED);
    r.setFill(Color.TRANSPARENT);
    return r;
  }
  
  protected Rectangle2D getAttackAreaR2D() {
    Rectangle2D r = new Rectangle2D(entity.getX() + entity.getWidth()/2 - this.attackRange/2, entity.getY() +
      entity.getHeight()/2 - this.attackRange/2,this.attackRange,
      this.attackRange);
    return r;
  }
  
  public void playDeathAnimation() {
    this.alive = false;
    texture.playAnimationChannel(animations.get("animDeath"));
  }
  
  protected void loadAnimations() throws Exception {
    throw new Exception("Nie zaimplementowane wczytywanie animacji.");
  };
  
  protected void initializeEnemy() throws Exception {
    throw new Exception("Nie zaimplementowana inicjalizacja przeciwnika.");
  }
  
  protected void attack() throws Exception {
    throw new Exception("Nie zaimplementowana obsługa ataków.");
  }
}
