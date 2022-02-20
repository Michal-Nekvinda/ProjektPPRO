package cz.uhk.nekvimi.chessdatabase;

public enum PgnTag {
    BLACK_NAME("Black"),
    WHITE_NAME("White"),
    BLACK_ELO("BlackElo"),
    WHITE_ELO("WhiteElo"),
    TOURNAMENT("Event"),
    SITE("Site"),
    RESULT("Result"),
    ROUND("Round"),
    ECO("ECO"),
    DATE("Date"),
    GAME_RECORD("Game");

    private String key;
    PgnTag(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}