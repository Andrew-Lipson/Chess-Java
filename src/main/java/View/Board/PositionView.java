package View.Board;

public class PositionView {

    private int file;
    private int rank;

    public PositionView(int file, int rank){
        this.file = file;
        this.rank = rank;
    }

    public int getFile(){
        return this.file;
    }

    public int getRank(){
        return this.rank;
    }
}

