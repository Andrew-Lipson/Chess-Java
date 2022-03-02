package Model;

import Model.Utilities.Fen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

public class PossibleMovesTest {


    @Test
    public void queenMovesTest(){
        Game game = Fen.convertFenToBoard("8/5P2/2q5/3Q3n/8/3B4/8/8 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{2,2},{4,2},{2,4},{1,5},{0,6},{4,4},{5,5},{6,6},{7,7},{3,2},{3,1},{3,0},{2,3},{1,3},{0,3},{3,4},{4,3},{5,3},{6,3},{7,3}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(3,3));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void rookMovesTest(){
        Game game = Fen.convertFenToBoard("8/8/8/8/k3r3/4P3/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{4,3},{4,2},{4,1},{4,0},{3,4},{2,4},{1,4},{4,5},{5,4},{6,4},{7,4}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,4));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void bishopMovesTest(){
        Game game = Fen.convertFenToBoard("8/6n1/8/8/3B4/8/8/6N1 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{2,3},{1,2},{0,1},{4,3},{5,2},{6,1},{2,5},{1,6},{0,7},{4,5},{5,6}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(3,4));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void knightMovesTest(){
        Game game = Fen.convertFenToBoard("8/3P4/8/4n3/6N1/3B4/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,1},{5,1},{2,2},{6,2},{2,4},{6,4},{3,5},{5,5}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,3));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void kingMovesTest(){
        Game game = Fen.convertFenToBoard("8/8/8/2p1p3/3K4/2ppp3/8/8 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{2,3},{3,3},{4,3},{2,4},{4,4},{2,5},{3,5},{4,5}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(3,4));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void pawnMovesCaptureTest(){
        Game game = Fen.convertFenToBoard("8/8/8/4p3/3NNB2/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,4},{5,4}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,3));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void pawnMovesDoubleTest(){
        Game game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",null);
        int[][] expectedMoves = new int[][] {{4,5},{4,4}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,6));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void pawnMovesEnPassantTest(){
        Game game = Fen.convertFenToBoard("rnbqkbnr/ppp1pppp/8/8/3Pp3/5N2/PPP2PPP/RNBQKB1R b KQkq d3 0 3",null);
        int[][] expectedMoves = new int[][] {{3,5},{4,5},{5,5}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,4));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @ParameterizedTest
    @DisplayName("White pieces unable to move")
    @ValueSource(ints= {0,1,2,3,4,5,6,7})
    public void unableToMoveTest1(int file){
        Game game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/p1p2p1p/8/8/P1P2P1P/PPPPPPPP/RNBQKBNR w KQkq - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(file,7));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @ParameterizedTest
    @DisplayName("Black pieces unable to move")
    @ValueSource(ints= {0,1,2,3,4,5,6,7})
    public void unableToMoveTest2(int file){
        Game game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/p1p2p1p/8/8/P1P2P1P/PPPPPPPP/RNBQKBNR w KQkq - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(file,0));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }


    @ParameterizedTest
    @DisplayName("Black pieces unable to move as King in Check")
    @ValueSource(ints= {0,1,2,3,4,5,6,7})
    public void kingInCheckTest1(int file){
        Game game = Fen.convertFenToBoard("8/8/8/rnnrqbbp/8/8/8/4k2R b - - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(file,3));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("King only has one spot not in Check")
    public void kingLimitedMovesTest1(){
        Game game = Fen.convertFenToBoard("2b5/5q2/8/4K3/r7/8/8/8 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,2}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,3));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("King must capture to not be in Check")
    public void kingLimitedMovesTest2(){
        Game game = Fen.convertFenToBoard("6qK/6pp/8/8/8/8/8/8 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{6,0}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(7,0));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("King must cover behind piece to not be in Check")
    public void kingLimitedMovesTest3(){
        Game game = Fen.convertFenToBoard("8/8/8/2pk4/8/8/8/2RRR3 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{2,2}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(3,3));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Queen must take or get in way to stop Check")
    public void queenStopCheckMovesTest1(){
        Game game = Fen.convertFenToBoard("8/5q2/8/k6R/8/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{7,3},{5,3},{3,3}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(5,1));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Bishop must take or get in way to stop Check")
    public void bishopStopCheckMovesTest1(){
        Game game = Fen.convertFenToBoard("8/5b2/8/k6R/8/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{7,3},{3,3}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(5,1));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Knight must take or get in way to stop Check")
    public void knightStopCheckMovesTest1(){
        Game game = Fen.convertFenToBoard("8/8/5n2/k6R/8/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{7,3},{3,3}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(5,2));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Pawn must take or get in way to stop Check")
    public void pawnStopCheckMovesTest1(){
        Game game = Fen.convertFenToBoard("8/6p1/k6R/8/8/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{7,2},{6,2}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(6,1));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Rook must take or get in way to stop Check")
    public void rookStopCheckMovesTest1(){
        Game game = Fen.convertFenToBoard("3B4/8/3r4/k7/8/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,0},{1,2}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(3,2));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Queen is Pinned")
    public void queenPinnedMovesTest1(){
        Game game = Fen.convertFenToBoard("3q4/8/8/8/8/8/3Q4/3K4 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,0},{3,1},{3,2},{3,3},{3,4},{3,5}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(3,6));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Bishop is Pinned")
    public void bishopPinnedMovesTest1(){
        Game game = Fen.convertFenToBoard("3q4/8/8/8/3B4/8/8/3K4 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(3,4));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Knight is Pinned")
    public void knightPinnedMovesTest1(){
        Game game = Fen.convertFenToBoard("3q4/8/3N4/8/8/8/8/3K4 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(3,2));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Pawn is Pinned")
    public void pawnPinnedMovesTest1(){
        Game game = Fen.convertFenToBoard("8/8/8/7b/8/8/4P3/3K4 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,6));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Rook is Pinned")
    public void rookPinnedMovesTest1(){
        Game game = Fen.convertFenToBoard("8/8/8/7b/8/8/4R3/3K4 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,6));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("White king with both castling options available")
    public void KingWithCastlingTest1(){
        Game game = Fen.convertFenToBoard("r3k2r/ppp1pp1p/7n/1N6/3n4/5Q2/PPP2P1P/R3K2R w KQkq - 0 6",null);
        int[][] expectedMoves = new int[][] {{2,7},{3,7},{5,7},{6,7},{3,6}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,7));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Black king with both castling options available")
    public void KingWithCastlingTest2(){
        Game game = Fen.convertFenToBoard("r3k2r/ppp1pp1p/7n/1N6/3n4/5Q2/PPP2P1P/R3K2R b KQkq - 0 6",null);
        int[][] expectedMoves = new int[][] {{2,0},{3,0},{5,0},{6,0},{3,1}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,0));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("White king with neither castling options available")
    public void KingWithCastlingTest3(){
        Game game = Fen.convertFenToBoard("r3k2r/ppp1pp1p/7n/1N6/3n4/5Q2/PPP2P1P/R3K2R w - - 0 6",null);
        int[][] expectedMoves = new int[][] {{3,7},{5,7},{3,6}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,7));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Black king with neither castling options available")
    public void KingWithCastlingTest4(){
        Game game = Fen.convertFenToBoard("r3k2r/ppp1pp1p/7n/1N6/3n4/5Q2/PPP2P1P/R3K2R b - - 0 6",null);
        int[][] expectedMoves = new int[][] {{3,0},{5,0},{3,1}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,0));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("White king with both castling options available but illegal")
    public void KingWithCastlingTest5(){
        Game game = Fen.convertFenToBoard("r3k2r/ppp1pp1p/7n/1N6/6Q1/4n3/PPP2P1P/R3K2R w KQkq - 0 6",null);
        int[][] expectedMoves = new int[][] {{3,6},{4,6}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,7));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Black king with both castling options available but illegal")
    public void KingWithCastlingTest6(){
        Game game = Fen.convertFenToBoard("r3k2r/ppp1pp1p/7n/1N6/6Q1/4n3/PPP2P1P/R3K2R b KQkq - 0 6",null);
        int[][] expectedMoves = new int[][] {{3,0},{5,0}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,0));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("White king with both castling options available but pieces in the way")
    public void KingWithCastlingTest7(){
        Game game = Fen.convertFenToBoard("rn2k1Br/ppp1pp1p/8/1N6/8/8/PPP2P1P/Rn2KQ1R w KQkq - 0 6",null);
        int[][] expectedMoves = new int[][] {{3,7},{4,6}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,7));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Black king with both castling options available but pieces in the way")
    public void KingWithCastlingTest8(){
        Game game = Fen.convertFenToBoard("rn2k1Br/ppp1pp1p/8/1N6/8/8/PPP2P1P/Rn2KQ1R b KQkq - 0 6",null);
        int[][] expectedMoves = new int[][] {{3,0},{5,0},{3,1}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,0));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("White king with both castling options available but in check")
    public void KingWithCastlingTest9(){
        Game game = Fen.convertFenToBoard("rnb1kbnr/pppp1ppp/8/4p3/7q/5P2/PPPPP1PP/R3K2R w KQkq - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,7},{5,7}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,7));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Black king with both castling options available but in check")
    public void KingWithCastlingTest10(){
        Game game = Fen.convertFenToBoard("r3k2r/pppp2pp/5p2/4p2Q/8/5P2/PPPPP1PP/R4K1R b kq - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,0},{5,0},{4,1}};
        ArrayList<Position> actualMoves = game.getLegalMoves(new Position(4,0));
        checkForCorrectPositions(actualMoves, expectedMoves);
    }


    private void checkForCorrectPositions(ArrayList<Position> actualMoves, int[][] expectedMoves){
        Assertions.assertEquals(expectedMoves.length,actualMoves.size());
        for (int[] move:expectedMoves) {
            Assertions.assertTrue(actualMoves.stream().anyMatch(position -> position.getFile()==move[0] && position.getRank()==move[1]));
        }
    }
}
