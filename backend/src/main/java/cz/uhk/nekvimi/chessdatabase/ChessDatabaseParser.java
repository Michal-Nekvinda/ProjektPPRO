package cz.uhk.nekvimi.chessdatabase;

import java.util.List;
import java.util.Map;

public interface ChessDatabaseParser {
    List<String> splitToIndividualGames(String games);
    Map<String, String> parseGame(String game);
}
