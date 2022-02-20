package cz.uhk.nekvimi.chessdatabase.rest;

import cz.uhk.nekvimi.chessdatabase.*;
import cz.uhk.nekvimi.chessdatabase.dto.ChessGamePreviewDto;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import cz.uhk.nekvimi.chessdatabase.entity.myFakeEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChessGameController {
    BlaRepo repo;
    ChessGameDbRepository repository;
    ChessDatabaseParser<PgnTag, String> databaseParser;
    ChessGameConverter<PgnTag, String> converter;

    public ChessGameController(ChessGameDbRepository repository, BlaRepo repo) {
        this.repository = repository;
        this.repo = repo;
    }

    @GetMapping("/api/chessGames")
    public List<ChessGameDb> findAll() {
        return repository.findAll();
    }

    @PostMapping("/api/database")
    public List<ChessGameDb> saveGames(@RequestParam("file") MultipartFile pgnFile) {

        databaseParser = new PgnDatabaseParser(Charset.forName("Cp1252"));
        converter = new PgnGameConverter();
        List<ChessGameDb> gameDtos = new ArrayList<>();
        ChessGamePreviewDto newGamesInfoDto = null;
        try {
            for (String game : databaseParser.splitDbToIndividualGames(pgnFile.getInputStream())) {
                var chessGame = databaseParser.parseToChessGame(game);
                repository.save(converter.convertToChessGameDb(chessGame));
                gameDtos.add(converter.convertToChessGameDb(chessGame));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameDtos;
    }
}
