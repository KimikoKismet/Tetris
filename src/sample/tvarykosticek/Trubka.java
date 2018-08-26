package sample.tvarykosticek;

import sample.Kosticka;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 23. 8. 2018
 * Time: 19:15
 */
public class Trubka extends Tvar {
    public Trubka(String cesta) {
        this.tvar = new Kosticka[][] {
                {new Kosticka(cesta),null,null},
                {new Kosticka(cesta),null,null},
                {new Kosticka(cesta),null,null},
                {new Kosticka(cesta),null,null}
        };

    }
}
