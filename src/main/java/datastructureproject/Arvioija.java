package datastructureproject;

import java.util.ArrayList;
import java.util.HashMap;

import chess.model.Side;

// Lähteenä käytetty https://www.chessprogramming.org/Simplified_Evaluation_Function

public class Arvioija {

    private static final HashMap<NappulaTyyppi, Integer> PISTEYTYS;
    static {
        PISTEYTYS = new HashMap<>();
        PISTEYTYS.put(NappulaTyyppi.SOTILAS, 100);
        PISTEYTYS.put(NappulaTyyppi.TORNI, 500);
        PISTEYTYS.put(NappulaTyyppi.KUNINGATAR, 900);
        PISTEYTYS.put(NappulaTyyppi.RATSU, 320);
        PISTEYTYS.put(NappulaTyyppi.LAHETTI, 330);
        PISTEYTYS.put(NappulaTyyppi.KUNINGAS, 5000);
    }

    private static final ArrayList<String> LINJAT;
    static {
        LINJAT = new ArrayList<>(); // Helpottaa pisteytyksen toteuttamista
        LINJAT.add("a");
        LINJAT.add("b");
        LINJAT.add("c");
        LINJAT.add("d");
        LINJAT.add("e");
        LINJAT.add("f");
        LINJAT.add("g");
        LINJAT.add("h");
    }

    private static final int[][] V_SOTILAAN_RUUDUT = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 5, 10, 10, -20, -20, 10, 10, 5 },
            { 5, -5, -10, 0, 0, -10, -5, 5 },
            { 0, 0, 0, 20, 20, 0, 0, 0 },
            { 5, 5, 10, 25, 25, 10, 5, 5 },
            { 10, 10, 20, 30, 30, 20, 10, 10 },
            { 50, 50, 50, 50, 50, 50, 50, 50 },
            { 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    private static final int[][] M_SOTILAAN_RUUDUT = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 50, 50, 50, 50, 50, 50, 50, 50 },
            { 10, 10, 20, 30, 30, 20, 10, 10 },
            { 5, 5, 10, 25, 25, 10, 5, 5 },
            { 0, 0, 0, 20, 20, 0, 0, 0 },
            { 5, -5, -10, 0, 0, -10, -5, 5 },
            { 5, 10, 10, -20, -20, 10, 10, 5 },
            { 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    private static final int[][] V_RATSUN_RUUDUT = {
            { -50, -40, -30, -30, -30, -30, -40, -50 },
            { -40, -20, 0, 5, 5, 0, -20, -40 },
            { -30, 5, 10, 15, 15, 10, 5, -30 },
            { -30, 0, 15, 20, 20, 15, 0, -30 },
            { -30, 5, 15, 20, 20, 15, 5, -30 },
            { -30, 0, 10, 15, 15, 10, 0, -30 },
            { -40, -20, 0, 0, 0, 0, -20, -40 },
            { -50, -40, -30, -30, -30, -30, -40, -50 }
    };

    private static final int[][] M_RATSUN_RUUDUT = {
            { -50, -40, -30, -30, -30, -30, -40, -50 },
            { -40, -20, 0, 0, 0, 0, -20, -40 },
            { -30, 0, 10, 15, 15, 10, 0, -30 },
            { -30, 5, 15, 20, 20, 15, 5, -30 },
            { -30, 0, 15, 20, 20, 15, 0, -30 },
            { -30, 5, 10, 15, 15, 10, 5, -30 },
            { -40, -20, 0, 5, 5, 0, -20, -40 },
            { -50, -40, -30, -30, -30, -30, -40, -50 }
    };

    private static final int[][] V_LAHETIN_RUUDUT = {
            { -20, -10, -10, -10, -10, -10, -10, -20 },
            { -10, 5, 0, 0, 0, 0, 5, -10 },
            { -10, 10, 10, 10, 10, 10, 10, -10 },
            { -10, 0, 10, 10, 10, 10, 0, -10 },
            { -10, 5, 5, 10, 10, 5, 5, -10 },
            { -10, 0, 5, 10, 10, 5, 0, -10 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -20, -10, -10, -10, -10, -10, -10, -20 }
    };

    private static final int[][] M_LAHETIN_RUUDUT = {
            { -20, -10, -10, -10, -10, -10, -10, -20 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -10, 0, 5, 10, 10, 5, 0, -10 },
            { -10, 5, 5, 10, 10, 5, 5, -10 },
            { -10, 0, 10, 10, 10, 10, 0, -10 },
            { -10, 10, 10, 10, 10, 10, 10, -10 },
            { -10, 5, 0, 0, 0, 0, 5, -10 },
            { -20, -10, -10, -10, -10, -10, -10, -20 }
    };

    private static final int[][] V_TORNIN_RUUDUT = {
            { 0, 0, 0, 5, 5, 0, 0, 0 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { 5, 10, 10, 10, 10, 10, 10, 5 },
            { 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    private static final int[][] M_TORNIN_RUUDUT = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 5, 10, 10, 10, 10, 10, 10, 5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { 0, 0, 0, 5, 5, 0, 0, 0 }
    };

    private static final int[][] V_KUNINGATTAREN_RUUDUT = {
            { -20, -10, -10, -5, -5, -10, -10, -20 },
            { -10, 0, 5, 0, 0, 0, 0, -10 },
            { -10, 5, 5, 5, 5, 5, 0, -10 },
            { 0, 0, 5, 5, 5, 5, 0, -5 },
            { -5, 0, 5, 5, 5, 5, 0, -5 },
            { -10, 0, 5, 5, 5, 5, 0, -10 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -20, -10, -10, -5, -5, -10, -10, -20 }
    };

    private static final int[][] M_KUNINGATTAREN_RUUDUT = {
            { -20, -10, -10, -5, -5, -10, -10, -20 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -10, 0, 5, 5, 5, 5, 0, -10 },
            { -5, 0, 5, 5, 5, 5, 0, -5 },
            { 0, 0, 5, 5, 5, 5, 0, -5 },
            { -10, 5, 5, 5, 5, 5, 0, -10 },
            { -10, 0, 5, 0, 0, 0, 0, -10 },
            { -20, -10, -10, -5, -5, -10, -10, -20 }
    };

    private static final int[][] V_KUNINKAAN_RUUDUT = {
            { 20, 30, 10, 0, 0, 10, 30, 20 },
            { 20, 20, 0, 0, 0, 0, 20, 20 },
            { -10, -20, -20, -20, -20, -20, -20, -10 },
            { -20, -30, -30, -40, -40, -30, -30, -20 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 }
    };

    private static final int[][] M_KUNINKAAN_RUUDUT = {
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -20, -30, -30, -40, -40, -30, -30, -20 },
            { -10, -20, -20, -20, -20, -20, -20, -10 },
            { 20, 20, 0, 0, 0, 0, 20, 20 },
            { 20, 30, 10, 0, 0, 10, 30, 20 }
    };

    // private static final int[][] V_KUNINKAAN_RUUDUT_LOPPUPELI = {
    // { -50, -30, -30, -30, -30, -30, -30, -50 },
    // { -30, -30, 0, 0, 0, 0, -30, -30 },
    // { -30, -10, 20, 30, 30, 20, -10, -30 },
    // { -30, -10, 30, 40, 40, 30, -10, -30 },
    // { -30, -10, 30, 40, 40, 30, -10, -30 },
    // { -30, -10, 20, 30, 30, 20, -10, -30 },
    // { -30, -20, -10, 0, 0, -10, -20, -30 },
    // { -50, -40, -30, -20, -20, -30, -40, -50 }
    // };

    // private static final int[][] M_KUNINKAAN_RUUDUT_LOPPUPELI = {
    // { -50, -40, -30, -20, -20, -30, -40, -50 },
    // { -30, -20, -10, 0, 0, -10, -20, -30 },
    // { -30, -10, 20, 30, 30, 20, -10, -30 },
    // { -30, -10, 30, 40, 40, 30, -10, -30 },
    // { -30, -10, 30, 40, 40, 30, -10, -30 },
    // { -30, -10, 20, 30, 30, 20, -10, -30 },
    // { -30, -30, 0, 0, 0, 0, -30, -30 },
    // { -50, -30, -30, -30, -30, -30, -30, -50 }
    // };

    public Arvioija() {
    }

    private int arvioiSijaintienMukaan(String ruutu, NappulaTyyppi nappula, Side puoli) {
        int linjaI = LINJAT.indexOf(ruutu.substring(0, 1));
        int riviI = Integer.valueOf(ruutu.substring(1, 2)) - 1;

        if (puoli == Side.WHITE) {
            switch (nappula) {
                case SOTILAS:
                    return V_SOTILAAN_RUUDUT[linjaI][riviI];
                case RATSU:
                    return V_RATSUN_RUUDUT[linjaI][riviI];
                case LAHETTI:
                    return V_LAHETIN_RUUDUT[linjaI][riviI];
                case TORNI:
                    return V_TORNIN_RUUDUT[linjaI][riviI];
                case KUNINGATAR:
                    return V_KUNINGATTAREN_RUUDUT[linjaI][riviI];
                case KUNINGAS:
                    return V_KUNINKAAN_RUUDUT[linjaI][riviI];
                default:
                    return 0;
            }
        } else {
            switch (nappula) {
                case SOTILAS:
                    return M_SOTILAAN_RUUDUT[linjaI][riviI];
                case RATSU:
                    return M_RATSUN_RUUDUT[linjaI][riviI];
                case LAHETTI:
                    return M_LAHETIN_RUUDUT[linjaI][riviI];
                case TORNI:
                    return M_TORNIN_RUUDUT[linjaI][riviI];
                case KUNINGATAR:
                    return M_KUNINGATTAREN_RUUDUT[linjaI][riviI];
                case KUNINGAS:
                    return M_KUNINKAAN_RUUDUT[linjaI][riviI];
                default:
                    return 0;
            }
        }
    }

    public int arvioiPelilauta(Pelilauta lauta) { // Toistaiseksi vain valkoiselle toimii
        HashMap<String, NappulaTyyppi> valkoiset = lauta.getValkoiset();
        HashMap<String, NappulaTyyppi> mustat = lauta.getMustat();
        int pisteet = 0;
        for (HashMap.Entry<String, NappulaTyyppi> set : valkoiset.entrySet()) {
            if (set.getValue() == null) {
                continue;
            }
            pisteet += PISTEYTYS.get(set.getValue());
            pisteet += arvioiSijaintienMukaan(set.getKey(), set.getValue(), Side.WHITE);
        }

        for (HashMap.Entry<String, NappulaTyyppi> set : mustat.entrySet()) {
            if (set.getValue() == null) {
                continue;
            }
            pisteet -= PISTEYTYS.get(set.getValue());
            pisteet -= arvioiSijaintienMukaan(set.getKey(), set.getValue(), Side.BLACK);
        }

        return pisteet;
    }
}
