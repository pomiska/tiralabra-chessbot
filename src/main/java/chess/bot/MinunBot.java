package chess.bot;

import java.util.ArrayList;
import java.util.Random;

import chess.engine.GameState;
import chess.model.Side;
import datastructureproject.Pelilauta;

public class MinunBot implements ChessBot {
    private Pelilauta lauta;
    private ArrayList<String> siirrot;
    private Random random;

    public MinunBot() {
        this.lauta = new Pelilauta();
        this.random = new Random();
        this.siirrot = new ArrayList<>();

    }

    public String nextMove(GameState gamestate) {
        Side vastustaja = null;
        if (gamestate.playing == Side.WHITE) {
            vastustaja = Side.BLACK;
        } else {
            vastustaja = Side.WHITE;
        }
        if (gamestate.getMoveCount() > 0) {
            lauta.siirraNappulaRuutuun(gamestate.getLatestMove().substring(0, 2),
                    gamestate.getLatestMove().substring(2), vastustaja);
        }
        siirrot.clear();
        siirrot = this.lauta.etsiLaillisetSiirrot(gamestate.playing);
        try {
            if (siirrot.size() > 0) {
                String siirto = siirrot.get(random.nextInt(siirrot.size()));
                lauta.siirraNappulaRuutuun(siirto.substring(0, 2), siirto.substring(2), gamestate.playing);
                System.out.println("lahto: " + siirto.substring(0, 2));
                System.out.println("kohde: " + siirto.substring(2));
                System.out.println("playing: " + gamestate.playing);
                return siirto;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("ERROR MAKING MOVE: " + e.getMessage());
        }
        return nextMove(gamestate);
    }

}
