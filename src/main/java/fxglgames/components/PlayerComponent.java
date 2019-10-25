package fxglgames.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.entity.component.RequiredComponents;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import fxglgames.utils.Utils;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

@Required(MoveComponent.class)
public class PlayerComponent extends Component {
  private PhysicsComponent physicsComponent;
  private MoveComponent moveComponent;
  private AnimatedTexture texture;
  private AnimationChannel animIdle, animWalk;
  
  //private static final int MAX_SPEED = 300;
  //private static final float _acc = 25.0f, _dcc = 20.0f;
  //private float velX = 0.0f, velY = 0.0f;
  
  //public boolean left = false, right = false, up = false, down = false;
  
  public PlayerComponent() {
    Image imageIdle = image("knight/KnightIdle.png");
    Image imageWalk = image("knight/KnightRun.png");
    
    animIdle = new AnimationChannel(imageIdle, Duration.seconds(0.66), 15);
    animWalk = new AnimationChannel(imageWalk, Duration.seconds(0.66), 8);
    
    texture = new AnimatedTexture(animIdle);
    texture.loop();
  }
  
  @Override
  public void onUpdate(double tpf) {
    //updateVelocity();
    updateFacing();
    updateAnimation();
    //moveComponent.move(velX, velY);
    super.onUpdate(tpf);
    
  }
  
  @Override
  public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(entity.getBoundingBoxComponent().getCenterLocal());
    entity.getViewComponent().addChild(texture);
    
  }
  
  
  
//  private void updateVelocity() {
//    //horizontal
//    if (right) {
//      velX += _acc;
//    } else if (left) {
//      velX -= _acc;
//    } else {
//      if (velX > 0) velX -= _dcc;
//      else if (velX < 0) velX += _dcc;
//    }
//    //vertical
//    if (up) {
//      velY -= _acc;
//    } else if (down) {
//      velY += _acc;
//    } else {
//      if (velY > 0) velY -= _dcc;
//      else if (velY < 0) velY += _dcc;
//    }
//
//    velX = Utils.clamp(velX, -MAX_SPEED, MAX_SPEED);
//    velY = Utils.clamp(velY, -MAX_SPEED, MAX_SPEED);
//  }
  
//  private void updateFacing() {
//    if (velX < 0) {
//      getEntity().setScaleX(-1);
//    } else if (velX > 0) {
//      getEntity().setScaleX(1);
//    }
//  }
    
    private void updateFacing() {
      int facing = moveComponent.getFacing();
      if (facing != 0)
        getEntity().setScaleX(facing);
    }
  
//  private void updateAnimation() {
//    if (velX != 0 || velY != 0) {
//      if (texture.getAnimationChannel() != animWalk) {
//        texture.loopAnimationChannel(animWalk);
//      }
//    } else {
//      if (texture.getAnimationChannel() != animIdle) {
//        texture.loopAnimationChannel(animIdle);
//      }
//    }
//  }
  
    private void updateAnimation() {
    if (moveComponent.isMoving()) {
      if (texture.getAnimationChannel() != animWalk) {
        texture.loopAnimationChannel(animWalk);
      }
    } else {
      if (texture.getAnimationChannel() != animIdle) {
        texture.loopAnimationChannel(animIdle);
      }
    }
  }
  
//  public boolean isLeft() {
//    return left;
//  }
//
//  public void setLeft(boolean left) {
//    this.left = left;
//  }
//
//  public boolean isRight() {
//    return right;
//  }
//
//  public void setRight(boolean right) {
//    this.right = right;
//  }
//
//  public boolean isUp() {
//    return up;
//  }
//
//  public void setUp(boolean up) {
//    this.up = up;
//  }
//
//  public boolean isDown() {
//    return down;
//  }
//
//  public void setDown(boolean down) {
//    this.down = down;
//  }
}
