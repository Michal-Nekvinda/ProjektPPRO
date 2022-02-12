package cz.uhk.nekvimi.chessdatabase.entity;

import cz.uhk.nekvimi.chessdatabase.Winner;

import javax.persistence.*;

@Entity
public class ChessGameInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne
    Player white;
    @ManyToOne
    Player black;
    Winner winner;
    long gameDataId;
    String eco;
    String tournament;
    String date;
    String round;

    public ChessGameInfo() {
    }

    public ChessGameInfo(Player white, Player black, Winner winner, long gameDataId, String eco, String tournament, String date, String round) {
        this.white = white;
        this.black = black;
        this.winner = winner;
        this.gameDataId = gameDataId;
        this.eco = eco;
        this.tournament = tournament;
        this.date = date;
        this.round = round;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getWhite() {
        return white;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public Winner getWinner() {
        return winner;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }

    public long getGameDataId() {
        return gameDataId;
    }

    public void setGameDataId(long gameDataId) {
        this.gameDataId = gameDataId;
    }

    public String getEco() {
        return eco;
    }

    public void setEco(String eco) {
        this.eco = eco;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }
}
