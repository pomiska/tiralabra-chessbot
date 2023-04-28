package chess.bot;

import java.util.ArrayList;
import java.util.Random;

import chess.engine.GameState;
import chess.model.Side;
import datastructureproject.Pelilauta;

public class MinunBot implements ChessBot {
    private Pelilauta lauta;
    private Random random;

    public MinunBot() {
        this.lauta = new Pelilauta();
        this.random = new Random();
    }

    public String nextMove(GameState gamestate) {
        Side vastustaja = null;
        if (gamestate.playing == Side.WHITE) {
            vastustaja = Side.BLACK;
        } else {
            vastustaja = Side.WHITE;
        }
        if (gamestate.getMoveCount() > 0) { // Haetaan vastustajan viimeksi tekemä siirto ennen oman siirron tekemistä
            String siirto = gamestate.getLatestMove();
            if (siirto.length() == 4) {
                lauta.siirraNappulaRuutuun(siirto.substring(0, 2), siirto.substring(2), vastustaja);
            } else if (siirto.length() == 5) {
                lauta.siirraNappulaRuutuun(siirto.substring(0, 2), siirto.substring(2, 4), vastustaja,
                        siirto.substring(4));
            }
        }
        ArrayList<String> siirrot = new ArrayList<>();
        siirrot = this.lauta.etsiLaillisetSiirrot(gamestate.playing);
        try {
            if (siirrot.size() > 0) {
                String siirto = siirrot.get(random.nextInt(siirrot.size()));
                if (siirto.length() == 4) {
                    lauta.siirraNappulaRuutuun(siirto.substring(0, 2), siirto.substring(2), gamestate.playing);
                } else if (siirto.length() == 5) {
                    lauta.siirraNappulaRuutuun(siirto.substring(0, 2), siirto.substring(2, 4), gamestate.playing, "q");
                }
                System.out.println("lahto: " + siirto.substring(0, 2));
                System.out.println("kohde: " + siirto.substring(2));
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
