package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Kuningatar implements Nappula {
    
    private final Puoli puoli;
    private final NappulaTyyppi tyyppi = NappulaTyyppi.KUNINGATAR;
    private final int arvo = 9;
    
    public Kuningatar(Puoli puoli) {
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
