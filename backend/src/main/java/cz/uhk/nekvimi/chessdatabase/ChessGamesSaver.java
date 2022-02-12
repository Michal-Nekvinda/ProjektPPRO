package cz.uhk.nekvimi.chessdatabase;

import cz.uhk.nekvimi.chessdatabase.dto.ChessGamesInfoDto;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameData;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameInfo;
import cz.uhk.nekvimi.chessdatabase.entity.Player;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.Map;

public class ChessGamesSaver {
    ChessDatabaseParser databaseParser;

    public ChessGamesSaver(ChessDatabaseParser parser) {
        this.databaseParser = parser;
    }

    public ChessGamesInfoDto saveChessGames(String file) {
        var ret = new ChessGamesInfoDto();
        Configuration c = new Configuration();
        Session session = c.buildSessionFactory().openSession();
        var games = databaseParser.splitToIndividualGames(file);
        for (var game : games) {
            var gameParts = databaseParser.parseGame(game);
            var gameData = new ChessGameData(gameParts.get(SupportedKeysInGameHeader.GAME_RECORD));
            session.save(gameData);
            var gameInfo = createGameInfo(gameParts, gameData.getId());
            session.save(gameInfo);
            ret.addGameInfo(gameInfo);
        }
        return ret;
    }

    private ChessGameInfo createGameInfo(Map<String, String> gameParts, Long gameDataId){
        var gameInfo = new ChessGameInfo();
        gameInfo.setWhite(new Player(gameParts.get(SupportedKeysInGameHeader.WHITE_NAME)));
        gameInfo.setBlack(new Player(gameParts.get(SupportedKeysInGameHeader.BLACK_NAME)));
        gameInfo.setWinner(Winner.getWinner(gameParts.get(SupportedKeysInGameHeader.RESULT)));
        gameInfo.setGameDataId(gameDataId);
        gameInfo.setRound(gameParts.get(SupportedKeysInGameHeader.ROUND));
        gameInfo.setDate(gameParts.get(SupportedKeysInGameHeader.DATE));
        gameInfo.setTournament(gameParts.get(SupportedKeysInGameHeader.TOURNAMENT));
        gameInfo.setEco(gameParts.get(SupportedKeysInGameHeader.ECO));

        return gameInfo;
    }
}
