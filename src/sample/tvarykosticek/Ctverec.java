package sample.tvarykosticek;

import sample.Kosticka;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 23. 8. 2018
 * Time: 19:14
 */
public class Ctverec extends Tvar {
    public Ctverec(String cesta) {
        this.tvar = new Kosticka[][] {
                {new Kosticka(cesta),new Kosticka(cesta),null},
                {new Kosticka(cesta),new Kosticka(cesta),null},
                {null,null,null}
        };
    }
}