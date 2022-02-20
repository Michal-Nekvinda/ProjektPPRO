package cz.uhk.nekvimi.chessdatabase.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TournamentInfo {
    long id;
    String name;
    String site;
    String round;

    public TournamentInfo() {
    }

    public TournamentInfo(String name, String site, String round) {
        this.name = name;
        this.site = site;
        this.round = round;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }
}
