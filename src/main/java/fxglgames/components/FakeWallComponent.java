package fxglgames.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import fxglgames.EntityType;

public class FakeWallComponent extends Component {
  
  private Boolean toDelete = false;
  
  @Override
  public void onUpdate(double tpf) {
    if (this.toDelete) {
      FXGL.getGameWorld().getEntitiesByType(EntityType.FAKE_WALL).stream().forEach(fakeWall -> {
        if (entity.isColliding(fakeWall)) {
          fakeWall.getComponent(FakeWallComponent.class).setToDelete(Boolean.TRUE);
        }
      });
      entity.removeFromWorld();
    }
  }
  
  public Boolean getToDelete() {
    return toDelete;
  }
  
  public void setToDelete(Boolean toDelete) {
    this.toDelete = toDelete;
  }
}
