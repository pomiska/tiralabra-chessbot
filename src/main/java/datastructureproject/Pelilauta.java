package datastructureproject;

import java.util.ArrayList;
import java.util.HashMap;

import chess.model.Side;

public class Pelilauta {

    private HashMap<String, NappulaTyyppi> valkoiset; // Tallennetaan kummankin puolen nappuloista tieto tyylillä
                                                      // <Ruutu, Nappula>
    private HashMap<String, NappulaTyyppi> mustat;

    public Pelilauta() {
        this.valkoiset = new HashMap<>();
        this.mustat = new HashMap<>();

        this.uusiLauta();
    }

    private void uusiLauta() {
        valkoiset.put("a1", NappulaTyyppi.TORNI);
        valkoiset.put("b1", NappulaTyyppi.RATSU);
        valkoiset.put("c1", NappulaTyyppi.LAHETTI);
        valkoiset.put("d1", NappulaTyyppi.KUNINGATAR);
        valkoiset.put("e1", NappulaTyyppi.KUNINGAS);
        valkoiset.put("f1", NappulaTyyppi.LAHETTI);
        valkoiset.put("g1", NappulaTyyppi.RATSU);
        valkoiset.put("h1", NappulaTyyppi.TORNI);
        valkoiset.put("a2", NappulaTyyppi.SOTILAS);
        valkoiset.put("b2", NappulaTyyppi.SOTILAS);
        valkoiset.put("c2", NappulaTyyppi.SOTILAS);
        valkoiset.put("d2", NappulaTyyppi.SOTILAS);
        valkoiset.put("e2", NappulaTyyppi.SOTILAS);
        valkoiset.put("f2", NappulaTyyppi.SOTILAS);
        valkoiset.put("g2", NappulaTyyppi.SOTILAS);
        valkoiset.put("h2", NappulaTyyppi.SOTILAS);

        mustat.put("a8", NappulaTyyppi.TORNI);
        mustat.put("b8", NappulaTyyppi.RATSU);
        mustat.put("c8", NappulaTyyppi.LAHETTI);
        mustat.put("d8", NappulaTyyppi.KUNINGATAR);
        mustat.put("e8", NappulaTyyppi.KUNINGAS);
        mustat.put("f8", NappulaTyyppi.LAHETTI);
        mustat.put("g8", NappulaTyyppi.RATSU);
        mustat.put("h8", NappulaTyyppi.TORNI);
        mustat.put("a7", NappulaTyyppi.SOTILAS);
        mustat.put("b7", NappulaTyyppi.SOTILAS);
        mustat.put("c7", NappulaTyyppi.SOTILAS);
        mustat.put("d7", NappulaTyyppi.SOTILAS);
        mustat.put("e7", NappulaTyyppi.SOTILAS);
        mustat.put("f7", NappulaTyyppi.SOTILAS);
        mustat.put("g7", NappulaTyyppi.SOTILAS);
        mustat.put("h7", NappulaTyyppi.SOTILAS);

    }

    public void siirraNappulaRuutuun(String lahtoRuutu, String kohdeRuutu, Side puoli) {
        if (puoli == Side.WHITE) {
            asetaNappulaRuutuun(kohdeRuutu, puoli, valkoiset.get(lahtoRuutu));
        } else if (puoli == Side.BLACK) {
            asetaNappulaRuutuun(kohdeRuutu, puoli, mustat.get(lahtoRuutu));
        }
        poistaNappulaRuudusta(lahtoRuutu, puoli);
    }

    private void asetaNappulaRuutuun(String ruutu, Side puoli, NappulaTyyppi tyyppi) {
        if (puoli == Side.WHITE) {
            valkoiset.put(ruutu, tyyppi);
            if (mustat.containsKey(ruutu)) {
                mustat.remove(ruutu);
            }
        } else if (puoli == Side.BLACK) {
            mustat.put(ruutu, tyyppi);
            if (valkoiset.containsKey(ruutu)) {
                valkoiset.remove(ruutu);
            }
        }
    }

    private void poistaNappulaRuudusta(String ruutu, Side puoli) {
        if (puoli == Side.WHITE) {
            valkoiset.remove(ruutu);
        } else if (puoli == Side.BLACK) {
            mustat.remove(ruutu);
        }
    }

    public ArrayList<String> etsiLaillisetSiirrot(Side puoli) {
        SiirtoGeneraattori sg = new SiirtoGeneraattori(valkoiset, mustat, puoli);
        ArrayList<String> siirrot = new ArrayList<>();
        if (puoli == Side.WHITE) {
            siirrot = sg.valkoisenSiirrot();
        } else {
            siirrot = sg.mustanSiirrot();
        }
        return sg.poistaValkoisenLaittomatSiirrot(siirrot);
    }

    public NappulaTyyppi getNappulaRuudusta(String ruutu) {
        if (valkoiset.containsKey(ruutu)) {
            return valkoiset.get(ruutu);
        }
        if (mustat.containsKey(ruutu)) {
            return mustat.get(ruutu);
        }
        return null;
    }
}
