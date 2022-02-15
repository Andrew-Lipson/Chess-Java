package Model;

public final class Position {

    private int file;
    private int rank;

    public Position(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    public int getFile() {
        return this.file;
    }

    public int getRank() {
        return this.rank;
    }
}
