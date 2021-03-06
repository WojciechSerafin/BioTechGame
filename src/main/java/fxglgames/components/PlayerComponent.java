package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
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
import static com.almasb.fxgl.dsl.FXGLForKtKt.runOnce;

@Required(MoveComponent.class)
public class PlayerComponent extends Component {
  private Boolean alive = true;
  private Boolean isAttacking = false;
  private MoveComponent moveComponent;
  private AnimatedTexture texture;
  private HashMap<String, AnimationChannel> animations = new HashMap<String, AnimationChannel>();
  private int playerViewRadius = 2000;

  
  public PlayerComponent() {
    Image imageIdle = image("robot/hero_idle_sekwencja.png");
    Image imageWalkDown = image("robot/hero_run_sekwencja.png");
    Image imageWalkUp = image("robot/hero_run_tyl_sekwencja.png");
    Image imageDeath = image("robot/hero_death_sekwencja.png");
    Image imageAttack = image("robot/hero_attack_sekwencja.png");

    animations.put("animIdle", new AnimationChannel(imageIdle, Duration.seconds(0.66), 10));
    animations.put("animWalkDown", new AnimationChannel(imageWalkDown, Duration.seconds(0.66), 10));
    animations.put("animWalkUp", new AnimationChannel(imageWalkUp, Duration.seconds(0.66), 10));
    animations.put("animDeath", new AnimationChannel(imageDeath, Duration.seconds(1), 7));
    animations.put("animAttack", new AnimationChannel(imageAttack, Duration.seconds(0.5), 12));

    texture = new AnimatedTexture(animations.get("animIdle"));
    texture.loop();
    
  }
  
  @Override
  public void onUpdate(double tpf) {
    updateAnimation();
    super.onUpdate(tpf);
  }
  
  @Override
  public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(entity.getBoundingBoxComponent().getCenterLocal());
    if (BioTechApp.DEBUG) {
      entity.getViewComponent().addChild(getViewRangeField());
    }
    entity.getViewComponent().addChild(texture);
  }
  

  
  private void updateAnimation() {
    if (!alive) {
      return;
    }
    if (isAttacking) {
      if (texture.getAnimationChannel() != animations.get("animAttack")) {
        texture.loopAnimationChannel(animations.get("animAttack"));
      }
      FXGL.runOnce(() -> {
        isAttacking = false;
      }, Duration.seconds(0.5));
    } else if (moveComponent.isMoving()) {
      int facing = moveComponent.getFacing();
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
  public void playDeathAnimation() {
    this.alive = false;
    if (texture.getAnimationChannel() != animations.get("animDeath")) {
      texture.playAnimationChannel(animations.get("animDeath"));
    }
  }
  
  public void setIsAttacking(Boolean isAttacking) {
    this.isAttacking = isAttacking;
  }
}
