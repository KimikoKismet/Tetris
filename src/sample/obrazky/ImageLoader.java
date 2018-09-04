package sample.obrazky;

import javafx.scene.image.Image;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 2. 9. 2018
 * Time: 17:08
 */
public class ImageLoader {
    public static Image LoadImage(String nazevSouboru) {
        Image image = new Image(ImageLoader.class.getResource(nazevSouboru).toExternalForm());
        return image;
    }

    public static Image LoadImage(String nazevSouboru, int width, int height) {
        Image image = new Image(ImageLoader.class.getResource(nazevSouboru).toExternalForm(),width,height,true,false);
        return image;
    }
}
