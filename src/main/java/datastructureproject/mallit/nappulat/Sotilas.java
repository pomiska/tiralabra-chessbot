package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Sotilas implements Nappula {

    private final Puoli puoli;
    private final NappulaTyyppi tyyppi;
    private final int arvo = 1;

    public Sotilas(Puoli puoli) {
        this.puoli = puoli;
        if (this.puoli == Puoli.VALKOINEN) {
            this.tyyppi = NappulaTyyppi.V_SOTILAS;
        } else {
            this.tyyppi = NappulaTyyppi.M_SOTILAS;
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
