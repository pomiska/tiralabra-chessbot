package datastructureproject;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.*;
import com.github.bhlangonijr.chesslib.*; // Käytetään valmista kirjastoa siirtojen testaamisessa, koska tiedetään varmasti että se generoi kaikki lailliset siirrot
import com.github.bhlangonijr.chesslib.move.*; //

import chess.model.Side;

public class SiirtoGeneraattoriTest {

    SiirtoGeneraattori sg;
    Pelilauta lauta;
    Board board;

    public SiirtoGeneraattoriTest() {
    }

    @Before
    public void setUp() {
        this.lauta = new Pelilauta();
        this.sg = new SiirtoGeneraattori(lauta.getValkoiset(), lauta.getMustat(), Side.WHITE);
        this.board = new Board();
    }

    @After
    public void tearDown() {
        this.lauta = null;
        this.sg = null;
        this.board = null;
    }

    @Test
    public void alkusiirrotGeneroidaanOikein() throws MoveGeneratorException {
        ArrayList<String> omatSiirrot = new ArrayList<>();
        omatSiirrot = this.lauta.etsiLaillisetSiirrot(Side.WHITE);
        MoveList oikeatSiirrotTemp = MoveGenerator.generateLegalMoves(this.board);
        ArrayList<String> oikeatSiirrot = new ArrayList<>();
        Collections.sort(omatSiirrot);
        for (int i = 0; i < oikeatSiirrotTemp.size(); i++) {
            oikeatSiirrot.add(oikeatSiirrotTemp.get(i).toString());
        }
        Collections.sort(oikeatSiirrot);
        assertEquals(omatSiirrot.toString(), oikeatSiirrot.toString());
    }

    @Test
    public void siirrotGeneroidaanOikein() throws MoveGeneratorException {
        String siirrotUCI = "e2e4 d7d5 b2b3 g7g6 g2g4 a7a5";
        this.lauta.pelaaSiirrot(siirrotUCI);
        siirrotUCI = siirrotUCI.toUpperCase();
        String[] s = siirrotUCI.split(" ");
        for (int i = 0; i < s.length; i++) {
            this.board.doMove(this.setMove(s[i].substring(0, 2), s[i].substring(2, 4), s[i].substring(4)));
        }

        ArrayList<String> omatSiirrot = new ArrayList<>();
        omatSiirrot = this.lauta.etsiLaillisetSiirrot(Side.WHITE);
        MoveList oikeatSiirrotTemp = MoveGenerator.generateLegalMoves(this.board);
        ArrayList<String> oikeatSiirrot = new ArrayList<>();
        Collections.sort(omatSiirrot);
        for (int i = 0; i < oikeatSiirrotTemp.size(); i++) {
            oikeatSiirrot.add(oikeatSiirrotTemp.get(i).toString());
        }
        Collections.sort(oikeatSiirrot);
        assertEquals(omatSiirrot.toString(), oikeatSiirrot.toString());

        siirrotUCI = "c2c4 e7e5 g1f3 f8b4";

        omatSiirrot.clear();
        omatSiirrot = this.lauta.etsiLaillisetSiirrot(Side.WHITE);
        oikeatSiirrotTemp.clear();
        oikeatSiirrotTemp = MoveGenerator.generateLegalMoves(this.board);
        oikeatSiirrot.clear();
        Collections.sort(omatSiirrot);
        for (int i = 0; i < oikeatSiirrotTemp.size(); i++) {
            oikeatSiirrot.add(oikeatSiirrotTemp.get(i).toString());
        }
        Collections.sort(oikeatSiirrot);
        assertEquals(omatSiirrot.toString(), oikeatSiirrot.toString());
    }

    public Move setMove(String starting, String ending, String promote) { // Kopioitu TestBot luokasta
        String promotionPiece = "";
        if (promote.length() > 0) {
            Piece piece = this.board.getPiece(Square.valueOf(starting));
            String side = piece.getPieceSide().value();
            switch (promote) {
                case "R":
                    promotionPiece = side + "_ROOK";
                    break;
                case "B":
                    promotionPiece = side + "_BISHOP";
                    break;
                case "N":
                    promotionPiece = side + "_KNIGHT";
                    break;
                case "Q":
                    promotionPiece = side + "_QUEEN";
                    break;
                default:
                    throw new Error("Something went wrong parsing the promoted piece");
            }
        }
        Move latestMove = promote.isEmpty() ? new Move(
                Square.fromValue(starting),
                Square.valueOf(ending))
                : new Move(Square.fromValue(starting),
                        Square.valueOf(ending), Piece.fromValue(promotionPiece));
        return latestMove;
    }

}
