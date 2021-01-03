package fxglgames.UI.buttons;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameButton extends StackPane {
  public GameButton(String name, Runnable action) {
    var bg = new Rectangle(200, 40, Color.BLACK);
    bg.setStroke(Color.WHITE);
    this.setWidth(200);
    this.setHeight(40);
    var text = FXGL.getUIFactory().newText(name, Color.WHITE, 18);
    
    bg.fillProperty().bind(
        Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
                          );
    text.fillProperty().bind(
        Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE)
                            );
    
    setOnMouseClicked(e -> action.run());
    
    getChildren().addAll(bg, text);
  }
  public GameButton(Texture texture, Runnable action) {
    var bg = texture;
    
//    bg.().bind(
//      Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
//    );
    bg.brighter();
    bg.
  
    setOnMouseClicked(e -> action.run());
    
    getChildren().addAll(bg);
  }
  public GameButton(Runnable action) {
    var bg = new Rectangle(162, 42, Color.TRANSPARENT);
    bg.setStroke(Color.WHITE);
    this.setWidth(162);
    this.setHeight(42);
  
    bg.strokeProperty().bind(
      Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
    );
  
    setOnMouseClicked(e -> action.run());
  
    getChildren().addAll(bg);
  }
}
