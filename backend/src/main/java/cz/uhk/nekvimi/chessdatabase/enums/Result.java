package cz.uhk.nekvimi.chessdatabase.enums;

public enum Result {
    WHITE_WIN("1-0"),
    DRAW("1/2-1/2"),
    BLACK_WIN("0-1"),
    UNKNOWN("*");

    Result(String result){
        this.result = result;
    }
    private String result;

    public String getValue(){
        return result;
    }

    public static Result getResult(String result) {
        if(result == null){
            return Result.UNKNOWN;
        }
        var parsed = result.trim().split("-");

        if (parsed.length != 2) {
            return Result.UNKNOWN;
        }
        if (parsed[0].trim().equals("1") && parsed[1].trim().equals("0")) {
            return Result.WHITE_WIN;
        }
        if (parsed[0].trim().equals("1/2") && parsed[1].trim().equals("1/2")) {
            return Result.DRAW;
        }
        if (parsed[0].trim().equals("0") && parsed[1].trim().equals("1")) {
            return Result.BLACK_WIN;
        }

        return Result.UNKNOWN;
    }
}



