package chess.bot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import chess.engine.GameState;
import chess.model.Side;
import datastructureproject.*;

public class MinunBot implements ChessBot {
    private Pelilauta lauta;
    private final int syvyys = 4;
    private Arvioija arvioija;

    public MinunBot() {
        this.lauta = new Pelilauta();
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
            this.lauta.teeSiirto(siirto, vastustaja);
        }
        ArrayList<String> siirrot = this.lauta.etsiLaillisetSiirrot(gamestate.playing);
        // ArrayList<String> jsiirrot = jarjestaSiirrot(lauta, siirrot,
        // gamestate.playing, this.syvyys - 1);
        //
        if (siirrot.size() > 0) {
            String parasSiirto = siirrot.get(0);
            int parasArvio = Integer.MIN_VALUE;
            for (int i = 0; i < siirrot.size(); i++) {
                String siirto = siirrot.get(i);
                Pelilauta l = lauta.kopioiPelilauta();
                l.teeSiirto(siirto, gamestate.playing);
                int arvio = alfabeta(l, this.syvyys, vastustaja, Integer.MIN_VALUE,
                        Integer.MAX_VALUE);
                l = null;
                if (arvio > parasArvio) {
                    parasSiirto = siirto;
                    parasArvio = arvio;
                }
            }
            this.lauta.teeSiirto(parasSiirto, gamestate.playing);
            return parasSiirto;

        } else {
            System.out.println("TYHJÄ LISTA");
            return null;
        }
    }

    public int alfabeta(Pelilauta l, int syvyys, Side puoli, int alfa, int beta) {
        if (puoli == Side.WHITE) {
            if (syvyys == 0) {
                return arvioija.arvioiPelilauta(l);
            }
            ArrayList<String> siirrot = l.etsiLaillisetSiirrot(puoli);
            int arvio = Integer.MIN_VALUE;
            if (siirrot.isEmpty()) {
                return arvio;
            }
            for (int i = 0; i < siirrot.size(); i++) {
                String siirto = siirrot.get(i);
                Pelilauta pl = l.kopioiPelilauta();
                pl.teeSiirto(siirto, puoli);
                arvio = Math.max(arvio, alfabeta(pl, syvyys - 1, Side.BLACK, alfa, beta));
                pl = null;
                alfa = Math.max(alfa, arvio);
                if (arvio >= beta) {
                    break;
                }
            }
            return arvio;
        } else {
            if (syvyys == 0) {
                return arvioija.arvioiPelilauta(l);
            }
            ArrayList<String> siirrot = l.etsiLaillisetSiirrot(puoli);
            int arvio = Integer.MAX_VALUE;
            if (siirrot.isEmpty()) {
                return arvio;
            }
            for (int i = 0; i < siirrot.size(); i++) {
                String siirto = siirrot.get(i);
                Pelilauta pl = l.kopioiPelilauta();
                pl.teeSiirto(siirto, puoli);
                arvio = Math.min(arvio, alfabeta(pl, syvyys - 1, Side.WHITE, alfa, beta));
                pl = null;
                beta = Math.min(beta, arvio);
                if (arvio <= alfa) {
                    break;
                }
            }
            return arvio;
        }
    }

    private ArrayList<String> jarjestaSiirrot(Pelilauta l, ArrayList<String> siirrot, Side vuoro, int syvyys) {
        ArrayList<Integer> arvotJ = new ArrayList<>();
        HashMap<String, Integer> arvot = new HashMap<>();
        ArrayList<String> jarjestetyt = new ArrayList<>();
        Side vastustaja = null;
        if (vuoro == Side.WHITE) {
            vastustaja = Side.BLACK;
        } else {
            vastustaja = Side.WHITE;
        }
        for (int i = 0; i < siirrot.size(); i++) {
            String siirto = siirrot.get(i);
            Pelilauta pl = l.kopioiPelilauta();
            pl.teeSiirto(siirto, vuoro);
            int arvio = alfabeta(pl, syvyys, vastustaja, Integer.MIN_VALUE,
                    Integer.MAX_VALUE);
            pl = null;
            arvot.put(siirto, arvio);
        }
        for (HashMap.Entry<String, Integer> entry : arvot.entrySet()) {
            arvotJ.add(entry.getValue());
        }
        Collections.sort(arvotJ);
        Collections.reverse(arvotJ);

        for (int arvo : arvotJ) {
            for (HashMap.Entry<String, Integer> entry : arvot.entrySet()) {
                if (entry.getValue().equals(arvo)) {
                    jarjestetyt.add(entry.getKey());
                }
            }
        }
        return jarjestetyt;
    }

}
