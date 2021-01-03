package fxglgames;

import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.ProgressBar;
import com.almasb.fxgl.ui.UIController;
import fxglgames.components.AttacksComponent;
import fxglgames.components.HPComponent;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BioTechController implements UIController {
  
  @FXML
  private Text nazwaLokacji;
  
  @FXML
  private ImageView skill1;
  
  @FXML
  private ImageView skill2;
  
  @FXML
  private ImageView skill3;
  
  @FXML
  private ImageView skill4;
  
  @FXML
  private ImageView playerInfo;
  
  @FXML
  private ProgressBar health;
  
  @FXML
  private ProgressBar mana;
  
  private GameScene gameScene;
  
  public BioTechController(GameScene gameScene) {
    this.gameScene = gameScene;
  }
  
  @Override
  public void init() {
    HPComponent hpComponent =
      gameScene.getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getComponent(HPComponent.class);
    AttacksComponent attacksComponent =
      gameScene.getGameWorld().getEntitiesByType(EntityType.PLAYER).get(0).getComponent(AttacksComponent.class);
    
    nazwaLokacji.setStroke(Color.GOLD);
    nazwaLokacji.setFill(Color.GOLD);
    nazwaLokacji.setVisible(true);
    
    
    Image image = new Image("assets/ui/icons/1.png");
    skill1.setImage(image);
    image = new Image("assets/ui/icons/2.png");
    skill2.setImage(image);
    image = new Image("assets/ui/icons/3.png");
    skill3.setImage(image);
    image = new Image("assets/ui/icons/4.png");
    skill4.setImage(image);
    image = new Image("assets/ui/icons/5.png");
    playerInfo.setImage(image);
    
    health.setScaleX(1);
    health.setScaleY(1);
    health.setEffect(null);
    health.getBackgroundBar().setEffect(null);
    health.getBackgroundBar().arcWidthProperty().unbind();
    health.getBackgroundBar().arcHeightProperty().unbind();
    health.getBackgroundBar().setArcWidth(0);
    health.getBackgroundBar().setArcHeight(0);
    //health.getBackgroundBar().setRotate(-90);
    
    health.getInnerBar().setEffect(null);
    health.getInnerBar().arcWidthProperty().unbind();
    health.getInnerBar().arcHeightProperty().unbind();
    health.getInnerBar().setArcWidth(0);
    health.getInnerBar().setArcHeight(0);
    health.setFill(Color.RED);
    health.setWidth(40);
    health.setHeight(40);
    
    health.setLabelVisible(false);
    health.setMaxValue(hpComponent.getMaxHealth());
    health.currentValueProperty().bind(hpComponent.curHealthProperty());
    
    mana.setScaleX(1);
    mana.setScaleY(1);
    mana.setEffect(null);
    mana.getBackgroundBar().setEffect(null);
    mana.getBackgroundBar().arcWidthProperty().unbind();
    mana.getBackgroundBar().arcHeightProperty().unbind();
    mana.getBackgroundBar().setArcWidth(0);
    mana.getBackgroundBar().setArcHeight(0);
    
    mana.getInnerBar().setEffect(null);
    mana.getInnerBar().arcWidthProperty().unbind();
    mana.getInnerBar().arcHeightProperty().unbind();
    mana.getInnerBar().setArcWidth(0);
    mana.getInnerBar().setArcHeight(0);
    mana.setFill(Color.BLUE.brighter());
    mana.setWidth(40);
    mana.setHeight(40);
    mana.maxValueProperty().bind(attacksComponent.maximumManaProperty());
    mana.currentValueProperty().bind(attacksComponent.manaProperty());
    
    mana.setLabelFill(Color.TRANSPARENT);
    mana.setLabelVisible(false);
  }
  
  public Text getNazwaLokacji() {
    return nazwaLokacji;
  }
}
