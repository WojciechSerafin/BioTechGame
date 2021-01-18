package fxglgames.UI.indicators;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class KeyIndicator {
  
  private Text text;
  private Rectangle rectangle;
  
  public KeyIndicator(String key) {
    this.text = new Text(key);
    text.setFill(Color.WHITE);
    text.setX(30);
    text.setY(-17);
    this.rectangle = new Rectangle(10D, 12D, Color.TRANSPARENT);
    rectangle.setStroke(Color.WHITE);
    rectangle.setX(28);
    rectangle.setY(-27);
  }
  
  public List<Shape> getChildrends() {
    List<Shape> shapes = new ArrayList<>();
    shapes.add(text);
    shapes.add(rectangle);
    return shapes;
  }
  
  public void show() {
    text.setVisible(true);
    rectangle.setVisible(true);
  }
  public void hide() {
    text.setVisible(false);
    rectangle.setVisible(false);
  }
}
