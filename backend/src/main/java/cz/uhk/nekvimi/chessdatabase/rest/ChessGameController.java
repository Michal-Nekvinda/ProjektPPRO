package cz.uhk.nekvimi.chessdatabase.rest;

import cz.uhk.nekvimi.chessdatabase.*;
import cz.uhk.nekvimi.chessdatabase.dto.ChessGamePreviewDto;
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

    @GetMapping("/api/getChessGames")
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

    @PostMapping("/api/deleteGames")
    public void deleteGames(@RequestBody List<Long> ids) {
        for (long id : ids) {
            repository.deleteById(id);
        }
    }

    @PostMapping("/api/save")
    public ChessGamePreviewDto saveGame(String game) {
        var chessGame = databaseParser.parseToChessGame(game);
        var chessGameSaved = repository.save(converter.convertToChessGameDb(chessGame));
        return new ChessGamePreviewDto(chessGameSaved);
    }

    @PostMapping("/api/database")
    public List<ChessGamePreviewDto> saveGames(@RequestParam("file") MultipartFile pgnFile) {
        databaseParser = new PgnDatabaseParser();
        converter = new PgnGameConverter();
        List<ChessGamePreviewDto> gameDtos = new ArrayList<>();
        try {
            for (String game : databaseParser.splitDbToIndividualGames(pgnFile.getInputStream())) {
                gameDtos.add(saveGame(game));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameDtos;
    }


    @GetMapping("/api/deleteAll")
    public boolean deleteAll(){
        repository.deleteAll();
        return true;
    }

}
