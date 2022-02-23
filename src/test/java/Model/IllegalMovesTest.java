package Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

public class IllegalMovesTest {

    @ParameterizedTest
    @DisplayName("Black pieces unable to move as King in Check")
    @ValueSource(ints= {0,1,2,3,4,5,6,7})
    public void kingInCheckTest1(int file){
        Game game = Fen.convertFenToBoard("8/8/8/rnnrqbbp/8/8/8/4k2R b - - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(file,3),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("King only has one spot not in Check")
    public void kingLimitedMovesTest1(){
        Game game = Fen.convertFenToBoard("2b5/5q2/8/4K3/r7/8/8/8 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,2}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(4,3),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("King must capture to not be in Check")
    public void kingLimitedMovesTest2(){
        Game game = Fen.convertFenToBoard("6qK/6pp/8/8/8/8/8/8 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{6,0}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(7,0),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("King must cover behind piece to not be in Check")
    public void kingLimitedMovesTest3(){
        Game game = Fen.convertFenToBoard("8/8/8/2pk4/8/8/8/2RRR3 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{2,2}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(3,3),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Queen must take or get in way to stop Check")
    public void queenStopCheckMovesTest1(){
        Game game = Fen.convertFenToBoard("8/5q2/8/k6R/8/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{7,3},{5,3},{3,3}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(5,1),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Bishop must take or get in way to stop Check")
    public void bishopStopCheckMovesTest1(){
        Game game = Fen.convertFenToBoard("8/5b2/8/k6R/8/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{7,3},{3,3}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(5,1),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Knight must take or get in way to stop Check")
    public void knightStopCheckMovesTest1(){
        Game game = Fen.convertFenToBoard("8/8/5n2/k6R/8/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{7,3},{3,3}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(5,2),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Pawn must take or get in way to stop Check")
    public void pawnStopCheckMovesTest1(){
        Game game = Fen.convertFenToBoard("8/6p1/k6R/8/8/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{7,2},{6,2}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(6,1),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Rook must take or get in way to stop Check")
    public void rookStopCheckMovesTest1(){
        Game game = Fen.convertFenToBoard("3B4/8/3r4/k7/8/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,0},{1,2}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(3,2),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Queen is Pinned")
    public void queenPinnedMovesTest1(){
        Game game = Fen.convertFenToBoard("3q4/8/8/8/8/8/3Q4/3K4 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,0},{3,1},{3,2},{3,3},{3,4},{3,5}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(3,6),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Bishop is Pinned")
    public void bishopPinnedMovesTest1(){
        Game game = Fen.convertFenToBoard("3q4/8/8/8/3B4/8/8/3K4 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(3,4),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Knight is Pinned")
    public void knightPinnedMovesTest1(){
        Game game = Fen.convertFenToBoard("3q4/8/3N4/8/8/8/8/3K4 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(3,2),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Pawn is Pinned")
    public void pawnPinnedMovesTest1(){
        Game game = Fen.convertFenToBoard("8/8/8/7b/8/8/4P3/3K4 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(4,6),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    @DisplayName("Rook is Pinned")
    public void rookPinnedMovesTest1(){
        Game game = Fen.convertFenToBoard("8/8/8/7b/8/8/4R3/3K4 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(4,6),game,true);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }


    private void checkForCorrectPositions(ArrayList<Position> actualMoves, int[][] expectedMoves){
        Assertions.assertEquals(expectedMoves.length,actualMoves.size());
        for (int[] move:expectedMoves) {
            Assertions.assertTrue(actualMoves.stream().anyMatch(position -> position.getFile()==move[0] && position.getRank()==move[1]));
        }
    }
}
