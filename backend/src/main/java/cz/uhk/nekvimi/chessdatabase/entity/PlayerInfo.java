package cz.uhk.nekvimi.chessdatabase.entity;

import cz.uhk.nekvimi.chessdatabase.Pieces;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PlayerInfo {
    @Id
    @GeneratedValue
    long id;
    String name;
    String elo;
    Pieces pieces;

    public PlayerInfo() {
    }
    public PlayerInfo(String name, String elo) {
        this.name = name;
        this.elo = elo;
        this.pieces = Pieces.UNKNOWN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElo() {
        return elo;
    }

    public void setElo(String elo) {
        this.elo = elo;
    }

    public Pieces getPieces() {
        return pieces;
    }

    public void setPieces(Pieces pieces) {
        this.pieces = pieces;
    }
}
