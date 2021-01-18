package fxglgames.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import fxglgames.BioTechApp;
import fxglgames.EntityType;
import fxglgames.UI.indicators.KeyIndicator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;

public class NoteMessageComponent extends Component {
  private String messageFileName;
  
  private Entity player;
  
  private Rectangle r;
  
  private KeyIndicator e;
  
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
    this.e = new KeyIndicator("E");
    e.getChildrends().forEach(shape -> entity.getViewComponent().addChild(shape));
  }
  
  @Override
  public void onUpdate(double tpf) {
    super.onUpdate(tpf);
    if (entity.isColliding(getPlayer())) {
      if (BioTechApp.DEBUG) {
        r.setStroke(Color.BLUE);
      }
      e.show();
    } else {
      if (BioTechApp.DEBUG) {
        r.setStroke(Color.RED);
      }
      e.hide();
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
