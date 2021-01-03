package fxglgames.components;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import javafx.util.Duration;

public class OilPoolComponent extends Component {
    private Integer damage;
    private Duration tickTime;

    public OilPoolComponent(Integer damage, Duration tickTime) {
        this.damage = damage;
        this.tickTime = tickTime;
    }

    public Integer getDamage() {
        return damage;
    }
    
    public void dealDamage(Entity e) {
        FXGL.runOnce(() -> {
            if (entity.isColliding(e)) {
                System.out.printf("dealing damage");
                HPComponent component = e.getComponent(HPComponent.class);
                component.hit(damage);
                dealDamage(e);
            }
        }, tickTime);
        
    }
}
