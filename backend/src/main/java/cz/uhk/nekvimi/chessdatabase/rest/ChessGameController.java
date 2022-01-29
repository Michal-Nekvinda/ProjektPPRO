package cz.uhk.nekvimi.chessdatabase.rest;

import cz.uhk.nekvimi.chessdatabase.PngFile;
import cz.uhk.nekvimi.chessdatabase.ChessGameRepository;
import cz.uhk.nekvimi.chessdatabase.PngParser;
import cz.uhk.nekvimi.chessdatabase.entity.ChessGameInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ChessGameController {
    ChessGameRepository chessGameRepository;
    PngParser pngParser;

    public ChessGameController(ChessGameRepository chessGameRepository){
        this.chessGameRepository = chessGameRepository;
    }

    @GetMapping("/api/chessGames")
    public List<ChessGameInfo> findAll(){

        return chessGameRepository.findAll();
    }

    @PostMapping("/api/database")
    public void create(@RequestBody PngFile pngFile){
        PngParser parser = new PngParser();
        pa
        var x = pngFile;
        String s = "ss";
        int xr = 56;
    }
}
