package cz.uhk.nekvimi.chessdatabase;

import cz.uhk.nekvimi.chessdatabase.entity.ChessGameInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChessGameRepository extends JpaRepository<ChessGameInfo, Long> {
}
