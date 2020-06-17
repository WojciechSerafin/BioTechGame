package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import fxglgames.EnemyType;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.stream.Collectors;

import static com.almasb.fxgl.dsl.FXGL.random;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class HealerBotComponent extends EnemyComponent {
  private int healingRange = 600;
  private int healingStrength = 10;
  
  @Override
  protected void loadAnimations() throws Exception {
    Image imageIdle = image("robot/healer_bot_idle1_sekwencja.png");
    Image imageWalkDown = image("robot/healer_bot_run1_sekwencja.png");
    Image imageWalkUp = image("robot/healer_bot_run1_tyl_sekwencja.png");
    Image imageDeath = image("robot/healer_bot_death1_sekwencja.png");
    Image imageAttack = image("robot/healer_bot_attack1_left_sekwencja.png");
    Image imageAttack2 = image("robot/healer_bot_attack1_right_sekwencja.png");
  
    animations.put("animIdle", new AnimationChannel(imageIdle, Duration.seconds(0.66), 8));
    animations.put("animWalkDown", new AnimationChannel(imageWalkDown, Duration.seconds(0.66), 12));
    animations.put("animWalkUp", new AnimationChannel(imageWalkUp, Duration.seconds(0.66), 12));
    animations.put("animDeath", new AnimationChannel(imageDeath, Duration.seconds(1), 6));
  
    int r = FXGL.random(0,1);
    animations.put("animAttack", new AnimationChannel(r == 0 ? imageAttack : imageAttack2, Duration.seconds(1), 20));
    //animations.put("animAttack2", new AnimationChannel(imageAttack2, Duration.seconds(1), 20));
  }
  
  @Override
  protected void initializeEnemy() throws Exception {
    this.playerFollowingSpeed = 100;
    this.checkForPlayerRadius = 270;
    this.attackRange = 300D;
    this.isAttacking = false;
  }
  
  @Override
  public void onAdded() {
    entity.getComponent(HPComponent.class).setEnemyType(EnemyType.HEALER_BOT);
    super.onAdded();
  }
  
  @Override
  protected void attack() throws Exception {
    FXGL.runOnce(() -> {
      isAttacking = false;
    }, Duration.millis(900));
    FXGL.runOnce(() -> {
      getEnemies().stream()
                  .filter(e -> e.distance(entity) <= healingRange)
                  .collect(Collectors.toList()).forEach(e -> {
                    e.getComponent(HPComponent.class).heal(healingStrength);
                  });
    }, Duration.millis(660));
  }
  
  /*@Override
  protected void updateAnimation() {
    if (!alive) {
      return;
    }
    if (isAttacking) {
      if (isNotAttackingAnimation()) {
        int r = FXGL.random(0,1);
        System.out.println(r == 0 ? "animAttack" : "animAttack2");
        texture.playAnimationChannel(animations.get(r == 0 ? "animAttack" : "animAttack2"));
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
    if (!isAttacking && isAttackingAnimation()) {
      texture.loopAnimationChannel(animations.get("animIdle"));
    }
  }
  private boolean isAttackingAnimation() {
    return (texture.getAnimationChannel() == animations.get("animAttack")) ||
           (texture.getAnimationChannel() == animations.get("animAttack2"));
  }
  private boolean isNotAttackingAnimation() {
    return (texture.getAnimationChannel() != animations.get("animAttack")) &&
           (texture.getAnimationChannel() != animations.get("animAttack2"));
  }*/
}

