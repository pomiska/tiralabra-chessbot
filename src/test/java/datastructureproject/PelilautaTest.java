package datastructureproject;

import chess.model.Side;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.*;

public class PelilautaTest { // Test

    Pelilauta lauta;

    public PelilautaTest() {
    }

    @Before
    public void setUp() {
        this.lauta = new Pelilauta();
    }

    @After
    public void tearDown() {
        this.lauta = null;
    }

    @Test
    public void laudanLuominenLuoOikeatNappulat() {
        assertEquals(this.lauta.getNappulaRuudusta("a1"), 'r');
        assertEquals(this.lauta.getNappulaRuudusta("b1"), 'n');
        assertEquals(this.lauta.getNappulaRuudusta("c1"), 'b');
        assertEquals(this.lauta.getNappulaRuudusta("d1"), 'q');
        assertEquals(this.lauta.getNappulaRuudusta("e1"), 'k');
        assertEquals(this.lauta.getNappulaRuudusta("f1"), 'b');
        assertEquals(this.lauta.getNappulaRuudusta("g1"), 'n');
        assertEquals(this.lauta.getNappulaRuudusta("h1"), 'r');

        assertEquals(this.lauta.getNappulaRuudusta("a2"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("b2"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("c2"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("d2"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("e2"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("f2"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("g2"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("h2"), 'p');

        assertEquals(this.lauta.getNappulaRuudusta("a8"), 'r');
        assertEquals(this.lauta.getNappulaRuudusta("b8"), 'n');
        assertEquals(this.lauta.getNappulaRuudusta("c8"), 'b');
        assertEquals(this.lauta.getNappulaRuudusta("d8"), 'q');
        assertEquals(this.lauta.getNappulaRuudusta("e8"), 'k');
        assertEquals(this.lauta.getNappulaRuudusta("f8"), 'b');
        assertEquals(this.lauta.getNappulaRuudusta("g8"), 'n');
        assertEquals(this.lauta.getNappulaRuudusta("h8"), 'r');

        assertEquals(this.lauta.getNappulaRuudusta("a7"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("b7"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("c7"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("d7"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("e7"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("f7"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("g7"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("h7"), 'p');
    }

    @Test
    public void siirtaminenToimiiOikein() {
        assertEquals(this.lauta.getNappulaRuudusta("e2"), 'p');
        this.lauta.teeSiirto("e2e4", Side.WHITE);
        assertEquals(this.lauta.getNappulaRuudusta("e4"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("e2"), ' ');

        assertEquals(this.lauta.getNappulaRuudusta("d7"), 'p');
        this.lauta.teeSiirto("d7d5", Side.BLACK);
        assertEquals(this.lauta.getNappulaRuudusta("d5"), 'p');
        assertEquals(this.lauta.getNappulaRuudusta("d7"), ' ');

        assertEquals(this.lauta.getNappulaRuudusta("e4"), 'p');
        this.lauta.teeSiirto("e4d5", Side.WHITE);
        assertEquals(this.lauta.getNappulaRuudusta("d5"), 'p');
        this.lauta.teeSiirto("e4d5", Side.WHITE);
        assertEquals(this.lauta.getNappulaRuudusta("e4"), ' ');
    }

    @Test
    public void tornittaminenSiirtaaOikeatNappulat() {
        char[][] valkoiset = new char[8][8];
        char[][] mustat = new char[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mustat[i][j] = ' ';
                valkoiset[i][j] = ' ';
            }
        }

        mustat[4][7] = 'k';
        mustat[0][7] = 'r';

        valkoiset[4][0] = 'k';
        valkoiset[7][0] = 'r';

        this.lauta.setMustat(mustat);
        this.lauta.setValkoiset(valkoiset);

        this.lauta.pelaaSiirrot("e1g1 e8c8");

        assertEquals(this.lauta.getNappulaRuudusta("g1"), 'k');
        assertEquals(this.lauta.getNappulaRuudusta("c8"), 'k');
        assertEquals(this.lauta.getNappulaRuudusta("f1"), 'r');
        assertEquals(this.lauta.getNappulaRuudusta("d8"), 'r');

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mustat[i][j] = ' ';
                valkoiset[i][j] = ' ';
            }
        }

        mustat[4][7] = 'k';
        mustat[7][7] = 'r';

        valkoiset[4][0] = 'k';
        valkoiset[0][0] = 'r';

        this.lauta.setMustat(mustat);
        this.lauta.setValkoiset(valkoiset);

        this.lauta.pelaaSiirrot("e1c1 e8g8");

        assertEquals(this.lauta.getNappulaRuudusta("c1"), 'k');
        assertEquals(this.lauta.getNappulaRuudusta("g8"), 'k');
        assertEquals(this.lauta.getNappulaRuudusta("d1"), 'r');
        assertEquals(this.lauta.getNappulaRuudusta("f8"), 'r');
        assertFalse(this.lauta.getValkoisenOikealleTornitus());
        assertFalse(this.lauta.getValkoisenVasemmalleTornitus());
        assertFalse(this.lauta.getMustanOikealleTornitus());
        assertFalse(this.lauta.getMustanVasemmalleTornitus());
    }

    @Test
    public void nappulanKorottaminenToimii() {
        char[][] valkoiset = new char[8][8];
        char[][] mustat = new char[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mustat[i][j] = ' ';
                valkoiset[i][j] = ' ';
            }
        }

        valkoiset[0][6] = 'p';
        mustat[0][1] = 'p';

        this.lauta.setMustat(mustat);
        this.lauta.setValkoiset(valkoiset);

        this.lauta.pelaaSiirrot("a7a8q a2a1n");

        assertEquals(this.lauta.getNappulaRuudusta("a1"), 'n');
        assertEquals(this.lauta.getNappulaRuudusta("a8"), 'q');
    }

}
