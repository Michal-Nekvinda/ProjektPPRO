package cz.uhk.nekvimi.chessdatabase.entity;

import cz.uhk.nekvimi.chessdatabase.Pieces;

import javax.persistence.*;

@Entity
public class ChessGameDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne(cascade = {CascadeType.ALL})
    PlayerInfo white;
    @ManyToOne(cascade = {CascadeType.ALL})
    PlayerInfo black;
    @OneToOne(cascade = {CascadeType.ALL})
    ChessGameInfo chessGameInfo;
    @ManyToOne(cascade = {CascadeType.ALL})
    TournamentInfo tournamentInfo;

    public ChessGameDb() {
    }

    public ChessGameDb(PlayerInfo white, PlayerInfo black, ChessGameInfo chessGameInfo, TournamentInfo tournamentInfo) {
        setWhite(white);
        setBlack(black);
        this.chessGameInfo = chessGameInfo;
        this.tournamentInfo = tournamentInfo;
    }

    public PlayerInfo getWhite() {
        return white;
    }

    public void setWhite(PlayerInfo white) {
        this.white = white;
        this.white.setPieces(Pieces.WHITE);
    }

    public PlayerInfo getBlack() {
        return black;
    }

    public void setBlack(PlayerInfo black) {
        this.black = black;
        this.black.setPieces(Pieces.BLACK);
    }

    public ChessGameInfo getChessGameInfo() {
        return chessGameInfo;
    }

    public void setChessGameInfo(ChessGameInfo chessGameInfo) {
        this.chessGameInfo = chessGameInfo;
    }

    public TournamentInfo getTournamentInfo() {
        return tournamentInfo;
    }

    public void setTournamentInfo(TournamentInfo tournamentInfo) {
        this.tournamentInfo = tournamentInfo;
    }
}
