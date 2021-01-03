package fxglgames.UI;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import fxglgames.UI.buttons.GameButton;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameMenu extends FXGLMenu {
  public GameMenu() {
    super(MenuType.GAME_MENU);
  
    var resumeGameButton = new GameButton( this::fireResume);
    resumeGameButton.setTranslateX(FXGL.getAppWidth() / 7.5 - resumeGameButton.getWidth() / 2);
    resumeGameButton.setTranslateY(FXGL.getAppHeight() / 1.72 - resumeGameButton.getHeight() / 2);
    getMenuContentRoot().getChildren().add(resumeGameButton);
  
    var newGameButton = new GameButton(this::fireNewGame);
    newGameButton.setTranslateX(FXGL.getAppWidth() / 3.32 - newGameButton.getWidth() / 2);
    newGameButton.setTranslateY(FXGL.getAppHeight() / 1.36 - newGameButton.getHeight() / 2);
    getMenuContentRoot().getChildren().add(newGameButton);
  
    var exitGameButton = new GameButton( this::fireExit);
    exitGameButton.setTranslateX(FXGL.getAppWidth() / 8.5 - exitGameButton.getWidth() / 2);
    exitGameButton.setTranslateY(FXGL.getAppHeight() / 1.1 - exitGameButton.getHeight() / 2);
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
    return FXGL.texture("menus/GameMenu.png");
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
  
  private static class GameText extends StackPane {
    public GameText (String name) {
      var bg = new Rectangle(400, 100, Color.BLACK);
      bg.setStroke(Color.WHITE);
      this.setWidth(400);
      this.setHeight(100);
      var text = FXGL.getUIFactory().newText(name, Color.RED, 18);
      
      getChildren().addAll(bg, text);
    }
  }
}
