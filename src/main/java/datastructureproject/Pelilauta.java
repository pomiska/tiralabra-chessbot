package datastructureproject;

import java.util.ArrayList;
import java.util.HashMap;

import chess.model.Side;

public class Pelilauta {

    private HashMap<String, NappulaTyyppi> valkoiset; // Tallennetaan kummankin puolen nappuloista tieto tyylillä
    // <Ruutu, NappulaTyyppi>
    private HashMap<String, NappulaTyyppi> mustat; //
    private ArrayList<String> tehdytSiirrot;
    private Boolean valkoisenOikealleTornitus; // pidetään kirjaa voiko tornittaa
    private Boolean valkoisenVasemmalleTornitus;
    private Boolean mustanOikealleTornitus;
    private Boolean mustanVasemmalleTornitus;
    private Boolean poistettuViimeksi;
    private NappulaTyyppi poistettu;
    private Side liikuttiViimeksi;

    public Pelilauta() {
        this.valkoiset = new HashMap<>();
        this.mustat = new HashMap<>();
        this.tehdytSiirrot = new ArrayList<>();
        this.valkoisenOikealleTornitus = true;
        this.valkoisenVasemmalleTornitus = true;
        this.mustanOikealleTornitus = true;
        this.mustanVasemmalleTornitus = true;

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

    public void siirraNappulaRuutuun(String siirto, Side puoli) {
        liikuttiViimeksi = puoli;
        String lahtoRuutu = siirto.substring(0, 2);
        String kohdeRuutu = siirto.substring(2, 4);
        if (siirto.length() > 4) {
            String korotus = siirto.substring(4);
            NappulaTyyppi k = null;
            if (korotus.equals("q")) {
                k = NappulaTyyppi.KUNINGATAR;
            } else if (korotus.equals("r")) {
                k = NappulaTyyppi.TORNI;
            } else if (korotus.equals("n")) {
                k = NappulaTyyppi.RATSU;
            } else if (korotus.equals("b")) {
                k = NappulaTyyppi.LAHETTI;
            }
            asetaNappulaRuutuun(kohdeRuutu, puoli, k);
            poistaNappulaRuudusta(lahtoRuutu, puoli);
            return;
        }
        if (puoli == Side.WHITE) {
            asetaNappulaRuutuun(kohdeRuutu, puoli, valkoiset.get(lahtoRuutu));
        } else {
            asetaNappulaRuutuun(kohdeRuutu, puoli, mustat.get(lahtoRuutu));
        }
        poistaNappulaRuudusta(lahtoRuutu, puoli);
        tehdytSiirrot.add(siirto);
        paivitaTornitus(lahtoRuutu, kohdeRuutu);
    }

    private void asetaNappulaRuutuun(String ruutu, Side puoli, NappulaTyyppi tyyppi) {
        if (puoli == Side.WHITE) {
            poistettuViimeksi = false;
            valkoiset.put(ruutu, tyyppi);
            if (mustat.containsKey(ruutu)) { // Jos vastustajalla on nappula meidän kohderuudussa, se poistetaan
                poistettuViimeksi = true;
                poistettu = mustat.get(ruutu);
                mustat.remove(ruutu);
            }
        } else if (puoli == Side.BLACK) {
            poistettuViimeksi = false;
            mustat.put(ruutu, tyyppi);
            if (valkoiset.containsKey(ruutu)) {
                poistettuViimeksi = true;
                poistettu = valkoiset.get(ruutu);
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

    public void peruViimeisinSiirto() {
        String siirto = tehdytSiirrot.get(tehdytSiirrot.size() - 1);
        String lahtoRuutu = siirto.substring(2, 4);
        String kohdeRuutu = siirto.substring(0, 2);
        if (siirto.length() > 4) {
            if (liikuttiViimeksi == Side.WHITE) {
                valkoiset.put(lahtoRuutu, NappulaTyyppi.SOTILAS);
                if (poistettuViimeksi) {
                    if (liikuttiViimeksi == Side.WHITE) {
                        mustat.put(lahtoRuutu, poistettu);
                    } else {
                        valkoiset.put(lahtoRuutu, poistettu);
                    }
                }
            } else {
                mustat.put(lahtoRuutu, NappulaTyyppi.SOTILAS);
                if (poistettuViimeksi) {
                    if (liikuttiViimeksi == Side.WHITE) {
                        mustat.put(lahtoRuutu, poistettu);
                    } else {
                        valkoiset.put(lahtoRuutu, poistettu);
                    }
                }
            }
        } else {
            this.siirraNappulaRuutuun(lahtoRuutu + kohdeRuutu, liikuttiViimeksi);
            if (poistettuViimeksi) {
                if (liikuttiViimeksi == Side.WHITE) {
                    mustat.put(lahtoRuutu, poistettu);
                } else {
                    valkoiset.put(lahtoRuutu, poistettu);
                }
            }
        }
        poistaNappulaRuudusta(lahtoRuutu, liikuttiViimeksi);
        tehdytSiirrot.remove(tehdytSiirrot.size() - 1);

    }

    private void paivitaTornitus(String lahtoRuutu, String kohdeRuutu) {
        if (lahtoRuutu.equals("e1")) {
            this.valkoisenOikealleTornitus = false;
            this.valkoisenVasemmalleTornitus = false;
        }
        if (lahtoRuutu.equals("a1") || kohdeRuutu.equals("a1")) {
            this.valkoisenVasemmalleTornitus = false;
        }
        if (lahtoRuutu.equals("h1") || kohdeRuutu.equals("h1")) {
            this.valkoisenOikealleTornitus = false;
        }
        if (lahtoRuutu.equals("e8")) {
            this.mustanOikealleTornitus = false;
            this.mustanVasemmalleTornitus = false;
        }
        if (lahtoRuutu.equals("a8") || kohdeRuutu.equals("a8")) {
            this.mustanVasemmalleTornitus = false;
        }
        if (lahtoRuutu.equals("h8") || kohdeRuutu.equals("h8")) {
            this.mustanOikealleTornitus = false;
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
        return sg.poistaLaittomatSiirrot(siirrot);
    }

    public NappulaTyyppi getNappulaRuudusta(String ruutu) { // Yksikkötestejä varten getteri
        if (valkoiset.containsKey(ruutu)) {
            return valkoiset.get(ruutu);
        }
        if (mustat.containsKey(ruutu)) {
            return mustat.get(ruutu);
        }
        return null;
    }

    public HashMap<String, NappulaTyyppi> getValkoiset() { // Yksikkötestejä varten
        return valkoiset;
    }

    public HashMap<String, NappulaTyyppi> getMustat() { // Yksikkötestejä varten
        return mustat;
    }

    public void pelaaSiirrot(String siirrot) { // Yksikkötestejä varten
        String[] s = siirrot.split(" ");
        Side puoli = null;
        for (int i = 0; i < s.length; i++) {
            if (i == 0 || (i % 2) == 0) {
                puoli = Side.WHITE;
            } else {
                puoli = Side.BLACK;
            }
            siirraNappulaRuutuun(s[i], puoli);
        }
    }

    public void setValkoiset(HashMap<String, NappulaTyyppi> v) {
        valkoiset.clear();
        for (HashMap.Entry<String, NappulaTyyppi> set : v.entrySet()) {
            String ruutu = set.getKey();
            NappulaTyyppi n = set.getValue();
            valkoiset.put(ruutu, n);
        }
    }

    public void setMustat(HashMap<String, NappulaTyyppi> m) {
        mustat.clear();
        for (HashMap.Entry<String, NappulaTyyppi> set : m.entrySet()) {
            String ruutu = set.getKey();
            NappulaTyyppi n = set.getValue();
            mustat.put(ruutu, n);
        }
    }

    public Pelilauta kopioiPelilauta() {
        Pelilauta l = new Pelilauta();
        l.setValkoiset(valkoiset);
        l.setMustat(mustat);
        return l;
    }
}
