
import java.util.ArrayDeque;


/**
 *
 * @author xvixvi
 */
public class Ristinolla {

    private String[][] lauta;
    
    public Ristinolla() {
        lauta = new String[3][3];
        for (int i = 0; i < lauta.length; i++) {
            for (int j = 0; j < lauta[0].length; j++) {
                lauta[i][j] = " ";
            }
        }
    }
    
    public Ristinolla(String[][] pelitilanne) {
        this.lauta = pelitilanne;
    }
    
    public void teeSiirto(int i, int j, String merkki) {
        lauta[i][j] = merkki;
    }
    
    public void teeRisti(int i, int j) {
        lauta[i][j] = "X";
    }
    
    public void teeNolla(int i, int j) {
        lauta[i][j] = "0";
    }
    
    public boolean voittikoRisti() {
        return voittikoMerkki("X");
    }
    
    public boolean voittikoNolla() {
        return voittikoMerkki("0");
    }
    
    public ArrayDeque<Integer> getTyhjatRuudut() {
        ArrayDeque<Integer> jono = new ArrayDeque<>();
        for (int i = 0; i < lauta.length; i++) {
            for (int j = 0; j < lauta.length; j++) {
                if (lauta[i][j].equalsIgnoreCase(" ")) {
                    jono.add(i);
                    jono.add(j);
                }
            }
        }
        return jono;
    }
    
    public Ristinolla clone() {
        Ristinolla klooni = new Ristinolla();
        for (int i = 0; i < lauta.length; i++) {
            for (int j = 0; j < lauta.length; j++) {
                klooni.teeSiirto(i, j, lauta[i][j]);
            }
        }
        return klooni;
    }
    
    public boolean voittikoMerkki(String merkki) {
        if (vaakavoitto(merkki)) return true;
        if (pystyvoitto(merkki)) return true;
        if (ristivoitto(merkki)) return true;
        return false;
    }
    
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < lauta.length; i++) {
            s = s.concat("|");
            for (int j = 0; j < lauta[0].length; j++) {
                s = s.concat(lauta[i][j] + "|");
            }
            if (i!=lauta.length-1) s = s.concat("\n");
        }
        return s;
    }

    private boolean vaakavoitto(String merkki) {
        for (String[] rivi : lauta) {
            for (int i = 0; i < rivi.length; i++) {
                if (!rivi[i].equalsIgnoreCase(merkki)) break;
                if (i == rivi.length-1) return true;
            }
        }
        return false;
    }

    private boolean pystyvoitto(String merkki) {
        for (int i = 0; i < lauta[0].length; i++) {
            for (int j = 0; j < lauta.length; j++) {
                if (!lauta[j][i].equalsIgnoreCase(merkki)) break;
                if (j == lauta.length-1) return true;
            }
        }
        return false;
    }

    private boolean ristivoitto(String merkki) {
        for (int j = 0; j < lauta.length; j++) {
            if (!lauta[j][j].equalsIgnoreCase(merkki)) break;
            if (j == lauta.length-1) return true;
        }
        for (int i = 0; i < lauta.length; i++) {
            if (!lauta[lauta.length-1-i][i].equalsIgnoreCase(merkki)) break;
            if (i == lauta.length-1) return true;
        }
        return false;
    }
}
