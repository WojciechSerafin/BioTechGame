package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.texture.AnimationChannel;
import fxglgames.EnemyType;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class ShooterBotComponent extends EnemyComponent {
  private int alertRange = 1200;
  private Boolean isSpawning = false;
  private Date nextAttack;
  private float attackSpeed = 2.5f;
  private double bulletSpeed = 300;
  
  @Override
  protected void loadAnimations() throws Exception {
    Image imageIdle = image("robot/shooter_bot_idle_sekwencja.png");
    Image imageWalkDown = image("robot/shooter_bot_run_sekwencja.png");
    Image imageWalkUp = image("robot/shooter_bot_run_tyl_sekwencja.png");
    Image imageDeath = image("robot/shooter_bot_death_sekwencja.png");
    Image imageAttack = image("robot/shooter_bot_attack_sekwencja.png");
  
    animations.put("animIdle", new AnimationChannel(imageIdle, Duration.seconds(0.66), 6));
    animations.put("animWalkDown", new AnimationChannel(imageWalkDown, Duration.seconds(0.66), 10));
    animations.put("animWalkUp", new AnimationChannel(imageWalkUp, Duration.seconds(0.66), 10));
    animations.put("animDeath", new AnimationChannel(imageDeath, Duration.seconds(1), 7));
    animations.put("animAttack", new AnimationChannel(imageAttack, Duration.seconds(1), 14));
  }
  
  @Override
  protected void initializeEnemy() throws Exception {
    this.playerFollowingSpeed = 220;
    this.checkForPlayerRadius = 400;
    this.attackRange = 400D;
    this.isAttacking = false;
  }
  
  @Override
  public void onAdded() {
    entity.getComponent(HPComponent.class).setEnemyType(EnemyType.SHOOTER_BOT);
    super.onAdded();
  }
  
  @Override
  protected void attack() throws Exception {
    Date now = new Date();
    if (nextAttack == null || now.getTime() > nextAttack.getTime()) {
      nextAttack = Date.from(Instant.now().plusMillis(Math.round(attackSpeed * 1000)));
      FXGL.runOnce(() -> {
        shootLeft();
      }, Duration.millis(400));
      FXGL.runOnce(() -> {
        shootRight();
      }, Duration.millis(800));
      FXGL.runOnce(() -> {
        isAttacking = false;
      }, Duration.millis(attackSpeed * 1000));
    }
  }
  
  public void shootLeft() {
      Point2D dir = new Point2D(getPlayer().getCenter().getX() - entity.getX() - entity.getWidth() / 2,
        getPlayer().getCenter().getY() - entity.getY() - entity.getHeight() / 2);
      
      spawn("enemyBullet",
        new SpawnData(entity.getCenter().add(- entity.getWidth() / 4, - entity.getHeight() / 4)).put("dir", dir)
        .put("bulletSpeed", bulletSpeed));
     
  }
  public void shootRight() {
      Point2D dir = new Point2D(getPlayer().getCenter().getX() - entity.getX() - entity.getWidth() / 2,
        getPlayer().getCenter().getY() - entity.getY() - entity.getHeight() / 2);
      
      spawn("enemyBullet",
        new SpawnData(entity.getCenter().add( entity.getWidth() / 4, - entity.getHeight() / 4)).put("dir", dir)
        .put("bulletSpeed", bulletSpeed));
  }
}

