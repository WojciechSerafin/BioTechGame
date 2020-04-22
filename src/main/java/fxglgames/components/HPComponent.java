package fxglgames.components;

import com.almasb.fxgl.dsl.components.view.GenericBarViewComponent;
import com.almasb.fxgl.entity.component.Component;
import fxglgames.EntityType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;


public class HPComponent extends GenericBarViewComponent {
    private DoubleProperty curHealth = new SimpleDoubleProperty();
    
    public HPComponent(double x, double y, @NotNull Color color, double initialValue,
                       double maxValue) {
        super(-18, -20, color, initialValue, maxValue);
    
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
            
            } else if (entity.getType() == EntityType.ENEMY) {
                entity.removeFromWorld();
            }
            return false;
        } else {
            return true;
        }
    }

    public double getCurHealth() {
        return curHealth.get();
    }

    public DoubleProperty curHealthProperty() {
        return curHealth;
    }
}
