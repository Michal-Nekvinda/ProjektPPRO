package cz.uhk.nekvimi.chessdatabase.parsing;

import cz.uhk.nekvimi.chessdatabase.ChessGame;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;

public interface ChessGameParser {
    ChessGameDb parseGame(ChessGame chessGame);
    ChessGameDb parseGame(String chessGame, String headerBodySeparator);
}
