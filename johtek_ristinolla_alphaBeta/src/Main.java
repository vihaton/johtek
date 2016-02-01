
import java.util.ArrayDeque;
import java.util.ArrayList;


/**
 *
 * @author xvixvi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static String[][] tehtava = new String[][] {
        {"0","X","0"},
        {"X"," ","X"},
        {" ","0"," "},
    };
    
    public static void main(String[] args) {
//        System.out.println(AlphaBetaArvo(new Solmu(null, new Ristinolla(tehtava), "0")));
//        System.out.println(AlphaBetaArvo(new Solmu(null, new Ristinolla(), "0")));
        
        Solmu juurisolmu = luoPelipuu();
//        pelaaJaTulosta(juurisolmu);
        
//        voittoTest();
        }

    private static Solmu luoPelipuu() {
        Ristinolla pelipoyta = new Ristinolla();
        Solmu root = new Solmu(null, pelipoyta, "X");
        ArrayDeque<Solmu> solmuPino = new ArrayDeque<>();
        solmuPino.addFirst(root);
        int i = 0;
        int ristinVoittoja = 0;
        int nollanVoittoja = 0;
        int tasapeleja = 0;
        
        while (!solmuPino.isEmpty()) {
            Solmu vanhempi = solmuPino.poll();
            
            if (vanhempi.peliOhi()) {
                if (vanhempi.getArvo() > 0) ristinVoittoja++;
                if (vanhempi.getArvo() < 0) nollanVoittoja++;
                else tasapeleja++;
                continue;
            }
                
            vanhempi.luoLapsia();
            
            ArrayList<Solmu> lapset = vanhempi.getLapset();
            for (Solmu lapsi : lapset) {
                
                solmuPino.addFirst(lapsi);
                
                i++;
            }
        }
        tulostaStatsit(ristinVoittoja, nollanVoittoja,tasapeleja, i);
        return root;
    }

    private static void pelaaJaTulosta(Solmu juurisolmu) {
        ArrayDeque<Solmu> pelipuu = new ArrayDeque<>();
        pelipuu.add(juurisolmu);
        int pelitilanne = 0;
        
        while(!pelipuu.isEmpty()) {
            Solmu s = pelipuu.poll();
            
            ArrayList<Solmu> lapset = s.getLapset();
            for (Solmu lapsi : lapset) {
                pelipuu.addFirst(lapsi);
            }
            
            System.out.println(s);
            System.out.println("******* " + pelitilanne + " *******");
            pelitilanne++;
        }
    }
    
    private static void tulostaStatsit(int ristinVoittoja, int nollanVoittoja, int tasapeleja, int i) {
        System.out.println("ristin voittoja " +ristinVoittoja);
        System.out.println("nollan voittoja " +nollanVoittoja);
        System.out.println("tasapeleja " +tasapeleja +"\n");
        
        int tuloksia = ristinVoittoja+nollanVoittoja+tasapeleja;
        System.out.println("risti voitti " +(ristinVoittoja-nollanVoittoja) + " peliä nollaa enemmän.\n");
        int prosenttia = (int)(100*((double)ristinVoittoja/(double)tuloksia));
        System.out.println("risti voitti " +prosenttia +"% kaikista peleistä (myös tasapelit)");
        prosenttia = (int)(100*((double)nollanVoittoja/(double)tuloksia));
        System.out.println("nolla voitti " +prosenttia +"% kaikista peleistä (myös tasapelit)");
        System.out.println("");
    }
    
    static String[][] vaakaVoittoRisti = new String[][] {
        {" "," "," "},
        {" ","0","0"},
        {"X","X","X"}
    };
    
    static String[][] pystyVoittoNolla = new String[][] {
        {" "," ","0"},
        {" "," ","0"},
        {"X","X","0"}
    };
    static String[][] ristiVoittoRisti = new String[][] {
        {" "," ","X"},
        {"0","X","0"},
        {"X","0","0"}
    };

    private static void voittoTest() {
        Ristinolla vaakaristi = new Ristinolla(vaakaVoittoRisti);
        Ristinolla pystynolla = new Ristinolla(pystyVoittoNolla);
        Ristinolla ristiristi = new Ristinolla(ristiVoittoRisti);
        
        System.out.println(vaakaristi);
        System.out.println("voittiko risti: " +vaakaristi.voittikoRisti());
        System.out.println("voittiko nolla: " +vaakaristi.voittikoNolla());
        System.out.println("");
        System.out.println(pystynolla);
        System.out.println("voittiko risti: " +pystynolla.voittikoRisti());
        System.out.println("voittiko nolla: " +pystynolla.voittikoNolla());
        System.out.println("");
        System.out.println(ristiristi);
        System.out.println("voittiko risti: " +ristiristi.voittikoRisti());
        System.out.println("voittiko nolla: " +ristiristi.voittikoNolla());
    }

    private static int AlphaBetaArvo(Solmu solmu) {
        return maxArvo(solmu, -1, 1);
    }

    private static int maxArvo(Solmu solmu, int a, int b) {
        if (solmu.peliOhi()) {
//            System.out.println("arvo " + solmu.getArvo());
            return solmu.getArvo();
        }
        
        int v = Integer.MIN_VALUE;
        solmu.luoLapsia();
        
        ArrayList<Solmu> lapset = solmu.getLapset();
        for (Solmu lapsi : lapset) {
            v = Math.max(v, minArvo(lapsi, a, b));
//            if (v>=b) return v;
            a = Math.max(a, v);
        }
        return v;
    }

    private static int minArvo(Solmu solmu, int a, int b) {
        if (solmu.peliOhi()) {
//            System.out.println("arvo " + solmu.getArvo());
            return solmu.getArvo();
        }
        
        int v = Integer.MAX_VALUE;
        
        solmu.luoLapsia();
        ArrayList<Solmu> lapset = solmu.getLapset();
        for (Solmu lapsi : lapset) {
            v = Math.min(v, maxArvo(lapsi, a, b));
//            if (v<= a) return v;
            b = Math.min(v, b);
        }
        return v;
    }
}
