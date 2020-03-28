package fxglgames.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import fxglgames.BioTechApp;
import fxglgames.EntityType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.almasb.fxgl.dsl.FXGL.*;

public class EnemyComponent extends Component {

  private Entity player;
  private PhysicsComponent physics;
  private int velX = 0, velY = 0;
  private int playerFollowingSpeed = 200;
  private int checkForPlayerRadius = 300;
  private double distanceToPlayer;

  @Override
  public void onUpdate(double tpf) {
    
    distanceToPlayer = getPlayer().distance(entity);
    if (distanceToPlayer <= checkForPlayerRadius) {
      double dx = getPlayer().getCenter().getX() - entity.getCenter().getX();
      double dy = getPlayer().getCenter().getY() - entity.getCenter().getY();
      
      velX = (int)(Math.sin(dx/distanceToPlayer) * playerFollowingSpeed);
      velY = (int)(Math.sin(dy/distanceToPlayer) * playerFollowingSpeed);
      
    } else {
      if (random(0, 1000) < 10) {
        velX = random(-100, 100);
        velY = random(-100, 100);
      }
    }
    physics.setVelocityY(velY);
    physics.setVelocityX(velX);
  }
  
  @Override
  public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(entity.getCenter());
    if (BioTechApp.DEBUG) {
      entity.getViewComponent().addChild(getFollowRangeCircle());
    }
    super.onAdded();
  }
  
  private Entity getPlayer() {
    if (player == null) {
      player = getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0);
    }
    return player;
  }
  
  private Circle getFollowRangeCircle() {
    Circle c = new Circle(entity.getWidth()/2, entity.getHeight()/2, this.checkForPlayerRadius);
    c.setStroke(Color.YELLOW);
    c.setFill(Color.TRANSPARENT);
    return c;
  }
}
