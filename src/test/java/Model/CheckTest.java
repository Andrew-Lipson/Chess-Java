package Model;

import Model.Utilities.Check;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class CheckTest {

    @ParameterizedTest
    @DisplayName("Checkmate")
    @ValueSource(strings = {
            "3b1q1q/1N2PRQ1/rR3KBr/B4PP1/2Pk1r1b/1P2P1N1/2P2P2/8 b - - 5 5",
            "1nb2b1n/rp4r1/2B5/p2p1pP1/1p1Pp1Pp/1RP4K/P1k1Q3/R1B3N1 b - d3 0 38",
            "k7/8/8/8/1R6/Q7/8/K7 b - - 0 1",
            "k7/8/8/R7/1R6/8/8/K7 b - - 0 1",
            "kr6/ppN5/8/8/8/8/8/K7 b - - 0 1",
            "kr6/pp6/1N6/8/8/8/R7/K7 b - - 0 1",
            "rnb1kbnr/pppp1ppp/8/4p3/6Pq/5P2/PPPPP2P/RNBQKBNR w KQkq - 0 1",
            "rnb1k1nr/pppp1ppp/8/2b1p3/4P3/2NP1N2/PPP2qPP/R1BQKB1R w KQkq - 0 1",
            "6bb/7b/8/8/8/8/8/K6k w - - 0 1",
            "8/2p5/Pp6/KP6/PP6/8/8/k7 w - - 0 1",
            "8/2p5/Pp6/KP6/NP6/8/8/qk6 w - - 0 1",
            "rnbq2nr/ppp1pp1p/5bp1/5k2/2BpPP2/5Q2/PPPP2PP/RNB1K1NR b KQ - 0 3",
            "1k6/8/8/8/4Q2b/3n4/3PP3/3RKN2 w - - 0 1"
    })
    public void CheckMateTest(String fen){
        Game game = Fen.convertFenToBoard (fen,null);
        Assertions.assertTrue(Check.isTheKingUnderAttack(game,game.getWhitesTurn()));
        Assertions.assertTrue(Check.isThereALegalMove(game, game.getWhitesTurn()));
    }

    @ParameterizedTest
    @DisplayName("Check")
    @ValueSource(strings = {
            "kr6/pp6/1N6/8/8/8/8/K7 b - - 0 1",
            "r1BKB1Nr/2PP4/4P3/q7/7b/6n1/3n4/k7 w - - 0 1",
            "rnbq2nr/ppp1pp1p/5bp1/5k2/2BpPP2/5Q2/PPPP2PP/RNB1K1NR b KQ e3 0 3",
            "k7/8/8/8/3p4/4K3/8/8 w - - 0 1",
            "k7/8/7b/3n2Q1/8/4K3/8/8 w - - 0 1",
            "3k4/8/8/8/4Q2b/8/3PP3/3RKN2 w - - 0 1",
            "k6R/8/8/8/8/8/K7/7q b - - 0 1"
    })
    public void CheckTest(String fen){
        Game game = Fen.convertFenToBoard (fen,null);
        Assertions.assertTrue(Check.isTheKingUnderAttack(game,game.getWhitesTurn()));
        Assertions.assertFalse(Check.isThereALegalMove(game, game.getWhitesTurn()));
    }

    @ParameterizedTest
    @DisplayName("StaleMate")
    @ValueSource(strings = {
            "k7/7R/8/8/8/8/8/KR6 b - - 0 1",
            "3k4/8/4Q3/8/8/8/K6B/8 b - - 0 1",
            "k7/P7/1Q6/3p1p1p/3P1P1P/8/8/K7 b - - 0 1",
            "7K/8/5n2/7n/8/8/8/7k w - - 0 1",
            "r1BKB2r/2PPP3/8/q7/7b/6n1/3n4/k7 w - - 0 1",
            "r1BKB2r/2PPP3/3p4/q7/7b/6n1/3n4/k7 w - - 0 1",
            "k6n/5p2/3N1Pp1/6P1/8/4B3/8/KR6 b - - 0 1"

    })
    public void StalemateTest(String fen){
        Game game = Fen.convertFenToBoard (fen,null);
        Assertions.assertFalse(Check.isTheKingUnderAttack(game,game.getWhitesTurn()));
        Assertions.assertTrue(Check.isThereALegalMove(game, game.getWhitesTurn()));
    }

    @ParameterizedTest
    @DisplayName("Normal")
    @ValueSource(strings = {
            "k7/P7/P7/3p1p1p/3P1P1P/8/8/K7 b - - 0 1",
            "rnbqkb1r/pppp1ppp/4pn2/8/2PP4/6P1/PP2PP1P/RNBQKBNR b KQkq - 0 3",
            "8/7k/6pP/6P1/8/p1p5/8/1K6 b - - 0 1",
            "8/2bB4/2P5/6k1/4K3/5P2/8/8 w - - 0 1",
            "rnbqkb1r/pppppp1p/5np1/8/2PP4/8/PP2PPPP/RNBQKBNR w KQkq - 0 3",
            "k7/8/8/8/4p3/4K3/8/8 w - - 0 1"

    })
    public void NormalTest(String fen){
        Game game = Fen.convertFenToBoard (fen,null);
        Assertions.assertFalse(Check.isTheKingUnderAttack(game,game.getWhitesTurn()));
        Assertions.assertFalse(Check.isThereALegalMove(game, game.getWhitesTurn()));
    }
}
