package datastructureproject;

import java.util.HashMap;

public class Arvioija {

    private HashMap<NappulaTyyppi, Integer> pisteytys;

    public Arvioija() {
        this.pisteytys = new HashMap<>();

        pisteytys.put(NappulaTyyppi.SOTILAS, 1);
        pisteytys.put(NappulaTyyppi.TORNI, 6);
        pisteytys.put(NappulaTyyppi.KUNINGATAR, 9);
        pisteytys.put(NappulaTyyppi.RATSU, 3);
        pisteytys.put(NappulaTyyppi.LAHETTI, 3);
        pisteytys.put(NappulaTyyppi.KUNINGAS, 100);
    }

    public int arvioiPelilauta(Pelilauta lauta) { // Toistaiseksi vain valkoiselle toimii
        HashMap<String, NappulaTyyppi> valkoiset = lauta.getValkoiset();
        HashMap<String, NappulaTyyppi> mustat = lauta.getMustat();
        int pisteet = 0;

        for (HashMap.Entry<String, NappulaTyyppi> set : valkoiset.entrySet()) {
            if (set.getValue() != null) {
                pisteet += pisteytys.get(set.getValue());
            }
        }

        for (HashMap.Entry<String, NappulaTyyppi> set : mustat.entrySet()) {
            if (set.getValue() != null) {
                pisteet -= pisteytys.get(set.getValue());
            }
        }

        return pisteet;
    }
}
