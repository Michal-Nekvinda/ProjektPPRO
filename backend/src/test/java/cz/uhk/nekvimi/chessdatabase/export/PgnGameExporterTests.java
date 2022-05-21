package cz.uhk.nekvimi.chessdatabase.export;

import cz.uhk.nekvimi.chessdatabase.ChessGame;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import cz.uhk.nekvimi.chessdatabase.enums.Result;
import cz.uhk.nekvimi.chessdatabase.parsing.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PgnGameExporterTests {

    @Test
    void givenPgnDb_ShouldBeTheSamePgnDbAfterImportAndExport() throws IOException {
        File testFile = new File("testDbSmall.pgn");
        InputStream str = new FileInputStream(testFile);

        InputStream str2 = new FileInputStream(testFile);
        var oldPgnContent = new String(str2.readAllBytes());

        ChessDatabaseParser dbParser = new PgnDatabaseParser();
        ChessGameParser gameParser = new PgnGameParser();
        ChessGameExporter gameExporter = new PgnGameExporter();

        //nacteni partii z testovaci db do datovych struktur aplikace
        List<ChessGame> games = dbParser.splitDbToIndividualGames(str);
        var gamesDb = new ArrayList<ChessGameDb>();
        for (var game : games) {
            gamesDb.add(gameParser.parseGame(game));
        }

        //prevod nactenych her do textoveho formatu pgn pro export
        var exportedGames = new ArrayList<String>();
        for (var game : gamesDb) {
            exportedGames.add(gameExporter.exportChessGameDb(game));
        }

        String emptyLine = System.lineSeparator() + System.lineSeparator();
        var newPgnContent = String.join(emptyLine, exportedGames);

        //obsah puvodniho a noveho souboru by mel byt stejny
        assertEquals(oldPgnContent, newPgnContent);
    }
}
