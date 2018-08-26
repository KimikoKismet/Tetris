package sample.tvarykosticek;

import sample.Kosticka;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 23. 8. 2018
 * Time: 19:14
 */
public class LkoMirror extends Tvar {
    public LkoMirror(String cesta) {
        this.tvar = new Kosticka[][] {
                {null,new Kosticka(cesta),null},
                {null,new Kosticka(cesta),null},
                {new Kosticka(cesta),new Kosticka(cesta),null}
        };
    }
}
