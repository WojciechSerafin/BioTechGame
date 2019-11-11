package fxglgames.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;


import java.util.Date;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGL.*;

@Required(MoveComponent.class)
public class PlayerComponent extends Component {
  private MoveComponent moveComponent;
  private AnimatedTexture texture;
  private AnimationChannel animIdle, animWalk;

  
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
    updateFacing();
    updateAnimation();
    super.onUpdate(tpf);
    
  }
  
  @Override
  public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(entity.getBoundingBoxComponent().getCenterLocal());
    entity.getViewComponent().addChild(texture);
    
  }
  
  private void updateFacing() {
    int facing = moveComponent.getFacing();
    if (facing != 0)
      getEntity().setScaleX(facing);
  }
  
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
  
}
