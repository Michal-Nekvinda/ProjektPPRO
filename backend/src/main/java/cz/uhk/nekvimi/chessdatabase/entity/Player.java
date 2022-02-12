package cz.uhk.nekvimi.chessdatabase.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Player {
    @Id
    @GeneratedValue
    long id;
    String name;
    @ElementCollection
    Map<Long, Integer> eloRecords;

    public Player(String name) {
        this.name = name;
        this.eloRecords = new HashMap<>();
    }

    public Player() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, Integer> getEloRecords() {
        return this.eloRecords;
    }

    public void addElo(Long gameId, int elo) {
        eloRecords.put(gameId, elo);
    }

    public Integer getEloInGame(Long gameId) {
        if (eloRecords.containsKey(gameId)) {
            return eloRecords.get(gameId);
        }
        return null;
    }
}
