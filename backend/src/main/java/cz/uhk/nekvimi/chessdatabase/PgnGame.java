package cz.uhk.nekvimi.chessdatabase;

import java.util.HashMap;
import java.util.Map;

public class PgnGame implements ChessGame<PgnTag, String> {
    private Map<PgnTag, String> gameParts;

    public PgnGame() {
        gameParts = new HashMap<>();
    }

    public void setGamePart(PgnTag key, String value) {
        gameParts.put(key, value);
    }

    public String getGamePart(PgnTag key) {
        if (gameParts.containsKey(key)) {
            return gameParts.get(key);
        }
        return null;
    }
}
