package cz.uhk.nekvimi.chessdatabase;

import cz.uhk.nekvimi.chessdatabase.dto.ChessGamePreviewDto;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameInfo;
import cz.uhk.nekvimi.chessdatabase.entity.PlayerInfo;
import cz.uhk.nekvimi.chessdatabase.entity.TournamentInfo;

public class PgnGameConverter implements ChessGameConverter<PgnTag, String> {

    @Override
    public ChessGameDb convertToChessGameDb(ChessGame<PgnTag, String> chessGame) {
        var x =  new ChessGameDb(
                getPlayerInfo(chessGame, true),
                getPlayerInfo(chessGame, false),
                getChessGameInfo(chessGame),
                getTournamentInfo(chessGame));
        return x;
    }

    @Override
    public ChessGamePreviewDto convertToChessGameDto(ChessGame<PgnTag, String> chessGame) {
        return new ChessGamePreviewDto(
                chessGame.getGamePart(PgnTag.WHITE_NAME),
                chessGame.getGamePart(PgnTag.WHITE_ELO),
                chessGame.getGamePart(PgnTag.BLACK_NAME),
                chessGame.getGamePart(PgnTag.BLACK_ELO),
                chessGame.getGamePart(PgnTag.RESULT),
                chessGame.getGamePart(PgnTag.ECO));
    }

    private PlayerInfo getPlayerInfo(ChessGame<PgnTag, String> chessGame, boolean white) {
        if (white) {
            return new PlayerInfo(chessGame.getGamePart(PgnTag.WHITE_NAME), chessGame.getGamePart(PgnTag.WHITE_ELO));

        } else {
            return new PlayerInfo(chessGame.getGamePart(PgnTag.BLACK_NAME), chessGame.getGamePart(PgnTag.BLACK_ELO));
        }
    }

    private ChessGameInfo getChessGameInfo(ChessGame<PgnTag, String> chessGame) {
        return new ChessGameInfo(
                chessGame.getGamePart(PgnTag.ECO),
                Result.getResult(chessGame.getGamePart(PgnTag.RESULT)),
                chessGame.getGamePart(PgnTag.GAME_RECORD),
                chessGame.getGamePart(PgnTag.DATE));
    }

    private TournamentInfo getTournamentInfo(ChessGame<PgnTag, String> chessGame) {
        return new TournamentInfo(
                chessGame.getGamePart(PgnTag.TOURNAMENT),
                chessGame.getGamePart(PgnTag.SITE),
                chessGame.getGamePart(PgnTag.ROUND));
    }
}