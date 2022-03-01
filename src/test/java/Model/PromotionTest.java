package Model;

import Contract.Contract;
import Model.Pieces.PieceType;
import Model.Utilities.Fen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromotionTest {

    @Mock
    private Contract.Observer mockObserver;

    @Test
    @DisplayName("Pawn promotion to Queen, forward move")
    public void promotionTest1(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 w - - 0 1",mockObserver);
        when(mockObserver.pawnPromotion(Mockito.anyBoolean())).thenReturn("q");

        game.makeAMove(new Position(3,1),new Position(3,0));
        Assertions.assertEquals("3Qn2k/8/8/8/K7/8/3p4/2Q5 b - - 0 1",game.getCompleteFEN());

        game.makeAMove(new Position(3,6),new Position(3,7));
        Assertions.assertEquals("3Qn2k/8/8/8/K7/8/8/2Qq4 w - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Rook, forward move")
    public void promotionTest2(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 w - - 0 1",mockObserver);
        when(mockObserver.pawnPromotion(Mockito.anyBoolean())).thenReturn("r");

        game.makeAMove(new Position(3,1),new Position(3,0));
        Assertions.assertEquals("3Rn2k/8/8/8/K7/8/3p4/2Q5 b - - 0 1",game.getCompleteFEN());

        game.makeAMove(new Position(3,6),new Position(3,7));
        Assertions.assertEquals("3Rn2k/8/8/8/K7/8/8/2Qr4 w - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Bishop, forward move")
    public void promotionTest3(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 w - - 0 1",mockObserver);
        when(mockObserver.pawnPromotion(Mockito.anyBoolean())).thenReturn("b");

        game.makeAMove(new Position(3,1),new Position(3,0));
        Assertions.assertEquals("3Bn2k/8/8/8/K7/8/3p4/2Q5 b - - 0 1",game.getCompleteFEN());

        game.makeAMove(new Position(3,6),new Position(3,7));
        Assertions.assertEquals("3Bn2k/8/8/8/K7/8/8/2Qb4 w - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Knight, forward move")
    public void promotionTest4(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 w - - 0 1",mockObserver);
        when(mockObserver.pawnPromotion(Mockito.anyBoolean())).thenReturn("n");

        game.makeAMove(new Position(3,1),new Position(3,0));
        Assertions.assertEquals("3Nn2k/8/8/8/K7/8/3p4/2Q5 b - - 0 1",game.getCompleteFEN());

        game.makeAMove(new Position(3,6),new Position(3,7));
        Assertions.assertEquals("3Nn2k/8/8/8/K7/8/8/2Qn4 w - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Queen, forward move")
    public void promotionTest5(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 b - - 0 1",mockObserver);
        when(mockObserver.pawnPromotion(Mockito.anyBoolean())).thenReturn("q");

        game.makeAMove(new Position(3,6),new Position(2,7));
        Assertions.assertEquals("4n2k/3P4/8/8/K7/8/8/2q5 w - - 0 2",game.getCompleteFEN());

        game.makeAMove(new Position(3,1),new Position(4,0));
        Assertions.assertEquals("4Q2k/8/8/8/K7/8/8/2q5 b - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Rook, forward move")
    public void promotionTest6(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 b - - 0 1",mockObserver);
        when(mockObserver.pawnPromotion(Mockito.anyBoolean())).thenReturn("r");

        game.makeAMove(new Position(3,6),new Position(2,7));
        Assertions.assertEquals("4n2k/3P4/8/8/K7/8/8/2r5 w - - 0 2",game.getCompleteFEN());

        game.makeAMove(new Position(3,1),new Position(4,0));
        Assertions.assertEquals("4R2k/8/8/8/K7/8/8/2r5 b - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Bishop, forward move")
    public void promotionTest7(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 b - - 0 1",mockObserver);
        when(mockObserver.pawnPromotion(Mockito.anyBoolean())).thenReturn("b");

        game.makeAMove(new Position(3,6),new Position(2,7));
        Assertions.assertEquals("4n2k/3P4/8/8/K7/8/8/2b5 w - - 0 2",game.getCompleteFEN());

        game.makeAMove(new Position(3,1),new Position(4,0));
        Assertions.assertEquals("4B2k/8/8/8/K7/8/8/2b5 b - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Knight, forward move")
    public void promotionTest8(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 b - - 0 1",mockObserver);
        when(mockObserver.pawnPromotion(Mockito.anyBoolean())).thenReturn("n");

        game.makeAMove(new Position(3,6),new Position(2,7));
        Assertions.assertEquals("4n2k/3P4/8/8/K7/8/8/2n5 w - - 0 2",game.getCompleteFEN());

        game.makeAMove(new Position(3,1),new Position(4,0));
        Assertions.assertEquals("4N2k/8/8/8/K7/8/8/2n5 b - - 0 2",game.getCompleteFEN());
    }
}
