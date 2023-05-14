package datastructureproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.*;

import chess.model.Side;

public class ArvioijaTest {

    Arvioija arvioija;
    Pelilauta lauta;

    public ArvioijaTest() {
    }

    @Before
    public void setUp() {
        this.arvioija = new Arvioija();
        this.lauta = new Pelilauta();
    }

    @After
    public void tearDown() {
        this.arvioija = null;
        this.lauta = null;
    }

    @Test
    public void alussaSamaPisteytys() {
        assertEquals(-5, this.arvioija.arvioiPelilauta(this.lauta, Side.WHITE));
        assertEquals(5, this.arvioija.arvioiPelilauta(this.lauta, Side.BLACK));
    }

    @Test
    public void valkoinenEdella() {
        this.lauta.pelaaSiirrot("b1c3");
        assertTrue((this.arvioija.arvioiPelilauta(this.lauta, Side.WHITE) > this.arvioija.arvioiPelilauta(this.lauta,
                Side.BLACK)));
    }

    @Test
    public void mustaEdella() {
        char[][] mustat = new char[8][8];
        char[][] valkoiset = new char[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                mustat[i][j] = ' ';
                valkoiset[i][j] = ' ';
            }
        }

        mustat[3][7] = 'k';
        mustat[3][5] = 'q';

        valkoiset[3][0] = 'k';
        valkoiset[3][1] = 'r';

        this.lauta.setMustat(mustat);
        this.lauta.setValkoiset(valkoiset);

        this.lauta.setLoppupeliTodeksi();

        assertTrue(this.arvioija.arvioiPelilauta(this.lauta, Side.BLACK) > this.arvioija.arvioiPelilauta(this.lauta,
                Side.WHITE));
    }

}
