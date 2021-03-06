package fxglgames.UI;

import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.dsl.FXGL;
import fxglgames.UI.buttons.GameButton;
import fxglgames.subscenes.YouSureSubScene;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Point2D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;

public class MainMenu extends FXGLMenu {
  
  public MainMenu() {
    super(MenuType.MAIN_MENU);
  
    Image image = new Image("assets/textures/cursor.png");
    FXGL.getGameScene().setCursor(image, new Point2D(3D, 3D));
    
    var newGameButton = new GameButton(this::fireNewGame);
    newGameButton.setTranslateX(FXGL.getAppWidth() / 3.32 - newGameButton.getWidth() / 2);
    newGameButton.setTranslateY(FXGL.getAppHeight() / 1.36 - newGameButton.getHeight() / 2);
    getMenuContentRoot().getChildren().add(newGameButton);
    
    var exitGameButton = new GameButton(  () -> getSceneService().pushSubScene(new YouSureSubScene()));
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
    return FXGL.texture("menus/MainMenu.png");
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
