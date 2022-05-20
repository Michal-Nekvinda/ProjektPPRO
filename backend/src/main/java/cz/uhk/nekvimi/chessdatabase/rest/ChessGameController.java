package cz.uhk.nekvimi.chessdatabase.rest;

import cz.uhk.nekvimi.chessdatabase.*;
import cz.uhk.nekvimi.chessdatabase.dto.ChessGamePreviewDto;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import cz.uhk.nekvimi.chessdatabase.parsers.ChessDatabaseParser;
import cz.uhk.nekvimi.chessdatabase.parsers.ChessGameParser;
import cz.uhk.nekvimi.chessdatabase.parsers.PgnDatabaseParser;
import cz.uhk.nekvimi.chessdatabase.parsers.PgnGameParser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChessGameController {
    ChessGameDbRepository repository;
    ChessDatabaseParser databaseParser;
    ChessGameParser gameParser;
    ChessGameExporter gameExporter;

    public ChessGameController(ChessGameDbRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/getGames")
    public List<ChessGamePreviewDto> findAll() {
        var gamesDb = repository.findAll();
        var gamesDto = new ArrayList<ChessGamePreviewDto>();
        for (var game : gamesDb) {
            gamesDto.add(new ChessGamePreviewDto(game));
        }
        return gamesDto;
    }

    @GetMapping("/api/getGame")
    public String getChessGame(@RequestParam("ID") Long id) {
        var game = repository.findById(id).get();
        return game.getChessGameInfo().getRawGameData();
    }

    @GetMapping("/api/exportGames")
    public String exportGames() {
        gameExporter = new PgnGameExporter();
        var gamesDb = repository.findAll();
        var exportedGames = new ArrayList<String>();
        for (var game : gamesDb) {
            exportedGames.add(gameExporter.exportChessGameDb(game));
        }

        String emptyLine = System.lineSeparator() + System.lineSeparator();
        return String.join(emptyLine, exportedGames);
    }

    @PostMapping("/api/deleteGames")
    public void deleteGames(@RequestBody List<Long> ids) {
        for (long id : ids) {
            repository.deleteById(id);
        }
    }

    @PostMapping("/api/saveNewGame")
    public ChessGamePreviewDto saveNewGame(@RequestBody String game) {
        gameParser = new PgnGameParser();
        var savedGame = gameParser.parseGame(game, "\n\n");
        return saveToDb(savedGame);
    }

    @PostMapping("/api/saveGames")
    public List<ChessGamePreviewDto> saveGames(@RequestParam("file") MultipartFile pgnFile) {
        databaseParser = new PgnDatabaseParser();
        gameParser = new PgnGameParser();
        List<ChessGamePreviewDto> gameDtos = new ArrayList<>();

        try {
            for (ChessGame game : databaseParser.splitDbToIndividualGames(pgnFile.getInputStream())) {
                var savedGame = saveToDb(gameParser.parseGame(game));
                if (savedGame.getErrorMessage() == null) {
                    gameDtos.add(savedGame);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameDtos;
    }

    private ChessGamePreviewDto saveToDb(ChessGameDb chessGameDb) {
        var error = Validator.validateGameDb(chessGameDb);

        if (error == null) {
            var chessGameSaved = repository.save(chessGameDb);
            return new ChessGamePreviewDto(chessGameSaved);
        }
        return new ChessGamePreviewDto(error);
    }
}
