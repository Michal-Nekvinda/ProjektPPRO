package cz.uhk.nekvimi.chessdatabase.entity;

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

    public PlayerInfo() {
    }
    public PlayerInfo(String name, String elo) {
        this.name = name;
        this.elo = elo;
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
}
