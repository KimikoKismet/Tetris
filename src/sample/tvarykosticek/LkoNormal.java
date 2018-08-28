package sample.tvarykosticek;

import javafx.scene.image.Image;
import sample.Kosticka;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 23. 8. 2018
 * Time: 18:56
 */
public class LkoNormal extends Tvar {
    public LkoNormal(Image image) {
        this.tvar = new Kosticka[][] {
                {new Kosticka(image),null},
                {new Kosticka(image),null},
                {new Kosticka(image),new Kosticka(image)}
        };
    }

}
