package sample;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 26. 8. 2018
 * Time: 18:10
 */
public class ArrayUtils {
    public static Kosticka[][] copy(Kosticka src[][]) {
         Kosticka[][] copy = new Kosticka[src.length][src[0].length];
         for (int i = 0; i<src.length; i++) {
             for (int j = 0; i<src[0].length; j++) {
                 copy[i][j] = src[i][j];
             }
         }
         return copy;
    }
}
