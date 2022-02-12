package cz.uhk.nekvimi.chessdatabase;

public enum SupportedKeysInGameHeader {
    BLACK_NAME("Black"),
    WHITE_NAME("White"),
    BLACK_ELO("BlackElo"),
    WHITE_ELO("WhiteElo"),
    TOURNAMENT("Event"),
    RESULT("Result"),
    ROUND("Round"),
    ECO("ECO"),
    DATE("Date"),
    GAME_RECORD("Game");

    private String key;
    SupportedKeysInGameHeader(String key) {
        this.key = key;
    }
}
