package sample;

import javafx.scene.image.Image;
import sample.tvarykosticek.*;

import java.util.Map;
import java.util.Random;

import static sample.Constants.POCET_BAREV;
import static sample.Constants.POCET_KOSTICEK;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 26. 8. 2018
 * Time: 18:10
 */
public class GameUtils {
    public static Kosticka[][] copy(Kosticka src[][]) {
        Kosticka[][] copy = new Kosticka[src.length][src[0].length];
        for (int i = 0; i<src.length; i++) {
            for (int j = 0; j<src[0].length; j++) {
                copy[i][j] = src[i][j];
            }
        }
        return copy;
    }

    /**
     * pokusi se vlozit kostku do hraciho pole
     * @param kostka kostka
     * @param src puvodni hraci pole
     * @param dest hraci pole pro vlozeni kostky
     * @param offsetX o kolik se ma kostka posunout
     * @param offsetY o kolik se ma kostka posunout
     * @return vraci true pokud se podarila kostka vlozit, jinak false
     */
    public static boolean vlozeniKosticky(Tvar kostka, Kosticka src[][], Kosticka[][] dest, int offsetX, int offsetY) {

        int x = kostka.getX() + offsetX;
        int y = kostka.getY() + offsetY;

        boolean status = true;

        loop: for (int radek = 0; radek < kostka.getTvar().length; radek++) {
            for (int sloupec = 0; sloupec < kostka.getTvar()[radek].length; sloupec++) {

                if (kostka.getTvar()[radek][sloupec] == null) {     // kontrola zda ma kostka na danem miste kosticku
                    /*
                     * Aby se kostka nezasekla prazdnym mistem, tak jej ignorovat.
                     * [.][.]
                     *    [.][.]       [.][.]
                     *                    [.][.]
                     * [.]        =>   [.]
                     * [.]             [.]
                     * [.]             [.]
                     * [.]             [.]
                     */
                    continue;
                }

                if (radek + y == src.length) {
                    status = false;
                    break loop;
                }

                if (src[radek + y][sloupec + x] == null) {
                    dest[radek + y][sloupec + x] = kostka.getTvar()[radek][sloupec];
                } else {
                    status = false;
                    break loop;
                }
            }
        }

        return status;
    }

    /**
     * vynasobi 2 matice
     * @param pole1
     * @param pole2
     * @return
     */
    public static int[][] nasobeniMatic(int[][] pole1, int[][] pole2) {
        int[][] novaMatice = new int[pole1.length][pole2[0].length];
        int j = 0;
        for (int radekNovaMatice = 0; radekNovaMatice<novaMatice.length; radekNovaMatice++) {
            for (int sloupecNovaMatice = 0; sloupecNovaMatice<novaMatice[0].length; sloupecNovaMatice++) {
                for (int i = 0; i < pole1[0].length; i++) {
                    j = j + pole1[radekNovaMatice][i] * pole2[i][sloupecNovaMatice];
                }
                novaMatice[radekNovaMatice][sloupecNovaMatice] = j;
                j = 0;
            }
        }
        return novaMatice;
    }

    /**
     * umaze radek z matice
     * @param radek radek ke smazani
     * @param hraciPole
     */
    public static void umazRadek(int radek, Kosticka[][] hraciPole) {
        for (int sloupec = 0; sloupec < hraciPole[0].length; sloupec++) {
            hraciPole[radek][sloupec] = null;
        }
    }

    /**
     * posune kosticky, tak aby se vyplnil prazdny radek
     * @param prazdnyRadek souradnice prazdneho radku
     * @param hraciPole hraci pole
     * @return
     */
    public static Kosticka[][] posunZbytekDolu(int prazdnyRadek,Kosticka[][] hraciPole) {
        Kosticka[][] copy = GameUtils.copy(hraciPole);

        for (int radek = prazdnyRadek - 1; radek > 0; radek--) {
            for (int sloupec = 0; sloupec<hraciPole[0].length; sloupec++) {
                copy[radek+1][sloupec] = hraciPole[radek][sloupec];
            }
        }
        return copy;
    }

    /**
     * zkontroluje, jestli neni GameOver
     * @param hraciPole hraci pole ke kontrole
     * @return true pokud nastal GameOver jinak false
     */
    public static boolean kontrolaGameOver(Kosticka[][] hraciPole) {
        for (int sloupec = 0; sloupec < hraciPole[0].length; sloupec++) {
            if (hraciPole[4][sloupec] != null) {
                return true;
            }
        }
        return false;
    }

    public static Tvar nahodnaKosticka(Map<KostickaEnum, Image> kostickyImages) {
        Random random = new Random();
        int randomindex = random.nextInt(POCET_KOSTICEK);
        Image nahodnaBarva = nahodnaBarva(kostickyImages);

        switch (randomindex) {
            case 0:
                return new Ctverec(nahodnaBarva);
            case 1:
                return new LkoMirror(nahodnaBarva);
            case 2:
                return new LkoNormal(nahodnaBarva);
            case 3:
                return new TkoKosticka(nahodnaBarva);
            case 4:
                return new Trubka(nahodnaBarva);
            case 5:
                return new ZkoMirror(nahodnaBarva);
            case 6:
                return new ZkoNormal(nahodnaBarva);
            default:
                return new ZkoNormal(nahodnaBarva);
        }
    }

    public static Image nahodnaBarva(Map<KostickaEnum, Image> kostickyImages) {
        Random random = new Random();
        int randomindex = random.nextInt(POCET_BAREV);

        return kostickyImages.get(KostickaEnum.values()[randomindex]);
    }


}
