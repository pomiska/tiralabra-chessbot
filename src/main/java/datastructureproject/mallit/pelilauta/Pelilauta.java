package datastructureproject.mallit.pelilauta;

import java.util.ArrayList;

import datastructureproject.mallit.nappulat.Kuningas;
import datastructureproject.mallit.nappulat.Kuningatar;
import datastructureproject.mallit.nappulat.Lahetti;
import datastructureproject.mallit.nappulat.Nappula;
import datastructureproject.mallit.nappulat.NappulaTyyppi;
import datastructureproject.mallit.nappulat.Ratsu;
import datastructureproject.mallit.nappulat.Sotilas;
import datastructureproject.mallit.nappulat.Torni;
import datastructureproject.mallit.nappulat.Tyhja;

public class Pelilauta {

    private Nappula[] ruudut;
    private Puoli vuoro;
    private ArrayList<String> mahdollisetSiirrot;
    private ArrayList<String> laillisetSiirrot;
    private ArrayList<NappulaTyyppi> valkoiset;
    private ArrayList<NappulaTyyppi> mustat;

    public Pelilauta() {
        this.ruudut = new Nappula[64]; // Ensimmäinen ruutu on valkoisen vasemmalla
        this.valkoiset = new ArrayList<>();
        this.mustat = new ArrayList<>();
        this.vuoro = Puoli.VALKOINEN;
        this.mahdollisetSiirrot = new ArrayList<>();
        this.laillisetSiirrot = new ArrayList<>();
        this.uusiLauta();
    }

    private void uusiLauta() {
        for (int i = 8; i < 16; i++) {
            this.ruudut[i] = new Sotilas(Puoli.VALKOINEN);
            this.valkoiset.add(i, NappulaTyyppi.V_SOTILAS);
        }
        for (int i = 48; i < 56; i++) {
            this.ruudut[i] = new Sotilas(Puoli.MUSTA);
            this.mustat.add(i, NappulaTyyppi.M_SOTILAS);
        }
        this.ruudut[0] = new Torni(Puoli.VALKOINEN);
        this.valkoiset.add(0, NappulaTyyppi.V_TORNI);
        this.ruudut[63] = new Torni(Puoli.MUSTA);
        this.mustat.add(63, NappulaTyyppi.M_TORNI);
        this.ruudut[7] = new Torni(Puoli.VALKOINEN);
        this.valkoiset.add(7, NappulaTyyppi.V_TORNI);
        this.ruudut[56] = new Torni(Puoli.MUSTA);
        this.mustat.add(56, NappulaTyyppi.M_TORNI);

        this.ruudut[1] = new Ratsu(Puoli.VALKOINEN);
        this.valkoiset.add(1, NappulaTyyppi.V_RATSU);
        this.ruudut[62] = new Ratsu(Puoli.MUSTA);
        this.mustat.add(62, NappulaTyyppi.M_RATSU);
        this.ruudut[6] = new Ratsu(Puoli.VALKOINEN);
        this.valkoiset.add(6, NappulaTyyppi.V_RATSU);
        this.ruudut[57] = new Ratsu(Puoli.MUSTA);
        this.mustat.add(57, NappulaTyyppi.M_RATSU);

        this.ruudut[2] = new Lahetti(Puoli.VALKOINEN);
        this.valkoiset.add(2, NappulaTyyppi.V_LAHETTI);
        this.ruudut[61] = new Lahetti(Puoli.MUSTA);
        this.mustat.add(61, NappulaTyyppi.M_LAHETTI);
        this.ruudut[5] = new Lahetti(Puoli.VALKOINEN);
        this.valkoiset.add(5, NappulaTyyppi.V_LAHETTI);
        this.ruudut[58] = new Lahetti(Puoli.MUSTA);
        this.mustat.add(58, NappulaTyyppi.M_LAHETTI);

        this.ruudut[3] = new Kuningatar(Puoli.VALKOINEN);
        this.valkoiset.add(3, NappulaTyyppi.V_KUNINGATAR);
        this.ruudut[59] = new Kuningatar(Puoli.MUSTA);
        this.mustat.add(59, NappulaTyyppi.M_KUNINGATAR);

        this.ruudut[4] = new Kuningas(Puoli.VALKOINEN);
        this.valkoiset.add(4, NappulaTyyppi.V_KUNINGAS);
        this.ruudut[60] = new Kuningas(Puoli.MUSTA);
        this.mustat.add(60, NappulaTyyppi.M_KUNINGATAR);

        for (int i = 16; i < 56; i++) {
            this.ruudut[i] = new Tyhja();
        }
    }

    private void siirraNappulaRuutuun(int lahtoRuutu, int kohdeRuutu, Nappula nappula) {
        this.asetaNappulaRuutuun(kohdeRuutu, nappula);
        this.poistaNappulaRuudusta(lahtoRuutu);
    }

    private void asetaNappulaRuutuun(int ruutu, Nappula nappula) {
        this.ruudut[ruutu] = nappula;
    }

    private void poistaNappulaRuudusta(int ruutu) {
        this.ruudut[ruutu] = new Tyhja();
    }

    private ArrayList<String> etsiLaillisetSiirrot(Puoli puoli) {
        if (puoli == Puoli.VALKOINEN) {
            return valkoisenSiirrot();
        } else {
            return mustanSiirrot();
        }
    }

    private ArrayList<String> valkoisenSiirrot() {
        // idea:
        // Etsitään kaikki mahdolliset siirrot jokaiselle valkoisen nappulalle,
        // jonka jälkeen tarkistetaan meneekö kuningas shakkiin mikäli siirron
        // toteuttaa.
        // Tämän voisi toteuttaa helposti tarkastamalla kuninkaasta lähtevät suorat ja
        // vinot linjat joka suuntaan.
        // Toteutetaan ensi viikolla.
        //

        return null;
    }

    private ArrayList<String> mustanSiirrot() {
        return null;
    }

    private void vaihdaVuoroa() {
        if (this.vuoro == Puoli.VALKOINEN) {
            this.vuoro = Puoli.MUSTA;
        } else {
            this.vuoro = Puoli.VALKOINEN;
        }
    }
}
