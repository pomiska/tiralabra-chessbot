package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Lahetti implements Nappula {

    private final Puoli puoli;
    private final NappulaTyyppi tyyppi = NappulaTyyppi.LAHETTI;
    private final int arvo = 3;

    public Lahetti(Puoli puoli) {
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