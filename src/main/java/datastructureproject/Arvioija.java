package datastructureproject;

import java.util.ArrayList;
import java.util.HashMap;

import chess.model.Side;

// Lähteenä käytetty https://www.chessprogramming.org/Simplified_Evaluation_Function

public class Arvioija {

    private static final HashMap<Character, Integer> PISTEYTYS;
    static {
        PISTEYTYS = new HashMap<>();
        PISTEYTYS.put('p', 100);
        PISTEYTYS.put('r', 500);
        PISTEYTYS.put('q', 900);
        PISTEYTYS.put('n', 320);
        PISTEYTYS.put('b', 330);
        PISTEYTYS.put('k', 20000);
    }

    private static final ArrayList<Character> LINJAT;
    static {
        LINJAT = new ArrayList<>(); // Helpottaa pisteytyksen toteuttamista
        LINJAT.add('a');
        LINJAT.add('b');
        LINJAT.add('c');
        LINJAT.add('d');
        LINJAT.add('e');
        LINJAT.add('f');
        LINJAT.add('g');
        LINJAT.add('h');
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

    private static final int[][] V_KUNINKAAN_RUUDUT_LOPPUPELI = {
            { -50, -30, -30, -30, -30, -30, -30, -50 },
            { -30, -30, 0, 0, 0, 0, -30, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -20, -10, 0, 0, -10, -20, -30 },
            { -50, -40, -30, -20, -20, -30, -40, -50 }
    };

    private static final int[][] M_KUNINKAAN_RUUDUT_LOPPUPELI = {
            { -50, -40, -30, -20, -20, -30, -40, -50 },
            { -30, -20, -10, 0, 0, -10, -20, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 30, 40, 40, 30, -10, -30 },
            { -30, -10, 20, 30, 30, 20, -10, -30 },
            { -30, -30, 0, 0, 0, 0, -30, -30 },
            { -50, -30, -30, -30, -30, -30, -30, -50 }
    };

    public Arvioija() {
    }

    private int arvioiSijaintienMukaan(int linjaI, int riviI, char nappula, Side puoli, Boolean loppupeli) {
        if (puoli == Side.WHITE) {
            switch (nappula) {
                case 'p':
                    return V_SOTILAAN_RUUDUT[linjaI][riviI];
                case 'n':
                    return V_RATSUN_RUUDUT[linjaI][riviI];
                case 'b':
                    return V_LAHETIN_RUUDUT[linjaI][riviI];
                case 'r':
                    return V_TORNIN_RUUDUT[linjaI][riviI];
                case 'q':
                    return V_KUNINGATTAREN_RUUDUT[linjaI][riviI];
                case 'k':
                    if (loppupeli) {
                        return V_KUNINKAAN_RUUDUT_LOPPUPELI[linjaI][riviI];
                    }
                    return V_KUNINKAAN_RUUDUT[linjaI][riviI];
                default:
                    return 0;
            }
        } else {
            switch (nappula) {
                case 'p':
                    return M_SOTILAAN_RUUDUT[linjaI][riviI];
                case 'n':
                    return M_RATSUN_RUUDUT[linjaI][riviI];
                case 'b':
                    return M_LAHETIN_RUUDUT[linjaI][riviI];
                case 'r':
                    return M_TORNIN_RUUDUT[linjaI][riviI];
                case 'q':
                    return M_KUNINGATTAREN_RUUDUT[linjaI][riviI];
                case 'k':
                    if (loppupeli) {
                        return M_KUNINKAAN_RUUDUT_LOPPUPELI[linjaI][riviI];
                    }
                    return M_KUNINKAAN_RUUDUT[linjaI][riviI];
                default:
                    return 0;
            }
        }
    }

    public int arvioiPelilauta(Pelilauta lauta, Side puoli) { // Toistaiseksi vain valkoiselle toimii
        char[][] valkoiset = lauta.getValkoiset();
        char[][] mustat = lauta.getMustat();
        int pisteet = 0;
        for (int i = 0; i < valkoiset.length; i++) {
            for (int j = 0; j < valkoiset[i].length; j++) {
                if (valkoiset[i][j] != ' ') {
                    pisteet += PISTEYTYS.get(valkoiset[i][j]);
                    pisteet += arvioiSijaintienMukaan(i, j, valkoiset[i][j], Side.WHITE, lauta.getLoppupeli());
                }
            }
        }

        for (int i = 0; i < mustat.length; i++) {
            for (int j = 0; j < mustat[i].length; j++) {
                if (mustat[i][j] != ' ') {
                    pisteet -= PISTEYTYS.get(mustat[i][j]);
                    pisteet -= arvioiSijaintienMukaan(i, j, mustat[i][j], Side.BLACK, lauta.getLoppupeli());
                }
            }
        }
        if (puoli == Side.BLACK) {
            pisteet *= -1;
        }
        return pisteet;
    }
}
