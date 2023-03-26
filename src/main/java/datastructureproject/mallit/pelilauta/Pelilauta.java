package datastructureproject.mallit.pelilauta;

import datastructureproject.mallit.nappulat.Kuningas;
import datastructureproject.mallit.nappulat.Kuningatar;
import datastructureproject.mallit.nappulat.Lahetti;
import datastructureproject.mallit.nappulat.Nappula;
import datastructureproject.mallit.nappulat.Ratsu;
import datastructureproject.mallit.nappulat.Sotilas;
import datastructureproject.mallit.nappulat.Torni;

public class Pelilauta {

    private Nappula[][] ruudut;
    private Puoli vuoro;

    public Pelilauta() {
        this.ruudut = new Nappula[8][8]; // Nappula ruudussa [rivi][sarake]
        this.vuoro = Puoli.VALKOINEN;
        this.uusiLauta();
    }

    private void uusiLauta() {
        for (int i = 0; i < 8; i++) {
            this.ruudut[1][i] = new Sotilas(Puoli.VALKOINEN);
            this.ruudut[6][i] = new Sotilas(Puoli.MUSTA);
        }
        this.ruudut[0][0] = new Torni(Puoli.VALKOINEN);
        this.ruudut[7][0] = new Torni(Puoli.MUSTA);
        this.ruudut[0][7] = new Torni(Puoli.VALKOINEN);
        this.ruudut[7][7] = new Torni(Puoli.MUSTA);

        this.ruudut[0][1] = new Ratsu(Puoli.VALKOINEN);
        this.ruudut[7][1] = new Ratsu(Puoli.MUSTA);
        this.ruudut[0][6] = new Ratsu(Puoli.VALKOINEN);
        this.ruudut[7][6] = new Ratsu(Puoli.MUSTA);

        this.ruudut[0][2] = new Lahetti(Puoli.VALKOINEN);
        this.ruudut[7][2] = new Lahetti(Puoli.MUSTA);
        this.ruudut[0][5] = new Lahetti(Puoli.VALKOINEN);
        this.ruudut[7][5] = new Lahetti(Puoli.MUSTA);

        this.ruudut[0][3] = new Kuningatar(Puoli.VALKOINEN);
        this.ruudut[7][3] = new Kuningatar(Puoli.MUSTA);

        this.ruudut[0][4] = new Kuningas(Puoli.VALKOINEN);
        this.ruudut[7][4] = new Kuningas(Puoli.MUSTA);
    }

    private void siirraNappulaRuutuun(int lahtoRivi, int lahtoSarake, int kohdeRivi, int kohdeSarake, Nappula nappula) {
        this.asetaNappulaRuutuun(kohdeRivi, kohdeSarake, nappula);
        this.poistaNappulaRuudusta(lahtoRivi, lahtoSarake);
    }

    private void siirraNappulaRuutuun(int lahtoRivi, int lahtoSarake, int kohdeRivi, int kohdeSarake) {
        this.asetaNappulaRuutuun(kohdeRivi, kohdeSarake, this.ruudut[lahtoRivi][lahtoSarake]);
        this.poistaNappulaRuudusta(lahtoRivi, lahtoSarake);
    }

    private void asetaNappulaRuutuun(int rivi, int sarake, Nappula nappula) {
        this.ruudut[rivi][sarake] = nappula;
    }

    private void poistaNappulaRuudusta(int rivi, int sarake) {
        this.ruudut[rivi][sarake] = null;
    }

    private void vaihdaVuoroa() {
        if (this.vuoro == Puoli.VALKOINEN) {
            this.vuoro = Puoli.MUSTA;
        } else {
            this.vuoro = Puoli.VALKOINEN;
        }
    }
}
