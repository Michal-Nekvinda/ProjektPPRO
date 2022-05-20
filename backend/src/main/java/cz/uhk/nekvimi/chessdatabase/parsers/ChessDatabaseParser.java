package cz.uhk.nekvimi.chessdatabase.parsers;

import cz.uhk.nekvimi.chessdatabase.ChessGame;

import java.io.InputStream;
import java.util.List;

public interface ChessDatabaseParser {
    List<ChessGame> splitDbToIndividualGames(InputStream gamesFile);
}
