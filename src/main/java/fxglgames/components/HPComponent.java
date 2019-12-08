package fxglgames.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class HPComponent extends Component {
    private DoubleProperty maxHealth = new SimpleDoubleProperty();
    private DoubleProperty curHealth = new SimpleDoubleProperty();
    public HPComponent(Integer health) {
        this.maxHealth.set(health);
        this.curHealth.set(health);
    }
    /**
     *
     * @param damage
     * @return if entity is alive
     */
    public Boolean hit(Integer damage) {
        this.curHealth.set(curHealth.get() - damage);
        if (this.curHealth.get() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public double getMaxHealth() {
        return maxHealth.get();
    }

    public DoubleProperty maxHealthProperty() {
        return maxHealth;
    }

    public double getCurHealth() {
        return curHealth.get();
    }

    public DoubleProperty curHealthProperty() {
        return curHealth;
    }
}
