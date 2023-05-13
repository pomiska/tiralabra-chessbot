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

}
