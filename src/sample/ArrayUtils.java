package sample;

import sample.tvarykosticek.Tvar;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 26. 8. 2018
 * Time: 18:10
 */
public class ArrayUtils {
    public static Kosticka[][] copy(Kosticka src[][]) {
        Kosticka[][] copy = new Kosticka[src.length][src[0].length];
        for (int i = 0; i<src.length; i++) {
            for (int j = 0; j<src[0].length; j++) {
                copy[i][j] = src[i][j];
            }
        }
        return copy;
    }

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
}
