package sample.tvarykosticek;

import javafx.scene.image.Image;
import sample.Kosticka;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 23. 8. 2018
 * Time: 19:15
 */
public class ZkoMirror extends Tvar {
    public ZkoMirror (Image image) {
        this.tvar = new Kosticka[][] {
                {null,new Kosticka(image),new Kosticka(image)},
                {new Kosticka(image),new Kosticka(image),null},
                {null,null,null}
        };

    }
}
