package juanolek.game;

public class GameLogic{

    int size;
    GameTile[][] tiles;

    GameLogic(int size){
        this.size = size;
        this.tiles = new GameTile[size][size];
    }


}