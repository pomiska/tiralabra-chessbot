package datastructureproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import chess.model.Side;

// Lähteenä käytetty https://www.chessprogramming.org/Simplified_Evaluation_Function

public class Arvioija {

    private HashMap<NappulaTyyppi, Integer> pisteytys;
    private ArrayList<String> linjat;

    public final int[][] sotilaanRuudut = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 5, 10, 10, -20, -20, 10, 10, 5 },
            { 5, -5, -10, 0, 0, -10, -5, 5 },
            { 0, 0, 0, 20, 20, 0, 0, 0 },
            { 5, 5, 10, 25, 25, 10, 5, 5 },
            { 10, 10, 20, 30, 30, 20, 10, 10 },
            { 50, 50, 50, 50, 50, 50, 50, 50 },
            { 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    public final int[][] ratsunRuudut = {
            { -50, -40, -30, -30, -30, -30, -40, -50 },
            { -40, -20, 0, 5, 5, 0, -20, -40 },
            { -30, 5, 10, 15, 15, 10, 5, -30 },
            { -30, 0, 15, 20, 20, 15, 0, -30 },
            { -30, 5, 15, 20, 20, 15, 5, -30 },
            { -30, 0, 10, 15, 15, 10, 0, -30 },
            { -40, -20, 0, 0, 0, 0, -20, -40 },
            { -50, -40, -30, -30, -30, -30, -40, -50 }
    };

    public final int[][] lahetinRuudut = {
            { -20, -10, -10, -10, -10, -10, -10, -20 },
            { -10, 5, 0, 0, 0, 0, 5, -10 },
            { -10, 10, 10, 10, 10, 10, 10, -10 },
            { -10, 0, 10, 10, 10, 10, 0, -10 },
            { -10, 5, 5, 10, 10, 5, 5, -10 },
            { -10, 0, 5, 10, 10, 5, 0, -10 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -20, -10, -10, -10, -10, -10, -10, -20 }
    };

    public final int[][] torninRuudut = {
            { 0, 0, 0, 5, 5, 0, 0, 0 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { 5, 10, 10, 10, 10, 10, 10, 5 },
            { 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    public final int[][] kuningattarenRuudut = {
            { -20, -10, -10, -5, -5, -10, -10, -20 },
            { -10, 0, 5, 0, 0, 0, 0, -10 },
            { -10, 5, 5, 5, 5, 5, 0, -10 },
            { 0, 0, 5, 5, 5, 5, 0, -5 },
            { -5, 0, 5, 5, 5, 5, 0, -5 },
            { -10, 0, 5, 5, 5, 5, 0, -10 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -20, -10, -10, -5, -5, -10, -10, -20 }
    };

    public final int[][] kuninkaanRuudutAlkupeli = {
            { 20, 30, 10, 0, 0, 10, 30, 20 },
            { 20, 20, 0, 0, 0, 0, 20, 20 },
            { -10, -20, -20, -20, -20, -20, -20, -10 },
            { -20, -30, -30, -40, -40, -30, -30, -20 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 }
    };

    public final int[][] kuninkaanRuudutLoppupeli = {
            { -50, -30, -30, -30, -30, -30, -30, -50 },
            { -30, -30, 0, 0, 0, 0, -30, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -20, -10, 0, 0, -10, -20, -30 },
            { -50, -40, -30, -20, -20, -30, -40, -50 }
    };

    public Arvioija() {
        this.pisteytys = new HashMap<>();

        pisteytys.put(NappulaTyyppi.SOTILAS, 100);
        pisteytys.put(NappulaTyyppi.TORNI, 500);
        pisteytys.put(NappulaTyyppi.KUNINGATAR, 900);
        pisteytys.put(NappulaTyyppi.RATSU, 320);
        pisteytys.put(NappulaTyyppi.LAHETTI, 330);
        pisteytys.put(NappulaTyyppi.KUNINGAS, 20000);

        this.linjat = new ArrayList<>(); // Helpottaa pisteytyksen toteuttamista
        this.linjat.add("a");
        this.linjat.add("b");
        this.linjat.add("c");
        this.linjat.add("d");
        this.linjat.add("e");
        this.linjat.add("f");
        this.linjat.add("g");
        this.linjat.add("h");
    }

    private int[][] peilaaTaulukko(int[][] t) { // Mustien arviointia varten
        int[][] peilattu = new int[8][8];
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < t[0].length; j++) {
                peilattu[i][j] = t[7 - i][j] * -1;
            }
        }
        return peilattu;
    }

    private int arvioiSijaintienMukaan(String ruutu, NappulaTyyppi nappula, Side puoli) {
        int linjaI = this.linjat.indexOf(ruutu.substring(0, 1));
        int riviI = Integer.valueOf(ruutu.substring(1, 2)) - 1;
        int[][] t;

        if (puoli == Side.WHITE) {
            switch (nappula) {
                case SOTILAS:
                    return sotilaanRuudut[linjaI][riviI];
                case RATSU:
                    return ratsunRuudut[linjaI][riviI];
                case LAHETTI:
                    return lahetinRuudut[linjaI][riviI];
                case TORNI:
                    return torninRuudut[linjaI][riviI];
                case KUNINGATAR:
                    return kuningattarenRuudut[linjaI][riviI];
                case KUNINGAS:
                    return kuninkaanRuudutAlkupeli[linjaI][riviI];
                default:
                    return 0;
            }
        } else {
            switch (nappula) {
                case SOTILAS:
                    t = peilaaTaulukko(sotilaanRuudut);
                    return t[linjaI][riviI];
                case RATSU:
                    t = peilaaTaulukko(ratsunRuudut);
                    return t[linjaI][riviI];
                case LAHETTI:
                    t = peilaaTaulukko(lahetinRuudut);
                    return t[linjaI][riviI];
                case TORNI:
                    t = peilaaTaulukko(torninRuudut);
                    return t[linjaI][riviI];
                case KUNINGATAR:
                    t = peilaaTaulukko(kuningattarenRuudut);
                    return t[linjaI][riviI];
                case KUNINGAS:
                    t = peilaaTaulukko(kuninkaanRuudutAlkupeli);
                    return t[linjaI][riviI];
                default:
                    return 0;
            }
        }
    }

    public int arvioiPelilauta(Pelilauta lauta) { // Toistaiseksi vain valkoiselle toimii
        Map<String, NappulaTyyppi> valkoiset = lauta.getValkoiset();
        Map<String, NappulaTyyppi> mustat = lauta.getMustat();
        int pisteet = 0;
        for (HashMap.Entry<String, NappulaTyyppi> set : valkoiset.entrySet()) {
            pisteet += pisteytys.get(set.getValue());
            pisteet += arvioiSijaintienMukaan(set.getKey(), set.getValue(), Side.WHITE);
        }

        for (HashMap.Entry<String, NappulaTyyppi> set : mustat.entrySet()) {
            pisteet -= pisteytys.get(set.getValue());
            pisteet -= arvioiSijaintienMukaan(set.getKey(), set.getValue(), Side.BLACK);
        }

        return pisteet;
    }
}
