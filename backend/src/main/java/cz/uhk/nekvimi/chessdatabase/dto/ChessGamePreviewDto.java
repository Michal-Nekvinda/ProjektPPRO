package cz.uhk.nekvimi.chessdatabase.dto;

import cz.uhk.nekvimi.chessdatabase.DtoWithError;

public class ChessGamePreviewDto extends DtoWithError {

    String whiteName;
    String whiteElo;
    String blackName;
    String blackElo;
    String result;
    String eco;
    String tournamentName;

    public ChessGamePreviewDto() {
    }

    public ChessGamePreviewDto(String whiteName, String whiteElo, String blackName, String blackElo, String result, String eco, String tournamentName) {
        this.whiteName = whiteName;
        this.whiteElo = whiteElo;
        this.blackName = blackName;
        this.blackElo = blackElo;
        this.result = result;
        this.eco = eco;
        this.tournamentName = tournamentName;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getWhiteName() {
        return whiteName;
    }

    public void setWhiteName(String whiteName) {
        this.whiteName = whiteName;
    }

    public String getWhiteElo() {
        return whiteElo;
    }

    public void setWhiteElo(String whiteElo) {
        this.whiteElo = whiteElo;
    }

    public String getBlackName() {
        return blackName;
    }

    public void setBlackName(String blackName) {
        this.blackName = blackName;
    }

    public String getBlackElo() {
        return blackElo;
    }

    public void setBlackElo(String blackElo) {
        this.blackElo = blackElo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getEco() {
        return eco;
    }

    public void setEco(String eco) {
        this.eco = eco;
    }
}
