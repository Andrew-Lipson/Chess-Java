package Model;

import Utility.CreateBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FenTest {

    @Test
    public void initialisingBoard(){
        // standard initialising
        Game board = new Game(null);
        Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",board.getCompleteFEN());

    }

    @ParameterizedTest
    @DisplayName("Correct Fen input")
    @ValueSource(strings = {
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", // normal starting position
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b Kkq - 0 1", // black moves first
            "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQ c6 0 2", // different position with en Passant
            "8/4npk1/5p1p/1Q5P/1p4P1/4r3/7q/3K1R2 b - - 1 49",
            "5r1k/6pp/4Qpb1/p7/8/6PP/P4PK1/3q4 b - - 4 37",
            "8/8/2P5/4B3/1Q6/4K3/6P1/3k4 w - - 5 67",
            "r2q1rk1/pp2ppbp/2p2np1/6B1/3PP1b1/Q1P2N2/P4PPP/3RKB1R b K d3 0 13"
    })
    public void testingInitialisingWithString(String fen){
        Game board = Fen.convertFenToBoard (fen,null);
        Assertions.assertEquals(fen, board.getCompleteFEN());
    }

    @ParameterizedTest
    @DisplayName("Incorrect Fen input")
    @ValueSource(strings = {
            "rnbqkbnr/pppppppp/8/8/6/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", // 6 files in rank 4
            "rnbqkbnr/pppppppp/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", // 6 files in rank 4
            "rnbqkbnr/pppplppp/8/8/8/8/PPPPPPPP/RNBQKBNR b Kkq - 0 1", // Wrong piece on board
            "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQ a6 0 2", // incorrect en Passant value
            "8/4npk1/5p1p/1Q5P/1p4P1/4r3/7q/3K1R2 b K - 1 49", //incorrect castling
            "5r1k/6pp/4Qpb1/p7/8/6PP/P4PK1/3q4 b - - -1 37", //incorrect halfmoves
            "8/8/2P5/4B3/1Q6/4K3/6P1/3k4 w - - 5 0", //incorrect fullmoves
            "r2q1rk1/pp2ppbp/2p2np1/6B1/3PP1b1/Q1P2N2/P4PPP/3RKB1R h K d3 0 13", //not white or black's move
            "rnbQkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBqKBNR w KQkq - 0 1" //Black in check even if White's turn
    })
    public void testingInitialisingWithString2(String fen){
        Game board = Fen.convertFenToBoard (fen,null);
        Assertions.assertNull(board);
    }



    @Test
    @DisplayName("Opening Board Position")
    public void creatingFen1(){
        Game board = new Game(null, CreateBoard.firstPosition(), null, null, new boolean[]{true, true}, new boolean[]{true, true}, true, null, 0, 1 );
        Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",board.getCompleteFEN());
    }

    @Test
    @DisplayName("rnbqkbnr/1ppppppp/8/p7/8/8/PPPPPPPP/RNBQKBNR, b, KQq, a6")
    public void creatingFen2() {
        Game board = new Game(null, CreateBoard.secondPosition(), null, null, new boolean[]{true, true}, new boolean[]{false, true}, false, new Position(0,2), 5, 10);
        Assertions.assertEquals("rnbqkbnr/1ppppppp/8/p7/8/8/PPPPPPPP/RNBQKBNR b KQq a6 5 10", board.getCompleteFEN());
    }

    @Test
    @DisplayName("rnbqkbnr/1ppppppp/8/p7/1P6/8/P1PPPPPP/RNBQKBNR, w, KQk, b3")
    public void creatingFen3() {
        Game board = new Game(null, CreateBoard.thirdPosition(), null, null, new boolean[]{true, true}, new boolean[]{true, false}, true, new Position(1,5), 0, 1);
        Assertions.assertEquals("rnbqkbnr/1ppppppp/8/p7/1P6/8/P1PPPPPP/RNBQKBNR w KQk b3 0 1", board.getCompleteFEN());
    }

    @Test
    @DisplayName("rnbqkbnr/1p1ppppp/8/p1p5/1P6/8/P1PPPPPP/RNBQKBNR, b, Kkq, c6")
    public void creatingFen4() {
        Game board = new Game(null, CreateBoard.fourthPosition(), null, null, new boolean[]{true, false}, new boolean[]{true, true}, false, new Position(2,2), 0, 1);
        Assertions.assertEquals("rnbqkbnr/1p1ppppp/8/p1p5/1P6/8/P1PPPPPP/RNBQKBNR b Kkq c6 0 1", board.getCompleteFEN());
    }

    @Test
    @DisplayName("rnbqkbnr/1p1ppppp/8/p1p5/1P1P4/8/P1P1PPPP/RNBQKBNR, w, KQ, d3")
    public void creatingFen5() {
        Game board = new Game(null, CreateBoard.fifthPosition(), null, null, new boolean[]{true, true}, new boolean[]{false, false}, true, new Position(3,5), 0, 1);
        Assertions.assertEquals("rnbqkbnr/1p1ppppp/8/p1p5/1P1P4/8/P1P1PPPP/RNBQKBNR w KQ d3 0 1", board.getCompleteFEN());
    }

    @Test
    @DisplayName("rnbqkbnr/1p1p1ppp/8/p1p1p3/1P1P4/8/P1P1PPPP/RNBQKBNR, b, Kk, e6")
    public void creatingFen6(){
        Game board = new Game(null, CreateBoard.sixthPosition(), null, null, new boolean[]{true, false}, new boolean[]{true, false}, false, new Position(4,2), 0, 1 );
        Assertions.assertEquals("rnbqkbnr/1p1p1ppp/8/p1p1p3/1P1P4/8/P1P1PPPP/RNBQKBNR b Kk e6 0 1",board.getCompleteFEN());
    }

    @Test
    @DisplayName("rnbqkbnr/1p1p1ppp/8/p1p1p3/1P1P1P2/8/P1P1P1PP/RNBQKBNR, w, Kq, f3")
    public void creatingFen7() {
        Game board = new Game(null, CreateBoard.seventhPosition(), null, null, new boolean[]{true, false}, new boolean[]{false, true}, true, new Position(5,5), 5, 10);
        Assertions.assertEquals("rnbqkbnr/1p1p1ppp/8/p1p1p3/1P1P1P2/8/P1P1P1PP/RNBQKBNR w Kq f3 5 10", board.getCompleteFEN());
    }

    @Test
    @DisplayName("rnbqkbnr/1p1p1p1p/8/p1p1p1p1/1P1P1P2/8/P1P1P1PP/RNBQKBNR, b, Qk, g6")
    public void creatingFen8() {
        Game board = new Game(null, CreateBoard.eighthPosition(), null, null, new boolean[]{false, true}, new boolean[]{true, false}, false, new Position(6,2), 0, 1);
        Assertions.assertEquals("rnbqkbnr/1p1p1p1p/8/p1p1p1p1/1P1P1P2/8/P1P1P1PP/RNBQKBNR b Qk g6 0 1", board.getCompleteFEN());
    }

    @Test
    @DisplayName("rnbqkbnr/1p1p1p1p/8/p1p1p1p1/1P1P1P1P/8/P1P1P1P1/RNBQKBNR, w, Qkq, h3")
    public void creatingFen9() {
        Game board = new Game(null, CreateBoard.ninthPosition(), null, null, new boolean[]{false, true}, new boolean[]{true, true}, true, new Position(7,5), 0, 1);
        Assertions.assertEquals("rnbqkbnr/1p1p1p1p/8/p1p1p1p1/1P1P1P1P/8/P1P1P1P1/RNBQKBNR w Qkq h3 0 1", board.getCompleteFEN());
    }

    @Test
    @DisplayName("No pieces, b, no castling")
    public void creatingFen10() {
        Game board = new Game(null, CreateBoard.tenthPosition(), null, null, new boolean[]{false, false}, new boolean[]{false, false}, false, null, 0, 1);
        Assertions.assertEquals("8/8/8/8/8/8/8/8 b - - 0 1", board.getCompleteFEN());
    }

    @Test
    @DisplayName("r2q1rk1/pp2ppbp/2p2np1/6B1/3PP1b1/Q1P2N2/P4PPP/3RKB1R, w, K")
    public void creatingFen11(){
        Game board = new Game(null, CreateBoard.eleventhPosition(), null, null, new boolean[]{true, false}, new boolean[]{false, false}, true, null, 0, 1 );
        Assertions.assertEquals("r2q1rk1/pp2ppbp/2p2np1/6B1/3PP1b1/Q1P2N2/P4PPP/3RKB1R w K - 0 1",board.getCompleteFEN());
    }

    @Test
    @DisplayName("Only Pawns, b, no Castling")
    public void creatingFen12() {
        Game board = new Game(null, CreateBoard.twelfthPosition(), null, null, new boolean[]{false, false}, new boolean[]{false, false}, false, null, 5, 10);
        Assertions.assertEquals("pppppppp/pppppppp/pppppppp/pppppppp/PPPPPPPP/PPPPPPPP/PPPPPPPP/PPPPPPPP b - - 5 10", board.getCompleteFEN());
    }

    @Test
    @DisplayName("Only white Queens, w, no Castling")
    public void creatingFen13() {
        Game board = new Game(null, CreateBoard.thirteenthPosition(), null, null, new boolean[]{false, false}, new boolean[]{false, false}, true, null, 0, 1);
        Assertions.assertEquals("QQQQQQQQ/QQQQQQQQ/QQQQQQQQ/QQQQQQQQ/QQQQQQQQ/QQQQQQQQ/QQQQQQQQ/QQQQQQQQ w - - 0 1", board.getCompleteFEN());
    }

    @Test
    @DisplayName("Only black Kings, b, no Castling")
    public void creatingFen14() {
        Game board = new Game(null, CreateBoard.fourteenthPosition(), null, null, new boolean[]{false, false}, new boolean[]{false, false}, false, null, 0, 1);
        Assertions.assertEquals("kkkkkkkk/kkkkkkkk/kkkkkkkk/kkkkkkkk/kkkkkkkk/kkkkkkkk/kkkkkkkk/kkkkkkkk b - - 0 1", board.getCompleteFEN());
    }

    @Test
    @DisplayName("Opening Board Position, w, q")
    public void creatingFen15() {
        Game board = new Game(null, CreateBoard.firstPosition(), null, null, new boolean[]{false, false}, new boolean[]{false, true}, true, null, 0, 1);
        Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w q - 0 1", board.getCompleteFEN());
    }

    @Test
    @DisplayName("Opening Board Position, b, Q")
    public void creatingFen16() {
        Game board = new Game(null, CreateBoard.firstPosition(), null, null, new boolean[]{false, true}, new boolean[]{false, false}, false, null, 0, 1);
        Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b Q - 0 1", board.getCompleteFEN());
    }



}