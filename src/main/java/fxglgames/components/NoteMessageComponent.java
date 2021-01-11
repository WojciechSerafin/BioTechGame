package fxglgames.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import fxglgames.BioTechApp;
import fxglgames.EntityType;
import fxglgames.subscenes.ListOrMessageSubScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;

public class NoteMessageComponent extends Component {
  private String messageFileName;
  
  private Entity player;
  
  private Rectangle r;
  
  
  public NoteMessageComponent(String messageFileName) {
    this.messageFileName = messageFileName;
  }
  
  @Override
  public void onAdded() {
    super.onAdded();
    if (BioTechApp.DEBUG) {
      this.r = new Rectangle(entity.getBoundingBoxComponent().getMinXLocal(),
        entity.getBoundingBoxComponent().getMinYLocal(),
        entity.getBoundingBoxComponent().getWidth(),
        entity.getBoundingBoxComponent().getHeight());
      r.setStroke(Color.RED);
      r.setFill(Color.TRANSPARENT);
      entity.getViewComponent().addChild(r);
    }
  }
  
  @Override
  public void onUpdate(double tpf) {
    super.onUpdate(tpf);
    if (BioTechApp.DEBUG) {
      if (entity.isColliding(getPlayer())) {
        r.setStroke(Color.BLUE);
      } else {
        r.setStroke(Color.RED);
      }
    }
  }
  
  protected Entity getPlayer() {
    if (player == null) {
      player = getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0);
    }
    return player;
  }
  
  public String getMessageFileName() {
    return messageFileName;
  }
}
