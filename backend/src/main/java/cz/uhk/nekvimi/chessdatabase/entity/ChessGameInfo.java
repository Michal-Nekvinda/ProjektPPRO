package cz.uhk.nekvimi.chessdatabase.entity;

import cz.uhk.nekvimi.chessdatabase.Result;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ChessGameInfo {
    long id;
    String eco;
    Result result;
    @Column(length = 5_000)
    String game_record;
    String date;

    public ChessGameInfo() {
    }

    public ChessGameInfo(String eco, Result result, String game_record, String date) {
        this.eco = eco;
        this.result = result;
        this.game_record = game_record;
        this.date = date;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEco() {
        return eco;
    }

    public void setEco(String eco) {
        this.eco = eco;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getGame_record() {
        return game_record;
    }

    public void setGame_record(String game_record) {
        this.game_record = game_record;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
