package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Kuningatar implements Nappula {

    private final Puoli puoli;
    private final NappulaTyyppi tyyppi;
    private final int arvo = 9;

    public Kuningatar(Puoli puoli) {
        this.puoli = puoli;
        if (this.puoli == Puoli.VALKOINEN) {
            this.tyyppi = NappulaTyyppi.V_KUNINGATAR;
        } else {
            this.tyyppi = NappulaTyyppi.M_KUNINGATAR;
        }
    }

    public Puoli getPuoli() {
        return this.puoli;
    }

    public NappulaTyyppi getTyyppi() {
        return tyyppi;
    }

    public int getArvo() {
        return this.arvo;
    }
}
