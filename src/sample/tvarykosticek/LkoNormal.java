package sample.tvarykosticek;

import sample.Kosticka;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 23. 8. 2018
 * Time: 18:56
 */
public class LkoNormal extends Tvar {
    public LkoNormal(String cesta) {
        this.tvar = new Kosticka[][] {
                {new Kosticka(cesta),null,null},
                {new Kosticka(cesta),null,null},
                {new Kosticka(cesta),new Kosticka(cesta),null}
        };

    }
}
