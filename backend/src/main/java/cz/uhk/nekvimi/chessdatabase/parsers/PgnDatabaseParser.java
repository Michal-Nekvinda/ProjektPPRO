package cz.uhk.nekvimi.chessdatabase.parsers;

import cz.uhk.nekvimi.chessdatabase.ChessGame;
import cz.uhk.nekvimi.chessdatabase.PgnGame;
import cz.uhk.nekvimi.chessdatabase.parsers.ChessDatabaseParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PgnDatabaseParser implements ChessDatabaseParser {
    Charset charset;

    public PgnDatabaseParser() {
        this.charset = Charset.defaultCharset();
    }

    public PgnDatabaseParser(Charset charset) {
        this.charset = charset;
    }

    @Override
    public List<ChessGame> splitDbToIndividualGames(InputStream gamesFile) {
        try {
            var pgnContent = new String(gamesFile.readAllBytes(), charset);
            var listOfGames = new ArrayList<ChessGame>();
            String processedGame = "";
            //ve validnim souboru je mezi hlavickou a zapisem partie je vzdy prazdny radek
            String[] partsSplitByEmptyLine = pgnContent.split("(?m)^\\s*$");

            for (String part : partsSplitByEmptyLine) {
                part = part.trim();
                if (!part.isEmpty()) {
                    if (part.charAt(0) == '[') {
                        //radek s hlavickou
                        processedGame += part;
                        continue;
                    }
                    if (part.charAt(0) == '{' || part.charAt(0) == '1') {
                        //radek, kde zacina zapis partie - ulozime partii a zacneme zpracovavat dalsi
                        listOfGames.add(new PgnGame(processedGame, part));
                        processedGame = "";
                        continue;
                    }
                    //nerozpoznany radek -> pgn soubor je nevalidni
                    return null;
                }
            }
            return listOfGames;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
