package fxglgames.UI;

import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.FXGLMenu;
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
    
    var newGameButton = new GameButton("New Game", this::fireNewGame);
    newGameButton.setTranslateX(FXGL.getAppWidth() / 2 - newGameButton.getWidth() / 2);
    newGameButton.setTranslateY(FXGL.getAppHeight() / 2 - newGameButton.getHeight() / 2);
    getMenuContentRoot().getChildren().add(newGameButton);
    
    var exitGameButton = new GameButton("Exit", this::fireExit);
    exitGameButton.setTranslateX(FXGL.getAppWidth() / 2 - exitGameButton.getWidth() / 2);
    exitGameButton.setTranslateY(FXGL.getAppHeight() / 2 - exitGameButton.getHeight() / 2 + exitGameButton.getHeight() * 1.5);
    getMenuContentRoot().getChildren().add(exitGameButton);
    
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
}
