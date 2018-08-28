package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 26. 8. 2018
 * Time: 12:06
 */
public class Score {
    private String nazevSouboru;

    public Score (String nazevSouboru){
        this.nazevSouboru = nazevSouboru;
    }

    public void SaveHighScore(int HighScore, String JmenoHrace) {
        FileWriter fw;                                                      //FileWriter je trida, ktera umoznuje zapis dat do textoveho souboru
        try{                                                                //try-catch blok pro odchytavani vyjimek v programu. Zde v kodu muze vyjimku vyhodit FileWriter
            fw = new FileWriter(nazevSouboru, true);                //Vytvoreni instance tridy FileWriter, append = jestli soubor jiz existuje, bude pokracovat na konci toho souboru
            fw.write(JmenoHrace+": " + HighScore + "\n");               //metoda FileWriter pro ulozeni textu do souboru. "\n" znak pro konec radku
            fw.flush();                                                     //Metoda flush() se pouziva pro okamyite vepsani do souboru
            fw.close();                                                     //Metoda close() se pouziva pro ukonceni zapisu do souboru
        }catch(Exception e){

        }
    }

    public ArrayList<String> prohlizeni() {
        BufferedReader br;                  // Trida usnadnujici cteni ze souboru, ale pro pouziti potrebuje jeste nejakou tridu Reader jako je FileReader
        ArrayList<String> scores = new ArrayList<>();
        try{
            br = new BufferedReader(new FileReader(nazevSouboru)); //Vytvoreni instance tridy BufferedReader, ktery pro cteni ze souboru bude pouzivat instanci tridy FileReader
            String radek;
            while ((radek=br.readLine())!=null) {
                scores.add(radek);
            }
            br.close();
        }catch (Exception e){

        }
        return scores;
    }

    public String[] rozdeleni(String radek) {
        String[] vysledek = radek.split(": ");
        return vysledek;
    }

}
