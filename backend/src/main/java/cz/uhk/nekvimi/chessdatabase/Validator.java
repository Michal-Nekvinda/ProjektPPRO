package cz.uhk.nekvimi.chessdatabase;

import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import cz.uhk.nekvimi.chessdatabase.enums.Result;
import cz.uhk.nekvimi.chessdatabase.parsing.PgnGameParts;

public class Validator {
    public static String validateGameDb(ChessGameDb game) {
        var generalError = "Chyba! Partii nelze uložit.";
        if (game == null) {
            return generalError + " Povinné položky musí být vyplněny.";
        }
        if (game.getWhite().getName().equals(PgnGameParts.notAvailable) ||
                game.getBlack().getName().equals(PgnGameParts.notAvailable)) {
            return generalError + " Jména hráčů musí být vyplněna.";
        }
        if (game.getChessGameInfo().getResult() == Result.UNKNOWN) {
            return generalError + " Výsledek partie musí být vyplněn.";
        }
        return null;
    }
}
