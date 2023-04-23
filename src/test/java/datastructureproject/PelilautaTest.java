package datastructureproject;

import chess.model.Side;
import static org.junit.Assert.assertEquals;
import org.junit.*;

public class PelilautaTest {

    Pelilauta lauta;

    public PelilautaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
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
        assertEquals(this.lauta.getNappulaRuudusta("a1"), NappulaTyyppi.TORNI);
        assertEquals(this.lauta.getNappulaRuudusta("b1"), NappulaTyyppi.RATSU);
        assertEquals(this.lauta.getNappulaRuudusta("c1"), NappulaTyyppi.LAHETTI);
        assertEquals(this.lauta.getNappulaRuudusta("d1"), NappulaTyyppi.KUNINGATAR);
        assertEquals(this.lauta.getNappulaRuudusta("e1"), NappulaTyyppi.KUNINGAS);
        assertEquals(this.lauta.getNappulaRuudusta("f1"), NappulaTyyppi.LAHETTI);
        assertEquals(this.lauta.getNappulaRuudusta("g1"), NappulaTyyppi.RATSU);
        assertEquals(this.lauta.getNappulaRuudusta("h1"), NappulaTyyppi.TORNI);

        assertEquals(this.lauta.getNappulaRuudusta("a2"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("b2"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("c2"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("d2"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("e2"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("f2"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("g2"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("h2"), NappulaTyyppi.SOTILAS);

        assertEquals(this.lauta.getNappulaRuudusta("a8"), NappulaTyyppi.TORNI);
        assertEquals(this.lauta.getNappulaRuudusta("b8"), NappulaTyyppi.RATSU);
        assertEquals(this.lauta.getNappulaRuudusta("c8"), NappulaTyyppi.LAHETTI);
        assertEquals(this.lauta.getNappulaRuudusta("d8"), NappulaTyyppi.KUNINGATAR);
        assertEquals(this.lauta.getNappulaRuudusta("e8"), NappulaTyyppi.KUNINGAS);
        assertEquals(this.lauta.getNappulaRuudusta("f8"), NappulaTyyppi.LAHETTI);
        assertEquals(this.lauta.getNappulaRuudusta("g8"), NappulaTyyppi.RATSU);
        assertEquals(this.lauta.getNappulaRuudusta("h8"), NappulaTyyppi.TORNI);

        assertEquals(this.lauta.getNappulaRuudusta("a7"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("b7"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("c7"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("d7"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("e7"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("f7"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("g7"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("h7"), NappulaTyyppi.SOTILAS);
    }

    @Test
    public void siirtaminenToimiiOikein() {
        assertEquals(this.lauta.getNappulaRuudusta("a2"), NappulaTyyppi.SOTILAS);
        this.lauta.siirraNappulaRuutuun("a2", "a4", Side.WHITE);
        assertEquals(this.lauta.getNappulaRuudusta("a4"), NappulaTyyppi.SOTILAS);
        assertEquals(this.lauta.getNappulaRuudusta("a2"), null);
    }

}
