package cz.uhk.nekvimi.chessdatabase.parsing;

import cz.uhk.nekvimi.chessdatabase.ChessGame;

import java.io.InputStream;
import java.util.List;

public interface ChessDatabaseParser {
    List<ChessGame> splitDbToIndividualGames(InputStream gamesFile);
}
