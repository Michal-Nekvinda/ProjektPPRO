export class ChessGame {
    id: number;
    whiteName: string;
    whiteElo: string;
    blackName: string;
    blackElo: string;
    result: string;
    eco: string;
    tournament: string;
    date: string;
    errorMessage: string
}

export enum PgnTag {
    BLACK_NAME = "Black",
    WHITE_NAME = "White",
    BLACK_ELO = "BlackElo",
    WHITE_ELO = "WhiteElo",
    TOURNAMENT = "Event",
    RESULT = "Result",
    ROUND = "Round",
    ECO = "ECO",
    DATE = "Date",
}

export enum Result {
    WHITE_WIN = "1-0",
    DRAW = "1/2-1/2",
    BLACK_WIN = "0-1",
}

