
import java.io.*;
import java.util.*;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        alusta();
        System.out.println(spamVaiHam("/home/xvixvi/NetBeansProjects/johtek/spamham/src/files/hamesim.txt"));
        System.out.println(spamVaiHam("/home/xvixvi/NetBeansProjects/johtek/spamham/src/files/spamesim.txt"));
    }

    private static double spamVaiHam(String fileName) {
        double logP = 0;
        Scanner s = luoLukija(fileName);
        
        while (s.hasNext()) {
            String rivi = s.nextLine();
            String[] sanat = rivi.split(" ");
            
            for (String sana : sanat) {
                logP += logSVH(sana);
            }
        }
        
        return Math.pow(10, logP);
    }
    
    private static Scanner luoLukija(String fileName) {
        try {
            Scanner s = new Scanner(new File(fileName));
            return s;
        } catch (Exception e) {
            System.out.println("hups@luolukija");
            return null;
        }
    }

    private static double logSVH(String sana) {
        return Math.log((getCount(spamCount, sana) / spamSanoja) / (getCount(hamCount, sana) / hamSanoja));
    }

    private static double getCount(HashMap<String,Double> kartta, String sana) {
        if (!kartta.containsKey(sana)) return 0.1;
        return kartta.get(sana);
    }
    
    private final static double spamSanoja = 75268;
    private final static double hamSanoja = 290673;
    private final static HashMap<String, Double> hamCount = new HashMap<>();
    private final static HashMap<String, Double> spamCount = new HashMap<>();

    private static void alusta() {
        Scanner sHam = luoLukija("/home/xvixvi/NetBeansProjects/johtek/spamham/src/files/hamcount.txt");
        Scanner sSpam = luoLukija("/home/xvixvi/NetBeansProjects/johtek/spamham/src/files/spamcount.txt");
        
        siirraKarttaan(sHam, hamCount);
        siirraKarttaan(sSpam, spamCount);
    }

    private static void siirraKarttaan(Scanner s, HashMap<String, Double> count) {
        while (s.hasNext()) {
            String rivi = s.nextLine();
            Double d = Double.valueOf(rivi.substring(0, 4).trim());
            String sana = rivi.substring(5).toLowerCase();
            count.put(sana, d);
        }
    }
}
