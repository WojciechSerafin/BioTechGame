package fxglgames.components;

import com.almasb.fxgl.entity.component.Component;
import fxglgames.EntityType;

public class NoteMessageComponent extends Component {
  public EntityType typWiadomosci;
  public String messageFileName;
  
  public NoteMessageComponent(EntityType typWiadomosci, String messageFileName) {
    this.typWiadomosci = typWiadomosci;
    this.messageFileName = messageFileName;
  }
}
