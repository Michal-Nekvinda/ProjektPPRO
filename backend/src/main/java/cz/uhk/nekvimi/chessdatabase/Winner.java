package cz.uhk.nekvimi.chessdatabase;

public enum Winner {
    WHITE("1-0"),
    DRAW("1/2-1/2"),
    BLACK("0-1"),
    UNKNOWN("*");

    Winner(String result){
        this.result = result;
    }
    private String result;

    public static Winner getWinner(String result) {
        var parsed = result.trim().split("-");
        if (parsed.length != 2) {
            return Winner.UNKNOWN;
        }
        if (parsed[0].trim().equals("1") && parsed[1].trim().equals("0")) {
            return Winner.WHITE;
        }
        if (parsed[0].trim().equals("1/2") && parsed[1].trim().equals("1/2")) {
            return Winner.DRAW;
        }
        if (parsed[0].trim().equals("0") && parsed[1].trim().equals("1")) {
            return Winner.BLACK;
        }

        return Winner.UNKNOWN;
    }

    public String getResult() {
        return this.result;
    }
}



