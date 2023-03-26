package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Torni implements Nappula {

    private final Puoli puoli;
    private final NappulaTyyppi tyyppi;
    private final int arvo = 4;

    public Torni(Puoli puoli) {
        this.puoli = puoli;
        if (this.puoli == Puoli.VALKOINEN) {
            this.tyyppi = NappulaTyyppi.V_TORNI;
        } else {
            this.tyyppi = NappulaTyyppi.M_TORNI;
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