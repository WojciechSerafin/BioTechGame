package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import fxglgames.EnemyType;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class DefenderBotComponent extends EnemyComponent {
  
  @Override
  protected void loadAnimations() throws Exception {
    Image imageIdle = image("robot/defender_bot_idle_sekwencja.png");
    Image imageWalkDown = image("robot/defender_bot_run1_sekwencja.png");
    Image imageWalkUp = image("robot/defender_bot_run1_tyl_sekwencja.png");
    Image imageDeath = image("robot/defender_bot_death_sekwencja.png");
    Image imageAttack = image("robot/defender_bot_attack_sekwencja.png");
  
    animations.put("animIdle", new AnimationChannel(imageIdle, Duration.seconds(0.66), 5));
    animations.put("animWalkDown", new AnimationChannel(imageWalkDown, Duration.seconds(0.66), 10));
    animations.put("animWalkUp", new AnimationChannel(imageWalkUp, Duration.seconds(0.66), 10));
    animations.put("animDeath", new AnimationChannel(imageDeath, Duration.seconds(1), 5));
    animations.put("animAttack", new AnimationChannel(imageAttack, Duration.seconds(1), 16));
  }
  
  @Override
  protected void initializeEnemy() throws Exception {
    this.playerFollowingSpeed = 150;
    this.checkForPlayerRadius = 250;
    this.attackRange = 70D;
    this.isAttacking = false;
  }
  
  @Override
  public void onAdded() {
    entity.getComponent(HPComponent.class).setEnemyType(EnemyType.DEFENDER_BOT);
    super.onAdded();
  }
  
  @Override
  protected void attack() throws Exception {
    FXGL.runOnce(() -> {
      getPlayer().getComponent(HPComponent.class).hit(10);
    }, Duration.millis(660));
    FXGL.runOnce(() -> {
      isAttacking = false;
    }, Duration.millis(1050));
  }
}

