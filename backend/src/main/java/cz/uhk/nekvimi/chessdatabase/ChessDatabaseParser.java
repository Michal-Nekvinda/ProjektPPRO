package cz.uhk.nekvimi.chessdatabase;

import java.io.InputStream;
import java.util.List;

public interface ChessDatabaseParser<K, V> {
    ChessGame<K, V> parseToChessGame(String game);

    List<String> splitDbToIndividualGames(InputStream gamesFile);
}
