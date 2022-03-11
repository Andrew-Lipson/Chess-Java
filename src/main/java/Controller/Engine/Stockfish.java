package Controller.Engine;

import Model.Game;
import Model.Pieces.PieceType;
import Model.Position;
import javafx.application.Platform;

import java.io.*;
import java.util.Objects;

import static java.util.Objects.isNull;

public class Stockfish {

    private Process process;
    private String command;
    private Game game;

    public Stockfish(String fen, Game game) {
        this.game = game;
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("stockfish_14.1_win_x64_avx2.exe");
        builder.redirectErrorStream(true);
        try {
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        command = "position fen " + fen +"\n";
    }

    public void run() {
        StockFishOutput stockFishOutput = getStockfishMove();
        Platform.runLater(() -> stockfishToMakeAMove(stockFishOutput));
    }

    public void stockfishToMakeAMove(StockFishOutput stockFishOutput) {
        if(isNull(stockFishOutput.getPieceType())){
            game.makeAMove(stockFishOutput.getPreviousPosition(),stockFishOutput.getNewPosition(), true);
        } else{
            game.makeAMove(stockFishOutput.getPreviousPosition(),stockFishOutput.getNewPosition(), true);
            game.promotionPieceDecision(stockFishOutput.getPieceType());
        }
    }


    public StockFishOutput getStockfishMove() {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

            w.write(command);
            w.write("go movetime 500\n");
            w.flush();

            String line;
            while (true) {
                line = r.readLine();
                String[] str = line.split(" ");
                if (Objects.equals(str[0], "bestmove")){
                    char[] chars = str[1].toCharArray();
                    Position previousPosition = changeToPosition(chars[0],chars[1]);
                    Position newPosition = changeToPosition(chars[2],chars[3]);
                    if (chars.length==4){
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

    private Position changeToPosition(char file, char rank){
        return new Position(file-97,8-Character.getNumericValue(rank));
    }
}
