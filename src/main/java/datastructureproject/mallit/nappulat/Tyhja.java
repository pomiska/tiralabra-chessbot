package datastructureproject.mallit.nappulat;

import datastructureproject.mallit.pelilauta.Puoli;

public class Tyhja implements Nappula {
    private final NappulaTyyppi tyyppi = NappulaTyyppi.TYHJA;

    public NappulaTyyppi getTyyppi() {
        return tyyppi;
    }

    public Puoli getPuoli() {
        return null;
    }

    public int getArvo() {
        return 0;
    }
}
