package fxglgames.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;

import java.time.Duration;

@Required(PhysicsComponent.class)
public class MoveComponent extends Component {
  private PhysicsComponent physics;
  
  
  private int speed = 300;
  
  public void moveLeft() {
    physics.setVelocityX(-speed);
  }

  public void moveRight() {
    physics.setVelocityX(speed);
  }

  public void moveUp() {
    physics.setVelocityY(-speed);
  }

  public void moveDown() {
    physics.setVelocityY(speed);
  }
  
  public void stopHorizontal() {
    physics.setVelocityX(0);
  }
  
  public void stopVertical() {
    physics.setVelocityY(0);
  }
  
  public void move(float velX, float velY) {
    physics.setVelocityX(velX);
    physics.setVelocityY(velY);
  }
  
  public boolean isMoving() {
    return physics.isMoving();
  }
  public int getFacing() {
    if (physics.getVelocityY() > 0)
      return 1;
    else if (physics.getVelocityY() < 0)
      return -1;
    else
      return 0;
  }
  
}
