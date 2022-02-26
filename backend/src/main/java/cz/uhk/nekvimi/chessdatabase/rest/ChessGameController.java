package cz.uhk.nekvimi.chessdatabase.rest;

import cz.uhk.nekvimi.chessdatabase.*;
import cz.uhk.nekvimi.chessdatabase.dto.ChessGamePreviewDto;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
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
    public List<ChessGameDb> findAll() {
        return repository.findAll();
    }

    @PostMapping("/api/database")
    public List<ChessGamePreviewDto> saveGames(@RequestParam("file") MultipartFile pgnFile) {

        databaseParser = new PgnDatabaseParser(Charset.forName("Cp1252"));
        converter = new PgnGameConverter();
        List<ChessGamePreviewDto> gameDtos = new ArrayList<>();
        try {
            for (String game : databaseParser.splitDbToIndividualGames(pgnFile.getInputStream())) {
                var chessGame = databaseParser.parseToChessGame(game);
                repository.save(converter.convertToChessGameDb(chessGame));
                gameDtos.add(converter.convertToChessGameDto(chessGame));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameDtos;
    }
}
