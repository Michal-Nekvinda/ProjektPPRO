package cz.uhk.nekvimi.chessdatabase;

public class PgnGame implements ChessGame {
    private String header;
    private String body;

    public PgnGame(String header, String body){
        this.header = header;
        this.body = body;
    }

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public String getBody() {
        return body;
    }
}
