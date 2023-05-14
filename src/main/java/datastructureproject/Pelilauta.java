package datastructureproject;

import java.util.ArrayList;
import java.util.Arrays;

import chess.model.Side;

public class Pelilauta {

    /*
     * Tämän avulla saadaan linjojen indeksit helposti, joka helpottaa taulukoiden
     * käsittelyä
     */
    private static final ArrayList<Character> LINJAT;
    static {
        LINJAT = new ArrayList<>();
        LINJAT.add('a');
        LINJAT.add('b');
        LINJAT.add('c');
        LINJAT.add('d');
        LINJAT.add('e');
        LINJAT.add('f');
        LINJAT.add('g');
        LINJAT.add('h');
    }

    /*
     * Tallennetaan tieto nappuloista tyylillä [linjan indeksi][rivin indeksi] =
     * nappulan tyyppi
     */
    private char[][] valkoiset;
    private char[][] mustat;

    private ArrayList<String> tehdytSiirrot;

    /* Pidetään kirjaa voiko tornittaa */
    private Boolean valkoisenOikealleTornitus;
    private Boolean valkoisenVasemmalleTornitus;
    private Boolean mustanOikealleTornitus;
    private Boolean mustanVasemmalleTornitus;

    public Pelilauta() {
        this.valkoiset = new char[8][8];
        this.mustat = new char[8][8];
        this.tehdytSiirrot = new ArrayList<>();
        this.valkoisenOikealleTornitus = true;
        this.valkoisenVasemmalleTornitus = true;
        this.mustanOikealleTornitus = true;
        this.mustanVasemmalleTornitus = true;
        this.uusiLauta();
    }

    private void uusiLauta() {
        for (int i = 0; i < valkoiset.length; i++) {
            Arrays.fill(valkoiset[i], ' ');
            Arrays.fill(mustat[i], ' ');
        }

        valkoiset[0][0] = 'r';
        valkoiset[1][0] = 'n';
        valkoiset[2][0] = 'b';
        valkoiset[3][0] = 'q';
        valkoiset[4][0] = 'k';
        valkoiset[5][0] = 'b';
        valkoiset[6][0] = 'n';
        valkoiset[7][0] = 'r';
        valkoiset[0][1] = 'p';
        valkoiset[1][1] = 'p';
        valkoiset[2][1] = 'p';
        valkoiset[3][1] = 'p';
        valkoiset[4][1] = 'p';
        valkoiset[5][1] = 'p';
        valkoiset[6][1] = 'p';
        valkoiset[7][1] = 'p';

        mustat[0][7] = 'r';
        mustat[1][7] = 'n';
        mustat[2][7] = 'b';
        mustat[3][7] = 'q';
        mustat[4][7] = 'k';
        mustat[5][7] = 'b';
        mustat[6][7] = 'n';
        mustat[7][7] = 'r';
        mustat[0][6] = 'p';
        mustat[1][6] = 'p';
        mustat[2][6] = 'p';
        mustat[3][6] = 'p';
        mustat[4][6] = 'p';
        mustat[5][6] = 'p';
        mustat[6][6] = 'p';
        mustat[7][6] = 'p';
    }

    /*
     * @param siirto pelattava siirto UCI-muodossa
     * 
     * @param puoli siirron tekevän pelaajan väri
     */
    public void teeSiirto(String siirto, Side puoli) {
        String lahtoruutu = siirto.substring(0, 2);
        String kohderuutu = siirto.substring(2, 4);
        if (siirto.length() > 4) {
            char korotus = siirto.charAt(4);
            asetaNappulaRuutuun(kohderuutu, puoli, korotus);
            poistaNappulaRuudusta(lahtoruutu, puoli);
            tehdytSiirrot.add(siirto);
            return;
        }
        if (siirto.equals("e1c1") && valkoiset[4][0] == 'k') { // Valkoisen tornitus vasemmalle
            asetaNappulaRuutuun("d1", Side.WHITE, 'r');
            poistaNappulaRuudusta("a1", Side.WHITE);
        }
        if (siirto.equals("e1g1") && valkoiset[4][0] == 'k') { // Valkoisen tornitus oikealle
            asetaNappulaRuutuun("f1", Side.WHITE, 'r');
            poistaNappulaRuudusta("h1", Side.WHITE);
        }
        if (siirto.equals("e8c8") && mustat[4][7] == 'k') { // Mustan tornitus vasemmalle
            asetaNappulaRuutuun("d8", Side.BLACK, 'r');
            poistaNappulaRuudusta("a8", Side.BLACK);
        }
        if (siirto.equals("e8g8") && mustat[4][7] == 'k') { // Mustan tornitus oikealle
            asetaNappulaRuutuun("f8", Side.BLACK, 'r');
            poistaNappulaRuudusta("h8", Side.BLACK);
        }
        asetaNappulaRuutuun(kohderuutu, puoli, getNappulaRuudusta(lahtoruutu));
        poistaNappulaRuudusta(lahtoruutu, puoli);
        tehdytSiirrot.add(siirto);
        paivitaTornitus(lahtoruutu, kohderuutu);
    }

    private void asetaNappulaRuutuun(String ruutu, Side puoli, char nappula) {
        int linjaI = LINJAT.indexOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1)) - 1;
        if (puoli == Side.WHITE) {
            valkoiset[linjaI][rivi] = nappula;
            if (mustat[linjaI][rivi] != ' ') { // Jos vastustajalla on nappula meidän kohderuudussa, se poistetaan
                mustat[linjaI][rivi] = ' ';
            }
        } else {
            mustat[linjaI][rivi] = nappula;
            if (valkoiset[linjaI][rivi] != ' ') { // Jos vastustajalla on nappula meidän kohderuudussa, se poistetaan
                valkoiset[linjaI][rivi] = ' ';
            }
        }
    }

    private void poistaNappulaRuudusta(String ruutu, Side puoli) {
        int linjaI = LINJAT.indexOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1)) - 1;
        if (puoli == Side.WHITE) {
            valkoiset[linjaI][rivi] = ' ';
        } else {
            mustat[linjaI][rivi] = ' ';
        }
    }

    /*
     * Jos tehdyn siirron lähtö- tai kohderuutu on tornin tai kuninkaan ruutu,
     * päivitetään tornituksen status falseksi
     */
    private void paivitaTornitus(String lahtoruutu, String kohderuutu) {
        if (lahtoruutu.equals("e1")) {
            this.valkoisenOikealleTornitus = false;
            this.valkoisenVasemmalleTornitus = false;
        }
        if (lahtoruutu.equals("a1") || kohderuutu.equals("a1")) {
            this.valkoisenVasemmalleTornitus = false;
        }
        if (lahtoruutu.equals("h1") || kohderuutu.equals("h1")) {
            this.valkoisenOikealleTornitus = false;
        }
        if (lahtoruutu.equals("e8")) {
            this.mustanOikealleTornitus = false;
            this.mustanVasemmalleTornitus = false;
        }
        if (lahtoruutu.equals("a8") || kohderuutu.equals("a8")) {
            this.mustanVasemmalleTornitus = false;
        }
        if (lahtoruutu.equals("h8") || kohderuutu.equals("h8")) {
            this.mustanOikealleTornitus = false;
        }
    }

    /*
     * Käydään ehdot läpi, joiden takia ei voisi tornittaa ja jos ne eivät täyty
     * palautetaan true
     */
    private boolean voikoValkoinenTornittaaVasemmalle(ArrayList<String> siirrot) {
        if (!valkoisenVasemmalleTornitus) {
            return false;
        }
        for (int i = 0; i < siirrot.size(); i++) {
            String kohde = siirrot.get(i).substring(2, 4);
            if (kohde.equals("e1") || kohde.equals("d1") || kohde.equals("c1")) {
                return false;
            }
            if (valkoiset[1][0] != ' ' || valkoiset[2][0] != ' ' || valkoiset[3][0] != ' ') {
                return false;
            }
        }
        return true;
    }

    private boolean voikoValkoinenTornittaaOikealle(ArrayList<String> siirrot) {
        if (!valkoisenOikealleTornitus) {
            return false;
        }
        for (int i = 0; i < siirrot.size(); i++) {
            String kohde = siirrot.get(i).substring(2, 4);
            if (kohde.equals("e1") || kohde.equals("f1") || kohde.equals("g1")) {
                return false;
            }
            if (valkoiset[5][0] != ' ' || valkoiset[6][0] != ' ') {
                return false;
            }
        }
        return true;
    }

    private boolean voikoMustaTornittaaVasemmalle(ArrayList<String> siirrot) {
        if (!mustanVasemmalleTornitus) {
            return false;
        }
        for (int i = 0; i < siirrot.size(); i++) {
            String kohde = siirrot.get(i).substring(2, 4);
            if (kohde.equals("e8") || kohde.equals("d8") || kohde.equals("c8")) {
                return false;
            }
            if (mustat[1][7] != ' ' || mustat[2][7] != ' ' || mustat[3][7] != ' ') {
                return false;
            }
        }
        return true;
    }

    private boolean voikoMustaTornittaaOikealle(ArrayList<String> siirrot) {
        if (!mustanOikealleTornitus) {
            return false;
        }
        for (int i = 0; i < siirrot.size(); i++) {
            String kohde = siirrot.get(i).substring(2, 4);
            if (kohde.equals("e8") || kohde.equals("f8") || kohde.equals("g8")) {
                return false;
            }
            if (mustat[5][0] != ' ' || mustat[6][0] != ' ') {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> etsiLaillisetSiirrot(Side puoli) {
        SiirtoGeneraattori sg = new SiirtoGeneraattori(valkoiset, mustat, puoli);
        ArrayList<String> omatSiirrot = new ArrayList<>();
        ArrayList<String> vastustajanSiirrot = new ArrayList<>();
        if (puoli == Side.WHITE) {
            omatSiirrot = sg.poistaLaittomatSiirrot(sg.valkoisenSiirrot());
            vastustajanSiirrot = sg.mustanSiirrot();
        } else {
            omatSiirrot = sg.poistaLaittomatSiirrot(sg.mustanSiirrot());
            vastustajanSiirrot = sg.valkoisenSiirrot();
        }
        if (puoli == Side.WHITE) {
            if (voikoValkoinenTornittaaVasemmalle(vastustajanSiirrot)) {
                omatSiirrot.add("e1c1");
            }
            if (voikoValkoinenTornittaaOikealle(vastustajanSiirrot)) {
                omatSiirrot.add("e1g1");
            }
        } else {
            if (voikoMustaTornittaaVasemmalle(vastustajanSiirrot)) {
                omatSiirrot.add("e8c8");
            }
            if (voikoMustaTornittaaOikealle(vastustajanSiirrot)) {
                omatSiirrot.add("e8g8");
            }
        }
        return omatSiirrot;
    }

    /*
     * Arviointia varten, jotta loppupelissä kuninkaan suotuisat ruudut ovat laudan
     * keskellä eikä reunoissa
     */
    public Boolean getLoppupeli() {
        return (tehdytSiirrot.size() > 49);
    }

    public void setValkoiset(char[][] v) {
        for (int i = 0; i < valkoiset.length; i++) {
            Arrays.fill(valkoiset[i], ' ');
        }

        valkoiset = Arrays.stream(v).map(char[]::clone).toArray(char[][]::new);
    }

    public void setMustat(char[][] m) {
        for (int i = 0; i < mustat.length; i++) {
            Arrays.fill(mustat[i], ' ');
        }

        mustat = Arrays.stream(m).map(char[]::clone).toArray(char[][]::new);
    }

    /*
     * Koska siirron peruminen ei toiminut oikein (luotu metodi löytyy alimmaisena
     * poiskommentoituna) joudutaan luomaan uusi pelilauta ja kopioimaan se jokaista
     * alfa-beta siirtoa varten. Alunperin kun nappuloista pidettiin kirjaa
     * HashMapeissa laudan kopioiminen ja pelilautojen luominen hidasti ohjelmaa
     * huomattavasti, siksi tietorakenteiksi vaihdettiin char-matriisit.
     */
    public Pelilauta kopioiPelilauta() {
        Pelilauta l = new Pelilauta();
        l.setValkoiset(valkoiset);
        l.setMustat(mustat);
        return l;
    }

    public char getNappulaRuudusta(String ruutu) { // Yksikkötestejä varten getteri
        int linjaI = LINJAT.indexOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1)) - 1;
        if (valkoiset[linjaI][rivi] != ' ') {
            return valkoiset[linjaI][rivi];
        }
        if (mustat[linjaI][rivi] != ' ') {
            return mustat[linjaI][rivi];
        }
        return ' ';
    }

    public char[][] getValkoiset() { // Yksikkötestejä varten
        return valkoiset;
    }

    public char[][] getMustat() { // Yksikkötestejä varten
        return mustat;
    }

    public void setLoppupeliTodeksi() { // yksikkötestejä varten
        for (int i = tehdytSiirrot.size(); i <= 50; i++) {
            tehdytSiirrot.add(" ");
        }
    }

    public void pelaaSiirrotListalta(ArrayList<String> s) { // Yksikkötestejä varten
        Side puoli = null;
        for (int i = 0; i < s.size(); i++) {
            String siirto = s.get(i);
            if (i == 0 || (i % 2) == 0) {
                puoli = Side.WHITE;
            } else {
                puoli = Side.BLACK;
            }
            teeSiirto(siirto, puoli);
        }

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
            teeSiirto(s[i], puoli);
        }
    }

    public Boolean getValkoisenVasemmalleTornitus() { // Yksikkötestejä varten
        return valkoisenVasemmalleTornitus;
    }

    public Boolean getValkoisenOikealleTornitus() { // Yksikkötestejä varten
        return valkoisenOikealleTornitus;
    }

    public Boolean getMustanVasemmalleTornitus() { // Yksikkötestejä varten
        return mustanVasemmalleTornitus;
    }

    public Boolean getMustanOikealleTornitus() { // Yksikkötestejä varten
        return mustanOikealleTornitus;
    }

    /*
     * Tämä ei jostain syystä toiminut, mutta mahdollisesti tulevaisuudessa
     * tällaisella metodilla voisi parantaa botin laskentanopeutta, joten jätän
     * tähän poiskommentoituna
     * 
     *
     * public void peruViimeisinSiirto() {
     * Side p;
     * if (tehdytSiirrot.size() % 2 == 0) {
     * p = Side.WHITE;
     * } else {
     * p = Side.BLACK;
     * }
     * String s = tehdytSiirrot.get(tehdytSiirrot.size() - 1);
     * tehdytSiirrot.remove(tehdytSiirrot.size() - 1);
     * String lahto = s.substring(0, 2);
     * String kohde = s.substring(2, 4);
     * int lahtoLinjaI = LINJAT.indexOf(lahto.charAt(0));
     * int lahtoRivi = Character.getNumericValue(lahto.charAt(1)) - 1;
     * int kohdeLinjaI = LINJAT.indexOf(kohde.charAt(0));
     * int kohdeRivi = Character.getNumericValue(kohde.charAt(1)) - 1;
     * if (s.length() == 5) {
     * if (p == Side.WHITE) {
     * valkoiset[lahtoLinjaI][lahtoRivi] = 'p';
     * if (poistettu) {
     * mustat[kohdeLinjaI][kohdeRivi] = poistettuNappula;
     * }
     * valkoiset[kohdeLinjaI][kohdeRivi] = ' ';
     * } else {
     * mustat[lahtoLinjaI][lahtoRivi] = 'p';
     * if (poistettu) {
     * valkoiset[kohdeLinjaI][kohdeRivi] = poistettuNappula;
     * }
     * mustat[kohdeLinjaI][kohdeRivi] = ' ';
     * }
     * } else {
     * if (p == Side.WHITE) {
     * valkoiset[lahtoLinjaI][lahtoRivi] = valkoiset[kohdeLinjaI][kohdeRivi];
     * if (poistettu) {
     * mustat[kohdeLinjaI][kohdeRivi] = poistettuNappula;
     * }
     * valkoiset[kohdeLinjaI][kohdeRivi] = ' ';
     * } else {
     * mustat[lahtoLinjaI][lahtoRivi] = valkoiset[kohdeLinjaI][kohdeRivi];
     * if (poistettu) {
     * valkoiset[kohdeLinjaI][kohdeRivi] = poistettuNappula;
     * }
     * mustat[kohdeLinjaI][kohdeRivi] = ' ';
     * }
     * }
     * }
     */

}
