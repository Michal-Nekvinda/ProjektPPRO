package cz.uhk.nekvimi.chessdatabase.dto;

import cz.uhk.nekvimi.chessdatabase.DtoWithError;

public class ChessGamePreviewDto extends DtoWithError {

    String whiteName;
    String whiteElo;
    String blackName;
    String blackElo;
    String result;
    String eco;

    public ChessGamePreviewDto() {
    }

    public ChessGamePreviewDto(String whiteName, String whiteElo, String blackName, String blackElo, String result, String eco) {
        this.whiteName = whiteName;
        this.whiteElo = whiteElo;
        this.blackName = blackName;
        this.blackElo = blackElo;
        this.result = result;
        this.eco = eco;
    }
}
