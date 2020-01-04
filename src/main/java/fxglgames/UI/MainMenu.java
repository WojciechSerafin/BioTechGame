package fxglgames.UI;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import fxglgames.UI.buttons.GameButton;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MainMenu extends FXGLMenu {
  
  public MainMenu() {
    super(MenuType.MAIN_MENU);
    
    var button = new GameButton("New Game", this::fireNewGame);
    button.setTranslateX(FXGL.getAppWidth() / 2 - button.getWidth() / 2);
    button.setTranslateY(FXGL.getAppHeight() / 2 - button.getHeight() / 2);
    getMenuContentRoot().getChildren().add(button);
  }
  
  @Override
  protected Button createActionButton(StringBinding stringBinding, Runnable runnable) {
    return new Button();
  }
  
  @Override
  protected Button createActionButton(String s, Runnable runnable) {
    return new Button();
  }
  
  @Override
  protected Node createBackground(double v, double v1) {
    return new Rectangle(v, v1, Color.GRAY);
  }
  
  @Override
  protected Node createProfileView(String s) {
    return new Text();
  }
  
  @Override
  protected Node createTitleView(String s) {
    return new Text();
  }
  
  @Override
  protected Node createVersionView(String s) {
    return new Text();
  }
  
  /*private static class GameButton extends StackPane {
    public GameButton (String name, Runnable action) {
      var bg = new Rectangle(200, 40, Color.BLACK);
      bg.setStroke(Color.WHITE);
      
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
  }*/
}
