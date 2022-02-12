package cz.uhk.nekvimi.chessdatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PgnParser implements ChessDatabaseParser {
    private static String gameHeaderSeparator = "#PGNchessGameHeaderSeparator#";

    @Override
    public List<String> splitToIndividualGames(String games) {
        var listOfGames = new ArrayList<String>();
        String actualGame = "";
        String[] partsSplitByEmptyLine = games.split("(?m)^\\s*$");
        for (String part : partsSplitByEmptyLine) {
            part = part.trim();
            if (!part.isEmpty()) {
                if (part.charAt(0) == '[') {
                    actualGame += part;
                    continue;
                }
                if (part.charAt(0) == '{' || part.charAt(0) == '1') {
                    actualGame += PgnParser.gameHeaderSeparator + part;
                    listOfGames.add(actualGame);
                    actualGame = "";
                    continue;
                }
                throw new AssertionError("invalid pgn file");
            }
        }

        return listOfGames;
    }

    @Override
    public Map<String, String> parseGame(String game) {
        var gameParts = new HashMap<String, String>();
        var headerAndBody = game.split(PgnParser.gameHeaderSeparator);
        if (headerAndBody.length != 2) {
            return null;
        }
        gameParts.put("Game", headerAndBody[1]);
        var headerLines = headerAndBody[0].split(System.getProperty("line.separator"));
        for (String line : headerLines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.charAt(0) == '[') {
                Pattern pattern = Pattern.compile("\\[(.*)\\]");
                Matcher matcher = pattern.matcher(line);
                if(matcher.find()){
                    line = matcher.group(1);
                    var keyAndValue = line.split("\"");
                    gameParts.put(keyAndValue[0].trim(), keyAndValue[1].trim());
                }
            }
            else{
                throw new AssertionError("invalid chess game in pgn file");
            }
        }

        return gameParts;
    }
}
