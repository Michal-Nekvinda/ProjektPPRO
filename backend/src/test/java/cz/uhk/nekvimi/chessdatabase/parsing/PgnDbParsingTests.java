package cz.uhk.nekvimi.chessdatabase.parsing;

import cz.uhk.nekvimi.chessdatabase.ChessGame;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import cz.uhk.nekvimi.chessdatabase.enums.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class PgnDbParsingTests {

    @Test
    void givenInvalidPgnDb_ShouldReturnNullAfterParsing() throws IOException {
        File testFile = new File("InvalidFile.pgn");
        InputStream str = new FileInputStream(testFile);

        ChessDatabaseParser dbParser = new PgnDatabaseParser();

        List<ChessGame> games = dbParser.splitDbToIndividualGames(str);
        assertEquals(games, null);
    }

    @Test
    void givenValidPgnDb_ShouldParseAllGamesCorrectly() throws IOException {
        File testFile = new File("testDbSmall.pgn");
        InputStream str = new FileInputStream(testFile);

        ChessDatabaseParser dbParser = new PgnDatabaseParser();
        ChessGameParser gameParser = new PgnGameParser();

        List<ChessGame> games = dbParser.splitDbToIndividualGames(str);
        assertEquals(games.size(), 4);

        var gamesDb = new ArrayList<ChessGameDb>();
        for (var game : games) {
            var gameDb = gameParser.parseGame(game);
            gamesDb.add(gameDb);
            assertNotEquals(gameDb.getWhite().getName(), PgnGameParts.notAvailable);
            assertNotEquals(gameDb.getBlack().getName(), PgnGameParts.notAvailable);
            assertNotEquals(gameDb.getChessGameInfo().getResult(), Result.UNKNOWN);
        }
        randomGamesCheck(gamesDb);
    }

    void randomGamesCheck(List<ChessGameDb> gamesFromSmallDb) {
        assertEquals(gamesFromSmallDb.get(0).getWhite().getName(), "Player 1");
        assertEquals(gamesFromSmallDb.get(1).getTournamentInfo().getName(), "OPJ sk.B2");
        assertEquals(gamesFromSmallDb.get(2).getBlack().getElo(), "1468");
        assertEquals(gamesFromSmallDb.get(3).getChessGameInfo().getResult().getValue(), "0-1");
    }

}
