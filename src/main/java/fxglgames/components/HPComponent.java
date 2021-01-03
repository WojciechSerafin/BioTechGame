package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.view.GenericBarViewComponent;
import com.almasb.fxgl.entity.component.Component;
import fxglgames.BioTechApp;
import fxglgames.EnemyType;
import fxglgames.EntityType;
import fxglgames.utils.Utils;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;


public class HPComponent extends GenericBarViewComponent {
    private DoubleProperty curHealth = new SimpleDoubleProperty();
    private Double maxHealth;
    private EnemyType enemyType;
    
    public HPComponent(double x, double y, @NotNull Color color, double initialValue,
                       double maxValue) {
        super(-18, -20, color, initialValue, maxValue);
        maxHealth = maxValue;
        DoubleProperty dp = new SimpleDoubleProperty();
        dp.set(0);
        
        this.curHealth.setValue(maxValue);
        valueProperty().bind(curHealth);
        getBar().setScaleX(0.75d);
        getBar().setScaleY(0.75d);
        getBar().getBackgroundBar().arcWidthProperty().bind(dp);
        getBar().getBackgroundBar().arcHeightProperty().bind(dp);
        getBar().getInnerBar().arcHeightProperty().bind(dp);
        getBar().getInnerBar().arcWidthProperty().bind(dp);
        getBar().getBackgroundBar().setEffect(null);
        getBar().getInnerBar().setEffect(null);
    }
    

    /**
     *
     * @param damage
     * @return if entity is alive
     */
    public Boolean hit(Integer damage) {
        this.curHealth.set(curHealth.get() - damage);
        if (this.curHealth.get() <= 0) {
            if (entity.getType() == EntityType.PLAYER) {
                entity.getComponent(PlayerComponent.class).playDeathAnimation();
                FXGL.runOnce(() -> FXGL.showMessage("You died", () -> FXGL.getGameController().gotoMainMenu()),
                  Duration.millis(1000));
            } else if (entity.getType() == EntityType.ENEMY) {
                getEnemyComponent().playDeathAnimation();
                FXGL.runOnce(() -> {entity.removeFromWorld();}, Duration.seconds(1));
            }
            return false;
        } else {
            return true;
        }
    }
    public void heal(Integer heal) {
        if (curHealth.get() > 0) {
            this.curHealth.set(Utils.clamp(curHealth.get() + heal, 0.0, maxHealth));
        }
    }
    
    public EnemyComponent getEnemyComponent() {
        return (EnemyComponent)entity.getComponent(enemyType.getComponentClazz());
    }
    
    public double getCurHealth() {
        return curHealth.get();
    }

    public DoubleProperty curHealthProperty() {
        return curHealth;
    }
    
    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }
    
    public EnemyType getEnemyType() {
        return enemyType;
    }
    
    public Double getMaxHealth() {
        return maxHealth;
    }
}
