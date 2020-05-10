package fxglgames;

import fxglgames.components.AlertBotComponent;
import fxglgames.components.BoxerBotComponent;
import fxglgames.components.EnemyComponent;

public enum EnemyType {
  BOXER_BOT(BoxerBotComponent.class), SHOOTER_BOT(EnemyComponent.class), ALERT_BOT(AlertBotComponent.class);
  Class componentClazz;
  
  EnemyType(Class componentClazz) {
    this.componentClazz = componentClazz;
  }
  
  public Class getComponentClazz() {
    return componentClazz;
  }
}
