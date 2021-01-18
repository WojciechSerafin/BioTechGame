package fxglgames.subscenes;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import fxglgames.UI.buttons.GameButton;

import static com.almasb.fxgl.dsl.FXGL.*;

public class YouSureSubScene extends SubScene {
  public YouSureSubScene() {
    Texture texture = texture("menus/AreYouSure.png", 954, 536);
  
    texture.setTranslateX(getAppWidth() / 2.0 - 477);
    texture.setTranslateY(getAppHeight() / 2.0 - 268);
  
    getContentRoot().getChildren().add(texture);
  
    var newGameButton = new GameButton(() -> getSceneService().popSubScene());
    newGameButton.setTranslateX(FXGL.getAppWidth() / 3.32 - newGameButton.getWidth() / 2);
    newGameButton.setTranslateY(FXGL.getAppHeight() / 1.36 - newGameButton.getHeight() / 2);
    getContentRoot().getChildren().add(newGameButton);
  
    var exitGameButton = new GameButton( () -> FXGL.getGameController().exit());
    exitGameButton.setTranslateX(FXGL.getAppWidth() / 8.5 - exitGameButton.getWidth() / 2);
    exitGameButton.setTranslateY(FXGL.getAppHeight() / 1.1 - exitGameButton.getHeight() / 2);
    getContentRoot().getChildren().add(exitGameButton);
  }
}
