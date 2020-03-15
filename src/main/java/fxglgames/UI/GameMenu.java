package fxglgames.UI;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
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
    var text = new GameText("PAUSE");
    text.setTranslateX(FXGL.getAppWidth() / 2 - text.getWidth() / 2);
    text.setTranslateY(FXGL.getAppHeight() / 4 - text.getHeight() / 2);
    getMenuContentRoot().getChildren().add(text);
  
  
    var resumeGameButton = new GameButton("Resume", this::fireResume);
    resumeGameButton.setTranslateX(FXGL.getAppWidth() / 2 - resumeGameButton.getWidth() / 2);
    resumeGameButton.setTranslateY(FXGL.getAppHeight() / 2 - resumeGameButton.getHeight() / 2 - resumeGameButton.getHeight() * 1);
    getMenuContentRoot().getChildren().add(resumeGameButton);
  
    var bachToMainMenuButton = new GameButton("Main Menu", this::fireExitToMainMenu);
    bachToMainMenuButton.setTranslateX(FXGL.getAppWidth() / 2 - bachToMainMenuButton.getWidth() / 2);
    bachToMainMenuButton.setTranslateY(FXGL.getAppHeight() / 2 - bachToMainMenuButton.getHeight() / 2 + resumeGameButton.getHeight() * 0.5);
    getMenuContentRoot().getChildren().add(bachToMainMenuButton);
    
    var newGameButton = new GameButton("New Game", this::fireNewGame);
    newGameButton.setTranslateX(FXGL.getAppWidth() / 2 - newGameButton.getWidth() / 2);
    newGameButton.setTranslateY(FXGL.getAppHeight() / 2 - newGameButton.getHeight() / 2 + resumeGameButton.getHeight() * 2);
    getMenuContentRoot().getChildren().add(newGameButton);
  
    var exitGameButton = new GameButton("Exit", this::fireExit);
    exitGameButton.setTranslateX(FXGL.getAppWidth() / 2 - exitGameButton.getWidth() / 2);
    exitGameButton.setTranslateY(FXGL.getAppHeight() / 2 - exitGameButton.getHeight() / 2 + exitGameButton.getHeight() * 3.5);
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
