package cz.uhk.nekvimi.chessdatabase.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChessGameData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
