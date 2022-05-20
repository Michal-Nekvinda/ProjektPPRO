package cz.uhk.nekvimi.chessdatabase;

import cz.uhk.nekvimi.chessdatabase.enums.PgnTag;

import java.util.HashMap;
import java.util.Map;

public class PgnGameParts {
    public static final String notAvailable = "N/A";

    private Map<PgnTag, String> gameParts;

    public PgnGameParts() {
        gameParts = new HashMap<>();
    }

    public void setGamePart(PgnTag key, String value) {
        gameParts.put(key, value);
    }

    public String getGamePart(PgnTag key) {
        if (gameParts.containsKey(key)) {
            return gameParts.get(key);
        }
        return notAvailable;
    }
}
