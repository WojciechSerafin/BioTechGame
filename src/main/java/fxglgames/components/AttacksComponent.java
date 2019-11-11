package fxglgames.components;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import java.time.Instant;
import java.util.Date;

import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.spawn;

public class AttacksComponent extends Component {

    private float attackSpeed = 5;
    private double bulletSpeed = 500;
    private Date nextAttack;
    private Date nextReset;
    private float resetDelay = 1.5f;

    public void attack() {
        Date now = new Date();
        if (nextAttack == null || now.getTime() > nextAttack.getTime()) {
            nextAttack = Date.from(Instant.now().plusMillis(Math.round(attackSpeed * 1000)));
            Point2D dir = new Point2D(getInput().getMouseXWorld() - entity.getX(),
                                     getInput().getMouseYWorld() - entity.getY());

            spawn("bullet", new SpawnData(entity.getCenter()).put("dir", dir)
                                                                        .put("bulletSpeed", bulletSpeed));

        }
    }

    public void resetAttack() {
        Date now = new Date();
        if (nextReset == null || now.getTime() > nextReset.getTime()) {
            nextReset = Date.from(Instant.now().plusMillis(Math.round(resetDelay * 1000)));
            nextAttack = now;
        }
    }
}
