package datastructureproject;

import java.util.ArrayList;
import java.util.HashMap;

import chess.model.Side;

public class SiirtoGeneraattori {

    private HashMap<String, NappulaTyyppi> valkoiset;
    private HashMap<String, NappulaTyyppi> mustat;
    private ArrayList<String> omatNappulat;
    private ArrayList<String> vastustajanNappulat;
    private ArrayList<String> linjat;
    private HashMap<String, String> kiinnitetyt; // Tallennetaan tieto minkä ruutujen nappulat on kiinnitetty
                                                 // kuninkaaseen
    // tyylillä <kiinnitettyRuutu, kiinnittäväRuutu>
    private ArrayList<String> siirrot;
    private Side puoli;
    private String shakittaja;

    public SiirtoGeneraattori(HashMap<String, NappulaTyyppi> valkoiset, HashMap<String, NappulaTyyppi> mustat,
            Side puoli) {
        this.valkoiset = valkoiset;
        this.mustat = mustat;
        this.omatNappulat = new ArrayList<>();
        this.vastustajanNappulat = new ArrayList<>();
        this.kiinnitetyt = new HashMap<>();
        this.siirrot = new ArrayList<>();
        this.puoli = puoli;

        this.linjat = new ArrayList<>();
        this.linjat.add("a");
        this.linjat.add("b");
        this.linjat.add("c");
        this.linjat.add("d");
        this.linjat.add("e");
        this.linjat.add("f");
        this.linjat.add("g");
        this.linjat.add("h");
    }

    public ArrayList<String> valkoisenSiirrot() {
        siirrot.clear();
        omatNappulat.clear();
        vastustajanNappulat.clear();
        for (HashMap.Entry<String, NappulaTyyppi> set : valkoiset.entrySet()) {
            omatNappulat.add(set.getKey());
        }
        for (HashMap.Entry<String, NappulaTyyppi> set : mustat.entrySet()) {
            vastustajanNappulat.add(set.getKey());
        }

        for (HashMap.Entry<String, NappulaTyyppi> set : valkoiset.entrySet()) {
            if (set.getValue() == NappulaTyyppi.SOTILAS) {
                siirrot.addAll(valkoisenSotilaanSiirrot(set.getKey()));
            } else if (set.getValue() == NappulaTyyppi.TORNI) {
                siirrot.addAll(torninSiirrot(set.getKey()));
            } else if (set.getValue() == NappulaTyyppi.RATSU) {
                siirrot.addAll(ratsunSiirrot(set.getKey()));
            } else if (set.getValue() == NappulaTyyppi.LAHETTI) {
                siirrot.addAll(lahetinSiirrot(set.getKey()));
            } else if (set.getValue() == NappulaTyyppi.KUNINGATAR) {
                siirrot.addAll(torninSiirrot(set.getKey()));
                siirrot.addAll(lahetinSiirrot(set.getKey()));
            } else if (set.getValue() == NappulaTyyppi.KUNINGAS) {
                siirrot.addAll(kuninkaanSiirrot(set.getKey()));
            }
        }
        return siirrot;
    }

    public ArrayList<String> mustanSiirrot() {
        siirrot.clear();
        omatNappulat.clear();
        vastustajanNappulat.clear();
        for (HashMap.Entry<String, NappulaTyyppi> set : mustat.entrySet()) {
            omatNappulat.add(set.getKey());
        }
        for (HashMap.Entry<String, NappulaTyyppi> set : valkoiset.entrySet()) {
            vastustajanNappulat.add(set.getKey());
        }

        for (HashMap.Entry<String, NappulaTyyppi> set : mustat.entrySet()) {
            if (set.getValue() == NappulaTyyppi.SOTILAS) {
                siirrot.addAll(mustanSotilaanSiirrot(set.getKey()));
            } else if (set.getValue() == NappulaTyyppi.TORNI) {
                siirrot.addAll(torninSiirrot(set.getKey()));
            } else if (set.getValue() == NappulaTyyppi.RATSU) {
                siirrot.addAll(ratsunSiirrot(set.getKey()));
            } else if (set.getValue() == NappulaTyyppi.LAHETTI) {
                siirrot.addAll(lahetinSiirrot(set.getKey()));
            } else if (set.getValue() == NappulaTyyppi.KUNINGATAR) {
                siirrot.addAll(torninSiirrot(set.getKey()));
                siirrot.addAll(lahetinSiirrot(set.getKey()));
            } else if (set.getValue() == NappulaTyyppi.KUNINGAS) {
                siirrot.addAll(kuninkaanSiirrot(set.getKey()));
            }
        }
        return siirrot;
    }

    private ArrayList<String> lahettiOikealleYlos(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        ArrayList<String> kohteet = new ArrayList<>();
        int linjaI = linjat.indexOf(linja);

        for (int i = 1; i <= 8; i++) { // Oikealle ylös
            if ((linjaI + i) > 7 || rivi + i > 8) {
                break;
            }
            String kohde = linjat.get(linjaI + i) + String.valueOf(rivi + i);
            if (omatNappulat.contains(kohde)) {
                break;
            }
            if (vastustajanNappulat.contains(kohde)) {
                kohteet.add(kohde);
                break;
            }
            kohteet.add(kohde);
        }

        for (int i = 0; i < kohteet.size(); i++) {
            String siirto = ruutu + kohteet.get(i);
            mahdollisetSiirrot.add(siirto);
        }

        return mahdollisetSiirrot;
    }

    private ArrayList<String> lahettiOikealleAlas(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        ArrayList<String> kohteet = new ArrayList<>();
        int linjaI = linjat.indexOf(linja);

        for (int i = 1; i <= 8; i++) { // Oikealle alas
            if ((linjaI + i) > 7 || rivi - i < 1) {
                break;
            }
            String kohde = linjat.get(linjaI + i) + String.valueOf(rivi - i);
            if (omatNappulat.contains(kohde)) {
                break;
            }
            if (vastustajanNappulat.contains(kohde)) {
                kohteet.add(kohde);
                break;
            }
            kohteet.add(kohde);
        }

        for (int i = 0; i < kohteet.size(); i++) {
            String siirto = ruutu + kohteet.get(i);
            mahdollisetSiirrot.add(siirto);
        }

        return mahdollisetSiirrot;
    }

    private ArrayList<String> lahettiVasemmalleAlas(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        ArrayList<String> kohteet = new ArrayList<>();
        int linjaI = linjat.indexOf(linja);

        for (int i = 1; i <= 8; i++) { // Vasemmalle alas
            if ((linjaI - i) < 0 || rivi - i < 1) {
                break;
            }
            String kohde = linjat.get(linjaI - i) + String.valueOf(rivi - i);
            if (omatNappulat.contains(kohde)) {
                break;
            }
            if (vastustajanNappulat.contains(kohde)) {
                kohteet.add(kohde);
                break;
            }
            kohteet.add(kohde);
        }

        for (int i = 0; i < kohteet.size(); i++) {
            String siirto = ruutu + kohteet.get(i);
            mahdollisetSiirrot.add(siirto);
        }

        return mahdollisetSiirrot;
    }

    private ArrayList<String> lahettiVasemmalleYlos(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        ArrayList<String> kohteet = new ArrayList<>();
        int linjaI = linjat.indexOf(linja);

        for (int i = 1; i <= 8; i++) { // Vasemmalle ylös
            if ((linjaI - i) < 0 || rivi + i > 8) {
                break;
            }
            String kohde = linjat.get(linjaI - i) + String.valueOf(rivi + i);
            if (omatNappulat.contains(kohde)) {
                break;
            }
            if (vastustajanNappulat.contains(kohde)) {
                kohteet.add(kohde);
                break;
            }
            kohteet.add(kohde);
        }

        for (int i = 0; i < kohteet.size(); i++) {
            String siirto = ruutu + kohteet.get(i);
            mahdollisetSiirrot.add(siirto);
        }

        return mahdollisetSiirrot;
    }

    private ArrayList<String> lahetinSiirrot(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        mahdollisetSiirrot.addAll(lahettiOikealleYlos(ruutu));
        mahdollisetSiirrot.addAll(lahettiOikealleAlas(ruutu));
        mahdollisetSiirrot.addAll(lahettiVasemmalleAlas(ruutu));
        mahdollisetSiirrot.addAll(lahettiVasemmalleYlos(ruutu));
        return mahdollisetSiirrot;
    }

    private ArrayList<String> ratsunSiirrot(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        ArrayList<String> kohteet = new ArrayList<>();
        int linjaI = linjat.indexOf(linja);
        if (rivi < 7 && !linja.equals("h")) {
            kohteet.add(linjat.get(linjaI + 1) + String.valueOf(rivi + 2)); // Oikealle ylöspäin ylempi
        }
        if (rivi < 8 && linjaI < 6) {
            kohteet.add(linjat.get(linjaI + 2) + String.valueOf(rivi + 1)); // Oikealle ylöspäin alempi
        }
        if (rivi > 1 && linjaI < 6) {
            kohteet.add(linjat.get(linjaI + 2) + String.valueOf(rivi - 1)); // Oikealle alaspäin ylempi
        }
        if (rivi > 2 && !linja.equals("h")) {
            kohteet.add(linjat.get(linjaI + 1) + String.valueOf(rivi - 2)); // Oikealle alaspäin alempi
        }
        if (rivi > 2 && !linja.equals("a")) {
            kohteet.add(linjat.get(linjaI - 1) + String.valueOf(rivi - 2)); // Vasemmalle alaspäin alempi
        }
        if (rivi > 1 && linjaI > 1) {
            kohteet.add(linjat.get(linjaI - 2) + String.valueOf(rivi - 1)); // Vasemmalle alaspäin ylempi
        }
        if (rivi < 8 && linjaI > 1) {
            kohteet.add(linjat.get(linjaI - 2) + String.valueOf(rivi + 1)); // Vasemmalle ylöspäin alempi
        }
        if (rivi < 7 && !linja.equals("a")) {
            kohteet.add(linjat.get(linjaI - 1) + String.valueOf(rivi + 2)); // Vasemmalle ylöspäin ylempi
        }

        for (int i = 0; i < kohteet.size(); i++) {
            if (omatNappulat.contains(kohteet.get(i))) {
                continue;
            }
            String siirto = ruutu + kohteet.get(i);
            mahdollisetSiirrot.add(siirto);
        }
        return mahdollisetSiirrot;
    }

    private ArrayList<String> torniVasemmalle(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        ArrayList<String> kohteet = new ArrayList<>();
        int linjaI = linjat.indexOf(linja);
        for (int i = linjaI - 1; i > 0; i--) { // Vasemmalle
            String kohde = linjat.get(linjaI) + String.valueOf(rivi);
            if (omatNappulat.contains(kohde)) {
                break;
            }
            if (vastustajanNappulat.contains(kohde)) {
                kohteet.add(kohde);
                break;
            }
            kohteet.add(kohde);
        }
        for (int i = 0; i < kohteet.size(); i++) {
            String siirto = ruutu + kohteet.get(i);
            mahdollisetSiirrot.add(siirto);
        }
        return mahdollisetSiirrot;
    }

    private ArrayList<String> torniOikealle(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        ArrayList<String> kohteet = new ArrayList<>();
        int linjaI = linjat.indexOf(linja);
        for (int i = linjaI + 1; i < linjat.size(); i++) { // Oikealle
            String kohde = linjat.get(linjaI) + String.valueOf(rivi);
            if (omatNappulat.contains(kohde)) {
                break;
            }
            if (vastustajanNappulat.contains(kohde)) {
                kohteet.add(kohde);
                break;
            }
            kohteet.add(kohde);
        }
        for (int i = 0; i < kohteet.size(); i++) {
            String siirto = ruutu + kohteet.get(i);
            mahdollisetSiirrot.add(siirto);
        }
        return mahdollisetSiirrot;
    }

    private ArrayList<String> torniYlos(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        ArrayList<String> kohteet = new ArrayList<>();
        for (int i = rivi + 1; i <= 8; i++) { // Ylös
            String kohde = linja + String.valueOf(i);
            if (omatNappulat.contains(kohde)) {
                break;
            }
            if (vastustajanNappulat.contains(kohde)) {
                kohteet.add(kohde);
                break;
            }
            kohteet.add(kohde);
        }
        for (int i = 0; i < kohteet.size(); i++) {
            String siirto = ruutu + kohteet.get(i);
            mahdollisetSiirrot.add(siirto);
        }
        return mahdollisetSiirrot;
    }

    private ArrayList<String> torniAlas(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        ArrayList<String> kohteet = new ArrayList<>();
        for (int i = rivi - 1; i > 0; i--) { // Alas
            String kohde = linja + String.valueOf(i);
            if (omatNappulat.contains(kohde)) {
                break;
            }
            if (vastustajanNappulat.contains(kohde)) {
                kohteet.add(kohde);
                break;
            }
            kohteet.add(kohde);
        }
        for (int i = 0; i < kohteet.size(); i++) {
            String siirto = ruutu + kohteet.get(i);
            mahdollisetSiirrot.add(siirto);
        }
        return mahdollisetSiirrot;
    }

    private ArrayList<String> torninSiirrot(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        mahdollisetSiirrot.addAll(torniOikealle(ruutu));
        mahdollisetSiirrot.addAll(torniVasemmalle(ruutu));
        mahdollisetSiirrot.addAll(torniYlos(ruutu));
        mahdollisetSiirrot.addAll(torniAlas(ruutu));
        return mahdollisetSiirrot;
    }

    private ArrayList<String> kuninkaanSiirrot(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        ArrayList<String> kohteet = new ArrayList<>();
        if (rivi < 8) {
            kohteet.add(linja + String.valueOf(rivi + 1)); // Ylempi ruutu
        }
        if (rivi > 1) {
            kohteet.add(linja + String.valueOf(rivi - 1)); // Alempi ruutu
        }
        if (!linja.equals("h") && rivi < 8) {
            kohteet.add(linjat.get((linjat.indexOf(linja) + 1)) + String.valueOf(rivi + 1)); // Yläoikea
        }
        if (!linja.equals("h")) {
            kohteet.add(linjat.get((linjat.indexOf(linja) + 1)) + String.valueOf(rivi)); // Oikea
        }
        if (!linja.equals("h") && rivi > 1) {
            kohteet.add(linjat.get((linjat.indexOf(linja) + 1)) + String.valueOf(rivi - 1)); // Alaoikea
        }
        if (!linja.equals("a") && rivi > 1) {
            kohteet.add(linjat.get((linjat.indexOf(linja) - 1)) + String.valueOf(rivi - 1)); // Alavasen
        }
        if (!linja.equals("a")) {
            kohteet.add(linjat.get((linjat.indexOf(linja) - 1)) + String.valueOf(rivi)); // Vasen
        }
        if (!linja.equals("a") && rivi < 8) {
            kohteet.add(linjat.get((linjat.indexOf(linja) - 1)) + String.valueOf(rivi + 1)); // Ylävasen
        }
        for (int i = 0; i < kohteet.size(); i++) {
            if (omatNappulat.contains(kohteet.get(i))) {
                continue;
            }
            String siirto = ruutu + kohteet.get(i);
            mahdollisetSiirrot.add(siirto);
        }
        return mahdollisetSiirrot;
    }

    private ArrayList<String> valkoisenSotilaanSiirrot(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        String k1 = linja + (String.valueOf((rivi + 1)));
        String k2 = linja + (String.valueOf((rivi + 2)));
        if (!linja.equals("a")) {
            String vasen = linjat.get((linjat.indexOf(linja)) - 1);
            String k3 = vasen + (String.valueOf((rivi + 1)));
            if (vastustajanNappulat.contains(k3)) {
                mahdollisetSiirrot.add(ruutu + k3);
            }
        }
        if (!linja.equals("h")) {
            String oikea = linjat.get((linjat.indexOf(linja)) + 1);
            String k4 = oikea + (String.valueOf((rivi + 1)));
            if (vastustajanNappulat.contains(k4)) {
                mahdollisetSiirrot.add(ruutu + k4);
            }
        }
        if (!omatNappulat.contains(k1) && !vastustajanNappulat.contains(k1)) {
            mahdollisetSiirrot.add(ruutu + k1);
        }
        if (!omatNappulat.contains(k2) && !vastustajanNappulat.contains(k2) && rivi == 2 && !omatNappulat.contains(k1)
                && !vastustajanNappulat.contains(k1)) {
            mahdollisetSiirrot.add(ruutu + k2);
        }
        return mahdollisetSiirrot;
    }

    private ArrayList<String> mustanSotilaanSiirrot(String ruutu) {
        ArrayList<String> mahdollisetSiirrot = new ArrayList<>();
        String linja = String.valueOf(ruutu.charAt(0));
        int rivi = Character.getNumericValue(ruutu.charAt(1));
        String k1 = linja + (String.valueOf((rivi - 1)));
        String k2 = linja + (String.valueOf((rivi - 2)));
        if (!linja.equals("a")) {
            String vasen = linjat.get((linjat.indexOf(linja)) - 1);
            String k3 = vasen + (String.valueOf((rivi - 1)));
            if (vastustajanNappulat.contains(k3)) {
                mahdollisetSiirrot.add(ruutu + k3);
            }
        }
        if (!linja.equals("h")) {
            String oikea = linjat.get((linjat.indexOf(linja)) + 1);
            String k4 = oikea + (String.valueOf((rivi - 1)));
            if (vastustajanNappulat.contains(k4)) {
                mahdollisetSiirrot.add(ruutu + k4);
            }
        }
        if (!omatNappulat.contains(k1) && !vastustajanNappulat.contains(k1)) {
            mahdollisetSiirrot.add(ruutu + k1);
        }
        if (!omatNappulat.contains(k2) && !vastustajanNappulat.contains(k2) && rivi == 7 && !omatNappulat.contains(k1)
                && !vastustajanNappulat.contains(k1)) {
            mahdollisetSiirrot.add(ruutu + k2);
        }
        return mahdollisetSiirrot;
    }

    private void etsiValkoisenKiinnitetytRuudut() { // Eli ruudut, joista nappulaa ei voi siirtää sillä kuningas
                                                    // joutuisi shakkiin
        String kuninkaanRuutu = "";
        kiinnitetyt.clear();
        for (HashMap.Entry<String, NappulaTyyppi> set : valkoiset.entrySet()) {
            if (set.getValue() == NappulaTyyppi.KUNINGAS) {
                kuninkaanRuutu = set.getKey();
                break;
            }
        }
        int rivi = Character.getNumericValue(kuninkaanRuutu.charAt(1));
        String linja = String.valueOf(kuninkaanRuutu.charAt(0));
        String kiinnitettyRuutu = "";
        String kiinnittavaRuutu = "";
        String ruutu = "";

        for (int i = linjat.indexOf(linja) + 1; i < linjat.size(); i++) { // Oikealle vaaka
            ruutu = linjat.get(i) + String.valueOf(rivi);
            if (!kiinnitettyRuutu.isEmpty() && valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = "";
                break;
            }
            if (mustat.containsKey(ruutu) && mustat.get(ruutu) != NappulaTyyppi.TORNI
                    || mustat.get(ruutu) != NappulaTyyppi.KUNINGATAR) {
                kiinnitettyRuutu = "";
                break;
            }
            if (valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = ruutu;
            }
            if (mustat.get(ruutu) == NappulaTyyppi.TORNI
                    || mustat.get(ruutu) == NappulaTyyppi.KUNINGATAR) {
                kiinnittavaRuutu = ruutu;
                break;
            }
        }

        if (!kiinnitettyRuutu.isEmpty() && !kiinnittavaRuutu.isEmpty()) {
            kiinnitetyt.put(kiinnitettyRuutu, kiinnittavaRuutu);
            kiinnitettyRuutu = "";
        }

        for (int i = linjat.indexOf(linja) - 1; i > 0; i--) { // Vasemmalle vaaka
            ruutu = linjat.get(i) + String.valueOf(rivi);
            if (!kiinnitettyRuutu.isEmpty() && valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = "";
                break;
            }
            if (mustat.containsKey(ruutu) && mustat.get(ruutu) != NappulaTyyppi.TORNI
                    || mustat.get(ruutu) != NappulaTyyppi.KUNINGATAR) {
                kiinnitettyRuutu = "";
                break;
            }
            if (valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = ruutu;
            }
            if (mustat.get(ruutu) == NappulaTyyppi.TORNI
                    || mustat.get(ruutu) == NappulaTyyppi.KUNINGATAR) {
                kiinnittavaRuutu = ruutu;
                break;
            }
        }

        if (!kiinnitettyRuutu.isEmpty() && !kiinnittavaRuutu.isEmpty()) {
            kiinnitetyt.put(kiinnitettyRuutu, kiinnittavaRuutu);
            kiinnitettyRuutu = "";
        }

        for (int i = rivi + 1; i <= 8; i++) { // Ylös
            ruutu = linja + String.valueOf(rivi);
            if (!kiinnitettyRuutu.isEmpty() && valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = "";
                break;
            }
            if (mustat.containsKey(ruutu) && mustat.get(ruutu) != NappulaTyyppi.TORNI
                    || mustat.get(ruutu) != NappulaTyyppi.KUNINGATAR) {
                kiinnitettyRuutu = "";
                break;
            }
            if (valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = ruutu;
            }
            if (mustat.get(ruutu) == NappulaTyyppi.TORNI
                    || mustat.get(ruutu) == NappulaTyyppi.KUNINGATAR) {
                kiinnittavaRuutu = ruutu;
                break;
            }
        }

        if (!kiinnitettyRuutu.isEmpty() && !kiinnittavaRuutu.isEmpty()) {
            kiinnitetyt.put(kiinnitettyRuutu, kiinnittavaRuutu);
            kiinnitettyRuutu = "";
        }

        for (int i = rivi - 1; i > 0; i--) { // Alas
            ruutu = linja + String.valueOf(rivi);
            if (!kiinnitettyRuutu.isEmpty() && valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = "";
                break;
            }
            if (mustat.containsKey(ruutu) && mustat.get(ruutu) != NappulaTyyppi.TORNI
                    || mustat.get(ruutu) != NappulaTyyppi.KUNINGATAR) {
                kiinnitettyRuutu = "";
                break;
            }
            if (valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = ruutu;
            }
            if (mustat.get(ruutu) == NappulaTyyppi.TORNI
                    || mustat.get(ruutu) == NappulaTyyppi.KUNINGATAR) {
                kiinnittavaRuutu = ruutu;
                break;
            }
        }

        if (!kiinnitettyRuutu.isEmpty() && !kiinnittavaRuutu.isEmpty()) {
            kiinnitetyt.put(kiinnitettyRuutu, kiinnittavaRuutu);
            kiinnitettyRuutu = "";
        }

        int linjaI = linjat.indexOf(linja) + 1;
        for (int i = rivi + 1; i < 8; i++) { // Oikealle ylös vinoon
            if (linjaI > 7) {
                break;
            }
            ruutu = linjat.get(linjaI) + String.valueOf(i);
            linjaI++;
            if (!kiinnitettyRuutu.isEmpty() && valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = "";
                break;
            }
            if (mustat.containsKey(ruutu) && mustat.get(ruutu) != NappulaTyyppi.LAHETTI
                    || mustat.get(ruutu) != NappulaTyyppi.KUNINGATAR) {
                kiinnitettyRuutu = "";
                break;
            }
            if (valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = ruutu;
            }
            if (mustat.get(ruutu) == NappulaTyyppi.LAHETTI
                    || mustat.get(ruutu) == NappulaTyyppi.KUNINGATAR) {
                kiinnittavaRuutu = ruutu;
                break;
            }
        }

        if (!kiinnitettyRuutu.isEmpty() && !kiinnittavaRuutu.isEmpty()) {
            kiinnitetyt.put(kiinnitettyRuutu, kiinnittavaRuutu);
            kiinnitettyRuutu = "";
        }

        linjaI = linjat.indexOf(linja) - 1;
        for (int i = rivi - 1; i > 0; i--) { // Vasemmalle alas vinoon
            if (linjaI < 0) {
                break;
            }
            ruutu = linjat.get(linjaI) + String.valueOf(i);
            linjaI--;
            if (!kiinnitettyRuutu.isEmpty() && valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = "";
                break;
            }

            if (mustat.containsKey(ruutu) && mustat.get(ruutu) != NappulaTyyppi.LAHETTI
                    || mustat.get(ruutu) != NappulaTyyppi.KUNINGATAR) {
                kiinnitettyRuutu = "";
                break;
            }
            if (valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = ruutu;
            }
            if (mustat.get(ruutu) == NappulaTyyppi.LAHETTI
                    || mustat.get(ruutu) == NappulaTyyppi.KUNINGATAR) {
                kiinnittavaRuutu = ruutu;
                break;
            }
        }

        if (!kiinnitettyRuutu.isEmpty() && !kiinnittavaRuutu.isEmpty()) {
            kiinnitetyt.put(kiinnitettyRuutu, kiinnittavaRuutu);
            kiinnitettyRuutu = "";
        }

        linjaI = linjat.indexOf(linja) + 1;
        for (int i = rivi - 1; i > 0; i--) { // Oikealle alas vinoon
            if (linjaI > 7) {
                break;
            }
            ruutu = linjat.get(linjaI) + String.valueOf(i);
            linjaI++;
            if (!kiinnitettyRuutu.isEmpty() && valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = "";
                break;
            }
            if (mustat.containsKey(ruutu) && mustat.get(ruutu) != NappulaTyyppi.LAHETTI
                    || mustat.get(ruutu) != NappulaTyyppi.KUNINGATAR) {
                kiinnitettyRuutu = "";
                break;
            }
            if (valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = ruutu;
            }
            if (mustat.get(ruutu) == NappulaTyyppi.LAHETTI
                    || mustat.get(ruutu) == NappulaTyyppi.KUNINGATAR) {
                kiinnittavaRuutu = ruutu;
                break;
            }
        }

        if (!kiinnitettyRuutu.isEmpty() && !kiinnittavaRuutu.isEmpty()) {
            kiinnitetyt.put(kiinnitettyRuutu, kiinnittavaRuutu);
            kiinnitettyRuutu = "";
        }

        linjaI = linjat.indexOf(linja) - 1;
        for (int i = rivi - 1; i < 8; i++) { // Vasemmalle ylös vinoon
            if (linjaI < 0) {
                break;
            }
            ruutu = linjat.get(linjaI) + String.valueOf(i);
            linjaI--;
            if (!kiinnitettyRuutu.isEmpty() && valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = "";
                break;
            }

            if (mustat.containsKey(ruutu) && mustat.get(ruutu) != NappulaTyyppi.LAHETTI
                    || mustat.get(ruutu) != NappulaTyyppi.KUNINGATAR) {
                kiinnitettyRuutu = "";
                break;
            }
            if (valkoiset.containsKey(ruutu)) {
                kiinnitettyRuutu = ruutu;
            }
            if (mustat.get(ruutu) == NappulaTyyppi.LAHETTI
                    || mustat.get(ruutu) == NappulaTyyppi.KUNINGATAR) {
                kiinnittavaRuutu = ruutu;
                break;
            }
        }

        if (!kiinnitettyRuutu.isEmpty() && !kiinnittavaRuutu.isEmpty()) {
            kiinnitetyt.put(kiinnitettyRuutu, kiinnittavaRuutu);
            kiinnitettyRuutu = "";
        }
    }

    public Boolean onkoShakissa(Side omaPuoli) {
        if (omaPuoli == Side.WHITE) {
            String kuninkaanRuutu = "";
            for (HashMap.Entry<String, NappulaTyyppi> set : valkoiset.entrySet()) {
                if (set.getValue() == NappulaTyyppi.KUNINGAS) {
                    kuninkaanRuutu = set.getKey();
                    break;
                }
            }
            ArrayList<String> vastustajanSiirrot = mustanSiirrot();
            for (int i = 0; i < vastustajanSiirrot.size(); i++) {
                String kohderuutu = vastustajanSiirrot.get(i).substring(2);
                if (kohderuutu.equals(kuninkaanRuutu)) {
                    shakittaja = vastustajanSiirrot.get(i).substring(0, 2);
                    return true;
                }
            }
        }
        if (omaPuoli == Side.BLACK) {
            String kuninkaanRuutu = "";
            for (HashMap.Entry<String, NappulaTyyppi> set : mustat.entrySet()) {
                if (set.getValue() == NappulaTyyppi.KUNINGAS) {
                    kuninkaanRuutu = set.getKey();
                    break;
                }
            }
            ArrayList<String> vastustajanSiirrot = valkoisenSiirrot();
            for (int i = 0; i < vastustajanSiirrot.size(); i++) {
                String kohderuutu = vastustajanSiirrot.get(i).substring(2);
                if (kohderuutu.equals(kuninkaanRuutu)) {
                    shakittaja = vastustajanSiirrot.get(i).substring(0, 2);
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<String> poistaShakki(ArrayList<String> siirrot) {
        ArrayList<String> laillisetSiirrot = new ArrayList<>();
        ArrayList<String> vastustajanSiirrot = new ArrayList<>();
        ArrayList<String> kuninkaanSiirrot = new ArrayList<>();
        ArrayList<String> blockaavatRuudut = new ArrayList<>();
        NappulaTyyppi shakittajanTyyppi = null;
        String kuninkaanRuutu = "";
        if (puoli == Side.WHITE) {
            vastustajanSiirrot = mustanSiirrot();
            for (HashMap.Entry<String, NappulaTyyppi> set : valkoiset.entrySet()) {
                if (set.getValue() == NappulaTyyppi.KUNINGAS) {
                    kuninkaanRuutu = set.getKey();
                    break;
                }
            }
            kuninkaanSiirrot = kuninkaanSiirrot(kuninkaanRuutu);
            shakittajanTyyppi = mustat.get(shakittaja);
        } else {
            vastustajanSiirrot = valkoisenSiirrot();
            for (HashMap.Entry<String, NappulaTyyppi> set : mustat.entrySet()) {
                if (set.getValue() == NappulaTyyppi.KUNINGAS) {
                    kuninkaanRuutu = set.getKey();
                    break;
                }
            }
            kuninkaanSiirrot = kuninkaanSiirrot(kuninkaanRuutu);
            shakittajanTyyppi = valkoiset.get(shakittaja);
        }
        for (int i = 0; i < vastustajanSiirrot.size(); i++) {
            if (kuninkaanSiirrot.contains(vastustajanSiirrot.get(i).substring(2))) {
                kuninkaanSiirrot.remove(kuninkaanSiirrot.indexOf(vastustajanSiirrot.get(i).substring(2)));
            }
        }
        for (int i = 0; i < kuninkaanSiirrot.size(); i++) {
            laillisetSiirrot.add(kuninkaanRuutu + kuninkaanSiirrot.get(i));
        }
        for (int i = 0; i < siirrot.size(); i++) {
            String kohderuutu = siirrot.get(i).substring(2);
            if (kohderuutu.equals(shakittaja)) {
                laillisetSiirrot.add(siirrot.get(i));
            }
        }
        String linja = String.valueOf(kuninkaanRuutu.charAt(0));
        int rivi = Character.getNumericValue(kuninkaanRuutu.charAt(1));
        String sLinja = String.valueOf(shakittaja.charAt(0));
        int sRivi = Character.getNumericValue(shakittaja.charAt(1));
        int linjaI = linjat.indexOf(linja);
        int sLinjaI = linjat.indexOf(sLinja);
        if (shakittajanTyyppi == NappulaTyyppi.TORNI || shakittajanTyyppi == NappulaTyyppi.KUNINGATAR) {
            ArrayList<String> shakittajanSiirrot = torninSiirrot(shakittaja);
            if (linja.equals(sLinja)) {
                for (int i = 0; i < shakittajanSiirrot.size(); i++) {
                    if (!shakittajanSiirrot.get(i).equals(kuninkaanRuutu)
                            && shakittajanSiirrot.get(i).substring(0, 2).equals(linja)) {
                        blockaavatRuudut.add(shakittajanSiirrot.get(i));
                    }
                }
            } else if (rivi == sRivi) {
                for (int i = 0; i < shakittajanSiirrot.size(); i++) {
                    if (!shakittajanSiirrot.get(i).equals(kuninkaanRuutu)
                            && rivi == Character.getNumericValue(shakittaja.charAt(1))) {
                        blockaavatRuudut.add(shakittajanSiirrot.get(i));
                    }
                }
            }
        } else if (shakittajanTyyppi == NappulaTyyppi.LAHETTI || shakittajanTyyppi == NappulaTyyppi.KUNINGATAR) {
            ArrayList<String> shakittajanSiirrot = new ArrayList<>();
            if (rivi < sRivi && linjaI < sLinjaI) {
                shakittajanSiirrot = lahettiVasemmalleAlas(shakittaja);
            } else if (rivi > sRivi && linjaI < sLinjaI) {
                shakittajanSiirrot = lahettiVasemmalleYlos(shakittaja);
            } else if (rivi < sRivi && linjaI > sLinjaI) {
                shakittajanSiirrot = lahettiOikealleYlos(shakittaja);
            } else if (rivi > sRivi && linjaI > sLinjaI) {
                shakittajanSiirrot = lahettiOikealleYlos(shakittaja);
            }
            for (int i = 0; i < shakittajanSiirrot.size(); i++) {
                if (!shakittajanSiirrot.get(i).equals(kuninkaanRuutu)) {
                    blockaavatRuudut.add(shakittajanSiirrot.get(i));
                }
            }
        }
        for (int i = 0; i < siirrot.size(); i++) {
            if (blockaavatRuudut.contains(siirrot.get(i).substring(2))
                    && !siirrot.get(i).substring(0, 2).equals(kuninkaanRuutu)) {
                laillisetSiirrot.add(siirrot.get(i));
            }
        }

        return laillisetSiirrot;
    }

    private ArrayList<String> poistaKuninkaanLaittomat(ArrayList<String> siirrot) {
        String kuninkaanRuutu = "";
        for (HashMap.Entry<String, NappulaTyyppi> set : valkoiset.entrySet()) {
            omatNappulat.add(set.getKey());
            if (set.getValue() == NappulaTyyppi.KUNINGAS) {
                kuninkaanRuutu = set.getKey();
            }
        }
        for (HashMap.Entry<String, NappulaTyyppi> set : mustat.entrySet()) {
            vastustajanNappulat.add(set.getKey());
        }
        ArrayList<String> kuninkaanSiirrot = kuninkaanSiirrot(kuninkaanRuutu);
        ArrayList<String> vastustajanSiirrot = mustanSiirrot();
        for (int i = 0; i < vastustajanSiirrot.size(); i++) {
            String vastustajanKohde = vastustajanSiirrot.get(i).substring(2);
            if (kuninkaanSiirrot.contains(vastustajanKohde)) {
                kuninkaanSiirrot.remove(vastustajanKohde);
            }
        }
        for (int i = 0; i < siirrot.size(); i++) {
            if (siirrot.get(i).substring(0, 2).equals(kuninkaanRuutu)
                    && !kuninkaanSiirrot.contains(siirrot.get(i).substring(2))) {
                siirrot.remove(i);
                i--;
            }
        }

        return siirrot;
    }

    public ArrayList<String> poistaValkoisenLaittomatSiirrot(ArrayList<String> siirrot) {
        ArrayList<String> laillisetSiirrot = new ArrayList<>();
        laillisetSiirrot.addAll(siirrot);
        if (onkoShakissa(puoli)) {
            System.out.println("SHAKISSA");
            System.out.println("SHAKISSA");
            System.out.println("SHAKISSA");
            System.out.println("SHAKISSA");
            System.out.println("SHAKISSA");
            return poistaShakki(laillisetSiirrot);
        }
        laillisetSiirrot = poistaKuninkaanLaittomat(laillisetSiirrot);
        etsiValkoisenKiinnitetytRuudut();

        String lahtoruutu = "";
        String kohderuutu = "";
        for (int i = 0; i < laillisetSiirrot.size(); i++) {
            lahtoruutu = laillisetSiirrot.get(i).substring(0, 2);
            kohderuutu = laillisetSiirrot.get(i).substring(2);
            System.out.println(lahtoruutu + " " + kohderuutu);

            if (kiinnitetyt.containsKey(lahtoruutu) && kiinnitetyt.get(lahtoruutu) != kohderuutu) {
                System.out.println("KIINNITETTY");
                laillisetSiirrot.remove(i);
                i--;
            }
        }
        return laillisetSiirrot;
    }
}
