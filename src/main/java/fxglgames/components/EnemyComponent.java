package fxglgames.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

import static com.almasb.fxgl.dsl.FXGL.*;

public class EnemyComponent extends Component {

  private PhysicsComponent physics;
  int dx = 0, dy = 0;


  @Override
  public void onUpdate(double tpf) {

    if (random(0, 1000) < 10) {
      dx = random(-100, 100);
      dy = random(-100, 100);
    }

    physics.setVelocityY(dy);
    physics.setVelocityX(dx);
  }
}
