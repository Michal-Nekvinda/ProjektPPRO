package cz.uhk.nekvimi.chessdatabase;

import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameInfo;
import cz.uhk.nekvimi.chessdatabase.entity.PlayerInfo;
import cz.uhk.nekvimi.chessdatabase.entity.TournamentInfo;
import cz.uhk.nekvimi.chessdatabase.enums.Result;
import cz.uhk.nekvimi.chessdatabase.parsing.PgnGameParts;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ValidatorTests {
    static PlayerInfo validPlayerInfo = new PlayerInfo("Michal", "2000");
    static PlayerInfo invalidPlayerInfo = new PlayerInfo(PgnGameParts.notAvailable, "0");
    static ChessGameInfo validGameInfo = new ChessGameInfo("", Result.DRAW, "", "");
    static ChessGameInfo invalidGameInfo = new ChessGameInfo("", Result.UNKNOWN, "", "");

    @Test
    void GivenInvalidChessGames_ShouldReturnError() {
        ChessGameDb gameDb1 = null;
        ChessGameDb gameDb2 = new ChessGameDb(validPlayerInfo, invalidPlayerInfo, validGameInfo, new TournamentInfo());
        ChessGameDb gameDb3 = new ChessGameDb(validPlayerInfo, validPlayerInfo, invalidGameInfo, new TournamentInfo());

        var error1 = Validator.validateGameDb(gameDb1);
        var error2 = Validator.validateGameDb(gameDb2);
        var error3 = Validator.validateGameDb(gameDb3);

        assertEquals(error1, "Chyba! Partii nelze uložit. Povinné položky musí být vyplněny.");
        assertEquals(error2, "Chyba! Partii nelze uložit. Jména hráčů musí být vyplněna.");
        assertEquals(error3, "Chyba! Partii nelze uložit. Výsledek partie musí být vyplněn.");
    }

    @Test
    void GivenValidChessGames_ShouldReturnNull() {
        ChessGameDb gameDb = new ChessGameDb(validPlayerInfo, validPlayerInfo, validGameInfo, new TournamentInfo());

        assertEquals(Validator.validateGameDb(gameDb), null);
    }
}
