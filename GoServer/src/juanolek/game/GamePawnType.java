package juanolek.game;

public enum GamePawnType {
    White,
    Black,
    Empty;

    public static GamePawnType other(GamePawnType type){
        if(type == White){
            return Black;
        }
        if(type == Black){
            return White;
        }
        return Empty;
    }
}
