package sample;

import javafx.scene.image.Image;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 22. 8. 2018
 * Time: 21:32
 */
public class Kosticka {
    private final Image kosticka;

    public Kosticka(String cesta) {
        kosticka = new Image(cesta);
    }

    public Image getKosticka() {
        return kosticka;
    }
}
