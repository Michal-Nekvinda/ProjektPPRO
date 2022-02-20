package cz.uhk.nekvimi.chessdatabase;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

        PgnDatabaseParser parser = new PgnDatabaseParser();
        var games = parser.splitDbToIndividualGames(str);

        var game = parser.parseToChessGame(games.get(0));

        //hra s mezerou v hlavicce
        var game2 = parser.parseToChessGame(games.get(1));
    }

}
