package komiks.controllers;

import fxglgames.BioTechApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import komiks.BioTechAppRunnable;

public class ImageController {
  @FXML
  private Pane imagePane;
  
  
  public void nextImage(ActionEvent actionEvent) {
    imagePane.getStyleClass().removeAll("mozg","cma");
    imagePane.getStyleClass().add("mozg");
  }
  
  public void prevImage(ActionEvent actionEvent) {
    imagePane.getStyleClass().removeAll("mozg","cma");
    imagePane.getStyleClass().add("cma");
  }
  
  public void play(ActionEvent actionEvent) {
    BioTechAppRunnable app = new BioTechAppRunnable(new String[0]);
    Thread thread = new Thread(app);
    thread.start();
  }
}
