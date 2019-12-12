package juanolek.game;

import juanolek.Message;
import juanolek.exceptions.InvalidMoveException;
import juanolek.exceptions.TrashDataException;

import java.util.ArrayList;
import java.util.List;

public class GameLogic{

    int size;
    GamePawnType makingMove;
    GameTile[][] tiles;


    GameLogic(int size){
        this.size = size;
        this.tiles = new GameTile[size][size];
        for(int x = 0; x < size; x++){
            for(int y= 0; y < size; y++){
                tiles[x][y] = new GameTile();
            }
        }
        this.makingMove = GamePawnType.White;
    }

    synchronized ArrayList<GameBoardChange> setPawn(int x, int y, GamePawnType type) throws InvalidMoveException, TrashDataException {
        ArrayList<GameBoardChange> changes = new ArrayList<>();

        if(type == GamePawnType.Empty){
            //trash data warning
            throw new TrashDataException("Type must be either Black or White");
        }
        if(type != makingMove){
            throw new InvalidMoveException("The opponent must move first");
        }
        if(x < 0 || x >= size || y < 0 || y >= size){
            throw new InvalidMoveException("Invalid coordinates");
        }
        if(tiles[x][y].type != GamePawnType.Empty){
            throw new InvalidMoveException("Cell not empty");
        }

        tiles[x][y].type = type;
        changes.add(GameBoardChange.add(x, y, type));
        makingMove = GamePawnType.other(makingMove);

        return changes;
    }


}