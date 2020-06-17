package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import fxglgames.EnemyType;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class GuardianBotComponent extends EnemyComponent {
  private Integer attackDamage = 100;
  @Override
  protected void loadAnimations() throws Exception {
    Image imageIdle = image("robot/guardian_bot_idle_sekwencja.png");
    Image imageWalkDown = image("robot/guardian_bot_run_sekwencja.png");
    Image imageWalkUp = image("robot/guardian_bot_run_tyl_sekwencja.png");
    Image imageDeath = image("robot/guardian_bot_death_sekwencja.png");
    Image imageAttack = image("robot/guardian_bot_attack_sekwencja.png");
  
    animations.put("animIdle", new AnimationChannel(imageIdle, Duration.seconds(0.66), 5));
    animations.put("animWalkDown", new AnimationChannel(imageWalkDown, Duration.seconds(0.66), 12));
    animations.put("animWalkUp", new AnimationChannel(imageWalkUp, Duration.seconds(0.66), 12));
    animations.put("animDeath", new AnimationChannel(imageDeath, Duration.seconds(1), 5));
    animations.put("animAttack", new AnimationChannel(imageAttack, Duration.seconds(1), 16));
  }
  
  @Override
  protected void initializeEnemy() throws Exception {
    this.playerFollowingSpeed = 180;
    this.checkForPlayerRadius = 230;
    this.attackRange = 90D;
    this.isAttacking = false;
  }
  
  @Override
  public void onAdded() {
    entity.getComponent(HPComponent.class).setEnemyType(EnemyType.GUARDIAN_BOT);
    super.onAdded();
  }
  
  @Override
  protected void attack() throws Exception {
    FXGL.runOnce(() -> {
      getPlayer().getComponent(HPComponent.class).hit(attackDamage);
    }, Duration.millis(660));
    FXGL.runOnce(() -> {
      isAttacking = false;
    }, Duration.millis(1050));
  }
}

