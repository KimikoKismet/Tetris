package sample.tvarykosticek;

import sample.Constants;
import sample.Kosticka;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 23. 8. 2018
 * Time: 18:48
 */
public abstract class Tvar {
    protected Kosticka[][] tvar;
    protected int x = Constants.HRA_POCET_SLOUPCU / 2;
    protected int y = Constants.POCATECNI_SOURADNICE;

    public Kosticka[][] getTvar() {
        return tvar;
    }

    public void setTvar(Kosticka[][] tvar) {
        this.tvar = tvar;
    }

    public int[][] getBody() {
        int[][] souradnice = new int[2][4];
        int k = 0;
        Kosticka[][] kostka = getTvar();
        for (int j = 0; j<kostka.length; j++) {
            for (int i = 0; i<kostka.length; i++) {
                Kosticka bod = kostka[i][j];
                if (bod != null) {
                    souradnice[0][k] = i;
                    souradnice[1][k] = j;
                    k++;
                }
            }
        }
        return souradnice;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
