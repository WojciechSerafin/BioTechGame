package fxglgames;

import fxglgames.components.*;

public enum EnemyType {
  BOXER_BOT(BoxerBotComponent.class), SHOOTER_BOT(ShooterBotComponent.class), ALERT_BOT(AlertBotComponent.class),
  HEALER_BOT(HealerBotComponent.class), GUARDIAN_BOT(GuardianBotComponent.class),
  DEFENDER_BOT(DefenderBotComponent.class), BOSS_BOT(BossBotComponent.class);
  Class componentClazz;
  
  EnemyType(Class componentClazz) {
    this.componentClazz = componentClazz;
  }
  
  public Class getComponentClazz() {
    return componentClazz;
  }
}
