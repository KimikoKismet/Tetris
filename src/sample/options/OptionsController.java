package sample.options;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import sample.Main;
import sample.menu.Controller;
import sample.obrazky.ImageLoader;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 29. 8. 2018
 * Time: 14:58
 */
public class OptionsController {
    public AnchorPane Pain;
    public ImageView BackButton;
    public Image Back;

    public void initialize() throws Exception {
        Image image = ImageLoader.LoadImage("MenuBackground.png");
        BackgroundSize backgroundSize = new BackgroundSize(100,100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        Pain.setBackground(background);

        Back = ImageLoader.LoadImage("BackButton.png");
        BackButton.setImage(Back);
    }

    public void backButtonAction() throws Exception {
        Parent root = FXMLLoader.load(Controller.class.getResource("menu.fxml"));    //načtení popisu scény
        Main.stage.setScene(new Scene(root, 600, 800));                 //vytvoření scény a nastavení zobrazení
        Main.stage.show();
    }

    public void BackClickButton() {
        Image SinglePlayerclick = ImageLoader.LoadImage("BackClickButton.png");
        BackButton.setImage(SinglePlayerclick);
    }

    public void BackReleaseButton() {
        BackButton.setImage(Back);
    }
}
