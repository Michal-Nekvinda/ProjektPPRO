package cz.uhk.nekvimi.chessdatabase;

public class PgnGame implements ChessGame {
    private String header;
    private String data;

    public PgnGame(String header, String data){
        this.header = header;
        this.data = data;
    }

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public String getData() {
        return data;
    }
}
