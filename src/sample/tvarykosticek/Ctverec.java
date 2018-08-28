package sample.tvarykosticek;

import javafx.scene.image.Image;
import sample.Kosticka;


/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 23. 8. 2018
 * Time: 19:14
 */
public class Ctverec extends Tvar {
    public Ctverec(Image image) {
        this.tvar = new Kosticka[][] {
                {new Kosticka(image),new Kosticka(image),null},
                {new Kosticka(image),new Kosticka(image),null},
                {null,null,null}
        };
    }
}