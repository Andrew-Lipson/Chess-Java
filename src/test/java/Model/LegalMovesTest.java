package Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class LegalMovesTest {

    @Test
    public void queenMovesTest(){
        Game game = Fen.convertFenToBoard("8/5P2/2q5/3Q3n/8/3B4/8/8 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{2,2},{4,2},{2,4},{1,5},{0,6},{4,4},{5,5},{6,6},{7,7},{3,2},{3,1},{3,0},{2,3},{1,3},{0,3},{3,4},{4,3},{5,3},{6,3},{7,3}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(3,3),game);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void rookMovesTest(){
        Game game = Fen.convertFenToBoard("8/8/8/8/k3r3/4P3/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{4,3},{4,2},{4,1},{4,0},{3,4},{2,4},{1,4},{4,5},{5,4},{6,4},{7,4}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(4,4),game);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void bishopMovesTest(){
        Game game = Fen.convertFenToBoard("8/6n1/8/8/3B4/8/8/6N1 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{2,3},{1,2},{0,1},{4,3},{5,2},{6,1},{2,5},{1,6},{0,7},{4,5},{5,6}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(3,4),game);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void knightMovesTest(){
        Game game = Fen.convertFenToBoard("8/3P4/8/4n3/6N1/3B4/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,1},{5,1},{2,2},{6,2},{2,4},{6,4},{3,5},{5,5}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(4,3),game);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void kingMovesTest(){
        Game game = Fen.convertFenToBoard("8/8/8/2p1p3/3K4/2ppp3/8/8 w - - 0 1",null);
        int[][] expectedMoves = new int[][] {{2,3},{3,3},{4,3},{2,4},{4,4},{2,5},{3,5},{4,5}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(3,4),game);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void pawnMovesCaptureTest(){
        Game game = Fen.convertFenToBoard("8/8/8/4p3/3NNB2/8/8/8 b - - 0 1",null);
        int[][] expectedMoves = new int[][] {{3,4},{5,4}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(4,3),game);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void pawnMovesDoubleTest(){
        Game game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",null);
        int[][] expectedMoves = new int[][] {{4,5},{4,4}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(4,6),game);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @Test
    public void pawnMovesEnPassantTest(){
        Game game = Fen.convertFenToBoard("rnbqkbnr/ppp1pppp/8/8/3Pp3/5N2/PPP2PPP/RNBQKB1R b KQkq d3 0 3",null);
        int[][] expectedMoves = new int[][] {{3,5},{4,5},{5,5}};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(4,4),game);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }


    @ParameterizedTest
    @DisplayName("White pieces unable to move")
    @ValueSource(ints= {0,1,2,3,4,5,6,7})
    public void unableToMoveTest1(int file){
        Game game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/p1p2p1p/8/8/P1P2P1P/PPPPPPPP/RNBQKBNR w KQkq - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(file,7),game);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    @ParameterizedTest
    @DisplayName("Black pieces unable to move")
    @ValueSource(ints= {0,1,2,3,4,5,6,7})
    public void unableToMoveTest2(int file){
        Game game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/p1p2p1p/8/8/P1P2P1P/PPPPPPPP/RNBQKBNR w KQkq - 0 1",null);
        int[][] expectedMoves = new int[][] {};
        ArrayList<Position> actualMoves = Moves.chooseMove(new Position(file,0),game);
        checkForCorrectPositions(actualMoves, expectedMoves);
    }

    private void checkForCorrectPositions(ArrayList<Position> actualMoves, int[][] expectedMoves){
        Assertions.assertEquals(expectedMoves.length,actualMoves.size());
        for (int[] move:expectedMoves) {
            Assertions.assertTrue(actualMoves.stream().anyMatch(position -> position.getFile()==move[0] && position.getRank()==move[1]));
        }
    }

}