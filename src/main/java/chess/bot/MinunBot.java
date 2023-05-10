package chess.bot;

import java.util.ArrayList;

import chess.engine.GameState;
import chess.model.Side;
import datastructureproject.*;

public class MinunBot implements ChessBot {
    private Pelilauta lauta;
    private int syvyys;
    private Arvioija arvioija;

    public MinunBot() {
        this.lauta = new Pelilauta();
        this.syvyys = 4;
        this.arvioija = new Arvioija();
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
            this.lauta.siirraNappulaRuutuun(siirto, vastustaja);
        }
        ArrayList<String> siirrot = new ArrayList<>();
        siirrot = this.lauta.etsiLaillisetSiirrot(gamestate.playing);

        if (siirrot.size() > 0) {
            String parasSiirto = siirrot.get(0);
            int parasArvio = Integer.MIN_VALUE;
            for (int i = 0; i < siirrot.size(); i++) {
                String siirto = siirrot.get(i);
                Pelilauta l = this.lauta.kopioiPelilauta();
                l.siirraNappulaRuutuun(siirto, gamestate.playing);
                int arvio = alfabeta(l, this.syvyys, vastustaja, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (arvio > parasArvio) {
                    parasSiirto = siirto;
                    parasArvio = arvio;
                }
            }
            this.lauta.siirraNappulaRuutuun(parasSiirto, gamestate.playing);
            return parasSiirto;

        } else {
            System.out.println("TYHJÄ LISTA");
            return null;
        }
    }

    public int alfabeta(Pelilauta pl, int syvyys, Side puoli, int alfa, int beta) {
        if (puoli == Side.WHITE) {
            if (syvyys == 0) {
                int x = arvioija.arvioiPelilauta(pl);
                return x;
            }
            ArrayList<String> siirrot = new ArrayList<>();
            siirrot = pl.etsiLaillisetSiirrot(puoli);
            int arvio = Integer.MIN_VALUE;
            if (siirrot.isEmpty()) {
                return arvio;
            }
            for (int i = 0; i < siirrot.size(); i++) {
                String siirto = siirrot.get(i);
                Pelilauta l = pl.kopioiPelilauta();
                l.siirraNappulaRuutuun(siirto, puoli);
                arvio = Math.max(arvio, alfabeta(l, syvyys - 1, Side.BLACK, alfa, beta));
                alfa = Math.max(alfa, arvio);
                if (beta <= alfa) {
                    break;
                }
            }
            return arvio;
        } else {
            if (syvyys == 0) {
                int x = arvioija.arvioiPelilauta(pl);
                return x;
            }
            ArrayList<String> siirrot = new ArrayList<>();
            siirrot = pl.etsiLaillisetSiirrot(puoli);
            int arvio = Integer.MAX_VALUE;
            if (siirrot.isEmpty()) {
                return arvio;
            }
            for (int i = 0; i < siirrot.size(); i++) {
                String siirto = siirrot.get(i);
                Pelilauta l = pl.kopioiPelilauta();
                l.siirraNappulaRuutuun(siirto, puoli);
                arvio = Math.min(arvio, alfabeta(l, syvyys - 1, Side.BLACK, alfa, beta));
                beta = Math.min(beta, arvio);
                if (beta <= alfa) {
                    break;
                }
            }
            return arvio;
        }
    }

}
