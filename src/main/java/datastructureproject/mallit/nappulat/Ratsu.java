package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Ratsu implements Nappula {

    private final Puoli puoli;
    private final NappulaTyyppi tyyppi;
    private final int arvo = 3;

    public Ratsu(Puoli puoli) {
        this.puoli = puoli;
        if (this.puoli == Puoli.VALKOINEN) {
            this.tyyppi = NappulaTyyppi.V_RATSU;
        } else {
            this.tyyppi = NappulaTyyppi.M_RATSU;
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