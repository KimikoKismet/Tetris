package sample.tvarykosticek;

import sample.Kosticka;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 24. 8. 2018
 * Time: 12:09
 */
public class TkoKosticka extends Tvar {
    public TkoKosticka(String cesta) {
        this.tvar = new Kosticka[][] {
                {null,new Kosticka(cesta),null},
                {new Kosticka(cesta),new Kosticka(cesta),new Kosticka(cesta)},
                {null,null,null}
        };
    }
}
