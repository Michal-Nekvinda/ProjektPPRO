package cz.uhk.nekvimi.chessdatabase;

public interface ChessGame<K, V> {
    void setGamePart(K key, V value);
    V getGamePart(K key);
}
