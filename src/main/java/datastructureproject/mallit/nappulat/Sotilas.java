package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Sotilas implements Nappula {

    private final Puoli puoli;
    private final NappulaTyyppi tyyppi = NappulaTyyppi.SOTILAS;
    private final int arvo = 1;

    public Sotilas(Puoli puoli) {
        this.puoli = puoli;
    }

    @Override
    public Puoli getPuoli() {
        return this.puoli;
    }

    @Override
    public NappulaTyyppi getTyyppi() {
        return tyyppi;
    }

    @Override
    public int getArvo() {
        return this.arvo;
    }
}
