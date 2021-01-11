package fxglgames.UI.buttons;

import javafx.beans.binding.Bindings;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameButton extends StackPane {
  
  public GameButton(Runnable action) {
    createBoxButton(action, 162D, 42D);
  }
  
  private void createBoxButton(Runnable action, Double width, Double height) {
    var bg = new Rectangle(width, height, Color.TRANSPARENT);
    bg.setStroke(Color.WHITE);
    this.setWidth(width);
    this.setHeight(height);
    
    bg.strokeProperty().bind(
      Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
    );
    
    setOnMouseClicked(e -> action.run());
    
    getChildren().addAll(bg);
  }
  
  public GameButton(Runnable action, String tekst) {
    createBoxButton(action, 10D, 10D);
    Text text = new Text(tekst);
    text.setFill(Color.WHITE);
    
    getChildren().add(text);
  }
}
