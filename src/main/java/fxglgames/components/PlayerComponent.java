package fxglgames.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import fxglgames.BioTechApp;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


import java.util.HashMap;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

@Required(MoveComponent.class)
public class PlayerComponent extends Component {
  private MoveComponent moveComponent;
  private AnimatedTexture texture;
  private HashMap<String, AnimationChannel> animations = new HashMap<String, AnimationChannel>();
  private int playerViewRadius = 2000;

  
  public PlayerComponent() {
    Image imageIdle = image("knight/KnightIdle.png");
    Image imageWalk = image("knight/KnightRun.png");

    animations.put("animIdle", new AnimationChannel(imageIdle, Duration.seconds(0.66), 15));
    animations.put("animWalk", new AnimationChannel(imageWalk, Duration.seconds(0.66), 8));

    texture = new AnimatedTexture(animations.get("animIdle"));
    texture.loop();
    
  }
  
  @Override
  public void onUpdate(double tpf) {
    //updateFacing();
    //updateAnimation();
    super.onUpdate(tpf);
  }
  
  @Override
  public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(entity.getBoundingBoxComponent().getCenterLocal());
    if (BioTechApp.DEBUG) {
      entity.getViewComponent().addChild(getViewRangeField());
    }
    //entity.getViewComponent().addChild(texture);
  }
  
  private void updateFacing() {
    int facing = moveComponent.getFacing();
    if (facing != 0)
      getEntity().setScaleX(facing);
  }
  
  private void updateAnimation() {
    if (moveComponent.isMoving()) {
      if (texture.getAnimationChannel() != animations.get("animWalk")) {
        texture.loopAnimationChannel(animations.get("animWalk"));
      }
    } else {
      if (texture.getAnimationChannel() != animations.get("animIdle")) {
        texture.loopAnimationChannel(animations.get("animIdle"));
      }
    }
  }
  public Node getViewRangeField() {
    //Circle c = new Circle(entity.getWidth()/2, entity.getHeight()/2, this.playerViewRadius);
    Rectangle c = new Rectangle(entity.getWidth()/2 - getPlayerViewRadius()/2,
                                entity.getHeight()/2 - getPlayerViewRadius()/2,
                                getPlayerViewRadius(),
                                getPlayerViewRadius());
    c.setStroke(Color.GREEN);
    c.setFill(Color.TRANSPARENT);
    return c;
  }
  
  public int getPlayerViewRadius() {
    return playerViewRadius;
  }
}
