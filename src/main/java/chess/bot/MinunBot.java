package chess.bot;

import java.util.ArrayList;
import java.util.Random;

import chess.engine.GameState;
import chess.model.Side;
import datastructureproject.*;

public class MinunBot implements ChessBot {
    private Pelilauta lauta;
    private Random random;
    private int syvyys;
    private Arvioija arvioija;

    public MinunBot() {
        this.lauta = new Pelilauta();
        this.random = new Random();
        this.syvyys = 3;
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
            if (siirto.length() == 4) {
                lauta.siirraNappulaRuutuun(siirto.substring(0, 2), siirto.substring(2), vastustaja);
            } else if (siirto.length() == 5) {
                lauta.siirraNappulaRuutuun(siirto.substring(0, 2), siirto.substring(2, 4), vastustaja,
                        siirto.substring(4));
            }
        }
        ArrayList<String> siirrot = this.lauta.etsiLaillisetSiirrot(gamestate.playing);

        int parasArvio = Integer.MIN_VALUE;
        String parasSiirto = "";

        if (siirrot.size() > 0) {
            for (int i = 0; i < siirrot.size(); i++) {
                String siirto = siirrot.get(i);
                Pelilauta lautaKopio = new Pelilauta();
                lautaKopio.setMustat(lauta.getMustat());
                lautaKopio.setValkoiset(lauta.getValkoiset());
                if (siirto.length() == 4) {
                    lautaKopio.siirraNappulaRuutuun(siirto.substring(0, 2), siirto.substring(2, 4), gamestate.playing);
                } else if (siirto.length() == 5) {
                    lautaKopio.siirraNappulaRuutuun(siirto.substring(0, 2), siirto.substring(2, 4), gamestate.playing,
                            siirto.substring(4));
                }
                int arvio = minimax(lautaKopio, this.syvyys, vastustaja);
                if (arvio > parasArvio) {
                    parasSiirto = siirto;
                    parasArvio = arvio;
                }
            }
            if (parasSiirto.length() == 4) {
                lauta.siirraNappulaRuutuun(parasSiirto.substring(0, 2), parasSiirto.substring(2), gamestate.playing);
            } else if (parasSiirto.length() == 5) {
                lauta.siirraNappulaRuutuun(parasSiirto.substring(0, 2), parasSiirto.substring(2, 4), gamestate.playing,
                        parasSiirto.substring(4));
            }
            return parasSiirto;
        } else {
            System.out.println("TYHJÄ LISTA");
            System.out.println("TYHJÄ LISTA");
            System.out.println("TYHJÄ LISTA");
            System.out.println("TYHJÄ LISTA");
            return null;
        }
    }

    public int minimax(Pelilauta lauta, int syvyys, Side puoli) {
        ArrayList<String> siirrot = lauta.etsiLaillisetSiirrot(puoli);
        if (puoli == Side.WHITE) {
            puoli = Side.BLACK;
        } else {
            puoli = Side.WHITE;
        }

        if (syvyys == 0) {
            return arvioija.arvioiPelilauta(lauta);
        }
        int arvio = Integer.MIN_VALUE;

        for (int i = 0; i < siirrot.size(); i++) {
            Pelilauta lautaKopio = new Pelilauta();
            lautaKopio.setMustat(lauta.getMustat());
            lautaKopio.setValkoiset(lauta.getValkoiset());
            lautaKopio.pelaaSiirrot(siirrot.get(i));
            arvio = Math.max(arvio, minimax(lautaKopio, syvyys - 1, puoli));
        }

        return arvio;
    }

}
