package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import fxglgames.EnemyType;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class AlertBotComponent extends EnemyComponent {
  
  @Override
  protected void loadAnimations() throws Exception {
    Image imageIdle = image("robot/alert_bot_idle_sekwencja.png");
    Image imageWalkDown = image("robot/alert_bot_run_sekwencja.png");
    Image imageWalkUp = image("robot/alert_bot_run_tyl_sekwencja.png");
    Image imageDeath = image("robot/alert_bot_death_sekwencja.png");
    Image imageAttack = image("robot/alert_bot_attack_sekwencja.png");
  
    animations.put("animIdle", new AnimationChannel(imageIdle, Duration.seconds(0.66), 6));
    animations.put("animWalkDown", new AnimationChannel(imageWalkDown, Duration.seconds(0.66), 6));
    animations.put("animWalkUp", new AnimationChannel(imageWalkUp, Duration.seconds(0.66), 6));
    animations.put("animDeath", new AnimationChannel(imageDeath, Duration.seconds(1), 7));
    animations.put("animAttack", new AnimationChannel(imageAttack, Duration.seconds(1), 11));
  }
  
  @Override
  protected void initializeEnemy() throws Exception {
    this.playerFollowingSpeed = 200;
    this.checkForPlayerRadius = 300;
    this.attackRange = 70D;
    this.isAttacking = false;
  }
  
  @Override
  public void onAdded() {
    entity.getComponent(HPComponent.class).setEnemyType(EnemyType.ALERT_BOT);
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

