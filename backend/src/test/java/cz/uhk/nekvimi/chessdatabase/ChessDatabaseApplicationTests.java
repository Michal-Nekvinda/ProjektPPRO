package cz.uhk.nekvimi.chessdatabase;

import cz.uhk.nekvimi.chessdatabase.parsers.ChessDatabaseParser;
import cz.uhk.nekvimi.chessdatabase.parsers.ChessGameParser;
import cz.uhk.nekvimi.chessdatabase.parsers.PgnDatabaseParser;
import cz.uhk.nekvimi.chessdatabase.parsers.PgnGameParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
class ChessDatabaseApplicationTests {

    @Test
    void pgnParserTest() throws IOException {
        File testFile = new File("Partie Michal Nekvinda.txt");
        InputStream str = new FileInputStream(testFile);

        ChessDatabaseParser dbParser = new PgnDatabaseParser();
        ChessGameParser gameParser = new PgnGameParser();
        var games = dbParser.splitDbToIndividualGames(str);
        for (var game: games) {
            var gameDb = gameParser.parseGame(game);
            assertEquals(gameDb.getWhite().getName(), "Nekvinda, Michal");

        }
    }

}
