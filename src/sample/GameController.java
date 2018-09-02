package sample;

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
import sample.tvarykosticek.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    public ImageView HowToPlay;
    private Map<KostickaEnum, Image> kostickyImages;
    private Tvar AktualKosticka;
    private Tvar NasledujuKosticka;
    private Kosticka[][] hraciPole;
    private Timeline timeline;
    private int score;
    private Image NasledujiciKosticka;
    private Kosticka[][] NasledujiciKostickaPole;

    public void backButtonAction() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));    //načtení popisu scény
        Main.stage.setScene(new Scene(root, 600, 800));                 //vytvoření scény a nastavení zobrazení
        Main.stage.show();
        timeline.stop();
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                System.out.println("Rotovat kostku");
                rotace(AktualKosticka,ULTIMATE_MATICE);// TODO rota
                break;
            case DOWN:
                System.out.println("Rychleji dolu");
                posun(Smer.DOLU);
                score = score + 1;
                break;
            case LEFT:
                System.out.println("Doleva");
                posun(Smer.DOLEVA);
                break;
            case RIGHT:
                System.out.println("Doprava");
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
            switch (event.getCode()) {
                case DIGIT1:
                    NasledujuKosticka = new Ctverec(nahodnaBarva());
                    vykresleni(Kosticka,NasledujuKosticka.getTvar(),NasledujiciKosticka);
                    break;
                case DIGIT2:
                    NasledujuKosticka = new LkoMirror(nahodnaBarva());
                    vykresleni(Kosticka,NasledujuKosticka.getTvar(),NasledujiciKosticka);
                    break;
                case DIGIT3:
                    NasledujuKosticka = new LkoNormal(nahodnaBarva());
                    vykresleni(Kosticka,NasledujuKosticka.getTvar(),NasledujiciKosticka);
                    break;
                case DIGIT4:
                    NasledujuKosticka = new TkoKosticka(nahodnaBarva());
                    vykresleni(Kosticka,NasledujuKosticka.getTvar(),NasledujiciKosticka);
                    break;
                case DIGIT5:
                    NasledujuKosticka = new Trubka(nahodnaBarva());
                    vykresleni(Kosticka,NasledujuKosticka.getTvar(),NasledujiciKosticka);
                    break;
                case DIGIT6:
                    NasledujuKosticka = new ZkoMirror(nahodnaBarva());
                    vykresleni(Kosticka,NasledujuKosticka.getTvar(),NasledujiciKosticka);
                    break;
                case DIGIT7:
                    NasledujuKosticka = new ZkoNormal(nahodnaBarva());
                    vykresleni(Kosticka,NasledujuKosticka.getTvar(),NasledujiciKosticka);
                    break;
            }
        }
    }

    @FXML
    public void initialize() throws Exception {
        Image image = new Image(Controller.class.getResource("TetrisBackground.png").toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        Pain.setBackground(background);
        score = 0;
        ScoreLabel.setText(score+"");


        Back = new Image(Controller.class.getResource("BackButton.png").toExternalForm());
        BackButton.setImage(Back);

        //Trubka trubka = new Trubka(Controller.class.getResource("TrubkaKosticka.png").toExternalForm());
        PlayBackground = new Image(Controller.class.getResource("Pole.png").toExternalForm());
        GraphicsContext gc = GameBoard.getGraphicsContext2D();
        gc.drawImage(PlayBackground,0,0);                                                               //vykresleni hraciho pozadi
        Image Ram = new Image(Controller.class.getResource("Okrajpole.png").toExternalForm());
        ram.setImage(Ram);
        NasledujiciKosticka = new Image(Controller.class.getResource("NasledujiciKosticka.png").toExternalForm());
        GraphicsContext gc2 = Kosticka.getGraphicsContext2D();
        gc2.drawImage(NasledujiciKosticka,0,0);                                                               //vykresleni hraciho pozadi

        kostickyImages = new HashMap<>(KostickaEnum.values().length);
        kostickyImages.put(KostickaEnum.CTVEREC,nactiObrazek("CtverecKosticka.png"));
        kostickyImages.put(KostickaEnum.TRUBKA,nactiObrazek("TrubkaKosticka.png"));
        kostickyImages.put(KostickaEnum.TKO,nactiObrazek("TkoKosticka.png"));
        kostickyImages.put(KostickaEnum.LKO,nactiObrazek("LkoKosticka.png"));
        kostickyImages.put(KostickaEnum.ZKO,nactiObrazek("ZkoKosticka.png"));

        if (Controller.pocethracu == 1) {
            Image Singleplayer = new Image(Controller.class.getResource("HowToPlay.png").toExternalForm());
            HowToPlay.setImage(Singleplayer);
        }

        GameInit();


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
                return new Ctverec(nahodnaBarva());
            case 1:
                return new LkoMirror(nahodnaBarva());
            case 2:
                return new LkoNormal(nahodnaBarva());
            case 3:
                return new TkoKosticka(nahodnaBarva());
            case 4:
                return new Trubka(nahodnaBarva());
            case 5:
                return new ZkoMirror(nahodnaBarva());
            case 6:
                return new ZkoNormal(nahodnaBarva());
            default:
                return new ZkoNormal(nahodnaBarva());
        }
    }

    private Image nahodnaBarva() {
        Random random = new Random();
        int randomindex = random.nextInt(POCET_BAREV);
        return kostickyImages.get(KostickaEnum.values()[randomindex]);
    }

    public void GameInit() {
        AktualKosticka = nahodnaKosticka();
        NasledujuKosticka = nahodnaKosticka();
        hraciPole = new Kosticka[HRA_POCET_RADKU][HRA_POCET_SLOUPCU];

        timeline = new Timeline(new KeyFrame(Duration.millis(POCATECNI_RYCHLOST),          //vytvoreni TIMERU
                ae -> gameLoop()));                                                                 //ae = Action Event
        timeline.setCycleCount(Animation.INDEFINITE);   //wutever
        timeline.play();
    }

    public void gameLoop() {
        if (kontrolaGameOver()) {
            GameOver();
        }
        posun(Smer.DOLU);

        vykresleni(Kosticka,NasledujuKosticka.getTvar(),NasledujiciKosticka);

        int scoreCounter = 0;
        for (int radek = 0; radek<hraciPole.length; radek++) {
            boolean kontrola = true;
            for (int sloupec = 0; sloupec<hraciPole[0].length; sloupec++) {
                if (hraciPole[radek][sloupec] == null) {
                    kontrola = false;
                    break;
                }
            }
            if (kontrola) {
                umazRadek(radek);
                posunZbytekDolu(radek);
                scoreCounter++;
                //score = score + SCORE_UMAZANI_RADKU;
                //ScoreLabel.setText(score+"");

            }
        }
        switch (scoreCounter) {
            case 1:
                score = score + SCORE_UMAZANI_RADKU;
                break;
            case 2:
                score = score + SCORE_UMAZANI_RADKU*3;
                break;
            case 3:
                score = score + SCORE_UMAZANI_RADKU*5;
                break;
            default:
                //nop
        }
        ScoreLabel.setText(score+"");


    }


    public boolean kontrolaGameOver() {
        for (int sloupec = 0; sloupec < hraciPole[0].length; sloupec++) {
            if (hraciPole[4][sloupec] != null) {
                return true;
            }
        }
        return false;
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

    public void posunZbytekDolu(int prazdnyRadek) {
        Kosticka[][] copy = ArrayUtils.copy(hraciPole);
        for (int radek = prazdnyRadek-1; radek>0; radek--) {
            for (int sloupec = 0; sloupec<hraciPole[0].length; sloupec++) {
                copy[radek+1][sloupec] = hraciPole[radek][sloupec];
            }
        }
        vykresleni(GameBoard, copy, PlayBackground,HRA_POCET_VIDITELNYCH_RADKU);
        hraciPole = copy;
    }

    public void umazRadek(int radek) {
        for (int sloupec = 0; sloupec<hraciPole[0].length; sloupec++) {
            hraciPole[radek][sloupec] = null;
        }
    }

    public boolean posun(Smer smer) {
        int x = AktualKosticka.getX() + smer.getX();
        int y = AktualKosticka.getY() + smer.getY();

        // Vytvoreni kopie hraciho pole s vlozenou kostickou s posunem dle smeru
        Kosticka[][] copyPole = ArrayUtils.copy(hraciPole);
        boolean status = ArrayUtils.vlozeniKosticky(AktualKosticka, hraciPole, copyPole, smer.getX(), smer.getY());

        if (status) {
            vykresleni(GameBoard, copyPole, PlayBackground, HRA_POCET_VIDITELNYCH_RADKU);
            AktualKosticka.setX(x);
            AktualKosticka.setY(y);
            return true;
        } else {
            copyPole = ArrayUtils.copy(hraciPole);
                /*
                 * Pokud je null, tak to znamena, ze se kostka nemuze pohnout smerem dolu a je nutne pridat do
                 * spadlych kostek predchozi krok tj. kdy je kostka o jedna vyse
                 */
            ArrayUtils.vlozeniKosticky(AktualKosticka, hraciPole, copyPole, 0, 0);
            hraciPole = copyPole;
            AktualKosticka = NasledujuKosticka;
            NasledujuKosticka = nahodnaKosticka();

            return false;
        }
    }

    public void vykresleni(Canvas canvas, Kosticka[][] hraciPole, Image pozadi) {
        vykresleni(canvas, hraciPole, pozadi, hraciPole.length);
    }

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

    public int[][] nasobeniMatic(int[][] pole1, int[][] pole2) {
        int[][] novaMatice = new int[pole1.length][pole2[0].length];
        int j = 0;
        for (int radekNovaMatice = 0; radekNovaMatice<novaMatice.length; radekNovaMatice++) {
            for (int sloupecNovaMatice = 0; sloupecNovaMatice<novaMatice[0].length; sloupecNovaMatice++) {
                for (int i = 0; i<pole1[0].length; i++) {
                    j = j + pole1[radekNovaMatice][i]*pole2[i][sloupecNovaMatice];
                }
                novaMatice[radekNovaMatice][sloupecNovaMatice] = j;
                j = 0;
            }
        }
        return novaMatice;
    }

    public void rotace(Tvar Aktual, int[][] Rotace) {
        int[][] otoceni = nasobeniMatic(Rotace,Aktual.getBody());
        int min = Integer.MAX_VALUE;
        for (int i = 0; i<otoceni[0].length; i++) {
            if (otoceni[0][i]<min) {
                min = otoceni[0][i];
            }
        }
        min = Math.abs(min);
        for (int i = 0; i<otoceni[0].length; i++) {
            otoceni[0][i] = otoceni[0][i] + min;
        }
        Kosticka[][] tmp = Aktual.createTvar(otoceni);
        AktualKosticka.setTvar(tmp);
        posun(Smer.NIC);
    }
}
