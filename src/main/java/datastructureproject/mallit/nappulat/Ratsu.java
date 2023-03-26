package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Ratsu implements Nappula {

    private final Puoli puoli;
    private final NappulaTyyppi tyyppi = NappulaTyyppi.RATSU;
    private final int arvo = 3;

    public Ratsu(Puoli puoli) {
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