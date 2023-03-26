package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Torni implements Nappula {

    private final Puoli puoli;
    private final NappulaTyyppi tyyppi = NappulaTyyppi.TORNI;
    private final int arvo = 4;

    public Torni(Puoli puoli) {
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