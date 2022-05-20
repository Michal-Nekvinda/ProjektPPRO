package cz.uhk.nekvimi.chessdatabase.dto;

import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;

public class ChessGamePreviewDto extends DtoWithError {
    long id;
    String whiteName;
    String whiteElo;
    String blackName;
    String blackElo;
    String result;
    String eco;
    String tournament;
    String date;

    public ChessGamePreviewDto() {
    }

    public ChessGamePreviewDto(String error) {
        this.setErrorMessage(error);
    }

    public ChessGamePreviewDto(ChessGameDb gameDb) {
        this.id = gameDb.getId();
        this.whiteName = gameDb.getWhite().getName();
        this.whiteElo = gameDb.getWhite().getElo();
        this.blackName = gameDb.getBlack().getName();
        this.blackElo = gameDb.getBlack().getElo();
        this.result = gameDb.getChessGameInfo().getResult().getValue();
        this.eco = gameDb.getChessGameInfo().getEco();
        this.tournament = gameDb.getTournamentInfo().getName();
        this.date = gameDb.getChessGameInfo().getDate();
    }

    public ChessGamePreviewDto(long id, String whiteName, String whiteElo, String blackName, String blackElo, String result, String eco, String tournament, String date) {
        this.id = id;
        this.whiteName = whiteName;
        this.whiteElo = whiteElo;
        this.blackName = blackName;
        this.blackElo = blackElo;
        this.result = result;
        this.eco = eco;
        this.tournament = tournament;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
