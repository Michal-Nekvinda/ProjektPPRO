package cz.uhk.nekvimi.chessdatabase.export;

import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;

public interface ChessGameExporter {
    public String exportChessGameDb(ChessGameDb gameDb);
}
