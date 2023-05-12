package datastructureproject;

import chess.model.Side;
import static org.junit.Assert.assertEquals;
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

}
