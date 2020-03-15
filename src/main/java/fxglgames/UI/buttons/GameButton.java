package fxglgames.UI.buttons;

import com.almasb.fxgl.dsl.FXGL;
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
}
