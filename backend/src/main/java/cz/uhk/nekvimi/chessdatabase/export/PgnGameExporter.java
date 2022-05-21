package cz.uhk.nekvimi.chessdatabase.export;

import cz.uhk.nekvimi.chessdatabase.parsing.PgnGameParts;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameInfo;
import cz.uhk.nekvimi.chessdatabase.enums.PgnTag;

public class PgnGameExporter implements ChessGameExporter {
    @Override
    public String exportChessGameDb(ChessGameDb gameDb) {
        var header = createHeader(gameDb);
        var body = createBody(gameDb.getChessGameInfo());
        return header + System.lineSeparator() + body;
    }

    private String createBody(ChessGameInfo chessGameInfo) {
        return chessGameInfo.getRawGameData() == null ? "" : chessGameInfo.getRawGameData();
    }

    private String createHeader(ChessGameDb gameDb) {
        var header = "";

        header += createHeaderRow(PgnTag.TOURNAMENT, gameDb.getTournamentInfo().getName());
        header += createHeaderRow(PgnTag.SITE, gameDb.getTournamentInfo().getSite());
        header += createHeaderRow(PgnTag.DATE, gameDb.getChessGameInfo().getDate());
        header += createHeaderRow(PgnTag.ROUND, gameDb.getTournamentInfo().getRound());
        header += createHeaderRow(PgnTag.WHITE_NAME, gameDb.getWhite().getName());
        header += createHeaderRow(PgnTag.BLACK_NAME, gameDb.getBlack().getName());
        header += createHeaderRow(PgnTag.RESULT, gameDb.getChessGameInfo().getResult().getValue());
        header += createHeaderRow(PgnTag.ECO, gameDb.getChessGameInfo().getEco());
        header += createHeaderRow(PgnTag.WHITE_ELO, gameDb.getWhite().getElo());
        header += createHeaderRow(PgnTag.BLACK_ELO, gameDb.getBlack().getElo());

        return header;
    }

    private String createHeaderRow(PgnTag tag, String item) {
        if (item == null || item.equals(PgnGameParts.notAvailable)) {
            return "";
        }
        return "[" + tag.getKey() + " \"" + item + "\"]" + System.lineSeparator();
    }
}
