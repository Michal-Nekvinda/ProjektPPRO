package cz.uhk.nekvimi.chessdatabase.parsers;

import cz.uhk.nekvimi.chessdatabase.ChessGame;
import cz.uhk.nekvimi.chessdatabase.PgnGame;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;

public class PgnGameParser implements ChessGameParser {
    @Override
    public ChessGameDb parseGame(ChessGame chessGame) {

        var pgnGameConverter = new PgnGameConverter();
        return pgnGameConverter.convertToChessGameDb(chessGame);
    }

    @Override
    public ChessGameDb parseGame(String chessGame, String headerBodySeparator) {
        var headerAndBody = chessGame.split(headerBodySeparator);
        if(headerAndBody.length != 2){
            return null;
        }
        return parseGame(new PgnGame(headerAndBody[0], headerAndBody[1]));
    }
}
