package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Kuningas implements Nappula {

    private final Puoli puoli;
    private final NappulaTyyppi tyyppi;
    private final int arvo = Integer.MAX_VALUE;

    public Kuningas(Puoli puoli) {
        this.puoli = puoli;
        if (this.puoli == Puoli.VALKOINEN) {
            this.tyyppi = NappulaTyyppi.V_KUNINGAS;
        } else {
            this.tyyppi = NappulaTyyppi.M_KUNINGAS;
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
