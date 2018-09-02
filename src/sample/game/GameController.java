package sample.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sample.*;
import sample.menu.Controller;
import sample.obrazky.ImageLoader;
import sample.score.Score;
import sample.tvarykosticek.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static sample.Constants.*;
import static sample.game.GameUtils.*;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 22. 8. 2018
 * Time: 22:27
 */
public class GameController implements EventHandler<KeyEvent> {

    public Canvas gameBoard;
    private Image playBackground;

    public Canvas kosticka;
    private Image nasledujiciKostickaBackground;

    public Label scoreLabel;

    public AnchorPane container;

    public ImageView backButton;
    private Image back;

    public ImageView ramecek;

    public ImageView howToPlay;

    private Map<KostickaEnum, Image> kostickyImages;

    private Tvar aktualKosticka;
    private Tvar nasledujuKosticka;
    private Kosticka[][] hraciPole;

    private Timeline timeline;

    private int score;


    @FXML
    public void initialize() throws Exception {
        Image image = ImageLoader.LoadImage("TetrisBackground.png");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        container.setBackground(background);

        score = 0;
        scoreLabel.setText(score+"");

        back = ImageLoader.LoadImage("backButton.png");
        backButton.setImage(back);

        playBackground = ImageLoader.LoadImage("Pole.png");
        GraphicsContext gc = gameBoard.getGraphicsContext2D();
        gc.drawImage(playBackground,0,0);                   //vykresleni hraciho pozadi

        ramecek.setImage(ImageLoader.LoadImage("Okrajpole.png"));

        nasledujiciKostickaBackground = ImageLoader.LoadImage("NasledujiciKosticka.png");
        GraphicsContext gc2 = kosticka.getGraphicsContext2D();
        gc2.drawImage(nasledujiciKostickaBackground,0,0);                                                               //vykresleni hraciho pozadi

        kostickyImages = new HashMap<>(KostickaEnum.values().length);
        kostickyImages.put(KostickaEnum.CTVEREC, ImageLoader.LoadImage("CtverecKosticka.png", KOSTICKA_SIZE, KOSTICKA_SIZE));
        kostickyImages.put(KostickaEnum.TRUBKA, ImageLoader.LoadImage("TrubkaKosticka.png", KOSTICKA_SIZE, KOSTICKA_SIZE));
        kostickyImages.put(KostickaEnum.TKO, ImageLoader.LoadImage("TkoKosticka.png", KOSTICKA_SIZE, KOSTICKA_SIZE));
        kostickyImages.put(KostickaEnum.LKO, ImageLoader.LoadImage("LkoKosticka.png", KOSTICKA_SIZE, KOSTICKA_SIZE));
        kostickyImages.put(KostickaEnum.ZKO, ImageLoader.LoadImage("ZkoKosticka.png", KOSTICKA_SIZE, KOSTICKA_SIZE));

        if (Controller.pocethracu == 1) {
            Image Singleplayer = ImageLoader.LoadImage("howToPlay.png");
            howToPlay.setImage(Singleplayer);
        }
        // TODO pocet hracu 2

        GameInit();
    }

    public void GameInit() {
        aktualKosticka = nahodnaKosticka(kostickyImages);
        nasledujuKosticka = nahodnaKosticka(kostickyImages);
        hraciPole = new Kosticka[HRA_POCET_RADKU][HRA_POCET_SLOUPCU];

        timeline = new Timeline(new KeyFrame(Duration.millis(POCATECNI_RYCHLOST),          //vytvoreni TIMERU
                ae -> gameLoop()));                                                                 //ae = Action Event
        timeline.setCycleCount(Animation.INDEFINITE);   //wutever
        timeline.play();
    }

    public void gameLoop() {
        if (kontrolaGameOver(hraciPole)) {
            GameOver();
        }

        posun(Smer.DOLU);

        vykresleni(kosticka, nasledujuKosticka.getTvar(), nasledujiciKostickaBackground);

        int scoreCounter = 0;
        for (int radek = 0; radek < hraciPole.length; radek++) {
            boolean kontrola = true;
            for (int sloupec = 0; sloupec < hraciPole[0].length; sloupec++) {
                if (hraciPole[radek][sloupec] == null) {
                    kontrola = false;
                    break;
                }
            }
            if (kontrola) {
                umazRadek(radek, hraciPole);
                hraciPole = posunZbytekDolu(radek,hraciPole);
                vykresleni(gameBoard, hraciPole, playBackground,HRA_POCET_VIDITELNYCH_RADKU);

                scoreCounter++;
            }
        }

        switch (scoreCounter) {
            case 1:
                score = score + SCORE_UMAZANI_RADKU;
                break;
            case 2:
                score = score + SCORE_UMAZANI_RADKU * 3;
                break;
            case 3:
                score = score + SCORE_UMAZANI_RADKU * 5;
                break;
            default:
                //nop
        }

        scoreLabel.setText(score+"");
    }

    public void backButtonAction() throws Exception {
        Parent root = FXMLLoader.load(Controller.class.getResource("menu.fxml"));    //načtení popisu scény
        Main.stage.setScene(new Scene(root, 600, 800));                 //vytvoření scény a nastavení zobrazení
        Main.stage.show();

        timeline.stop();
    }

    public void backClickButton() {
        Image SinglePlayerclick = ImageLoader.LoadImage("backClickButton.png");
        backButton.setImage(SinglePlayerclick);
    }

    public void backReleaseButton() {
        backButton.setImage(back);
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                rotace(aktualKosticka);
                break;
            case DOWN:
                posun(Smer.DOLU);
                score = score + 1;
                break;
            case LEFT:
                posun(Smer.DOLEVA);
                break;
            case RIGHT:
                posun(Smer.DOPRAVA);
                break;
            case SPACE:
                while (posun(Smer.DOLU)) {
                    score = score + 2;
                }
                break;
            default:
                // nop
        }
        if (Controller.pocethracu == 2) {
            Image nahodnaBarva = nahodnaBarva(kostickyImages);
            switch (event.getCode()) {
                case DIGIT1:
                    nasledujuKosticka = new Ctverec(nahodnaBarva);
                    vykresleni(kosticka, nasledujuKosticka.getTvar(), nasledujiciKostickaBackground);
                    break;
                case DIGIT2:
                    nasledujuKosticka = new LkoMirror(nahodnaBarva);
                    vykresleni(kosticka, nasledujuKosticka.getTvar(), nasledujiciKostickaBackground);
                    break;
                case DIGIT3:
                    nasledujuKosticka = new LkoNormal(nahodnaBarva);
                    vykresleni(kosticka, nasledujuKosticka.getTvar(), nasledujiciKostickaBackground);
                    break;
                case DIGIT4:
                    nasledujuKosticka = new TkoKosticka(nahodnaBarva);
                    vykresleni(kosticka, nasledujuKosticka.getTvar(), nasledujiciKostickaBackground);
                    break;
                case DIGIT5:
                    nasledujuKosticka = new Trubka(nahodnaBarva);
                    vykresleni(kosticka, nasledujuKosticka.getTvar(), nasledujiciKostickaBackground);
                    break;
                case DIGIT6:
                    nasledujuKosticka = new ZkoMirror(nahodnaBarva);
                    vykresleni(kosticka, nasledujuKosticka.getTvar(), nasledujiciKostickaBackground);
                    break;
                case DIGIT7:
                    nasledujuKosticka = new ZkoNormal(nahodnaBarva);
                    vykresleni(kosticka, nasledujuKosticka.getTvar(), nasledujiciKostickaBackground);
                    break;
            }
        }
    }

    /**
     * posune kostku danym smerem
     * @param smer smer, kterym sem a kostka posunout
     * @return true pokud se posunuti povedlo,jinak false
     */
    public boolean posun(Smer smer) {
        int x = aktualKosticka.getX() + smer.getX();
        int y = aktualKosticka.getY() + smer.getY();

        // Vytvoreni kopie hraciho pole s vlozenou kostickou s posunem dle smeru
        Kosticka[][] copyPole = GameUtils.copy(hraciPole);
        boolean status = GameUtils.vlozeniKosticky(aktualKosticka, hraciPole, copyPole, smer.getX(), smer.getY());

        if (status) {
            vykresleni(gameBoard, copyPole, playBackground, HRA_POCET_VIDITELNYCH_RADKU);
            aktualKosticka.setX(x);
            aktualKosticka.setY(y);
            return true;
        } else {
            copyPole = GameUtils.copy(hraciPole);
                /*
                 * Pokud je null, tak to znamena, ze se kostka nemuze pohnout smerem dolu a je nutne pridat do
                 * spadlych kostek predchozi krok tj. kdy je kostka o jedna vyse
                 */
            GameUtils.vlozeniKosticky(aktualKosticka, hraciPole, copyPole, 0, 0);
            hraciPole = copyPole;
            aktualKosticka = nasledujuKosticka;
            nasledujuKosticka = nahodnaKosticka(kostickyImages);

            return false;
        }
    }

    /**
     * Otoci kostku o uhel dany matici {@link Constants#ULTIMATE_MATICE}.
     * @param aktual tvar k otoceni
     */
    public void rotace(Tvar aktual) {

        // Otoceni
        int[][] otoceni = nasobeniMatic(ULTIMATE_MATICE, aktual.getBody());

        /*
         * Pri otoceni muze dojit k presunu do jineho kvadrantu (- souradnice x nebo y), takze je potreba otocenou
         * kostku dat zpet.
         */
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < otoceni[0].length; i++) {
            if (otoceni[0][i] < min) {
                min = otoceni[0][i];
            }
        }

        min = Math.abs(min);

        for (int i = 0; i < otoceni[0].length; i++) {
            otoceni[0][i] = otoceni[0][i] + min;
        }

        Kosticka[][] tmp = aktual.createTvar(otoceni);

        aktualKosticka.setTvar(tmp);

        posun(Smer.NIC);
    }

    /**
     * vykresli pole a image do Canvasu
     * @param canvas
     * @param hraciPole
     * @param pozadi
     */
    public void vykresleni(Canvas canvas, Kosticka[][] hraciPole, Image pozadi) {
        vykresleni(canvas, hraciPole, pozadi, hraciPole.length);
    }

    /**
     * vykresli pole a image do Canvasu
     * @param canvas
     * @param hraciPole
     * @param pozadi
     */
    public void vykresleni(Canvas canvas, Kosticka[][] hraciPole, Image pozadi, int pocetViditelnychRadku) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Smazat vykreslene obrazky z predchoziho tiku
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(pozadi,0,0);

        // offset od kdy zacit vykreslovat (prvni radky jsou neviditelne)
        int offset = hraciPole.length - pocetViditelnychRadku;

        for (int radek = 0; radek < hraciPole.length - offset; radek++) {
            for (int sloupec = 0; sloupec < hraciPole[0].length; sloupec++) {

                if (hraciPole[radek + offset][sloupec] != null) {
                    gc.drawImage(
                            hraciPole[radek + offset][sloupec].getKosticka(),
                            sloupec * KOSTICKA_SIZE,
                            radek * KOSTICKA_SIZE
                    );
                }
            }
        }
    }

    public void GameOver() {
        timeline.stop();

        // Priprava dialogu pro zadani jmena
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Game Over");
        dialog.setHeaderText("GAME OVER. Do you want to save your score");
        dialog.setContentText("Please enter your name loser:");

        /*
         * Nezarucena informace -- takhle to fungvalo ve starsi knihovne pro GUI -- Swing.
         *
         * Obsluha udalosti (mys, klavesnice, tlacitka) probiha v GUI servisnim vlakne a stejne tak
         * zobrazeni formularu a diaogu.
         *
         * Tady herni smycka je obsluhovana timerem, ktery vytvari svoje vlastni vlakno.
         *
         * Kdyz se pokusim zavolat dialog.showAndWait() z vlakna timeru, tak vypadne vyjimka
         * "java.lang.IllegalStateException: showAndWait is not allowed during animation or layout processing"
         *
         * --> Vyresi se to tak, ze zobrazeni dialogu se bude delegovat na GUI vlakno pomoci Platform.runLater.
         */

        // Zobrazeni dialogu pro zadani jmena
        Platform.runLater(() -> {
            String name;
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                name = result.get().length() < 3 ? "N00b." : result.get();
                Score tmp = new Score("HighScore.txt");
                tmp.SaveHighScore(score,name);
            }
        });
    }
}
