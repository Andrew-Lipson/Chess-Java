package Model;

import Contract.Contract;
import Model.Utilities.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DrawTest {

    private Game game;

    @Mock
    private Contract.Observer mockObserver;


    @Test
    @DisplayName("Insufficient Material: Only Kings")
    public void InsufficientMaterialTest1(){

        game = Fen.convertFenToBoard("8/7k/8/8/8/8/8/7K b - - 0 4",mockObserver);

        game.gameOver();

        verify(mockObserver,times(1)).gameOver(true, "Insufficient Material!");
    }

    @Test
    @DisplayName("Insufficient Material: White King vs Black King & Knight")
    public void InsufficientMaterialTest2(){

        game = Fen.convertFenToBoard("8/6nk/8/8/8/8/8/7K b - - 0 4",mockObserver);

        game.gameOver();

        verify(mockObserver,times(1)).gameOver(true, "Insufficient Material!");
    }

    @Test
    @DisplayName("Insufficient Material: Black King vs White King & Knight")
    public void InsufficientMaterialTest3(){

        game = Fen.convertFenToBoard("8/7k/8/8/8/7N/8/7K b - - 0 4",mockObserver);

        game.gameOver();

        verify(mockObserver,times(1)).gameOver(true, "Insufficient Material!");
    }

    @Test
    @DisplayName("Insufficient Material: White King vs Black King & Bishop")
    public void InsufficientMaterialTest4(){

        game = Fen.convertFenToBoard("8/7k/8/8/8/8/8/6bK b - - 0 4",mockObserver);

        game.gameOver();

        verify(mockObserver,times(1)).gameOver(true, "Insufficient Material!");
    }

    @Test
    @DisplayName("Insufficient Material: Black King vs White King & Bishop")
    public void InsufficientMaterialTest5(){

        game = Fen.convertFenToBoard("8/7k/8/8/8/8/8/6BK b - - 0 4",mockObserver);

        game.gameOver();

        verify(mockObserver,times(1)).gameOver(true, "Insufficient Material!");
    }

    @Test
    @DisplayName("Insufficient Material: Both King & Bishop (on the same colour)")
    public void InsufficientMaterialTest6(){

        game = Fen.convertFenToBoard("7b/7k/8/8/8/8/8/B6K b - - 0 4",mockObserver);

        game.gameOver();

        verify(mockObserver,times(1)).gameOver(true, "Insufficient Material!");
    }

    @Test
    @DisplayName("Sufficient Material: Both King & Bishop (on different colours)")
    public void SufficientMaterialTest1(){

        game = Fen.convertFenToBoard("6b1/7k/8/8/8/8/8/B6K b - - 0 4",mockObserver);

        game.gameOver();

        verify(mockObserver,never()).gameOver(true, "Insufficient Material!");
    }

    @Test
    @DisplayName("Sufficient Material: Both King & Knights")
    public void SufficientMaterialTest2(){

        game = Fen.convertFenToBoard("6N1/7k/8/8/8/8/8/n6K b - - 0 4",mockObserver);

        game.gameOver();

        verify(mockObserver,never()).gameOver(true, "Insufficient Material!");
    }

    @Test
    @DisplayName("Sufficient Material: King vs King & a pawn")
    public void SufficientMaterialTest3(){

        game = Fen.convertFenToBoard("8/7k/8/8/8/P7/8/7K b - - 0 4",mockObserver);

        game.gameOver();

        verify(mockObserver,never()).gameOver(true, "Insufficient Material!");
    }

    @Test
    @DisplayName("Sufficient Material: King & bishop vs King knight")
    public void SufficientMaterialTest4(){

        game = Fen.convertFenToBoard("8/6Nk/8/8/8/8/8/6bK b - - 0 4",mockObserver);

        game.gameOver();

        verify(mockObserver,never()).gameOver(true, "Insufficient Material!");
    }

    @Test
    @DisplayName("Simple repetition")
    public void RepetitionTest1(){

        game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",mockObserver);
        game.gameOver();

        game.makeAMove(new Position(1,7),new Position(0,5));
        game.makeAMove(new Position(1,0),new Position(0,2));
        game.makeAMove(new Position(0,5),new Position(1,7));
        game.makeAMove(new Position(0,2),new Position(1,0));
        game.makeAMove(new Position(1,7),new Position(0,5));
        game.makeAMove(new Position(1,0),new Position(0,2));
        game.makeAMove(new Position(0,5),new Position(1,7));

        verify(mockObserver,never()).gameOver(true, "Repetition!");

        game.makeAMove(new Position(0,2),new Position(1,0));


        verify(mockObserver,times(1)).gameOver(true, "Repetition!");
    }

    @Test
    @DisplayName("Repetition but castling Changed")
    public void RepetitionTest2(){

        game = Fen.convertFenToBoard("r3kbnr/p1pppppp/bqn5/1p6/6P1/7N/PPPPPPBP/RNBQK2R w KQkq - 7 6",mockObserver);
        game.gameOver();

        game.makeAMove(new Position(7,7),new Position(6,7));
        game.makeAMove(new Position(0,0),new Position(2,0));
        game.makeAMove(new Position(6,7),new Position(7,7));
        game.makeAMove(new Position(2,0),new Position(0,0));
        game.makeAMove(new Position(7,7),new Position(6,7));
        game.makeAMove(new Position(0,0),new Position(2,0));
        game.makeAMove(new Position(6,7),new Position(7,7));
        game.makeAMove(new Position(2,0),new Position(0,0));

        verify(mockObserver,never()).gameOver(true, "Repetition!");

        game.makeAMove(new Position(7,7),new Position(6,7));
        game.makeAMove(new Position(0,0),new Position(2,0));

        verify(mockObserver,times(1)).gameOver(true, "Repetition!");
    }

    @Test
    @DisplayName("Repetition but with en Passant at the start")
    public void RepetitionTest3(){

        game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",mockObserver);
        game.gameOver();


        game.makeAMove(new Position(5,6),new Position(5,4)); // en Passant now available


        game.makeAMove(new Position(1,0),new Position(0,2));
        game.makeAMove(new Position(1,7),new Position(0,5));
        game.makeAMove(new Position(0,2),new Position(1,0));
        game.makeAMove(new Position(0,5),new Position(1,7));
        game.makeAMove(new Position(1,0),new Position(0,2));
        game.makeAMove(new Position(1,7),new Position(0,5));
        game.makeAMove(new Position(0,2),new Position(1,0));
        game.makeAMove(new Position(0,5),new Position(1,7));

        verify(mockObserver,never()).gameOver(true, "Repetition!");

        game.makeAMove(new Position(1,0),new Position(0,2));

        verify(mockObserver,times(1)).gameOver(true, "Repetition!");
    }

    @Test
    @DisplayName("50 Moves without Capture or Pawn Promotion")
    public void FiftyMoveTest1(){

        game = Fen.convertFenToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 48 1",mockObserver);
        game.gameOver();

        game.makeAMove(new Position(1,7),new Position(0,5));

        verify(mockObserver,never()).gameOver(true, "50 Rule Move!");

        game.makeAMove(new Position(1,0),new Position(0,2));

        verify(mockObserver,times(1)).gameOver(true, "50 Rule Move!");
    }


}
