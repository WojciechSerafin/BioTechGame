package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import fxglgames.EnemyType;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class BoxerBotComponent extends EnemyComponent {
  
  @Override
  protected void loadAnimations() throws Exception {
    Image imageIdle = image("robot/boxer_bot_idle.png");
    Image imageWalkDown = image("robot/boxer_bot_run.png");
    Image imageWalkUp = image("robot/boxer_bot_run_tyl.png");
    Image imageDeath = image("robot/boxer_bot_death.png");
    Image imageAttack = image("robot/boxer_bot_attack.png");
  
    animations.put("animIdle", new AnimationChannel(imageIdle, Duration.seconds(0.66), 5));
    animations.put("animWalkDown", new AnimationChannel(imageWalkDown, Duration.seconds(0.66), 16));
    animations.put("animWalkUp", new AnimationChannel(imageWalkUp, Duration.seconds(0.66), 16));
    animations.put("animDeath", new AnimationChannel(imageDeath, Duration.seconds(1), 8));
    animations.put("animAttack", new AnimationChannel(imageAttack, Duration.seconds(1), 20));
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
    entity.getComponent(HPComponent.class).setEnemyType(EnemyType.BOXER_BOT);
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

