package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] lukujoukko;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        lukujoukko = new int[KAPASITEETTI];
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        lukujoukko = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;

    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0 || kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetin ja kasvatuskoon täytyy olla yli 0");
        }
        lukujoukko = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            lukujoukko[alkioidenLkm] = luku;
            alkioidenLkm++;
            if (alkioidenLkm == lukujoukko.length) {
                kasvataTaulukkoa();
            }
            return true;
        }
        return false;
    }

    public void kasvataTaulukkoa() {
        int[] vanhaJoukko = new int[lukujoukko.length];
        kopioiTaulukko(lukujoukko, vanhaJoukko);
        lukujoukko = new int[alkioidenLkm + kasvatuskoko];
        kopioiTaulukko(vanhaJoukko, lukujoukko);
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == lukujoukko[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        int paikka = etsiLukuTaulukosta(luku);
        if(paikka != -1){
            for (int i = paikka; i < alkioidenLkm - 1; i++) {
                    lukujoukko[i] = lukujoukko[i + 1];
                }
                alkioidenLkm--;
                return true;
        }
        return false;
    }
    
    public int etsiLukuTaulukosta(int luku){
        int paikka = -1;
        for(int i = 0; i < alkioidenLkm; i++) {
            if(luku == lukujoukko[i]) {
                paikka = i;
            }
        }
        return paikka;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        }
        String tuotos = "{";
        for (int i = 0; i < alkioidenLkm - 1; i++) {
            tuotos += lukujoukko[i] + ", ";
        }
        tuotos += lukujoukko[alkioidenLkm - 1] + "}";
        return tuotos;
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = lukujoukko[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        int[] bTaulu = b.toIntArray();
        for (int j = 0; j < bTaulu.length; j++) {
            a.lisaa(bTaulu[j]);
        }
        return a;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        for (int i = 0; i < a.alkioidenLkm; i++) {
            for (int j = 0; j < b.alkioidenLkm; j++) {
                if (!a.kuuluu(j)) {
                    a.poista(j);
                }
            }
        }
        return a;
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < bTaulu.length; i++) {
            a.poista(bTaulu[i]);
        }
        return a;
    }

}
