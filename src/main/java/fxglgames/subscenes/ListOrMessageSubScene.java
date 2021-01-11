package fxglgames.subscenes;


import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import fxglgames.UI.buttons.GameButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.*;

public class ListOrMessageSubScene extends SubScene {
  public ListOrMessageSubScene(String listOrMessageName) {
  
    System.out.println(listOrMessageName);
    
    Texture texture = texture(listOrMessageName, 954, 536);
  
  
    texture.setTranslateX(getAppWidth() / 2.0 - 477);
    texture.setTranslateY(getAppHeight() / 2.0 - 268);
    
    getContentRoot().getChildren().add(texture);
  
    var close = new GameButton(() -> {getSceneService().popSubScene();}, "X");
    
    close.setTranslateX(getAppWidth() - 25);
    close.setTranslateY(10);
    getContentRoot().getChildren().add(close);
  }
}
