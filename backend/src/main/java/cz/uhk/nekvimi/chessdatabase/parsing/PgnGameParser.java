package cz.uhk.nekvimi.chessdatabase.parsing;

import cz.uhk.nekvimi.chessdatabase.ChessGame;
import cz.uhk.nekvimi.chessdatabase.PgnGame;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameInfo;
import cz.uhk.nekvimi.chessdatabase.entity.PlayerInfo;
import cz.uhk.nekvimi.chessdatabase.entity.TournamentInfo;
import cz.uhk.nekvimi.chessdatabase.enums.PgnTag;
import cz.uhk.nekvimi.chessdatabase.enums.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PgnGameParser implements ChessGameParser {
    @Override
    public ChessGameDb parseGame(ChessGame chessGame) {

        var pgnGameParts = parseToGameParts(chessGame);
        if (pgnGameParts == null) {
            return null;
        }

        return new ChessGameDb(
                getPlayerInfo(pgnGameParts, true),
                getPlayerInfo(pgnGameParts, false),
                getChessGameInfo(pgnGameParts),
                getTournamentInfo(pgnGameParts));
    }

    @Override
    public ChessGameDb parseGame(String chessGame, String headerBodySeparator) {
        var headerAndBody = chessGame.split(headerBodySeparator);
        if (headerAndBody.length != 2) {
            return null;
        }
        return parseGame(new PgnGame(headerAndBody[0], headerAndBody[1]));
    }

    private PlayerInfo getPlayerInfo(PgnGameParts chessGame, boolean white) {
        if (white) {
            return new PlayerInfo(chessGame.getGamePart(PgnTag.WHITE_NAME), chessGame.getGamePart(PgnTag.WHITE_ELO));

        } else {
            return new PlayerInfo(chessGame.getGamePart(PgnTag.BLACK_NAME), chessGame.getGamePart(PgnTag.BLACK_ELO));
        }
    }

    private ChessGameInfo getChessGameInfo(PgnGameParts chessGame) {
        return new ChessGameInfo(
                chessGame.getGamePart(PgnTag.ECO),
                Result.getResult(chessGame.getGamePart(PgnTag.RESULT)),
                chessGame.getGamePart(PgnTag.GAME_BODY),
                chessGame.getGamePart(PgnTag.DATE));
    }

    private TournamentInfo getTournamentInfo(PgnGameParts chessGame) {
        return new TournamentInfo(
                chessGame.getGamePart(PgnTag.TOURNAMENT),
                chessGame.getGamePart(PgnTag.SITE),
                chessGame.getGamePart(PgnTag.ROUND));
    }

    private PgnGameParts parseToGameParts(ChessGame game) {
        var pgnGame = new PgnGameParts();
        var headerLines = game.getHeader().split("\n");
        var body = game.getBody();

        pgnGame.setGamePart(PgnTag.GAME_BODY, formatGameString(body));

        for (String line : headerLines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.charAt(0) == '[') {
                //vyparsovani obsahu hlavicky na radku
                Pattern pattern = Pattern.compile("\\[(.*)\\]");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    line = matcher.group(1);
                    var keyAndValue = line.split("\"");
                    if (keyAndValue.length < 2) {
                        //nevalidne zapsana informace v hlavicce
                        return null;
                    }
                    var pgnTag = tryGetTagFromString(keyAndValue[0].trim());
                    if (pgnTag != null) {
                        pgnGame.setGamePart(pgnTag, keyAndValue[1]);
                    }
                }
            } else {
                //nevalidni radek v hlavicce
                return null;
            }
        }

        return pgnGame;
    }

    private String formatGameString(String game) {
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
