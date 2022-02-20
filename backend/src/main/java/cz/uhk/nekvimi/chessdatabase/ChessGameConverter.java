package cz.uhk.nekvimi.chessdatabase;

import cz.uhk.nekvimi.chessdatabase.dto.ChessGamePreviewDto;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;

public interface ChessGameConverter<K, V> {
    ChessGameDb convertToChessGameDb(ChessGame<K, V> chessGame);

    ChessGamePreviewDto convertToChessGameDto(ChessGame<K, V> chessGame);
}
