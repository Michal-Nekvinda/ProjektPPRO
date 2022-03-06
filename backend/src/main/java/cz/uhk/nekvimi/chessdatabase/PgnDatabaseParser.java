package cz.uhk.nekvimi.chessdatabase;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PgnDatabaseParser implements ChessDatabaseParser<PgnTag,String> {
    private static final String gameHeaderSeparator = "#PGNchessGameHeaderSeparator#";
    Charset charset;

    public PgnDatabaseParser() {
        this.charset = Charset.defaultCharset();
    }

    public PgnDatabaseParser(Charset charset) {
        this.charset = charset;
    }

    @Override
    public List<String> splitDbToIndividualGames(InputStream pgnFile) {
        try {
            String pgnContent = new String(pgnFile.readAllBytes(), charset);
            var listOfGames = new ArrayList<String>();
            String actualGame = "";
            String[] partsSplitByEmptyLine = pgnContent.split("(?m)^\\s*$");
            for (String part : partsSplitByEmptyLine) {
                part = part.trim();
                if (!part.isEmpty()) {
                    if (part.charAt(0) == '[') {
                        actualGame += part;
                        continue;
                    }
                    if (part.charAt(0) == '{' || part.charAt(0) == '1') {
                        actualGame += PgnDatabaseParser.gameHeaderSeparator + part;
                        listOfGames.add(actualGame);
                        actualGame = "";
                        continue;
                    }
                    throw new AssertionError("invalid pgn file");
                }
            }

            return listOfGames;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ChessGame<PgnTag, String> parseToChessGame(String game) {
        var pgnGame = new PgnGame();
        var headerAndBody = game.split(PgnDatabaseParser.gameHeaderSeparator);
        if (headerAndBody.length != 2) {
            return null;
        }

        pgnGame.setGamePart(PgnTag.GAME_DATA, formatGameString(headerAndBody[1]));
        var headerLines = headerAndBody[0].split(System.getProperty("line.separator"));
        for (String line : headerLines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.charAt(0) == '[') {
                Pattern pattern = Pattern.compile("\\[(.*)\\]");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    line = matcher.group(1);
                    var keyAndValue = line.split("\"");
                    var pgnTag = tryGetTagFromString(keyAndValue[0].trim());
                    if (pgnTag != null) {
                        pgnGame.setGamePart(pgnTag, keyAndValue[1]);
                    }
                }
            } else {
                throw new AssertionError("invalid chess game in pgn file");
            }
        }

        return pgnGame;
    }

    private String formatGameString(String game){
        return game.replace("\n", " ").replace("\r", "");
    }

    private PgnTag tryGetTagFromString(String key) {
        for (PgnTag tag : PgnTag.values()) {
            if (tag.getKey().equals(key)) {
                return tag;
            }
        }
        return null;
    }
}
