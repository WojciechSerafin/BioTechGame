package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import fxglgames.EnemyType;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.stream.Collectors;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class AlertBotComponent extends EnemyComponent {
  private int alertRange = 1200;
  private Boolean isSpawning = false;
  
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
    this.attackRange = 300D;
    this.isAttacking = false;
  }
  
  @Override
  public void onAdded() {
    entity.getComponent(HPComponent.class).setEnemyType(EnemyType.ALERT_BOT);
    super.onAdded();
  }
  
  @Override
  protected void attack() throws Exception {
    if (!isSpawning) {
      isSpawning = true;
      FXGL.runOnce(() -> {
        spawnFriends(1);
        isSpawning = false;
      }, Duration.seconds(10));
    }
    FXGL.runOnce(() -> {
      getEnemies().stream()
                  .filter(e -> e.distance(entity) <= alertRange)
                  .collect(Collectors.toList()).forEach(e -> {
                    e.getComponent(HPComponent.class).getEnemyComponent().alert();
                  });
    }, Duration.millis(660));
    FXGL.runOnce(() -> {
      isAttacking = false;
    }, Duration.millis(1000));
  }
  private void spawnFriends(int numberOfFriendsToSpawn) {
    if (entity == null)
      return;
    double x = entity.getX();
    double y = entity.getY();
    for (int i = 0; i < numberOfFriendsToSpawn; i++) {
      switch(FXGL.random(1,2)) {
        case 1:
          spawn("BoxerBot", x+random(-100, 100), y+random(-100, 100));
          break;
        case 2:
          spawn("AlertBot", x+random(-100, 100), y+random(-100, 100));
          break;
        default:
          break;
      }
    }
  }
}

