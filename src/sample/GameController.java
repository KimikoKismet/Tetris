package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import sample.tvarykosticek.Trubka;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 22. 8. 2018
 * Time: 22:27
 */
public class GameController implements EventHandler<KeyEvent> {

    public Canvas GameBoard;
    public Canvas Kosticka;
    public Label ScoreLabel;
    public AnchorPane Pain;
    public ImageView BackButton;
    public Image Back;

    public void backButtonAction() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));    //načtení popisu scény
        Main.stage.setScene(new Scene(root, 600, 800));                 //vytvoření scény a nastavení zobrazení
        Main.stage.show();
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                System.out.println("Rotovat kostku");   // TODO rotate
                break;
            case DOWN:
                System.out.println("Rychleji dolu");    // TODO move down
                break;
            case LEFT:
                System.out.println("Doleva");           // TODO move left
                break;
            case RIGHT:
                System.out.println("Doprava");           // TODO move right
                break;
            default:
                // nop
        }
    }

    @FXML
    public void initialize() throws Exception {
        Image image = new Image(Controller.class.getResource("TetrisBackground.png").toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        Pain.setBackground(background);
        Back = new Image(Controller.class.getResource("BackButton.png").toExternalForm());
        BackButton.setImage(Back);
        Trubka trubka = new Trubka(Controller.class.getResource("TrubkaKosticka.png").toExternalForm());
    }

    public void BackClickButton() {
        Image SinglePlayerclick = new Image(Controller.class.getResource("BackClickButton.png").toExternalForm());
        BackButton.setImage(SinglePlayerclick);
    }

    public void BackReleaseButton() {
        BackButton.setImage(Back);
    }

    public void KeyListener() {

    }
}
