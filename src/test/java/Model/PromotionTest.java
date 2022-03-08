package Model;

import Contract.Contract;
import Model.Pieces.PieceType;
import Model.Utilities.Fen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromotionTest {

    @Mock
    private Contract.Observer mockObserver;

    @Test
    @DisplayName("Test that when a pawn moves to a promotion spot, the observer is called to promote")
    public void promotionRecognisedCorrectly() {
        // given
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 w - - 0 1",mockObserver);

        // when
        game.makeAMove(new Position(3,1),new Position(3,0));

        // then
        verify(mockObserver, times(1)).displayPromotionPopup();
    }

    @Test
    @DisplayName("Test that when a pawn moves to a non promotion spot, the obserer is not called to promote")
    public void promotionNotRecognisedCorrectly() {
        // given
        // TODO: provide a position and make a move where the promotion should not take place
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/KB6/8/3p4/2Q5 w - - 0 1",mockObserver);

        // when
        game.makeAMove(new Position(2,4),new Position(2,3));

        // then
        verify(mockObserver, never()).displayPromotionPopup();
    }

    @Test
    @DisplayName("After promoting a piece, new FEN correctly generated")
    public void promotionTest1(){
        // given
        // TODO: make this FEN place the pawn on the promotion square
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 w - - 0 1",mockObserver);

        /**
         * no need to call `makeAMove()` here, this has already been tested that it correctly
         * calls the observer in the earlier tests. Once the observer is called, the view handles selecting promotion,
         * and we just now want to test the handling of the selected promotion
         *
         * If you wanted to test what happens in between, that would need to be handled by testing the view itself.
         * to including it here is in the same way you have not tested handlePieceClicked() or handleCircleClicked().
         *
         * Another option is to just merge the tests above into these tests, set the FEN at the top to the position pre promotion
         * and call `makeAMove()` here, followed by `game.promotionPieceDecision(PieceType.Queen)`. Then at the end of the
         * test, verify the observer was called however many times you expect, and with which colours.
         */
        game.makeAMove(new Position(3,1),new Position(3,0));
        game.promotionPieceDecision(PieceType.Queen);
        Assertions.assertEquals("3Qn2k/8/8/8/K7/8/3p4/2Q5 b - - 0 1",game.getCompleteFEN());

        /**
         * Using this approach, you would not test a second move.. However if using last option mentioned
         * above where you test it all in one big test, you can do that.
         */
        game.makeAMove(new Position(3,6),new Position(3,7));
        game.promotionPieceDecision(PieceType.Queen);
        Assertions.assertEquals("3Qn2k/8/8/8/K7/8/8/2Qq4 w - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Rook, forward move")
    public void promotionTest2(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 w - - 0 1",mockObserver);

        game.makeAMove(new Position(3,1),new Position(3,0));
        game.promotionPieceDecision(PieceType.Rook);
        Assertions.assertEquals("3Rn2k/8/8/8/K7/8/3p4/2Q5 b - - 0 1",game.getCompleteFEN());

        game.makeAMove(new Position(3,6),new Position(3,7));
        game.promotionPieceDecision(PieceType.Rook);
        Assertions.assertEquals("3Rn2k/8/8/8/K7/8/8/2Qr4 w - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Bishop, forward move")
    public void promotionTest3(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 w - - 0 1",mockObserver);


        game.makeAMove(new Position(3,1),new Position(3,0));
        game.promotionPieceDecision(PieceType.Bishop);
        Assertions.assertEquals("3Bn2k/8/8/8/K7/8/3p4/2Q5 b - - 0 1",game.getCompleteFEN());

        game.makeAMove(new Position(3,6),new Position(3,7));
        game.promotionPieceDecision(PieceType.Bishop);
        Assertions.assertEquals("3Bn2k/8/8/8/K7/8/8/2Qb4 w - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Knight, forward move")
    public void promotionTest4(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 w - - 0 1",mockObserver);

        game.makeAMove(new Position(3,1),new Position(3,0));
        game.promotionPieceDecision(PieceType.Knight);
        Assertions.assertEquals("3Nn2k/8/8/8/K7/8/3p4/2Q5 b - - 0 1",game.getCompleteFEN());

        game.makeAMove(new Position(3,6),new Position(3,7));
        game.promotionPieceDecision(PieceType.Knight);
        Assertions.assertEquals("3Nn2k/8/8/8/K7/8/8/2Qn4 w - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Queen, via capture")
    public void promotionTest5(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 b - - 0 1",mockObserver);

        game.makeAMove(new Position(3,6),new Position(2,7));
        game.promotionPieceDecision(PieceType.Queen);
        Assertions.assertEquals("4n2k/3P4/8/8/K7/8/8/2q5 w - - 0 2",game.getCompleteFEN());

        game.makeAMove(new Position(3,1),new Position(4,0));
        game.promotionPieceDecision(PieceType.Queen);
        Assertions.assertEquals("4Q2k/8/8/8/K7/8/8/2q5 b - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Rook, via capture")
    public void promotionTest6(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 b - - 0 1",mockObserver);

        game.makeAMove(new Position(3,6),new Position(2,7));
        game.promotionPieceDecision(PieceType.Rook);
        Assertions.assertEquals("4n2k/3P4/8/8/K7/8/8/2r5 w - - 0 2",game.getCompleteFEN());

        game.makeAMove(new Position(3,1),new Position(4,0));
        game.promotionPieceDecision(PieceType.Rook);
        Assertions.assertEquals("4R2k/8/8/8/K7/8/8/2r5 b - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Bishop, via capture")
    public void promotionTest7(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 b - - 0 1",mockObserver);

        game.makeAMove(new Position(3,6),new Position(2,7));
        game.promotionPieceDecision(PieceType.Bishop);
        Assertions.assertEquals("4n2k/3P4/8/8/K7/8/8/2b5 w - - 0 2",game.getCompleteFEN());

        game.makeAMove(new Position(3,1),new Position(4,0));
        game.promotionPieceDecision(PieceType.Bishop);
        Assertions.assertEquals("4B2k/8/8/8/K7/8/8/2b5 b - - 0 2",game.getCompleteFEN());
    }

    @Test
    @DisplayName("Pawn promotion to Knight, via capture")
    public void promotionTest8(){
        Game game = Fen.convertFenToBoard("4n2k/3P4/8/8/K7/8/3p4/2Q5 b - - 0 1",mockObserver);

        game.makeAMove(new Position(3,6),new Position(2,7));
        game.promotionPieceDecision(PieceType.Knight);
        Assertions.assertEquals("4n2k/3P4/8/8/K7/8/8/2n5 w - - 0 2",game.getCompleteFEN());

        game.makeAMove(new Position(3,1),new Position(4,0));
        game.promotionPieceDecision(PieceType.Knight);
        Assertions.assertEquals("4N2k/8/8/8/K7/8/8/2n5 b - - 0 2",game.getCompleteFEN());
    }
}
