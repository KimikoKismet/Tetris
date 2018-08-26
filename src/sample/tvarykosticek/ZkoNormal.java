package sample.tvarykosticek;

import sample.Kosticka;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 23. 8. 2018
 * Time: 19:15
 */
public class ZkoNormal extends Tvar {
    public ZkoNormal(String cesta) {
        this.tvar = new Kosticka[][] {
                {new Kosticka(cesta),new Kosticka(cesta),null},
                {null,new Kosticka(cesta),new Kosticka(cesta)},
                {null,null,null}
        };

    }
}
