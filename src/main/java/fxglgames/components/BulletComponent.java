package fxglgames.components;


import com.almasb.fxgl.entity.component.Component;
public class BulletComponent extends Component {
    private Integer damage;

    public BulletComponent(Integer damage) {
        this.damage = damage;
    }

    public Integer getDamage() {
        return damage;
    }
}
