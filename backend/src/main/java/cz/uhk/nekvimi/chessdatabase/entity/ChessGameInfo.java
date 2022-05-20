package cz.uhk.nekvimi.chessdatabase.entity;

import cz.uhk.nekvimi.chessdatabase.enums.Result;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ChessGameInfo {
    long id;
    String eco;
    Result result;
    @Column(length = 5000)
    String rawGameData;
    String date;

    public ChessGameInfo() {
    }

    public ChessGameInfo(String eco, Result result, String rawGameData, String date) {
        this.eco = eco;
        this.result = result;
        this.rawGameData = rawGameData;
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

    public String getRawGameData() {
        return rawGameData;
    }

    public void setRawGameData(String rawGameData) {
        this.rawGameData = rawGameData;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
