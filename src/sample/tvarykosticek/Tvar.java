package sample.tvarykosticek;

import sample.Kosticka;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 23. 8. 2018
 * Time: 18:48
 */
public abstract class Tvar {
    protected Kosticka[][] tvar;

    public Kosticka[][] getTvar() {
        return tvar;
    }

    public void setTvar(Kosticka[][] tvar) {
        this.tvar = tvar;
    }


}
