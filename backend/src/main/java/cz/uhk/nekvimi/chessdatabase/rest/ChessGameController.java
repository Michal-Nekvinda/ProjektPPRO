package cz.uhk.nekvimi.chessdatabase.rest;

import cz.uhk.nekvimi.chessdatabase.*;
import cz.uhk.nekvimi.chessdatabase.dto.ChessGamePreviewDto;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChessGameController {
    ChessGameDbRepository repository;
    ChessDatabaseParser<PgnTag, String> databaseParser;
    ChessGameConverter<PgnTag, String> converter;

    public ChessGameController(ChessGameDbRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/chessGames")
    public List<ChessGamePreviewDto> findAll() {
        var gamesDb = repository.findAll();
        var gamesDto = new ArrayList<ChessGamePreviewDto>();
        for (var game : gamesDb) {
            gamesDto.add(new ChessGamePreviewDto(game));
        }
        return gamesDto;
    }

    @GetMapping("/api/getChessGame")
    public String getChessGame(@RequestParam("ID") Long id) {
        var game = repository.findById(id).get();
        return game.getChessGameInfo().getRawGameData();
    }

    @GetMapping("/api/deleteGames")
    public void deleteGames() {
        repository.deleteAll();
    }

    @PostMapping("/api/save")
    public long saveGame(ChessGameDb game) {
        return repository.save(game).getId();
    }

    @PostMapping("/api/database")
    public List<ChessGamePreviewDto> saveGames(@RequestParam("file") MultipartFile pgnFile) {

        databaseParser = new PgnDatabaseParser();
        converter = new PgnGameConverter();
        List<ChessGamePreviewDto> gameDtos = new ArrayList<>();
        try {
            for (String game : databaseParser.splitDbToIndividualGames(pgnFile.getInputStream())) {
                var chessGame = databaseParser.parseToChessGame(game);
                var chessGameSaved = repository.save(converter.convertToChessGameDb(chessGame));
                gameDtos.add(new ChessGamePreviewDto(chessGameSaved));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameDtos;
    }
}
