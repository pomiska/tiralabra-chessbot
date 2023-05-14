package chess.bot;

import static org.junit.Assert.assertTrue;

import org.junit.*;

import chess.engine.GameState;

public class MinunBotTest {

    MinunBot bot;
    GameState gs;

    public MinunBotTest() {
    }

    @Before
    public void setUp() {
        this.bot = new MinunBot();
        this.gs = new GameState();
    }

    @After
    public void tearDown() {
        this.bot = null;
        this.gs = null;
    }

    @Test
    public void palauttaaSiirron() {
        assertTrue(this.bot.nextMove(gs).length() > 0);
    }

}
