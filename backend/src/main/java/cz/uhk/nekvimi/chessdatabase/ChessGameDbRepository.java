package cz.uhk.nekvimi.chessdatabase;

import cz.uhk.nekvimi.chessdatabase.entity.ChessGameDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessGameDbRepository extends JpaRepository<ChessGameDb, Long> {
}
