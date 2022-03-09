package Controller;

import Model.Pieces.PieceType;
import Model.Position;

import java.io.*;
import java.util.Objects;

public class Stockfish {

    public StockFishOutput getStockfishMove(String fen) {
        String command = "position fen " + fen +"\n";
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("stockfish_14.1_win_x64_avx2.exe");
        builder.redirectErrorStream(true);
        try {
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));



            w.write("isready\n");
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
