package fxglgames.components;

import com.almasb.fxgl.dsl.components.view.GenericBarViewComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;

import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.spawn;

public class AttacksComponent extends GenericBarViewComponent {

    private DoubleProperty mana = new SimpleDoubleProperty();
    private DoubleProperty maximumMana = new SimpleDoubleProperty();
    private int initMaxMana = 100;
    private int resetCost = 20;
    private Date nextManaRestoration;
    private int manaRestorationRate = 1;
    private int manaRestorationDelay = 2000;
    
    private float attackSpeed = 2.5f;
    private double bulletSpeed = 500;
    private Date nextAttack;
    private Date nextReset;
    private float resetDelay = 1f;
    
    private DoubleProperty nextAtackCD =  new SimpleDoubleProperty();
    private DoubleProperty nextAtackResetCD =  new SimpleDoubleProperty();
    
    public AttacksComponent(double x, double y, @NotNull Color color) {
        super(x, y, color, 0, 1);
        nextAtackCD.setValue(0);
        valueProperty().bind(nextAtackCD);
        this.getBar().setLabelVisible(false);
        this.getBar().setVisible(false);
        mana.setValue(initMaxMana);
        maximumMana.setValue(initMaxMana);
        nextManaRestoration = new Date();
    }
    
    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
        Double attackCD = 0.0;
        Date now = new Date();
        if (nextAttack == null || now.getTime() > nextAttack.getTime()) {
            attackCD = 0.0;
        } else {
            attackCD = (nextAttack.getTime() - now.getTime()) / ((double)attackSpeed * 1000);
        }
        nextAtackCD.setValue(attackCD);
        
        if (now.getTime() > nextManaRestoration.getTime()) {
            nextManaRestoration = Date.from(Instant.now().plusMillis(manaRestorationDelay));
            mana.setValue(mana.doubleValue() + manaRestorationRate);
        }
    }
    
    public void attack() {
        Date now = new Date();
        if (nextAttack == null || now.getTime() > nextAttack.getTime()) {
            nextAttack = Date.from(Instant.now().plusMillis(Math.round(attackSpeed * 1000)));
            Point2D dir = new Point2D(getInput().getMouseXWorld() - entity.getX() - entity.getWidth() / 2,
                                      getInput().getMouseYWorld() - entity.getY() - entity.getHeight() / 2);

            spawn("bullet", new SpawnData(entity.getCenter()).put("dir", dir)
                                                                        .put("bulletSpeed", bulletSpeed));

        }
    }

    public void resetAttack() {
        Date now = new Date();
        if ((nextReset == null || now.getTime() > nextReset.getTime()) && mana.get() > resetCost) {
            nextReset = Date.from(Instant.now().plusMillis(Math.round(resetDelay * 1000)));
            nextAttack = now;
            mana.setValue(mana.get() - resetCost);
        }
    }
    
    public double getMana() {
        return mana.get();
    }
    
    public DoubleProperty manaProperty() {
        return mana;
    }
    
    public double getMaximumMana() {
        return maximumMana.get();
    }
    
    public DoubleProperty maximumManaProperty() {
        return maximumMana;
    }
}
