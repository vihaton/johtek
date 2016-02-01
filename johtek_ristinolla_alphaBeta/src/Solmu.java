
import java.util.ArrayDeque;
import java.util.ArrayList;


/**
 *
 * @author xvixvi
 */
public class Solmu {

    private Ristinolla pelitilanne;
    private int arvo;
    private ArrayList<Solmu> lapset;
    private Solmu vanhempi;
    private String merkki;
    
    public Solmu(Solmu vanhempi, Ristinolla pelitilanne, String m) {
        this.vanhempi = vanhempi;
        this.pelitilanne = pelitilanne;
        arvo = 0;
        lapset = new ArrayList<>();
        merkki = m;
    }
    
    public String getMerkki() {
        return merkki;
    }
    
    public void lisaaLapsi(Solmu s) {
        lapset.add(s);
    }
    
    public ArrayList<Solmu> getLapset() {
        return lapset;
    }
    
    public int getArvo() {
        return arvo;
    }
    
    public Ristinolla getTilanne() {
        return pelitilanne;
    }
    
    public boolean peliOhi() {
        if (pelitilanne.voittikoNolla()) {
            arvo = -1;
            return true;
        }
        if (pelitilanne.voittikoRisti()) {
            arvo = 1;
            return true;
        }
        return pelitilanne.getTyhjatRuudut().isEmpty();
    }
    
    public void luoLapsia() {
        ArrayDeque<Integer> koordinaatit = pelitilanne.getTyhjatRuudut();
        
        while (!koordinaatit.isEmpty()) {
            int i = koordinaatit.poll();
            int j = koordinaatit.poll();
            
            String uusiMerkki = "";
            if (merkki.equalsIgnoreCase("X")) uusiMerkki = "0";
            else uusiMerkki = "X";
            
            Solmu lapsi = new Solmu(this, pelitilanne.clone(), uusiMerkki);
            lapsi.getTilanne().teeSiirto(i, j, merkki);
            lapset.add(lapsi);
        }
    }
    
    @Override
    public String toString() {
        String s = merkki + ":n vuoro tilanteessa\n";
        s = s.concat(pelitilanne.toString());
        return s;
    }
}
