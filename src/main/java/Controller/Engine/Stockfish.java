package Controller.Engine;

import Model.Game;
import Model.Pieces.PieceType;
import Model.Utilities.Position;
import javafx.application.Platform;

import java.io.*;
import java.util.Objects;

import static java.util.Objects.isNull;

public class Stockfish {

    private final Game game;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    /**
     * Constructs a Stockfish instance that has a connection with a Stockfish application called "stockfish.exe"
     *
     * @param game The game that stockfish will be making moves on
     */
    public Stockfish(Game game) {
        this.game = game;
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("Stockfish/stockfish.exe");
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();
            this.bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Stockfish to decide what the best move is and to update the Game
     */
    public void stockfishsTurn() {
        StockFishOutput stockFishOutput = getStockfishMove();
        Platform.runLater(() -> stockfishToMakeAMove(stockFishOutput)); // to run on the UI thread
    }

    /**
     * Communicate with the stockfish application and get the best move for this.game
     *
     * @return the output from stockfish
     */
    private StockFishOutput getStockfishMove() {
        try {
            bufferedWriter.write("position fen " + game.getFullFen() +"\n");
            bufferedWriter.write("go movetime 500\n");
            bufferedWriter.flush();

            String line;
            while (true) {
                line = bufferedReader.readLine();
                String[] str = line.split(" ");
                if (Objects.equals(str[0], "bestmove")) {
                    char[] chars = str[1].toCharArray();
                    Position previousPosition = changeToPosition(chars[0],chars[1]);
                    Position newPosition = changeToPosition(chars[2],chars[3]);
                    if (chars.length==4) {
                        return new StockFishOutput(previousPosition, newPosition);
                    } else{
                        PieceType pieceType = PieceType.getPieceType(Character.toString(chars[4]));
                        return new StockFishOutput(previousPosition, newPosition, pieceType);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Make the move on the Game
     *
     * @param stockFishOutput stockfish's output
     */
    private void stockfishToMakeAMove(StockFishOutput stockFishOutput) {
        if(isNull(stockFishOutput.getPieceType())) {
            this.game.makeAMove(stockFishOutput.getPreviousPosition(),stockFishOutput.getNewPosition());
        } else {
            this.game.makeAMove(stockFishOutput.getPreviousPosition(),stockFishOutput.getNewPosition());
            this.game.promotionPieceDecision(stockFishOutput.getPieceType());
        }
    }


    /**
     * Get a Position Object from the Algebraic Notation
     *
     * @param file the file letter
     * @param rank the rank number
     * @return a Position Object
     */
    private Position changeToPosition(char file, char rank) {
        return new Position(file-97,8-Character.getNumericValue(rank));
    }
}
