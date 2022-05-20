package cz.uhk.nekvimi.chessdatabase.enums;

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
    GAME_DATA("Game");

    private String key;
    PgnTag(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
