package Model;

import Contract.*;
import Model.Utilities.Fen;
import Utility.MockObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MakingAMoveTest {

    private Contract.Observer mockObserver;
    private Game game;
    private final String[][] whitePawnOpening = new String[][]{
            {"rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR b KQkq a3 0 1", "rnbqkbnr/pppppppp/8/8/8/P7/1PPPPPPP/RNBQKBNR b KQkq - 0 1"},
            {"rnbqkbnr/pppppppp/8/8/1P6/8/P1PPPPPP/RNBQKBNR b KQkq b3 0 1", "rnbqkbnr/pppppppp/8/8/8/1P6/P1PPPPPP/RNBQKBNR b KQkq - 0 1"},
            {"rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR b KQkq c3 0 1", "rnbqkbnr/pppppppp/8/8/8/2P5/PP1PPPPP/RNBQKBNR b KQkq - 0 1"},
            {"rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq d3 0 1", "rnbqkbnr/pppppppp/8/8/8/3P4/PPP1PPPP/RNBQKBNR b KQkq - 0 1"},
            {"rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", "rnbqkbnr/pppppppp/8/8/8/4P3/PPPP1PPP/RNBQKBNR b KQkq - 0 1"},
            {"rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR b KQkq f3 0 1", "rnbqkbnr/pppppppp/8/8/8/5P2/PPPPP1PP/RNBQKBNR b KQkq - 0 1"},
            {"rnbqkbnr/pppppppp/8/8/6P1/8/PPPPPP1P/RNBQKBNR b KQkq g3 0 1", "rnbqkbnr/pppppppp/8/8/8/6P1/PPPPPP1P/RNBQKBNR b KQkq - 0 1"},
            {"rnbqkbnr/pppppppp/8/8/7P/8/PPPPPPP1/RNBQKBNR b KQkq h3 0 1", "rnbqkbnr/pppppppp/8/8/8/7P/PPPPPPP1/RNBQKBNR b KQkq - 0 1"}
    };

    private final String[][] blackPawnOpening = new String[][]{
            {"rnbqkbnr/1ppppppp/8/p7/3P4/8/PPP1PPPP/RNBQKBNR w KQkq a6 0 2","rnbqkbnr/1ppppppp/p7/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2"},
            {"rnbqkbnr/p1pppppp/8/1p6/3P4/8/PPP1PPPP/RNBQKBNR w KQkq b6 0 2","rnbqkbnr/p1pppppp/1p6/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2"},
            {"rnbqkbnr/pp1ppppp/8/2p5/3P4/8/PPP1PPPP/RNBQKBNR w KQkq c6 0 2","rnbqkbnr/pp1ppppp/2p5/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2"},
            {"rnbqkbnr/ppp1pppp/8/3p4/3P4/8/PPP1PPPP/RNBQKBNR w KQkq d6 0 2","rnbqkbnr/ppp1pppp/3p4/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2"},
            {"rnbqkbnr/pppp1ppp/8/4p3/3P4/8/PPP1PPPP/RNBQKBNR w KQkq e6 0 2","rnbqkbnr/pppp1ppp/4p3/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2"},
            {"rnbqkbnr/ppppp1pp/8/5p2/3P4/8/PPP1PPPP/RNBQKBNR w KQkq f6 0 2","rnbqkbnr/ppppp1pp/5p2/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2"},
            {"rnbqkbnr/pppppp1p/8/6p1/3P4/8/PPP1PPPP/RNBQKBNR w KQkq g6 0 2","rnbqkbnr/pppppp1p/6p1/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2"},
            {"rnbqkbnr/ppppppp1/8/7p/3P4/8/PPP1PPPP/RNBQKBNR w KQkq h6 0 2","rnbqkbnr/ppppppp1/7p/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2"}
    };



    @BeforeEach
    public void createObserver(){
        mockObserver = new MockObserver();
    }

    @ParameterizedTest
    @DisplayName("Pawn opening for White")
    @ValueSource(ints= {0,1,2,3,4,5,6,7})
    public void WhitePAwnOpeningMoveTest(int file){
        game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",mockObserver);
        game.makeAMove(new Position(file,6),new Position(file,4));
        Assertions.assertEquals(whitePawnOpening[file][0],game.getCompleteFEN());

        game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",mockObserver);
        game.makeAMove(new Position(file,6),new Position(file,5));
        Assertions.assertEquals(whitePawnOpening[file][1],game.getCompleteFEN());
    }


    @ParameterizedTest
    @DisplayName("Pawn opening for Black")
    @ValueSource(ints= {0,1,2,3,4,5,6,7})
    public void BlackPAwnOpeningMoveTest(int file){
        game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq d3 0 1",mockObserver);
        game.makeAMove(new Position(file,1),new Position(file,3));
        Assertions.assertEquals(blackPawnOpening[file][0],game.getCompleteFEN());

        game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq d3 0 1",mockObserver);
        game.makeAMove(new Position(file,1),new Position(file,2));
        Assertions.assertEquals(blackPawnOpening[file][1],game.getCompleteFEN());
    }

    @Test
    @DisplayName("Black en Passant")
    public void enPassantTest(){
        game = Fen.convertFenToBoard("rnbqkbnr/ppp1pppp/8/8/3Pp3/5N2/PPP2PPP/RNBQKB1R b KQkq d3 0 3",mockObserver);

        game.makeAMove(new Position(4,4),new Position(3,5));
        Assertions.assertEquals("rnbqkbnr/ppp1pppp/8/8/8/3p1N2/PPP2PPP/RNBQKB1R w KQkq - 0 4",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn Capture")
    public void pawnCaptureTest(){
        game = Fen.convertFenToBoard("rnbqkbnr/ppp1pppp/8/8/3Pp3/5N2/PPP2PPP/RNBQKB1R b KQkq d3 0 3",mockObserver);

        game.makeAMove(new Position(4,4),new Position(5,5));
        Assertions.assertEquals("rnbqkbnr/ppp1pppp/8/8/3P4/5p2/PPP2PPP/RNBQKB1R w KQkq - 0 4",game.getCompleteFEN());
        game.makeAMove(new Position(6,6),new Position(5,5));
        Assertions.assertEquals("rnbqkbnr/ppp1pppp/8/8/3P4/5P2/PPP2P1P/RNBQKB1R b KQkq - 0 4",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Queen Movement")
    public void QueenTest(){
        game = Fen.convertFenToBoard("rnbqkbnr/ppp1pppp/8/8/3P4/5P2/PPP2P1P/RNBQKB1R b KQkq - 0 4",mockObserver);

        game.makeAMove(new Position(3,0),new Position(3,3));
        Assertions.assertEquals("rnb1kbnr/ppp1pppp/8/3q4/3P4/5P2/PPP2P1P/RNBQKB1R w KQkq - 1 5",game.getCompleteFEN());
        game.makeAMove(new Position(3,7),new Position(4,6));
        Assertions.assertEquals("rnb1kbnr/ppp1pppp/8/3q4/3P4/5P2/PPP1QP1P/RNB1KB1R b KQkq - 2 5",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Queen Capture")
    public void QueenTest2(){
        game = Fen.convertFenToBoard("rnb1kbnr/ppp1pppp/8/3q4/3P4/5P2/PPP1QP1P/RNB1KB1R b KQkq - 2 5",mockObserver);

        game.makeAMove(new Position(3,3),new Position(5,5));
        Assertions.assertEquals("rnb1kbnr/ppp1pppp/8/8/3P4/5q2/PPP1QP1P/RNB1KB1R w KQkq - 0 6",game.getCompleteFEN());
        game.makeAMove(new Position(4,6),new Position(5,5));
        Assertions.assertEquals("rnb1kbnr/ppp1pppp/8/8/3P4/5Q2/PPP2P1P/RNB1KB1R b KQkq - 0 6",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Bishop Movement")
    public void BishopTest(){
        game = Fen.convertFenToBoard("rnb1kbnr/ppp1pp1p/8/8/3P4/5Q2/PPP2P1P/RNB1KB1R w KQkq - 0 4",mockObserver);

        game.makeAMove(new Position(5,7),new Position(1,3));
        Assertions.assertEquals("rnb1kbnr/ppp1pp1p/8/1B6/3P4/5Q2/PPP2P1P/RNB1K2R b KQkq - 1 4",game.getCompleteFEN());
        game.makeAMove(new Position(2,0),new Position(3,1));
        Assertions.assertEquals("rn2kbnr/pppbpp1p/8/1B6/3P4/5Q2/PPP2P1P/RNB1K2R w KQkq - 2 5",game.getCompleteFEN());

        game.makeAMove(new Position(2,7),new Position(4,5));
        Assertions.assertEquals("rn2kbnr/pppbpp1p/8/1B6/3P4/4BQ2/PPP2P1P/RN2K2R b KQkq - 3 5",game.getCompleteFEN());
        game.makeAMove(new Position(5,0),new Position(6,1));
        Assertions.assertEquals("rn2k1nr/pppbppbp/8/1B6/3P4/4BQ2/PPP2P1P/RN2K2R w KQkq - 4 6",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Bishop Capture")
    public void BishopTest2(){
        game = Fen.convertFenToBoard("rn2k1nr/pppbppbp/8/1B6/3P4/4BQ2/PPP2P1P/RN2K2R b KQkq - 0 4",mockObserver);

        game.makeAMove(new Position(6,1),new Position(3,4));
        Assertions.assertEquals("rn2k1nr/pppbpp1p/8/1B6/3b4/4BQ2/PPP2P1P/RN2K2R w KQkq - 0 5",game.getCompleteFEN());
        game.makeAMove(new Position(4,5),new Position(3,4));
        Assertions.assertEquals("rn2k1nr/pppbpp1p/8/1B6/3B4/5Q2/PPP2P1P/RN2K2R b KQkq - 0 5",game.getCompleteFEN());

        game.makeAMove(new Position(3,1),new Position(1,3));
        Assertions.assertEquals("rn2k1nr/ppp1pp1p/8/1b6/3B4/5Q2/PPP2P1P/RN2K2R w KQkq - 0 6",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Knight Movement")
    public void KnightTest1(){
        game = Fen.convertFenToBoard("rn2k1nr/ppp1pp1p/8/1b6/3B4/5Q2/PPP2P1P/RN2K2R w KQkq - 0 6",mockObserver);

        game.makeAMove(new Position(1,7),new Position(2,5));
        Assertions.assertEquals("rn2k1nr/ppp1pp1p/8/1b6/3B4/2N2Q2/PPP2P1P/R3K2R b KQkq - 1 6",game.getCompleteFEN());

        game.makeAMove(new Position(1,0),new Position(2,2));
        Assertions.assertEquals("r3k1nr/ppp1pp1p/2n5/1b6/3B4/2N2Q2/PPP2P1P/R3K2R w KQkq - 2 7",game.getCompleteFEN());

    }

    @Test
    @DisplayName("Knight Capture")
    public void KnightTest2(){
        game = Fen.convertFenToBoard("r3k1nr/ppp1pp1p/2n5/1b6/3B4/2N2Q2/PPP2P1P/R3K2R w KQkq - 2 7",mockObserver);

        game.makeAMove(new Position(2,5),new Position(1,3));
        Assertions.assertEquals("r3k1nr/ppp1pp1p/2n5/1N6/3B4/5Q2/PPP2P1P/R3K2R b KQkq - 0 7",game.getCompleteFEN());

        game.makeAMove(new Position(2,2),new Position(3,4));
        Assertions.assertEquals("r3k1nr/ppp1pp1p/8/1N6/3n4/5Q2/PPP2P1P/R3K2R w KQkq - 0 8",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Rook Movement and making Castling unavailable")
    public void RookTest1(){
        game = Fen.convertFenToBoard("r3k1nr/ppp1pp1p/8/1N6/3n4/5Q2/PPP2P1P/R3K2R w KQkq - 0 8",mockObserver);

        game.makeAMove(new Position(7,7),new Position(6,7));
        Assertions.assertEquals("r3k1nr/ppp1pp1p/8/1N6/3n4/5Q2/PPP2P1P/R3K1R1 b Qkq - 1 8",game.getCompleteFEN());

        game.makeAMove(new Position(0,0),new Position(3,0));
        Assertions.assertEquals("3rk1nr/ppp1pp1p/8/1N6/3n4/5Q2/PPP2P1P/R3K1R1 w Qk - 2 9",game.getCompleteFEN());

        game.makeAMove(new Position(0,7),new Position(3,7));
        Assertions.assertEquals("3rk1nr/ppp1pp1p/8/1N6/3n4/5Q2/PPP2P1P/3RK1R1 b k - 3 8",game.getCompleteFEN());

    }

    @Test
    @DisplayName("Rook Capture and making Castling unavailable")
    public void RookTest2(){
        game = Fen.convertFenToBoard("3rk1nr/ppp1pp1p/8/1N6/3n4/5Q2/PPP2P1P/3RK1R1 w k - 3 8",mockObserver);

        game.makeAMove(new Position(3,7),new Position(3,4));
        Assertions.assertEquals("3rk1nr/ppp1pp1p/8/1N6/3R4/5Q2/PPP2P1P/4K1R1 b k - 0 8",game.getCompleteFEN());

        game.makeAMove(new Position(3,0),new Position(3,4));
        Assertions.assertEquals("4k1nr/ppp1pp1p/8/1N6/3r4/5Q2/PPP2P1P/4K1R1 w k - 0 9",game.getCompleteFEN());

        game.makeAMove(new Position(6,7),new Position(6,0));
        Assertions.assertEquals("4k1Rr/ppp1pp1p/8/1N6/3r4/5Q2/PPP2P1P/4K3 b k - 0 9",game.getCompleteFEN());

        game.makeAMove(new Position(7,0),new Position(6,0));
        Assertions.assertEquals("4k1r1/ppp1pp1p/8/1N6/3r4/5Q2/PPP2P1P/4K3 w - - 0 10",game.getCompleteFEN());
    }

    @Test
    @DisplayName("King Movement and making Castling unavailable")
    public void KingTest1(){
        game = Fen.convertFenToBoard("r3kQ1r/ppp1pp1p/8/1b6/3B4/2N5/PPP2P1P/R3Kn1R b KQkq - 2 7",mockObserver);

        game.makeAMove(new Position(4,0),new Position(3,1));
        Assertions.assertEquals("r4Q1r/pppkpp1p/8/1b6/3B4/2N5/PPP2P1P/R3Kn1R w KQ - 3 8",game.getCompleteFEN());

        game.makeAMove(new Position(4,7),new Position(3,7));
        Assertions.assertEquals("r4Q1r/pppkpp1p/8/1b6/3B4/2N5/PPP2P1P/R2K1n1R b - - 4 8",game.getCompleteFEN());

    }

    @Test
    @DisplayName("King Capture and making Castling unavailable")
    public void KingTest2(){
        game = Fen.convertFenToBoard("r3kQ1r/ppp1pp1p/8/1N6/3B4/8/PPP2P1P/R3Kn1R b KQkq - 2 7",mockObserver);

        game.makeAMove(new Position(4,0),new Position(5,0));
        Assertions.assertEquals("r4k1r/ppp1pp1p/8/1N6/3B4/8/PPP2P1P/R3Kn1R w KQ - 0 8",game.getCompleteFEN());

        game.makeAMove(new Position(4,7),new Position(5,7));
        Assertions.assertEquals("r4k1r/ppp1pp1p/8/1N6/3B4/8/PPP2P1P/R4K1R b - - 0 8",game.getCompleteFEN());
    }

    @Test
    @DisplayName("King Castling king side")
    public void KingTest3(){
        game = Fen.convertFenToBoard("r3k2r/ppp1pp1p/8/1N6/3B4/8/PPP2P1P/R3K2R w KQkq - 2 7",mockObserver);

        game.makeAMove(new Position(4,7),new Position(6,7));
        Assertions.assertEquals("r3k2r/ppp1pp1p/8/1N6/3B4/8/PPP2P1P/R4RK1 b kq - 3 7",game.getCompleteFEN());

        game.makeAMove(new Position(4,0),new Position(6,0));
        Assertions.assertEquals("r4rk1/ppp1pp1p/8/1N6/3B4/8/PPP2P1P/R4RK1 w - - 4 8",game.getCompleteFEN());
    }

    @Test
    @DisplayName("King Castling queen side")
    public void KingTest4(){
        game = Fen.convertFenToBoard("r3k2r/ppp1pp1p/8/1N6/3B4/8/PPP2P1P/R3K2R w KQkq - 2 7",mockObserver);

        game.makeAMove(new Position(4,7),new Position(2,7));
        Assertions.assertEquals("r3k2r/ppp1pp1p/8/1N6/3B4/8/PPP2P1P/2KR3R b kq - 3 7",game.getCompleteFEN());

        game.makeAMove(new Position(4,0),new Position(2,0));
        Assertions.assertEquals("2kr3r/ppp1pp1p/8/1N6/3B4/8/PPP2P1P/2KR3R w - - 4 8",game.getCompleteFEN());
    }
}
