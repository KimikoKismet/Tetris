package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sample.tvarykosticek.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static sample.Constants.*;

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
    public static Image PlayBackground;
    public ImageView ram;
    private Map<KostickaEnum, Image> kostickyImages;
    private Tvar AktualKosticka;
    private Tvar NasledujuKosticka;
    private Kosticka[][] hraciPole;

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

        //Trubka trubka = new Trubka(Controller.class.getResource("TrubkaKosticka.png").toExternalForm());
        PlayBackground = new Image(Controller.class.getResource("Pole.png").toExternalForm());
        GraphicsContext gc = GameBoard.getGraphicsContext2D();
        gc.drawImage(PlayBackground,0,0);                                                               //vykresleni hraciho pozadi
        Image Ram = new Image(Controller.class.getResource("Okrajpole.png").toExternalForm());
        ram.setImage(Ram);

        kostickyImages = new HashMap<>(KostickaEnum.values().length);
        kostickyImages.put(KostickaEnum.CTVEREC,nactiObrazek("CtverecKosticka.png"));
        kostickyImages.put(KostickaEnum.TRUBKA,nactiObrazek("TrubkaKosticka.png"));
        kostickyImages.put(KostickaEnum.TKO,nactiObrazek("TkoKosticka.png"));
        kostickyImages.put(KostickaEnum.LKO,nactiObrazek("LkoKosticka.png"));
        kostickyImages.put(KostickaEnum.ZKO,nactiObrazek("ZkoKosticka.png"));


    }

    public void BackClickButton() {
        Image SinglePlayerclick = new Image(Controller.class.getResource("BackClickButton.png").toExternalForm());
        BackButton.setImage(SinglePlayerclick);
    }

    public void BackReleaseButton() {
        BackButton.setImage(Back);
    }

    private Image nactiObrazek(String nazev) {
        return new Image(
                Controller.class.getResource(nazev).toExternalForm(),
                KOSTICKA_SIZE,
                KOSTICKA_SIZE,
                true,
                false,
                false
        );
    }

    private Tvar nahodnaKosticka() {
        Random random = new Random();
        int randomindex = random.nextInt(POCET_KOSTICEK);
        switch (randomindex) {
            case 0:
                return new Ctverec(kostickyImages.get(KostickaEnum.CTVEREC));
            case 1:
                return new LkoMirror(kostickyImages.get(KostickaEnum.LKO));
            case 2:
                return new LkoNormal(kostickyImages.get(KostickaEnum.LKO));
            case 3:
                return new TkoKosticka(kostickyImages.get(KostickaEnum.TKO));
            case 4:
                return new Trubka(kostickyImages.get(KostickaEnum.TRUBKA));
            case 5:
                return new ZkoMirror(kostickyImages.get(KostickaEnum.ZKO));
            case 6:
                return new ZkoNormal(kostickyImages.get(KostickaEnum.ZKO));
            default:
                return new ZkoNormal(kostickyImages.get(KostickaEnum.ZKO));
        }
    }

    public void GameInit() {
        AktualKosticka = nahodnaKosticka();
        NasledujuKosticka = nahodnaKosticka();
        hraciPole = new Kosticka[HRA_POCET_RADKU][HRA_POCET_SLOUPCU];
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(POCATECNI_RYCHLOST),          //vytvoreni TIMERU
                ae -> gameLoop()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void gameLoop() {
        //TODO posouvat kosticku dolu
    }

    public void posun(Smer smer) {
        int x = AktualKosticka.getX();
        int y = AktualKosticka.getY();
        Kosticka[][] copyPole = ArrayUtils.copy(hraciPole);

    }

    public void vykresleni(GraphicsContext gc, Kosticka[][] hraciPole) {
        gc.clearRect(0, 0, GameBoard.getWidth(), GameBoard.getHeight());    // Smazat vykreslene obrazky z predchoziho tiku
        gc.drawImage(PlayBackground,0,0);

        for (int radek = 0; radek<hraciPole.length; radek++) {
            for (int sloupec = 0; sloupec<hraciPole[0].length; sloupec++) {
                if (hraciPole[radek][sloupec] != null) {
                    gc.drawImage(hraciPole[radek][sloupec].getKosticka(),sloupec*KOSTICKA_SIZE,radek*KOSTICKA_SIZE);
                }
            }
        }

    }
}
