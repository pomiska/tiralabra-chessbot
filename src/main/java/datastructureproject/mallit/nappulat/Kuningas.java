package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Kuningas implements Nappula {
    
    private final Puoli puoli;
    private final NappulaTyyppi tyyppi = NappulaTyyppi.KUNINGAS;
    private final int arvo = Integer.MAX_VALUE;
    
    public Kuningas(Puoli puoli) {
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
