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

    public static Kosticka[][] vlozeniKosticky(Tvar kostka, Kosticka src[][], int offsetX, int offsetY) {

        int x = kostka.getX() + offsetX;
        int y = kostka.getY() + offsetY;

        Kosticka[][] tmp = ArrayUtils.copy(src);

        int kontrola = 0;

        for (int radek = 0; radek < kostka.getTvar().length; radek++) {
            for (int sloupec = 0; sloupec < kostka.getTvar()[radek].length; sloupec++) {

                if (kostka.getTvar()[radek][sloupec] == null) {
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
                    kontrola = 1;
                }
                else if (src[radek + y][sloupec + x] != null) {
                    kontrola = 1;
                }
                else if (src[radek+y][sloupec+x] == null) {
                    tmp[radek + y][sloupec + x] = kostka.getTvar()[radek][sloupec];
                }
            }
        }

        return kontrola == 0 ? tmp : null;
    }
}
