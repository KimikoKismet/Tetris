package sample;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Controller {
    public AnchorPane Pain;
    public ImageView SingleplayerButton;
    public ImageView MultiplayerButton;
    public ImageView HighScoresButton;
    public ImageView ExitButton;
    public Image options;
    public Image Singleplayer;
    public Image Multiplayer;
    public Image Highscores;
    public Image Exit;
    public static int pocethracu;
    public ImageView OptionsButton;


    @FXML
    public void initialize() throws Exception {
        Image image = new Image(Controller.class.getResource("MenuBackground.png").toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100,100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        Pain.setBackground(background);
        Singleplayer = new Image(Controller.class.getResource("SinglePlayerButton.png").toExternalForm());
        SingleplayerButton.setImage(Singleplayer);
        Multiplayer = new Image(Controller.class.getResource("MultiplayerButton.png").toExternalForm());
        MultiplayerButton.setImage(Multiplayer);
        Highscores = new Image(Controller.class.getResource("HighScoreButton.png").toExternalForm());
        HighScoresButton.setImage(Highscores);
        Exit = new Image(Controller.class.getResource("ExitButton.png").toExternalForm());
        ExitButton.setImage(Exit);
        options = new Image(Controller.class.getResource("OptionsButton.png").toExternalForm());
        OptionsButton.setImage(options);
    }

    public void ExitButtonAction() {
        Platform.exit();
    }

    public void singleplayerButtonAction() throws Exception {
        pocethracu = 1;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = fxmlLoader.load();

        Main.stage.setScene(new Scene(root, 600, 800));                         //vytvoření scény a nastavení zobrazení
        Main.stage.show();                                                                    //zobrazí připravenou scénu

        Main.stage.getScene().setOnKeyPressed(fxmlLoader.<GameController>getController());
    }

    public void MultiplayerButtonAction() throws Exception {
        pocethracu = 2;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = fxmlLoader.load();

        Main.stage.setScene(new Scene(root, 600, 800));                         //vytvoření scény a nastavení zobrazení
        Main.stage.show();                                                                    //zobrazí připravenou scénu

        Main.stage.getScene().setOnKeyPressed(fxmlLoader.<GameController>getController());                  //TODO pridani poctu hracu do kontroleru
    }

    public void HighScoresButtonAction() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("score1.fxml"));    //načtení popisu scény
        Main.stage.setScene(new Scene(root, 600, 800));                 //vytvoření scény a nastavení zobrazení
        Main.stage.show();
    }

    public void OptionsButtonAction() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("options.fxml"));    //načtení popisu scény
        Main.stage.setScene(new Scene(root, 600, 800));                 //vytvoření scény a nastavení zobrazení
        Main.stage.show();
    }

    public void MultiplayerClickButton() {
        Image Multiplayerclick = new Image(Controller.class.getResource("MultiplayerClickButton.png").toExternalForm());
        MultiplayerButton.setImage(Multiplayerclick);
    }

    public void MultiplayerReleaseButton() {
        MultiplayerButton.setImage(Multiplayer);
    }

    public void SingleplayerClickButton() {
        Image SinglePlayerclick = new Image(Controller.class.getResource("SingleplayerClickButton.png").toExternalForm());
        SingleplayerButton.setImage(SinglePlayerclick);
    }

    public void SingleplayerReleaseButton() {
        SingleplayerButton.setImage(Singleplayer);
    }
    public void HighScoreClickButton() {
        Image Highscoreclick = new Image(Controller.class.getResource("HighScoreClickButton.png").toExternalForm());
        HighScoresButton.setImage(Highscoreclick);
    }

    public void HighScoreReleaseButton() {
        HighScoresButton.setImage(Highscores);
    }
    public void ExitClickButton() {
        Image Exitclick = new Image(Controller.class.getResource("ExitClickButton.png").toExternalForm());
        ExitButton.setImage(Exitclick);
    }

    public void ExitReleaseButton() {
        ExitButton.setImage(Exit);
    }

    public void OptionsClickButton() {
        Image optionsClick = new Image(Controller.class.getResource("OptionsClickButton.png").toExternalForm());
        OptionsButton.setImage(optionsClick);
    }

    public void OptionsReleaseButton() {
        OptionsButton.setImage(options);
    }
}
