package cz.uhk.nekvimi.chessdatabase.dto;

import cz.uhk.nekvimi.chessdatabase.DtoWithError;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameInfo;

import java.util.ArrayList;
import java.util.List;

public class ChessGamesInfoDto extends DtoWithError {
    List<ChessGameInfo> chessGamesInfo;

    public ChessGamesInfoDto() {
        this.chessGamesInfo = new ArrayList<>();
    }

    public void addGameInfo(ChessGameInfo gameInfo){
        chessGamesInfo.add(gameInfo);
    }
}
