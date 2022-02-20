package cz.uhk.nekvimi.chessdatabase;

public enum Pieces {
    WHITE("White"),
    BLACK("Black"),
    UNKNOWN("Unknown");

    private String key;
    Pieces(String key) {
        this.key = key;
    }
}
