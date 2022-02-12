package cz.uhk.nekvimi.chessdatabase.rest;

import cz.uhk.nekvimi.chessdatabase.PgnFile;
import cz.uhk.nekvimi.chessdatabase.ChessGameRepository;
import cz.uhk.nekvimi.chessdatabase.ChessGamesSaver;
import cz.uhk.nekvimi.chessdatabase.PgnParser;
import cz.uhk.nekvimi.chessdatabase.dto.ChessGamesInfoDto;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*")
public class ChessGameController {
    ChessGameRepository chessGameRepository;
    ChessGamesSaver chessGamesSaver;

    public ChessGameController(ChessGameRepository chessGameRepository){
        this.chessGameRepository = chessGameRepository;
    }

    @GetMapping("/api/chessGames")
    public List<ChessGameInfo> findAll(){

        return chessGameRepository.findAll();
    }

    @PostMapping("/api/database")
    public ChessGamesInfoDto create(@RequestParam("file") MultipartFile pgnFile){
        try {
            String pgnContent = new String(pgnFile.getInputStream().readAllBytes(), Charset.forName("Cp1252"));
            ChessGamesSaver chessGamesSaver = new ChessGamesSaver(new PgnParser());
            var newGamesInfoDto = chessGamesSaver.saveChessGames(pgnContent);
            return newGamesInfoDto;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
