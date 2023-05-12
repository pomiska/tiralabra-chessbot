package datastructureproject;

import java.util.ArrayList;
import java.util.Arrays;

import chess.model.Side;

public class Pelilauta {
    private char[][] valkoiset; // tallennetaan tieto nappuloista tyylillä [linjan indeksi][rivin indeksi] =
                                // nappulan tyyppi
    private char[][] mustat;
    private ArrayList<Character> linjat;
    private ArrayList<String> tehdytSiirrot;
    private Boolean valkoisenOikealleTornitus; // pidetään kirjaa voiko tornittaa
    private Boolean valkoisenVasemmalleTornitus;
    private Boolean mustanOikealleTornitus;
    private Boolean mustanVasemmalleTornitus;
    private char poistettuNappula;
    private Boolean poistettu;

    public Pelilauta() {
        this.valkoiset = new char[8][8];
        this.mustat = new char[8][8];
        this.tehdytSiirrot = new ArrayList<>();
        this.valkoisenOikealleTornitus = true;
        this.valkoisenVasemmalleTornitus = true;
        this.mustanOikealleTornitus = true;
        this.mustanVasemmalleTornitus = true;

        this.linjat = new ArrayList<>();
        this.linjat.add('a');
        this.linjat.add('b');
        this.linjat.add('c');
        this.linjat.add('d');
        this.linjat.add('e');
        this.linjat.add('f');
        this.linjat.add('g');
        this.linjat.add('h');

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
        asetaNappulaRuutuun(kohderuutu, puoli, getNappulaRuudusta(lahtoruutu));
        poistaNappulaRuudusta(lahtoruutu, puoli);
        tehdytSiirrot.add(siirto);
        paivitaTornitus(lahtoruutu, kohderuutu);
    }

    private void asetaNappulaRuutuun(String ruutu, Side puoli, char nappula) {
        poistettu = false;
        int linjaI = linjat.indexOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1)) - 1;
        if (puoli == Side.WHITE) {
            valkoiset[linjaI][rivi] = nappula;
            if (mustat[linjaI][rivi] != ' ') { // Jos vastustajalla on nappula meidän kohderuudussa, se poistetaan
                poistettuNappula = mustat[linjaI][rivi];
                poistettu = true;
                mustat[linjaI][rivi] = ' ';
            }
        } else if (puoli == Side.BLACK) {
            mustat[linjaI][rivi] = nappula;
            if (valkoiset[linjaI][rivi] != ' ') { // Jos vastustajalla on nappula meidän kohderuudussa, se poistetaan
                poistettuNappula = valkoiset[linjaI][rivi];
                poistettu = true;
                valkoiset[linjaI][rivi] = ' ';
            }
        }
    }

    private void poistaNappulaRuudusta(String ruutu, Side puoli) {
        int linjaI = linjat.indexOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1)) - 1;
        if (puoli == Side.WHITE) {
            valkoiset[linjaI][rivi] = ' ';
        } else {
            mustat[linjaI][rivi] = ' ';
        }
    }

    public void peruViimeisinSiirto() {
        Side p;
        if (tehdytSiirrot.size() % 2 == 0) {
            p = Side.WHITE;
        } else {
            p = Side.BLACK;
        }
        String s = tehdytSiirrot.get(tehdytSiirrot.size() - 1);
        tehdytSiirrot.remove(tehdytSiirrot.size() - 1);
        String lahto = s.substring(0, 2);
        String kohde = s.substring(2, 4);
        int lahtoLinjaI = linjat.indexOf(lahto.charAt(0));
        int lahtoRivi = Character.getNumericValue(lahto.charAt(1)) - 1;
        int kohdeLinjaI = linjat.indexOf(kohde.charAt(0));
        int kohdeRivi = Character.getNumericValue(kohde.charAt(1)) - 1;
        if (s.length() == 5) {
            if (p == Side.WHITE) {
                valkoiset[lahtoLinjaI][lahtoRivi] = 'p';
                if (poistettu) {
                    mustat[kohdeLinjaI][kohdeRivi] = poistettuNappula;
                }
                valkoiset[kohdeLinjaI][kohdeRivi] = ' ';
            } else {
                mustat[lahtoLinjaI][lahtoRivi] = 'p';
                if (poistettu) {
                    valkoiset[kohdeLinjaI][kohdeRivi] = poistettuNappula;
                }
                mustat[kohdeLinjaI][kohdeRivi] = ' ';
            }
        } else {
            if (p == Side.WHITE) {
                valkoiset[lahtoLinjaI][lahtoRivi] = valkoiset[kohdeLinjaI][kohdeRivi];
                if (poistettu) {
                    mustat[kohdeLinjaI][kohdeRivi] = poistettuNappula;
                }
                valkoiset[kohdeLinjaI][kohdeRivi] = ' ';
            } else {
                mustat[lahtoLinjaI][lahtoRivi] = valkoiset[kohdeLinjaI][kohdeRivi];
                if (poistettu) {
                    valkoiset[kohdeLinjaI][kohdeRivi] = poistettuNappula;
                }
                mustat[kohdeLinjaI][kohdeRivi] = ' ';
            }
        }
    }

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

    public char getNappulaRuudusta(String ruutu) { // Yksikkötestejä varten getteri
        int linjaI = linjat.indexOf(ruutu.charAt(0));
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

    public void pelaaSiirrotListalta(ArrayList<String> s) {
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

    public Pelilauta kopioiPelilauta() {
        Pelilauta l = new Pelilauta();
        l.setValkoiset(valkoiset);
        l.setMustat(mustat);
        return l;
    }
}
